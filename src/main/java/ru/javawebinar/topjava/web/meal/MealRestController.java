package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final MealService service;
    private final MealRepository repository;

    @Autowired
    public MealRestController(MealService service, MealRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public Collection<MealTo> getAll(int userId){
        return repository.getAll(userId);
    }

    public Meal create(Meal meal, int userId){
        checkNew(meal);
        return service.save(meal, userId);
    }

    public Meal get(int id, int userId){
        return service.get(id, userId);
    }

    public void update(Meal meal, int userId){
        service.update(meal, userId);
    }

    public void delete(int id, int userId){
        service.delete(id, userId);
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return service.getBetweenHalfOpen(startDate,endDate, SecurityUtil.authUserId());
    }
}