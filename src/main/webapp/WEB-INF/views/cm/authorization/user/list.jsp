<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/ntels.tld" prefix="ntels" %>

<script type="text/javascript">
//Ajax로 첫 화면의 데이터 호출
//화면 먼저 보이고 데이터를 불러야 사용자가 덜 답답해 함
$(document).ready(function() {
	init();

	//버튼 클릭시
	$("#btn_search").click(function() {
		$("#page").val("1");
		goSearch();
	});
	$("#btn_add").click(function() {
		goInsertPage();
	});

	// for message alert
	var resultMsg = "${resultMsg}";
	if(resultMsg != null && resultMsg != "") {
		var param = new Object();
		param.userGroupIdC =  '${user.userGroupIdC}';
		param.page =  '${user.page}';
		param.perPage = '${user.perPage}';

		$.ajax({
		    url: 'listAction.ajax',
		    type: 'POST',
		    data:param,
		    async: true,
		    success: function(data){
		    	$('#userTable').html(data);

		    	alert("${resultMsg}");
		    },
		    error: function(e){
		        alert(e.reponseText);
		    }
		});
	} else {
		goPostPage('#userTable', 'listAction.ajax', 1);
	}
});

function init(){
	try{
		var userGroupIdC = "${user.userGroupIdC}";
    	console.log("${userGroup.userGroupIdC}: " + userGroupIdC);

		$("#userGroupIdC > option[value = " + userGroupIdC + "]").attr("selected", "true");
	}catch (e){
		console.log(e);
	}
}

function goSearch() {
	console.log("test 3333");

	var param = new Object();
	param.page =  $("#page").val();
	param.perPage =  $("#perPage").val();
	param.userGroupIdC =  $("#userGroupIdC").val();

	$.post('listAction.ajax', param, function(data) {
		$('#userTable').html(data);
	});
}

function goInsertPage(){
	$("#user").attr("action", "/cm/authorization/user/insert");
	$("#user").submit();
}

function goUpdatePage(userId){
	$("#userId").val(userId);

	$("#user").attr("action", "/cm/authorization/user/update");
	$("#user").submit();
}
</script>
<div id="container">
<section id="listSection">
	<article>
	<form id="user" name="user" method="post">
	<input id="userId" name="userId" type="hidden">
		<header>
			<h1><spring:message code="label.user.list.text"/></h1>
			<div class="icons">
				<a href="#" class="ico_search" id="btn_search"><spring:message code="label.common.icon.search.text"/></a>
				<ntels:auth auth="${menuAuth}">
					<a href="#" class="ico_add" id="btn_add"><spring:message code="label.common.icon.insert.text"/></a>
				</ntels:auth>
			</div>
		</header>
		<div class="search">
			<label for="s01"><spring:message code="label.user.userGroupId.text"/>
				<select id="userGroupIdC" name="userGroupIdC">
					<option value="all"><spring:message code="label.common.selectbox.all"/></option>
					<c:forEach items="${listUserGroup}" var="userGroup" varStatus="status">
					<option value="${userGroup.userGroupId}">${userGroup.userGroupName}</option>
					</c:forEach>
				</select>
			 </label>
		</div>
		<div id="userTable"></div> <!-- 데이터는 다른 화면에서 미리 만들고 ajax로 호출함 -->
	</form>
	</article>
</section>
</div>