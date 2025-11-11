<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Departures</title></head>
<body>
<h2>Departures</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Line</th>
        <th>Date</th>
        <th>Time</th>
        <th>Seats</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <c:forEach var="d" items="${departures}">
        <tr>
            <td>${d.id}</td>
            <td>${d.line.startStation} → ${d.line.endStation}</td>
            <td>${d.date}</td>
            <td>${d.time}</td>
            <td>${d.availableSeats}</td>
            <td>${d.price}</td>
            <td><a href="${pageContext.request.contextPath}/tickets/purchase?departureId=${d.id}">Buy</a></td>
        </tr>
    </c:forEach>
</table>

<p><a href="${pageContext.request.contextPath}/">Home</a></p>
</body>
</html>
