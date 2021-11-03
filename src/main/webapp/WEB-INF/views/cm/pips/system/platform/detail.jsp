<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });

    function back(){
        location.href = "/cm/system/platform/list";
    }

    function modify(id){
        $("#platformId").val(id);
        $("#form_edit").submit();
    }

    function deleteAction(id){
        $("#platformId_").val(id);
        $("#form_delete").submit();
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">타 플랫폼 상세</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>타 플랫폼 관리</li>
                <li>타 플랫폼 상세</li>
            </ul>
        </div>

        <div class="tbl_btm_area2">
            <div class="left_area">
                <button class="btn btn-gray" type="button" onclick="deleteAction('<c:out value="${platformDetail.platformId}"/>')">삭제</button>
            </div>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>종류</th>
                        <td colspan="3"><c:out value="${platformDetail.platformTpNm}"/></td>
                    </tr>
                    <tr>
                        <th>플랫폼 이름</th>
                        <td><c:out value="${platformDetail.platformNm}"/></td>
                        <th>연동사</th>
                        <td><c:out value="${platformDetail.platformCompany}"/></td>
                    </tr>
                    <tr>
                        <th>플랫폼 ID</th>
                        <td><c:out value="${platformDetail.platformId}"/></td>
                        <th>플랫폼 인증 Key</th>
                        <td><c:out value="${platformDetail.platformAuthKey}"/></td>
                    </tr>
                    <tr>
                        <th>접속 URL</th>
                        <td colspan="3"><c:out value="${platformDetail.platformUrl}"/></td>
                    </tr>
                    <tr>
                        <th>Noti URL</th>
                        <td colspan="3"><c:out value="${platformDetail.platformNotiUrl}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
                <button class="btn btn-bluegreen" type="button" onclick="modify('<c:out value="${platformDetail.platformId}"/>')">수정</button>
            </div>
        </div>
    </div>
</div>
<form:form id="form_edit" action="/cm/system/platform/edit" name="edit" commandName="edit" method="post">
      <input type="text" id="platformId" name="platformId" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="form_delete" action="/cm/system/platform/deleteAction" name="delete" commandName="delete" method="post">
      <input type="text" id="platformId_" name="platformId_" style="width:0;height:0;visibility:hidden"/>
</form:form>