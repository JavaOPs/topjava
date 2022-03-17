<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <title><spring:message code="meal.title"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><a href="${pageContext.request.contextPath}"><spring:message code="app.home"/></a></h3>
    <hr>
    <c:choose>
        <c:when test="${action=='create'}">
            <h2><spring:message code="meal.create"/></h2>
            <br />
        </c:when>
        <c:otherwise>
            <h2><spring:message code="meal.update"/></h2>
            <br />
        </c:otherwise>
    </c:choose>

    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="app.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="app.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
