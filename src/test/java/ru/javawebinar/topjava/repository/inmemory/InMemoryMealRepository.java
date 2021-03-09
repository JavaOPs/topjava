package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, Map<Integer, Meal>> userMealsRepository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal->save(meal, USER_ID));
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
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
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId,meal ->true);
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter){
        Map<Integer, Meal> mealMap = userMealsRepository.get(userId);
        return CollectionUtils.isEmpty(mealMap)? Collections.emptyList()
                : mealMap.values().stream().filter(filter)
                //.sorted(Comparator.comparing(Meal::getDateTime).reversed()).map(meal -> new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),meal.getCalories()>2000))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime,endDateTime));
    }
}

