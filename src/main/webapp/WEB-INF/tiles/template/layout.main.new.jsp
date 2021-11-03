<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="ko-KR">
<head>
<meta name="viewport" content="width=device-width,initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="label.title" /></title>
<tiles:insertAttribute name="css" ignore="true" />
<tiles:insertAttribute name="js" ignore="true" />
</head>
<body>
<form name="frmMenuHandle"></form>
<tiles:insertAttribute name="header" />
<div id="wrap">
    <tiles:insertAttribute name="left-body" />
    <tiles:insertAttribute name="right-body" />
</div>
</body>
</html>
