<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/ntels.tld" prefix="ntels" %>

<link rel="stylesheet" type="text/css" media="screen" href="/styles/jqgrid/ui.jqgrid.css" />
<script src="/scripts/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="/scripts/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>

<script type="text/javascript">
//Ajax로 첫 화면의 데이터 호출
//화면 먼저 보이고 데이터를 불러야 사용자가 덜 답답해 함
$(document).ready(function() {
	init();

	//버튼 클릭시
	$("#btn_search").click(function() {
		goSearch();
	});
	$("#btn_add").click(function() {
		goInsertPage();
	});

	var resultMsg = "${resultMsg}";
	if(resultMsg != null && resultMsg != "") {
		alert("${resultMsg}");
	} else {
		//goPostPage('#userTable', 'listAction.ajax', 1);
	}
});

function init(){
	try{
		var userGroupIdC = "${user.userGroupIdC}";
    	console.log("${userGroup.userGroupIdC}: " + userGroupIdC);

		$("#userGroupIdC > option[value = " + userGroupIdC + "]").attr("selected", "ture");
	}catch(e){
		console.log(e);
	}

	initGrid();
}

function getParam(){
	var param = new Object();
	param.page =  $("#page").val();
	param.perPage =  $("#perPage").val();
	param.userGroupIdC =  $("#userGroupIdC").val();

	return param;
}

function initGrid() {

	var param = new Object();
	param.page =  $("#page").val();
	param.perPage =  $("#perPage").val();
	param.userGroupIdC =  $("#userGroupIdC").val();

	// 그리드 초기화

	$("#userTable").jqGrid({
	   	url:'listActionJson.json?'+ $.param(getParam()),
	    mtype:"POST",
	   	datatype: "json",
	   	jsonReader : {repeatitems: false},
	   	colNames:['<spring:message code="label.user.userId.text" />',
		'<spring:message code="label.user.userName.text" />',
		'<spring:message code="label.user.userGroupId.text" />',
		'<spring:message code="label.user.deptName.text" />',
		'<spring:message code="label.user.ipBandwidth.text" />',
		'<spring:message code="label.user.passwordDueDate.text" />',
		'<spring:message code="label.user.passwordChangePeriod.text" />',
		'<spring:message code="label.user.accountLock.text" />'],
	   	colModel:[
	   		{name:'userId',index:'userId', width:60},
	   		{name:'userName',index:'userName', width:60},
	   		{name:'userGroupName',index:'userGroupName', width:60},
	   		{name:'deptName',index:'deptName', width:60},
	   		{name:'ipBandwidth',index:'ipBandwidth', width:80},
	   		{name:'passwordDueDate',index:'passwordDueDate', width:100},
	   		{name:'passwordChangePeriod',index:'passwordChangePeriod', width:100},
	   		{name:'accountLock',index:'accountLock', width:50},
	   	],
	   	rowNum:-1,
	   	height:300,
	   	rowList:[10,20,30],
	   	pager: '#pager2',
	   	sortname: 'userId',
	    viewrecords: true,
	    sortorder: "desc",
	    width:1000,
	    hidegrid: false,
	    caption:"사용자 관리",
	    ondblClickRow : function(id){
	    	alert("사용자 ID :"+$("#userTable").getRowData(id).userId);
	    	goUpdatePage($("#userTable").getRowData(id).userId);
	    }
	});

	$("#userTable").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false});
}

function goSearch(){
	var param = new Object();
	param.page =  $("#page").val();
	param.perPage =  $("#perPage").val();
	param.userGroupIdC =  $("#userGroupIdC").val();

	// 조회 조건을 변경에 따른 파라미터 재설정
    $("#userTable").jqGrid('setGridParam', {url:'listActionJson.json?'+ $.param(param)}).trigger("reloadGrid");
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
		<table id="userTable" style="width: 100%"></table> <!-- 데이터는 다른 화면에서 미리 만들고 ajax로 호출함 -->
		<div id="pager2"></div>
	</form>
	</article>
</section>
</div>

<script type="text/javascript">
<!--
//-->
</script>