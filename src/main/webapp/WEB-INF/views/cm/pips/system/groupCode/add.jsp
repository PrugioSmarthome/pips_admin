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

        if($("#commCdGrpCd_").val() == null || $("#commCdGrpCd_").val() == ""){
            alert("그룹코드를 입력해주세요.");
            return;
        }

        var commCdGrpNm = $("#commCdGrpNm_").val();
        var commCdGrpCd = $("#commCdGrpCd_").val();
        var rem = $("#rem_").val();

        var flag = "false";
        $.ajax({
             url: '/cm/system/groupCode/checkGroupCode',
             type: 'POST',
             data: {"commCdGrpCd": commCdGrpCd},
             dataType : "text",
             async: false,
             success: function(data){
                 if(data == "true"){
                    alert("이미 등록된 그룹코드입니다.");
                    flag = "true";
                 }
             },
             error: function(e){
                 console.log("에러");
                 console.log(e.responseText.trim());
             },
             complete: function() {
             }
        });
        if(flag == "true"){
            return;
        }

        $("#commCdGrpNm").val(commCdGrpNm);
        $("#commCdGrpCd").val(commCdGrpCd);
        $("#rem").val(rem);

        $("#form_add").submit();
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">그룹코드 등록</h2>
            <ul class="location">
                <li>시스템관리</li>
                <li>그룹코드 관리</li>
                <li>그룹코드 등록</li>
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
                        <th>그룹코드명</th>
                        <td>
                            <input type="text" name="commCdGrpNm_" id="commCdGrpNm_" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>그룹코드</th>
                        <td>
                            <input type="text" name="commCdGrpCd_" id="commCdGrpCd_" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>그룹코드 설명</th>
                        <td>
                            <input type="text" name="rem_" id="rem_" class="form-control" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">등록</button>
            </div>
        </div>
    </div>
</div>

<form:form id="form_add" action="/cm/system/groupCode/addGroupCode" name="addGroupCode" commandName="addGroupCode" method="post">
      <input type="text" id="commCdGrpNm" name="commCdGrpNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="commCdGrpCd" name="commCdGrpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="rem" name="rem" style="width:0;height:0;visibility:hidden"/>
</form:form>
