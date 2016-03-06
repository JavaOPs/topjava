<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vladimir_Sentso
  Date: 06.03.2016
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>mealListWithExceed</title>
</head>
<body>
<h2>Meal</h2>

<table style="border: 1px solid; width: 500px; text-align:center">
    <thead style="background:#4698ff">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>calories</th>
        <th>Exceed</th>
        <th colspan="3"></th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
    <c:forEach items="${mealList}" var="meal">
    <c:if test="${meal.exceed}">
        <tr style="background:#45b768">
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.exceed}"/></td>
        </tr>
    </c:if>
    <c:if test="${!meal.exceed}">
        <tr style="background:#fa1ce5">
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.exceed}"/></td>
        </tr>
    </c:if>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
