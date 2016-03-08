<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="HH" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th>ID</th>
        <th>Date</th>
        <th>Description</th>
        <th>calories</th>
        <th>Exceed</th>
        <th>Actions</th>
        <th colspan="3"></th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
    <c:forEach items="${mealList}" var="meal">
    <c:if test="${meal.exceed}">
        <tr style="background:#ff16ed">
            <c:url var="Update" value="/meal/update?id=${meal.id}"/>
            <c:url var="Delete" value="/meal/delete?id=${meal.id}"/>
            <td><c:out value="${meal.id}"/></td>
            <td><c:out value="${meal.formattedDateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.exceed}"/></td>
            <td><a href="${Update}">Update/ </a>
                <a href="${Delete}">Delete</a></td>
        </tr>
    </c:if>
    <c:if test="${!meal.exceed}">
        <tr style="background:#40fa4e">
            <c:url var="Update" value="/meal/update?id=${meal.id}"/>
            <c:url var="Delete" value="/meal/delete?id=${meal.id}"/>
            <td><c:out value="${meal.id}"/></td>
            <td><c:out value="${meal.formattedDateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.exceed}"/></td>
            <td><a href="${Update}">Update/ </a>
                <a href="${Delete}">Delete</a></td>
        </tr>
    </c:if>
    </c:forEach>
    </tbody>
</table>
<c:url var="addUrl" value="/meal/add"/>
<a href="${addUrl}">Add</a>
</body>
</html>
