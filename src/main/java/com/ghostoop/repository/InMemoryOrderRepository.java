package com.ghostoop.repository;

import com.ghostoop.model.Order;
import com.ghostoop.model.OrderStatus;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In-memory implementation of {@link OrderRepository}.
 */
public class InMemoryOrderRepository extends InMemoryRepository<Order, String> implements OrderRepository {

    @Override
    public List<Order> findByCustomerId(String customerId) {
        if (customerId == null) {
            return List.of();
        }
        return storage.values().stream()
                .filter(o -> o.getCustomer() != null && o.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        if (status == null) {
            return List.of();
        }
        return storage.values().stream()
                .filter(o -> o.getStatus() == status)
                .collect(Collectors.toList());
    }
}
