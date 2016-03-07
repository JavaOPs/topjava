package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladimir_Sentso on 06.03.2016.
 */
public interface MealDao {

    UserMeal addMeal(LocalDateTime dateTime, String description, int calories);

    UserMeal removeMeal(long id);

    UserMeal updateMeal(UserMeal userMeal);

    UserMeal getMeal(long id);
    List<UserMeal> getMealList();

    default MealDao getMealDao() {
        return this;
    }
}
