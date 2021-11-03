<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

        var name = '<c:out value="${session_user.userGroupName}"/>';
        var userId = '<c:out value="${userId}"/>';
        if(name == "MULTI_COMPLEX_ADMIN"){
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
                    "url":"/cm/common/housingcplx/multiList",
                    "dataSrc":"",
                    "data":{"userId" : userId,
                            "noAll" : "Y"}
                    },
                    columns:[
                        {"data":"houscplxNm",
                            "render":function(data, type, row, meta){
                                return row["houscplxNm"];
                            }
                        },
                        {"data":"houscplxCd",
                                "render":function(data, type, row, meta){
                                    var nm = row['houscplxNm'];
                                    return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                                }
                        }
                    ]
            });
        }else {
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
                    "url":"/cm/common/housingcplx/listNotAll",
                    "dataSrc":""
                    },
                    columns:[
                        {"data":"houscplxNm",
                            "render":function(data, type, row, meta){
                                return row["houscplxNm"];
                            }
                        },
                        {"data":"houscplxCd",
                                "render":function(data, type, row, meta){
                                    var nm = row['houscplxNm'];
                                    return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                                }
                        }
                    ]
            });
        }

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

        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm_").val("<c:out value="${houscplxNm}"/>");


        var complex_admin_yn = "<c:out value="${COMPLEX_ADMIN_YN}"/>";
        if(complex_admin_yn == "Y"){
            selectList("dong");
        }

        var name = "<c:out value="${houscplxNm}"/>";
        var searchingYn = "<c:out value="${searchingYn}"/>";
        if(name != ""){
            if(searchingYn == "Y"){
                selectList("searchingYn");
            }else{
                selectList();
            }
        }


    });


    function dong_change(){
        selectList("hose");
        //$("#hose").val("all");
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#houscplxNm_").val(strarray[1]);
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

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
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

    //검색버튼을 눌렀을경우
    function btn_search(){
        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        $("#houscplxNm_").val($("#houscplxNm").val());
        $("#visitDate").val($("#start").val().replace(/\./gi,''));
        var houscplxCdVal = $("#houscplxCd").val();

        if(houscplxCdVal == "" || houscplxCdVal == "all"){
            alert("단지를 선택해주세요.");
            return;
        }

        $("#form_search").submit();
    }

    //엑셀 다운로드
    function excel(){
        var list = new Array();
        var name = "<c:out value="${session_user.userGroupName}"/>";

        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN" || name == "MULTI_COMPLEX_ADMIN"){
            <c:forEach items="${resultList}" var="VisitCarList" varStatus="status">
                var cJson = new Object();

                cJson.단지명 = "<c:out value="${VisitCarList.houscplxNm}"/>";
                cJson.차량번호 = "<c:out value="${VisitCarList.car_num}"/>";
                cJson.동 = "<c:out value="${VisitCarList.dong}"/>";
                cJson.호 = "<c:out value="${VisitCarList.hose}"/>";
                cJson.방문날짜 = "<c:out value="${VisitCarList.visit_date}"/>";
                cJson.방문차량예약신청시간 = "<c:out value="${VisitCarList.visit_date}"/> <c:out value="${VisitCarList.visit_starttime}"/>";

                list.push(cJson);
            </c:forEach>
        }else{
            <c:forEach items="${resultList}" var="VisitCarList" varStatus="status">
                var cJson = new Object();

                cJson.차량번호 = "<c:out value="${VisitCarList.car_num}"/>";
                cJson.동 = "<c:out value="${VisitCarList.dong}"/>";
                cJson.호 = "<c:out value="${VisitCarList.hose}"/>";
                cJson.방문날짜 = "<c:out value="${VisitCarList.visit_date}"/>";
                cJson.방문차량예약신청시간 = "<c:out value="${VisitCarList.visit_date}"/> <c:out value="${VisitCarList.visit_starttime}"/>";

                list.push(cJson);
            </c:forEach>
        }


        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "방문 차량 목록";
        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN" || name == "MULTI_COMPLEX_ADMIN"){
            param.tableHeader = "['단지명', '차량번호', '동', '호', '방문날짜', '방문차량예약신청시간']";
        }else{
            param.tableHeader = "['차량번호', '동', '호', '방문날짜', '방문차량예약신청시간']";
        }

        param.tableData = hJson;

        var date = new Date();
        var year = date.getFullYear();
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
          month = "0" + month;
        }
        if(day.length == 1){
          day = "0" + day;
        }
        var today = year + "" + month + "" + day;

        $.ajax({
            url: '/cm/common/excel/download',
            type: 'POST',
            data: param,
            traditional: true,
            xhrFields: {
                responseType: 'blob'
            },
            success: function(blob){
                console.log(blob.size);
                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_방문차량목록.xlsx";
                link.click();
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">방문 차량 목록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>방문 차량 조회</li>
                <li>방문 차량 목록</li>
            </ul>
        </div>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:40%"/>
                    <col style="width:6%"/>
                    <col />
                    <col style="width:6%"/>
                    <col />
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
                        <th>· 방문날짜</th>
                        <td style="width:350px">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="start" id="start" value="<c:out value="${visitDate}"/>" />
                            </div>
                            <script type="text/javascript">
                            $('#start').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#start').datepicker('setDate', 'today');
                            </script>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </c:if>

        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:6%"/>
                    <col />
                    <col style="width:6%"/>
                    <col />
                    <col style="width:11%"/>
                    <col style="width:25%"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
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
                        <th>· 방문날짜</th>
                        <td style="width:350px">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="start" id="start" value="<c:out value="${visitDate}"/>" />
                            </div>
                            <script type="text/javascript">
                            $('#start').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#start').datepicker('setDate', 'today');
                            </script>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </c:if>

        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col" style="width:30%;">단지명</th>
                        <th scope="col" style="width:15%;">차량번호</th>
                        <th scope="col" style="width:8%;">동</th>
                        <th scope="col" style="width:8%;">호</th>
                        <th scope="col" style="width:15%;">방문날짜</th>
                        <th scope="col" style="width:24%;">방문차량 예약 신청시간</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><c:out value="${list.houscplxNm}"/></td>
                        <td class="text-center"><c:out value="${list.car_num}"/></td>
                        <td class="text-center"><c:out value="${list.dong}"/></td>
                        <td class="text-center"><c:out value="${list.hose}"/></td>
                        <td class="text-center"><c:out value="${list.visit_date}"/></td>
                        <td class="text-center"><c:out value="${list.visit_date}"/> <c:out value="${list.visit_starttime}"/></td>
                    </tr>
                 </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="javascript:excel();"><i class="fas fa-table"></i>Export</button>
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

        </c:if>

        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col" style="width:30%;">차량번호</th>
                        <th scope="col" style="width:8%;">동</th>
                        <th scope="col" style="width:8%;">호</th>
                        <th scope="col" style="width:24%;">방문날짜</th>
                        <th scope="col" style="width:30%;">방문차량 예약 신청시간</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><c:out value="${list.car_num}"/></td>
                        <td class="text-center"><c:out value="${list.dong}"/></td>
                        <td class="text-center"><c:out value="${list.hose}"/></td>
                        <td class="text-center"><c:out value="${list.visit_date}"/></td>
                        <td class="text-center"><c:out value="${list.visit_date}"/> <c:out value="${list.visit_starttime}"/></td>
                    </tr>
                 </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="javascript:excel();"><i class="fas fa-table"></i>Export</button>
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
        </c:if>
    </div>
</div>

<form:form id="form_search" action="/cm/visit/car/search" name="info" commandName="info" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm_" name="houscplxNm_" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="visitDate" name="visitDate" style="width:0;height:0;visibility:hidden"/>
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

<script type="text/javascript">
    var visitDate = '<c:out value="${visitDate}"/>';
    if(visitDate != ""){
        var year = visitDate.substring(0,4);
        var month = visitDate.substring(4,6);
        var day = visitDate.substring(6);

        visitDate = year + "." + month + "." + day;

        $("#start").val(visitDate);
    }
</script>