<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Admin Dashboard</title></head>
<body>
<h2>Admin Dashboard</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/admin/users">Show Users</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/lines">Manage Lines</a></li>
</ul>

<h3>Select Line to Manage Departures:</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Route</th>
        <th>Action</th>
    </tr>
    <c:forEach var="line" items="${lines}">
        <tr>
            <td>${line.id}</td>
            <td>${line.startStation} → ${line.endStation}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/departures/${line.id}">Manage Departures</a>
            </td>
        </tr>
    </c:forEach>
</table>



<p><a href="${pageContext.request.contextPath}/">Home</a> </p>
</body>
</html>