package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.*;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping (value = UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {

    static final String REST_URL = "/rest/meals";

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll()
    {
     return super.getAll();
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(int id)
    {
        return super.get(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete (@RequestParam (value = "id") int id)
    {
        super.delete(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal meal, @RequestParam (value = "id") int id)
    {
        super.update(meal, id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal create(@RequestBody UserMeal meal)
    {
        return super.create(meal);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<UserMealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate, @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate, @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(
                startDate != null ? startDate : TimeUtil.MIN_DATE, startTime != null ? startTime : LocalTime.MIN,
                endDate != null ? endDate : TimeUtil.MAX_DATE, endTime != null ? endTime : LocalTime.MAX);
 


    }




}
