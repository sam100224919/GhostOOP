package com.ghostoop.exceptions;

/**
 * Exception thrown when validation fails for a domain model or business entity.
 */
public class ValidationException extends IllegalArgumentException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
