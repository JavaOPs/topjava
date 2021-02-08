<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<ul>
    <table border="1" cellpadding="6" cellspacing="2">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
        <c:forEach var="mt" items="${mealsTo}">
            <tr style="color: ${mt.excess ? 'red' : 'green'}">
                <td>
                        ${mt.dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))}
                </td>
                <td>${mt.description}</td>
                <td>${mt.calories}</td>
            </tr>
        </c:forEach>
    </table>
</ul>
<div>
    <button onclick="location.href='/topjava'">Back to main</button>
</div>
</body>
</html>