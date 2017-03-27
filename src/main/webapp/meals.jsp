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

    <title>Meals</title>
</head>
<body>

    <table border="1">
        <tr>
            <th>Description</th>
            <th>Time</th>
            <th>Calories</th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
        <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed == true ? 'exceed' : 'notExceed'}">
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.calories}"/></td>
        </tr>
        </c:forEach>
    </table>

</body>
</html>
