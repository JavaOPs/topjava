<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .normal {color: green;}
        .excess {color: red;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>
<table border="1px" cellpadding="10" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealList}" var="meal">
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" />
                <fmt:formatDate value="${parseDateTime}" pattern="yyyy-MM-dd HH:mm" />
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a> </td>
            <td>
                <a href="#" onclick="deleteMeal(${meal.id}); return false;">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form id="deleteForm" method="post" action="meals">
    <input type="hidden" name="action" value="delete"/>
    <input type="hidden" name="id" id="deleteId"/>
</form>

<script>
    function deleteMeal(id) {
            document.getElementById('deleteId').value = id;
            document.getElementById('deleteForm').submit();
    }
</script>

</body>
</html>