package com.ghostoop.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * Persistence abstraction allowing saving and loading collections of domain entities.
 * Decouples domain and service layers from direct file I/O details.
 *
 * @param <T> the type of entity to persist
 */
public interface DataPersistence<T> {

    /**
     * Persists a list of entities to persistent storage.
     *
     * @param data the list of entities to persist
     * @throws IOException if an I/O or persistence error occurs
     */
    void saveAll(List<T> data) throws IOException;

    /**
     * Loads all persisted entities from storage.
     *
     * @return a list of loaded entities
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if entity class cannot be resolved
     */
    List<T> loadAll() throws IOException, ClassNotFoundException;
}
