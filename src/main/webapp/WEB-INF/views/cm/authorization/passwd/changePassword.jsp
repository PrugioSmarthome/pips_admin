<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
 .caution{
    color:red;
    font-size:10px;
 }
</style>
<script type="text/javascript">

$(document).ready(function(){


});

function btn_ok_(){
    goOK();
}

function goOK() {
	if(isValid() == false){
		return;
	}

	$("#changePassword").submit();
}

function isValid(){
    var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{9,15}$/;

    var newpw = $("#newPassword").val();

    //비밀번호 유효성 체크
    if($("#currentPassword").val() == ""){
        $('#currentPassworderror').css('visibility', 'visible');
        document.getElementById("currentPassworderror").innerHTML="현재 비밀번호를 입력해 주세요";
        return false;
    }else{
        $('#currentPassworderror').css('visibility', 'hidden');
    }
    if($("#newPassword").val() == ""){
        $('#newPassworderror').css('visibility', 'visible');
        document.getElementById("newPassworderror").innerHTML="새 비밀번호 확인을 입력해 주세요";
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

    if($("#newPassword").val() !=  $("#newPasswordRe").val()){
        $('#newPasswordReerror').css('visibility', 'visible');
        document.getElementById("newPasswordReerror").innerHTML="새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.";
        return false;
    }else{
        $('#newPasswordReerror').css('visibility', 'hidden');
    }
    if($("#phonenum1").val() == "" || $("#phonenum2").val() == ""){
        $('#phoneerror').css('visibility', 'visible');
        document.getElementById("phoneerror").innerHTML="휴대폰 번호를 입력해 주세요";
        return false;
    }else{
        var tel = $("#selectphonenum").val()+$("#phonenum1").val()+$("#phonenum2").val()
        $("#telNo").val(tel);
        $('#phoneerror').css('visibility', 'hidden');
    }
    return true;
}

</script>

<form:form action="/cm/authorization/passwd/changePasswordAction" name="changePassword" commandName="changePassword" method="post">
    <div class="login_wrap">
        <div class="logo">
            <div><img src="/images/h1_logo.png" alt="PRUGIO" /></div>
            <div>스마트홈 플랫폼 앱 Admin</div>
        </div>
        <div class="in pt90">
            <h1 class="tit">비밀번호 변경</h1>
            <div class="txt1">새로 사용할 비밀번호를 변경하세요.</div>
            <ul class="inp_list">
                <li>
                    <h2 class="tit">현재 비밀번호</h2>
                    <div class="inp"><input type="password" id="currentPassword" name="currentPassword" class="form-control" value="${changePassword.currentPassword}"/></div>
                    <span id="currentPassworderror" class="caution" style="visibility:hidden"></span>
                    <form:errors path="newPassword" class="caution"/>
                </li>
                <li>
                    <h2 class="tit">새 비밀번호</h2>
                    <div class="inp"><input type="password" id="newPassword" name="newPassword" class="form-control" value="${changePassword.newPassword}"/></div>
                    <span id="newPassworderror" class="caution" style="visibility:hidden"></span>
                    <div class="txt_noti">9~15자의 대문자, 소문자, 숫자, 특수문자 모두 사용하세요.<br />(허용 특수문자 ~ ! @ # $ % - + )</div>
                </li>
                <li>
                    <h2 class="tit">새 비밀번호 확인</h2>
                    <div class="inp"><input type="password" id="newPasswordRe" name="newPasswordRe" class="form-control" value="${changePassword.newPasswordRe}"/></div>
                    <span id="newPasswordReerror" class="caution" style="visibility:hidden"></span>
                </li>
                <li>
                    <h2 class="tit">휴대폰 번호</h2>
                    <div class="inp">
                        <div class="input-group">
                            <div class="w80">
                            <select id="selectphonenum" name="selectphonenum" class="custom-select">
                                <option value="010">010</option>
                                <option value="011">011</option>
                                <option value="016">016</option>
                                <option value="017">017</option>
                                <option value="019">019</option>
                            </select>
                            </div>
                            <span class="bul_space">-</span>
                            <input type="text" id="phonenum1" name="phonenum1" class="form-control" maxlength="4"/>
                            <span class="bul_space">-</span>
                            <input type="text" id="phonenum2" name="phonenum2" class="form-control" maxlength="4"/>
                            <input id="telNo" name="telNo" value="${changePassword.telNo}" style="visibility:hidden"/>
                        </div>
                    </div>
                    <span id="phoneerror" class="caution" style="visibility:hidden"></span>
                </li>
            </ul>
            <div class="btn_area clearfix">
                <div class="float-right"><input type="button" id="btn_ok" class="btn btn-bluegreen" value="확인" onclick="btn_ok_();"/></div>
            </div>
        </div>
    </div>
</form:form>