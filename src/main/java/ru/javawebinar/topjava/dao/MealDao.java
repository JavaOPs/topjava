package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Vladimir_Sentso on 06.03.2016.
 */
public interface MealDao {

    UserMeal addMeal(LocalDateTime dateTime, String description, int calories);

    UserMeal removeMeal(UserMeal userMeal);

    UserMeal updateMeal(UserMeal userMeal);

    List<UserMeal> getMealList();

}
