<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    #list_table_filter{
        visibility:hidden;
        width:0;
        height:0;
    }
</style>
<script type="text/javascript">
    $(document).ready(function(){
        $('#user_table').DataTable( {
            filter : false,
            lengthChange : false,
            columnDefs: [{ orderable: false, targets: 11 }],
            "language": {
                  "emptyTable": "조회된 데이터가 없습니다.",
                  "zeroRecords": "검색된 데이터가 없습니다."
                }
        });

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
        var yn = "<c:out value="${delYn}"/>";
        if(yn != ""){
            $("#select_del_yn").val("<c:out value="${delYn}"/>");
            $("#delYn").val("<c:out value="${delYn}"/>");
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

        var name = "<c:out value="${houscplxNm}"/>";
        if(name != ""){
            if(searchingYn == "Y"){
                selectList("searchingYn");
            }else{
                selectList();
            }
        }

    });


    //리스트 선택시 상세 화면으로 이동
    function click_row(rows){
        $("#wpadId").val(rows);
        $("#delYn_").val("N");
        $("#form_detail").submit();
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

    //검색
    function search(){
        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        var name = "<c:out value="${session_user.userGroupName}"/>";
        if(name == "COMPLEX_ADMIN" || name == "MULTI_COMPLEX_ADMIN"){
            $("#delYn").val("N");
        }else{
            $("#delYn").val($("#select_del_yn").val());
        }



        $("#form_list").submit();
    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //장치동기화 버튼(단지만 선택한경우 API콜 안됨)
    function all_devicesync_call(){
        if($("#dong").val() == "all"){
            alert("동까지 선택해야 동기화가 가능합니다.");
            return false;
        }

        var name = "<c:out value="${session_user.userGroupName}"/>";
        if(name == "COMPLEX_ADMIN" || name == "MULTI_COMPLEX_ADMIN"){
            $("#delYn").val("N");
        }else{
            $("#delYn").val($("#select_del_yn").val());
        }

        var hmnetId;
        $.ajax({
            type: "post",
            url: '/cm/household/device/hmnetIdSearch',
            async:false,
            data: {"houscplxCd" : $("#houscplxCd").val()},
            success: function(data){
                hmnetId = data;
            }
        });

        var param = new Object();
        param.hmnetId = hmnetId;
        param.houscplxCd = $("#houscplxCd").val();
        param.dongNo = $("#dong").val();
        if($("#hose").val() == "all" || $("#hose").val() == ""){
            param.hoseNo = "*";
        }else{
            param.hoseNo = $("#hose").val();
        }

        $.ajax({
            url: '/cm/api/device/sync',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                var return_data = JSON.stringify(data);
                return_data = JSON.parse(return_data);
                //var return_data = JSON.parse(data);

                if(return_data.params[0].reqStatus == true){
                    alert("요청되었습니다.");
                }else{
                    alert("요청이 실패했습니다.");
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

    //개별 장치동기화 버튼
    function api_call(hid,cd,dong,hoe){
        var param = new Object();
        param.hmnetId = hid;
        param.houscplxCd = cd;
        param.dongNo = dong;
        param.hoseNo = hoe;

        $.ajax({
            url: '/cm/api/device/sync',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                //var return_data = JSON.parse(data);
                var return_data = JSON.stringify(data);
                return_data = JSON.parse(return_data);
                if(return_data.params[0].reqStatus == true){
                    alert("요청되었습니다.");
                }else{
                    alert("요청이 실패했습니다.");
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
    //엑셀 다운로드
    function excel(){
        var list = new Array();
        var name = "<c:out value="${session_user.userGroupName}"/>";

        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            <c:forEach items="${householdDeviceList}" var="DeviceList" varStatus="status">
                var cJson = new Object();
                <c:choose>
                    <c:when test="${DeviceList.bizcoCd eq 'COMAX'}">
                        cJson.홈넷사 = "코맥스";
                    </c:when>
                    <c:when test="${DeviceList.bizcoCd eq 'KOCOM'}">
                        cJson.홈넷사 = "코콤";
                    </c:when>
                    <c:when test="${DeviceList.bizcoCd eq 'HYUNDAITEL'}">
                        cJson.홈넷사 = "현대통신";
                    </c:when>
                    <c:when test="${DeviceList.bizcoCd eq 'ICONTROLS'}">
                        cJson.홈넷사 = "HDC아이콘트롤스";
                    </c:when>
                    <c:when test="${DeviceList.bizcoCd eq 'OTHER'}">
                        cJson.홈넷사 = "기타";
                    </c:when>
                </c:choose>

                cJson.단지 = "<c:out value="${DeviceList.houscplxNm}"/>";
                cJson.동 = "<c:out value="${DeviceList.dongNo}"/>";
                cJson.호 = "<c:out value="${DeviceList.hoseNo}"/>";
                cJson.조명 = "<c:out value="${DeviceList.lights}"/>";
                cJson.밸브 = "<c:out value="${DeviceList.gaslock}"/>";
                cJson.냉방 = "<c:out value="${DeviceList.aircon}"/>";
                cJson.난방 = "<c:out value="${DeviceList.heating}"/>";
                cJson.환기 = "<c:out value="${DeviceList.ventilator}"/>";
                cJson.대기전력 = "<c:out value="${DeviceList.smartConsent}"/>";
                cJson.전동커튼 = "<c:out value="${DeviceList.curtain}"/>";
                cJson.일괄스위치 = "<c:out value="${DeviceList.lightSwitch}"/>";

                list.push(cJson);
            </c:forEach>
        }else{
            <c:forEach items="${householdDeviceList}" var="DeviceList" varStatus="status">
                var cJson = new Object();

                cJson.동 = "<c:out value="${DeviceList.dongNo}"/>";
                cJson.호 = "<c:out value="${DeviceList.hoseNo}"/>";
                cJson.조명 = "<c:out value="${DeviceList.lights}"/>";
                cJson.밸브 = "<c:out value="${DeviceList.gaslock}"/>";
                cJson.냉방 = "<c:out value="${DeviceList.aircon}"/>";
                cJson.난방 = "<c:out value="${DeviceList.heating}"/>";
                cJson.환기 = "<c:out value="${DeviceList.ventilator}"/>";
                cJson.대기전력 = "<c:out value="${DeviceList.smartConsent}"/>";
                cJson.전동커튼 = "<c:out value="${DeviceList.curtain}"/>";
                cJson.일괄스위치 = "<c:out value="${DeviceList.lightSwitch}"/>";

                list.push(cJson);
            </c:forEach>
        }


        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "세대장치 목록";
        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            param.tableHeader = "['홈넷사', '단지', '동', '호', '조명', '밸브', '냉방', '난방', '환기', '대기전력', '전동커튼', '일괄스위치']";
        }else{
            param.tableHeader = "['동', '호', '조명', '밸브', '냉방', '난방', '환기', '대기전력', '전동커튼', '일괄스위치']";
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
                link.download= today+"_세대장치목록.xlsx";
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

    function dong_change(){
        selectList("hose");
    }

</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">세대장치 목록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>세대장치 관리</li>
                <li>세대장치 목록</li>
            </ul>
        </div>
        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
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
                        </tr>
                        <tr>
                            <th>· 단지상태</th>
                            <td>
                                <select name="select_del_yn" id="select_del_yn" class="custom-select">
                                    <option value="N">활성화</option>
                                    <option value="Y">비활성화</option>
                                </select>
                            </td>
                            <td colspan="4">
                                <button type="button" class="btn btn-brown btn-sm" onclick="search();">검색</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
            <div class="search_area">
                <table>
                    <colgroup>
                        <col style="width:8%"/>
                        <col style="width:38%"/>
                        <col style="width:6%"/>
                        <col style="width:15%"/>
                        <col style="width:6%"/>
                        <col style="width:15%"/>
                        <col style="width:12%"/>
                    </colgroup>
                    <tbody>
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
                        <col style="width:30%"/>
                        <col style="width:10%"/>
                        <col style="width:30%"/>
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
                            <td>
                                <button type="button" class="btn btn-brown btn-sm" onclick="search();">검색</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:if>

        <div class="table_wrap">
            <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                <c:if test="${delYn eq 'N'}">
                    <div class="tbl_top_area">
                        <div class="right_area">
                            <button class="btn btn-gray btn-sm" type="button" onclick="all_devicesync_call();">장치정보 동기화</button>
                        </div>
                    </div>
                </c:if>
            </c:if>
            <table class="table" id="table1">
                <thead>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                        <tr>
                            <th rowspan="2" style="vertical-align:middle;">홈넷사</th>
                            <th rowspan="2" style="vertical-align:middle;">단지</th>
                            <th rowspan="2" style="vertical-align:middle;">동</th>
                            <th rowspan="2" style="vertical-align:middle;">호</th>
                            <th colspan="8" style="vertical-align:middle;">장치타입</th>
                            <th rowspan="2" style="vertical-align:middle;">장치 정보 동기화</th>
                        </tr>
                        <tr>
                            <th>조명</th>
                            <th>밸브</th>
                            <th>냉방</th>
                            <th>난방</th>
                            <th>환기</th>
                            <th>대기 전력</th>
                            <th>전동 커튼</th>
                            <th>일괄스위치</th>
                        </tr>
                    </c:if>
                    <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                        <tr>
                            <th rowspan="2" style="vertical-align:middle;">동</th>
                            <th rowspan="2" style="vertical-align:middle;">호</th>
                            <th colspan="8" style="vertical-align:middle;">장치타입</th>
                            <th rowspan="2" style="vertical-align:middle;">장치 정보 동기화</th>
                        </tr>
                        <tr>
                            <th>조명</th>
                            <th>밸브</th>
                            <th>냉방</th>
                            <th>난방</th>
                            <th>환기</th>
                            <th>대기 전력</th>
                            <th>전동 커튼</th>
                            <th>일괄스위치</th>
                        </tr>
                    </c:if>
                </thead>
                <tbody>
                    <c:set value="${householdDeviceList}" var="listdata"/>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                        <c:forEach items="${householdDeviceList}" var="DeviceList" varStatus="status">
                            <tr>
                                <c:choose>
                                    <c:when test="${DeviceList.bizcoCd eq 'COMAX'}">
                                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');">코맥스</a></td>
                                    </c:when>
                                    <c:when test="${DeviceList.bizcoCd eq 'KOCOM'}">
                                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');">코콤</a></td>
                                    </c:when>
                                    <c:when test="${DeviceList.bizcoCd eq 'HYUNDAITEL'}">
                                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');">현대통신</a></td>
                                    </c:when>
                                    <c:when test="${DeviceList.bizcoCd eq 'ICONTROLS'}">
                                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');">HDC 아이콘트롤스</a></td>
                                    </c:when>
                                    <c:when test="${DeviceList.bizcoCd eq 'OTHER'}">
                                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');">기타</a></td>
                                    </c:when>
                                </c:choose>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.houscplxNm}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.dongNo}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.hoseNo}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.lights}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.gaslock}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.aircon}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.heating}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.ventilator}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.smartConsent}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.curtain}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.lightSwitch}"/></a></td>
                                <c:if test="${delYn eq 'N'}">
                                    <td class="text-center"><i class="fas fa-sync-alt" style="cursor:pointer" onclick="api_call('<c:out value="${DeviceList.hmnetId}"/>','<c:out value="${DeviceList.houscplxCd}"/>','<c:out value="${DeviceList.dongNo}"/>','<c:out value="${DeviceList.hoseNo}"/>');"></i></td>
                                </c:if>
                                <c:if test="${delYn eq 'Y'}">
                                    <td class="text-center">-</td>
                                </c:if>

                            </tr>
                            <input type="text" id="hid_${status.index}" value="<c:out value="${DeviceList.hmnetId}"/>" style="display:none;"/>
                        </c:forEach>
                    </c:if>
                    <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                        <c:forEach items="${householdDeviceList}" var="DeviceList" varStatus="status">
                            <tr>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.dongNo}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.hoseNo}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.lights}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.gaslock}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.aircon}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.heating}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.ventilator}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.smartConsent}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.curtain}"/></a></td>
                                <td class="text-center"><a href="#" onclick="click_row('<c:out value="${DeviceList.wpadId}"/>');"><c:out value="${DeviceList.lightSwitch}"/></a></td>
                                <c:if test="${delYn eq 'N'}">
                                    <td class="text-center"><i class="fas fa-sync-alt" style="cursor:pointer" onclick="api_call('<c:out value="${DeviceList.hmnetId}"/>','<c:out value="${DeviceList.houscplxCd}"/>','<c:out value="${DeviceList.dongNo}"/>','<c:out value="${DeviceList.hoseNo}"/>');"></i></td>
                                </c:if>

                                <c:if test="${delYn eq 'Y'}">
                                    <td class="text-center">-</td>
                                </c:if>

                            </tr>
                            <input type="text" id="hid_${status.index}" value="<c:out value="${DeviceList.hmnetId}"/>" style="display:none;"/>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
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
<form:form id="form_list" action="/cm/household/device/search" name="householdDevice" commandName="householdDevice" method="post">
    <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delYn" name="delYn" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detail" action="/cm/household/device/view" name="householdDevice" commandName="householdDevice" method="post">
    <input type="text" id="wpadId" name="wpadId" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delYn_" name="delYn" style="width:0;height:0;visibility:hidden"/>
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