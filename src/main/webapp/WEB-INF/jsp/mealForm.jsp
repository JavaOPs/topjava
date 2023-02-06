<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css">
    <title>Meal</title>
</head>
<body>
    <section>
        <h2><a href="index.html">Home</a></h2>
        <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
        <hr>
        <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <form method="post" action="meals" class="edit">
            <input type="hidden" name="id" value="${meal.id}">
            <dl>
                <dt>DateTime</dt>
                <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
            </dl>
            <dl>
                <dt>Description</dt>
                <dd><input type="text" value="${meal.description}" size="40" name="description" required></dd>
            </dl>
            <dl>
                <dt>Calories</dt>
                <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
            </dl>
            <button type="submit">Save</button>
            <button onclick="window.history.back()" type="button">Cancel</button>
        </form>
    </section>
</body>
</html>
