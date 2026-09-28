package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a line item inside an {@link Order}.
 * Demonstrates composition, immutable snapshot pricing, and quantity validation.
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Product product;
    private final int quantity;
    private final double unitPrice;

    /**
     * Constructs an OrderItem capturing the product, quantity, and locking in the unit price.
     *
     * @param product  the product being purchased; cannot be null
     * @param quantity the positive quantity; must be greater than 0
     * @throws ValidationException if product is null or quantity <= 0
     */
    public OrderItem(Product product, int quantity) {
        validateProduct(product);
        validateQuantity(quantity);

        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
    }

    /**
     * Overloaded constructor allowing explicit override of snapshot unit price (e.g. For discounts).
     *
     * @param product   the product being purchased
     * @param quantity  the quantity
     * @param unitPrice the unit price at purchase time
     * @throws ValidationException if parameters violate validation rules
     */
    public OrderItem(Product product, int quantity, double unitPrice) {
        validateProduct(product);
        validateQuantity(quantity);
        validatePrice(unitPrice);

        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Calculates the line-item subtotal (unitPrice * quantity).
     *
     * @return the subtotal amount
     */
    public double getSubtotal() {
        return unitPrice * quantity;
    }

    private static void validateProduct(Product product) {
        if (product == null) {
            throw new ValidationException("OrderItem product cannot be null.");
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new ValidationException("OrderItem quantity must be strictly greater than 0.");
        }
    }

    private static void validatePrice(double price) {
        if (Double.isNaN(price) || Double.isInfinite(price) || price < 0.0) {
            throw new ValidationException("OrderItem price must be a non-negative finite number.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity &&
                Double.compare(orderItem.unitPrice, unitPrice) == 0 &&
                Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return String.format("OrderItem [product=%s, qty=%d, unitPrice=$%.2f, subtotal=$%.2f]",
                product.getName(), quantity, unitPrice, getSubtotal());
    }
}
