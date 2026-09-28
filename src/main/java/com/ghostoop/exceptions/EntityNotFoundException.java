package com.ghostoop.exceptions;

/**
 * Thrown when an entity cannot be found by its identifier or query criteria.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
