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
<title>푸르지오 스마트홈 플랫폼 앱 관리자 서비스 403 에러페이지</title>
<link rel="icon" href="https://www.prugio.com/asset/images/common/favicon.ico">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-datepicker.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/all.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery-ui.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
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
    function back() {
        window.history.back();
    }

    function go_home(){
        var origin = window.location.origin;
        var targetUrl = origin + "/cm/housingcplx/info/list";

        window.location.href = targetUrl;
    }
</script>
</head>
<body>
	<div class="error_wrap">
		<div class="error">
			<div class="ico"><i class="fas fa-exclamation-triangle"></i></div>
			<div class="tit">요청하신 페이지를 표시할 수 없습니다.</div>
			<div class="txt">잘못된 경로로 접근하셨거나,<br />현재 운영되지 않는 페이지입니다.</div>
			<div class="btn_area">
				<a href="#" class="btn btn-gray w160" onclick="back();">이전 페이지로</a>
				<a href="#" class="btn btn-bluegreen w160" onclick="go_home();">홈페이지 바로가기</a>
			</div>
		</div>
	</div>
</body>
</html>