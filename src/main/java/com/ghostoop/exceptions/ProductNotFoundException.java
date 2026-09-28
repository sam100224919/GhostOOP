package com.ghostoop.exceptions;

/**
 * Thrown when a requested product cannot be found.
 */
public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(String productId) {
        super("Product with ID '" + productId + "' was not found.");
    }
}
