<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="localDateTimeFormatUtil" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="stylesheet/styles.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p>Add Meal</p>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="list" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${list}">
        <c:set var="excess" value="${meal.excess == true ? 'color:#FF0000' : 'color:#008000'}"/>
        <tr style=${excess}>
            <td><c:out value="${f:formatLocalDateTime(meal.localDateTime, 'yyyy-MM-dd hh:mm')}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td>Update</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
