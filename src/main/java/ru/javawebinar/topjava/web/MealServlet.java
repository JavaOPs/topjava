package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import sun.rmi.runtime.Log;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private UserMealRepository userMealRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userMealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));
        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        userMealRepository.save(meal);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        LOG.info("action: " + action);
        if (action == null){
            LOG.info("getAll");
            LOG.info(userMealRepository.getAll().toString());
            req.setAttribute("mealList",
                    MealsUtil.getWithExceeded(userMealRepository.getAll(),2000));
            req.getRequestDispatcher("/meals.jsp").forward(req,resp);
        } else if (action.equals("delete")){
            int id = getId(req);
            LOG.info("delete {}", id);
            userMealRepository.delete(id);
            resp.sendRedirect("meals");
        } else {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now(), "", 1000) :
                    userMealRepository.get(getId(req));
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("mealEdit.jsp").forward(req,resp);
        }
    }

    private int getId(HttpServletRequest request){
        //LOG.info("delete {}", id);
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        //LOG.info("delete {}", id);
        return Integer.parseInt(paramId);
    }

}
