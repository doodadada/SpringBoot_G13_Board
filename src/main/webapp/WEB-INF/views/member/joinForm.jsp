<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/board.css">
    <script type="text/javascript" src="/script/board.js"></script>
</head>
<body>
<form name="join" class="login-form" action="join" method="post">
    <h2>Join</h2>
    <div class="field">
        <label>User ID</label>
        <input style="flex:3" type="text" name="userid" value="${dto.userid}">
        <input style="flex:1" type="button" value="ID Check" onClick="idcheck()"/>
        <input type="hidden" name="reid" value="${reid}">
    </div>
    <div class="field"><label>Password</label><input type="password" name="pwd" ></div>
    <div class="field"><label>Retype Pw</label><input type="password" name="pwd_check"></div>
    <div class="field"><label>name</label><input type="text" name="name" value="${dto.name}"></div>
    <div class="field"><label>Email</label><input type="text" name="email" value="${dto.email}"></div>
    <div class="field"><label>Phone</label><input type="text" name="phone" value="${dto.phone}"></div>
    <div class="field"><label>${message}</label></div>
    <div class="login-button">
        <input type="submit" class="btn-login" value="Join"/>
        <input type="button" class="btn-login" value="Back" onClick="location.href='/'"/>
    </div>
</form>
</body>
</html>
