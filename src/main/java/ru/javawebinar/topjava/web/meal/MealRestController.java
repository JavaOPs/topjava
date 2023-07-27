package ru.javawebinar.topjava.web.meal;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {

    public MealRestController(MealService service) {
        super(service);
    }

    public Meal get(int id) {
        return super.get(id);
    }

    public void delete(int id) {
        super.delete(id);
    }

    public List<MealTo> getAll() {
        return super.getAll();
    }

    public Meal create(Meal meal) {
        return super.create(meal);
    }

    public void update(Meal meal, int id) {
        super.update(meal, id);
    }

    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}