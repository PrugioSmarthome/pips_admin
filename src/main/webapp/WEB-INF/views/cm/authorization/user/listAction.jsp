<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/ntels.tld" prefix="ntels" %>

<script type="text/javascript">
//Ajax로 첫 화면의 데이터 호출
$(document).ready(function() {
	document.onselectstart = function () { return false; };
	
	//초기 페이지당 보여줄 갯수 선택창 view 숨김 설정
	$("#selectPerPage").hide();
	//페이지당 보여줄 갯수 선택창 view 여부 처리
	$("#currentPerPage").click(function() {
		$("#selectPerPage").toggle();	
	});
});

//페이지당 보여줄 갯수 선택
function setPerPage(perPage) {
	$("#page").val("1");
	$("#perPage").val(perPage);	//실제 setting.
	$("#selectPageSize").text(perPage + '<spring:message code="label.common.perpage.text"/>'); //화면에 Display.
	
	goSearch();
}
</script>

		<ntels:perPageControl totalCount="${listUser.totalCount}" perPage="${listUser.perPage}" page="${listUser.page}"><spring:message code="label.common.perpage.normal.text" /></ntels:perPageControl>
		 
		<table class="view">
			<caption></caption>
			<thead>
			   <tr>
					<th><spring:message code="label.user.userId.text" /></th>
					<th><spring:message code="label.user.userName.text" /></th>
					<th><spring:message code="label.user.userGroupId.text" /></th>
					<th><spring:message code="label.user.deptName.text" /></th>
					<th><spring:message code="label.user.empNo.text" /></th>
					<th><spring:message code="label.user.telNo.text" /></th>
					<th><spring:message code="label.user.ipBandwidth.text" /></th>
					<th><spring:message code="label.user.passwordDueDate.text" /></th>
					<th><spring:message code="label.user.passwordChangePeriod.text" /></th>
					<th><spring:message code="label.user.accountLock.text" /></th>
			   </tr>
			</thead>
	
			<c:if test="${not empty listUser.lists}">
			<tbody>
			
			<c:forEach items="${listUser.lists}" var="user" varStatus="status">
			   <tr <ntels:auth auth="${menuAuth}"> onDblclick="goUpdatePage('${user.userId}');" style="cursor:pointer;"</ntels:auth> class="odd">
					<th scope="row">${user.userId}</th>
					<td align="center">${user.userName}</td>
					<td align="center">${user.userGroupName}</td>
					<td align="center">${user.deptName}</td>
					<td align="center">${user.empNo}</td>
					<td align="center">${user.telNo}</td>
					<td align="center">${user.ipBandwidth}</td>
					<td align="center">${user.passwordDueDate}</td>
					<td align="center">${user.passwordChangePeriod}</td>
					<td align="center">${user.accountLock}</td>
			   </tr>
			</c:forEach>
			</tbody>
			</c:if>
		</table>

<!-- ajax사용 페이징 -->
<ntels:paging isAjax="true" ajaxMethod="goPostPage" ajaxUrl="listAction.ajax" ajaxTarget="#userTable" active="${listUser.page}"  totalCount="${listUser.totalCount}" perPage="${listUser.perPage}" perSize="${listUser.perSize}" parameterKeys="getObj('userGroupIdC')"/>
