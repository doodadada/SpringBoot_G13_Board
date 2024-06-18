function idcheck(){
    //최초로 입력된 아이디가 없으면 "아이디를 먼저 입력하세요"라는 메세지와 함께 팝업창없이 되돌아갑니다
    if( document.join.userid.value==''){
        alert("아이디를 먼저 입력하세요");
        document.join.userid.focus();
        return;
    }
    var inputid = document.join.userid.value;
    var opt="toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=500, height=200";
    // inputid 에 저장된 아이디에 사용불가능/가능 을 조회해서 결과와 함께 페이지에 보여줄 예정
    window.open("idcheck?userid="+inputid , 'idcheck', opt);
}