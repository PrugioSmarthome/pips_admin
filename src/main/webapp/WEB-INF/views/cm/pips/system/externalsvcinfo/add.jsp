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
        ServiceList();
        TypeList();
    });
    function edit_submit(){
        if(isValid() == false){
            return;
        }
        var svctpcd = $("#svcnm").val().split(",");
        var svcgrptpcd = $("#svccd").val().split(",");
        $("#svcGrpTpCd").val(svcgrptpcd[0]);
        $("#svcNm").val(svcgrptpcd[1]);
        $("#svcKeyCd").val($("#svckey").val());
        $("#urlCont").val($("#url").val());
        $("#exprtnYmd").val($("#ymd").val().replace(/\-/gi,''));
        $("#svcTpCd").val(svctpcd[0]);
        $("#cont").val(svctpcd[1]);
        $("#userId").val($("#userid").val());

        $("#form_edit").submit();
    }

    function isValid(){
        if($("#svccd").val() == "NULL"){
            alert("Type을 선택해 주세요.");
            return false;
        }
        if($("#svcnm").val() == "NULL"){
            alert("서비스명을 선택해주세요.");
            return false;
        }
        if($("#svckey").val() == ""){
            alert("서비스Key를 입력해주세요.");
            return false;
        }
        if($("#userid").val() == ""){
            alert("ID를 입력해주세요.");
            return false;
        }
        var expUrl = /^http[s]?\:\/\//i;
        if(expUrl.test($("#url").val()) == false || $("#url").val() == ""){
            alert("올바른 도메인주소를 넣어주세요.");
            return false;
        }

        return true;
    }
    function cancel(){
        location.href = "/cm/system/externalService/list";
    }

    function ServiceList(){
        $.ajax({
            url: '/cm/system/externalService/info/list',
            type: 'POST',
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    $("#svcnm").append('<option value="'+item.detailCode+','+item.codeName+'">'+item.codeName+'</option>');
                })
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }

    function TypeList(){
        $.ajax({
            url: '/cm/system/externalService/type/list',
            type: 'POST',
            dataType : "json",
            success: function(data){

                $.each(data, function(i, item){
                    $("#svccd").append('<option value="'+item.detailCode+','+item.codeName+'">'+item.codeName+'</option>');
                })
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }

    //서비스명 중복 체크
    function service_check(e){
        var param = new Object();
        var svcname = $(e).val().split(",");

        if(svcname != "NULL" || svcname != "ADDRESS_SEARCH"){
            param.svcTpCd = svcname[0];
            $.ajax({
                url: '/cm/system/externalService/servicename',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    if(data > 0){
                        alert("이미 등록된 서비스입니다. 다른 서비스를 선택해주세요.");
                        $("#svcnm").val("NULL");
                    }
                },
                error: function(e){
                    console.log("에러");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
        }
    }
</script>
<div id="container">

    <div class="container">

        <div class="top_area" style="margin:0 0 20px 0;">
            <h2 class="tit">외부연계 관리 정보 등록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>외부연계 관리</li>
                <li>외부연계 관리 목록</li>
                <li>외부연계 관리 정보 상세</li>
                <li>외부연계 관리 정보 등록</li>
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
                        <td>
                            <select name="svccd" id="svccd" class="custom-select">
                                <option value="NULL">선택</option>
                            </select>
                        </td>
                        <th>서비스명</th>
                        <td>
                            <select name="svcnm" id="svcnm" class="custom-select" onchange="service_check(this);">
                                <option value="NULL">선택</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>유효기간<br />(YYYY-MM-DD)</th>
                        <td><input type="text" class="form-control" id="ymd" /></td>
                        <script type="text/javascript">
                            $('#ymd').datepicker({
                                format: "yyyy-mm-dd",
                                language: "ko",
                                autoclose: true
                            });
                        </script>
                        <th>서비스 Key</th>
                        <td><input type="text" class="form-control" id="svckey" /></td>
                    </tr>
                    <tr>
                        <th>ID</th>
                        <td><input type="text" class="form-control" id="userid" /></td>
                        <th>URL</th>
                        <td><input type="text" class="form-control" id="url" /></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="edit_submit();">저장</button>
            </div>
        </div>

    </div>

</div>

<form:form id="form_edit" action="/cm/system/externalService/addExternalServiceInfoAction" name="detail" commandName="detail" method="post">
      <input type="text" id="svcGrpTpCd" name="svcGrpTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="svcTpCd" name="svcTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="svcNm" name="svcNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="cont" name="cont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="svcKeyCd" name="svcKeyCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="urlCont" name="urlCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="exprtnYmd" name="exprtnYmd" style="width:0;height:0;visibility:hidden"/>
</form:form>