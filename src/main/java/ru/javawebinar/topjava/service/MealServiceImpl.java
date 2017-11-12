package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userID) throws NotFoundException {
        if (repository.delete(id, userID)) {
            throw new NotFoundException(
                    String.format("Meal with id=%s belongs to user with id=%s not found!", id, userID));
        }
    }

    @Override
    public Meal get(int id, int userID) throws NotFoundException {
        Meal result = repository.get(id, userID);
        if (result == null) {
            throw new NotFoundException(
                    String.format("Meal with id=%s belongs to user with id=%s not found!", id, userID));
        }
        return result;
    }

    @Override
    public Collection<MealWithExceed> getAll(int userID) {
        return getWithExceeded(repository.getAll(userID), AuthorizedUser.getCaloriesPerDay());
    }

    @Override
    public Collection<MealWithExceed> getTimeDataFiltered(LocalDateTime beginDate, LocalDateTime endDate, int userID) {
        return getFilteredWithExceeded(
                repository.getDataFiltered(beginDate.toLocalDate(), endDate.toLocalDate(), userID),
                beginDate.toLocalTime(), endDate.toLocalTime(), AuthorizedUser.getCaloriesPerDay()
        );
    }
}