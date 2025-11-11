<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Manage Departures</title></head>
<body>
<h2>Departures</h2>
<table border="1">
    <tr><th>ID</th><th>Line</th><th>Time</th><th>Bus</th></tr>
    <c:forEach var="d" items="${departures}">
        <tr>
            <td>${d.id}</td>
            <td>${d.line.name}</td>
            <td>${d.time}</td>
            <td>${d.bus}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
