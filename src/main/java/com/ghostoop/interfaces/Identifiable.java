package com.ghostoop.interfaces;

/**
 * Common entity contract providing an identifier.
 *
 * @param <ID> the type of identifier
 */
public interface Identifiable<ID> {

    /**
     * Returns the unique entity identifier.
     *
     * @return the identifier
     */
    ID getId();
}
