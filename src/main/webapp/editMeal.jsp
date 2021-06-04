<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <title>Edit meal</title>
</head>
<body>
<ul id="menu">
    <li><h3><a href="index.html">Home</a></h3></li>
    <li><h3><a href="users">Users</a></h3></li>
    <li><h3><a href="meals">Meals</a></h3></li>
</ul>
<hr>
<h2>Edit meal</h2>
<c:set var="meal" value="${requestScope.meal}"/>
<form action="meals" method="post">
    <table id="table_beauty">
        <td><b>Date time:</b></td>
        <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></td>
        <tr>
            <td><b>Description:</b></td>
            <td><input type="text" name="description" value="${meal.description}"/></td>
        </tr>
        <tr>
            <td><b>Calories:</b></td>
            <td><input type="number" name="calories" value="${meal.calories}"/></td>
        </tr>
    </table>
    <input type="submit" value="Save" class="btn">
    <input type="hidden" name="id" value="${meal.id}">
    <input type="reset" value="Clear" class="btn"/>
</form>
</body>
</html>

