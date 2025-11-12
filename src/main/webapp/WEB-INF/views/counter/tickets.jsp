<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Prodate karte</title></head>
<body>
<h2>Pregled prodatih karata</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<c:if test="${not empty selectedUser}">
    <h3>Karte korisnika: ${selectedUser.username}</h3>
</c:if>

<c:if test="${empty tickets}">
    <p>Nema prodatih karata.</p>
</c:if>

<c:if test="${not empty tickets}">
    <table border="1">
        <thead>
        <tr>
            <th>Šifra</th>
            <th>Korisnik</th>
            <th>Linija</th>
            <th>Datum polaska</th>
            <th>Vreme</th>
            <th>Sedišta</th>
            <th>Cena</th>
            <th>Datum kupovine</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ticket" items="${tickets}">
            <tr>
                <td>${ticket.ticketCode}</td>
                <td>${ticket.user.username}</td>
                <td>${ticket.departure.line.startStation} → ${ticket.departure.line.endStation}</td>
                <td>${ticket.departure.date}</td>
                <td>${ticket.departure.time}</td>
                <td>${ticket.seatCount}</td>
                <td>${ticket.priceAtPurchase} RSD</td>
                <td>${ticket.purchaseDate}</td>
                <td>
                    <c:choose>
                        <c:when test="${ticket.status == 'ACTIVE'}">
                            <span style="color:green;">Aktivna</span>
                        </c:when>
                        <c:when test="${ticket.status == 'CANCELLED'}">
                            <span style="color:red;">Otkazana</span>
                        </c:when>
                        <c:otherwise>
                            <span style="color:gray;">Iskorišćena</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<p>
    <a href="${pageContext.request.contextPath}/counter/sales">Nazad na prodaju</a> |
    <a href="${pageContext.request.contextPath}/">Početna</a>
</p>
</body>
</html>