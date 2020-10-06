package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DaoInterface;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private DaoInterface<Meal, Long> dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String forward = "/meals.jsp";
        String action = (req.getParameter("action") == null) ? "def" : req.getParameter("action");

        switch (action) {
            case ("delete"):
                log.info("doGet delete");
                dao.deleteById(Long.parseLong(req.getParameter("id")));
                resp.sendRedirect("meals");
                break;
            case ("update"):
                log.info("doGet update");
                req.setAttribute("meal", dao.getOne(Long.parseLong(req.getParameter("id"))));
            case ("add"):
                log.info("doGet add");
                forward = "/editMeal.jsp";
            default:
                log.info("doGet default");
                req.setAttribute("mealToList", filteredByStreams(dao.findAll(), TIME_MIN, TIME_MAX, CALORIES_TEST));
                req.getRequestDispatcher(forward).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idString = req.getParameter("id");

        if (idString != null && !idString.isEmpty()) {
            log.info("doPost update");
            dao.update(new Meal(
                    Long.parseLong(req.getParameter("id")),
                    LocalDateTime.parse(req.getParameter("dateTime"), DATE_TIME_FORMATTER),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            ));
        } else {
            log.info("doPost save");
            dao.save(new Meal(
                    LocalDateTime.parse(req.getParameter("dateTime"), DATE_TIME_FORMATTER),
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories"))
            ));
        }
        resp.sendRedirect("meals");
    }

    @Override
    public void init() throws ServletException {
        dao = new MealDaoInMemoryImpl();
    }
}

