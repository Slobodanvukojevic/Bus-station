<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Reports</title></head>
<body>
<h2>Generate Reports</h2>

<form method="get" action="${pageContext.request.contextPath}/admin/reports/daily">
    <label>Date:</label>
    <input type="date" name="date" required/>
    <input type="submit" value="Generate Daily Report"/>
</form>

<form method="get" action="${pageContext.request.contextPath}/admin/reports/line">
    <label>Line ID:</label>
    <input type="number" name="lineId" min="1" required/>
    <input type="submit" value="Generate Line Report"/>
</form>

<form method="get" action="${pageContext.request.contextPath}/admin/reports/summary">
    <input type="submit" value="Generate Summary Report"/>
</form>

<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<p><a href="${pageContext.request.contextPath}/admin/dashboard">Back</a></p>
</body>
</html>
