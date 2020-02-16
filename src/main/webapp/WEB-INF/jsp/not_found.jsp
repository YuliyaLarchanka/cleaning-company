<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.error.page.title.404" var="title"/>
<fmt:message bundle="${loc}" key="local.error.page.message.400" var="message"/>

<html>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<body class="container">
<div>
    <h1 style="color:#CF0B15;" class="text-center"><c:out value="${title}"/></h1>
    <h3 class="text-center"><c:out value="${message}"/></h3>
</div>
</body>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</html>
