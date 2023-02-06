<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <title>Meal list</title>
</head>
<body>
    <section>
        <h2><a href="index.html">Home</a></h2>
        <h3>Meal list</h3>
        <hr>
        <table class="mealTable">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                </tr>
            </thead>
            <c:forEach items="${mealList}" var="meal">
                <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.MealTo" scope="page" />
                <tr class="${meal.excess.get()? 'excess' : 'normal'}">
                    <td>
                        <%=TimeUtil.toString(meal.getDateTime())%>
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
