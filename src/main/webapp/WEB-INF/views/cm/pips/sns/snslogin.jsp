<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/common2.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/ui-style.css" />">
<meta name="google-site-verification" content="AVXFPc7fAdpQXM0l6OUn9JTwCN93KzEc6Vn4BN4jpWU" />
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/gasparesganga-jquery-loading-overlay@2.1.7/dist/loadingoverlay.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<style>
    .login-or-new {
      position: inherit;
      margin: 30px 0 25px;
      font-size: 13px;
      line-height: 19px;
      color: rgba(22, 22, 22, 0.34);
      text-align: center;
      border-bottom: 1px solid #ddd;
    }
</style>


<%
    String cookieParam = request.getParameter("cookieParam");
%>
<script type="text/javascript">
    //var pathname = window.location.pathname; // Returns path only (/path/example.html)
    //var origin   = window.location.origin;   // Returns base URL (https://example.com)
    var startUri      = window.location.href;     // Returns full URL (https://example.com/path/example.html)

    var cookieParam = "<%=cookieParam%>";

    //console.log(startUri);

    $(document).ready(function(){
        // Show full page LoadingOverlay
        $.LoadingOverlay("show");

        // Hide it after 3 seconds
        setTimeout(function(){
            $.LoadingOverlay("hide");
        }, 3000);
        setCookie("startUri", startUri, 60);

        if (getCookie("loginType") && cookieParam == "Y") { // getCookie함수로 id라는 이름의 쿠키를 불러와서 있을경우

            $("#snsType").val(getCookie("loginType"));
            $("#text_idh").val(getCookie("loginId"));
            $("#text_nmh").val(getCookie("loginPwd"));

            if(getCookie("loginType") == "NORMAL"){
                $("#sns_login").submit();
            } else {
                location.href = '/aispeaker/oauth?snsType=' + getCookie("loginType")
            }

        } else if(cookieParam == "N") {
            setCookie("loginType", "", 0)
            setCookie("loginId", "", 0)
            setCookie("loginPwd", "", 0)
        }
    });

    function setCookie(name, value, expiredays) //쿠키 저장함수
    {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    }

    function getCookie(Name) { // 쿠키 불러오는 함수
        var search = Name + "=";
        if (document.cookie.length > 0) { // if there are any cookies
            offset = document.cookie.indexOf(search);
            if (offset != -1) { // if cookie exists
                offset += search.length; // set index of beginning of value
                end = document.cookie.indexOf(";", offset); // set index of end of cookie value
                if (end == -1)
                    end = document.cookie.length;
                return unescape(document.cookie.substring(offset, end));
            }
        }
    }


    function snsLogin(param){
        if(param == "" || param == undefined) {
            $("#snsType").val("NORMAL");
            $("#text_idh").val($("#text_id").val());
            $("#text_nmh").val($("#text_nm").val());
            if(cookieParam == "Y") {
                setCookie("loginType", $("#snsType").val(), 60);
                setCookie("loginId", $("#text_idh").val(), 60);
                setCookie("loginPwd", $("#text_nmh").val(), 60);

                setCookie("startUri", startUri, 60);
            } else {
                setCookie("loginType", "", 0);
                setCookie("loginId", "", 0);
                setCookie("loginPwd", "", 0);
            }

            $("#sns_login").submit();

        } else {
            $("#snsType").val(param);
            if(cookieParam == "Y") {
                setCookie("loginType", $("#snsType").val(), 60);
                //setCookie("loginId", $("#text_idh").val(), 60);
                //setCookie("loginPwd", $("#text_nmh").val(), 60);

                setCookie("startUri", startUri, 60);
            } else {
                setCookie("loginType", "", 0);
                setCookie("loginId", "", 0);
                setCookie("loginPwd", "", 0);
            }
            location.href = '/aispeaker/oauth?snsType=' + param
        }
    }
</script>
    <div id="wrap" style="padding-top: 0;">
      <div class="layout-wrap" style="padding-top: 0;">
        <div class="layout-wrap__inner" style="padding-top: 0;">
          <div class="login-wrap" style="padding-top: 0;">
            <div class="login-head">
              <p class="login-head__text">스마트 라이프의 시작</p>
              <h1 class="login-head__logo">
                <span class="for-a11y">PUOGIO</span>
              </h1>
            </div>
            <div class="portal-login">
              <ul class="portal-login__list">
                <li class="portal-login__item portal-login__item--kakao">
                  <a href="javascript:snsLogin('kakao')" class="portal-login__link">
                    <span class="portal-login__text">카카오로 로그인</span>
                  </a>
                </li>
                <li class="portal-login__item portal-login__item--naver">
                  <a href="javascript:snsLogin('naver')" class="portal-login__link">
                    <span class="portal-login__text">네이버로 로그인</span>
                  </a>
                </li>
                <li class="portal-login__item portal-login__item--google">
                  <a href="javascript:snsLogin('google')" class="portal-login__link">
                    <span class="portal-login__text">구글로 로그인</span>
                  </a>
                </li>
              </ul>
            </div>
            <p class="login-or-new"></p>
            <h6 class="form-signin-heading" style="margin-top:20px">■ 푸르지오 스마트홈 아이디로 로그인</h6>
            <form action="">
              <div class="login-form">
                <div class="ui-input-block">
                  <input type="text" title="아이디" placeholder="아이디" class="ui-input" id="text_id" name="text_id"/>
                </div>
                <div class="ui-input-block">
                  <input type="password" title="비밀번호" placeholder="비밀번호" class="ui-input" id="text_nm" name="text_nm"/>
                </div>
                <button type="button" class="ui-button ui-basic-button" onclick="javascript:snsLogin();">로그인</button>

                <div style="color:red;bottom-top:5px">※ 해당 제휴 서비스를 이용시 사용했던 아이디로 로그인해주시기 바랍니다.</div>
                <div style="color:red;bottom-top:5px">※ 다른 아이디로 로그인시 제휴 서비스 내에서 별도의 아이디가 생성되어 기존의 가입 정보와 혼동이 발생될 수 있습니다.</div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>


    <form:form id="sns_login" action="/aispeaker/oauth" method="get">
        <input type="text" id="snsType" name="snsType" style="width:0;height:0;visibility:hidden"/>
        <input type="text" id="text_idh" name="text_idh" style="width:0;height:0;visibility:hidden"/>
        <input type="text" id="text_nmh" name="text_nmh" style="width:0;height:0;visibility:hidden"/>
    </form:form>



