package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImp;
import ru.javawebinar.topjava.model.UserMeal;
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
public class UpdateActionsServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UpdateActionsServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        long id = Long.valueOf(req.getParameter("id").trim());
        int calories = Integer.parseInt(req.getParameter("calories").trim());
        String description = req.getParameter("description");
        String dateTime = req.getParameter("dateTime").trim();
        LocalDateTime time = LocalDateTime
                .parse(dateTime, DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH));
        UserMeal userMeal = new UserMeal(id, time, description, calories);
        MealDao dao = new MealDaoMapImp();
        dao.updateMeal(userMeal);
        LOG.debug("updated  user witch " + id);
        resp.sendRedirect("/topjava/meal");

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        MealDao dao = new MealDaoMapImp();
        UserMeal userMeal = dao.getMeal(id);
        String dateTime = userMeal.getDateTime()
                .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH));
        String description = userMeal.getDescription();
        int calories = userMeal.getCalories();

        LOG.debug("redirect to updateMeal");

        req.setCharacterEncoding("UTF-8");
        req.setAttribute("id", id);
        req.setAttribute("dateTime", dateTime);
        req.setAttribute("description", description);
        req.setAttribute("calories", calories);
        req.getRequestDispatcher("/updateMeal.jsp").forward(req, resp);
    }

    private List<UserMealWithExceed> getFilteredMealsWithExceeded(MealDao dao) {
        return UserMealsUtil.getFilteredMealsWithExceeded(dao.getMealList(),
                LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }
}
