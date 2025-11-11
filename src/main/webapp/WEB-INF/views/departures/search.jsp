<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Search Departures</title></head>
<body>
<h2>Search Departures</h2>

<form method="get" action="${pageContext.request.contextPath}/departures/search">
    <label>Start:</label><input type="text" name="start"/><br/>
    <label>End:</label><input type="text" name="end"/><br/>
    <label>Date:</label><input type="date" name="date"/><br/>
    <input type="submit" value="Search"/>
</form>

<c:if test="${not empty results}">
    <h3>Results:</h3>
    <c:forEach var="d" items="${results}">
        <p>${d.line.startStation} → ${d.line.endStation}, ${d.date} ${d.time} (${d.availableSeats} seats)</p>
    </c:forEach>
</c:if>

<p><a href="${pageContext.request.contextPath}/">Home</a></p>
</body>
</html>
