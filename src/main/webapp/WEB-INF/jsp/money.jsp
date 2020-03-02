<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.money.title" var="title"/>
<fmt:message bundle="${loc}" key="local.money.button" var="button"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</head>
<body>
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
    <form class="form-horizontal" role="form" method="post" action="/controller">
        <div class="col-md-8" style="margin-bottom: 700px; margin-top: 20px">
            <h2><c:out value="${title}"/></h2>
            <input class="form-control" autocomplete="off" min="1" max="5000" step="0.1" type="number" name="money" required/>
            <button type="submit" class="btn" style="margin-top: 20px"><c:out value="${button}"/></button>
            <input type="hidden" name="command_name" value="add_money">
        </div>
    </form>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>
