package com.ghostoop.exceptions;

/**
 * Thrown when an invalid transition between order states is attempted.
 */
public class InvalidOrderStateException extends IllegalStateException {
    public InvalidOrderStateException(String message) {
        super(message);
    }
}
