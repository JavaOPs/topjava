package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoInterface;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private DaoInterface<Meal, Long> dao;

    @Override
    public void init() throws ServletException {
        dao = new InMemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String forward = "/meals.jsp";
        String action = (req.getParameter("action") == null) ? "def" : req.getParameter("action");

        switch (action) {
            case ("delete"):
                log.info("doGet delete");
                dao.deleteById(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect("meals");
                break;
            case ("update"):
                log.info("doGet update");
                req.setAttribute("meal", dao.getOne(Long.parseLong(req.getParameter("id"))));
            case ("add"):
                log.info("doGet add");
                forward = "/editMeal.jsp";
                req.getRequestDispatcher(forward).forward(req, resp);
                break;
            default:
                log.info("doGet default");
                req.setAttribute("mealToList", filteredByStreams(dao.findAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_TEST));
                req.getRequestDispatcher(forward).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String forward = "/editMeal.jsp";
        Meal meal = new Meal();
        String idString = req.getParameter("id");

        log.info("doPost save begin");
        try{
            meal.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
            meal.setDescription(req.getParameter("description"));
            meal.setCalories(Integer.parseInt(req.getParameter("calories")));
            if (idString != null && !idString.isEmpty()) {
                meal.setId(Long.parseLong(req.getParameter("id")));
            }
            dao.save(meal);
        }catch (NumberFormatException | DateTimeParseException e){
            e.printStackTrace();
        }
        log.info("doPost save complete");

        req.getRequestDispatcher(forward).forward(req, resp);
    }
}

