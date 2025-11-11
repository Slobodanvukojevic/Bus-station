<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Manage Lines</title></head>
<body>
<h2>Bus Lines</h2>
<table border="1">
    <tr><th>ID</th><th>Route</th></tr>
    <c:forEach var="l" items="${lines}">
        <tr><td>${l.id}</td><td>${l.route}</td></tr>
    </c:forEach>
</table>
</body>
</html>
