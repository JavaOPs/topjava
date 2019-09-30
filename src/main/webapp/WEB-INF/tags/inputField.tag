<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="name" required="true" description="Name of corresponding property in bean object" %>
<%@ attribute name="labelCode" required="true" description="Field label" %>
<%@ attribute name="inputType" required="false" description="Input type" %>

<spring:bind path="${name}">
    <div class="form-group ${status.error ? 'error' : '' }">
        <label class="col-form-label"><spring:message code="${labelCode}"/></label>
        <c:choose>
            <c:when test="${inputType == 'password'}"><form:password path="${name}" class="form-control is-invalid"/></c:when>
            <c:when test="${inputType == 'number'}"><form:input path="${name}" type="number" class="form-control is-invalid"/></c:when>
            <c:otherwise><form:input path="${name}" class="form-control is-invalid"/></c:otherwise>
        </c:choose>
        <div class="invalid-feedback">${status.errorMessage}</div>
    </div>
</spring:bind>