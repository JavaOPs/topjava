<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meals list</h2>


<table id="mealsTable">
    <tr>
        <th width="80">Date</th>
        <th width="120">Description</th>
        <th width="60">Calories</th>
    </tr>
    <c:forEach var="meal" items="${mealsList}">
        <c:if test="${meal.isExceed()==true}">
            <tr>
                <td style="color:#ff6c36">${meal.getFormatedDateTime()}</td>
                <td style="color:#ff6c36">${meal.getDescription()}</td>
                <td style="color:#ff6c36">${meal.getCalories()}</td>
            </tr>
        </c:if>
        <c:if test="${meal.isExceed()==false}">
            <tr>
                <td style="color:#3fff38">${meal.getFormatedDateTime()}</td>
                <td style="color:#3fff38">${meal.getDescription()}</td>
                <td style="color:#3fff38">${meal.getCalories()}</td>
            </tr>
        </c:if>
    </c:forEach>
</table>


</body>
</html>