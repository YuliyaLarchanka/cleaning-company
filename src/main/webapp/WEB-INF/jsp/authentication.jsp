<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="errorKey" value="${requestScope.errorKey}" />
<c:set var="successKey" value="${requestScope.successKey}" />


<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="local" var="loc" />
<fmt:message bundle="${loc}" key="local.sign.in" var="signIn" />
<fmt:message bundle="${loc}" key="local.profile.email" var="email" />
<fmt:message bundle="${loc}" key="local.profile.password" var="password" />
<fmt:message bundle="${loc}" key="local.create.account" var="createAccount" />

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<body>
<div class="container" style="margin-bottom: 100px">
    <c:if test="${not (errorKey eq null)}">
        <fmt:message bundle="${loc}" key="${errorKey}" var="error" />
        <div class="alert alert-danger alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <strong><c:out value="${error}"/></strong>
        </div>
    </c:if>
    <c:if test="${not (successKey eq null)}">
        <fmt:message bundle="${loc}" key="${successKey}" var="success" />
        <div class="alert alert-info alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <strong><c:out value="${success}"/></strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="login-form">
                <form method="post" action="/controller">
                    <h2 class="text-center" style="padding-block: 10px"><c:out value="${signIn}"/></h2>
                    <div class="form-group">
                        <label><c:out value="${email}"/></label>
                        <input type="email" class="form-control" name="email" required="required">
                    </div>
                    <div class="form-group">
                        <label><c:out value="${password}"/></label>
                        <input type="password" class="form-control" name="password" required="required">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block"><c:out value="${signIn}"/></button>
                    </div>
                    <input type="hidden" name="command_name" value="authenticate_user">
                </form>
                <p class="text-center"><a href="/registration"><c:out value="${createAccount}"/></a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
