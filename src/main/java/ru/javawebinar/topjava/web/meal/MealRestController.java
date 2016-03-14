package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

/**
 * Created by Vladimir_Sentso on 13.03.2016.
 */
public interface MealRestController {

    UserMeal get(int id);

    void delete(int id);

    Collection<UserMeal> getAll();

}
