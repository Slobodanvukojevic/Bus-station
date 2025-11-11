<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Register</title></head>
<body>
<h2>Create Account</h2>
<form method="post" action="/register">
    <label>Username:</label><input type="text" name="username"/><br/>
    <label>Email:</label><input type="email" name="email"/><br/>
    <label>Password:</label><input type="password" name="password"/><br/>
    <input type="submit" value="Register"/>
</form>
</body>
</html>
