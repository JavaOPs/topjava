package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> userMealsRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal->save(meal, USER_ID));
    }


    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealMap = userMealsRepository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealMap = userMealsRepository.get(userId);
        return mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealMap = userMealsRepository.get(userId);
        return mealMap != null ?null:mealMap.get(id);
    }

    @Override
    public List<MealTo> getAll(int userId) {
        return getAllFiltered(userId,meal ->true);
    }

    private List<MealTo> getAllFiltered(int userId, Predicate<Meal> filter){
        Map<Integer, Meal> mealMap = userMealsRepository.get(userId);
        return CollectionUtils.isEmpty(mealMap)? Collections.emptyList()
                : mealMap.values().stream().filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).map(meal -> new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),meal.getCalories()>2000))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime,endDateTime));
    }
}

