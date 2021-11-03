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
        location.href = "/cm/system/platform/list";
    }
    //등록하기전 유효성체크
    function isValid(){
        var expUrl = /^http[s]?\:\/\//i;
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;

        if($("#platformTpNm_").val() == ""){
            alert("종류를 입력해주세요.");
            return false;
        }

        if($("#platformNm_").val() == ""){
            alert("플랫폼 이름을 입력해주세요.");
            return false;
        }

        if($("#platformCompany_").val() == ""){
            alert("연동사를 입력해주세요.");
            return false;
        }

        if(expUrl.test($("#platformUrl_").val()) == false || $("#platformUrl_").val() == ""){
            alert("올바른 접속URL 주소를 넣어주세요.");
            return false;
        }

        if(expUrl.test($("#platformNotiUrl_").val()) == false || $("#platformNotiUrl_").val() == ""){
            alert("올바른 NotiURL 주소를 넣어주세요.");
            return false;
        }

        return true;
    }

    function formsubmit(){
        if(isValid() == false){
            return;
        }

        $("#platformTpNm").val($("#platformTpNm_").val());
        $("#platformNm").val($("#platformNm_").val());
        $("#platformCompany").val($("#platformCompany_").val());
        $("#platformUrl").val($("#platformUrl_").val());
        $("#platformNotiUrl").val($("#platformNotiUrl_").val());

        $("#form_add").submit();
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">타 플랫폼 신규등록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>타 플랫폼 관리</li>
                <li>타 플랫폼 신규등록</li>
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
                        <th>종류</th>
                        <td colspan="3"><input type="text" class="form-control" id="platformTpNm_" name="platformTpNm_" /></td>
                    </tr>
                    <tr>
                        <th>플랫폼 이름</th>
                        <td><input type="text" class="form-control" id="platformNm_" name="platformNm_" /></td>
                        <th>연동사</th>
                        <td><input type="text" class="form-control" id="platformCompany_" name="platformCompany_" /></td>
                    </tr>
                    <tr>
                        <th>접속 URL</th>
                        <td colspan="3"><input type="text" class="form-control" id="platformUrl_" name="platformUrl_" /></td>
                    </tr>
                    <tr>
                        <th>Noti URL</th>
                        <td colspan="3"><input type="text" class="form-control" id="platformNotiUrl_" name="platformNotiUrl_" /></td>
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

<form:form id="form_add" action="/cm/system/platform/insertAction" name="add" commandName="add" method="post">
      <input type="text" id="platformTpNm" name="platformTpNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="platformNm" name="platformNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="platformCompany" name="platformCompany" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="platformUrl" name="platformUrl" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="platformNotiUrl" name="platformNotiUrl" style="width:0;height:0;visibility:hidden"/>
</form:form>