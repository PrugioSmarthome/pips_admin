<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<div id="gnb">
    <c:set var="requestUri" value="${requestScope['javax.servlet.forward.request_uri']}"/>
    <c:set var="requestPaths" value="${fn:split(requestUri, '/')}"/>
    <c:set var="requestTarget" value="${requestPaths[1]}${requestPaths[2]}"/>
    <ul>
    <c:forEach items="${sessionUserMenuList}" var="userMenuList" varStatus="status">

        <c:if test="${userMenuList.stepNo eq 1}">
            <li id="top_menu"><p><a href="#" id="top_a_tag_${status.index}" value="${userMenuList.authType}"><c:out value="${userMenuList.menuName}" /></a></p></li>
        </c:if>
        <c:if test="${userMenuList.stepNo eq 2}">
            <c:set var="viewUri" value="${userMenuList.viewPath}"/>
            <c:set var="viewPaths" value="${fn:split(viewUri, '/')}"/>
            <c:set var="viewTarget" value="${viewPaths[1]}${viewPaths[2]}"/>
            <c:choose>
                <c:when test="${requestTarget eq viewTarget}">
                    <li id="sub_menu_link"><a href="<c:out value="${userMenuList.viewPath}"/>"/><c:out value="${userMenuList.menuName}" /></a></li>
                </c:when>
                <c:otherwise>
                    <li id="sub_menu"><a href="<c:out value="${userMenuList.viewPath}"/>"/><c:out value="${userMenuList.menuName}" /></a></li>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:forEach>
    </ul>
</div>
