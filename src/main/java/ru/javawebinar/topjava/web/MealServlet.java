package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.CrudDAO;
import ru.javawebinar.topjava.DAO.MealDB;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("delete".equals(request.getParameter("action"))) {
            Meal meal = new Meal(Integer.parseInt(request.getParameter("id")), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
            CrudDAO.getInstance().delete(meal);
        }
        LOG.debug("redirect to userList");
        request.setAttribute("mealList", MealDB.getInstance().getAllMealsWithExceed());
        request.setAttribute("formatter", formatter);
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);

    }
}
