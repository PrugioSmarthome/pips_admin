<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    .dataTables_length {
            float: right !important;
    }
</style>
<script type="text/javascript">

    var multiDanjiList = new Array();
    var multiDanjiListCd = new Array();

    $(document).on('change', 'input[name="box_chk"]', function(){
        if($(this).is(":checked")){
            var val = $(this).val().split("_");
            multiDanjiList.push(val[1]);
            multiDanjiListCd.push(val[0]);
        } else{
            var val = $(this).val().split("_");
            for(let i = 0; i < multiDanjiList.length; i++) {
              if(multiDanjiList[i] == val[1]) {
                multiDanjiList.splice(i, 1);
                i--;
              }
            }
            for(let i = 0; i < multiDanjiListCd.length; i++) {
              if(multiDanjiListCd[i] == val[0]) {
                multiDanjiListCd.splice(i, 1);
                i--;
              }
            }
        }
    });

    $(document).ready(function(){

        $("#userGroupIdSelect").change(function() {
            var selGroupId = $("#userGroupIdSelect").val();
            var strarray = selGroupId.split("/");
            if (strarray[0] == 'SYSTEM_ADMIN' || strarray[0] == 'SUB_SYSTEM_ADMIN') {
                $("#tblSystem").show();
                $("#tblComplex").hide();
                $("#tblMultiComplex").hide();
                $("#divMultiComplex").hide();
            } else if (strarray[0] == 'COMPLEX_ADMIN') {
                $("#tblSystem").hide();
                $("#tblMultiComplex").hide();
                $("#divMultiComplex").hide();
                $("#tblComplex").show();
            } else if (strarray[0] == 'MULTI_COMPLEX_ADMIN') {
                $("#tblSystem").hide();
                $("#tblMultiComplex").show();
                $("#divMultiComplex").show();
                $("#tblComplex").hide();
            } else {
                $("#tblSystem").hide();
                $("#tblComplex").hide();
                $("#tblMultiComplex").hide();
                $("#divMultiComplex").hide();
            }

        });

        var selGroupName = $("#userGroupName").val();
        if (selGroupName == 'SYSTEM_ADMIN' || selGroupName == 'SUB_SYSTEM_ADMIN') {
            $("#tblSystem").show();
            $("#tblComplex").hide();
            $("#tblMultiComplex").hide();
            $("#divMultiComplex").hide();
        } else if (selGroupName == 'COMPLEX_ADMIN') {
            $("#tblSystem").hide();
            $("#tblMultiComplex").hide();
            $("#divMultiComplex").hide();
            $("#tblComplex").show();
        } else if (selGroupName == 'MULTI_COMPLEX_ADMIN') {
            $("#tblSystem").hide();
            $("#tblMultiComplex").show();
            $("#divMultiComplex").show();
            $("#tblComplex").hide();
        } else {
            $("#tblSystem").hide();
            $("#tblComplex").hide();
            $("#tblMultiComplex").hide();
            $("#divMultiComplex").hide();
        }

            var userId = $("#userId").val();
            $('#list_table2').DataTable({
                "order": [],
                "bLengthChange": true,
                "lengthMenu": [10, 30, 50, 100],
                "pageLength" : 50,
                "dom": 'iltp',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "조회된 데이터가 없습니다.",
                    "lengthMenu" : "_MENU_ 개씩 보기"
                },
                ajax:{
                    "url":"/cm/system/user/selectMultiDanjiList",
                    "data": function(d){
                        d.userId = userId;
                    },
                    "dataSrc":""
                    },
                    columns:[
                        {"data":"houscplxCd",
                                "render":function(data, type, row, meta){
                                    var nm = row['houscplxNm'];
                                    var cnt = row['cnt'];
                                    if(cnt == 1){
                                        return "<div class='custom-control custom-checkbox'><input type='checkbox' name='box_chk' class='custom-control-input' id='"+data+"_"+nm+"' value='"+data+"_"+nm+"'><label class='custom-control-label' for='"+data+"_"+nm+"'></label></div>";
                                    }else{
                                        return "<div class='custom-control custom-checkbox'><input type='checkbox' name='box_chk' class='custom-control-input' id='"+data+"_"+nm+"' value='"+data+"_"+nm+"' checked><label class='custom-control-label' for='"+data+"_"+nm+"'></label></div>";
                                    }
                                }
                        },
                        {"data":"houscplxNm",
                            "render":function(data, type, row, meta){
                                return row["houscplxNm"];
                            }
                        }
                    ]
            });

            $("#list_table2").css("width","100%");

        if($("#multiUserYn").val() == "Y"){

            var result = "<tr><th rowspan='${fn:length(multiUserInfo)}'>관리 단지명</th>";
            result += "<td><c:out value='${multiUserInfo[0].houscplxNm}'/></td></tr>";

            <c:forEach items="${multiUserInfo}" var="list" varStatus="status" begin="1">
                result += "<tr><td><c:out value='${list.houscplxNm}'/></td></tr>";
            </c:forEach>

            <c:forEach items="${multiUserInfo}" var="list" varStatus="status">
                multiDanjiList.push("<c:out value='${list.houscplxNm}'/>");
                multiDanjiListCd.push("<c:out value='${list.houscplxCd}'/>");
            </c:forEach>

            $("#houscplxNmList").append(result);
        }

    });

    //취소버튼
    function cancel(){
        location.href = "/cm/system/user/list";
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

   //모달 단지리스트 검색(멀티 단지 관리자)
    function list_search2(){
        var text = $("#search_text2").val();
         $('#list_table2').DataTable().search(text).draw();
    }

    function formsubmit(){
        //시스템 관리자, 단지 관리자 따로 value 값 지정해줘야함

        var selGroupName = $("#userGroupName").val();
        var userGroupIdSelect = $("#userGroupIdSelect").val();
        var userGroupId = userGroupIdSelect.split("/");
        if (userGroupId[0] == 'SYSTEM_ADMIN' || userGroupId[0] == 'SUB_SYSTEM_ADMIN') {
            $("#initAccount").val($("input[name='syRadio']:checked").val());
            $("#tblComplex").remove();
            $("#tblMultiComplex").remove();
            $("#divMultiComplex").remove();
        } else if (userGroupId[0] == 'COMPLEX_ADMIN') {
            $("#initAccount").val($("input[name='coRadio']:checked").val());
            $("#tblSystem").remove();
            $("#tblMultiComplex").remove();
            $("#divMultiComplex").remove();
        } else if (userGroupId[0] == 'MULTI_COMPLEX_ADMIN') {
            $("#initAccount").val($("input[name='mtRadio']:checked").val());
            $("#tblSystem").remove();
            $("#tblComplex").remove();
        }

        if($("#multiUserYn").val() == "Y"){
            if($("#multiChangeYn").val() == "N"){
                var danjiArray = new Array();
                $("input[name=box_chk]:checked").each(function() {
                    var val = $(this).val().split("_");
                    danjiArray.push(val[0]);
                })
                $("#danjiArray").val(danjiArray);
            }
        }

        if(userGroupId[0] != 'MULTI_COMPLEX_ADMIN'){
            $("#multiUserYn").val("N");
        }

        $("#userGroupId").val(userGroupId[1]);

        $("#form_edit").submit();
    }

    //단지선택 버튼 클릭시 단지리스트 팝업
    function houscplxNm_popup(){
        //Ajax 들어갈 부분
        $.ajax({
                url: '/cm/common/housingcplx/list',
                type: 'POST',
                dataType : "json",
                success: function(data){

                $("#householdDeviceList").empty();
                $.each(data, function(i, item){
                    $("#householdDeviceList").append("<tr><td class='text-center'>"+item.houscplxNm+"</td><td class='text-center'><input class='btn btn-gray btn-sm' type='button' id='"+item.houscplxCd+"_"+item.houscplxNm+"' value='선택' onclick='selectbtn(this)'/></td></tr>")
                })

                },
                error: function(e){

                },
                complete: function() {
                }
            });
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");

        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#houscplxCd").val(strarray[0]);

        $("#closebtn").click();
    }

    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('box_chk');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                var chechAllVal = check[i].value.split("_");
                multiDanjiList.push(chechAllVal[1]);
                multiDanjiListCd.push(chechAllVal[0]);
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
                var chechAllVal = check[i].value.split("_");

                for(let j = 0; j < multiDanjiListCd.length; j++) {
                    if(multiDanjiListCd[j] == chechAllVal[0]) {
                        multiDanjiListCd.splice(j, 1);
                        j--;
                    }
                }
                for(let k = 0; k < multiDanjiList.length; k++) {
                    if(multiDanjiList[k] == chechAllVal[1]) {
                        multiDanjiList.splice(k, 1);
                        k--;
                    }
                }
            }
        }
    }

    //단지선택(멀티 단지 관리자)
    function danjiCheck(){

        if(multiDanjiList[0] == null){
            alert("단지를 선택해주세요.");
            return;
        }

        var result = "<tr><th rowspan='"+multiDanjiList.length+"'>관리 단지명</th>";
        result += "<td>"+multiDanjiList[0]+"</td></tr>"
        for(var i=1;i<multiDanjiList.length;i++){
            result += "<tr><td>"+multiDanjiList[i]+"</td></tr>";
        }

        $("#houscplxNmList").empty();
        $("#houscplxNmList").append(result);

        $("#danjiArray").val(multiDanjiListCd);
        $("#multiChangeYn").val("Y");
        $("#multiUserYn").val("Y");

        $("#modal2").modal('hide');
    }

</script>
<form:form id="form_edit" action="/cm/system/user/editUserAction" method="post" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">사용자 정보 수정</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>사용자 정보 관리</li>
                <li>사용자 정보 수정</li>
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
                        <th>사용자 그룹</th>
                        <td>
                            <select id="userGroupIdSelect" class="custom-select">
                                <c:forEach items="${userGroupList}" var="list" varStatus="status">
                                    <option value="<c:out value="${list.userGroupName}"/>/<c:out value="${list.userGroupId}"/>" <c:if test="${list.description eq userInfo.description}"> selected </c:if>><c:out value="${list.description}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
            <table class="table2" id="tblSystem" style="display:none;">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>사용자 ID</th>
                        <td>${userInfo.userId}</td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><input type="text" class="form-control" id="userName" name="userName" value="<c:out value="${userInfo.userName}"/>" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>소속</th>
                        <td><input type="text" class="form-control" id="deptName" name="deptName" value="<c:out value="${userInfo.deptName}"/>" /></td>
                    </tr>
                    <tr>
                        <th>활성화</th>
                        <td>
                        <c:if test="${userInfo.initAccount eq 'Y'}">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="Y" checked id="syRadio1" name="syRadio">
                                <label class="custom-control-label" for="syRadio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="N" id="syRadio2" name="syRadio">
                                <label class="custom-control-label" for="syRadio2">Yes</label>
                            </div>
                        </c:if>
                        <c:if test="${userInfo.initAccount eq 'N'}">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="Y" id="syRadio1" name="syRadio">
                                <label class="custom-control-label" for="syRadio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="N" checked id="syRadio2" name="syRadio">
                                <label class="custom-control-label" for="syRadio2">Yes</label>
                            </div>
                        </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>메모</th>
                        <td><input type="text" class="form-control" id="description" name="description" value="<c:out value="${userInfo.description}"/>" /></td>
                    </tr>
                </tbody>
            </table>
            <table class="table2" id="tblComplex" style="display:none;">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>사용자 ID</th>
                        <td><c:out value="${userInfo.userId}"/></td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><input type="text" class="form-control" id="userName" name="userName" value="<c:out value="${userInfo.userName}"/>" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>관리 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="houscplxNm" name="houscplxNm" value="<c:out value="${userInfo.houscplxNm}"/>" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" onclick="houscplxNm_popup();">단지선택</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>휴대폰 번호</th>
                        <td><input type="text" class="form-control" id="telNo" name="telNo" value="<c:out value="${userInfo.telNo}"/>" /></td>
                    </tr>
                    <tr>
                        <th>활성화</th>
                        <td>
                        <c:if test="${userInfo.initAccount eq 'Y'}">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="Y" checked id="coRadio1" name="coRadio">
                                <label class="custom-control-label" for="coRadio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="N" id="coRadio2" name="coRadio">
                                <label class="custom-control-label" for="coRadio2">Yes</label>
                            </div>
                        </c:if>
                        <c:if test="${userInfo.initAccount eq 'N'}">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="Y" id="coRadio1" name="coRadio">
                                <label class="custom-control-label" for="coRadio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="N" checked id="coRadio2" name="coRadio">
                                <label class="custom-control-label" for="coRadio2">Yes</label>
                            </div>
                        </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
            <table class="table2" id="tblMultiComplex" style="display:none;">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>사용자 ID</th>
                        <td><c:out value="${userInfo.userId}"/></td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><input type="text" class="form-control" id="userName" name="userName" value="<c:out value="${userInfo.userName}"/>" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>휴대폰 번호</th>
                        <td><input type="text" class="form-control" id="telNo" name="telNo" value="<c:out value="${userInfo.telNo}"/>" /></td>
                    </tr>
                    <tr>
                        <th>활성화</th>
                        <td>
                        <c:if test="${userInfo.initAccount eq 'Y'}">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="Y" checked id="mtRadio1" name="mtRadio">
                                <label class="custom-control-label" for="mtRadio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="N" id="mtRadio2" name="mtRadio">
                                <label class="custom-control-label" for="mtRadio2">Yes</label>
                            </div>
                        </c:if>
                        <c:if test="${userInfo.initAccount eq 'N'}">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="Y" id="mtRadio1" name="mtRadio">
                                <label class="custom-control-label" for="mtRadio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="N" checked id="mtRadio2" name="mtRadio">
                                <label class="custom-control-label" for="mtRadio2">Yes</label>
                            </div>
                        </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>

            <div id="divMultiComplex" style="display:none;">
                <br/>
                <button id="danjiSelect" style="width:920px;" class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal2">단지선택</button>
                <div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap2">
                    <table class="table2">
                        <colgroup>
                            <col style="width:150px"/>
                            <col />
                        </colgroup>
                        <tbody id="houscplxNmList"></tbody>
                    </table>
                </div>
            </div>

        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">저장</button>
            </div>
        </div>

    </div>

</div>

    <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${userInfo.houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="userGroupId" name="userGroupId" value="<c:out value="${userInfo.userGroupId}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="userGroupName" name="userGroupName" value="<c:out value="${userInfo.userGroupName}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="userId" name="userId" value="<c:out value="${userInfo.userId}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="initAccount" name="initAccount" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="multiUserYn" id="multiUserYn" value="<c:out value="${multiUserYn}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="multiChangeYn" id="multiChangeYn" value="N" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="danjiArray" id="danjiArray" style="width:0;height:0;visibility:hidden"/>
</form:form>

<!-- 단지선택 팝업(단지 관리자) -->
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
                                <th>· 검색어</th>
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
                <div class="table_wrap mt-3">
                    <table class="table" id="list_table" style="width:100%;text-align:center;">
                        <thead>
                            <tr>
                                <th scope="col">항목</th>
                                <th scope="col">선택</th>
                            </tr>
                        </thead>
                        <tbody id="householdDeviceList">
                        </tbody>
                    </table>
                </div>
                <!-- //테이블 -->
            </div>
            <!-- //모달바디 -->
        </div>
    </div>
</div>

<!-- 단지선택 팝업(멀티 단지 관리자) -->
<div class="modal fade" id="modal2" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered " role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">단지선택</h5>
                <button type="button" id="closebtn2" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
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
                                <td><input type="text" class="form-control" id="search_text2"/></td>
                                <td class="text-right"><button type="button" id="search_btn2" onclick="list_search2();" class="btn btn-brown btn-sm">검색</button></td>
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
                    <table class="table" id="list_table2" style="text-align:center;">
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