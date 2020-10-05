<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<c:set var="someMeal" value="${requestScope.meal}"/>
<form action="meals" method="post">
    <p>
    <table>
        <td><b>Date time:</b></td>
        <td><input type="datetime-local" name="dateTime" value="${someMeal.dateTime}"/></td>
        </tr>
        <tr>
            <td><b>Description:</b></td>
            <td><input type="tetext" name="description" value="${someMeal.description}"/></td>
        </tr>
        <tr>
            <td><b>Calories:</b></td>
            <td><input type="number" name="calories" value="${someMeal.calories}"/></td>
        </tr>
    </table>
    </p>
    <button type="submit" name="id" value=${someMeal.id}>Save</button>
    <input type="reset" value="Clear"/>
</form>
</body>
</html>

