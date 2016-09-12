<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
    <link href="css/mealList.css" rel="stylesheet">
</head>
<body>
<h2><a href="index.html">Home</a></h2>


<table>
    <thead>
    <tr>
        <td>Id</td>
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    </thead>

    <c:forEach items="${mealList}" var="meal">

        <tr
                <c:if test="${meal.isExceed}">class="red" </c:if> >
            <td>${meal.id}</td>
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form action="meals" method="get">
                    <input type="submit" value="Править" class="editButton">

                </form>

            </td>
            <td>
                <form method="get">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${meal.id}">
                    <input type="submit" value="Удалить" class="deleteButton">

                </form>

            </td>
        </tr>


    </c:forEach>

</table>

</body>
</html>
