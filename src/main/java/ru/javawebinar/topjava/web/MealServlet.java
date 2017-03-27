package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by alena.nikiforova on 26.03.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static final MealDao mealDao = new MealDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("edit".equals(request.getParameter("action"))) {
            LOG.debug("Edit action launched");
            int userId = Integer.parseInt(request.getParameter("userId"));
            Meal meal = MealsUtil.meals.get(userId);
            if (meal != null) {
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
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
        }

        LOG.debug("forward to meals.jsp");
        request.setAttribute("meals", MealsUtil.getWithExceeded(new ArrayList<>(MealsUtil.meals.values()),
                                                                    MealsUtil.caloriesPerDay));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
