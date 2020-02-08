<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="" scope="page"/>
<c:set var="user_id" value="${sessionScope.user_id}" />
<c:set var="user_type" value="${sessionScope.user_type}" />
<c:set var="first_name" value="${sessionScope.first_name}" />

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.nav.users" var="users"/>
<fmt:message bundle="${loc}" key="local.nav.all.orders" var="allOrders"/>
<fmt:message bundle="${loc}" key="local.nav.all.promocodes" var="allPromoCodes"/>

<fmt:message bundle="${loc}" key="local.title.profile" var="profile"/>
<fmt:message bundle="${loc}" key="local.title.home" var="home"/>
<fmt:message bundle="${loc}" key="local.title.about" var="about"/>
<fmt:message bundle="${loc}" key="local.title.service" var="service"/>
<fmt:message bundle="${loc}" key="local.title.registration" var="registration"/>
<fmt:message bundle="${loc}" key="local.language" var="language"/>
<fmt:message bundle="${loc}" key="local.locref.name.en" var="en_loc"/>
<fmt:message bundle="${loc}" key="local.locref.name.ru" var="ru_loc"/>
<fmt:message bundle="${loc}" key="local.sign.in" var="signIn"/>
<fmt:message bundle="${loc}" key="local.sign.out" var="signOut"/>
<fmt:message bundle="${loc}" key="local.my.orders" var="myOrders"/>

<head>
    <meta charset="utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" id="bootstrap-css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet">
    <script src="/js/main.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="js/moment-with-locales.min.js"></script>
    <script src="../../js/bootstrap-input-spinner.js"></script>

<nav class="navbar navbar-expand-lg navbar-dark primary-color navbar-right" id="header">
    <div class="container">

    <a class="navbar-brand" href="#">
        <img src="../../img/corgy2.jpg" height="30" alt="logo"/>
    </a>

    <div class="collapse navbar-collapse" id="basicExampleNav">

        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/home">${home}
                    <span class="sr-only">(current)</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/about">${about}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/catalog">${service}</a>
            </li>
            <c:if test="${user_type eq 'ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/users">${users}</a>
                </li>
            </c:if>
            <c:if test="${user_type eq 'MANAGER'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/all-orders">${allOrders}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/all-promocodes">${allPromoCodes}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/catalog-items">Change Catalog</a>
                </li>
            </c:if>
        </ul>



        <ul class="nav navbar-nav navbar-right">
            <c:if test="${user_id eq null}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/authentication">${signIn}</a>
                </li>
            </c:if>
            <c:if test="${not (user_id eq null)}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/orders">${myOrders}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/profile">${profile}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" onclick="document.getElementById('logout').submit(); return false;">${signOut}</a>
                </li>
                <form id="logout" class="hide" action="controller" method="post">
                    <input type="hidden" name="command_name" value="logout">
                </form>
            </c:if>


            <div class="dropdown">
                <a href="#" class="btn dropdown-toggle" data-toggle="dropdown" type="button" id="dropdownMenuButton" aria-haspopup="true" aria-expanded="false">${language}<span class="caret"></span></a>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li>
                        <a class="dropdown-item" href="#" onclick="document.getElementById('changeLanguageRu').submit(); return false;">${ru_loc}</a>
                        <form id="changeLanguageRu" class="hide" action="/controller" method="post">
                            <input type="hidden" name="locale" value="ru-RU" />
                            <input type="hidden" id="lang1" name="path"/>
                            <input type="hidden" name="command_name" value="change_locale">
                        </form>
                    </li>
                    <li>
                        <a class="dropdown-item" href="#" onclick="document.getElementById('changeLanguageEn').submit(); return false;">${en_loc}</a>
                        <form id="changeLanguageEn" class="hide" action="/controller" method="post">
                            <input type="hidden" name="local" value="en-EN" />
                            <input type="hidden" id="lang2" name="path" />
                            <input type="hidden" name="command_name" value="change_locale">
                        </form>
                    </li>
                </div>
            </div>
        </ul>
    </div>
        </div>
</nav>
<script>
  document.getElementById('lang1').setAttribute("value", location.href);
  document.getElementById('lang2').setAttribute("value", location.href);
</script>
</head>

