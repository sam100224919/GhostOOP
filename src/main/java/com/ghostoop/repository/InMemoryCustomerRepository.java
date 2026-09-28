package com.ghostoop.repository;

import com.ghostoop.model.Customer;
import java.util.Optional;

/**
 * In-memory implementation of {@link CustomerRepository}.
 */
public class InMemoryCustomerRepository extends InMemoryRepository<Customer, String> implements CustomerRepository {

    @Override
    public Optional<Customer> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return storage.values().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }
}
