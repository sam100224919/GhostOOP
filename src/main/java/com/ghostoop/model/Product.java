package com.ghostoop.model;

import com.ghostoop.exceptions.InvalidProductException;
import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.Identifiable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract base class representing a product in the GhostOOP system.
 * Demonstrates encapsulation, abstraction, identity semantics, and data validation.
 */
public abstract class Product implements Identifiable<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String name;
    private double price;

    /**
     * Constructs a Product with the specified identifier, name, and unit price.
     *
     * @param id    the unique product identifier; cannot be null or blank
     * @param name  the product name; cannot be null or blank
     * @param price the product price; must be non-negative
     * @throws ValidationException if any input violates domain validation rules
     */
    public Product(String id, String name, double price) {
        validateId(id);
        validateName(name);
        validatePrice(price);

        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
    }

    /**
     * Returns the unique identifier of this product.
     *
     * @return the product id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of this product.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of this product after validation.
     *
     * @param name the new name; cannot be null or blank
     * @throws ValidationException if the name is invalid
     */
    public void setName(String name) {
        validateName(name);
        this.name = name.trim();
    }

    /**
     * Returns the unit price of this product.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Updates the unit price of this product after validation.
     *
     * @param price the new unit price; must be non-negative
     * @throws ValidationException if price is negative, NaN, or infinite
     */
    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }

    /**
     * Returns the specific product type descriptor.
     * Subclasses must implement this method to describe their concrete classification.
     *
     * @return a String representing the concrete product type
     */
    public abstract String getProductType();

    protected static void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("Product ID cannot be null or blank.");
        }
    }

    protected static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Product name cannot be null or blank.");
        }
    }

    protected static void validatePrice(double price) {
        if (Double.isNaN(price) || Double.isInfinite(price) || price < 0.0) {
            throw new ValidationException("Product price must be a non-negative finite number.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name='%s', price=$%.2f]",
                getProductType(), id, name, price);
    }
}
