<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<a href="index.html">Home</a>
<table border="1">
    <th>Дата/Время</th>
    <th>Описание</th>
    <th>Калории</th>
<c:forEach var="m" items="${meals}">
    <tr style="color:${m.excess ? 'green' : 'red'}"/>
       <td ><javatime:format value="${m.dateTime}" pattern="yyyy-MM-dd HH:mm" locale="US" style="MS"/></td>
        <td>${m.description}</td>
        <td>${m.calories}</td>

    </tr>
</c:forEach>
</table>
</body>
</html>
