<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
<!--
//Ajax로 첫 화면의 데이터 호출
//화면 먼저 보이고 데이터를 불러야 사용자가 덜 답답해 함
$(document).ready(function(){
	var param = new Object();
	param.mode = "popup";

	$.post("/cm/authorization/passwd/changePassword.ajax", param, function(data) {
		$("#changePasswordPopUp").html(data);
	});
});
//-->
</script>

<!--s : content-->
<section>
	<header><spring:message code="label.passwd.changePassword.text"/><a href="javascript:closeModal();">close</a></header>
	<article>
       <div id="changePasswordPopUp"></div>
    </article>
</section>
<!--s : content-->
