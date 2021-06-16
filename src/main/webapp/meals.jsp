<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <div>
        <div>
            <form method="get" action="meals">
                <div>
                    <div>
                        <label>From date (including)</label>
                        <input name="startDate" type="date" autocomplete="off">
                    </div>
                    <div>
                        <label>Up to date (including)</label>
                        <input name="endDate" type="date" autocomplete="off">
                    </div>
                    <div>
                        <label>From time to time (including)</label>
                        <input name="startTime" type="time" autocomplete="off">
                    </div>
                    <div>
                        <label>Until the time (excluding)</label>
                        <input name="endTime" type="time" autocomplete="off">
                    </div>
                </div>
                <input name="action" value="filter" type="hidden">
                <button onclick="window.history.back()" type="button">Cancel</button>
                <button type="submit">Filter</button>
            </form>
        </div>
    </div>
    <table border="1" cellpadding="8" cellspacing="0">
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
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
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