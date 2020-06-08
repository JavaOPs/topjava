<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <title>Meals</title>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <th>Date and Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Excess</th>
        <th>Actions</th>
    </tr>
    </thead>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.isExcess() ? "red" : "green"}">
            <td>${dateTimeFormatter.format(meal.getDateTime())}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td>${meal.isExcess()}</td>
            <td>
                <a href="meals?action=edit&id=${meal.getId()}">Edit</a>
                <a href="meals?action=delete&id=${meal.getId()}">Remove</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="meals?action=create">Create new meal</a>
</body>
</html>
