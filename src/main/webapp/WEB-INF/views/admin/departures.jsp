<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Manage Departures</title></head>
<body>
<h2>Manage Departures</h2>

<!-- Add Departure -->
<form method="post" action="${pageContext.request.contextPath}/admin/departures">
    <input type="hidden" name="lineId" value="${line.id}"/>

    <label>Datum:</label>
    <input type="date" name="date" required/><br/>

    <label>Vreme:</label>
    <input type="time" name="time" required/><br/>

    <label>Vozač:</label>
    <input type="text" name="driver" required/><br/>

    <label>Broj sedišta:</label>
    <input type="number" name="seats" min="1" required/><br/>

    <label>Cena:</label>
    <input type="number" step="0.01" name="price" required/><br/>

    <button type="submit">Dodaj polazak</button>
</form>

<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<h3>Existing Departures</h3>
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
            <td>
                <a href="${pageContext.request.contextPath}/admin/revenue/${d.id}">Prihod</a> |
                <form method="post" action="${pageContext.request.contextPath}/admin/departures/delete/${d.id}" style="display:inline;">
                    <input type="submit" value="Delete" onclick="return confirm('Da li ste sigurni?');"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="${pageContext.request.contextPath}/admin">Back to Dashboard</a></p>
</body>
</html>
