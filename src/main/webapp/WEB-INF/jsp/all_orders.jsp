<%@ page import="by.larchanka.tiptopcleaning.entity.OrderStatus" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="orderList" value="${requestScope.orderList}"/>
<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<c:set var="statusList" value="<%=OrderStatus.values()%>"/>

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
        <c:forEach var="accountOrder" items="${requestScope.orderList}">
            <div class="col-6 border-warning" style="margin-bottom: 50px">
                <form method="post" action="/controller">
                    <h3><fmt:formatDate value="${accountOrder.dateTime}" pattern="dd/MM/yyyy HH:mm"/></h3>
                    <c:forEach var="orderItem" items="${accountOrder.orderItemList}">
                        <p><c:out value="${orderItem.catalogItem.name}"/> <c:out value="${orderItem.amount}"/></p>
                    </c:forEach>

                    <div class="form-group">
                        <label class="control-label">Order status:</label>
                        <select class="form-control" name="account_order_status">
                            <c:forEach var="status" items="${statusList}">
                                <option value="<c:out value="${status.name}"/>"
                                        <c:if test="${accountOrder.orderStatus eq status}">selected="selected"</c:if>>
                                    <c:out value="${status.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>

                    <h4>Payment method: <c:out value="${accountOrder.paymentMethod}"/></h4>
                    <h4>Total cost: <c:out value="${accountOrder.totalCost}"/> RUB</h4>
                    <div style="height: 20px"></div>
                    <button type="submit" class="btn btn-primary">Save</button>
                    <input type="hidden" name="account_order_id" value="<c:out value="${accountOrder.id}"/>">
                    <input type="hidden" name="command_name" value="change_status">
                </form>
            </div>
        </c:forEach>
    </div>

    <nav aria-label="Page navigation example">
        <ul class="pagination">

            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link" href="all-orders?page=${currentPage - 1}">Previous</a></li>
            </c:if>

            <c:forEach begin="1" end="${pagesCount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link" href="#">${i}<span class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="all-orders?page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt pagesCount}">
                <li class="page-item"><a class="page-link" href="all-orders?page=${currentPage + 1}">Next</a></li>
            </c:if>

        </ul>
    </nav>

    <%--For displaying Previous link except for the 1st page --%>
    <%--<c:if test="${currentPage != 1}">--%>
    <%--<td><a href="all-orders?page=${currentPage - 1}">Previous</a></td>--%>
    <%--</c:if>--%>

    <%--&lt;%&ndash;For displaying Page numbers.--%>
    <%--The when condition does not display a link for the current page&ndash;%&gt;--%>
    <%--<table border="1" cellpadding="5" cellspacing="5">--%>
    <%--<tr>--%>
    <%--<c:forEach begin="1" end="${pagesCount}" var="i">--%>
    <%--<c:choose>--%>
    <%--<c:when test="${currentPage eq i}">--%>
    <%--<td>${i}</td>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--<td><a href="all-orders?page=${i}">${i}</a></td>--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--</c:forEach>--%>
    <%--</tr>--%>
    <%--</table>--%>

    <%--&lt;%&ndash;For displaying Next link &ndash;%&gt;--%>
    <%--<c:if test="${currentPage lt pagesCount}">--%>
    <%--<td><a href="all-orders?page=${currentPage + 1}">Next</a></td>--%>
    <%--</c:if>--%>
</div>
</body>
<%--<jsp:include page="/WEB-INF/jsp/footer.jsp"/>--%>
</html>
