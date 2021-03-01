package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        //А здесь слэш не влияет от хоста или нет. Странно?
        //При форварде мы остаемся на сервере
        //request.getRequestDispatcher("/users.jsp").forward(request, response);

        //Слешь перед /users.jsp говорит нам что маршрутизация идет от хоста т.е. от localhost, а не от localhost/topjava
        //Отправляется ответ браузеру что нужно пойти по другому пути.
        response.sendRedirect("users.jsp");
    }
}
