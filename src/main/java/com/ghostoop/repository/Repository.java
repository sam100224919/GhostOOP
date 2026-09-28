package com.ghostoop.repository;

import com.ghostoop.interfaces.Identifiable;
import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface defining standard CRUD operations.
 * Demonstrates abstraction, generics, and separation of data access concerns.
 *
 * @param <T>  the entity type, must implement {@link Identifiable}
 * @param <ID> the primary key/identifier type
 */
public interface Repository<T extends Identifiable<ID>, ID> {

    /**
     * Saves an entity to the repository.
     *
     * @param entity the entity to save; cannot be null
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the identifier; cannot be null
     * @return an {@link Optional} containing the entity if found, or empty if not
     */
    Optional<T> findById(ID id);

    /**
     * Returns all entities currently stored in the repository.
     *
     * @return a List of entities
     */
    List<T> findAll();

    /**
     * Checks whether an entity with the specified ID exists in the repository.
     *
     * @param id the identifier
     * @return true if exists, false otherwise
     */
    boolean existsById(ID id);

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier
     * @return true if removed, false if not found
     */
    boolean deleteById(ID id);

    /**
     * Returns the total count of entities in the repository.
     *
     * @return total count
     */
    long count();

    /**
     * Clears all entities from the repository.
     */
    void clear();
}
