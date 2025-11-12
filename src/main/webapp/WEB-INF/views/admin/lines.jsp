<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Manage Lines</title></head>
<body>
<h2>Manage Bus Lines</h2>

<!-- Form for adding a new line -->
<form method="post" action="${pageContext.request.contextPath}/admin/lines">
    <label>Start Station:</label>
    <input type="text" name="start" required/><br/>
    <label>End Station:</label>
    <input type="text" name="end" required/><br/><br/>
    <input type="submit" value="Add Line"/>
</form>

<!-- Messages -->
<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<!-- Lines list -->
<h3>Existing Lines</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Start</th>
        <th>End</th>
        <th>Action</th>
    </tr>
    <c:forEach var="l" items="${lines}">
        <tr>
            <td>${l.id}</td>
            <td>${l.startStation}</td>
            <td>${l.endStation}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin/lines/delete/${l.id}" style="display:inline;">
                    <input type="submit" value="Delete"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<p><a href="${pageContext.request.contextPath}/admin">Back to Dashboard</a></p>
</body>
</html>