package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

    static final AtomicInteger counter = new AtomicInteger(START_SEQ);

    final Map<Integer, T> map = new ConcurrentHashMap<>();

    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        if (entity.isNew()) {
            entity.setId(counter.getAndIncrement());
            map.put(entity.getId(), entity);
            return entity;
        }
        return map.computeIfPresent(entity.getId(), (id, oldT) -> entity);
    }

    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    public T get(int id) {
        return map.get(id);
    }

    Collection<T> getCollection() {
        return map.values();
    }

    void put(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        map.put(entity.id(), entity);
    }
}