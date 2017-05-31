<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 26.03.2017
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        table {
            border-collapse: collapse;
        }
        td, th {
            padding: 8px;
        }
        tr.exceed {
            color: red;
        }
        tr.notExceed {
            color: green;
        }
    </style>

    <title>Моя еда</title>
</head>
<body>

    <table border="1">
        <tr>
            <th>id</th>
            <th>Описание</th>
            <th>Время</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
        <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? 'exceed' : 'notExceed'}">
            <td><c:out value="${meal.id}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&userId=<c:out value="${meal.id}"/>">Редактировать</a></td>
            <td><a href="meals?action=delete&userId=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
        </c:forEach>
    </table>

    <br>
    <br>
    <a href="meals?action=add">Добавить</a>

</body>
</html>
