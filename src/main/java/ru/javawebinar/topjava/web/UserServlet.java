package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Aspire on 03.12.2016.
 */
public class UserServlet extends javax.servlet.http.HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);
    
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        LOG.debug("redirect to userList");
        response.sendRedirect("userList.jsp");
        //        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }
}
