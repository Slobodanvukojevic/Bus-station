
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>

<form method="post" action="${pageContext.request.contextPath}/login">
    <label>Username:</label><br/>
    <label>
        <input type="text" name="username"/>
    </label><br/>
    <label>Password:</label><br/>
    <label>
        <input type="password" name="password"/>
    </label><br/><br/>
    <input type="submit" value="Login"/>
</form>


    <p style="color:red;">Invalid username or password</p>


</body>
</html>
