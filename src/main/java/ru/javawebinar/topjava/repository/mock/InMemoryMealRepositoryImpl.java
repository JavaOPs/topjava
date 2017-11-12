package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userID) {
        log.info("delete {}", id);
        Meal result = get(id, userID);
        return result != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userID) {
        log.info("get {}", id);
        Meal result = repository.get(id);
        return result.belongsToUser(userID) ? result : null;
    }

    @Override
    public Collection<Meal> getAll(int userID) {
        log.info("getAll for user with id=" + userID);
        return repository.values().stream()
                .filter(meal -> meal.belongsToUser(userID))
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getDataFiltered(LocalDate beginDate, LocalDate endDate, int userID) {
        log.info("getAll from " + beginDate + "to " + endDate);
        return getAll(userID).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), beginDate, endDate))
                .collect(Collectors.toList());
    }
}

