package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoInterface;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.dao.SomeDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static String MEALTO_LIST = "/meals.jsp";
    private static String EDIT_MEAL = "/editMeal.jsp";
    private DaoInterface dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mealId = req.getParameter("id");
        if (mealId != null && !mealId.isEmpty()) {
            dao.deleteById(Long.parseLong(mealId));
        }
        req.setAttribute("mealToList", getTestListOfMealTo(dao.findAll()));
        req.getRequestDispatcher(MEALTO_LIST).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String forward = EDIT_MEAL;
        String mealId = req.getParameter("id");

        long notNullAndNotEmptyParam = req.getParameterMap().values().stream()
                .filter(params -> params[0] != null && !params[0].isEmpty())
                .count();

        if (notNullAndNotEmptyParam == 1 && mealId != null && !mealId.isEmpty()) {
            req.setAttribute("meal", dao.getOne(Long.parseLong(mealId)));
        } else if (notNullAndNotEmptyParam >= 3) {
            forward = MEALTO_LIST;
            if (mealId.isEmpty()) {
                dao.save(getMealByReqParam(req));
            } else {
                dao.update(getMealByReqParam(req));
            }

            req.setAttribute("mealToList", getTestListOfMealTo(dao.findAll()));
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        dao = new MealDaoImpl(SomeDB.getConnectToDB());
    }

    private Meal getMealByReqParam(HttpServletRequest req) {
        Meal newMeal;
        String idString = req.getParameter("id");
        try {
            newMeal = new Meal(
                    LocalDateTime.parse(req.getParameter("dateTime"), dateTimeFormatter)
                    , req.getParameter("description")
                    , Integer.parseInt(req.getParameter("calories"))
                    , (idString.isEmpty())? 0 : Long.parseLong(idString)
            );
        } catch (Exception e) {
            throw new RuntimeException("Not correct data param for new Meal", e);
        }
        return newMeal;
    }
}

