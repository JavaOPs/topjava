<%--
  Created by IntelliJ IDEA.
  User: Vladimir_Sentso
  Date: 07.03.2016
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="HH" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add</title>
</head>
<body>
<h2>Add userMeal</h2>
<c:url var="addMeal" value="/meal/add"/>
<form method="post" action="${addMeal}">
    Meal DateTime:
    <input type="text" name="dateTime" value="<c:out value="${dateTime}"/> "><br/>
    Meal calories:
    <input type="text" name="calories" value="<c:out value="${calories}"/> "><br/>
    Meal description:
    <input type="text" name="description" value="<c:out value="${description}"/> "><br/>
    <input type="submit" value="Add"/>
</form>
</body>
</html>
