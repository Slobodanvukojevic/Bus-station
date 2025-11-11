<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Purchase Ticket</title></head>
<body>
<h2>Buy Ticket</h2>
<form method="post" action="/tickets/purchase">
    <label>Departure ID:</label><input type="number" name="departureId"/><br/>
    <label>Seat Number:</label><input type="number" name="seatNumber"/><br/>
    <input type="submit" value="Purchase"/>
</form>
</body>
</html>
