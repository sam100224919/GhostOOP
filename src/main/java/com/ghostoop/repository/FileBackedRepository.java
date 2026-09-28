package com.ghostoop.repository;

import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.DataPersistence;
import com.ghostoop.interfaces.Identifiable;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * File-backed repository adapter extending {@link InMemoryRepository}.
 * Demonstrates combining in-memory speed with file persistence synchronization.
 *
 * @param <T>  the serializable entity type
 * @param <ID> the identifier type
 */
public class FileBackedRepository<T extends Identifiable<ID> & Serializable, ID> extends InMemoryRepository<T, ID> {

    private final DataPersistence<T> persistence;

    public FileBackedRepository(DataPersistence<T> persistence) {
        if (persistence == null) {
            throw new ValidationException("Persistence handler cannot be null.");
        }
        this.persistence = persistence;
        loadFromDisk();
    }

    /**
     * Loads entities from persistent storage into memory.
     */
    public void loadFromDisk() {
        try {
            List<T> loaded = persistence.loadAll();
            storage.clear();
            for (T item : loaded) {
                storage.put(item.getId(), item);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load repository data from persistence storage.", e);
        }
    }

    /**
     * Flushes current memory entities to persistent storage.
     */
    public void saveToDisk() {
        try {
            persistence.saveAll(findAll());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save repository data to persistence storage.", e);
        }
    }

    @Override
    public T save(T entity) {
        T saved = super.save(entity);
        saveToDisk();
        return saved;
    }

    @Override
    public boolean deleteById(ID id) {
        boolean removed = super.deleteById(id);
        if (removed) {
            saveToDisk();
        }
        return removed;
    }

    @Override
    public void clear() {
        super.clear();
        saveToDisk();
    }
}
