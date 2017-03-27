<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>
        <c:choose>
            <c:when test="${action}"
        </c:choose>
        Редактирование еды</title>
</head>
<body>
<jsp:useBean id="item" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<%--<c:out value="${item.id}"/>--%>

</body>
</html>
