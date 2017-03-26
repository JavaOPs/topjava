package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by alena.nikiforova on 26.03.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals.jsp");

        request.setAttribute("abc", MealsUtil.getWithExceeded(MealsUtil.meals, MealsUtil.caloriesPerDay));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

}
