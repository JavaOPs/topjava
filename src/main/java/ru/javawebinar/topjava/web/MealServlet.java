package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.time.LocalDateTime.of;
import static java.time.LocalDateTime.parse;
import static java.time.Month.MAY;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Vladimir_Sentso on 05.03.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm", Locale.ENGLISH);
        List<UserMealWithExceed> lst = UserMealsUtil.getFilteredMealsWithExceeded(Arrays.asList(
                new UserMeal(1, parse("30/May/2015 10:00", formatter), "Завтрак", 500),
                new UserMeal(2, of(2015, MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(3, of(2015, MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(4, of(2015, MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(5, of(2015, MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(6, of(2015, MAY, 31, 20, 0), "Ужин", 510)),
                LocalTime.of(0, 0), LocalTime.of(23, 59), 2200);
        // response.sendRedirect("mealListWitchExceed.jsp");
        LOG.debug("redirect to mealList");
        request.setAttribute("mealList", lst);
        request.getRequestDispatcher("/mealListWitchExceed.jsp").forward(request, response);
    }


}
