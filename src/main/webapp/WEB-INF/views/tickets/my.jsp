<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>My Tickets</title></head>
<body>
<h2>My Tickets</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Route</th>
        <th>Date</th>
        <th>Time</th>
        <th>Seats</th>
        <th>Price</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <c:forEach var="t" items="${tickets}">
        <tr>
            <td>${t.id}</td>
            <td>${t.departure.line.startStation} → ${t.departure.line.endStation}</td>
            <td>${t.departure.date}</td>
            <td>${t.departure.time}</td>
            <td>${t.seatCount}</td>
            <td>${t.price}</td>
            <td>${t.status}</td>
            <td>
                <c:if test="${t.status eq 'ACTIVE'}">
                    <a href="${pageContext.request.contextPath}/tickets/cancel/${t.id}">Cancel</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="${pageContext.request.contextPath}/">Home</a></p>
</body>
</html>
