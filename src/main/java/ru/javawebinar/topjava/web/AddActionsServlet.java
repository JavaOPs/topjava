package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImp;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Vladimir_Sentso on 07.03.2016.
 */
public class AddActionsServlet extends HttpServlet{

    private static final Logger LOG = LoggerFactory.getLogger(AddActionsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int calories = Integer.parseInt(request.getParameter("calories"));
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");

        String dateTime = request.getParameter("dateTime");
        LocalDateTime time = LocalDateTime
                .parse(dateTime, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH));
        MealDao dao = new MealDaoMapImp();
        dao.addMeal(time, description, calories);
        LOG.debug("saved new user witch ");
        response.sendRedirect("/topjava/meal");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("redirect to addMeal");
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("dateTime", "");
        request.setAttribute("description", "п");
        request.setAttribute("calories", "");
        request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
    }

    private List<UserMealWithExceed> getFilteredMealsWithExceeded(MealDao dao) {
        return UserMealsUtil.getFilteredMealsWithExceeded(dao.getMealList(),
                LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }
}
