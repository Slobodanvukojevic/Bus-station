<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Username:</label><br/>
    <input type="text" name="username"/><br/>
    <label>Password:</label><br/>
    <input type="password" name="password"/><br/><br/>
    <input type="submit" value="Login"/>
</form>



<c:if test="${param.error ne null}">
    <p style="color:red;">Invalid username or password.</p>
</c:if>


<p><a href="${pageContext.request.contextPath}/register">Create new account</a></p>
</body>
</html>
