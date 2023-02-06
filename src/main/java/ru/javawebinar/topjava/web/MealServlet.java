package ru.javawebinar.topjava.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;

/**
 * @author Alexei Valchuk, 06.02.2023, email: a.valchukav@gmail.com
 */

@WebServlet(ServletUrls.MEALS)
public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("getAll");
        req.setAttribute("mealList", MealsUtil.getWithExcesses(MealsUtil.MEAL_LIST, 2000));
        req.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(req, resp);
    }
}
