package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer in the GhostOOP system.
 * Demonstrates inheritance from {@link User}, constructor chaining, and composition
 * by managing collections of wishlist/saved {@link Product} objects and {@link Order} records.
 */
public class Customer extends User {

    private static final long serialVersionUID = 1L;

    private String phoneNumber;
    private final List<Product> wishlist;
    private final List<Order> orders;

    /**
     * Constructs a Customer with ID, name, email, and phone number.
     *
     * @param id          the unique customer identifier
     * @param name        the customer name
     * @param email       the customer email
     * @param phoneNumber the customer phone number
     */
    public Customer(String id, String name, String email, String phoneNumber) {
        super(id, name, email);
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber.trim();
        this.wishlist = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    /**
     * Overloaded constructor providing a default placeholder phone number when omitted.
     *
     * @param id   the unique customer identifier
     * @param name the customer name
     * @param email the customer email
     */
    public Customer(String id, String name, String email) {
        this(id, name, email, "N/A");
    }

    /**
     * Overloaded constructor for compatibility with initial model.
     *
     * @param id   the unique customer identifier
     * @param name the customer name
     */
    public Customer(String id, String name) {
        this(id, name, id == null ? "invalid@example.com" : id.toLowerCase().trim() + "@example.com", "N/A");
    }

    /**
     * Constructs a Customer with ID, name, and an initial product list.
     * Performs a defensive copy and validates elements.
     *
     * @param id the unique customer identifier
     * @param name the customer name
     * @param initialProducts the initial products list
     */
    public Customer(String id, String name, List<Product> initialProducts) {
        this(id, name);
        if (initialProducts != null) {
            for (Product p : initialProducts) {
                if (p == null) {
                    throw new ValidationException("Initial product list cannot contain null elements.");
                }
                this.wishlist.add(p);
            }
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber.trim();
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    /**
     * Returns an unmodifiable view of the customer's wishlist products.
     *
     * @return unmodifiable product list
     */
    public List<Product> getWishlist() {
        return Collections.unmodifiableList(wishlist);
    }

    /**
     * Legacy alias for wishlist products for compatibility.
     *
     * @return unmodifiable product list
     */
    public List<Product> getProducts() {
        return getWishlist();
    }

    /**
     * Returns the count of products in the customer's wishlist.
     *
     * @return number of products
     */
    public int getProductCount() {
        return this.wishlist.size();
    }

    /**
     * Calculates the total value of all products in the customer's wishlist.
     *
     * @return total price of wishlist products
     */
    public double calculateTotalProductValue() {
        double total = 0.0;
        for (Product product : wishlist) {
            total += product.getPrice();
        }
        return total;
    }

    /**
     * Adds a product to the customer's wishlist.
     *
     * @param product the product to add
     * @throws ValidationException if product is null
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new ValidationException("Cannot add null product to customer wishlist.");
        }
        this.wishlist.add(product);
    }

    /**
     * Removes a product from the customer's wishlist.
     *
     * @param product the product to remove
     * @return true if removed, false otherwise
     */
    public boolean removeProduct(Product product) {
        return this.wishlist.remove(product);
    }

    /**
     * Returns an unmodifiable list of orders associated with this customer.
     *
     * @return unmodifiable orders list
     */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    /**
     * Associates an order with this customer.
     *
     * @param order the order to associate
     * @throws ValidationException if order is null
     */
    public void addOrder(Order order) {
        if (order == null) {
            throw new ValidationException("Cannot add null order to customer.");
        }
        if (!this.orders.contains(order)) {
            this.orders.add(order);
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new ValidationException("Phone number cannot be null or blank.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name='%s', email='%s', phone='%s', wishlistSize=%d, ordersCount=%d]",
                getRole(), getId(), getName(), getEmail(), phoneNumber, wishlist.size(), orders.size());
    }
}
