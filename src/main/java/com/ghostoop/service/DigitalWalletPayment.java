package com.ghostoop.service;

import com.ghostoop.exceptions.PaymentException;
import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.Payment;
import com.ghostoop.model.PaymentResult;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Digital wallet payment simulation (e.g. PayPal, Apple Pay) implementing {@link Payment}.
 * Demonstrates polymorphism, email/account verification, and different payment workflow logic.
 */
public class DigitalWalletPayment implements Payment {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final String walletProvider;
    private final String walletEmail;

    public DigitalWalletPayment(String walletProvider, String walletEmail) {
        validateProvider(walletProvider);
        validateWalletEmail(walletEmail);

        this.walletProvider = walletProvider.trim();
        this.walletEmail = walletEmail.trim();
    }

    public String getWalletProvider() {
        return walletProvider;
    }

    public String getWalletEmail() {
        return walletEmail;
    }

    @Override
    public String getMethodName() {
        return "DigitalWallet (" + walletProvider + ")";
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0.0) {
            throw new PaymentException("Payment amount must be a positive finite value.");
        }

        // Simulate digital wallet token checkout
        String txId = "DW-" + walletProvider.toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String message = String.format("Digital wallet payment of $%.2f processed via %s account '%s'.",
                amount, walletProvider, walletEmail);
        return new PaymentResult(txId, true, amount, getMethodName(), message);
    }

    private static void validateProvider(String provider) {
        if (provider == null || provider.trim().isEmpty()) {
            throw new ValidationException("Wallet provider name cannot be null or blank.");
        }
    }

    private static void validateWalletEmail(String email) {
        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Invalid digital wallet email address: " + email);
        }
    }
}
