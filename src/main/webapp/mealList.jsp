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
        <td>Action</td>
    </tr>
    </thead>

    <c:forEach items="${mealList}" var="meal">

     <%--   <c:otherwise><tr></c:otherwise>&ndash;%&gt;--%>
        <tr <c:if test="${meal.isExceed}">class = "red" </c:if> <c:if test="${!meal.isExceed}">class = "green" </c:if>>
            <td>${meal.id}</td>
            <td>${meal.dateTime.format(formatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form method="get">

                    <a href="<c:url value ="/edit?id=${user.id}"/>">Редактировать</a> <br>
                </form>


                <a href="<c:url value="/delete?id=${user.id}"/>">Удалить пользователя</a></td>
        </tr>


    </c:forEach>

</table>

</body>
</html>
