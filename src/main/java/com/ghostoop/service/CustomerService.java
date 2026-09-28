package com.ghostoop.service;

import com.ghostoop.exceptions.CustomerNotFoundException;
import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.model.Customer;
import com.ghostoop.model.Product;
import com.ghostoop.repository.CustomerRepository;
import java.util.List;

/**
 * Service managing customer records, profiles, and wishlist interactions.
 */
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        if (customerRepository == null) {
            throw new ValidationException("CustomerRepository cannot be null.");
        }
        this.customerRepository = customerRepository;
    }

    /**
     * Registers a new customer after ensuring email uniqueness.
     *
     * @param customer the customer to register
     * @return the saved customer
     */
    public Customer registerCustomer(Customer customer) {
        if (customer == null) {
            throw new ValidationException("Customer cannot be null.");
        }
        if (customerRepository.existsById(customer.getId())) {
            throw new ValidationException("Customer with ID '" + customer.getId() + "' already exists.");
        }
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new ValidationException("Customer with email '" + customer.getEmail() + "' already exists.");
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomer(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new ValidationException("Customer ID cannot be null or blank.");
        }
        return customerRepository.findById(customerId.trim())
                .orElseThrow(() -> new CustomerNotFoundException(customerId.trim()));
    }

    public Customer getCustomerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or blank.");
        }
        return customerRepository.findByEmail(email.trim())
                .orElseThrow(() -> new CustomerNotFoundException("email: " + email.trim()));
    }

    public void addProductToWishlist(String customerId, Product product) {
        Customer customer = getCustomer(customerId);
        customer.addProduct(product);
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
