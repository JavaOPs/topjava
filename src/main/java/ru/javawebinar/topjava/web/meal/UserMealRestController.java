package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserMealService;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController implements MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(User.class);
    @Autowired
    private UserMealService service;



}
