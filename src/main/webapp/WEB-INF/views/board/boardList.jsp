<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/board.css">
    <script type="text/javascript">
        function withDraw() {
            var ans = confirm("정말로 탈퇴하시겠습니까?");
            if (ans) location.href = "deleteMember";
        }
    </script>
</head>
<body>
<div id="main_container">
    <h2>게시글 리스트</h2>
    <div class="logininfo">
        <div class="login">
            ${loginUser.name}(${loginUser.userid})님이 로그인하셨습니다
            <input type="button" value="회원정보수정" onClick="location.href='updateMemberForm'"/>
            <input type="button" value="로그아웃" onClick="location.href='logout'"/>
            <input type="button" value="회원탈퇴" onClick="withDraw();"/>
        </div>
        <div class="writebutton">
            <input type="button" value="게시글 등록" onClick="location.href='insertBoardForm'"/>
        </div>
    </div>
    <div class="board">
        <div class="title_row">
            <div class="title_col">번호</div>
            <div class="title_col">제목</div>
            <div class="title_col">작성자</div>
            <div class="title_col">작성일</div>
            <div class="title_col">조회수</div>
        </div>
        <c:forEach items="${boardList}" var="board">
            <div class="row">
                <div class="col">${board.num}</div>
                <div class="col">
                    <a style="text-decoration:none" href="boardView?num=${board.num}">
                            ${board.title}
                    </a>&nbsp;
                    <c:if test="${board.replycnt>0}">
                        <span style="color:red; font-weight:bold">[${board.replycnt}]</span>
                    </c:if>
                    <c:if test="${not empty board.savefilename}">
                        <span style="color:blue; font-weight:bold;font-size:90%">[img]</span>
                    </c:if>

                </div>
                <div class="col">${board.userid}</div>
                <div class="col"><fmt:formatDate value="${board.writedate}"/></div>
                <div class="col">${board.readcount}</div>
            </div>
        </c:forEach>
    </div>

    <div class="paging">
        <c:if test="${paging.prev}">
            <a href="boardlist?page=${paging.beginPage-1}">◀</a>&nbsp;
        </c:if>

        <c:forEach begin="${paging.beginPage}" end="${paging.endPage}" var="index">
            <c:if test="${paging.page == index}">
                <span style="color:red">&nbsp;${index}&nbsp;</span>
            </c:if>
            <c:if test="${paging.page != index}">
                <a href="boardlist?page=${index}">&nbsp;${index}&nbsp;</a>
            </c:if>
        </c:forEach>

        <c:if test="${paging.next}">
            <a href="boardlist?page=${paging.endPage+1}">▶</a>
        </c:if>
    </div>
</div>
</body>
</html>
