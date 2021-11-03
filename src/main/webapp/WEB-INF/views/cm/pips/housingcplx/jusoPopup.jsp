<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
	request.setCharacterEncoding("UTF-8");  //한글깨지면 주석제거
	//request.setCharacterEncoding("EUC-KR");  //해당시스템의 인코딩타입이 EUC-KR일경우에
	String inputYn = StringEscapeUtils.escapeHtml4(request.getParameter("inputYn"));
	String roadFullAddr = StringEscapeUtils.escapeHtml4(request.getParameter("roadFullAddr"));
	String roadAddrPart1 = StringEscapeUtils.escapeHtml4(request.getParameter("roadAddrPart1"));
	String roadAddrPart2 = StringEscapeUtils.escapeHtml4(request.getParameter("roadAddrPart2"));
	String engAddr = StringEscapeUtils.escapeHtml4(request.getParameter("engAddr"));
	String jibunAddr = StringEscapeUtils.escapeHtml4(request.getParameter("jibunAddr"));
	String zipNo = StringEscapeUtils.escapeHtml4(request.getParameter("zipNo"));
	String addrDetail = StringEscapeUtils.escapeHtml4(request.getParameter("addrDetail"));
	String admCd    = StringEscapeUtils.escapeHtml4(request.getParameter("admCd"));
	String rnMgtSn = StringEscapeUtils.escapeHtml4(request.getParameter("rnMgtSn"));
	String bdMgtSn  = StringEscapeUtils.escapeHtml4(request.getParameter("bdMgtSn"));
	String detBdNmList  = StringEscapeUtils.escapeHtml4(request.getParameter("detBdNmList"));
	String bdNm  = StringEscapeUtils.escapeHtml4(request.getParameter("bdNm"));
	String bdKdcd  = StringEscapeUtils.escapeHtml4(request.getParameter("bdKdcd"));
	String siNm  = StringEscapeUtils.escapeHtml4(request.getParameter("siNm"));
	String sggNm  = StringEscapeUtils.escapeHtml4(request.getParameter("sggNm"));
	String emdNm  = StringEscapeUtils.escapeHtml4(request.getParameter("emdNm"));
	String liNm  = StringEscapeUtils.escapeHtml4(request.getParameter("liNm"));
	String rn  = StringEscapeUtils.escapeHtml4(request.getParameter("rn"));
	String udrtYn  = StringEscapeUtils.escapeHtml4(request.getParameter("udrtYn"));
	String buldMnnm  = StringEscapeUtils.escapeHtml4(request.getParameter("buldMnnm"));
	String buldSlno  = StringEscapeUtils.escapeHtml4(request.getParameter("buldSlno"));
	String mtYn  = StringEscapeUtils.escapeHtml4(request.getParameter("mtYn"));
	String lnbrMnnm  = StringEscapeUtils.escapeHtml4(request.getParameter("lnbrMnnm"));
	String lnbrSlno  = StringEscapeUtils.escapeHtml4(request.getParameter("lnbrSlno"));
	String emdNo  = StringEscapeUtils.escapeHtml4(request.getParameter("emdNo"));
	String entX  = StringEscapeUtils.escapeHtml4(request.getParameter("entX"));
	String entY  = StringEscapeUtils.escapeHtml4(request.getParameter("entY"));
%>
<script language="javascript">
function init(){
	var url = location.href;
	var confmKey = '<c:out value="${svcKeyCd}"/>';//승인키
	var resultType = "4"; // 도로명주소 검색결과 화면 출력유형, 1 : 도로명, 2 : 도로명+지번, 3 : 도로명+상세건물명, 4 : 도로명+지번+상세건물명
	var inputYn= "<%=inputYn%>";
	if(inputYn != "Y"){
		document.form.confmKey.value = confmKey;
		document.form.returnUrl.value = url;
		document.form.resultType.value = resultType;
		document.form.action="http://www.juso.go.kr/addrlink/addrCoordUrl.do"; // 인터넷망
		document.form.submit();
	}else{
		opener.jusoCallBack("<%=roadFullAddr%>","<%=roadAddrPart1%>","<%=addrDetail%>", "<%=roadAddrPart2%>","<%=engAddr%>"
			, "<%=jibunAddr%>","<%=zipNo%>", "<%=admCd%>", "<%=rnMgtSn%>", "<%=bdMgtSn%>", "<%=detBdNmList%>"
			, "<%=bdNm%>", "<%=bdKdcd%>", "<%=siNm%>", "<%=sggNm%>", "<%=emdNm%>", "<%=liNm%>", "<%=rn%>", "<%=udrtYn%>"
			, "<%=buldMnnm%>", "<%=buldSlno%>", "<%=mtYn%>", "<%=lnbrMnnm%>", "<%=lnbrSlno%>", "<%=emdNo%>", "<%=entX%>", "<%=entY%>");
		window.close();
	}
}
</script>
<body onload="init();">
    <form id="form" name="form" method="post">
        <input type="hidden" id="confmKey" name="confmKey" value=""/>
        <input type="hidden" id="returnUrl" name="returnUrl" value=""/>
        <input type="hidden" id="resultType" name="resultType" value=""/>
    </form>
</body>