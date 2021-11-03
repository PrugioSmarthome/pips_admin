<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
        $("#houscplxCdGoList").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxCdGoListNm").val("<c:out value="${houscplxNm}"/>");
        var houscplxCdGoList = "<c:out value="${houscplxCdGoList}"/>";
        var houscplxCdGoListNm = "<c:out value="${houscplxCdGoListNm}"/>";
        if(houscplxCdGoListNm != ""){
            $("#houscplxNm").val(houscplxCdGoListNm);
            $("#houscplxCd").val(houscplxCdGoList);
            $("#houscplxCdGoList").val("<c:out value="${houscplxCdGoList}"/>");
            $("#houscplxCdGoListNm").val("<c:out value="${houscplxCdGoListNm}"/>");
        }
        $("#user").val("<c:out value="${userTpCd}"/>");
        $("#userTpCd").val("<c:out value="${userTpCd}"/>");
        $("#appr").val("<c:out value="${apprStsCd}"/>");
        $("#apprStsCd").val("<c:out value="${apprStsCd}"/>");
        var str = "<c:out value="${userNm}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        $("#name").val(str);
        $("#userNm").val(str);
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
        $("#houscplxCdGoListNm").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#houscplxCd").val("all");
        }else{
            $("#houscplxCd").val(strarray[0]);
            $("#houscplxCdGoList").val(strarray[0]);
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
        $("#apprStsCd").val($("#appr").val());
        $("#houscplxNm_").val($("#houscplxNm").val());
        $("#form_search").submit();
    }

    function excel(){
        var list = new Array();

        <c:forEach items="${userList}" var="list" varStatus="status">
            var cJson = new Object();
            <c:choose>
                <c:when test="${list.houscplxNm eq ''}">
                    cJson.단지명 = "-";
                </c:when>
                <c:otherwise>
                    cJson.단지명 = "<c:out value="${list.houscplxNm}"/>";
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${list.dongNo eq ''}">
                    cJson.동 = "-";
                </c:when>
                <c:otherwise>
                    cJson.동 = "<c:out value="${list.dongNo}"/>";
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${list.hoseNo eq ''}">
                    cJson.호 = "-";
                </c:when>
                <c:otherwise>
                    cJson.호 = "<c:out value="${list.hoseNo}"/>";
                </c:otherwise>
            </c:choose>
            cJson.아이디 = "<c:out value="${list.userId}"/>";

            var userName = "<c:out value="${list.userNm}"/>";
            userName = userName.replace(/&#034;/gi, "");

            cJson.이름 = userName;
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
            <c:choose>
                <c:when test="${list.apprStsCd eq 'APPROVAL'}">
                    cJson.승인여부 = "승인";
                </c:when>
                <c:when test="${list.apprStsCd eq 'REJECT'}">
                    cJson.승인여부 = "반려";
                </c:when>
                <c:when test="${list.apprStsCd eq 'REQUEST'}">
                    cJson.승인여부 = "요청";
                </c:when>
                <c:otherwise>
                    cJson.승인여부 = "-";
                </c:otherwise>
            </c:choose>
            cJson.가입유형 = "<c:out value="${list.certfTpCd}"/>";
            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "회원정보 목록";
        param.tableHeader = "['단지명', '동', '호', '아이디', '이름', '회원구분', '로그인허용', '승인여부', '가입유형']";
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
                link.download= today+"_회원정보 목록.xlsx";
                link.click();
            },
            error: function(e){

            },
            complete: function() {
            }
        });
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


    //회원,입주민 탈퇴버튼
    function btn_action(e){
        var table = $("#table1").DataTable();
        var count = table.data().length;
        $("#checkList").val(checkList);
        var check = $("#checkList").val().split(",");
        var list = new Array();
        var flag = "NO";
        var CANCEL_TP_CD = "NO";
        var hsholdid = new Array();

        if(check != "" && check != null){
            for(var i = 0 ; i < check.length ; i++){
                var str = check[i].split("/");
                var jsonObject = new Object();
                jsonObject.userId = str[0];
                jsonObject.cancelTpCd = e;
                jsonObject.fmlyTpCd = str[1];
                jsonObject.hsholdId = str[2];
                list.push(jsonObject);
                flag = "YES";
                if(e == "RESIDENT" && str[1] == ""){
                    CANCEL_TP_CD = "YES";
                }
                if(str[1] == "REPRESENTATIVE"){
                    CANCEL_TP_CD = "REPRESENTATIVE";
                    hsholdid.push(str[2]);
                }
            }
        }

        if(flag == "NO"){
            alert("탈퇴시킬 회원을 선택해주세요.");
            return;
        }
        if(CANCEL_TP_CD == "YES"){
            alert("선택한 회원에 비 입주민 회원이 포함되어있습니다. 입주민 회원만 선택해 주세요.");
            return;
        }
        if(CANCEL_TP_CD == "REPRESENTATIVE"){
            if(hsholdid.length > 1){
                alert("탈퇴시킬 회원에 가족대표가 하나이상 포함되어 있습니다.");
                return;
            }else{
                var usercheck;
                var str = hsholdid[0];
                str = str.split(".");
                var param = new Object();
                param.houscplxCd = str[0];
                param.dongNo = str[1];
                param.hoseNo = str[2];
                $.ajax({
                    url: '/cm/pips/user/usercheck',
                    type: 'POST',
                    async: false,
                    data: param,
                    dataType : "json",
                    success: function(data){
                        if(data == 1){
                            console.log("구성원이 없는 가족대표");
                            usercheck = "true";
                        }else{
                            alert("탈퇴시킬 회원에 가족대표가 포함되어있습니다. 해당 회원정보 수정화면에서 가족대표를 변경후 탈퇴 시키시기 바랍니다.");
                            usercheck = "false";
                        }
                    },
                    error: function(e){
                        console.log("에러");
                        console.log(e.responseText.trim());
                    },
                    complete: function() {
                    }
                });
                if(usercheck == "false"){
                    return;
                }
            }
        }

        var conf;
        if(e == "MEMBER"){
            conf = confirm("선택한 아이디 모두 탈퇴 하시겠습니까?")
        }

        if(e == "RESIDENT"){
            conf = confirm("선택한 아이디 모두 입주민탈퇴 실행하시겠습니까?")
        }

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
                    window.location.reload();
                },
                error: function(e){

                },
                complete: function() {
                }
            });
        }
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }



</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">회원정보 목록</h2>
            <ul class="location">
                <li>회원 관리</li>
                <li>회원정보 관리</li>
                <li>회원정보 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:8%"/>
                    <col style="width:34%"/>
                    <col style="width:10%"/>
                    <col style="width:14%"/>
                    <col style="width:10%"/>
                    <col style="width:14%"/>
                    <col style="width:10%"/>
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
                        <th>· 이름</th>
                        <td>
                            <input type="text" class="form-control" id="name"/>
                        </td>
                        <th>· 회원구분</th>
                        <td>
                            <select name="user" id="user" class="custom-select">
                                <option value="all">전체</option>
                                <option value="RESIDENT">입주민</option>
                                <option value="NORMAL">일반회원</option>
                            </select>
                        </td>
                        <th>· 승인여부</th>
                        <td>
                            <select name="appr" id="appr" class="custom-select">
                                <option value="all">전체</option>
                                <option value="APPROVAL">승인</option>
                                <option value="REJECT">반려</option>
                                <option value="REQUEST">요청</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <br/>
        <div class="table_wrap">
            <div class="float-right" style="height:50px">
                <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
            </div>
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:5%"/>
                    <col style="width:16%"/>
                    <col style="width:6%"/>
                    <col style="width:6%"/>
                    <col style="width:15%"/>
                    <col style="width:10%"/>
                    <col style="width:10%"/>
                    <col style="width:12%"/>
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
                        <th scope="col">로그인허용</th>
                        <th scope="col">승인여부</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${list.userId}"/>/<c:out value="${list.fmlyTpCd}"/>/<c:out value="${list.hsholdId}"/>"/>
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
                        <c:choose>
                            <c:when test="${list.useYn eq 'Y'}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">YES</a></td>
                            </c:when>
                            <c:when test="${list.useYn eq 'N' || list.useYn eq ''}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">NO</a></td>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.apprStsCd eq 'APPROVAL'}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">승인</a></td>
                            </c:when>
                            <c:when test="${list.apprStsCd eq 'REJECT'}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">반려</a></td>
                            </c:when>
                            <c:when test="${list.apprStsCd eq 'REQUEST'}">
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">요청</a></td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');">-</a></td>
                            </c:otherwise>
                        </c:choose>
                        <td class="text-center"><a href="#" onclick="edit_page('<c:out value="${list.userId}"/>','<c:out value="${list.hsholdId}"/>');"><c:out value="${list.certfTpCd}"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-gray" type="button" onclick="btn_action('MEMBER')">회원탈퇴</button>
                    <button class="btn btn-bluegreen" type="button" onclick="btn_action('RESIDENT')">입주민탈퇴</button>
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

<input type="text" id="checkList" name="checkList" style="width:0;height:0;visibility:hidden"/>
<form:form id="form_search" action="/cm/pips/user/search" name="info" commandName="info" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm_" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userTpCd" name="userTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="apprStsCd" name="apprStsCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userNm" name="userNm" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_edit" action="/cm/pips/user/edit" name="info" commandName="info" method="post">
      <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCdGoList" name="houscplxCdGoList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCdGoListNm" name="houscplxCdGoListNm" style="width:0;height:0;visibility:hidden"/>
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