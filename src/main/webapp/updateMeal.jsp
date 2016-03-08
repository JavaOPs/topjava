<%--
  Created by IntelliJ IDEA.
  User: Vladimir_Sentso
  Date: 08.03.2016
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="HH" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>UpdateMeal</title>
</head>
<body>
<h2>Update meal</h2>
<c:url var="updateMeal" value="/meal/update"/>
<form method="post" action="${updateMeal}">
    Meal id:
    <label>
        <input type="text" name="id" readonly="readonly" value="<c:out value="${id}"/> ">
    </label><br/>
    Meal DateTime:
    <label>
        <input type="text" name="dateTime" value="<c:out value="${dateTime}"/> ">
    </label><br/>
    Meal calories:
    <label>
        <input type="text" name="calories" value="<c:out value="${calories}"/> ">
    </label><br/>
    Meal description:
    <label>
        <input type="text" name="description" value="<c:out value="${description}"/> ">
    </label><br/>
    <input type="submit" value="Update"/>
</form>
</body>
</html>

