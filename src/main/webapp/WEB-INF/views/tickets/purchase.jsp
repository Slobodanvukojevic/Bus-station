<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Buy Ticket</title></head>
<body>
<h2>Buy Ticket</h2>

<form method="post" action="${pageContext.request.contextPath}/tickets/buy">
    <input type="hidden" name="departureId" value="${departure.id}"/>
    <label>Seats:</label><input type="number" name="seatCount" min="1" value="1"/><br/><br/>
    <input type="submit" value="Confirm Purchase"/>
</form>

<p><a href="${pageContext.request.contextPath}/departures/list">Back to Departures</a></p>
</body>
</html>
