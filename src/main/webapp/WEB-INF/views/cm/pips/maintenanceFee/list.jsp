<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

        $("#yr_").val("<c:out value="${yr}"/>");
        $("#mm_").val("<c:out value="${mm}"/>");

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
                    "data":{"userId" : userId}
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
        }

        var groupName = "<c:out value="${session_user.userGroupName}"/>";
        var searchingYn = "<c:out value="${searchingYn}"/>";
        if(groupName == "COMPLEX_ADMIN" && searchingYn != "Y"){
            $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
            selectList("dong");
        }

        $("#home_name").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");

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

        var name = "<c:out value="${houscplxNm}"/>";
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

        $("#yr").val($("#yr_").val());
        $("#mm").val($("#mm_").val());
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
        $("#home_name").val(strarray[1]);
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
    }

    //등록페이지 이동
    function add(){
        location.href = "/cm/maintenance/fee/add";
    }

    //리스트 선택시 상세 화면으로 이동
    function click_row(hsholdId, yr, mm){
        $("#hsholdId").val(hsholdId);
        $("#yr__").val(yr);
        $("#mm__").val(mm);
        $("#form_detail").submit();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">단지 관리비 목록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지 관리비 관리</li>
                <li>단지 관리비 목록</li>
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
                        <th>· 부과년월</th>
                        <td>
                            <select name="yr_" id="yr_" class="custom-select" style="width:150px;">
                                <option value="">전체</option>
                                <%
                                    for(int i=2020; i<=2050; i++){
                                %>
                                    <option value="<%=i%>"><%=i%> 년</option>
                                <%
                                    }
                                %>
                            </select>
                            <select name="mm_" id="mm_" class="custom-select" style="width:150px;">
                                <option value="">전체</option>
                                <%
                                    for(int j=1; j<=12; j++){
                                    String jj = String.format("%02d", j);
                                %>
                                    <option value="<%=jj%>"><%=jj%> 월</option>
                                <%
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" disabled id="home_name"/>
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
        </c:if>

        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
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
                        <th>· 부과년월</th>
                        <td>
                            <select name="yr_" id="yr_" class="custom-select" style="width:150px;">
                                <option value="">전체</option>
                                <%
                                    for(int i=2020; i<=2050; i++){
                                %>
                                    <option value="<%=i%>"><%=i%> 년</option>
                                <%
                                    }
                                %>
                            </select>
                            <select name="mm_" id="mm_" class="custom-select" style="width:150px;">
                                <option value="">전체</option>
                                <%
                                    for(int j=1; j<=12; j++){
                                    String jj = String.format("%02d", j);
                                %>
                                    <option value="<%=jj%>"><%=jj%> 월</option>
                                <%
                                    }
                                %>
                            </select>
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
        </c:if>

        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                </div>
            </div>
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:40%"/>
                    <col style="width:10%"/>
                    <col style="width:10%"/>
                    <col style="width:15%"/>
                    <col style="width:15%"/>
                    <col style="width:10%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th>아파트명</th>
                        <th>동</th>
                        <th>호</th>
                        <th>납기내</th>
                        <th>납기후</th>
                        <th>부과년월</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${maintenanceFeeList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.houscplxNm}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.dongNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.hoseNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');"><fmt:formatNumber type="number" value="${list.beforeMgmcstQty}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');"><fmt:formatNumber type="number" value="${list.afterMgmcstQty}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.yr}${list.mm}</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
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

        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:40%"/>
                    <col style="width:10%"/>
                    <col style="width:10%"/>
                    <col style="width:15%"/>
                    <col style="width:15%"/>
                    <col style="width:10%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th>아파트명</th>
                        <th>동</th>
                        <th>호</th>
                        <th>납기내</th>
                        <th>납기후</th>
                        <th>부과년월</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${maintenanceFeeList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.houscplxNm}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.dongNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.hoseNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');"><fmt:formatNumber type="number" value="${list.beforeMgmcstQty}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');"><fmt:formatNumber type="number" value="${list.afterMgmcstQty}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.yr}${list.mm}</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
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
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:15%"/>
                    <col style="width:15%"/>
                    <col style="width:25%"/>
                    <col style="width:25%"/>
                    <col style="width:20%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th>동</th>
                        <th>호</th>
                        <th>납기내</th>
                        <th>납기후</th>
                        <th>부과년월</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${maintenanceFeeList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.dongNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.hoseNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');"><fmt:formatNumber type="number" value="${list.beforeMgmcstQty}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');"><fmt:formatNumber type="number" value="${list.afterMgmcstQty}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${list.hsholdId}"/>','<c:out value="${list.yr}"/>','<c:out value="${list.mm}"/>');">${list.yr}${list.mm}</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
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

<form:form id="form_detail" action="/cm/maintenance/fee/view" name="detail" commandName="detail" method="post">
      <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="yr__" name="yr__" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="mm__" name="mm__" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/maintenance/fee/search" name="list" commandName="list" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="yr" name="yr" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="mm" name="mm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
</form:form>