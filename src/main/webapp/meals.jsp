<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://ru.javawebinar.topjava/functions" prefix="f" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<table width="100%" border="1" align="center">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach items="${mealsForTables}" var="meal">
        <tr>
            <c:if test="${meal.excess == false}">
                <td><span style="color: #008000"><c:out
                        value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}"/></span></td>
                <td><span style="color: #008000"><c:out value="${meal.description}"/></span></td>
                <td><span style="color: #008000"><c:out value="${meal.calories}"/></span></td>
            </c:if>
            <c:if test="${meal.excess == true}">
                <td><span style="color: #FF0000"><c:out
                        value="${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}"/></span></td>
                <td><span style="color: #FF0000"><c:out value="${meal.description}"/></span></td>
                <td><span style="color: #FF0000"><c:out value="${meal.calories}"/></span></td>
            </c:if>
        </tr>
    </c:forEach>

</table>

</body>
</html>
