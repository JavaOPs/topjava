<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 26.03.2017
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <jsp:useBean id="abc" scope="request" type="java.util.List"/>
    <c:forEach items="${abc}" var="meal">
        <c:out value="${meal.}"
    </c:forEach>
</body>
</html>
