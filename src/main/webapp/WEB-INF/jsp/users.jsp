<%@ page import="by.larchanka.tiptopcleaning.entity.UserType" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="userList" value="${requestScope.userList}"/>
<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<c:set var="types" value="<%=UserType.values()%>"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.registration.title" var="title"/>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
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

    <div class="row">
        <c:forEach var="user" items="${requestScope.userList}">
            <div class="col-6 border-warning">
                <form method="post" action="/controller">
                    <h4>ID: <c:out value="${user.id}"/></h4>
                    <p>First name: <c:out value="${user.firstName}"/></p>
                    <p>Last name: <c:out value="${user.lastName}"/></p>

                    <div class="form-group">
                        <label for="user-type" class="control-label">Type:</label>
                        <select class="form-control" name="user_type" id="user-type">
                            <c:forEach var="type" items="${types}">
                                <option value="<c:out value="${type.name}"/>"
                                        <c:if test="${user.type eq type}">selected="selected"</c:if>><c:out
                                        value="${type.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <p>Email: <c:out value="${user.email}"/></p>
                    <button type="submit" class="btn btn-primary">Save</button>
                    <input type="hidden" name="command_name" value="change_user_type">
                    <input type="hidden" name="account_id" value="<c:out value="${user.id}"/>">
                </form>
            </div>
            <div style="height: 50px; border-bottom: #3f3f3f"></div>
        </c:forEach>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>
