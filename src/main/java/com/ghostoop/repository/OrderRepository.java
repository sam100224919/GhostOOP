package com.ghostoop.repository;

import com.ghostoop.model.Order;
import com.ghostoop.model.OrderStatus;
import java.util.List;

/**
 * Order-specific repository interface with specialized queries.
 */
public interface OrderRepository extends Repository<Order, String> {

    /**
     * Finds orders placed by a specific customer ID.
     *
     * @param customerId the customer identifier
     * @return list of matching orders
     */
    List<Order> findByCustomerId(String customerId);

    /**
     * Finds orders matching a specific {@link OrderStatus}.
     *
     * @param status the order status
     * @return list of matching orders
     */
    List<Order> findByStatus(OrderStatus status);
}
