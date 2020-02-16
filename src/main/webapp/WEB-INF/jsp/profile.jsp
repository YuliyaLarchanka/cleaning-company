<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="http://tomcat.apache.org/example-taglib" %>


<c:set var="user" value="${requestScope.user}" />
<c:set var="errorKey" value="${requestScope.errorKey}" />
<c:set var="successKey" value="${requestScope.successKey}" />
<c:set var="user_type" value="${sessionScope.user_type}" />

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.profile.edit" var="editProfile"/>
<fmt:message bundle="${loc}" key="local.profile.email" var="email"/>
<fmt:message bundle="${loc}" key="local.profile.password" var="password"/>
<fmt:message bundle="${loc}" key="local.profile.password.confirm" var="confirmPassword"/>
<fmt:message bundle="${loc}" key="local.profile.odd.information" var="oddInformation"/>

<fmt:message bundle="${loc}" key="local.profile.name" var="name"/>
<fmt:message bundle="${loc}" key="local.profile.surname" var="surname"/>
<fmt:message bundle="${loc}" key="local.profile.phone.number" var="phoneNumber"/>
<fmt:message bundle="${loc}" key="local.profile.street" var="street"/>
<fmt:message bundle="${loc}" key="local.profile.house" var="house"/>
<fmt:message bundle="${loc}" key="local.profile.apartment" var="apartment"/>
<fmt:message bundle="${loc}" key="local.profile.entrance" var="entrance"/>
<fmt:message bundle="${loc}" key="local.profile.floor" var="floor"/>
<fmt:message bundle="${loc}" key="local.profile.intercom.code" var="intercomCode"/>
<fmt:message bundle="${loc}" key="local.profile.button.save" var="buttonSave"/>
<fmt:message bundle="${loc}" key="local.profile.button.add.money" var="addMoney"/>
<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<body>

<div class="container">
    <h1><c:out value="${editProfile}"/></h1>
    <hr>
    <div class="row">
        <div class="col-md-9 personal-info">
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

            <div style="margin-bottom: 30px"><ctg:hello role="${ user_type }"/></div>

            <form class="form-horizontal" role="form" method="post" action="/controller">
                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${email}"/></label>
                    <div class="col-lg-8">
                        <input type="email" class="form-control" name="email" required="required" value="${user.email}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${password}"/></label>
                    <div class="col-lg-8">
                        <input type="password" class="form-control" required="required" name="password">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${confirmPassword}"/></label>
                    <div class="col-lg-8">
                        <input type="password" class="form-control" required="required" name="confirm_password">
                    </div>
                </div>


                <hr>
                <h5><c:out value="${oddInformation}"/></h5>
                <br>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${name}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" minlength="2" maxlength="20" name="first_name" required="required" value="${user.firstName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${surname}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" minlength="2" maxlength="20" name="last_name" required="required" value="${user.lastName}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${phoneNumber}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" type="tel"
                               name="phone_number" placeholder="+375 29 1122334" maxlength="15" minlength="7" pattern="[+375]{0,1}[0-9]{2}[\s0-9]*$" value="${user.phoneNumber}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${street}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" maxlength="30" type="text" name="street" value="${user.street}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${house}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" maxlength="10" type="text" name="house_number" value="${user.house}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${apartment}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" type="text" maxlength="10" name="apartment_number" value="${user.apartment}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${entrance}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" type="text" maxlength="10" name="entrance_number" value="${user.entrance}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${floor}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" type="number" max="100" name="floor_number" value="${user.floor}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-3 control-label"><c:out value="${intercomCode}"/></label>
                    <div class="col-lg-8">
                        <input class="form-control" autocomplete="off" type="text" name="intercom_code" value="${user.intercomCode}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-3 control-label"></label>
                    <div class="col-md-8">
                        <button type="submit" class="btn btn-primary"><c:out value="${buttonSave}"/></button>
                    </div>
                </div>
                <input type="hidden" name="command_name" value="edit_profile">
            </form>
        </div>
        <div style="margin-bottom: 280px; margin-right: 100px">
            <h2>${user.money}$</h2>
            <a type="button" class="btn btn-success btn-lg" href="${pageContext.request.contextPath}/money"><c:out value="${addMoney}"/></a>
        </div>
    </div>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>
