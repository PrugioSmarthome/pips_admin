<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html lang="ko-KR">
<head>
	<meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, user-scalable=yes, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<title><spring:message code="label.title" /></title>

	<link rel="stylesheet" href="<c:url value="/styles/basic.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/styles/jquery-ui-1.11.3.css" />" type="text/css" />
	<tiles:insertAttribute name="js" ignore="true" />
</head>

<body>
<form name="frmMenuHandle"></form>

<!--[s : logo]-->
<div id="layoutLogo">
	<tiles:insertAttribute name="header" ignore="true" />
</div>
<!--[e : logo]-->


<!--[s : page]-->
<div id="layoutPage">
	<!--[s : menu]-->
	<div id="layoutMenu">
		<tiles:insertAttribute name="left" ignore="true" />
	</div>
	<!--[e : menu]-->


	<!--[s : contents]-->
	<div id="layoutContents">
		<tiles:insertAttribute name="body" />
	</div>
	<!--[e : contents]-->


	<!--[s : links]-->
	<div id="layoutLinks">
		<tiles:insertAttribute name="right"  ignore="true" />
	</div>
	<!--[e : links]-->
</div>
<!--[e : page]-->


<!--[s : copy]-->
<div id="layoutCopy">
	<tiles:insertAttribute name="footer" />
</div>
<!--[e : copy]-->
</body>
</html>
