<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    .dataTables_length {
            float: right !important;
    }
</style>
<script type="text/javascript">

    var danjiArray = new Array();
    var danjiArrayNm = new Array();

    $(document).on('change', 'input[name="box_chk"]', function(){
        if($(this).is(":checked")){
            var val = $(this).val().split("_");
            danjiArrayNm.push(val[1]);
            danjiArray.push(val[0]);
        } else{
            var val = $(this).val().split("_");
            for(let i = 0; i < danjiArrayNm.length; i++) {
              if(danjiArrayNm[i] == val[1]) {
                danjiArrayNm.splice(i, 1);
                i--;
              }
            }
            for(let i = 0; i < danjiArray.length; i++) {
              if(danjiArray[i] == val[0]) {
                danjiArray.splice(i, 1);
                i--;
              }
            }
        }
    });

    $(document).ready(function(){

    $("#danjiSelect").hide();

        $('#list_table').DataTable({
            "order": [],
            "bLengthChange": true,
            "lengthMenu": [10, 30, 50, 100],
            "pageLength" : 30,
            "dom": 'iltp',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다.",
                "lengthMenu" : "_MENU_ 개씩 보기"
            },
            ajax:{
                "url":"/cm/common/housingcplx/listNotAll",
                "dataSrc":""
                },
                columns:[
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                var nm = row['houscplxNm'];
                                return "<div class='custom-control custom-checkbox'><input type='checkbox' name='box_chk' class='custom-control-input' id='"+data+"_"+nm+"' value='"+data+"_"+nm+"'><label class='custom-control-label' for='"+data+"_"+nm+"'></label></div>";
                            }
                    },
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            return row["houscplxNm"];
                        }
                    }
                ]
        });

        $("#list_table").css("width","100%")

    });

   //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
         $('#list_table').DataTable().search(text).draw();
    }

    //취소버튼
    function cancel(){
        location.href = "/cm/system/serviceLink/list";
    }
    //등록하기전 유효성체크
    function isValid(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var fileCheck = document.getElementById("inputGroupFile01").value;
        var fileCheck2 = document.getElementById("inputGroupFile02").value;
        var tpcd = $("#selLnkSvcGrpTpCd").val()
        if($("#selLnkSvcGrpTpCd").val() == "NULL"){
            alert("구분을 선택해주세요.");
            return false;
        }
        if($("#txtLnkSvcNm").val() == "NULL"){
            alert("서비스명을 선택해주세요.");
            return false;
        }
        if($("#txtPerchrgNm").val() == ""){
            alert("담당자를 입력해주세요.");
            return false;
        }
        if($("#txtOffcPhoneNo").val() == ""){
            alert("사무실전화를 입력해주세요.");
            return false;
        }
        if($("#txtLnkOrdNo").val() == ""){
            alert("정렬순서를 입력해주세요.");
            return false;
        }

        if(tpcd == "PRUGIO_APP" || tpcd == "ADDTION_APP" || tpcd == "GREENERY_APP"){
            if(!fileCheck){
                alert("아이콘 이미지를 선택해주세요.");
                return false;
            }
        }else if(tpcd == "PRUGIO_WEB" || tpcd == "ADDTION_WEB" || tpcd == "GREENERY_WEB"){
            if(!fileCheck2){
                alert("아이콘 이미지를 선택해주세요.");
                return false;
            }
        }

        if( $('#danjiListTable td').length <= 0 ) {
            alert("적용할 단지를 선택해주세요.");
            return false;
        }



        return true;
    }

    function formsubmit(){
        if(isValid() == false){
            return;
        }

        var lnkOrdNo = $("#txtLnkOrdNo").val();
        var flag = "false";
        $.ajax({
             url: '/cm/system/serviceLink/checkServiceLinkOrd',
             type: 'POST',
             data: {"lnkOrdNo": lnkOrdNo},
             dataType : "text",
             async: false,
             success: function(data){
                 if(data == "true"){
                    alert("정렬순서가 중복되었습니다.");
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

        var list = new Array();
        if ($("#ANDROID-URL").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "ANDROID";
            obj.lnkAttrTpCd = "URL";
            obj.lnkAttrCont = $("#ANDROID-URL").val();
            list.push(obj);
        }

        if ($("#IOS-URL").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "IOS";
            obj.lnkAttrTpCd = "URL";
            obj.lnkAttrCont = $("#IOS-URL").val();
            list.push(obj);
        }

        if ($("#ANDROID-SCHEMA").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "ANDROID";
            obj.lnkAttrTpCd = "SCHEMA";
            obj.lnkAttrCont = $("#ANDROID-SCHEMA").val();
            list.push(obj);
        }

        if ($("#IOS-SCHEMA").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "IOS";
            obj.lnkAttrTpCd = "SCHEMA";
            obj.lnkAttrCont = $("#IOS-SCHEMA").val();
            list.push(obj);
        }

        if ($("#ANDROID-DEEP_LINK").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "ANDROID";
            obj.lnkAttrTpCd = "DEEP_LINK";
            obj.lnkAttrCont = $("#ANDROID-DEEP_LINK").val();
            list.push(obj);
        }

        if ($("#IOS-DEEP_LINK").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "IOS";
            obj.lnkAttrTpCd = "DEEP_LINK";
            obj.lnkAttrCont = $("#IOS-DEEP_LINK").val();
            list.push(obj);
        }

        if ($("#ANDROID-APP_ID").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "ANDROID";
            obj.lnkAttrTpCd = "APP_ID";
            obj.lnkAttrCont = $("#ANDROID-APP_ID").val();
            list.push(obj);
        }

        if ($("#IOS-APP_ID").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "IOS";
            obj.lnkAttrTpCd = "APP_ID";
            obj.lnkAttrCont = $("#IOS-APP_ID").val();
            list.push(obj);
        }

        if ($("#WEB-URL").val() != "") {
            var obj = new Object();
            obj.lnkTpCd = "WEB";
            obj.lnkAttrTpCd = "URL";
            obj.lnkAttrCont = $("#WEB-URL").val();
            list.push(obj);
        }

        $("#serviceLinkList").val(JSON.stringify(list));
        var svcname = $("#txtLnkSvcNm").val();
        var svcnamearray = svcname.split("/");
        $("#lnkSvcGrpTpCd").val($("#selLnkSvcGrpTpCd").val());
        $("#lnkSvcTpCd").val($("#selLnkSvcGrpTpCd").val());
        $("#lnkSvcNm").val(svcname);
        $("#perchrgNm").val($("#txtPerchrgNm").val());
        $("#offcPhoneNo").val($("#txtOffcPhoneNo").val());
        $("#emailNm").val($("#txtEmailNm").val());
        $("#useYn").val($("input[name='radio']:checked").val());
        $("#mainScreenYn").val($("input[name='mainRadio']:checked").val());
        $("#delYn").val("N");
        $("#lnkOrdNo").val($("#txtLnkOrdNo").val());
        $("#form_add").submit();
    }
    function grpTpCd(e){
        var tpcd = $(e).val();
        if(tpcd == "PRUGIO_APP" || tpcd == "ADDTION_APP" || tpcd == "GREENERY_APP"){
            $("#app").show();
            $("#danjiSelect").show();
            $("#web").hide();
        }else if(tpcd == "PRUGIO_WEB" || tpcd == "ADDTION_WEB" || tpcd == "GREENERY_WEB"){
            $("#web").show();
            $("#danjiSelect").show();
            $("#app").hide();
        }
    }

    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('box_chk');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                var chechAllVal = check[i].value.split("_");
                danjiArrayNm.push(chechAllVal[1]);
                danjiArray.push(chechAllVal[0]);
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
                var chechAllVal = check[i].value.split("_");

                for(let j = 0; j < danjiArray.length; j++) {
                    if(danjiArray[j] == chechAllVal[0]) {
                        danjiArray.splice(j, 1);
                        j--;
                    }
                }
                for(let k = 0; k < danjiArrayNm.length; k++) {
                    if(danjiArrayNm[k] == chechAllVal[1]) {
                        danjiArrayNm.splice(k, 1);
                        k--;
                    }
                }
            }
        }
    }

    //단지선택
    function danjiCheck(){

        if(danjiArrayNm[0] == null){
            alert("단지를 선택해주세요.");
            return;
        }

        var result = "<table id='danjiListTable' class='table' style='text-align:center;'><thead><tr><th style='text-align:center;'>단지명</th></tr></thead><tbody>";
        for(var i=0;i<danjiArrayNm.length;i++){
            result += "<tr><td style='text-align:center;'>"+danjiArrayNm[i]+"</td></tr>";
        }
        result += "</tbody></table>";

        $("#danjiList").empty();
        $("#danjiList").append(result);

        $("#danjiArray").val(danjiArray);

        $("#modal1").modal('hide');
    }

    function SetNum(obj){
        val=obj.value;
        re=/[^0-9]/gi;
        obj.value=val.replace(re,"");
    }

</script>
<form:form id="form_add" action="/cm/system/serviceLink/addServiceLinkAction" method="post" enctype="multipart/form-data" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">연계 웹/앱 신규등록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>연계 웹/앱 관리</li>
                <li>연계 웹/앱 신규등록</li>
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
                        <th>구분</th>
                        <td>
                            <select name="selLnkSvcGrpTpCd" id="selLnkSvcGrpTpCd" class="custom-select" onchange="grpTpCd(this);">
                                <option value="NULL">선택</option>
                                <c:forEach items="${typeList}" var="list" varStatus="status">
                                    <option value="<c:out value="${list.commCd}"/>"><c:out value="${list.commCdNm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                        <th>서비스명</th>
                        <td>
                            <input type="text" class="form-control" id="txtLnkSvcNm" name="txtLnkSvcNm" maxlength="20"/>
                        </td>
                    </tr>
                    <tr>
                        <th>담당자</th>
                        <td><input type="text" class="form-control" id="txtPerchrgNm" name="txtPerchrgNm" maxlength="50"/></td>
                        <th>사무실 전화</th>
                        <td><input type="text" class="form-control" id="txtOffcPhoneNo" name="txtOffcPhoneNo" /></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><input type="text" class="form-control" id="txtEmailNm" name="txtEmailNm" /></td>
                        <th>활성화</th>
                        <td>
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                <label class="custom-control-label" for="radio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="Y" checked id="radio2" name="radio">
                                <label class="custom-control-label" for="radio2">Yes</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>메인화면 표시</th>
                        <td>
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="N" id="radio3" name="mainRadio">
                                <label class="custom-control-label" for="radio3">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="Y" checked id="radio4" name="mainRadio">
                                <label class="custom-control-label" for="radio4">Yes</label>
                            </div>
                        </td>
                        <th>정렬순서</th>
                        <td><input type="text" class="form-control" id="txtLnkOrdNo" name="txtLnkOrdNo" onkeyup="SetNum(this);" maxlength="11"/></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap2" id="app" style="display:none;">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th rowspan="2">스토어 URL</th>
                        <td>Android</td>
                        <td><input type="text" class="form-control" id="ANDROID-URL" /></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><input type="text" class="form-control" id="IOS-URL" /></td>
                    </tr>

                    <tr>
                        <th rowspan="2">스키마</th>
                        <td>Android</td>
                        <td><input type="text" class="form-control" id="ANDROID-SCHEMA" /></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><input type="text" class="form-control" id="IOS-SCHEMA" /></td>
                    </tr>

                    <tr>
                        <th rowspan="2">Deep Link</th>
                        <td>Android</td>
                        <td><input type="text" class="form-control" id="ANDROID-DEEP_LINK" /></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><input type="text" class="form-control" id="IOS-DEEP_LINK" /></td>
                    </tr>

                    <tr>
                        <th rowspan="2">App Id</th>
                        <td>Android</td>
                        <td><input type="text" class="form-control" id="ANDROID-APP_ID" /></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><input type="text" class="form-control" id="IOS-APP_ID" /></td>
                    </tr>
                    <tr>
                        <th>아이콘 이미지</th>
                        <td colspan="2">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputGroupFile01" name="inputGroupFile01" aria-describedby="inputGroupFileAddon01" accept=".png, .jpg, .jpeg, .gif, .doc, .docx, .xls, .xlsx, .hwp, .ppt, .pptx, .txt"/>
                                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                            </div>
                            <script type="text/javascript">
                                $('#inputGroupFile01').on('change',function(){
                                    var fileName = $(this).val();
                                    $(this).next('.custom-file-label').html(fileName);
                                    $("#isAttachFile").val("Y");
                                })
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>


        <div class="table_wrap2" id="web" style="display:none;">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>URL</th>
                        <td style="width:100%;"><input type="text" class="form-control" id="WEB-URL" /></td>
                    </tr>
                    <tr>
                        <th>아이콘 이미지</th>
                        <td colspan="2">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputGroupFile02" name="inputGroupFile02" aria-describedby="inputGroupFileAddon02" accept=".png, .jpg, .jpeg, .gif"/>
                                <label class="custom-file-label" for="inputGroupFile02">Choose file</label>
                            </div>
                            <script type="text/javascript">
                                $('#inputGroupFile02').on('change',function(){
                                    var fileName = $(this).val();
                                    $(this).next('.custom-file-label').html(fileName);
                                    $("#isAttachFile").val("Y");
                                })
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br/>
        <button id="danjiSelect" style="width:920px;" class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1">단지선택</button>
        <div id="danjiList" style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;">
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">저장</button>
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
                <h5 class="modal-title">단지선택</h5>
                <button type="button" id="closebtn" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

            <!-- 모달바디 -->
            <div class="modal-body">
            <div class="container">
            <div class="row">
            <div class="col-lg-12 col-sm-12 col-11 main-section">
                <!-- 검색영역 -->
                <div class="search_area">
                    <table>
                        <colgroup>
                            <col style="width:70px"/>
                            <col />
                            <col style="width:95px"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>검색어</th>
                                <td><input type="text" class="form-control" id="search_text"/></td>
                                <td class="text-right"><button type="button" id="search_btn" onclick="list_search();" class="btn btn-brown btn-sm">검색</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //검색영역 -->

                <!-- 테이블상단 -->
                <div class="tbl_top_info mt-4">
                    <div class="total"></div>
                </div>
                <!-- //테이블상단 -->

                <!-- 테이블 -->
                <div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap mt-3">
                    <table class="table" id="list_table" style="text-align:center;">
                        <thead>
                            <tr>
                                <th scope="col" style="text-align:center;">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="checkAll" onclick="javascript:checkall();">
                                        <label class="custom-control-label" for="checkAll"></label>
                                    </div>
                                </th>
                                <th scope="col" style="text-align:center;">항목</th>
                            </tr>
                        </thead>
                        <tbody id="householdDeviceList">
                        </tbody>
                    </table>
                </div>
                <!-- //테이블 -->
            </div>
            </div>
            </div>
            </div>
            <!-- //모달바디 -->
        <button class="btn btn-gray" type="button" onclick="javascript:danjiCheck()">선택완료</button>
        </div>
    </div>
</div>

    <input type="text" id="lnkSvcGrpTpCd" name="lnkSvcGrpTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="lnkSvcTpCd" name="lnkSvcTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="lnkSvcNm" name="lnkSvcNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="perchrgNm" name="perchrgNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="offcPhoneNo" name="offcPhoneNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="emailNm" name="emailNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="useYn" name="useYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="serviceLinkList" name="serviceLinkList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delYn" name="delYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="isAttachFile" id="isAttachFile" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="danjiArray" id="danjiArray" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="danjiArrayNm" id="danjiArrayNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="mainScreenYn" id="mainScreenYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="lnkOrdNo" id="lnkOrdNo" style="width:0;height:0;visibility:hidden"/>
</form:form>