package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alexei Valchuk, 06.02.2023, email: a.valchukav@gmail.com
 */

public interface MealRepository {

    Meal save (Meal meal, int userId);

    boolean delete (int id, int userId);

    Meal get (int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
