<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });

    function list() {
        location.href = "/cm/system/user/list";
    }

    function edit() {
        $("#form_edit").submit();
    }

    function close() {
        $("#closebtn").click();
    }

    function del(){
        var conf = confirm("해당 정보를 삭제 하시겠습니까?");
        if(conf == true){
            $("#form_del").submit();
        }
    }

    function init_password() {
        $.ajax({
            url: '/cm/system/user/editPassword',
            type: 'GET',
            data : {
                "userId" : $("#userId").val(),
                "password" : $("#initPassword").val()
            },
            success: function(data){

                alert("성공했습니다.");
                location.reload();
                $("#closebtn").click();
            },
            error: function(e){

                alert("실패했습니다.");
                location.reload();
                $("#closebtn").click();
            }
        });
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">사용자 정보 상세</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>사용자 정보 관리</li>
                <li>사용자 정보 상세</li>
            </ul>
        </div>
        <div class="tbl_btm_area2">
            <div class="left_area">
                <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <button class="btn btn-gray" onclick="del();">계정삭제</button>
                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1">비밀번호 초기화</button>
                </c:if>
            </div>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>사용자 그룹</th>
                        <td><c:out value="${userInfo.description}"/></td>
                    </tr>
                    <tr>
                        <th>사용자 ID</th>
                        <td><c:out value="${userInfo.userId}"/></td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><c:out value="${userInfo.userName}"/></td>
                    </tr>
                    <tr>
                        <th>소속</th>
                        <td><c:out value="${userInfo.deptName}"/></td>
                    </tr>
                    <c:if test="${userInfo.userGroupName ne 'MULTI_COMPLEX_ADMIN'}">
                        <tr>
                            <th>관리 단지명</th>
                            <td><c:out value="${userInfo.houscplxNm}"/></td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>휴대폰 번호</th>
                        <td><c:out value="${userInfo.telNo}"/></td>
                    </tr>
                    <tr>
                        <th>활성화</th>
                        <c:choose>
                            <c:when test="${userInfo.initAccount eq 'N'}">
                                <td>Y</td>
                            </c:when>
                            <c:when test="${userInfo.initAccount eq 'Y'}">
                                <td>N</td>
                            </c:when>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>메모</th>
                        <td><c:out value="${userInfo.description}"/></td>
                    </tr>
                </tbody>
            </table>

            <c:if test="${userInfo.userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                <div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap2">
                    <table class="table2">
                        <colgroup>
                            <col style="width:150px"/>
                            <col />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th rowspan="${fn:length(multiUserInfo)}">관리 단지명</th><td><c:out value="${multiUserInfo[0].houscplxNm}"/></td>
                            </tr>
                            <c:forEach items="${multiUserInfo}" var="list" varStatus="status" begin="1">
                                <tr><td><c:out value="${list.houscplxNm}"/></td></tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-brown" onclick="edit();">수정</button>
                <button class="btn btn-brown" onclick="list();">목록</button>
            </div>
        </div>

    </div>

</div>

<!-- 단지선택 팝업 -->
<div class="modal fade" id="modal1" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered " role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">비밀번호 초기화</h5>
                <button type="button" id="closebtn" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

            <!-- 모달바디 -->
            <div class="modal-body">
                <!-- 검색영역 -->
                <div class="search_area">
                    <table>
                        <colgroup>
                            <col style="width:120px"/>
                            <col />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>초기 비밀번호</th>
                                <td><input type="password" class="form-control" id="initPassword"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //검색영역 -->

                <div class="tbl_btm_area2">
                    <div class="right_area">
                        <button class="btn btn-brown" onclick="close();">취소</button>
                        <button class="btn btn-brown" onclick="init_password();">초기화</button>
                    </div>
                </div>

            </div>
            <!-- //모달바디 -->
        </div>
    </div>
</div>


<form:form id="form_edit" action="/cm/system/user/edit" name="detail" commandName="detail" method="post">
      <input type="text" id="userId" name="userId" value="<c:out value="${userInfo.userId}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_del" action="/cm/system/user/deleteUserAction" name="detail" commandName="detail" method="post">
      <input type="text" id="userId" name="userId" value="<c:out value="${userInfo.userId}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>