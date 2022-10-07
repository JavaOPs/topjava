package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "MealServlet", value = "/MealServlet")
public class MealServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // PrintWriter pw = response.getWriter();
        List<MealTo> mealsForTables = MealsUtil.filteredByStreams(MealsUtil.meals, LocalTime.of(0, 0),
                LocalTime.of(23, 59), MealsUtil.CALORIES_PER_DAY);

        request.setAttribute("mealsForTables", mealsForTables);


        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);

//        RequestDispatcher dispatcher = request.getRequestDispatcher("/meals.jsp");
//        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
