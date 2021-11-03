<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html lang="ko-KR">
<head>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="js" />
</head>
<body>
  <div class="popWrap">
    <tiles:insertAttribute name="body" />
  </div>
</body>
</html>