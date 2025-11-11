<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>User Profile</title></head>
<body>
<h2>Welcome, ${user.username}</h2>
<p>Email: ${user.email}</p>
<p>Role: ${user.role}</p>
<a href="/tickets">My Tickets</a> | <a href="/logout">Logout</a>
</body>
</html>
