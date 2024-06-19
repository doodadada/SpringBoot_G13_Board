<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/board.css">
    <script type="text/javascript">
        function selectedimage(){
            document.frm.submit();
        }
    </script>
</head>
<body>
<div id="wrap" align="center" style="width:100%">
    <form name="frm" action="fileupload" method="post" enctype="multipart/form-data">
        <h1>파일 선택</h1>
        <input type="file" name="image" onchange="selectedimage();">
    </form>
</div>
</body>
</html>
