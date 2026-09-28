package com.ghostoop.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the result or transaction receipt of a processed payment.
 */
public class PaymentResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String transactionId;
    private final boolean successful;
    private final double amount;
    private final String paymentMethod;
    private final String message;
    private final LocalDateTime timestamp;

    public PaymentResult(String transactionId, boolean successful, double amount, String paymentMethod, String message) {
        this.transactionId = transactionId;
        this.successful = successful;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentResult that = (PaymentResult) o;
        return successful == that.successful &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, successful, amount, paymentMethod);
    }

    @Override
    public String toString() {
        return String.format("PaymentResult [txId=%s, success=%b, amount=$%.2f, method='%s', message='%s']",
                transactionId, successful, amount, paymentMethod, message);
    }
}
