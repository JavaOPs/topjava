package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

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
    public UserMeal save(UserMeal userMeal) {

        return repository.save(userMeal, userMeal.getUserID());
    }

    @Override
    public boolean update(UserMeal userMeal) {
        return save(userMeal) != null;
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public boolean remove(UserMeal userMeal) {
        return remove(userMeal.getId());
    }

    @Override
    public boolean remove(int id) {
        return repository.delete(id);
    }

    @Override
    public Collection<UserMeal> getAllUserMeal(int userID) {
        return repository.getAll(userID);
    }
}
