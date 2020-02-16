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
<fmt:message bundle="${loc}" key="local.catalog.label.new.catalog.item" var="newCataloItem"/>
<fmt:message bundle="${loc}" key="local.catalog.label.name" var="name"/>
<fmt:message bundle="${loc}" key="local.catalog.label.price" var="price"/>
<fmt:message bundle="${loc}" key="local.catalog.label.multiple.supported" var="multipleSupported"/>
<fmt:message bundle="${loc}" key="local.catalog.label.image" var="image"/>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <script src="../../js/bootstrap-input-spinner.js"></script>
    <link rel="stylesheet prefetch"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
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


    <h3><c:out value="${yourApartment}"/></h3>
    <div class="row">
        <c:forEach var="catalogItem" items="${requestScope.catalogItemList}">
            <c:if test="${catalogItem.multipleSupported eq true}">
                <div class="col-6">
                    <div style="margin-bottom: 60px; width: 300px">
                        <label style="font-size: 20px"><c:out value="${catalogItem.name}"/> - <c:out
                                value="${catalogItem.price}"/> RUB
                            <img class="img-responsive" src="../../img/items/<c:out value="${catalogItem.imageName}"/>"
                                 style="width: 280px; height: 280px"></label>
                        <div class="btn-toolbar">
                            <div class="btn-group" style="margin-right: 10px">
                                <form method="post" action="/controller">
                                    <input type="hidden" class="item-name"
                                           value="<c:out value="${catalogItem.name}"/>"/>
                                    <input type="hidden" class="item-price"
                                           value="<c:out value="${catalogItem.price}"/>"/>
                                    <input type="hidden" class="item-multiple-supported"
                                           value="<c:out value="${catalogItem.multipleSupported}"/>"/>
                                    <button type="button" class="btn btn-default" aria-label="Left Align"
                                            data-toggle="modal" data-target="#editCatalogItemModal"
                                            data-id="<c:out value="${catalogItem.id}"/>"><c:out value="${edit}"/></button>
                                </form>
                            </div>
                            <div class="btn-group">
                                <form method="post" action="/controller">
                                    <input type="hidden" name="catalog_item_id"
                                           value="<c:out value="${catalogItem.id}"/>"/>
                                    <input type="hidden" name="command_name" value="delete_catalog_item">
                                    <button type="submit" class="btn btn-primary btn-secondary"><c:out
                                            value="${delete}"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>

    <br>
    <h3><c:out value="${oddOptions}"/></h3>
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
                    <div class="btn-toolbar">
                        <div class="btn-group">
                            <form method="post" action="/controller">
                                <input type="hidden" class="item-name" value="<c:out value="${catalogItem.name}"/>"/>
                                <input type="hidden" class="item-price" value="<c:out value="${catalogItem.price}"/>"/>
                                <input type="hidden" class="item-multiple-supported"
                                       value="<c:out value="${catalogItem.multipleSupported}"/>"/>
                                <button type="button" class="btn btn-default" style="margin-right: 10px"
                                        aria-label="Left Align" data-toggle="modal" data-target="#editCatalogItemModal"
                                        data-id="<c:out value="${catalogItem.id}"/>"><c:out value="${edit}"/></button>
                            </form>
                            <form method="post" action="/controller">
                                <input type="hidden" name="catalog_item_id" value="<c:out value="${catalogItem.id}"/>"/>
                                <input type="hidden" name="command_name" value="delete_catalog_item">
                                <button type="submit" class="btn btn-primary btn-secondary"><c:out
                                        value="${delete}"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <br>
    </form>

    <h1 style="margin-top: 20px"><c:out value="${newCataloItem}"/></h1>

    <div class="row">
        <div class="col-6">
            <form method="post" action="/controller" enctype="multipart/form-data">
                <label><c:out value="${name}"/></label>
                <input class="form-control" autocomplete="off" style="width: 300px;" minlength="2" maxlength="30" type="text" name="name"
                       required="required"/>
                <label><c:out value="${price}"/></label>
                <input class="form-control" autocomplete="off" style="width: 300px;" min="1" max="200.0" type="number" name="price"
                       required="required"/>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="multiple_supported" id="checkbox1"/>
                    <label for="checkbox1" class="form-check-label"><c:out value="${multipleSupported}"/></label>
                </div>
                <br>
                <label><c:out value="${image}"/></label>
                <input type="file" name="image" required="required"/>
                <input type="hidden" name="command_name" value="create_catalog_item">
                <div class="row">
                    <button type="submit" class="btn btn-primary" style="margin-top: 20px; margin-right: 10px">Create
                    </button>
                    <button type="reset" class="btn btn-primary" style="margin-top: 20px">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="editCatalogItemModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" action="controller" id="itemEdit" enctype="multipart/form-data">
                    <label><c:out value="${name}"/></label>
                    <input class="form-control" autocomplete="off" style="width: 300px;" minlength="2" maxlength="30" type="text" name="name"
                           id="name" required="required"/>
                    <label><c:out value="${price}"/></label>
                    <input class="form-control" autocomplete="off" style="width: 300px;" min="1" max="200.0" type="number" name="price"
                           id="price" required="required"/>
                    <input type="checkbox" name="multiple_supported" id="multiple_supported"/>
                    <label><c:out value="${multipleSupported}"/></label>
                    <br>
                    <label><c:out value="${image}"/></label>
                    <input type="file" name="image" required="required"/>
                    <input type="hidden" name="catalog_item_id" id="catalog_item_id">
                    <input type="hidden" name="command_name" value="edit_catalog_item" id="editCatalogItem"/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" form="itemEdit">Save changes</button>
            </div>
        </div>
    </div>
</div>
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../js/jquery.min.js"><\/script>')</script>
<script src="../../js/bootstrap.min.js"></script>
<script>
  $("#editCatalogItemModal").on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget);
    const id = button.data('id');
    const modal = $(this);
    const item = $(button).closest("form");
    modal.find('.modal-body #name').val(item.find(".item-name").val());
    modal.find('.modal-body #price').val(item.find(".item-price").val());
    modal.find('.modal-body #multiple_supported').prop('checked', item.find(".item-multiple-supported").val() === 'true');
    modal.find('.modal-body #catalog_item_id').val(id);
  })
</script>
</html>
