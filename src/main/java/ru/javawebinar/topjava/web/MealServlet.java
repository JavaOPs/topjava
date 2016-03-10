package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImp;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Vladimir_Sentso on 05.03.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm", Locale.ENGLISH);
        MealDao dao = new MealDaoMapImp();
        List<UserMealWithExceed> lst = getFilteredMealsWithExceeded(dao);
        LOG.debug("redirect to mealList");
        request.setAttribute("mealList", lst);
        request.getRequestDispatcher("/mealListWitchExceed.jsp").forward(request, response);
    }

    private List<UserMealWithExceed> getFilteredMealsWithExceeded(MealDao dao) {
        return UserMealsUtil.getFilteredMealsWithExceeded(dao.getMealList(),
                LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }


}
