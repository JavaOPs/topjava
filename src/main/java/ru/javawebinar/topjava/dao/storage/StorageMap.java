package ru.javawebinar.topjava.dao.storage;

import ru.javawebinar.topjava.model.UserMeal;

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
    }
}
