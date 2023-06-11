<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="localDateTimeFormatUtil" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="stylesheet/styles.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form action="meals" method="POST">
    <c:set var="meal" value="${requestScope.meal}"/>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.MealTo"/>
    <input type="hidden" name="id" value="${meal.id}"/>
    <p>
        <label>Date Time:</label>
        <input type="datetime-local" name="dateTime" value="${f:formatLocalDateTime(meal.localDateTime, 'yyyy-MM-dd hh:mm')}">
    </p>
    <br/>
    <p>
        <label>Description:</label>
        <input type="text" name="description" value="${meal.description}">
    </p>
    <br/>
    <p>
        <label>Calories:</label>
        <input type="text" name="calories" value="${meal.calories}">
    </p>
    <br/>
    <p>
        <input type="submit" value="Save"/>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </p>
</form>
</body>
</html>