<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0px;
            padding: 0px;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2><c:choose>
    <c:when test="${param.action == 'create'}">Create meal</c:when>
    <c:otherwise>Edit meal</c:otherwise>
</c:choose></h2></h2>
<hr>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request" />
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <input type="hidden" name="action" value="${param.action}">
    <dl>
        <dt>DataTime:</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" value="${meal.description}" size="40px" name="description"></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" value="${meal.calories}" name="calories"></dd>
    </dl>
    <button type="submit">Save</button>
    <button type="button" id="cancelButton">Cancel</button>

    <script>
        document.getElementById('cancelButton').addEventListener('click', function(event) {
            event.preventDefault();
            window.location.href = 'meals';
        });
    </script>
</form>
</body>
</html>