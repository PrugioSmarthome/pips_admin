<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">

    var checkList = new Array();

    $(document).on('change', 'input[name="check"]', function(){
        if($(this).is(":checked")){
            var val = $(this).val();
            checkList.push(val);
        } else{
            var val = $(this).val();
            for(let i = 0; i < checkList.length; i++) {
              if(checkList[i] == val) {
                checkList.splice(i, 1);
                i--;
              }
            }
        }
    });

    $(document).ready(function(){
        $('#table1').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            "columnDefs": [
                { "width": "6%", "targets": 0 },
                { "width": "20%", "targets": 1 },
                { "orderable": false, "targets": 0 }
            ]
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

        var searchingYn = "<c:out value="${searchingYn}"/>";

        var name = "<c:out value="${session_user.userGroupName}"/>";
        if(name == "COMPLEX_ADMIN"){
            if(searchingYn == "Y"){
                selectList("searchingYn");
            }else{
                selectList();
            }
        }

        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxNm_").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#dongNo").val("<c:out value="${dongNo}"/>");
        $("#hoseNo").val("<c:out value="${hoseNo}"/>");
        var str = "<c:out value="${userNm}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        $("#userNm").val(str);
        $("#name").val(str);
        var name = "<c:out value="${houscplxNm}"/>";
        if(name != "" || name != "전체"){
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
        var name = "<c:out value="${session_user.userGroupName}"/>";
        if(name == "COMPLEX_ADMIN"){
            param.houscplxCd = "<c:out value="${session_user.houscplxCd}"/>";
        }else{
            param.houscplxCd = $("#houscplxCd").val();
        }

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
                    var temp = new Array();
                    $.each(data, function(i, item){
                        if ($.inArray(item.dongNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.dongNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[i]){
                                $("#dong").append("<option selected value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                            }else{
                                $("#dong").append("<option value='"+item.dongNo+"'>"+item.dongNo+"</option>");
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
                            if(dong == temp[i]){
                                $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                            }else{
                                $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
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
                console.log("에러");
                console.log(e.responseText.trim());
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
        if(strarray[1] == "전체"){
            $("#houscplxCd").val("");
        }else{
            $("#houscplxCd").val(strarray[0]);
        }

        $("#closebtn").click();
        if(strarray[1] != "전체"){
            selectList("dong");
//            selectList();
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
        $("#houscplxNm_").val($("#houscplxNm").val());
        $("#form_list").submit();
    }
    //검색버튼 눌렀을경우 유효성 체크
    function isValid(){
        if($("#houscplxNm") == ""){
            alert("단지명을 선택해주세요.");
            return false;
        }

        return true;
    }

    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('check');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                checkList.push(check[i].value);
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;

                for(let j = 0; j < checkList.length; j++) {
                    if(checkList[j] == check[i].value) {
                        checkList.splice(j, 1);
                        j--;
                    }
                }
            }
        }
    }


    //승인,반려 버튼
    function btn_apicall(e){
        $("#checkList").val(checkList);
        var check = $("#checkList").val().split(",");
        var list = new Array();
        var flag = "NO";

        if(check != "" && check != null){
            for(var i = 0 ; i < check.length ; i++){
                var str = check[i].split("/");
                var jsonObject = new Object();
                jsonObject.userId = str[0];
                jsonObject.hsholdId = str[1];
                jsonObject.apprStsCd = e;
                list.push(jsonObject);
                flag = "YES";
            }
        }

        if(flag == "NO"){
            alert("세대원을 선택해주세요.");
            return;
        }

        var userList = JSON.stringify(list);
        var param = new Object();
        param.userList = userList;

        $("#btnReject").prop('disabled', true);
        $("#btnApproval").prop('disabled', true);

        $.ajax({
            url: '/cm/api/device/regi',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                console.log(data);
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
                $("#btnReject").prop('disabled', false);
                $("#btnApproval").prop('disabled', false);
                window.location.reload();
            },
            complete: function() {
            }
        });

        setTimeout(() => window.location.reload(), 5000);

    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    function dong_change(){
        selectList("hose");
//        $("#hose").val("all");
    }

    function excel(){
        var list = new Array();
        var name =  "<c:out value="${session_user.userGroupName}"/>";

        if(name == "MULTI_COMPLEX_ADMIN"){
            <c:forEach items="${householderList}" var="householder" varStatus="status">
                var cJson = new Object();
                cJson.가족대표요청일 = "<fmt:formatDate value='${householder.apprDemDt}' pattern='yyyy-MM-dd'/>";
                cJson.단지명 = "<c:out value="${householder.houscplxNm}"/>";
                cJson.동 = "<c:out value="${householder.dongNo}"/>";
                cJson.호 = "<c:out value="${householder.hoseNo}"/>";
                cJson.아이디 = "<c:out value="${householder.userId}"/>";
                cJson.이름 = "<c:out value="${householder.userNm}"/>";
                cJson.가입유형 = "<c:out value="${householder.certfTpCd}"/>";

                list.push(cJson);
            </c:forEach>
        }else if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            <c:forEach items="${householderList}" var="householder" varStatus="status">
                var cJson = new Object();
                cJson.가족대표요청일 = "<fmt:formatDate value='${householder.apprDemDt}' pattern='yyyy-MM-dd'/>";
                cJson.단지명 = "<c:out value="${householder.houscplxNm}"/>";
                cJson.동 = "<c:out value="${householder.dongNo}"/>";
                cJson.호 = "<c:out value="${householder.hoseNo}"/>";
                cJson.아이디 = "<c:out value="${householder.userId}"/>";
                cJson.이름 = "<c:out value="${householder.userNm}"/>";
                cJson.가입유형 = "<c:out value="${householder.certfTpCd}"/>";

                list.push(cJson);
            </c:forEach>
        }else{
            <c:forEach items="${householderList}" var="householder" varStatus="status">
                var cJson = new Object();
                cJson.가족대표요청일 = "<fmt:formatDate value='${householder.apprDemDt}' pattern='yyyy-MM-dd'/>";;
                cJson.동 = "<c:out value="${householder.dongNo}"/>";
                cJson.호 = "<c:out value="${householder.hoseNo}"/>";
                cJson.아이디 = "<c:out value="${householder.userId}"/>";
                cJson.이름 = "<c:out value="${householder.userNm}"/>";
                cJson.가입유형 = "<c:out value="${householder.certfTpCd}"/>";

                list.push(cJson);
            </c:forEach>
        }

        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "세대구성신청목록";

        if(name == "MULTI_COMPLEX_ADMIN"){
            param.tableHeader = "['가족대표요청일', '단지명', '동', '호', '아이디', '이름', '가입유형']";
        }else if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            param.tableHeader = "['가족대표요청일', '단지명', '동', '호', '아이디', '이름', '가입유형']";
        }else{
            param.tableHeader = "['가족대표요청일', '동', '호', '아이디', '이름', '가입유형']";
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

                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_세대구성신청목록.xlsx";
                link.click();
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">세대구성 신청 목록</h2>
            <ul class="location">
                <li>회원 관리</li>
                <li>세대구성 신청정보</li>
                <li>세대구성 신청 목록</li>
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
                                <input type="text" id="houscplxNm" class="form-control" disabled />
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
                        <th>· 이름</th>
                        <td>
                            <input type="text" id="name" class="form-control" />
                        </td>
                        <td colspan="4">
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
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
                    <col style="width:20%"/>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                    <col style="width:10%"/>
                <col style="width:20%"/>
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
                        <th>· 이름</th>
                        <td>
                            <input type="text" class="form-control" id="name"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </c:if>

        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll" onclick="checkall();">
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">가족대표 요청일</th>
                        <th scope="col">단지명</th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </c:if>
                <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll" onclick="checkall();">
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">가족대표 요청일</th>
                        <th scope="col">단지명</th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </c:if>
                <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll" onclick="checkall();">
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">가족대표 요청일</th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </c:if>
                </thead>
                <tbody>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <c:forEach items="${householderList}" var="householder" varStatus="status">
                        <tr>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${householder.userId}"/>/<c:out value="${householder.hsholdId}"/>"/>
                                    <label class="custom-control-label" for="check${status.index}"></label>
                                </div>
                            </td>
                            <td class="text-center"><fmt:formatDate value="${householder.apprDemDt}" pattern="yyyy-MM-dd"/></td>
                            <td class="text-center"><c:out value="${householder.houscplxNm}"/></td>
                            <td class="text-center"><c:out value="${householder.dongNo}"/></td>
                            <td class="text-center"><c:out value="${householder.hoseNo}"/></td>
                            <td class="text-center"><c:out value="${householder.userId}"/></td>
                            <td class="text-center"><c:out value="${householder.userNm}"/></td>
                            <td class="text-center"><c:out value="${householder.certfTpCd}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                    <c:forEach items="${householderList}" var="householder" varStatus="status">
                        <tr>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${householder.userId}"/>/<c:out value="${householder.hsholdId}"/>"/>
                                    <label class="custom-control-label" for="check${status.index}"></label>
                                </div>
                            </td>
                            <td class="text-center"><fmt:formatDate value="${householder.apprDemDt}" pattern="yyyy-MM-dd"/></td>
                            <td class="text-center"><c:out value="${householder.houscplxNm}"/></td>
                            <td class="text-center"><c:out value="${householder.dongNo}"/></td>
                            <td class="text-center"><c:out value="${householder.hoseNo}"/></td>
                            <td class="text-center"><c:out value="${householder.userId}"/></td>
                            <td class="text-center"><c:out value="${householder.userNm}"/></td>
                            <td class="text-center"><c:out value="${householder.certfTpCd}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                    <c:forEach items="${householderList}" var="householder" varStatus="status">
                        <tr>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${householder.userId}"/>/<c:out value="${householder.hsholdId}"/>"/>
                                    <label class="custom-control-label" for="check${status.index}"></label>
                                </div>
                            </td>
                            <td class="text-center"><fmt:formatDate value="${householder.apprDemDt}" pattern="yyyy-MM-dd"/></td>
                            <td class="text-center"><c:out value="${householder.dongNo}"/></td>
                            <td class="text-center"><c:out value="${householder.hoseNo}"/></td>
                            <td class="text-center"><c:out value="${householder.userId}"/></td>
                            <td class="text-center"><c:out value="${householder.userNm}"/></td>
                            <td class="text-center"><c:out value="${householder.certfTpCd}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>

                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button id="btnReject" class="btn btn-gray" type="button" onclick="btn_apicall('REJECT')">반려</button>
                    <button id="btnApproval" class="btn btn-bluegreen" type="button" onclick="btn_apicall('APPROVAL')">승인</button>
                </div>
            </div>
        </div>

    </div>
</div>

<input type="text" id="checkList" name="checkList" style="width:0;height:0;visibility:hidden"/>
<form:form id="form_list" action="/cm/household/group/list" name="household" commandName="household" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm_" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userNm" name="userNm" style="width:0;height:0;visibility:hidden"/>
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