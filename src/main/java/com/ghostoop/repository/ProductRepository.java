package com.ghostoop.repository;

import com.ghostoop.model.Product;
import java.util.List;

/**
 * Product-specific repository interface with specialized query methods.
 */
public interface ProductRepository extends Repository<Product, String> {

    /**
     * Finds products matching a specific product type (e.g. PhysicalProduct, DigitalProduct).
     *
     * @param type the product type
     * @return list of matching products
     */
    List<Product> findByType(String type);

    /**
     * Finds products with price less than or equal to the specified maximum.
     *
     * @param maxPrice the maximum price
     * @return list of matching products
     */
    List<Product> findByPriceLessThanEqual(double maxPrice);
}
