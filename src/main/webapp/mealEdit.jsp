<%@ taglib prefix="f" uri="functions.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Aspire
  Date: 07.12.2016
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h2>Meal Edit</h2>
<form method="post" action='meals' name="mealEdit">
    Meal ID: <input
        type="text" readonly name="mealId"
        value="<c:out value="${meal.id}" />"
    /><br />

    Description: <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"
    /><br />

    Calories: <input
        type="number" name="calories"
        value="<c:out value="${meal.calories}" />"
    /> <br />

    Exceed: <input
        type="checkbox" name="exceed"
        value="<c:out value="${meal.exceed}" />"
    /> <br />

    Date and Time: <input
        type="datetime-local" name="dateTime"
        value="<c:out value="${meal.dateTime}" />"
    /> <br />

    <input type="submit" value="Submit" />

</form>

</body>
</html>
