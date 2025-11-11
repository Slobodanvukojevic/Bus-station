<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Register</title></head>
<body>
<h2>Create Account</h2>

<form method="post" action="${pageContext.request.contextPath}/register">
    <label>Username:</label><br/>
    <label>
        <input type="text" name="username"/>
    </label><br/>
    <label>Email:</label><br/>
    <label>
        <input type="email" name="email"/>
    </label><br/>
    <label>Password:</label><br/>
    <label>
        <input type="password" name="password"/>
    </label><br/><br/>
    <input type="submit" value="Register"/>
</form>

<p><a href="${pageContext.request.contextPath}/login">Back to Login</a></p>
</body>
</html>
