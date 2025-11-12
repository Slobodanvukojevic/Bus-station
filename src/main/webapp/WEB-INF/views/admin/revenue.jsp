<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Revenue Report</title></head>
<body>
<h2>Izveštaj o prihodima</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<p><strong>Polazak ID:</strong> ${departureId}</p>
<p><strong>Ukupan prihod:</strong> ${revenue} RSD</p>

<a href="${pageContext.request.contextPath}/admin/revenue/${departureId}/pdf" target="_blank">
    <button>Preuzmi PDF izveštaj</button>
</a>

<p><a href="${pageContext.request.contextPath}/admin">Nazad na Dashboard</a></p>
</body>
</html>