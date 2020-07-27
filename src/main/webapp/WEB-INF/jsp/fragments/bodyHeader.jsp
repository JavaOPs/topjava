<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>
<header>
    <a href="meals"><fmt:message key="app.title"/></a> | <a href="users"><fmt:message key="user.title"/></a> | <a href="${pageContext.request.contextPath}"><fmt:message key="app.home"/></a>
</header>