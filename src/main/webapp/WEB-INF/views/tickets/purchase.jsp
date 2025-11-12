<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Kupovina karte</title></head>
<body>
<h2>Kupovina karte</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<c:if test="${not empty departure}">
    <p>
        <strong>Linija:</strong> ${departure.line.startStation} → ${departure.line.endStation}<br/>
        <strong>Datum:</strong> ${departure.date}<br/>
        <strong>Vreme:</strong> ${departure.time}<br/>
        <strong>Cena po sedištu:</strong> ${departure.price} RSD<br/>
        <strong>Slobodna mesta:</strong> ${departure.availableSeats}
    </p>

    <form method="post" action="${pageContext.request.contextPath}/tickets/buy">
        <input type="hidden" name="departureId" value="${departure.id}"/>

        <label>Broj sedišta:</label>
        <input type="number" name="seatCount" min="1" max="${departure.availableSeats}" value="1" required/>
        <br/><br/>

        <input type="submit" value="Potvrdi kupovinu"/>
    </form>
</c:if>

<p><a href="${pageContext.request.contextPath}/departures/list">Nazad na polaske</a></p>
</body>
</html>