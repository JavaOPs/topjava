package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.dao.storage.StorageMap;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Vladimir_Sentso on 07.03.2016.
 */
public class MealDaoMapImp implements MealDao {
    private static final AtomicLong counter = new AtomicLong(0);
    private final Map<Long, UserMeal> storage = StorageMap.getInstance().getStorage();

    @Override
    public UserMeal addMeal(LocalDateTime dateTime, String description, int calories) {
        final long tmp = counter.incrementAndGet();
        return storage.put(tmp, new UserMeal(tmp, dateTime, description, calories));
    }

    @Override
    public UserMeal removeMeal(long id) {
        return storage.remove(id);
    }

    @Override
    public UserMeal updateMeal(UserMeal userMeal) {
        return storage.put(userMeal.getId(), userMeal);
    }

    @Override
    public UserMeal getMeal(long id) {
        return storage.get(id);
    }

    @Override
    public List<UserMeal> getMealList() {
        return new ArrayList<>(storage.values());
    }
}
