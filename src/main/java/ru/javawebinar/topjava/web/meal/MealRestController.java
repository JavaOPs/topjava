package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Vladimir_Sentso on 13.03.2016.
 */
public interface MealRestController {

    boolean delete(int id);

    boolean delete(UserMeal userMeal);

    UserMeal save(UserMeal userMeal);

    boolean update(UserMeal userMeal);

    UserMeal get(int id);

    Collection<UserMealWithExceed> getAllMealByUser(User user);


    Collection<UserMealWithExceed> getAllMealByUserBetweenDateTime(User user, LocalDateTime startTime,
                                                                   LocalDateTime endTime);

    boolean delete(User user);


    Collection<UserMealWithExceed> getAllMealByUser(User user, int calories);


    Collection<UserMealWithExceed> getAllMealByUserBetweenDateTime(User user, LocalDateTime startTime,
                                                                   LocalDateTime endTime, int calories);
}
