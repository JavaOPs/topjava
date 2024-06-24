package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.dao.UserMealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_DATE_TIME = "dateTime";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_CALORIES = "calories";
    private static final String PARAMETER_ACTION = "action";
    private static final String REDIRECT_MEALS = "meals";

    private static final Logger log = getLogger(MealServlet.class);

    private UserMealDao userMealDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userMealDao = new UserMealDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(CHARACTER_ENCODING);

        String actionParam = request.getParameter(PARAMETER_ACTION);
        Action action = Action.fromString(actionParam);

        if (action == null) {
            log.warn("Unknown action: {}", actionParam);
            response.sendRedirect(REDIRECT_MEALS);
            return;
        }

        switch (action) {
            case CREATE:
            case UPDATE:
                createOrUpdateMeal(request, action);
                break;
            case DELETE:
                deleteMeal(request);
                break;
        }

        response.sendRedirect(REDIRECT_MEALS);
    }

    private Meal parseMeal(HttpServletRequest request) {
        String id = request.getParameter(PARAMETER_ID);
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter(PARAMETER_DATE_TIME));
        String description = request.getParameter(PARAMETER_DESCRIPTION);
        int calories = Integer.parseInt(request.getParameter(PARAMETER_CALORIES));

        return new Meal(id == null || id.isEmpty() ? null : Integer.valueOf(id),
                dateTime, description, calories);
    }

    private void createOrUpdateMeal(HttpServletRequest request, Action action) {
        Meal meal = parseMeal(request);
        log.info(action == Action.CREATE ? "Create {}" : "Update {}", meal);
        if (action == Action.CREATE) {
            userMealDao.create(meal);
        } else {
            userMealDao.update(meal);
        }
    }

    private void deleteMeal(HttpServletRequest request) {
        int id = getId(request);
        log.info("Delete {}", id);
        userMealDao.deleteById(id);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionParam = request.getParameter(PARAMETER_ACTION);
        Action action = Action.fromString(actionParam);

        if (action == null) {
            action = Action.LIST;
        }

        switch (action) {
            case CREATE:
                Meal meal = new Meal(LocalDateTime.now(), "Введите описание еды", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            case UPDATE:
                final Meal updateMeal = userMealDao.getById(getId(request));
                if (updateMeal == null) {
                    log.warn("Meal with id {} not found", getId(request));
                    response.sendRedirect(REDIRECT_MEALS);
                } else {
                    request.setAttribute("meal", updateMeal);
                    request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                }
                break;
            default:
                log.info("getAllMeals");
                List<MealTo> mealWithExcess = MealsUtil.getMealWithExcess(userMealDao.getAll(), MealsUtil.CALORIES_PER_DAY);
                request.setAttribute("mealList", mealWithExcess);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter(PARAMETER_ID));
        return Integer.parseInt(paramId);
    }

}

enum Action {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    LIST("list");

    private final String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static Action fromString(String action) {
        for (Action a : Action.values()) {
            if (a.getAction().equalsIgnoreCase(action)) {
                return a;
            }
        }
        return null;
    }
}