<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <form method="post" action="users">
        <fmt:message key="app.login"/>: <select name="userId">
        <option value="100000" selected>User</option>
        <option value="100001">Admin</option>
    </select>
        <button type="submit"><fmt:message key="common.select"/></button>
    </form>
    <ul>
        <li><a href="users"><fmt:message key="user.title"/></a></li>
        <li><a href="meals"><fmt:message key="meal.title"/></a></li>
    </ul>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>