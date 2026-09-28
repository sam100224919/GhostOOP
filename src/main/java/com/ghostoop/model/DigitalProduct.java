package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;

/**
 * Represents a digital product with a file size specification in megabytes and download URL.
 * Demonstrates inheritance from {@link Product}, constructor chaining via super(...),
 * validation, and method overriding.
 */
public class DigitalProduct extends Product {

    private static final long serialVersionUID = 1L;

    private double fileSizeMb;
    private String downloadUrl;

    /**
     * Constructs a DigitalProduct with the specified attributes.
     *
     * @param id         the unique product identifier
     * @param name       the product name
     * @param price      the unit price
     * @param fileSizeMb the file size in megabytes; must be positive (> 0.0)
     * @throws ValidationException if any validation rule is violated
     */
    public DigitalProduct(String id, String name, double price, double fileSizeMb) {
        this(id, name, price, fileSizeMb, "https://downloads.ghostoop.com/" + (id == null ? "placeholder" : id.trim().toLowerCase()));
    }

    /**
     * Overloaded constructor specifying both file size and explicit download URL.
     *
     * @param id          the unique product identifier
     * @param name        the product name
     * @param price       the unit price
     * @param fileSizeMb  the file size in megabytes
     * @param downloadUrl the secure download URL
     */
    public DigitalProduct(String id, String name, double price, double fileSizeMb, String downloadUrl) {
        super(id, name, price);
        validateFileSizeMb(fileSizeMb);
        validateDownloadUrl(downloadUrl);
        this.fileSizeMb = fileSizeMb;
        this.downloadUrl = downloadUrl.trim();
    }

    /**
     * Returns the file size in megabytes.
     *
     * @return the file size in MB
     */
    public double getFileSizeMb() {
        return fileSizeMb;
    }

    /**
     * Updates the file size in megabytes after validation.
     *
     * @param fileSizeMb the new file size; must be strictly positive
     * @throws ValidationException if file size is not strictly positive or is non-finite
     */
    public void setFileSizeMb(double fileSizeMb) {
        validateFileSizeMb(fileSizeMb);
        this.fileSizeMb = fileSizeMb;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        validateDownloadUrl(downloadUrl);
        this.downloadUrl = downloadUrl.trim();
    }

    @Override
    public String getProductType() {
        return "DigitalProduct";
    }

    private static void validateFileSizeMb(double fileSizeMb) {
        if (Double.isNaN(fileSizeMb) || Double.isInfinite(fileSizeMb) || fileSizeMb <= 0.0) {
            throw new ValidationException("Digital product file size must be a positive finite number greater than zero.");
        }
    }

    private static void validateDownloadUrl(String downloadUrl) {
        if (downloadUrl == null || downloadUrl.trim().isEmpty()) {
            throw new ValidationException("Download URL cannot be null or blank.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name='%s', price=$%.2f, fileSize=%.2f MB, downloadUrl='%s']",
                getProductType(), getId(), getName(), getPrice(), fileSizeMb, downloadUrl);
    }
}
