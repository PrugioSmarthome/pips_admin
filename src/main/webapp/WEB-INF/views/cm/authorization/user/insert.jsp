<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tag/ntels.tld" prefix="ntels" %>

<!-- s:날짜 Picker -->
<script type="text/javascript">
$(document).ready(function() {
	$.datepicker.setDefaults($.datepicker.regional['ko']);
	$( ".date" ).datepicker();
});
</script>
<!-- e:날짜 Picker -->

<script type="text/javascript">
//Ajax로 첫 화면의 데이터 호출
//화면 먼저 보이고 데이터를 불러야 사용자가 덜 답답해 함
$(document).ready(function(){
	init();

	//버튼 클릭시
	$("#btn_ok").click(function() {
		goOK();
	});

	$("#btn_cancel").click(function() {
		goCancel();
	});
});

function init(){
	var userGroupId = "${user.userGroupId}";
	console.log("user.userGroupId: " + userGroupId);

//	$("#userGroupId > option[value = " + userGroupId + "]").attr("selected", "ture");
}

function goOK() {
	if($("#userId").val() == "") {
		alert("<spring:message code="NotEmpty.user.userId"/>");
		$("#userId").focus();
		return;
	}

	if(isValid() == false){
		return;
	}

	$("#user").submit();
}


function isValid(){
	if(isValidPassword($("#password").val()) == false) {
		alert(ERR_MSG);
		$("#password").focus();
		return false;
	}

	if(isValidPassword($("#passwordRe").val()) == false) {
		alert(ERR_MSG);
		$("#passwordRe").focus();
		return false;
	}

	if($("#password").val() == $("#passwordRe").val());
	else{
		alert("'새 비밀번호'와 '새 비밀번호 확인'의 값이 다릅니다.");
		$("#passwordRe").focus();
		return false;
	}

	return true;
}


function goCancel() {
	$("#user").attr("action", "/cm/authorization/user/list");
	$("#user").submit();
}

function checkEmail(strEmail) {
	var arrMatch = strEmail.match(/^(\".*\"|[A-Za-z0-9_-]([A-Za-z0-9_-]|[\+\.])*)@(\[\d{1,3}(\.\d{1,3}){3}]|[A-Za-z0-9][A-Za-z0-9_-]*(\.[A-Za-z0-9][A-Za-z0-9_-]*)+)$/);

	if(arrMatch == null)
		return false;

	return true;
}
</script>
<div id="container">
      <!--s : content-->
      <section>
         <article>
			<form:form method="post" action="/cm/authorization/user/insertAction" name="user" commandName="user">
			<input id="page" name="page" value="${user.page}" type="hidden">
			<input id="perPage" name="perPage" value="${user.perPage}" type="hidden">
			<input id="userGroupIdC" name="userGroupIdC" value="${user.userGroupIdC}" type="hidden">
            <header>
               <h1><spring:message code="label.user.insert.text"/></h1>
               <div class="icons">
               		<%-- <ntels:auth auth="${menuAuth}"> --%>
					<a href="#" id="btn_ok" class="ico_save" title='<spring:message code="label.common.icon.save.text"/>'><spring:message code="label.common.icon.save.text"/></a>
               		<%-- </ntels:auth> --%>
					<a href="#" id="btn_cancel" class="ico_back" title='<spring:message code="label.common.icon.list.text"/>'><spring:message code="label.common.icon.list.text"/></a>
               </div>
            </header>

			<c:if test="${inputError != null}">
			<p class="caution">${inputError}</p>
			</c:if>

            <table class="modif">
            <colgroup>
            <col width="20%">
            <col width="30%">
            <col width="20%">
            <col width="30%">
            </colgroup>
            <tr>
               <th><label><strong>*</strong><spring:message code="label.user.userId.text"/></label></th>
               <td colspan="3">
					<input id="userId" name="userId" value="${user.userId}" placeholder="<spring:message code="label.user.userId.text"/>" type="text" maxlength="50">
					<form:errors path="userId" class="caution"/>
               </td>
            </tr>
            <tr>
               <th><label><strong>*</strong><spring:message code="label.user.userName.text"/></label></th>
               <td colspan="3">
               		<input id="userName" name="userName" value="${user.userName}" placeholder="<spring:message code="label.user.userName.text"/>" type="text" maxlength="30">
               		<form:errors path="userName" class="caution"/>
               </td>
            </tr>
            <tr>
               <th><label><strong>*</strong><spring:message code="label.user.userGroupId.text"/></label></th>
               <td colspan="3">
	               	<select id="userGroupId" name="userGroupId">
						<option value=""><spring:message code="label.userGroup.user_group.select.text"/></option>
						<c:forEach items="${listUserGroup}" var="userGroup" varStatus="status">
						<option value="${userGroup.userGroupId}">${userGroup.userGroupName}</option>
						</c:forEach>
					</select>
					<form:errors path="userGroupId" class="caution"/>
               </td>
            </tr>
            <tr>
               <th><label><strong>*</strong><spring:message code="label.user.password.text"/></label></th>
               <td>
               	  <input id="password" name="password" value="${user.password}" placeholder="<spring:message code="label.user.password.text"/>" type="password" maxlength="20">
               	  <form:errors path="password" class="caution"/>
               </td>
               <th><label><strong>*</strong><spring:message code="label.user.passwordRe.text"/></label></th>
               <td>
               	  <input id="passwordRe" name="passwordRe" value="${user.passwordRe}" placeholder="<spring:message code="label.user.passwordRe.text"/>" type="password" maxlength="20">
               	  <form:errors path="passwordRe" class="caution"/>
               </td>
            </tr>
            <tr>
               <th><label><spring:message code="label.user.deptName.text"/></label></th>
               <td>
               	  <input id="deptName" name="deptName" value="${user.deptName}" placeholder="<spring:message code="label.user.deptName.text"/>" type="text" maxlength="30">
               </td>
               <th><label><spring:message code="label.user.empNo.text"/></label></th>
               <td>
               	  <input id="empNo" name="empNo" value="${user.empNo}" placeholder="<spring:message code="label.user.empNo.text"/>" type="text" maxlength="20">
               </td>
            </tr>
            <tr>
               <th><label><spring:message code="label.user.telNo.text"/></label></th>
               <td>
               	  <input id="telNo" name="telNo" value="${user.telNo}" placeholder="<spring:message code="label.user.telNo.text"/>" type="text" maxlength="20">
               </td>
               <th><label><spring:message code="label.user.webMail.text"/></label></th>
               <td>
               	  <input id="webMail" name="webMail" value="${user.webMail}" placeholder="<spring:message code="label.user.webMail.text"/>" type="email" maxlength="20">
               </td>
            </tr>
            <tr>
               <th><label><strong>*</strong><spring:message code="label.user.ipBandwidth.text"/></label></th>
               <td colspan="3">
               	  <input id="ipBandwidth" name="ipBandwidth"
               	  		 value="<c:if test="${user.ipBandwidth == null}">*.*.*.*</c:if><c:if test="${user.ipBandwidth != null}">${user.ipBandwidth}</c:if>"
               	  		 placeholder="<spring:message code="label.user.ipBandwidth.text"/>" maxlength="20">
               	  <form:errors path="ipBandwidth" class="caution"/>
               </td>
            </tr>
            <tr>
               <th><label><strong>*</strong><spring:message code="label.user.passwordDueDate.text"/></label></th>
               <td>
               	  <input id="passwordDueDate" name="passwordDueDate"
               	  		 value="<c:if test="${user.passwordDueDate != null}">${user.passwordDueDate}</c:if><c:if test="${user.passwordDueDate == null}">${passwordDueDate}</c:if>"
               	  		 placeholder="<spring:message code="label.user.passwordDueDate.text"/>" maxlength="10" class="px100 date" readonly="readonly">
               	  <a href="javascript:$('#passwordDueDate').datepicker('show');" id="btn_startDate" class="ico_cal"><img src="/images/ico_cal.gif" alt="날짜선택"></a>
               	  <form:errors path="passwordDueDate" class="caution"/>
               </td>
               <th><label><strong>*</strong><spring:message code="label.user.passwordChangePeriod.text"/></label></th>
               <td>
               	  <spring:message code="label.user.passwordChangePeriod.1.text"/> <input id="passwordChangePeriod" name="passwordChangePeriod" value="30" placeholder="<spring:message code="label.user.passwordChangePeriod.text"/>" size="3"> <spring:message code="label.user.passwordChangePeriod.2.text"/>
               	  <form:errors path="passwordChangePeriod" class="caution"/>
               </td>
            </tr>
            </table>
			</form:form>
            <!--e : 등록table-->

         </article>
      </section>
      <!--s : content-->
</div>