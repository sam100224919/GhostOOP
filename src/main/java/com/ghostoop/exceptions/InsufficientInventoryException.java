package com.ghostoop.exceptions;

/**
 * Thrown when an inventory operation cannot be fulfilled due to insufficient stock.
 */
public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}
