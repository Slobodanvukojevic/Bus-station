<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Admin Dashboard</title></head>
<body>
<h2>Admin Dashboard</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/admin/users">Manage Users</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/lines">Manage Lines</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/departures">Manage Departures</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/reports">Reports</a></li>
</ul>
</body>
</html>
