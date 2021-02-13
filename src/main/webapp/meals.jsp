
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
<h2 align="center">Meals</h2>
<section>
    <a href="meals?action=create">Add meal</a>
    <table border="5" width="900" align="center" bordercolor="black">
        <tr>
            <th width="300">Date</th>
            <th width="300">Desc</th>
            <th width="300">Calories</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess?"exceed":"normal"}">
            <td><%= TimeUtil.toString(meal.getDateTime()) %></td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update meal</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete meal</a></td>
            </tr>

        </c:forEach>
    </table>
</section>
</body>
</html>