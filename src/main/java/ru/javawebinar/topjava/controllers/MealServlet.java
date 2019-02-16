package ru.javawebinar.topjava.controllers;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    List<Meal> list = new CopyOnWriteArrayList<>();
    List<MealTo> mealToList = new CopyOnWriteArrayList<>();

    @Override
    public void init() throws ServletException {
        super.init();
        list = Arrays.asList(
                new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2018, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2018, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2018, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        mealToList = MealsUtil.getFilteredWithExcess(list, TimeUtil.MIN_TIME, TimeUtil.MAX_TIME, 2000);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setAttribute("meals", mealToList);
        //resp.sendRedirect("meals.jsp");
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
