<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<%--    <jsp:param name="selected" value="home" />--%>
<%--</jsp:include>--%>

<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.home.title.large" var="largeTitle" />
<fmt:message bundle="${loc}" key="local.home.title.small" var="smallTitle" />
<fmt:message bundle="${loc}" key="local.home.page.room.cleaning.description" var="room" />

<body>

<div class="has-bg-img" style="height: 722px">
    <div class="container">
        <c:if test="${not (errorKey eq null)}">
            <fmt:message bundle="${loc}" key="${errorKey}" var="error"/>
            <div class="alert alert-danger alert-dismissable">
                <a class="panel-close close" data-dismiss="alert">×</a>
                <i class="fa fa-coffee"></i>
                <strong><c:out value="${error}"/></strong>
            </div>
        </c:if>
        <c:if test="${not (successKey eq null)}">
            <fmt:message bundle="${loc}" key="${successKey}" var="success"/>
            <div class="alert alert-info alert-dismissable">
                <a class="panel-close close" data-dismiss="alert">×</a>
                <i class="fa fa-coffee"></i>
                <strong><c:out value="${success}"/></strong>
            </div>
        </c:if>
        <div style="height: 80px"></div>
        <h1 class="text-center">${largeTitle}</h1>
        <h4 class="text-center">${smallTitle}</h4>
        <div style="height: 500px"></div>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>
