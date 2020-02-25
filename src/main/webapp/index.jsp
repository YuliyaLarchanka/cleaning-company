<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.home.title.large" var="largeTitle"/>
<fmt:message bundle="${loc}" key="local.home.title.small" var="smallTitle"/>
<fmt:message bundle="${loc}" key="local.home.page.room.cleaning.description" var="room"/>
<fmt:message bundle="${loc}" key="local.home.page.what.we.clean" var="whatWeClean"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom" var="bedroom"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom" var="bathroom"/>
<fmt:message bundle="${loc}" key="local.home.page.kitchen" var="kitchen"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner1" var="inner1"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner2" var="inner2"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner3" var="inner3"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner4" var="inner4"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner5" var="inner5"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner6" var="inner6"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner7" var="inner7"/>
<fmt:message bundle="${loc}" key="local.home.page.bedroom.inner8" var="inner8"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner1" var="inner1B"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner2" var="inner2B"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner3" var="inner3B"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner4" var="inner4B"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner5" var="inner5B"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner6" var="inner6B"/>
<fmt:message bundle="${loc}" key="local.home.page.bathroom.inner7" var="inner7B"/>
<fmt:message bundle="${loc}" key="local.home.page.kitchen.inner1" var="inner1K"/>
<fmt:message bundle="${loc}" key="local.home.page.kitchen.inner2" var="inner2K"/>
<fmt:message bundle="${loc}" key="local.home.page.kitchen.inner3" var="inner3K"/>
<fmt:message bundle="${loc}" key="local.home.page.kitchen.inner4" var="inner4K"/>
<fmt:message bundle="${loc}" key="local.home.page.kitchen.inner5" var="inner5K"/>
<fmt:message bundle="${loc}" key="local.home.page.start" var="start"/>
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
        <div style="height: 200px"></div>
        <h1 class="text-center"><c:out value="${largeTitle}"/></h1>
        <h4 class="text-center"><c:out value="${smallTitle}"/></h4>
        <button class="btn btn-lg btn-outline-primary" style="margin-left: 460px; margin-top: 30px"><a href="/catalog"><c:out
                value="${start}"/></a></button>
    </div>
</div>
<div align="center" style="margin-top: 10px">
    <img src="../img/arrow.png" height="60" width="60" alt="logo"/>
</div>
<div class="container">
    <h1 align="center" style="margin-top: 65px; margin-bottom: 30px; color: #ff8d1e"><c:out
            value="${whatWeClean}"/></h1>
    <div class="row" style="margin-bottom: 130px">
        <button type="button" class="btn btn-default" data-toggle="modal"
                style="margin-left: 20px; margin-right: 60px; height: 300px; width: 320px"
                data-target="#modal1">
            <img src="../img/index-kitchen.jpg" height="250" width="300" alt="logo"/>
            <h4 style="margin-top: 5px"><c:out value="${kitchen}"/></h4>
        </button>

        <button type="button" class="btn btn-default" data-toggle="modal"
                style="margin-left: 20px; margin-right: 60px; height: 300px; width: 320px"
                data-target="#modal2">
            <img src="../img/index-bathroom.jpg" height="250" width="300" alt="logo"/>
            <h4 style="margin-top: 5px"><c:out value="${bathroom}"/></h4>>
        </button>

        <button type="button" class="btn btn-default" data-toggle="modal"
                style="margin-left: 20px; height: 300px; width: 320px"
                data-target="#modal3">
            <img src="../img/index-bedroom.jpg" height="250" width="300" alt="logo"/>
            <h4 style="margin-top: 5px"><c:out value="${bedroom}"/></h4>>
        </button>
    </div>

    <div class="modal fade" id="modal1" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle"><c:out value="${kitchen}"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p><c:out value="${inner1K}"/></p>
                    <p><c:out value="${inner2K}"/></p>
                    <p><c:out value="${inner3K}"/></p>
                    <p><c:out value="${inner4K}"/></p>
                    <p><c:out value="${inner5K}"/></p>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modal2" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle2"><c:out value="${bathroom}"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p><c:out value="${inner1B}"/></p>
                    <p><c:out value="${inner2B}"/></p>
                    <p><c:out value="${inner3B}"/></p>
                    <p><c:out value="${inner4B}"/></p>
                    <p><c:out value="${inner5B}"/></p>
                    <p><c:out value="${inner6B}"/></p>
                    <p><c:out value="${inner7B}"/></p>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modal3" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLongTitle3"><c:out value="${bedroom}"/></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p><c:out value="${inner1}"/></p>
                    <p><c:out value="${inner2}"/></p>
                    <p><c:out value="${inner3}"/></p>
                    <p><c:out value="${inner4}"/></p>
                    <p><c:out value="${inner5}"/></p>
                    <p><c:out value="${inner6}"/></p>
                    <p><c:out value="${inner7}"/></p>
                    <p><c:out value="${inner8}"/></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>
