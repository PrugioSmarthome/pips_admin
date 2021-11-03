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
    var isExistCheckYn = "N";

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

        $("#userGroupId").change(function() {
            var selGroupId = $("#userGroupId").val();
            var strarray = selGroupId.split("/");
            if (strarray[0] == 'SYSTEM_ADMIN' || strarray[0] == 'SUB_SYSTEM_ADMIN') {
                $("#tblSystem").show();
                $("#tblComplex").hide();
                $("#tblMultiComplex").hide();
                $("#danjiSelect").hide();
            } else if (strarray[0] == 'COMPLEX_ADMIN') {
                $("#tblSystem").hide();
                $("#tblMultiComplex").hide();
                $("#danjiSelect").hide();
                $("#tblComplex").show();
            } else if (strarray[0] == 'MULTI_COMPLEX_ADMIN') {
                $("#tblSystem").hide();
                $("#tblMultiComplex").show();
                $("#danjiSelect").show();
                $("#tblComplex").hide();
            } else {
                $("#tblSystem").hide();
                $("#tblComplex").hide();
                $("#tblMultiComplex").hide();
                $("#danjiSelect").hide();
            }

        });

        $('#list_table2').DataTable({
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

        $("#list_table2").css("width","100%");

    });

    //취소버튼
    function cancel(){
        location.href = "/cm/system/user/list";
    }
    function existId() {
        var selGroupId = $("#userGroupId").val();
        var userId = '';
        var strarray = selGroupId.split("/");
        if (strarray[0] == 'SYSTEM_ADMIN' || strarray[0] == 'SUB_SYSTEM_ADMIN') {
            userId = $("#tblSystem [name=userId]").val();
            if ($("#tblSystem [name=userId]").val() == '') {
                alert("사용자 ID를 입력해주세요.");
                return;
            }
        } else if (strarray[0] == 'COMPLEX_ADMIN') {
            userId = $("#tblComplex [name=userId]").val();
            if ($("#tblComplex [name=userId]").val() == '') {
                alert("사용자 ID를 입력해주세요.");
                return;
            }
        } else if (strarray[0] == 'MULTI_COMPLEX_ADMIN') {
            userId = $("#tblMultiComplex [name=userId]").val();
            if ($("#tblMultiComplex [name=userId]").val() == '') {
                alert("사용자 ID를 입력해주세요.");
                return;
            }
        }

        $.ajax({
            url: '/cm/system/user/existUser',
            type: 'GET',
            data : {"userId" : userId},
            success: function(data){
                if (data == 'true') {
                    alert(userId + " 는 사용중인 ID 입니다.");
                } else {
                    alert("사용 가능합니다.");
                    isExistCheckYn = "Y";
                }
            },
            error: function(e){

            }
        });
    }
    //등록하기전 유효성체크
    function isValid(){
        var RegExp = /[\{\}\[\]\/;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
        var str = $("#userName").val();
        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }

        if(isExistCheckYn == 'N') {
            alert("사용자 ID 중복체크를 해주십시요.");
            return false;
        }
        if($("#userGroupId").val() == "NULL"){
            alert("사용자 그룹을 선택해주세요.");
            return false;
        }

        var selGroupId = $("#userGroupId").val();
        if (selGroupId == 'SYSTEM_ADMIN' || selGroupId == 'SUB_SYSTEM_ADMIN') {
            if($("#tblSystem [name=userId]").val() == ""){
                alert("사용자 ID를 입력해주세요.");
                return false;
            }
            if($("#tblSystem [name=password]").val() == ""){
                alert("초기비밀번호를 입력해주세요.");
                return false;
            }
        } else if (selGroupId == 'COMPLEX_ADMIN'){
            if($("#tblComplex [name=userId]").val() == ""){
                alert("사용자 ID를 입력해주세요.");
                return false;
            }
            if($("#tblComplex [name=password]").val() == ""){
                alert("초기비밀번호를 입력해주세요.");
                return false;
            }
        } else if (selGroupId == 'MULTI_COMPLEX_ADMIN'){
            if($("#tblMultiComplex [name=userId]").val() == ""){
                alert("사용자 ID를 입력해주세요.");
                return false;
            }
            if($("#tblMultiComplex [name=password]").val() == ""){
                alert("초기비밀번호를 입력해주세요.");
                return false;
            }
        }

        return true;
    }

    function formsubmit(){
        if(isValid() == false){
            return;
        }

        var selGroupId = $("#userGroupId").val();
        var strarray = selGroupId.split("/");
        if (strarray[0] == 'SYSTEM_ADMIN' || strarray[0] == 'SUB_SYSTEM_ADMIN') {
            $("#tblComplex").remove();
            $("#tblMultiComplex").remove();
        } else if (strarray[0] == 'COMPLEX_ADMIN') {
            $("#tblSystem").remove();
            $("#tblMultiComplex").remove();
        } else if (strarray[0] == 'MULTI_COMPLEX_ADMIN') {
            $("#tblSystem").remove();
            $("#tblComplex").remove();
        }
        var str = $("#userName").val();
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        $("#userName").val(str);
        $("#ugid").val(strarray[1]);
        $("#ugname").val(strarray[0]);

        $("#form_add").submit();
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
        $("#hname").val(strarray[1]);
        $("#deptName").val(strarray[1]);
        $("#houscplxCd").val(strarray[0]);

        $("#closebtn").click();
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

        var result = "<table id='danjiListTable' class='table2' style='text-align:center;'><thead><tr><th style='text-align:center;'>관리 단지명</th></tr></thead><tbody>";
        for(var i=0;i<multiDanjiList.length;i++){
            result += "<tr><td style='text-align:center;'>"+multiDanjiList[i]+"</td></tr>";
        }
        result += "</tbody></table>";

        $("#danjiList").empty();
        $("#danjiList").append(result);

        $("#houscplxCd").val(multiDanjiListCd);
        $("#multiYn").val("Y");

        $("#modal2").modal('hide');
    }

</script>
<form:form id="form_add" action="/cm/system/user/addUserAction" method="post" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">사용자 정보 등록</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>사용자 정보 관리</li>
                <li>사용자 정보 등록</li>
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
                            <select id="userGroupId" class="custom-select">
                                <option value="NULL">선택</option>
                                <c:forEach items="${userGroupList}" var="list" varStatus="status">
                                    <option value="<c:out value="${list.userGroupName}"/>/<c:out value="${list.userGroupId}"/>"><c:out value="${list.description}"/></option>
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
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="userId" name="userId" maxlength="20"/>
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" onclick="existId();">중복체크</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><input type="text" class="form-control" id="userName" name="userName" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>소속</th>
                        <td><input type="text" class="form-control" id="deptName" name="deptName" /></td>
                    </tr>
                    <tr>
                        <th>메모</th>
                        <td><input type="text" class="form-control" id="description" name="description" /></td>
                    </tr>
                    <tr>
                        <th>초기 비밀번호</th>
                        <td><input type="password" class="form-control" id="password" name="password" /></td>
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
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="userId" name="userId" maxlength="20"/>
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" onclick="existId();">중복체크</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><input type="text" class="form-control" id="userName" name="userName" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>관리 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="hname" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" onclick="houscplxNm_popup();">단지선택</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>휴대폰 번호</th>
                        <td><input type="text" class="form-control" id="telNo" name="telNo" /></td>
                    </tr>
                    <tr>
                        <th>초기 비밀번호</th>
                        <td><input type="password" class="form-control" id="password" name="password" /></td>
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
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="userId" name="userId" maxlength="20"/>
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" onclick="existId();">중복체크</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>사용자 이름</th>
                        <td><input type="text" class="form-control" id="userName" name="userName" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>휴대폰 번호</th>
                        <td><input type="text" class="form-control" id="telNo" name="telNo" /></td>
                    </tr>
                    <tr>
                        <th>초기 비밀번호</th>
                        <td><input type="password" class="form-control" id="password" name="password" /></td>
                    </tr>
                </tbody>
            </table>

            <div id="danjiSelect" style="display:none;">
                <br/>
                <button style="width:920px;" class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal2">단지선택</button>
                <div id="danjiList" style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;">
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
    <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="deptName" name="deptName" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="ugid" name="userGroupId" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="ugname" name="userGroupName" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="multiYn" name="multiYn" value="N" style="width:0;height:0;visibility:hidden"/>
</div>
</form:form>

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
                        <tbody id="householdDeviceList2">
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