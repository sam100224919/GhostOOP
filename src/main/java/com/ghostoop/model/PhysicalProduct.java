package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;

/**
 * Represents a physical tangible item with a shipping weight and dimensions.
 * Demonstrates inheritance from {@link Product}, constructor chaining via super(...),
 * validation, and method overriding.
 */
public class PhysicalProduct extends Product {

    private static final long serialVersionUID = 1L;

    private double weight;
    private String dimensions;

    /**
     * Constructs a PhysicalProduct with the specified attributes.
     *
     * @param id     the unique product identifier
     * @param name   the product name
     * @param price  the unit price
     * @param weight the weight in kilograms/units; must be positive (> 0.0)
     * @throws ValidationException if any validation rule is violated
     */
    public PhysicalProduct(String id, String name, double price, double weight) {
        this(id, name, price, weight, "Standard");
    }

    /**
     * Overloaded constructor specifying both weight and physical dimensions.
     *
     * @param id         the unique product identifier
     * @param name       the product name
     * @param price      the unit price
     * @param weight     the weight in kilograms
     * @param dimensions physical dimensions description (e.g. "10x10x5 cm")
     */
    public PhysicalProduct(String id, String name, double price, double weight, String dimensions) {
        super(id, name, price);
        validateWeight(weight);
        validateDimensions(dimensions);
        this.weight = weight;
        this.dimensions = dimensions.trim();
    }

    /**
     * Returns the physical weight of this product.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Updates the weight of this physical product after validation.
     *
     * @param weight the new weight; must be strictly positive
     * @throws ValidationException if weight is not strictly positive or is non-finite
     */
    public void setWeight(double weight) {
        validateWeight(weight);
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        validateDimensions(dimensions);
        this.dimensions = dimensions.trim();
    }

    @Override
    public String getProductType() {
        return "PhysicalProduct";
    }

    private static void validateWeight(double weight) {
        if (Double.isNaN(weight) || Double.isInfinite(weight) || weight <= 0.0) {
            throw new ValidationException("Physical product weight must be a positive finite number greater than zero.");
        }
    }

    private static void validateDimensions(String dimensions) {
        if (dimensions == null || dimensions.trim().isEmpty()) {
            throw new ValidationException("Dimensions cannot be null or blank.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name='%s', price=$%.2f, weight=%.2f kg, dimensions='%s']",
                getProductType(), getId(), getName(), getPrice(), weight, dimensions);
    }
}
