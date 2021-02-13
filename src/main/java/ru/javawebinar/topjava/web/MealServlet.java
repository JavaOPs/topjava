package ru.javawebinar.topjava.web;

import jdk.incubator.jpackage.internal.Log;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal userMeal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("id")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));

        log.info(userMeal.isNew()?"Create {}":"Uodate {}", userMeal);
        repository.save(userMeal);
        resp.sendRedirect("meal");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action==null){
            log.info("get meals");
            request.setAttribute("meals", MealsUtil.getFiltered(new ArrayList(repository.getAll()),2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        else if(action.equals("delete")){
            Integer id = getId(request);
            log.info("delete {}", id);
            repository.delete(id);
            response.sendRedirect("/meals");
        } else {
            final Meal meal= action.equals("create")? new Meal(LocalDateTime.now(), "", 1000)
                    :repository.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        }
    }

    private Integer getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
