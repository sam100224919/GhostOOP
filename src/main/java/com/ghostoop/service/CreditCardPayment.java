package com.ghostoop.service;

import com.ghostoop.exceptions.PaymentException;
import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.Payment;
import com.ghostoop.model.PaymentResult;
import java.util.UUID;

/**
 * Credit card payment simulation implementing {@link Payment}.
 * Demonstrates polymorphism, data masking/encapsulation, and validation.
 */
public class CreditCardPayment implements Payment {

    private final String cardNumber;
    private final String cardHolderName;
    private final String expirationDate;
    private final String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        validateCardNumber(cardNumber);
        validateCardHolder(cardHolderName);
        validateExpirationDate(expirationDate);
        validateCvv(cvv);

        this.cardNumber = cardNumber.replaceAll("\\s+", "");
        this.cardHolderName = cardHolderName.trim();
        this.expirationDate = expirationDate.trim();
        this.cvv = cvv.trim();
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getMaskedCardNumber() {
        if (cardNumber.length() <= 4) {
            return "****";
        }
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public String getMethodName() {
        return "CreditCard";
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0.0) {
            throw new PaymentException("Payment amount must be a positive finite value.");
        }

        // Simulate credit card authorization
        String txId = "CC-TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String message = String.format("Credit Card payment of $%.2f approved for card holder '%s' (%s).",
                amount, cardHolderName, getMaskedCardNumber());
        return new PaymentResult(txId, true, amount, getMethodName(), message);
    }

    private static void validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.replaceAll("\\s+", "").length() < 13) {
            throw new ValidationException("Card number must contain at least 13 digits.");
        }
    }

    private static void validateCardHolder(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Card holder name cannot be null or blank.");
        }
    }

    private static void validateExpirationDate(String exp) {
        if (exp == null || !exp.trim().matches("^(0[1-9]|1[0-2])/([0-9]{2})$")) {
            throw new ValidationException("Expiration date must be in MM/YY format.");
        }
    }

    private static void validateCvv(String cvv) {
        if (cvv == null || !cvv.trim().matches("^[0-9]{3,4}$")) {
            throw new ValidationException("CVV must be 3 or 4 digits.");
        }
    }
}
