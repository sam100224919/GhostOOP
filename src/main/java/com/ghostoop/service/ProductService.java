package com.ghostoop.service;

import com.ghostoop.exceptions.InvalidProductException;
import com.ghostoop.exceptions.ProductNotFoundException;
import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.model.Product;
import com.ghostoop.repository.ProductRepository;
import java.util.List;

/**
 * Service managing product catalog operations and business rules.
 */
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        if (productRepository == null) {
            throw new ValidationException("ProductRepository cannot be null.");
        }
        this.productRepository = productRepository;
    }

    /**
     * Registers a new product in the catalog.
     *
     * @param product the product to register
     * @return the saved product
     */
    public Product registerProduct(Product product) {
        if (product == null) {
            throw new InvalidProductException("Product to register cannot be null.");
        }
        if (productRepository.existsById(product.getId())) {
            throw new InvalidProductException("Product with ID '" + product.getId() + "' already exists.");
        }
        return productRepository.save(product);
    }

    /**
     * Retrieves a product by its ID or throws {@link ProductNotFoundException}.
     *
     * @param productId the unique product ID
     * @return the product
     */
    public Product getProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new ValidationException("Product ID cannot be null or blank.");
        }
        return productRepository.findById(productId.trim())
                .orElseThrow(() -> new ProductNotFoundException(productId.trim()));
    }

    /**
     * Updates product pricing.
     *
     * @param productId the product ID
     * @param newPrice  the new price
     * @return the updated product
     */
    public Product updateProductPrice(String productId, double newPrice) {
        Product product = getProduct(productId);
        product.setPrice(newPrice);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    public List<Product> getProductsAffordableUnder(double maxPrice) {
        return productRepository.findByPriceLessThanEqual(maxPrice);
    }

    public boolean removeProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return false;
        }
        return productRepository.deleteById(productId.trim());
    }
}
