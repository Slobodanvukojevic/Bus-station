<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Prodaja karata</title></head>
<body>
<h2>Prodaja karata - Šalter</h2>

<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<h3>Prodaj kartu</h3>
<form method="post" action="${pageContext.request.contextPath}/counter/sell">
    <label>Polazak:</label>
    <select name="departureId" required>
        <option value="">-- Izaberi polazak --</option>
        <c:forEach var="dep" items="${departures}">
            <option value="${dep.id}">
                    ${dep.line.startStation} → ${dep.line.endStation} |
                    ${dep.date} ${dep.time} |
                Slobodno: ${dep.availableSeats} |
                    ${dep.price} RSD
            </option>
        </c:forEach>
    </select><br/>

    <label>Broj karata:</label>
    <input type="number" name="seatCount" min="1" value="1" required/><br/><br/>

    <input type="submit" value="Prodaj kartu"/>
</form>

<h3>Pregled karata</h3>
<p>
    <a href="${pageContext.request.contextPath}/counter/tickets">
        <button>Sve prodate karte</button>
    </a>
</p>


<p><a href="${pageContext.request.contextPath}/">Početna</a></p>
</body>
</html>