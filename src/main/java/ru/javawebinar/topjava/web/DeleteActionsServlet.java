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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Vladimir_Sentso on 07.03.2016.
 */
public class DeleteActionsServlet extends HttpServlet {
    private static final Logger LOG = getLogger(DeleteActionsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        MealDao dao = new MealDaoMapImp();
        dao.removeMeal(Long.parseLong(id));
        LOG.debug("remove meal witch id = " + id);
        List<UserMealWithExceed> lst = getFilteredMealsWithExceeded(dao);
        req.setAttribute("mealList", lst);
        LOG.debug("redirect to mealListWitchExceed");
        req.getRequestDispatcher("/mealListWitchExceed.jsp").forward(req, resp);
    }

    private List<UserMealWithExceed> getFilteredMealsWithExceeded(MealDao dao) {
        return UserMealsUtil.getFilteredMealsWithExceeded(dao.getMealList(),
                LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    }
}
