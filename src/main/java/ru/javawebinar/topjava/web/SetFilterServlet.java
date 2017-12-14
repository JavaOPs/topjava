package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static ru.javawebinar.topjava.AuthorizedUser.getFilter;

public class SetFilterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        getFilter().setBeginTime(req.getParameter("beginTime") == null || req.getParameter("beginTime").equals("") ?
                null : LocalTime.parse(req.getParameter("beginTime")));
        getFilter().setEndTime(req.getParameter("endTime") == null || req.getParameter("endTime").equals("") ?
                null : LocalTime.parse(req.getParameter("endTime")));
        getFilter().setBeginDate(req.getParameter("beginDate") == null || req.getParameter("beginDate").equals("") ?
                null : LocalDate.parse(req.getParameter("beginDate")));
        getFilter().setEndDate(req.getParameter("endDate") == null || req.getParameter("endDate").equals("") ?
                null : LocalDate.parse(req.getParameter("endDate")));
        resp.sendRedirect(req.getContextPath() + "/meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getFilter().setBeginTime(null);
        getFilter().setEndTime(null);
        getFilter().setBeginDate(null);
        getFilter().setEndDate(null);

        resp.sendRedirect(req.getContextPath() + "/meals");
    }
}
