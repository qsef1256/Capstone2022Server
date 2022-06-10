<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% response.setStatus(HttpServletResponse.SC_OK); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>error</title>
</head>
<body>

<c:set var="baseHost" value="<%=request.getRemoteAddr()%>"/>

<c:if test="${baseHost eq 'localhost' or baseHost eq '127.0.0.1' or baseHost eq '0:0:0:0:0:0:0:1'}">
    <c:out value="${requestScope['jakarta.servlet.error.status_code']}"/>
    <c:out value="${requestScope['jakarta.servlet.error.message']}"/>
    <c:out value="${requestScope['jakarta.servlet.error.exception']}"/>
</c:if>

</body>
</html>