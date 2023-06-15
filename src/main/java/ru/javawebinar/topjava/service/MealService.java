package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, Integer userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, Integer userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getBetweenOpen(LocalDate startDate, LocalDate endDate, Integer userId) {
        if ((startDate != null & endDate != null) && startDate.isAfter(endDate)) {
            throw new RuntimeException("can't get between half and open - startDate is more than endDate");
        }
        return repository.getBetweenOpen(
                startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.now() : endDate,
                userId);
    }
}