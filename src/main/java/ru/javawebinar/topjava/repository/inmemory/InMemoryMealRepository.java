package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenOpen;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, authUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (isUserExists(userId)) {
            Map<Integer, Meal> meals = repository.get(userId);
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                log.info("user with id={} saves a meal with id={}", userId, meal.getId());
                meals.put(meal.getId(), meal);
                return meal;
            } else {
                // handle case: update, but not present in storage
                log.info("user with id={} saves a meal with id={}", userId, meal.getId());
                return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            }
        } else {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> meals = new HashMap<>();
            meals.put(meal.getId(), meal);
            log.info("user with id={} saves a meal with id={}", userId, meal.getId());
            repository.put(userId, meals);
            return meal;
        }
    }

    @Override
    public boolean delete(int id, Integer userId) {
        log.info("user with id={} deletes a meal with id={}", userId, id);
        if (isUserExists(userId)) {
            return repository.get(userId).remove(id) != null;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id, Integer userId) {
        log.info("user with id={} gets a meal with id={}", userId, id);
        return isUserExists(userId) ? repository.get(userId).get(id) : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        log.info("user with id={} gets all meals", userId);
        return getFilterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenOpen(LocalDate startDate, LocalDate endDate, Integer userId) {
        log.info("user with id={} gets meals between {} and {}", userId, startDate, endDate);
        Predicate<Meal> filter = meal -> isBetweenOpen(meal.getDate(), startDate, endDate);
        return getFilterByPredicate(userId, filter);
    }

    private List<Meal> getFilterByPredicate(Integer userId, Predicate<Meal> filter) {
        return isUserExists(userId) ?
                repository.get(userId).values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDate).reversed())
                        .collect(Collectors.toList()) :
                Collections.emptyList();
    }

    private boolean isUserExists(Integer userId) {
        return repository.containsKey(userId);
    }
}

