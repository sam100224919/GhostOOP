package com.ghostoop.exceptions;

/**
 * Thrown when a requested customer cannot be found.
 */
public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(String customerId) {
        super("Customer with ID '" + customerId + "' was not found.");
    }
}
