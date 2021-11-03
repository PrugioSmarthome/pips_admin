<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
    function logout() {
        document.frmMenuHandle.action="<%=request.getContextPath()%>/cm/logoutAction";
        document.frmMenuHandle.method="post";
        document.frmMenuHandle.submit();
        disconnectWebSocketClient(webSocketClient);
    }

    function btnMyPageSave() {
        console.log("btnMyPageSave() start!!!");
        var myPageCf = confirm("변경된 내용을 저장 하시겠습니까?");
        if (myPageCf == false) {
            return;
        }

        $.ajax({
            url: '/cm/updateMyInfo',
            type: 'GET',
            data : {
                "userId" : '<c:out value="${session_user.userId}"/>',
                "userName" : $("#hdrUserName").val(),
                "deptName" :$("#hdrDeptName").val(),
                "telNo" : $("#hdrTelNo").val()
            },
            success: function(data){
                console.log("data : ", data);
                alert("수정했습니다.");
                location.reload();
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            }
        });

    }

    function btnMyPageComplexSave() {
        console.log("btnMyPageComplexSave() start!!!");
        var myPageCf = confirm("변경된 내용을 저장 하시겠습니까?");
        if (myPageCf == false) {
            return;
        }

        $.ajax({
            url: '/cm/updateMyInfo',
            type: 'GET',
            data : {
                "userId" : '<c:out value="${session_user.userId}"/>',
                "telNo" : $("#hdrTelNo").val()
            },
            success: function(data){
                console.log("data : ", data);
                alert("수정했습니다.");
                location.reload();
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            }
        });

    }

</script>
<c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
<c:out value="${userGroupName}"/>
<c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
    <div id="nav" class="fixed-top">
        <div class="in">
            <h1 class="logo"><a href="<%=request.getContextPath()%>/cm/housingcplx/info/list"><img src="/images/h1_logo.png" alt="" /></a></h1>
            <div class="util">
                <p class="apt_name"><c:out value="${session_user.userName}"/></p>
                <div class="btn_mypage">
                    <a href="#" class="btn_pop"><i class="fas fa-user-circle"></i></a>
                    <div class="pop_over">
                        <p class="tit">마이페이지</p>
                        <button class="btn_close"><img src="/images/btn_x.png" alt="" /></button>
                        <div class="tbl_pop_over">
                            <table>
                                <colgroup>
                                    <col />
                                    <col />
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>· 사용자 ID</th>
                                        <td><input type="text" class="form-control" disabled value="<c:out value="${session_user.userId}"/>" /></td>
                                    </tr>
                                    <tr>
                                        <th>· 사용자 이름</th>
                                        <td><input type="text" id="hdrUserName" class="form-control" value="<c:out value="${session_user.userName}"/>" /></td>
                                    </tr>
                                    <tr>
                                        <th>· 사용자 그룹</th>
                                        <td><input type="text" class="form-control" disabled value="<c:out value="${session_user.userGroupName}"/>" /></td>
                                    </tr>
                                    <tr>
                                        <th>· 소속</th>
                                        <td><input type="text" id="hdrDeptName" class="form-control" value="<c:out value="${session_user.deptName}"/>" /></td>
                                    </tr>
                                    <tr>
                                        <th>· 휴대폰 번호</th>
                                        <td><input type="text" id="hdrTelNo" class="form-control" value="<c:out value="${session_user.telNo}"/>" /></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="btm_area">
                            <button type="button" class="btn btn-gray">취소</button>
                            <button type="button" class="btn btn-bluegreen" onclick = "btnMyPageSave();">저장</button>
                        </div>
                        <span class="bul"></span>
                    </div>
                </div>
                <a href="javascript:logout();" class="btn_logout"><i class="fas fa-unlock"></i></a>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${userGroupName eq 'COMPLEX_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN' }">
	<div id="nav" class="fixed-top type2">
		<div class="in">
			<h1 class="logo"><a href="<%=request.getContextPath()%>/cm/housingcplx/info/list"><img src="/images/h1_logo.png" alt="" /></a></h1>
			<div class="util">
				<p class="admin_info"><c:out value="${session_user.userName}"/></p>
				<div class="btn_mypage">
					<a href="#" class="btn_pop">수정</a>
					<div class="pop_over">
						<p class="tit">관리자 정보</p>
						<button class="btn_close"><img src="/images/btn_x.png" alt="" /></button>
						<div class="tbl_pop_over">
							<table>
								<colgroup>
									<col />
									<col />
								</colgroup>
								<tbody>
									<tr>
										<th>· 사용자 ID</th>
                                        <td><input type="text" class="form-control" disabled value="<c:out value="${session_user.userId}"/>" /></td>
									</tr>
									<tr>
										<th>· 단지명</th>
                                        <td><input type="text" class="form-control" disabled value="<c:out value="${session_user.houscplxNm}"/>" /></td>
									</tr>
									<tr>
										<th>· 사용자 그룹</th>
                                        <td><input type="text" class="form-control" disabled value="<c:out value="${session_user.userGroupName}"/>" /></td>
									</tr>
									<tr>
										<th>· 휴대폰 번호</th>
                                        <td><input type="text" id="hdrTelNo" class="form-control" value="<c:out value="${session_user.telNo}"/>" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="btm_area">
							<button type="button" class="btn btn-gray">취소</button>
							<button type="button" class="btn btn-bluegreen" onclick = "btnMyPageComplexSave();">저장</button>
						</div>
						<span class="bul"></span>
					</div>
				</div>
				<a href="javascript:logout();" class="btn_logout"><i class="fas fa-unlock"></i></a>
			</div>
		</div>
	</div>
</c:if>

