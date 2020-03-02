<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="promoCodeList" value="${requestScope.promoCodeList}"/>
<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<c:set var="type" value="${sessionScope.user_type}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.nav.all.promocodes" var="title"/>
<fmt:message bundle="${loc}" key="local.page.promocodes.name" var="name"/>
<fmt:message bundle="${loc}" key="local.page.promocodes.discount" var="discount"/>

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
        <c:forEach var="promoCode" items="${requestScope.promoCodeList}">
            <div class="col-6">
                <form method="post" action="/controller">
                    <h4 style="margin-top: 20px"><c:out value="${name}"/> <c:out value="${promoCode.value}"/></h4>
                    <p><c:out value="${discount}"/> <c:out value="${promoCode.discountPercentage}"/>%</p>
                    <button type="submit" class="btn btn-primary">Delete</button>
                    <input type="hidden" name="promo_code_id" value="<c:out value="${promoCode.id}"/>">
                    <input type="hidden" name="command_name" value="delete_promocode">
                </form>
            </div>
        </c:forEach>
    </div>
    <div style="margin-top: 100px">
        <h4>Add promocode</h4>
            <form  method="post" action="/controller">
                <label>Name</label>
                <input class="form-control" autocomplete="off" style="width: 300px;" type="text" minlength="2" maxlength="10" name="value" required="required"/>
                <label>Discount</label>
                <input class="form-control" autocomplete="off" style="width: 300px;" type="number" min="1" max="100" name="discount" required="required"/>
                <button type="submit" class="btn btn-primary" style="margin-top: 20px">Save</button>
                <input type="hidden" name="command_name" value="add_promocode">
            </form>
    </div>
</div>
</body>
</html>