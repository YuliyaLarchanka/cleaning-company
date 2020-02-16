<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="errorKey" value="${requestScope.errorKey}" />
<c:set var="successKey" value="${requestScope.successKey}" />

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.registration.title" var="title"/>
<fmt:message bundle="${loc}" key="local.profile.name" var="name"/>
<fmt:message bundle="${loc}" key="local.profile.surname" var="surname"/>
<fmt:message bundle="${loc}" key="local.profile.email" var="email"/>
<fmt:message bundle="${loc}" key="local.profile.password" var="password"/>
<fmt:message bundle="${loc}" key="local.profile.password.confirm" var="passwordConfirm"/>
<fmt:message bundle="${loc}" key="local.registration.button" var="registerNow"/>
<fmt:message bundle="${loc}" key="local.registration.have.account" var="haveAccount"/>
<fmt:message bundle="${loc}" key="local.sign.in" var="signIn"/>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<body>
<div class="container" style="margin-bottom: 160px">
    <c:if test="${not (errorKey eq null)}">
        <fmt:message bundle="${loc}" key="${errorKey}" var="error" />
        <div class="alert alert-danger alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <strong><c:out value="${error}" /></strong>
        </div>
    </c:if>
    <c:if test="${not (successKey eq null)}">
        <fmt:message bundle="${loc}" key="${successKey}" var="success" />
        <div class="alert alert-info alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <strong><c:out value="${success}" /></strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <form method="post" action="/controller">
                <h2><c:out value="${title}"/></h2>
                <div class="form-group">
                    <label><c:out value="${name}"/></label>
                    <input type="text" class="form-control" minlength="2" maxlength="10" name="first_name" required="required">
                </div>
                <div class="form-group">
                    <label><c:out value="${surname}"/></label>
                    <input type="text" class="form-control" minlength="2" maxlength="10" name="last_name" required="required">
                </div>

                <div class="form-group">
                    <label><c:out value="${email}"/></label>
                    <input type="email" class="form-control" name="email" required="required">
                </div>
                <div class="form-group">
                    <label><c:out value="${password}"/></label>
                    <input type="password" class="form-control" name="password" required="required">
                </div>
                <div class="form-group">
                    <label><c:out value="${passwordConfirm}"/></label>
                    <input type="password" class="form-control" name="confirm_password" required="required">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-success btn-lg btn-block"><c:out value="${registerNow}"/></button>
                </div>
                <input type="hidden" name="command_name" value="register_user">
            </form>
            <div class="text-center">${haveAccount} <a href="${pageContext.request.contextPath}/authentication"><c:out value="${signIn}"/></a></div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>

