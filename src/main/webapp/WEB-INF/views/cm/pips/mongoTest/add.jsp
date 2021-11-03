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
    //취소버튼
    function cancel(){
        window.history.back();
    }

    function formsubmit(){

        $("#hsholdId").val($("#hsholdId_").val());
        $("#ymd").val($("#ymd_").val());
        $("#serviceCharge").val($("#serviceCharge_").val());
        $("#maintenanceCost").val($("#maintenanceCost_").val());

        $("#form_add").submit();
    }

    function btn_add() {
        location.href="/cm/mongo/test/add2";
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">테스트 등록</h2>
            <ul class="location">
                <li>테스트 등록</li>
                <li>테스트 등록</li>
                <li>테스트 등록</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>hshold_id</th>
                        <td><input type="text" class="form-control" id="hsholdId_" name="hsholdId_" /></td>
                    </tr>
                    <tr>
                        <th>ymd</th>
                        <td><input type="text" class="form-control" id="ymd_" name="ymd_" /></td>
                    </tr>
                    <tr>
                        <th>관리비</th>
                        <td><input type="text" class="form-control" id="serviceCharge_" name="serviceCharge_" /></td>
                    </tr>
                    <tr>
                        <th>유지보수비</th>
                        <td><input type="text" class="form-control" id="maintenanceCost_" name="maintenanceCost_" /></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-add" onclick="btn_add(); return false;"><i class="fas fa-plus-square"></i></button>
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">등록</button>
            </div>
        </div>
    </div>
</div>

<form:form id="form_add" action="/cm/mongo/test/insertAction" name="add" commandName="add" method="post">
      <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="ymd" name="ymd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="serviceCharge" name="serviceCharge" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="maintenanceCost" name="maintenanceCost" style="width:0;height:0;visibility:hidden"/>
</form:form>