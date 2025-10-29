<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Prijava</title>
</head>
<body>
<h2>Prijava</h2>

<c:if test="${param.error != null}">
    <p style="color:red;">Pogrešno korisničko ime ili lozinka.</p>
</c:if>

<c:if test="${param.logout != null}">
    <p style="color:green;">Uspešno ste se odjavili.</p>
</c:if>

<form method="post" action="<c:url value='/login'/>">
    <label>Korisničko ime: <input type="text" name="username" required/></label><br/>
    <label>Lozinka: <input type="password" name="password" required/></label><br/>
    <button type="submit">Prijavi se</button>
</form>

<p>Nemate nalog?
    <a href="<c:url value='register.jsp'/>">Registrujte se</a>
</p>
</body>
</html>
