<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: anikiforova
  Date: 27.03.2017
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="doAction" scope="request" type="java.lang.String"/>
    <title>
        <c:choose>
            <c:when test="${doAction.equals('add')}">
                Добавление еды
            </c:when>
            <c:otherwise>
                Редактирование еды
            </c:otherwise>
        </c:choose>
    </title>
</head>
<body>

<h1>
    <c:choose>
        <c:when test="${doAction.equals('add')}">
            Добавление еды
        </c:when>
        <c:otherwise>
            Редактирование еды
        </c:otherwise>
    </c:choose>
</h1>

<c:choose>
    <c:when test="${doAction.equals('edit')}">
        <jsp:useBean id="item" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
        <form method="POST" action='meals' name="edit" >
            id :
            <input type='text' title='id' readonly="readonly" name='id' value="${item.id}"/> <br><br>
            Дата :
            <input type='date' title='date' name='date' value="${item.dateTime.toLocalDate()}"/> <br><br>
            Время :
            <input type='time' title='date' name='time' value="${item.dateTime.toLocalTime()}"/> <br><br>
            Описание :
            <input type="text" title='description' name="description" value="<c:out value="${item.description}" />"> <br><br>
            Калории :
            <input type="text" title='calories' name="calories" value="<c:out value="${item.calories}" />"> <br><br>
            <input type='submit' name='submit' property="edit"/>
        </form>
    </c:when>
    <c:otherwise>
        <form method="POST" action='meals' name="add" >
            Дата :
            <input type='date' title='date' name='date'/> <br><br>
            Время :
            <input type='time' title='date' name='time'/> <br><br>
            Описание :
            <input type="text" title='description' name="description"> <br><br>
            Калории :
            <input type="text" title='calories' name="calories"> <br><br>
            <input type='submit' name='submit'/>
        </form>
    </c:otherwise>

</c:choose>


</body>
</html>
