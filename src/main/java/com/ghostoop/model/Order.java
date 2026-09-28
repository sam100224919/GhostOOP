package com.ghostoop.model;

import com.ghostoop.exceptions.InvalidOrderStateException;
import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.Identifiable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a customer order in the GhostOOP system.
 * Demonstrates composition, identity management, total calculation, state transitions,
 * and immutable timestamps.
 */
public class Order implements Identifiable<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final Customer customer;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructs a new Order in PENDING status with an empty items list.
     *
     * @param id       the unique order ID
     * @param customer the customer placing the order
     * @throws ValidationException if id is invalid or customer is null
     */
    public Order(String id, Customer customer) {
        validateId(id);
        validateCustomer(customer);

        this.id = id.trim();
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;

        // Maintain bidirectional customer relationship
        this.customer.addOrder(this);
    }

    /**
     * Constructs an Order with an initial collection of items.
     *
     * @param id       the unique order ID
     * @param customer the customer placing the order
     * @param items    the collection of initial items
     */
    public Order(String id, Customer customer, List<OrderItem> items) {
        this(id, customer);
        if (items != null) {
            for (OrderItem item : items) {
                addItem(item);
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Returns an unmodifiable list of items contained in this order.
     *
     * @return unmodifiable items list
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Adds an item to the order if the order is still PENDING.
     *
     * @param item the item to add
     * @throws InvalidOrderStateException if order is not PENDING
     * @throws ValidationException        if item is null
     */
    public void addItem(OrderItem item) {
        if (this.status != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("Cannot add items to an order that is in " + status + " status.");
        }
        if (item == null) {
            throw new ValidationException("Cannot add null OrderItem to order.");
        }
        this.items.add(item);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Overloaded helper to add a product and quantity directly.
     *
     * @param product  the product
     * @param quantity the quantity
     */
    public void addItem(Product product, int quantity) {
        addItem(new OrderItem(product, quantity));
    }

    /**
     * Calculates the total monetary cost of the entire order.
     *
     * @return total cost
     */
    public double calculateTotal() {
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Transitions the order to a new status with validation.
     *
     * @param targetStatus the next order status
     * @throws InvalidOrderStateException if transition is illegal
     */
    public void transitionTo(OrderStatus targetStatus) {
        this.status.validateTransition(targetStatus);
        this.status = targetStatus;
        this.updatedAt = LocalDateTime.now();
    }

    private static void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Order ID cannot be null or blank.");
        }
    }

    private static void validateCustomer(Customer customer) {
        if (customer == null) {
            throw new ValidationException("Order customer cannot be null.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Order [id=%s, customer=%s, status=%s, itemsCount=%d, total=$%.2f, createdAt=%s]",
                id, customer.getName(), status, items.size(), calculateTotal(), createdAt);
    }
}
