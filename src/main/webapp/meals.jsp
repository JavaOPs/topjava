<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="editMeal.html">
    <p>Add Meal</p>
</a>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:forEach items="#{requestScope.dbResponse}" var="mealTo">
        <c:set var="excess" value="${mealTo.excess == false ? 'colortextGreen': 'colortextRed'}"/>
        <tr class = ${excess} >
            <javatime:format value="${mealTo.dateTime}" style="MS" var="parsedDateTime"/>
            <td> ${parsedDateTime}</td>
            <td> ${mealTo.description}</td>
            <td> ${mealTo.calories}</td>
            <td><a href="updateMeal.html">Update</a></td>
            <td><a href="deleteMeal.html">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

