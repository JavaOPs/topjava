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
            <td>Date and Time</td>
            <td>Description</td>
            <td>Calories</td>
            <td>Excess</td>
        </tr>
    </thead>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach var="meal" items="${meals}">
        <tr style="color: ${meal.isExcess() ? "green" : "red"}">
            <td>${dateTimeFormatter.format(meal.getDateTime())}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td>${meal.isExcess()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
