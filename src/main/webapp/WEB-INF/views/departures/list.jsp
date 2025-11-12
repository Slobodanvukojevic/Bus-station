<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head><title>Departures</title></head>
<body>
<h2>Svi polasci</h2>

<sec:authorize access="isAuthenticated()">
    <p>Prijavljeni ste kao: <sec:authentication property="principal.username"/></p>
</sec:authorize>

<c:if test="${empty departures}">
    <p>Nema dostupnih polazaka.</p>
</c:if>

<c:if test="${not empty departures}">
    <table border="1">
        <tr>
            <th>Linija</th>
            <th>Datum</th>
            <th>Vreme</th>
            <th>Slobodna mesta</th>
            <th>Cena</th>
            <th>Akcija</th>
        </tr>
        <c:forEach var="d" items="${departures}">
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
                            <%-- Ako je korisnik prijavljen, prikaži dugme za kupovinu --%>
                            <sec:authorize access="isAuthenticated()">
                                <a href="${pageContext.request.contextPath}/tickets/purchase?departureId=${d.id}">
                                    <button>Kupi kartu</button>
                                </a>
                            </sec:authorize>

                            <%-- Ako NIJE prijavljen, prikaži link za login --%>
                            <sec:authorize access="!isAuthenticated()">
                                <a href="${pageContext.request.contextPath}/login">
                                    <button>Prijavi se za kupovinu</button>
                                </a>
                            </sec:authorize>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<p>
    <sec:authorize access="isAuthenticated()">
        <a href="${pageContext.request.contextPath}/tickets/search">Pretraži polaske</a> |
        <a href="${pageContext.request.contextPath}/tickets/my">Moje karte</a> |
    </sec:authorize>
    <a href="${pageContext.request.contextPath}/">Početna</a>
</p>
</body>
</html>