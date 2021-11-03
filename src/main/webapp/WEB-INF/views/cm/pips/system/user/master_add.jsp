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
        $('#list_table').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            ajax:{
                "url":"/cm/common/housingcplx/list",
                "dataSrc":""
                },
                columns:[
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            if(meta.row == 0){
                                return "전체";
                            }else{
                                return row["houscplxNm"];
                            }
                        }
                    },
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                if(meta.row == 0){
                                    return "<input class='btn btn-gray btn-sm' type='button' id='_전체' value='선택' onclick='selectbtn(this)'/>";
                                }else{
                                    var nm = row['houscplxNm'];
                                    return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                                }
                            }
                    }
                ]
        });

        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm_").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#user").val("<c:out value="${userTpCd}"/>");
        $("#userTpCd").val("<c:out value="${userTpCd}"/>");
        var str = "<c:out value="${userNm}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        $("#name").val(str);
        $("#userNm").val(str);
        $("#searchingUserId").val("<c:out value="${userId}"/>");
        var name = "<c:out value="${houscplxNm}"/>";

        var dong = "<c:out value="${dongNo}"/>";
        var hose = "<c:out value="${hoseNo}"/>";
        if(dong == "all"){
            $("#dongNo").val("all");
        }else{
            $("#dongNo").val("<c:out value="${dongNo}"/>");
        }
        if(hose == "all"){
            $("#hoseNo").val("all");
        }else{
            $("#hoseNo").val("<c:out value="${hoseNo}"/>");
        }

        var searchingYn = "<c:out value="${searchingYn}"/>";
        if(name != ""){
            if(searchingYn == "Y"){
                selectList("searchingYn");
            }else{
                selectList();
            }
        }
    });



    //단지명 선택했을경우 동,호 리스트 가져오기
    function selectList(gubun){
        var param = new Object();
        param.delYn = "N";
        param.houscplxCd = $("#houscplxCd").val();

        var dong = "<c:out value="${dongNo}"/>";
        var hose = "<c:out value="${hoseNo}"/>";

        $.ajax({
            url: '/cm/common/household/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#search_text").value = "";

                if(gubun == "dong"){
                    $("#dong").empty();
                    if(dong == "all" || dong == ""){
                        $("#dong").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#dong").append("<option value='all'>전체</option>");
                    }
                    var j = 0;
                    var temp = new Array();
                    $.each(data, function(i, item){
                        if ($.inArray(item.dongNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.dongNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[j]){
                                $("#dong").append("<option selected value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                                j++;
                            }else{
                                $("#dong").append("<option value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                                j++;
                            }
                        }
                    })
                } else if(gubun == "hose"){
                    $("#hose").empty();
                    if(hose == "all" || hose == ""){
                        $("#hose").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#hose").append("<option value='all'>전체</option>");
                    }
                    var temp = new Array();
                    $.each(data, function(i, item){
                        if (item.dongNo == $("#dong").val() && $.inArray(item.hoseNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.hoseNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[j]){
                                $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                                j++;
                            }else{
                                $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                                j++;
                            }
                        }
                    })
                } else if(gubun == "searchingYn"){
                    $("#dong").empty();
                    $("#hose").empty();
                    if(dong == "all" || dong == ""){
                        $("#dong").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#dong").append("<option value='all'>전체</option>");
                    }
                    if(hose == "all" || hose == ""){
                        $("#hose").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#hose").append("<option value='all'>전체</option>");
                    }
                    var j = 0;
                    var k = 0;
                    var temp = new Array();
                    var temp1 = new Array();

                    $.each(data, function(i, item){
                         if ($.inArray(item.dongNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.dongNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[j]){
                                $("#dong").append("<option selected value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                                j++;
                            }else{
                                $("#dong").append("<option value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                                j++;
                            }
                         }
                        if (item.dongNo == $("#dong").val() && $.inArray(item.hoseNo, temp1) == -1) {  // temp1 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp1.push(item.hoseNo);              // temp 배열에 값을 넣는다.
                            if(hose == temp1[k]){
                                $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                                k++;
                            }else{
                                $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                                k++;
                            }
                        }
                    })
                }
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }

    function dong_change(){
        selectList("hose");
        //$("#hose").val("all");
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#houscplxCd").val("all");
        }else{
            $("#houscplxCd").val(strarray[0]);
        }

        $("#closebtn").click();
        if(strarray[1] != "전체"){
            selectList("dong");
        }

    }
    //검색버튼을 눌렀을경우
    function btn_search(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var str = $("#name").val();
        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        $("#userNm").val(str);
        $("#userTpCd").val($("#user").val());
        $("#houscplxNm_").val($("#houscplxNm").val());
        $("#searchingUserId_").val($("#searchingUserId").val());
        $("#form_search").submit();
    }



    function edit_page(id,hid){
        $("#hsholdId").val(hid);
        $("#userId").val(id);
        $("#form_edit").submit();
    }


    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('check');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
            }
        }
    }

    //가족대표를 선택한경우 그 가족대표가 세대구성원중 혼자만 남아있는지 체크하는 함수
    function Representative_Check(id){
        var userListCheck;
        var str = id[0];
        str = str.split(".");

        var param = new Object();
        param.houscplxCd = str[0];
        param.dongNo = str[1];
        param.hoseNo = str[2];
        $.ajax({
            url: '/cm/pips/user/usercheck',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                if(data == 1){
                    userListCheck = true;
                }else{
                    userListCheck = false;
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

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //취소
    function btn_back(){
        location.href = "/cm/system/userMaster/list";
    }

    //등록
    function btn_insert(){

        var userMasterArray = $("input[name='check']").length;
        var userMasterData = new Array(userMasterArray);
        var userMasterList = $("input[name='check']");

        for(var i=0; i<userMasterArray; i++){
            if(userMasterList[i].checked == true) {
                userMasterData[i] = $("input[name='check']")[i].value+"_Y";
            }else{
                userMasterData[i] = $("input[name='check']")[i].value+"_N";
            }
        }

        $("#userMasterList").val(userMasterData);
        $("#form_insert").submit();
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">마스터 계정 신규등록</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>마스터 계정 관리</li>
                <li>마스터 계정 목록</li>
                <li>마스터 계정 신규등록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:40%"/>
                    <col style="width:7%"/>
                    <col style="width:20%"/>
                    <col style="width:7%"/>
                    <col style="width:20%"/>
                    <col style="width:6%"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" disabled id="houscplxNm"/>
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
                                </div>
                            </div>
                        </td>
                        <th>· 동</th>
                        <td>
                            <select name="dong" id="dong" class="custom-select" onchange="dong_change();">
                                <option value="all">전체</option>
                            </select>
                        </td>
                        <th>· 호</th>
                        <td>
                            <select name="hose" id="hose" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>· 회원구분</th>
                        <td>
                            <select name="user" id="user" class="custom-select">
                                <option value="all">전체</option>
                                <option value="RESIDENT">입주민</option>
                                <option value="NORMAL">일반회원</option>
                            </select>
                        </td>
                        <th>· 이름</th>
                        <td>
                            <input type="text" class="form-control" id="name"/>
                        </td>
                    </tr>
                    <tr>
                        <th>· 아이디</th>
                        <td>
                            <input type="text" class="form-control" id="searchingUserId"/>
                        </td>
                        <th style="visibility:hidden;">· 검색</th>
                        <td colspan="3">
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:3"/>
                    <col style="width:31%"/>
                    <col style="width:7%"/>
                    <col style="width:7%"/>
                    <col style="width:19%"/>
                    <col style="width:13%"/>
                    <col style="width:10%"/>
                    <col style="width:10%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" onclick="checkall();" id="checkAll"/>
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">단지명</th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">회원구분</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <div class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${list.userId}"/>" <c:if test="${list.masterYn eq 'Y'}"> checked </c:if> />
                            <label class="custom-control-label" for="check${status.index}"></label>
                            </div>
                        </td>
                        <c:choose>
                            <c:when test="${list.houscplxNm eq ''}">
                                <td class="text-center">-</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.houscplxNm}"/></a></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.dongNo eq ''}">
                                <td class="text-center">-</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.dongNo}"/></a></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.hoseNo eq ''}">
                                <td class="text-center">-</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.hoseNo}"/></a></td>
                            </c:otherwise>
                        </c:choose>

                        <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.userId}"/></a></td>
                        <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.userNm}"/></a></td>

                        <c:choose>
                            <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">가족대표</a></td>
                            </c:when>
                            <c:when test="${list.fmlyTpCd eq 'FAMILY'}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">구성원</a></td>
                            </c:when>
                            <c:when test="${list.fmlyTpCd eq ''}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">일반회원</a></td>
                            </c:when>
                        </c:choose>
                        <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.certfTpCd}"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-gray" type="button" onclick="btn_back();">취소</button>
                    <button class="btn btn-bluegreen" type="button" onclick="btn_insert();">저장</button>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $('#table1').DataTable({
                "order": [],
                "bLengthChange" : false,
                "dom": '<i<t>p>',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "조회된 데이터가 없습니다."
                },
            });
        </script>
    </div>
</div>

<form:form id="form_search" action="/cm/system/userMaster/search" name="info" commandName="info" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm_" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userTpCd" name="userTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userNm" name="userNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="searchingUserId_" name="searchingUserId_" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_edit" action="/cm/system/userMaster/edit" name="info" commandName="info" method="post">
      <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_insert" action="/cm/system/userMaster/insert" name="info" commandName="info" method="post">
      <input type="text" id="userMasterList" name="userMasterList" style="width:0;height:0;visibility:hidden"/>
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