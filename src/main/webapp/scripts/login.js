function login(){
    var f = document.formLogin;

    if(f.text_id.value == "") {
        alert("아이디를 입력하십시요");

        f.text_id.focus();
        return;
    }

    if(f.text_nm.value == "") {
        alert("비밀번호를 입력하십시요");
        f.text_nm.focus();
        return;
    }

    //f.submit();
    var loginCheck = "${loginCheck}";

    var param = new Object();
    param.text_id = $("#text_id").val();
    param.text_nm = $("#text_nm").val();
    param.useLanguage = 'KR-ko';

    $.ajax({
        url:  '/cm/loginAction',
        type: 'POST',
        data: param,
        //timeout: 1000,
        success: function(data){
            if(data == "GO_MAIN") {
                goMenuPage("/cm/housingcplx/info/list");
            } else if(data == "INIT_ACCOUNT") {
                goMenuPage("/cm/authorization/passwd/changePassword");
            } else if(data == "ERROR_INPUT_NULL") {
                alert('아이디 또는 비밀번호를 입력하십시요.');
            } else if(data == "LOGIN_FAIL") {
                alert('아이디 또는 비밀번호가 일치하지 않습니다.');
            } else if(data == "FAIL_PASS_IP_BANDWIDTH") {
                alert('접근할 수 없습니다');
            } else if(data == "OVER_LOGIN_FAIL_COUNT") {
                alert('로그인 실패 횟수를 초과하였습니다. 계정이 잠겨서 로그인할 수 없습니다. 관리자에게 문의하십시요.');
            } else if(data == "LOCK_ACCOUNT") {
                alert('로그인 실패 횟수를 초과하여 로그인할 수 없습니다. 관리자에게 문의하십시요.');
            }
        },
        error: function(e){
            console.log(e.responseText.trim());
            viewLayer(e.responseText.trim());
            alert("로그인 에러 관리자에게 문의하십시오.");
        },
        complete: function() {
        }
    });
}