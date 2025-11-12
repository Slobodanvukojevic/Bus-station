<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Pretraga polazaka</title></head>
<body>
<h2>Pretraži polaske</h2>

<form method="post" action="${pageContext.request.contextPath}/tickets/search">
    <label>Polazište:</label>
    <select name="start" required>
        <option value="">-- Izaberi polazište --</option>
        <c:forEach var="station" items="${startStations}">
            <option value="${station}" ${criteria.start == station ? 'selected' : ''}>${station}</option>
        </c:forEach>
    </select><br/>

    <label>Odredište:</label>
    <select name="end" required>
        <option value="">-- Izaberi odredište --</option>
        <c:forEach var="station" items="${endStations}">
            <option value="${station}" ${criteria.end == station ? 'selected' : ''}>${station}</option>
        </c:forEach>
    </select><br/>

    <label>Datum:</label>
    <input type="date" name="date" value="${criteria.date}" required/><br/>

    <label>Vreme od:</label>
    <input type="time" name="timeFrom" value="${criteria.timeFrom}"/><br/>

    <label>Vreme do:</label>
    <input type="time" name="timeTo" value="${criteria.timeTo}"/><br/><br/>

    <input type="submit" value="Pretraži"/>
</form>

<c:if test="${not empty results}">
    <h3>Rezultati pretrage (${results.size()})</h3>
    <table border="1">
        <tr>
            <th>Linija</th>
            <th>Datum</th>
            <th>Vreme</th>
            <th>Slobodna mesta</th>
            <th>Cena</th>
            <th>Akcija</th>
        </tr>
        <c:forEach var="d" items="${results}">
            <tr>
                <td>${d.line.startStation} → ${d.line.endStation}</td>
                <td>${d.date}</td>
                <td>${d.time}</td>
                <td>${d.availableSeats} / ${d.capacity}</td>
                <td>${d.price} RSD</td>
                <td>
                    <c:choose>
                        <c:when test="${d.availableSeats == 0}">
                            <span style="color:red;">Popunjeno</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/tickets/purchase?departureId=${d.id}">
                                <button>Kupi</button>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty results && not empty criteria.start}">
    <p>Nema polazaka za zadati kriterijum.</p>
</c:if>

<p><a href="${pageContext.request.contextPath}/">Početna</a></p>
</body>
</html>