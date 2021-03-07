package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealTo> getAll(){
        return MealsUtil.getTos(new ArrayList<>(service.getAll()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal create(Meal meal){
        checkNew(meal);
        return service.save(meal);
    }

    public Meal get(int id){
        return service.get(id);
    }

    public void update(Meal meal){
        service.update(meal);
    }

    public void delete(int id){
        service.delete(id);
    }
}