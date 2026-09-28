package com.ghostoop.model;

import com.ghostoop.exceptions.InsufficientInventoryException;
import com.ghostoop.exceptions.ValidationException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages inventory quantities, stock reservation, and release for products.
 * Demonstrates encapsulation, Map collections, input validation, and business rule enforcement.
 */
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    // product.getId() -> available stock
    private final Map<String, Integer> stockLevels;
    // product.getId() -> reserved stock (e.g., during checkout before payment settlement)
    private final Map<String, Integer> reservedLevels;

    public Inventory() {
        this.stockLevels = new HashMap<>();
        this.reservedLevels = new HashMap<>();
    }

    /**
     * Adds stock for a given product.
     *
     * @param product  the product
     * @param quantity positive quantity to add
     * @throws ValidationException if product is null or quantity <= 0
     */
    public void addStock(Product product, int quantity) {
        if (product == null) {
            throw new ValidationException("Product cannot be null when adding stock.");
        }
        addStock(product.getId(), quantity);
    }

    /**
     * Overloaded method to add stock by product ID directly.
     *
     * @param productId the unique product identifier
     * @param quantity  positive quantity to add
     */
    public void addStock(String productId, int quantity) {
        validateProductId(productId);
        if (quantity <= 0) {
            throw new ValidationException("Stock quantity to add must be strictly greater than 0.");
        }
        stockLevels.put(productId, stockLevels.getOrDefault(productId, 0) + quantity);
    }

    /**
     * Returns the available stock quantity for a product ID.
     *
     * @param productId the product ID
     * @return current available quantity (>= 0)
     */
    public int getAvailableStock(String productId) {
        validateProductId(productId);
        return stockLevels.getOrDefault(productId, 0);
    }

    /**
     * Checks if requested quantity is available in stock.
     *
     * @param productId the product ID
     * @param quantity  the required quantity
     * @return true if available, false otherwise
     */
    public boolean isAvailable(String productId, int quantity) {
        if (quantity <= 0) return false;
        return getAvailableStock(productId) >= quantity;
    }

    /**
     * Reserves stock for an order item. Moves stock from available to reserved.
     *
     * @param productId the product ID
     * @param quantity  quantity to reserve
     * @throws InsufficientInventoryException if requested quantity exceeds available stock
     */
    public void reserveStock(String productId, int quantity) {
        validateProductId(productId);
        if (quantity <= 0) {
            throw new ValidationException("Quantity to reserve must be greater than 0.");
        }
        int current = getAvailableStock(productId);
        if (current < quantity) {
            throw new InsufficientInventoryException(String.format(
                    "Insufficient stock for product '%s'. Requested: %d, Available: %d", productId, quantity, current));
        }
        stockLevels.put(productId, current - quantity);
        reservedLevels.put(productId, reservedLevels.getOrDefault(productId, 0) + quantity);
    }

    /**
     * Releases previously reserved stock back to available inventory (e.g. In case of cancelled order).
     *
     * @param productId the product ID
     * @param quantity  quantity to release
     */
    public void releaseReservedStock(String productId, int quantity) {
        validateProductId(productId);
        if (quantity <= 0) {
            throw new ValidationException("Quantity to release must be greater than 0.");
        }
        int reserved = reservedLevels.getOrDefault(productId, 0);
        if (reserved < quantity) {
            throw new ValidationException(String.format(
                    "Cannot release %d items of '%s'; only %d items are currently reserved.", productId, quantity, reserved));
        }
        reservedLevels.put(productId, reserved - quantity);
        stockLevels.put(productId, stockLevels.getOrDefault(productId, 0) + quantity);
    }

    /**
     * Confirms the deduction of reserved stock after an order is successfully finalized/paid.
     *
     * @param productId the product ID
     * @param quantity  quantity to finalize
     */
    public void confirmReservedStockDeduction(String productId, int quantity) {
        validateProductId(productId);
        if (quantity <= 0) {
            throw new ValidationException("Quantity to confirm deduction must be greater than 0.");
        }
        int reserved = reservedLevels.getOrDefault(productId, 0);
        if (reserved < quantity) {
            throw new ValidationException(String.format(
                    "Cannot finalize deduction of %d items of '%s'; only %d items are currently reserved.", productId, quantity, reserved));
        }
        reservedLevels.put(productId, reserved - quantity);
    }

    /**
     * Directly reduces available stock without prior reservation.
     *
     * @param productId the product ID
     * @param quantity  quantity to deduct
     */
    public void deductStock(String productId, int quantity) {
        validateProductId(productId);
        if (quantity <= 0) {
            throw new ValidationException("Quantity to deduct must be greater than 0.");
        }
        int current = getAvailableStock(productId);
        if (current < quantity) {
            throw new InsufficientInventoryException(String.format(
                    "Insufficient stock for product '%s'. Requested: %d, Available: %d", productId, quantity, current));
        }
        stockLevels.put(productId, current - quantity);
    }

    /**
     * Returns an unmodifiable snapshot view of all stock levels.
     *
     * @return unmodifiable map of productId -> available quantity
     */
    public Map<String, Integer> getAllStockLevels() {
        return Collections.unmodifiableMap(stockLevels);
    }

    /**
     * Returns an unmodifiable snapshot view of all reserved stock levels.
     *
     * @return unmodifiable map of productId -> reserved quantity
     */
    public Map<String, Integer> getAllReservedLevels() {
        return Collections.unmodifiableMap(reservedLevels);
    }

    private static void validateProductId(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new ValidationException("Product ID cannot be null or blank.");
        }
    }
}
