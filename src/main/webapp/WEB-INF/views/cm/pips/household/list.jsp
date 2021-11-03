<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
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
        }

        if(name == "COMPLEX_ADMIN"){
            selectList();

            $("#dongNo").val("<c:out value="${dongNo}"/>");
            $("#hoseNo").val("<c:out value="${hoseNo}"/>");
            var str = "<c:out value="${userNm}"/>";
            str = str.replace(/&lt;/g,"<");
            str = str.replace(/&gt;/g,">");
            $("#userNm").val(str);
            $("#name").val(str);
        }else if(name == "MULTI_COMPLEX_ADMIN"){
            var str = "<c:out value="${userNm}"/>";
            str = str.replace(/&lt;/g,"<");
            str = str.replace(/&gt;/g,">");
            $("#userNm").val(str);
            $("#name").val(str);
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

            var searchingYn = "<c:out value="${searchingYn}"/>";
            if(searchingYn == "Y"){
                selectMultiList("searchingYn");
            }else{
                selectMultiList();
            }
        }


    });

    //단지관리자 동, 호 가져오기
    function selectList(){
        var param = new Object();
        param.delYn = "N";

        var dong = "<c:out value="${dongNo}"/>";
        var hose = "<c:out value="${hoseNo}"/>";

        $.ajax({
            url: '/cm/common/household/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#dong").empty();
                $("#hose").empty();
                if(dong == "all"){
                    $("#dong").append("<option value='all' selected>전체</option>");
                }else{
                    $("#dong").append("<option value='all'>전체</option>");
                }
                if(hose == "all"){
                    $("#hose").append("<option value='all' selected>전체</option>");
                }else{
                    $("#hose").append("<option value='all'>전체</option>");
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
                     if(hose == item.hoseNo){
                        $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                     }else{
                        $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                     }
                })
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }

    //멀티 단지 관리자 단지명 선택했을경우 동,호 리스트 가져오기
    //단지명 선택했을경우 동,호 리스트 가져오기
    function selectMultiList(gubun){
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

    function click_row(id,hid){
        $("#userId").val(id);
        $("#hsholdId").val(hid);
        $("#form_list").submit();
    }

    function btn_search(){
        var RegExp = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
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

        $("#form_search").submit();

    }

    function dong_change(){
        selectMultiList("hose");
    }

    //단지 관리자 엑셀다운
    function excel(){
        var list = new Array();

        <c:forEach items="${pipsUserList}" var="list" varStatus="status">
            var cJson = new Object();
            <c:choose>
                <c:when test="${list.dongNo eq ''}">
                    cJson.동 = "-";
                </c:when>
                <c:otherwise>
                    cJson.동 = '<c:out value="${list.dongNo}"/>';
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${list.hoseNo eq ''}">
                    cJson.호 = "-";
                </c:when>
                <c:otherwise>
                    cJson.호 = '<c:out value="${list.hoseNo}"/>';
                </c:otherwise>
            </c:choose>
            cJson.아이디 = '<c:out value="${list.userId}"/>';
            cJson.이름 = '<c:out value="${list.userNm}"/>';
            <c:choose>
                <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                    cJson.회원구분 = "가족대표";
                </c:when>
                <c:when test="${list.fmlyTpCd eq 'FAMILY'}">
                    cJson.회원구분 = "구성원";
                </c:when>
                <c:when test="${list.fmlyTpCd eq ''}">
                    cJson.회원구분 = "일반회원";
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${list.useYn eq 'Y'}">
                    cJson.로그인허용 = "YES";
                </c:when>
                <c:when test="${list.useYn eq 'N' || list.useYn eq ''}">
                    cJson.로그인허용 = "NO";
                </c:when>
            </c:choose>
            cJson.가입유형 = '<c:out value="${list.certfTpCd}"/>';
            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "세대정보 목록";
        param.tableHeader = "['동', '호', '아이디', '이름', '회원구분', '로그인허용', '가입유형']";
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
                link.download= today+"_세대정보 목록.xlsx";
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

    //멀티 단지 관리자 엑셀다운
    function multiExcel(){
        var list = new Array();

        <c:forEach items="${pipsUserList}" var="list" varStatus="status">
            var cJson = new Object();
            <c:choose>
                <c:when test="${list.houscplxNm eq ''}">
                    cJson.단지명 = "-";
                </c:when>
                <c:otherwise>
                    cJson.단지명 = '<c:out value="${list.houscplxNm}"/>';
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${list.dongNo eq ''}">
                    cJson.동 = "-";
                </c:when>
                <c:otherwise>
                    cJson.동 = '<c:out value="${list.dongNo}"/>';
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${list.hoseNo eq ''}">
                    cJson.호 = "-";
                </c:when>
                <c:otherwise>
                    cJson.호 = '<c:out value="${list.hoseNo}"/>';
                </c:otherwise>
            </c:choose>
            cJson.아이디 = '<c:out value="${list.userId}"/>';
            cJson.이름 = '<c:out value="${list.userNm}"/>';
            <c:choose>
                <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                    cJson.회원구분 = "가족대표";
                </c:when>
                <c:when test="${list.fmlyTpCd eq 'FAMILY'}">
                    cJson.회원구분 = "구성원";
                </c:when>
                <c:when test="${list.fmlyTpCd eq ''}">
                    cJson.회원구분 = "일반회원";
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${list.useYn eq 'Y'}">
                    cJson.로그인허용 = "YES";
                </c:when>
                <c:when test="${list.useYn eq 'N' || list.useYn eq ''}">
                    cJson.로그인허용 = "NO";
                </c:when>
            </c:choose>
            cJson.가입유형 = '<c:out value="${list.certfTpCd}"/>';
            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "세대정보 목록";
        param.tableHeader = "['단지명', '동', '호', '아이디', '이름', '회원구분', '로그인허용', '가입유형']";
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
                link.download= today+"_세대정보 목록.xlsx";
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
            selectMultiList("dong");
        }
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //입주민 탈퇴버튼
    function btn_action(e){
        var check = document.getElementsByName('check');
        var list = new Array();
        var flag = "NO";
        var CANCEL_TP_CD = "NO";
        for(var i = 0 ; i < check.length ; i++){
            if(check[i].checked){
                var str = $("input[name='check']").eq(i).attr("value").split("/");
                var jsonObject = new Object();
                jsonObject.userId = str[0];
                jsonObject.cancelTpCd = e;
                jsonObject.fmlyTpCd = str[1];
                jsonObject.hsholdId = str[2];
                list.push(jsonObject);
                flag = "YES";

                if(str[1] == "REPRESENTATIVE"){
                    var hsholdId = str[2];
                    $.ajax({
                        type: "post",
                        url: '/cm/household/user/secessionCheck',
                        async:false,
                        data: {"hsholdId": hsholdId},
                        success: function(data){
                            if(data == "ok"){
                                CANCEL_TP_CD = "NO";
                            }else if(data == "no"){
                                CANCEL_TP_CD = "REPRESENTATIVE";
                            }
                        }
                    });
                }
            }
        }

        if(flag == "NO"){
            alert("입주민 탈퇴시킬 아이디를 선택해 주세요.");
            return;
        }
        if(CANCEL_TP_CD == "REPRESENTATIVE"){
            alert("탈퇴시킬 회원에 가족대표가 포함되어있습니다. 해당 회원정보 수정화면에서 가족대표를 변경후 탈퇴 시키시기 바랍니다.");
            return;
        }

        var conf = confirm("선택한 아이디 모두 입주민탈퇴 실행하시겠습니까?");

        if(conf == true){
            var userList = JSON.stringify(list);

            var param = new Object();
            param.userList = userList;
            $.ajax({
                url: '/cm/api/user',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    var return_data = JSON.stringify(data);
                    return_data = JSON.parse(return_data);

                    window.location.reload();

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
        <div class="top_area">
            <h2 class="tit">세대정보 목록</h2>
            <ul class="location">
                <li>세대 관리</li>
                <li>세대정보 관리</li>
                <li>세대정보 목록</li>
            </ul>
        </div>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 동</th>
                        <td style="width:200px;">
                            <select name="dong" id="dong" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                        <th>· 호</th>
                        <td style="width:200px;">
                            <select name="hose" id="hose" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                        <th>· 이름</th>
                        <td style="width:200px;">
                            <input type="text" id="name" class="form-control" />
                        </td>
                        <td style="width:100px;">
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </c:if>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 단지명</th>
                        <td style="width:400px;">
                            <div class="input-group">
                                <input type="text" class="form-control" disabled id="home_name"/>
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
                                </div>
                            </div>
                        </td>
                        <th>· 동</th>
                        <td style="width:200px;">
                            <select name="dong" id="dong" class="custom-select" onchange="dong_change();">
                                <option value="all">전체</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>· 이름</th>
                        <td style="width:400px;">
                            <input type="text" id="name" class="form-control" />
                        </td>
                        <th>· 호</th>
                        <td style="width:200px;">
                            <select name="hose" id="hose" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                        <td style="width:100px;">
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </c:if>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" onclick="checkall();" id="checkAll"/>
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">회원구분</th>
                        <th scope="col">로그인 허용</th>
                        <th scope="col">승인여부</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${pipsUserList}" var="pipsUser" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${pipsUser.userId}"/>/<c:out value="${pipsUser.fmlyTpCd}"/>/<c:out value="${pipsUser.hsholdId}"/>">
                                <label class="custom-control-label" for="check${status.index}"></label>
                            </div>
                        </td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.dongNo}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.hoseNo}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.userId}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.userNm}"/></a></td>
                        <c:choose>
                        <c:when test="${pipsUser.fmlyTpCd eq 'REPRESENTATIVE'}">
                            <td class="text-center">가족대표</td>
                        </c:when>
                        <c:when test="${pipsUser.fmlyTpCd eq 'FAMILY'}">
                            <td class="text-center">구성원</td>
                        </c:when>
                        </c:choose>
                        <c:choose>
                        <c:when test="${pipsUser.useYn eq 'Y'}">
                            <td class="text-center">YES</td>
                        </c:when>
                        <c:when test="${pipsUser.useYn eq 'N'}">
                            <td class="text-center">NO</td>
                        </c:when>
                        </c:choose>
                        <c:choose>
                        <c:when test="${pipsUser.apprStsCd eq 'APPROVAL'}">
                            <td class="text-center">승인</td>
                        </c:when>
                        <c:when test="${pipsUser.apprStsCd eq 'REJECT'}">
                            <td class="text-center">반려</td>
                        </c:when>
                        <c:when test="${pipsUser.apprStsCd eq 'REQUEST'}">
                            <td class="text-center">요청</td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">-</td>
                        </c:otherwise>
                        </c:choose>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.certfTpCd}"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-bluegreen" type="button" onclick="btn_action('RESIDENT');">입주민탈퇴</button>
                </div>
            </div>
        </div>
        </c:if>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="multiExcel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
            <table class="table" id="table1">
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
                        <th scope="col">로그인 허용</th>
                        <th scope="col">승인여부</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${pipsUserList}" var="pipsUser" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${pipsUser.userId}"/>/<c:out value="${pipsUser.fmlyTpCd}"/>/<c:out value="${pipsUser.hsholdId}"/>">
                                <label class="custom-control-label" for="check${status.index}"></label>
                            </div>
                        </td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.houscplxNm}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.dongNo}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.hoseNo}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.userId}"/></a></td>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.userNm}"/></a></td>
                        <c:choose>
                        <c:when test="${pipsUser.fmlyTpCd eq 'REPRESENTATIVE'}">
                            <td class="text-center">가족대표</td>
                        </c:when>
                        <c:when test="${pipsUser.fmlyTpCd eq 'FAMILY'}">
                            <td class="text-center">구성원</td>
                        </c:when>
                        </c:choose>
                        <c:choose>
                        <c:when test="${pipsUser.useYn eq 'Y'}">
                            <td class="text-center">YES</td>
                        </c:when>
                        <c:when test="${pipsUser.useYn eq 'N'}">
                            <td class="text-center">NO</td>
                        </c:when>
                        </c:choose>
                        <c:choose>
                        <c:when test="${pipsUser.apprStsCd eq 'APPROVAL'}">
                            <td class="text-center">승인</td>
                        </c:when>
                        <c:when test="${pipsUser.apprStsCd eq 'REJECT'}">
                            <td class="text-center">반려</td>
                        </c:when>
                        <c:when test="${pipsUser.apprStsCd eq 'REQUEST'}">
                            <td class="text-center">요청</td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">-</td>
                        </c:otherwise>
                        </c:choose>
                        <td class="text-center"><a href="#" onclick="click_row('<c:out value="${pipsUser.userId}"/>','<c:out value="${pipsUser.hsholdId}"/>')"><c:out value="${pipsUser.certfTpCd}"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-bluegreen" type="button" onclick="btn_action('RESIDENT');">입주민탈퇴</button>
                </div>
            </div>
        </div>
        </c:if>



    </div>
</div>

<form:form id="form_list" action="/cm/household/user/edit" name="household" commandName="household" method="post">
      <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/household/user/list" name="household" commandName="household" method="post">
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userNm" name="userNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
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
