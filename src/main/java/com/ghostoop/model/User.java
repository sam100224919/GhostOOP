package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.Identifiable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Abstract base class representing a user within the GhostOOP system.
 * Demonstrates abstraction, encapsulation, and common identity/contact management.
 */
public abstract class User implements Identifiable<String>, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final String id;
    private String name;
    private String email;

    /**
     * Constructs a User with validated identifier, name, and email.
     *
     * @param id    the unique user identifier
     * @param name  the user name
     * @param email the user email address
     * @throws ValidationException if any input violates domain constraints
     */
    public User(String id, String name, String email) {
        validateId(id);
        validateName(name);
        validateEmail(email);

        this.id = id.trim();
        this.name = name.trim();
        this.email = email.trim();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email.trim();
    }

    /**
     * Returns the role of this user (e.g. Customer, Employee).
     *
     * @return the role descriptor
     */
    public abstract String getRole();

    protected static void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new ValidationException("User ID cannot be null or blank.");
        }
    }

    protected static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("User name cannot be null or blank.");
        }
    }

    protected static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Invalid email address: " + email);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name='%s', email='%s']", getRole(), id, name, email);
    }
}
