<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="name" required="true" description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" description="Field label" %>
<%@ attribute name="inputType" required="false" description="Input type" %>

<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'error' : '' }"/>
    <div class="${cssGroup}">
        <label class="control-label col-xs-2">${label}</label>

        <div class="col-xs-8">
            <c:choose>
                <c:when test="${inputType == 'password'}"><form:password path="${name}"/></c:when>
                <c:when test="${inputType == 'number'}"><form:input path="${name}" type="number"/></c:when>
                <c:otherwise><form:input path="${name}"/></c:otherwise>
            </c:choose>
            &nbsp;<span class="help-inline">${status.errorMessage}</span>
        </div>
    </div>
</spring:bind>