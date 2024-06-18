<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
<table width="700" cellpadding="0" cellspacing="0" border="1">
    <form action="update" method="post">
        <tr>
            <td>작성자</td>
            <td><input type="text" name="writer" size="80" value="${dto.writer}"></td>
        </tr>
        <tr>
            <td>제목</td>
            <td><input type="text" name="title" size="80" value="${dto.title}"></td>
        </tr>
        <tr>
            <td>내용</td>
            <td><input type="text" name="content"size="80" value="${dto.content}"></td>
        </tr>
        <tr>
            <td colspan="2">${message}</td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="수정">&nbsp;&nbsp;&nbsp;
                <input type="hidden" name="id" value="${dto.id}"/>
                <a href="/">목록보기</a></td>
        </tr>
    </form>
</table>
</body>
</html>
