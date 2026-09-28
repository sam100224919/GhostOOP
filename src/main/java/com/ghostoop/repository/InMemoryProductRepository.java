package com.ghostoop.repository;

import com.ghostoop.model.Product;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In-memory implementation of {@link ProductRepository}.
 * Demonstrates inheritance from generic {@link InMemoryRepository} and stream-based querying.
 */
public class InMemoryProductRepository extends InMemoryRepository<Product, String> implements ProductRepository {

    @Override
    public List<Product> findByType(String type) {
        if (type == null) {
            return List.of();
        }
        return storage.values().stream()
                .filter(p -> p.getProductType().equalsIgnoreCase(type.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByPriceLessThanEqual(double maxPrice) {
        return storage.values().stream()
                .filter(p -> p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
