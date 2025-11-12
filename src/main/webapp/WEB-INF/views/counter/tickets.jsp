<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Prodate karte</title></head>
<body>
<h2>Pregled prodatih karata</h2>

<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<c:if test="${not empty selectedUser}">
    <h3>Karte korisnika: ${selectedUser.username}</h3>
</c:if>

<!-- Sortiranje -->
<div>
    <strong>Sortiraj po:</strong>

    <c:choose>
        <c:when test="${not empty selectedUser}">
            <a href="${pageContext.request.contextPath}/counter/tickets/user?userId=${selectedUser.id}&sort=date_desc">
                <button>Datum kupovine ↓</button>
            </a>
            <a href="${pageContext.request.contextPath}/counter/tickets/user?userId=${selectedUser.id}&sort=date_asc">
                <button>Datum kupovine ↑</button>
            </a>
            <a href="${pageContext.request.contextPath}/counter/tickets/user?userId=${selectedUser.id}&sort=seats_desc">
                <button>Broj mesta ↓</button>
            </a>
            <a href="${pageContext.request.contextPath}/counter/tickets/user?userId=${selectedUser.id}&sort=seats_asc">
                <button>Broj mesta ↑</button>
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/counter/tickets?sort=date_desc">
                <button>Datum kupovine ↓</button>
            </a>
            <a href="${pageContext.request.contextPath}/counter/tickets?sort=date_asc">
                <button>Datum kupovine ↑</button>
            </a>
            <a href="${pageContext.request.contextPath}/counter/tickets?sort=seats_desc">
                <button>Broj mesta ↓</button>
            </a>
            <a href="${pageContext.request.contextPath}/counter/tickets?sort=seats_asc">
                <button>Broj mesta ↑</button>
            </a>
        </c:otherwise>
    </c:choose>

    <span>
        <em>Trenutno:
            <c:choose>
                <c:when test="${currentSort == 'date_desc'}">Datum ↓</c:when>
                <c:when test="${currentSort == 'date_asc'}">Datum ↑</c:when>
                <c:when test="${currentSort == 'seats_desc'}">Mesta ↓</c:when>
                <c:when test="${currentSort == 'seats_asc'}">Mesta ↑</c:when>
                <c:otherwise>Datum ↓</c:otherwise>
            </c:choose>
        </em>
    </span>
</div>

<c:if test="${empty tickets}">
    <p>Nema prodatih karata.</p>
</c:if>

<c:if test="${not empty tickets}">
    <table border="1">
        <thead>
        <tr style="background-color: #f0f0f0;">
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
                <td>${ticket.departure.date}  </td>
                <td>${ticket.departure.time}</td>
                <td style="text-align:center;">${ticket.seatCount}</td>
                <td style="text-align:right;">${ticket.priceAtPurchase} RSD</td>
                <td>${ticket.purchaseDate}</td>

                <td style="text-align:center;">
                    <c:if test="${ticket.status == 'ACTIVE'}">
                        <form method="post" action="${pageContext.request.contextPath}/counter/tickets/cancel/${ticket.id}" style="display:inline;">
                            <input type="submit" value="Otkaži"
                                   onclick="return confirm('Da li ste sigurni da želite da otkažete kartu?');"
                                   style="background-color:#ff4444; color:white; padding:5px 10px; border:none; cursor:pointer;"/>
                        </form>
                    </c:if>
                    <c:if test="${ticket.status != 'ACTIVE'}">
                        <span>-</span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p style="margin-top: 20px;">
        <strong>Ukupno karata:</strong> ${tickets.size()}
    </p>
</c:if>

<p>
    <a href="${pageContext.request.contextPath}/counter/sales">
        <button>← Nazad na prodaju</button>
    </a> |
    <a href="${pageContext.request.contextPath}/">
        <button>Početna</button>
    </a>
</p>
</body>
</html>