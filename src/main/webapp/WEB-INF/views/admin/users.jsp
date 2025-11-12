<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Manage Users</title></head>
<body>
<h2>All Users</h2>
<table border="1">
    <tr><th>ID</th><th>Username</th><th>Email</th><th>Role</th></tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.email}</td>
            <td>${u.role}</td>
        </tr>
    </c:forEach>
</table>
<p><a href="${pageContext.request.contextPath}/admin">Back to Dashboard</a></p>
</body>
</html>