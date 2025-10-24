
<html>
<head>
    <title><c:out value="${pageTitle != null ? pageTitle : 'Bus Station'}"/></title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>"/>
</head>
<body>
<header>
    <nav>
        <a href="<c:url value='/'/>">Početna</a>
        <a href="<c:url value='/departures'/>">Pretraga</a>
        <c:choose>
            <c:when test="${pageContext.request.userPrincipal != null}">
                <a href="<c:url value='/my-tickets'/>">Moje karte</a>
                <a href="<c:url value='/logout'/>">Odjava</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/login'/>">Prijava</a>
                <a href="<c:url value='/register'/>">Registracija</a>
            </c:otherwise>
        </c:choose>
        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
            | <a href="<c:url value='/admin/lines'/>">Admin</a>
        </c:if>
    </nav>
</header>
<main>

</main>
<footer>
    <small>© Bus Station</small>
</footer>
</body>
</html>
