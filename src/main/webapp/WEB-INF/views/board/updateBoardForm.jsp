<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/board.css">
    <script type="text/javascript">
        function updateBoardCheck(pass){
            if(document.insertBoard.pass.value!=pass){
                alert("비밀번호가 일치하지 않습니다");
                document.insertBoard.content.focus();
                return false;
            }else{
                return true;
            }
        }
        function selectImg(){
            var opt="toolbar=no,menubar=no,width=450,height=200";
            window.open('selectimg','selectimg',opt);
        }
    </script>
</head>
<body>
<div id="main_container">
    <h2>게시글 수정</h2>
    <div class="board">
        <form class="insertBoard" method="post" name="insertBoard" action="updateBoard"
              enctype="multipart/form-data">
            <div class="field">
                <label>작성자</label>
                <input type="text" name="userid" value="${board.userid}" readonly/>
            </div>
            <div class="field">
                <label>비밀번호</label>
                <input style="flex:2" type="password" name="pass" />
                <div  style="flex:2; margin-left:20px;">게시물 작성시 입력한 비밀번호 입력</div>
            </div>
            <div class="field">
                <label>이메일</label>
                <input type="text" name="email" value="${board.email}" readonly/>
            </div>
            <div class="field">
                <label>제목</label><input type="text" name="title" value="${board.title}"/>
            </div>
            <div class="field">
                <label>내용</label>
                <textarea name="content"rows="10" cols="100">${board.content}</textarea>
            </div>

            <div class="field">
                <label>이미지</label>
                <input type="button" value="파일 선택" onClick ="selectImg()">
            </div>
            <c:choose>
                <c:when test="${empty dto.image}">
                    <div class="field">
                        <label>이미지 미리보기</label>
                        <div style="flex:4">
                            <img src="" id="previewimg" width="150" style="display:none" />
                            <input type="hidden" name="savefilename">
                            <input type="hidden" name="image">
                            <div id="image"></div>
                            <div id="savefilename"></div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="field">
                        <label>이미지 미리보기</label>
                        <div style="flex:4">
                            <img src="/upload/${dto.savefilename}" id="previewimg" width="150" />
                            <input type="hidden" name="savefilename"value="${dto.savefilename}">
                            <input type="hidden" name="image" value="${dto.image}">
                            <div id="image">${dto.image}</div>
                            <div id="savefilename">${dto.savefilename}</div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="field">
                <label>기존이미지</label>
                <c:choose>
                    <c:when test="${empty board.savefilename}">
                        <img src="upload/nofile.jpg" height="80" style="text-align: left;">
                    </c:when>
                    <c:otherwise>
                        <img src="/upload/${board.savefilename}" height="80" style="text-align: left;">
                    </c:otherwise>
                </c:choose>&nbsp;&nbsp;
            </div>
            <input type="hidden" name="oldimage" value="${board.image}" />
            <input type="hidden" name="oldsavefilename" value="${board.savefilename}" />

            <div class="field">
                <input type="submit" value="수정완료" onClick="return updateBoardCheck('${board.pass}')" />
                <input type="button" value="되돌아가기" onClick="location.href='boardViewWithoutCnt?num=${board.num}'" />
            </div>
            <input type="hidden" name="num" value="${board.num}" />
        </form>
    </div>
</div>
</body>
</html>
