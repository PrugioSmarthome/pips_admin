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

        var houscplxCd = "<c:out value="${houscplxCd}"/>";
        var houscplxNm = "<c:out value="${houscplxNm}"/>";
        $("#houscplxCd").val(houscplxCd);
        $("#houscplxNm").val(houscplxNm);
        $("#houscplxNm_").val(houscplxNm);
        $("#houscplxNm__").val(houscplxNm);

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

    //검색 버튼
    function search(){
        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        $("#form_search").submit();
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //단지 선택 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#houscplxNm_").val(strarray[1]);
        $("#houscplxNm__").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#houscplxCd").val("");
        }else{
            $("#houscplxCd").val(strarray[0]);
        }

        $("#closebtn").click();
        if(strarray[1] != "전체"){
            selectList("dong");
        }
    }

    function tab_click(){
        location.href = "/cm/system/device/list";
    }

    function dong_change(){
        selectList("hose");
        //$("#hose").val("all");
    }


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

    //수정페이지 이동
    function modify(){
        var houscplxCd_ = "<c:out value="${houscplxCd}"/>";
        var dongNo_ = "<c:out value="${dongNo}"/>";
        var hoseNo_ = "<c:out value="${hoseNo}"/>";

        $("#houscplxCd_").val(houscplxCd_);
        $("#dongNo_").val(dongNo_);
        $("#hoseNo_").val(hoseNo_);

        $("#form_edit").submit();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">장치 설정 목록</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>장치 설정</li>
                <li>장치 설정 목록</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click()"><a href="#">단지별 설정</a></li>
                <li class="on"><a href="#">세대별 설정</a></li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:40%"/>
                    <col style="width:7%"/>
                    <col style="width:17%"/>
                    <col style="width:7%"/>
                    <col style="width:17%"/>
                    <col style="width:12%"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" disabled id="houscplxNm_"/>
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
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" onclick="search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th rowspan="2" style="vertical-align:middle;">홈넷사</th>
                        <th rowspan="2" style="vertical-align:middle;">단지이름</th>
                        <th rowspan="2" style="vertical-align:middle;">동</th>
                        <th rowspan="2" style="vertical-align:middle;">호</th>
                        <th colspan="8" style="vertical-align:middle;">장치 타입</th>
                    </tr>
                    <tr>
                        <th>조명</th>
                        <th>밸브</th>
                        <th>에어컨</th>
                        <th>난방</th>
                        <th>환기</th>
                        <th>대기전력</th>
                        <th>전동커튼</th>
                        <th>일괄스위치</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceList}" var="list" varStatus="status">
                        <tr>
                            <c:choose>
                                <c:when test="${list.bizcoCd eq 'COMAX'}">
                                    <td class="text-center">코맥스</td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'KOCOM'}">
                                    <td class="text-center">코콤</td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'HYUNDAITEL'}">
                                    <td class="text-center">현대통신</td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'ICONTROLS'}">
                                    <td class="text-center">HDC 아이콘트롤스</td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'OTHER'}">
                                    <td class="text-center">기타</td>
                                </c:when>
                            </c:choose>

                            <td class="text-center"><c:out value="${list.houscplxNm}"/></td>
                            <td class="text-center"><c:out value="${list.dongNo}"/></td>
                            <td class="text-center"><c:out value="${list.hoseNo}"/></td>
                            <td class="text-center"><c:out value="${list.lightsYn}"/></td>
                            <td class="text-center"><c:out value="${list.gaslockYn}"/></td>
                            <td class="text-center"><c:out value="${list.airconYn}"/></td>
                            <td class="text-center"><c:out value="${list.heatingYn}"/></td>
                            <td class="text-center"><c:out value="${list.ventilatorYn}"/></td>
                            <td class="text-center"><c:out value="${list.smartConsentYn}"/></td>
                            <td class="text-center"><c:out value="${list.curtainYn}"/></td>
                            <td class="text-center"><c:out value="${list.lightSwitchYn}"/></td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-bluegreen" type="button" onclick="modify();">수정</button>
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

<form:form id="form_edit" action="/cm/system/device/hsholdEdit" name="edit" commandName="edit" method="post">
      <input type="text" id="houscplxCd_" name="houscplxCd_" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm__" name="houscplxNm__" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo_" name="dongNo_" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo_" name="hoseNo_" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/system/device/hsholdSearch" name="list" commandName="list" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
</form:form>