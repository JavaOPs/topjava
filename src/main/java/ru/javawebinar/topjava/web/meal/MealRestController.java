package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * Created by Vladimir_Sentso on 13.03.2016.
 */
public interface MealRestController {

    UserMeal get(int id);

    void delete(int id);

    Collection<UserMeal> getAll();

    void deleteAll();

    void update(UserMeal meal);

    UserMeal create(UserMeal meal);

    Collection<UserMeal> getBetween(LocalDate start, LocalDate endTime);

    Collection<UserMealWithExceed> getAllExceedMeal();

    Collection<UserMealWithExceed> getBetweenExceedMeal(LocalDate start, LocalDate end);

    Collection<UserMealWithExceed> getAllExceedMeal(int calories);

    Collection<UserMealWithExceed> getBetweenExceedMeal(LocalDate start, LocalDate end, int calories);

    Collection<UserMealWithExceed> getBetweenExceedMealWitchTime(LocalDate startD, LocalDate endD, LocalTime start,
                                                                 LocalTime end, int calories);

    Collection<UserMealWithExceed> getBetweenExceedMealWitchTime(LocalDate startD, LocalDate endD, LocalTime start,
                                                                 LocalTime end);
}
