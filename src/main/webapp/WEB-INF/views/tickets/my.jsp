<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>My Tickets</title></head>
<body>
<h2>Moje karte</h2>

<c:if test="${empty tickets}">
    <p>Nemate kupljenih karata.</p>
</c:if>

<c:if test="${not empty tickets}">
    <table border="1">
        <thead>
        <tr>
            <th>Šifra</th>
            <th>Linija</th>
            <th>Datum</th>
            <th>Vreme</th>
            <th>Sedišta</th>
            <th>Cena</th>
            <th>Status</th>
            <th>Akcija</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ticket" items="${tickets}">
            <tr>
                <td>${ticket.ticketCode}</td>
                <td>${ticket.departure.line.startStation} → ${ticket.departure.line.endStation}</td>
                <td>${ticket.departure.date}</td>
                <td>${ticket.departure.time}</td>
                <td>${ticket.seatCount}</td>
                <td>${ticket.priceAtPurchase}</td>
                <td>${ticket.status}</td>
                <td>
                    <c:if test="${ticket.status == 'ACTIVE'}">
                        <form method="post" action="${pageContext.request.contextPath}/tickets/cancel/${ticket.id}" style="display:inline;">
                            <input type="submit" value="Otkaži" onclick="return confirm('Da li ste sigurni?');"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<p>
    <a href="${pageContext.request.contextPath}/tickets/search">Kupi novu kartu</a> |
    <a href="${pageContext.request.contextPath}/">Početna</a>
</p>
</body>
</html>