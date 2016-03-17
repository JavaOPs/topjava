package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static ru.javawebinar.topjava.LoggedUser.LOGGED_USER;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    ConfigurableApplicationContext appCtx;
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(UserMealRestController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        switch (action == null ? "" : action) {
            case "":
                String id = request.getParameter("id");
                UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id), null,
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));
                LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
                if (userMeal.isNew())
                    controller.create(userMeal);
                else {
                    HttpSession session = request.getSession();
                    userMeal.setUserID(LOGGED_USER.id(session.getId()));
                    controller.update(userMeal);
                }
                break;
            case "filter":

        }

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserMeal meal;
        if (LOGGED_USER.id(request.getSession().getId()) != null)
        switch (action != null ? action : "") {
            case "":
                LOG.info("getAll");
                request.setAttribute("mealList", controller.getAllExceedMeal());
                request.getRequestDispatcher("/mealList.jsp").forward(request, response);
                break;
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                meal = new UserMeal(LocalDateTime.now(), "", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                break;
            case "update":
                meal = controller.get(getId(request));
                LOG.info("Update meal {} , user ID {}", meal.getId(), meal.getUserID());
                request.setAttribute("meal", meal);
                HttpSession session = request.getSession();
                session.setAttribute("userID", meal.getUserID());
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                break;
            default:
                throw new ServletException(new IllegalAccessException("Not allow parameter"));
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
