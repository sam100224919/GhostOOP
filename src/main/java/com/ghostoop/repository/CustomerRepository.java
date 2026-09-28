package com.ghostoop.repository;

import com.ghostoop.model.Customer;
import java.util.Optional;

/**
 * Customer-specific repository interface with specialized queries.
 */
public interface CustomerRepository extends Repository<Customer, String> {

    /**
     * Finds a customer by their email address.
     *
     * @param email the email address
     * @return Optional containing Customer if found
     */
    Optional<Customer> findByEmail(String email);
}
