package com.ghostoop.repository;

import com.ghostoop.exceptions.ValidationException;
import com.ghostoop.interfaces.Identifiable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic in-memory repository backed by a LinkedHashMap to preserve insertion order.
 * Demonstrates generics, collection encapsulation, and reusable data access logic.
 *
 * @param <T>  the entity type
 * @param <ID> the primary key/identifier type
 */
public class InMemoryRepository<T extends Identifiable<ID>, ID> implements Repository<T, ID> {

    protected final Map<ID, T> storage = new LinkedHashMap<>();

    @Override
    public T save(T entity) {
        if (entity == null) {
            throw new ValidationException("Cannot save null entity.");
        }
        if (entity.getId() == null) {
            throw new ValidationException("Entity ID cannot be null when saving.");
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean existsById(ID id) {
        if (id == null) {
            return false;
        }
        return storage.containsKey(id);
    }

    @Override
    public boolean deleteById(ID id) {
        if (id == null) {
            return false;
        }
        return storage.remove(id) != null;
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
