package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isDateBetweenHalfOpen;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach((meal) -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        if(meal.getUserId() != userId) { return null; }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if(repository.get(id).getUserId() != userId) { return false; }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if(repository.get(id).getUserId() != userId) { return null; }
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> mealsList = new ArrayList<>(repository.values());
        return mealsList.stream()
                .filter((meal) -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getBetweenHalfOpen(int userId, int caloriesPerDay, LocalTime startTime, LocalTime endTime, @Nullable LocalDateTime startDate, @Nullable LocalDateTime endDate) {
        return getFilteredTos(getAll(userId), caloriesPerDay, startTime, endTime).stream()
                .filter((mealTo -> isDateBetweenHalfOpen(mealTo.getDateTime(), startDate, endDate)))
                .collect(Collectors.toList());
    }
}

