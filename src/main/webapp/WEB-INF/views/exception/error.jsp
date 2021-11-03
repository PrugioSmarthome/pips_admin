<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.daewooenc.pips.admin.core.util.SessionUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="format-detection" content="telephone=no, address=no, email=no" />
<title>푸르지오 스마트홈 플랫폼 앱 관리자 서비스 500 에러페이지</title>
<link rel="icon" href="https://www.prugio.com/asset/images/common/favicon.ico">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-datepicker.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/all.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery-ui.min.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/common2.css" />">
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/ui-style.css" />">
<script src="<c:url value="/scripts/jquery/jquery-3.4.1.min.js" />"></script>
<script src="<c:url value="/scripts/jquery/jquery-ui.min.js" />"></script>
<script src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/scripts/jquery/jquery.dynatree.js" />"></script>
<script src="<c:url value="/scripts/jquery/jquery-3.4.1.min.js" />"></script>
<script src="<c:url value="/scripts/jquery/bootstrap-datepicker.min.js" />"></script>
<script src="<c:url value="/scripts/jquery/bootstrap-datepicker.ko.min.js" />"></script>
<script src="<c:url value="/scripts/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/scripts/common_ui.js" />"></script>
<script type="text/javascript">
    $(document).ready(function(){
        if (getCookie("loginId")) { // getCookie함수로 id라는 이름의 쿠키를 불러와서 있을경우
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

    function back() {
        window.history.back();
    }

    function go_home(){
        var origin = window.location.origin;
        var targetUrl = origin + "/cm/housingcplx/info/list";

        window.location.href = targetUrl;
    }
</script>
  <head>
    <title>PUOGIO</title>

    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

  </head>
  <body>
    <div id="wrap">
      <div class="layout-wrap">
        <div class="layout-wrap__inner">
          <div class="error-wrap">
            <div class="error-icon"></div>
            <p class="error-title">${resultMsg}</p>
            <p class="error-text">${resultMsg2}</p>

            <a href="javascript:back();" class="ui-button ui-basic-button">이전 페이지로</a>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
