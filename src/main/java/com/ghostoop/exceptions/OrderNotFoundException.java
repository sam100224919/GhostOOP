package com.ghostoop.exceptions;

/**
 * Thrown when a requested order cannot be found.
 */
public class OrderNotFoundException extends EntityNotFoundException {
    public OrderNotFoundException(String orderId) {
        super("Order with ID '" + orderId + "' was not found.");
    }
}
