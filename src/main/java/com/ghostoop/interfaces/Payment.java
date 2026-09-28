package com.ghostoop.interfaces;

import com.ghostoop.exceptions.PaymentException;
import com.ghostoop.model.PaymentResult;

/**
 * Common payment processor contract.
 * Demonstrates polymorphism and interface-driven design for payment implementations.
 */
public interface Payment {

    /**
     * Returns the name or type of this payment method.
     *
     * @return payment method type
     */
    String getMethodName();

    /**
     * Processes a payment transaction for the specified monetary amount.
     *
     * @param amount the non-negative monetary amount to charge
     * @return a {@link PaymentResult} containing status and transaction metadata
     * @throws PaymentException if payment details are invalid or amount cannot be processed
     */
    PaymentResult processPayment(double amount);
}
