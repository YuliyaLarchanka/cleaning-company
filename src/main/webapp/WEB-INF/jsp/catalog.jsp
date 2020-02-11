<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="catalogItemList" value="${requestScope.catalogItemList}"/>
<c:set var="errorKey" value="${requestScope.errorKey}"/>
<c:set var="successKey" value="${requestScope.successKey}"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.catalog.apartment" var="yourApartment"/>
<fmt:message bundle="${loc}" key="local.catalog.odd.options" var="oddOptions"/>
<fmt:message bundle="${loc}" key="local.catalog.date.time" var="dateTime"/>
<fmt:message bundle="${loc}" key="local.catalog.payment.method" var="paymentMethod"/>
<fmt:message bundle="${loc}" key="local.catalog.payment.method.cash" var="cash"/>
<fmt:message bundle="${loc}" key="local.catalog.payment.method.card" var="card"/>
<fmt:message bundle="${loc}" key="local.catalog.payment.method.account.balance" var="accountBalance"/>
<fmt:message bundle="${loc}" key="local.catalog.promo.code" var="promoCode"/>
<fmt:message bundle="${loc}" key="local.catalog.button.create.order" var="createOrderButton"/>
<fmt:message bundle="${loc}" key="local.catalog.button.label" var="label"/>
<fmt:message bundle="${loc}" key="local.catalog.button.delete" var="delete"/>
<fmt:message bundle="${loc}" key="local.catalog.button.edit" var="edit"/>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<head>
    <script src="../../js/bootstrap-input-spinner.js"></script>
    <link rel="stylesheet prefetch" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel='stylesheet' href='/webjars/datetimepicker/2.3.4/jquery.datetimepicker.css'>
    <script type="text/javascript" src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/datetimepicker/2.3.4/jquery.datetimepicker.js"></script>


    <style class="cp-pen-styles">.nopad {
        padding-left: 0 !important;
        padding-right: 0 !important;
    }

    /*image gallery*/
    .image-checkbox {
        cursor: pointer;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        -webkit-box-sizing: border-box;
        border: 4px solid transparent;
        margin-bottom: 0;
        outline: 0;
    }

    .image-checkbox input[type="checkbox"] {
        display: none;
    }

    .image-checkbox-checked {
        border-color: #4783B0;
    }

    .image-checkbox .fa {
        position: absolute;
        color: #4A79A3;
        background-color: #fff;
        padding: 10px;
        top: 0;
        right: 0;
    }

    .image-checkbox-checked .fa {
        display: block !important;
    }</style>


</head>
<body>
<div class="container" style="margin-bottom: 50px">
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

    <form method="post" action="/controller">
        <h3>${yourApartment}</h3>
        <div class="row">
            <c:forEach var="catalogItem" items="${requestScope.catalogItemList}">
                <c:if test="${catalogItem.multipleSupported eq true}">
                    <div class="col-6">
                        <div style="margin-bottom: 60px; width: 300px">
                            <label style="font-size: 20px"><c:out value="${catalogItem.name}"/> - <c:out
                                    value="${catalogItem.price}"/> RUB
                                <img class="img-responsive" src="../../img/items/<c:out value="${catalogItem.imageName}"/>"
                                     style="width: 280px; height: 280px"></label>
                            <input type="number" value="1" min="1" max="5" step="1"
                                   name="multiple_item/<c:out value="${catalogItem.id}"/>"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <br>
        <h3>${oddOptions}</h3>
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
        <div class="row">
            <c:forEach var="catalogItem" items="${requestScope.catalogItemList}">
                <c:if test="${catalogItem.multipleSupported eq false}">
                    <div class="col-sm">
                        <label class="image-checkbox">
                            <img class="img-responsive" src="../../img/items/<c:out value="${catalogItem.imageName}"/>"
                                 style="width: 250px; height: 250px">
                            <input name="single_item_id" value="<c:out value="${catalogItem.id}"/>" type="checkbox">
                            <i class="fa fa-check hidden"></i>
                            <h4><c:out value="${catalogItem.name}"/> - <c:out value="${catalogItem.price}"/>RUB</h4>
                        </label>
                    </div>
                </c:if>
            </c:forEach>
        </div>

        <br>
        <h3 style="margin-top: 50px">${dateTime}</h3>
        <input class="form-control" type="text" name="date" required="required" value="">

        <br>
        <h3 style="margin-top: 50px">${paymentMethod}</h3>
        <div class="row">
            <div class="col-sm">
                <label class="image-radio">
                    <img class="img-responsive" src="../../img/cash.png" style="width: 230px; height: 180px">
                    <input name="payment_method" value="cash" type="radio" checked>
                    <h2>${cash}</h2>
                </label>
            </div>
            <div class="col-sm">
                <label class="image-radio">
                    <img class="img-responsive" src="../../img/card.png" style="width: 230px; height: 180px">
                    <input name="payment_method" value="card" type="radio">
                    <h2>${card}</h2>
                </label>
            </div>
            <div class="col-sm">
                <label class="image-radio">
                    <img class="img-responsive" src="../../img/account-balance.png" style="width: 230px; height: 180px">
                    <input name="payment_method" value="account_balance" type="radio">
                    <h2>${accountBalance}</h2>
                </label>
            </div>
        </div>


        <div style="margin-top: 100px">
            <label style="font-size: 20px">${promoCode}</label>
            <input style="width: 300px;" class="form-control" autocomplete="off" type="text" name="promo_code"
                   placeholder="PROMO">
        </div>

        <br>
        <label class="col-md-3 control-label"></label>
        <div class="col-md-8">
            <c:if test="${user_id eq null}">
                <button type="submit" class="btn btn-primary btn-lg" disabled>${createOrderButton}</button>
                <p>(${label})</p>
            </c:if>
            <c:if test="${not (user_id eq null)}">
                <button type="submit" class="btn btn-primary btn-lg">${createOrderButton}</button>
            </c:if>
        </div>
        <input type="hidden" name="command_name" value="create_order">
    </form>
</div>

<script>
  $("input[type='number']").InputSpinner({

    decrementButton: "<strong>-</strong>",
    incrementButton: "<strong>+</strong>",
    groupClass: "input-group-spinner",
    buttonsClass: "btn-outline-secondary",
    buttonsWidth: "50px",
    textAlign: "center",
    autoDelay: 500,
    autoInterval: 100,
    boostThreshold: 15,
    boostMultiplier: "auto",
    locale: null

  });
</script>
<script>// image gallery
// init the state from the input
$(".image-checkbox").each(function () {
  if ($(this).find('input[type="checkbox"]').first().attr("checked")) {
    $(this).addClass('image-checkbox-checked');
  } else {
    $(this).removeClass('image-checkbox-checked');
  }
});

// sync the state to the input
$(".image-checkbox").on("click", function (e) {
  $(this).toggleClass('image-checkbox-checked');
  var $checkbox = $(this).find('input[type="checkbox"]');
  $checkbox.prop("checked", !$checkbox.prop("checked"));

  e.preventDefault();
});
//# sourceURL=pen.js
</script>
<script src="../../js/jquery.simple-dtpicker.js"></script>
<link type="text/css" href="../../css/jquery.simple-dtpicker.css" rel="stylesheet"/>
<script src="/js/date-time-picker.js"></script>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

</html>
