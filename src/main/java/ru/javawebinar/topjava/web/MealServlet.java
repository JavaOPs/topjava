package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoInterface;
import ru.javawebinar.topjava.dao.DaoMealMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by alena.nikiforova on 26.03.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static final DaoInterface<Meal> mealDao = new DaoMealMap(MealsUtil.meals);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("edit".equals(request.getParameter("action"))) {
            LOG.debug("Edit action launched");
            int userId = Integer.parseInt(request.getParameter("userId"));
            Meal meal = mealDao.getById(userId);
            if (meal != null) {
                request.setAttribute("doAction", "edit");
                request.setAttribute("item", meal);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            }
        }
        if ("delete".equals(request.getParameter("action"))) {
            int id = Integer.parseInt(request.getParameter("userId"));
            LOG.debug("Delete action launched for id = " + id);
            mealDao.delete(id);
        }
        if ("add".equals(request.getParameter("action"))) {
            LOG.debug("Add action launched");
            request.setAttribute("doAction", "add");
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
        }

        LOG.debug("forward to meals from delete.jsp");
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(mealDao.getAll(), MealsUtil.caloriesPerDay);
        List<MealWithExceed> sortedMeals = meals.stream()
                                                .sorted(Comparator.comparing(MealWithExceed::getDateTime))
                                                .collect(Collectors.toList());
        request.setAttribute("meals", sortedMeals);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("POST method invoked");

        request.setCharacterEncoding("UTF-8");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date") + " " + request.getParameter("time"),
                MealsUtil.formatter);
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            mealDao.update(new Meal(date, description, calories, id));
        } else {
            mealDao.add(new Meal(date, description, calories));
        }

        LOG.debug("forward to meals from edit/add.jsp");
        response.sendRedirect("/topjava/meals");
    }
}
