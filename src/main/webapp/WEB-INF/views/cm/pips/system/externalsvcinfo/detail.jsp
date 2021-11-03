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
        window.history.back();
    }
    function btn_delete(e) {
        var conf = confirm("해당 외부연계를 삭제하시겠습니까?");
        if(conf == true) {
            $("#form_delete [name='svcId']").val(e);
            $("#form_delete").submit();
        }
    }

    function edit(e){
        $("#form_edit [name='svcId']").val(e);
        $("#form_edit").submit();
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">외부연계 관리 정보 상세</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>외부연계 관리</li>
                <li>외부연계 관리 목록</li>
                <li>외부연계 관리 정보 상세</li>
            </ul>
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
                        <th>Type</th>
                        <td><c:out value="${serviceDetailInfo.svcNm}"/></td>
                        <th>서비스명</th>
                        <td><c:out value="${serviceDetailInfo.cont}"/></td>
                    </tr>
                    <tr>
                        <th>유효기간<br />(YYYY-MM-DD)</th>
                        <td><c:out value="${serviceDetailInfo.exprtnYmd}"/></td>
                        <th>URL</th>
                        <td><c:out value="${serviceDetailInfo.urlCont}"/></td>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <td><c:out value="${serviceDetailInfo.userId}"/></td>
                        <th>서비스 Key</th>
                        <td><c:out value="${serviceDetailInfo.svcKeyCd}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="btn_delete('<c:out value="${serviceDetailInfo.svcId}"/>');">삭제</button>
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
                <button class="btn btn-bluegreen" type="button" onclick="edit('<c:out value="${serviceDetailInfo.svcId}"/>');">수정</button>
            </div>
        </div>

    </div>

</div>


<form:form id="form_edit" action="/cm/system/externalService/edit" name="detail" commandName="detail" method="post">
      <input type="text" id="svcId" name="svcId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_delete" action="/cm/system/externalService/deleteExternalServiceInfoAction" name="detail" commandName="detail" method="post">
      <input type="text" id="svcId" name="svcId" style="width:0;height:0;visibility:hidden"/>
</form:form>