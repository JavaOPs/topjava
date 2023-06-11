package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MapStorage implements Storage {
    private static final Logger log = getLogger(MapStorage.class);

    private final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void clear() {
        log.debug("clear");
        storage.clear();
    }

    @Override
    public void save(Meal meal) {
        Integer id = meal.getId();
        if (id == null) {
            int newId = counter.incrementAndGet();
            log.debug("save meal with id = {}", newId);
            meal.setId(newId);
            storage.putIfAbsent(newId, meal);
        } else {
            throw new RuntimeException("meal with id = " + id + " exists in the storage");
        }
    }

    @Override
    public Meal get(Integer id) {
        Meal meal = storage.getOrDefault(id, null);
        if (meal != null) {
            log.debug("get meal with id = {}", id);
            return meal;
        } else {
            throw new RuntimeException("meal with id = " + id + " does not exist in the storage");
        }
    }

    @Override
    public void update(Meal meal) {
        Integer id = meal.getId();
        if (id != null) {
            log.debug("update meal with id = {}", meal.getId());
            storage.replace(id, meal);
        } else {
            throw new RuntimeException("meal with id = " + id + " does not exist in the storage");
        }
    }

    @Override
    public void delete(Integer id) {
        if (id != null && storage.containsKey(id)) {
            log.debug("delete meal with id = {}", id);
            storage.remove(id);
        } else {
            throw new RuntimeException("meal with id = " + id + " does not exist in the storage");
        }
    }

    @Override
    public List<Meal> getAllSorted() {
        log.debug("return sorted list");
        return storage.values().stream().
                sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        log.debug("return size");
        return storage.size();
    }
}
