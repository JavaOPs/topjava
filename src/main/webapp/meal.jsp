<%--
  Created by IntelliJ IDEA.
  User: ilyakyazimov
  Date: 26.11.2021
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<c:set var="action" value="${requestScope.action}"/>
<c:set var="meal" value="${requestScope.meal}"/>
<c:if test="${action=='edit'}">
    <h2>Edit meal</h2>
    </c:if>
    <c:if test="${action=='insert'}">
        <h2>Add meal</h2>
    </c:if>

<form method="POST" action="meals">
    <input type="hidden" name="mealId" value="${meal.id}">
    <dl><dt>DateTime:</dt><dd><input aria-label="dateTime" type="datetime-local" name="dateTime" value="${meal.dateTime}"/></dd></dl>
    <dl><dt>Description:</dt><dd><input aria-label="description" type="text" name="description" value="${meal.description}"/></dd></dl>
    <dl><dt>Calories:</dt><dd><input aria-label="calories" type="text" name="calories" value="${meal.calories}"/></dd></dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
