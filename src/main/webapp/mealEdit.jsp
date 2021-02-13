
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    .normal{color: green}
    .exceed{color: orangered}
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2 align="center">Edit the Meal</h2>
<section>
    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.Meal"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Date & Time: </dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description: </dt>
            <dd><input type="text" value="${meal.description}" size="50" name="description"></dd>
        </dl>

        <dl>
            <dt>Calories: </dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>