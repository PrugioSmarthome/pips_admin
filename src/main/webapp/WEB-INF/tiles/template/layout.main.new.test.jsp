<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="ko-KR">
<head>
<meta name="viewport"
	content="target-densitydpi=device-dpi, width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="label.title" /></title>

<tiles:insertAttribute name="css" ignore="true" />
<tiles:insertAttribute name="js" ignore="true" />

<link rel="stylesheet" type="text/css" href="<c:url value="/styles/main.css" />">
<script type="text/javascript">
$(function() {
	$("#loadZone").hide();
});
</script>
</head>

<body>
<form name="frmMenuHandle"></form>
<tiles:insertAttribute name="header" />
<div class="container-fluid">
    <div class="row">
        <tiles:insertAttribute name="left-body" />
        <tiles:insertAttribute name="right-body" />
    </div>
</div>
</body>
</html>
