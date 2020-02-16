<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="accountOrderList" value="${requestScope.accountOrderList}"/>
<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.orders.status" var="orderStatus"/>
<fmt:message bundle="${loc}" key="local.orders.payment" var="paymentMethod"/>
<fmt:message bundle="${loc}" key="local.orders.cost" var="totalCost"/>

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
        <c:forEach var="accountOrder" items="${requestScope.accountOrderList}">
            <div class="col-6 border-warning">
                <h3><fmt:formatDate value="${accountOrder.dateTime}" pattern="dd/MM/yyyy HH:mm"/></h3>
                <c:forEach var="orderItem" items="${accountOrder.orderItemList}">
                    <p><c:out value="${orderItem.catalogItem.name}"/> <c:out value="${orderItem.amount}"/></p>
                </c:forEach>
                <p><c:out value="${orderStatus}"/><c:out value="${accountOrder.orderStatus}"/></p>
                <h4><c:out value="${orderStatus}"/><c:out value="${accountOrder.paymentMethod}"/></h4>
                <h4><c:out value="${totalCost}"/><c:out value="${accountOrder.totalCost}"/> RUB</h4>
                <div style="height: 20px"></div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
