<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.18/datatables.min.css"/>
<style>
    .caution{
        color:red;
        font-size:10px;
    }
</style>
<script type="text/javascript">
    //취소버튼
    function btncancel(){
        location.href = "/cm/login";
    }
    //확인버튼
    function btnsubmit(){
        if(isValid() == false){
            return;
        }

        $("#resetPassword").submit();
    }
    function isValid(){
        var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{9,15}$/;
        var newpw = $("#newPassword").val();

        if($("#newPassword").val() == ""){
            $('#newPassworderror').css('visibility', 'visible');
            document.getElementById("newPassworderror").innerHTML="새 비밀번호를 입력해 주세요";
            return false;
        }else{
            $('#newPassworderror').css('visibility', 'hidden');
        }
        if(!reg.test(newpw)){
            $('#newPassworderror').css('visibility', 'visible');
            document.getElementById("newPassworderror").innerHTML="비밀번호 생성 규칙에 맞게 입력해 주세요";
            return false;
        }else{
            $('#newPassworderror').css('visibility', 'hidden');
        }
        if($("#newPasswordRe").val() == ""){
            $('#newPasswordReerror').css('visibility', 'visible');
            document.getElementById("newPasswordReerror").innerHTML="새 비밀번호 확인을 입력해 주세요";
            return false;
        }else{
            $('#newPasswordReerror').css('visibility', 'hidden');
        }
        if($("#newPassword").val() != $("#newPasswordRe").val()){
            $('#newPasswordReerror').css('visibility', 'visible');
            document.getElementById("newPasswordReerror").innerHTML="새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.";
            return false;
        }else{
            $('#newPasswordReerror').css('visibility', 'hidden');
        }
        return true;
    }
</script>

<form:form action="/cm/authorization/passwd/resetPasswordAction" id="resetPassword" name="resetPassword" commandName="resetPassword" method="post">
    <div class="login_wrap">
        <div class="logo">
            <div><img src="/images/h1_logo.png" alt="PRUGIO" /></div>
            <div>스마트홈 플랫폼 앱 Admin</div>
        </div>
        <div class="in pt100">
            <h1 class="tit">비밀번호 재설정</h1>
            <ul class="inp_list">
                <li>
                    <h2 class="tit">새 비밀번호</h2>
                    <div class="inp"><input type="password" class="form-control" id="newPassword" name="newPassword" value="${resetPassword.newPassword}"/></div>
                    <span id="newPassworderror" class="caution" style="visibility:hidden"></span>
                    <div class="txt_noti">9~15자의 대문자, 소문자, 숫자, 특수문자 모두 사용하세요.<br />(허용 특수문자 ~ ! @ # $ % - + )</div>
                </li>
                <li>
                    <h2 class="tit">새 비밀번호 확인</h2>
                    <div class="inp"><input type="password" class="form-control" id="newPasswordRe" name="newPasswordRe" value="${resetPassword.newPasswordRe}"/></div>
                    <span id="newPasswordReerror" class="caution" style="visibility:hidden"></span>
                </li>
            </ul>
            <div class="btn_area clearfix">
                <div class="float-right">
                    <input class="btn btn-gray" type="button" value="취소" onclick="btncancel();"/>
                    <input class="btn btn-bluegreen" type="button" value="확인" onclick="btnsubmit();"/>
                    <span class="caution" style="visibility:hidden"><input type="text" class="form-control" id="userId" name="userId" value="${userId}"/></span>
                    </span>
                </div>
            </div>
        </div>
    </div>
</form:form>