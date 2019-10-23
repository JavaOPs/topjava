package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.Repo;
import ru.javawebinar.topjava.util.MealsTransformer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int DEFAULT_CALORIES_PER_DAY = 2000;
    private static List<MealTo> newMeal;
    private Integer id = 7;
    Repo manager = new Repo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("show map");

        newMeal = MealsTransformer.getFiltered(Repo.getMealToStorage(), DEFAULT_CALORIES_PER_DAY);
        Map<Integer, MealTo> mapOfMeals = newMeal.stream().collect(Collectors.toMap(MealTo::getId, y -> y));
        req.setAttribute("meals", mapOfMeals);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("delete");
        if (action.equals("Delete")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            manager.delete(id);
        } else {
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"), formatter);
            String description = req.getParameter("description");
            Integer calories = Integer.parseInt(req.getParameter("calories"));
            manager.create(new Meal(id, dateTime, description, calories));
            id++;
        }
        resp.sendRedirect("/topjava_war/meals");


    }


}
