package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealsRepository;
import ru.javawebinar.topjava.repository.InMemoryRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private final InMemoryRepository mealsRepo;

    public MealServlet() {
        mealsRepo = new MealsRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward;
        String action = request.getParameter("action");

        switch (action == null ? "listMeals" : action) {
            case "delete":
                mealsRepo.delete(Integer.parseInt(request.getParameter("mealId")));
                response.sendRedirect("meals");
                break;
            case "edit":
                forward = INSERT_OR_EDIT;
                Meal meal = mealsRepo.getById(Integer.parseInt(request.getParameter("mealId")));
                request.setAttribute("meal", meal);
                request.setAttribute("action", action);
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            case "insert":
                forward = INSERT_OR_EDIT;
                request.setAttribute("action", action);
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            case "listMeals":
            default:
                forward = LIST_MEALS;
                request.setAttribute("meals", createListTo(mealsRepo.getAll(), caloriesPerDay));
                request.getRequestDispatcher(forward).forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);

        String mealId = request.getParameter("mealId");

        if(mealId == null || mealId.isEmpty()) {
            mealsRepo.add(meal);
        }
        else {
            meal.setId(Integer.parseInt(mealId));
            mealsRepo.update(meal);
        }

        request.setAttribute("meals", createListTo(mealsRepo.getAll(), caloriesPerDay));
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }
}
