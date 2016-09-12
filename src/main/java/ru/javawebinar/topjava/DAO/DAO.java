package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

/**
 * Created by skorpion on 11.09.16.
 */
public interface DAO {
    public List<MealWithExceed> getAllMealsWithExceed();
}
