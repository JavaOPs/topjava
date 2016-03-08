package ru.javawebinar.topjava.dao.storage;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vladimir_Sentso on 06.03.2016.
 */
public class StorageMap {
    private static StorageMap ourInstance = new StorageMap();
    private final Map<Long, UserMeal> storageMap = new ConcurrentHashMap<>(12000, 0.5f);

    public static StorageMap getInstance() {
        return ourInstance;
    }

    public Map<Long, UserMeal> getStorage() {
        return storageMap;
    }

    private StorageMap() {
        UserMeal u1 = new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        UserMeal u2 = new UserMeal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        UserMeal u3 = new UserMeal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        UserMeal u4 = new UserMeal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        UserMeal u5 = new UserMeal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        UserMeal u6 = new UserMeal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        storageMap.put(u1.getId(), u1);
        storageMap.put(u2.getId(), u2);
        storageMap.put(u3.getId(), u3);
        storageMap.put(u4.getId(), u4);
        storageMap.put(u5.getId(), u5);
        storageMap.put(u6.getId(), u6);
    }
}

