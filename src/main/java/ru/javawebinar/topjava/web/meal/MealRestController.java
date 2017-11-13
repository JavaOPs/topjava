package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.FilterHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static org.springframework.format.annotation.DateTimeFormat.*;

@Controller
public class MealRestController {
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        return service.save(meal);
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.id(), AuthorizedUser.getCaloriesPerDay());
    }

    public Collection<MealWithExceed> getTimeDataFiltered(@DateTimeFormat(iso = ISO.DATE) LocalDate beginDate,
                                                          @DateTimeFormat(iso = ISO.DATE) LocalDate endDate,
                                                          @DateTimeFormat(iso = ISO.TIME) LocalTime beginTime,
                                                          @DateTimeFormat(iso = ISO.TIME) LocalTime endTime) {
        AuthorizedUser.getFilter().setBeginDate(beginDate);
        AuthorizedUser.getFilter().setEndDate(endDate);
        AuthorizedUser.getFilter().setBeginTime(beginTime);
        AuthorizedUser.getFilter().setEndTime(endTime);
        if (beginTime == null) {beginTime = LocalTime.MIN;}
        if (endTime == null) {endTime = LocalTime.MAX;}
        if (beginDate == null) {beginDate = LocalDate.MIN;}
        if (endDate == null) {endDate = LocalDate.MAX;}
        return service.getTimeDataFiltered(beginDate, endDate, beginTime, endTime,
                AuthorizedUser.id(), AuthorizedUser.getCaloriesPerDay());
    }

    public FilterHolder getUserFilter() {
        return AuthorizedUser.getFilter();
    }
}