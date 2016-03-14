package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */

@Controller
public class UserMealServiceImpl implements UserMealService {
    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal, int userID) {

        return repository.save(userMeal, userID);
    }

    @Override
    public boolean update(UserMeal userMeal, int userID) {
        return save(userMeal, userID) != null;
    }

    @Override
    public UserMeal get(int id, int userID) {
        return repository.get(id, userID);
    }

    @Override
    public boolean remove(int id, int userID) {
        return repository.delete(id, userID);
    }

    @Override
    public Collection<UserMeal> getAllUserMeal(int userID) {
        return repository.getAll(userID);
    }

    @Override
    public Collection<UserMeal> getBetweenUserMeal(int userID, LocalDate start, LocalDate end) {
        return repository.getBetween(start, end, userID);
    }

    @Override
    public boolean deleteAllMeal(int userID) {
        Collection<UserMeal> temp = getAllUserMeal(userID);
        temp.forEach(userMeal -> remove(userMeal.getId(), userID));
        return temp.size() > 0;
    }


}
