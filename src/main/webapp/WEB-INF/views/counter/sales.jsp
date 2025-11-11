<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Counter Sales</title></head>
<body>
<h2>Counter Ticket Sales</h2>

<form method="post" action="${pageContext.request.contextPath}/counter/sell">
    <label>Departure:</label>
    <select name="departureId">
        <c:forEach var="d" items="${departures}">
            <option value="${d.id}">
                    ${d.line.startStation} → ${d.line.endStation} | ${d.date} ${d.time}
            </option>
        </c:forEach>
    </select><br/>
    <label>Seats:</label>
    <input type="number" name="seatCount" min="1" value="1"/><br/><br/>
    <input type="submit" value="Sell Ticket"/>
</form>

<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<p><a href="${pageContext.request.contextPath}/">Home</a></p>
</body>
</html>
