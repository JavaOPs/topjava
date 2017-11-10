<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://topjava.webinar.ru/model/functions" prefix="f" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><<a href="index.html">home</a></h3>
<h2>Meals</h2>
<table cellpadding="10px">

    <tr bgcolor="#dcdcdc">
        <th>Дата/время</th>
        <th>Описание</th>
        <th>Калолрии</th>
    </tr>
    <c:forEach var="mealsL" items="${mealsList}">
        <c:if test="${mealsL.exceed == true}">
            <tr bgcolor="#ff4500" align="center">
                <td>${f:formatLocalDateTime(mealsL.dateTime, 'yyyy-dd-MM HH:mm')}</td>
                <td>${mealsL.description}</td>
                <td>${mealsL.calories}</td>
            </tr>
        </c:if>

        <c:if test="${mealsL.exceed == false}">
            <tr bgcolor="#7fff00" align="center">


                <td>${f:formatLocalDateTime(mealsL.dateTime, 'yyyy-dd-MM HH:mm')}</td>
                <td>${mealsL.description}</td>
                <td>${mealsL.calories}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
