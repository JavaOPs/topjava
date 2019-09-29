<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата:</th>
        <th>Описание:</th>
        <th>Калории:</th>
    </tr>
<c:forEach var="meals" items="${requestScope.meals}">
    <c:if test="${meals.excess == true}"> <c:set var="color" value="red"/></c:if>
    <c:if test="${meals.excess == false}"> <c:set var="color" value="green"/></c:if>
    <tr style="color: ${color}">
        <th><c:out value="${meals.date} ${meals.time}"/></th>
        <th><c:out value="${meals.description}"/></th>
        <th><c:out value="${meals.calories}"/></th>
    </tr>
</c:forEach>
</table>
</body>
</html>
