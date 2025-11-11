<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>My Tickets</title></head>
<body>
<h2>Your Tickets</h2>
<table border="1">
    <tr><th>ID</th><th>Line</th><th>Departure</th><th>Seat</th></tr>
    <c:forEach var="ticket" items="${tickets}">
        <tr>
            <td>${ticket.id}</td>
            <td>${ticket.departure.line.name}</td>
            <td>${ticket.departure.time}</td>
            <td>${ticket.seatNumber}</td>
        </tr>
    </c:forEach>
</table>
<a href="/purchase">Buy New Ticket</a>
</body>
</html>
