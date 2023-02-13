<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
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
        <a href="meals?action=create">Add meal</a>
        <br><br>
        <table class="mealTable">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" class="ru.javawebinar.topjava.to.MealTo" scope="page" />
                <tr class="${meal.excess.get()? 'excess' : 'normal'}">
                    <td>
                        <%=DateTimeUtil.toString(meal.getDateTime())%>
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
