<%--
  Created by IntelliJ IDEA.
  User: Win10Pro
  Date: 07.10.2019
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border="3">

        <c:forEach var="meals" items="${meals}">
            <tr style="background-color: ${meals.value.excess ? 'yellow':'white'}">
            <td> ${meals.value.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))} </td>
            <td> ${meals.value.description} </td>
            <td> ${meals.value.calories}</td>

            </tr>
        </c:forEach>

    <form method="post">
        <label>DateTime:
            <input type="datetime-local" name="dateTime"><br />
        </label>

        <label>Description:
            <input type="text" name="description"><br />
        </label>

        <label>Calories:
            <input type="text" name="calories"><br />
        </label>
        <button type="submit">Submit</button>
    </form>

</table>
</body>
</html>
