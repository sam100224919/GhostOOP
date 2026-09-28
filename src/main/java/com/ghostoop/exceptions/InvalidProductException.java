package com.ghostoop.exceptions;

/**
 * Thrown when a product is invalid or violates product-specific constraints.
 */
public class InvalidProductException extends ValidationException {
    public InvalidProductException(String message) {
        super(message);
    }
}
