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
<form action="meals" method="post">
    <button type="submit">Add meal</button>
</form>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Action</th>
    </tr>
    <c:forEach items="#{requestScope.mealToList}" var="mealTo">
        <c:set var="excess" value="${mealTo.excess == false ? 'coloredGreen': 'coloredRed'}"/>
        <tr class=${excess}>
            <javatime:format value="${mealTo.dateTime}" style="MS" var="parsedDateTime"/>
            <td> ${parsedDateTime}</td>
            <td> ${mealTo.description}</td>
            <td> ${mealTo.calories}</td>
            <td>
                <form action="meals" method="post">
                    <button type="submit" name="id" value=${mealTo.id}>Update</button>
                </form>
            </td>
            <td>
                <form action="meals" method="get">
                    <button type="submit" name="id" value=${mealTo.id}>Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>

