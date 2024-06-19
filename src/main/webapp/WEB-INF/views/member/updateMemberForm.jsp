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
<form name="update" class="login-form" action="updateMember" method="post">
    <h2>Update Member</h2>
    <div class="field">
        <label>User ID</label>
        <input type="text" name="userid" value="${loginUser.userid}" readonly />
    </div>
    <c:choose>
        <c:when test="${loginUser.provider == 'kakao'}">
            <div class="field"><label>Password</label><input type="password" name="pwd" value="1" readonly></div>
            <div class="field"><label>Retype Pw</label><input type="password" name="pwd_check" value="1" readonly></div>
        </c:when>
        <c:otherwise>
            <div class="field"><label>Password</label><input type="password" name="pwd"></div>
            <div class="field"><label>Retype Pw</label><input type="password" name="pwd_check"></div>
        </c:otherwise>
    </c:choose>
    <div class="field">
        <label>name</label><input type="text" name="name" value="${loginUser.name}">
    </div>
    <div class="field">
        <label>Email</label><input type="text" name="email" value="${loginUser.email}">
    </div>
    <div class="field">
        <label>Phone</label><input type="text" name="phone" value="${loginUser.phone}">
    </div>
    <div class="field"><label>${message}</label></div>
    <div class="login-button">
        <input type="submit" class="btn-login" value="Update"  onClick="return updateCheck()" />
        <input type="button" class="btn-login" value="Back"
               onClick="location.href='boardlist'"/>
    </div>
</form>
</body>
</html>
