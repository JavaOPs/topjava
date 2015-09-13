<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MEAL LIST</title>
    <style>
        .green{color: green}
        .red{color: red}
    </style>
    <h2><a href="index.html">HOME PAGE</a></h2>
    <table border="1px" cellpadding="10px">
       <tr>
           <thead align="center"><h3>List of Meals</h3></thead>
       </tr>
        <tr>
            <th>ID</th>
            <th>DATE</th>
            <th>TIME</th>
            <th>DESCRIPTION</th>
            <th>CALORIES</th>
            <th colspan="3">EDIT</th>
        </tr>
        <c:forEach items="${fMeals}" var="Meal" >

            <tr class="${Meal.exceed ? 'red' : 'green'}">
                <td>${Meal.id}</td>
                <td>${Meal.date}</td>
                <td>${Meal.time}</td>
                <td>${Meal.description}</td>
                <td>${Meal.calories}</td>
                <td><a href="add">Add</a> </td>
                <td><a href="edit/${Meal.id}">Edit</a> </td>
                <td><a href="meals?id=${Meal.id}">Delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</head>
<body>

</body>
</html>
