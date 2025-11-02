<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Bus Station</title></head>
<body>

<h2>Welcome to Bus Station</h2>

<c:choose>
    <c:when test="${pageContext.request.userPrincipal != null}">
        <p>Logged in as: <strong>${pageContext.request.userPrincipal.name}</strong></p>
        <a href="/tickets">My Tickets</a> |
        <a href="/departures">Departures</a> |
        <a href="/logout">Logout</a>
    </c:when>
    <c:otherwise>
        <a href="/login">Login</a> |
        <a href="/register">Register</a> |
        <a href="/departures">Departures</a>
    </c:otherwise>
</c:choose>

</body>
</html>
