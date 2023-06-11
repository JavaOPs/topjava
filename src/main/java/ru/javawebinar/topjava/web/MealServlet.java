package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private final Logger log = getLogger(MealServlet.class);

    private final Storage storage = new MapStorage();

    private final int caloriesPerDay = 2000;

    @Override
    public void init(ServletConfig config) {
        getList().forEach(storage::save);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") != null ? request.getParameter("action") : "default";
        switch (Objects.requireNonNull(action)) {
            case ("default"):
                forwardToMeals(request, response);
                break;
            case ("delete"):
                storage.delete(Integer.parseInt(request.getParameter("id")));
                log.debug("redirect to meals");
                response.sendRedirect("/topjava/meals");
                break;
            case ("update"):
                forwardToEdit(request, response, storage.get(Integer.parseInt(request.getParameter("id"))));
                break;
            case ("add"):
                forwardToEdit(request, response, new Meal(LocalDateTime.now(), "Еда", 500));
                break;
            default:
                throw new RuntimeException("unknown " + action + " passes to the doGet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        String id = request.getParameter("id");
        if (id.equals("")) {
            storage.save(new Meal(dateTime, description, calories));
        } else {
            Meal meal = new Meal(Integer.parseInt(id), dateTime, description, calories);
            storage.update(meal);
        }

        forwardToMeals(request, response);
    }

    private void forwardToMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        List<MealTo> filteredByStreams = filteredByStreams(storage.getAllSorted(), caloriesPerDay);
        request.setAttribute("list", filteredByStreams);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    private void forwardToEdit(HttpServletRequest request, HttpServletResponse response, Meal meal) throws ServletException, IOException {
        log.debug("forward to edit");
        request.setAttribute("meal", createTo(meal, meal.getCalories() > caloriesPerDay));
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }
}
