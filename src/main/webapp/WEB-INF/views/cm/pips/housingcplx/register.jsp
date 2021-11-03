<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

        webapplist($("#webappnum").val());

        $("#serviceLinkDelete").click();
    });

    function webapplist(num){
        $.ajax({
            url: '/cm/housingcplx/info/serviceLinkType/list',
            type: 'POST',
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    $("#webapplist_"+num+"").append("<option value='"+item.commCd+"'>"+item.commCdNm+"</option>")
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

    function select(n,e){
        var param = new Object();
        param.lnkSvcGrpTpCd = $(n).val();
        var str = e.split("_");

        $.ajax({
            url: '/cm/housingcplx/info/serviceLinkInfo/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#servicelist_"+str[1]+"").empty();
                $("#servicelist_"+str[1]+"").append("<option value='all'>선택</option>");
                $.each(data, function(i, item){
                    $("#servicelist_"+str[1]+"").append("<option value='"+item.lnkSvcId+"'>"+item.lnkSvcNm+"</option>")
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

    //홈넷서버 리스트
    function chang(){
        var param = new Object();
        param.svrTpCd = $("#tpcd").val();
        $.ajax({
            url: '/cm/housingcplx/info/homenet/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#hmnet").empty();
                $("#hmnet").append("<option value='null'>선택</option>");
                console.log(data);
                $.each(data, function(i, item){
                    $("#hmnet").append("<option value='"+item.hmnetId+"'>"+item.hmnetNm+"</option>");
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

    //홈넷서버 사용유무 체크
    function hmnetsvrcheck(){
        if($("#tpcd").val() == "HOUSCPLX_SVR"){
            var param = new Object();
            param.svrTpCd = $("#tpcd").val();
            param.hmnetId = $("#hmnet").val();

            $.ajax({
                url: '/cm/housingcplx/info/checkHomenetInfo',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    if(data.status == true){
                        alert("선택하신 홈넷서버는 사용이 가능합니다.");
                    }
                    if(data.status == false){
                        alert(data.params[0].msg);
                        $("#hmnet").val("null");
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
    }

    //취소
    function back(){
        location.href = "/cm/housingcplx/info/list";
    }
    //저장
    function next(){
        $("#bizcoCd").val($("#biz").val());
        $("#heatTpCd").val($("#heat").val());
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var RegExp2 = /[\,;:|*~`!\-_+┼<>@\#$%&\'\"\\\=]/gi;
        if(RegExp.test($("#houscplxNm").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if($("#houscplxNm").val() == ""){
            alert("단지명칭을 입력해 주세요");
            return;
        }
        if(RegExp2.test($("#screenHouscplxNm").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if($("#screenHouscplxNm").val() == ""){
            alert("화면표시단지명을 입력해 주세요");
            return;
        }
        if($("#postNo").val() == "" || $("#roadnmBasAddrCont").val() == "" || $("#areaBasAddrCont").val() == ""){
            alert("단지주소를 입력해 주세요");
            return;
        }
        if($("#landDimQty").val() == ""){
            alert("대지면적을 입력해 주세요");
            return;
        }
        if($("#archDimQty").val() == ""){
            alert("연면적을 입력해 주세요");
            return;
        }
        /*
        if($("#busiApprYmd").val() == ""){
            alert("사업승인일을 입력해 주세요");
            return;
        }
        */
        if($("#busiConendYmd").val() == ""){
            alert("사업준공일을 입력해 주세요");
            return;
        }
        if($("#wholeHsholdCnt").val() == ""){
            alert("총세대수를 입력해 주세요");
            return;
        }
        if($("#wholeDongCnt").val() == ""){
            alert("총동수를 입력해 주세요");
            return;
        }
        if($("#lwstUnGrFlrcnt").val() == ""){
            alert("지하층을 입력해 주세요");
            return;
        }
        if($("#hgstAbgrFlrcnt").val() == ""){
            alert("지상층을 입력해 주세요");
            return;
        }
        if($("#hmnet").val() == "null"){
            alert("홈넷서버를 선택해 주세요");
            return;
        }
        if($("#heat").val() == "null"){
            alert("난방방식을 선택해 주세요");
            return;
        }
        if($("#wholeWlLcnt").val() == ""){
            alert("전체 주차대수를 입력해 주세요");
            return;
        }
        if($("#hsholdWlLcnt").val() == ""){
            alert("세대당 주차대수를 입력해 주세요");
            return;
        }

        $("#hmnetId").val($("#hmnet").val());
        $("#heatTpCd").val($("#heat").val());

        $('.step1').css('display', 'none');
        $('.step2').css('display', 'block');
    }

    function goPopup(){
    	// 주소검색을 수행할 팝업 페이지를 호출합니다.
    	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrCoordUrl.do)를 호출하게 됩니다.
    	var pop = window.open("/cm/housingcplx/info/search/addressService","pop","width=570,height=420, scrollbars=yes, resizable=yes");
    }


    function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo,entX,entY){
    		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
    		var sido = "";
    		var sigungu = "";
    		sigungu = sggNm.split(" ");

    		if(siNm == "서울특별시"){sido = "서울"}
    		if(siNm == "부산광역시"){sido = "부산"}
    		if(siNm == "대구광역시"){sido = "대구"}
    		if(siNm == "인천광역시"){sido = "인천"}
    		if(siNm == "광주광역시"){sido = "광주"}
    		if(siNm == "대전광역시"){sido = "대전"}
    		if(siNm == "울산광역시"){sido = "울산"}
    		if(siNm == "경기도"){sido = "경기"}
    		if(siNm == "강원도"){sido = "강원"}
    		if(siNm == "충청북도"){sido = "충북"}
    		if(siNm == "충청남도"){sido = "충남"}
    		if(siNm == "전라북도"){sido = "전북"}
    		if(siNm == "전라남도"){sido = "전남"}
    		if(siNm == "경상북도"){sido = "경북"}
    		if(siNm == "경상남도"){sido = "경남"}
    		if(siNm == "제주특별자치도"){sido = "제주"}
    		if(siNm == "세종특별자치시"){sido = "세종"}

    		if(sigungu.length > 1){
                $("#addrSiGunGuNm").val(sigungu[0]);
    		}else{
    		    $("#addrSiGunGuNm").val(sggNm);
    		}
            $("#postNo").val(zipNo);
            $("#roadnmBasAddrCont").val(roadAddrPart1);
            $("#areaBasAddrCont").val(jibunAddr);
            $("#post").val(zipNo);
            $("#roadnm").val(roadAddrPart1);
            $("#area").val(jibunAddr);
            $("#addrSiDoNm").val(sido);

            $("#addrEupMyeonDongNm").val(emdNm);


            $("#latudCont").val(entX);
            $("#lotudCont").val(entY);
    }


    function page_back(){
        $('.step1').css('display', 'block');
        $('.step2').css('display', 'none');
    }
    function page_back2(){
        $('.step2').css('display', 'block');
        $('.step3').css('display', 'none');
    }
    function next2(){
        var table = $("#table1").DataTable();
        var cnt = table.data().length;
        if(cnt > 0){
            $('.step2').css('display', 'none');
            $('.step3').css('display', 'block');
        }else{
            alert("세대별평형 정보를 입력해주세요.");
            return;
        }
    }

     function dataArray(datas){
    	var Jsonlist = JSON.stringify(datas);
        var qJsonArray = new Array();
        var zero = "0";
        var zero2 = "00";
        var zero3 = "000";

        for(var i = 0 ; i < datas.length ; i++){
            var cJson = new Object();
            var dong = datas[i].동;
            var hose = datas[i].호;
            if(dong.length < 4){dong = zero+dong;}
            else if(dong.length < 3){dong = zero2+dong;}
            else if(dong.length < 2){dong = zero3+dong;}
            else{dong = datas[i].동;}
            if(hose.length < 4){hose = zero+hose;}
            else if(hose.length < 3){hose = zero2+hose;}
            else if(hose.length < 2){hose = zero3+hose;}
            else{hose = datas[i].호;}
            cJson.dongNo = dong;
            cJson.hoseNo = hose;
            var type  = datas[i].타입;
            if(type == "" || type == null){
                type  = "-";
            }else{
                type  = datas[i].타입;
            }
            cJson.ptypeNm = type;
            cJson.dimQty = datas[i].전용면적;
            cJson.crerId = "<c:out value="${session_user.userId}"/>";
            qJsonArray.push(cJson);
        }
        var hJson = JSON.stringify(qJsonArray);
        $("#householdList").val(hJson);

    	$('#table1').DataTable({
    			// 표시 건수기능 숨기기
    			lengthChange: false,
    			// 검색 기능 숨기기
    			searching: false,
    			// 정렬 기능 숨기기
    			ordering: false,
    			// 정보 표시 숨기기
    			info: false,
    			// 페이징 기능 숨기기
    			paging: false,
    			data:datas,
    			columns:[
                    {"data": '동',
                        "render":function(data, type, row, meta){
                            var dong = row['동'];
                            var newdong;
                            if(dong.length < 4){newdong = zero+dong;}
                            else if(dong.length < 3){newdong = zero2+dong;}
                            else if(dong.length < 2){newdong = zero3+dong;}
                            else{newdong = dong;}
                            return newdong;
                        }
                    },
                    {"data": '호',
                        "render":function(data, type, row, meta){
                            var hose = row["호"];
                            var newhose;
                            if(hose.length < 4){newhose = zero+hose;}
                            else if(hose.length < 3){newhose = zero2+hose;}
                            else if(hose.length < 2){newhose = zero3+hose;}
                            else{newhose = hose;}
                            return newhose;
                        }
                    },
                    {"data": '전용면적'},
                    {"data": '타입',
                        "render":function(data, type, row, meta){
                            var type = row["타입"];
                            if(type == "" || type == null){
                                type = "-";
                            }
                            return type;
                        }
                    }
            ]
    		});
     }

     function insert(){
        if($("#day").val() == "null"){
            alert("검침일을 설정해주세요.");
            return;
        }
        if($("#qry_cont").val() == ""){
            alert("단지반경을 입력해주세요.");
            return;
        }
        if($("#elct").val() == ""){
            alert("전기 값 을 입력해주세요.");
            return;
        }
        if($("#hotwtr").val() == ""){
            alert("온수 값 을 입력해주세요.");
            return;
        }
        if($("#hotwtr_cd").val() == "null"){
            alert("온수 단위를 입력해주세요.");
            return;
        }
        if($("#heat_val").val() == ""){
            alert("난방 값 을 입력해주세요.");
            return;
        }
        if($("#heat_cd").val() == "null"){
            alert("난방 단위를 입력해주세요.");
            return;
        }
        if($("#gas").val() == ""){
            alert("가스 값 을 입력해주세요.");
            return;
        }
        if($("#wtrspl").val() == ""){
            alert("수도 값 을 입력해주세요.");
            return;
        }



        var num = $("#webappnum").val();
        var qJsonArray = new Array();
        for(var i = 0 ; i <= num ; i++){
            var exist = document.getElementById('webapplist_'+i) ? true : false;

            if(exist == false){
                console.log("없음");
            }else{
                var cJson = new Object();
                cJson.lnkSvcId = $("#servicelist_"+i+"").val();
                cJson.crerId = "<c:out value="${session_user.userId}"/>";
                qJsonArray.push(cJson);
            }
        }
        var hJson = JSON.stringify(qJsonArray);

        $("#serviceLinkList").val(hJson);



        var JsonArray = new Array();
        var a1Json = new Object();
        var a2Json = new Object();
        var a3Json = new Object();
        var a4Json = new Object();
        var a5Json = new Object();

        a1Json.enrgTpCd = "ELCT";
        a1Json.enrgUntCd = "KWH";
        a1Json.enrgMaxQty = $("#elct").val();
        a1Json.crerId = "<c:out value="${session_user.userId}"/>";
        JsonArray.push(a1Json);

        a2Json.enrgTpCd = "HOTWTR";
        a2Json.enrgUntCd = $("#hotwtr_cd").val();
        a2Json.enrgMaxQty = $("#hotwtr").val();
        a2Json.crerId = "<c:out value="${session_user.userId}"/>";
        JsonArray.push(a2Json);

        a3Json.enrgTpCd = "HEAT";
        a3Json.enrgUntCd = $("#heat_cd").val();
        a3Json.enrgMaxQty = $("#heat_val").val();
        a3Json.crerId = "<c:out value="${session_user.userId}"/>";
        JsonArray.push(a3Json);

        a4Json.enrgTpCd = "GAS";
        a4Json.enrgUntCd = "M3";
        a4Json.enrgMaxQty = $("#gas").val();
        a4Json.crerId = "<c:out value="${session_user.userId}"/>";
        JsonArray.push(a4Json);

        a5Json.enrgTpCd = "WTRSPL";
        a5Json.enrgUntCd = "M3";
        a5Json.enrgMaxQty = $("#wtrspl").val();
        a5Json.crerId = "<c:out value="${session_user.userId}"/>";
        JsonArray.push(a5Json);


        var sJson = JSON.stringify(JsonArray);
        $("#energyUnitList").val(sJson);
        $("#excwkBizcoNm").val($("#bizco_nm").html());
        $("#qryRangeCont").val($("#qry_cont").val());
        $("#enrgMeasYmd").val($("#day").val());
        $("#crerId").val("<c:out value="${session_user.userId}"/>");

        $("#housingCplx").submit();
     }

    function SetNum(obj){
        val=obj.value;
        re=/[^0-9]/gi;
        obj.value=val.replace(re,"");
    }

    function SetNum2(obj){
        val=obj.value;
        re=/[^0-9-"."]/gi;
        obj.value=val.replace(re,"");
    }

</script>

<form:form action="/cm/housingcplx/info/addHousingCplxInfoAction" id="housingCplx" name="housingCplx" commandName="housingCplx" method="post">
<input type="text" id="householdList" name="householdList" value="<c:out value="${householdList}"/>" style="display:none;"/>
<input type="text" id="serviceLinkList" name="serviceLinkList" value="<c:out value="${serviceLinkList}"/>" style="display:none;"/>
<input type="text" id="energyUnitList" name="energyUnitList" value="<c:out value="${energyUnitList}"/>" style="display:none;"/>
<input type="text" id="addrSiDoNm" name="addrSiDoNm" value="<c:out value="${addrSiDoNm}"/>" style="display:none;"/>
<input type="text" id="addrSiGunGuNm" name="addrSiGunGuNm" value="<c:out value="${addrSiGunGuNm}"/>" style="display:none;"/>
<input type="text" id="addrEupMyeonDongNm" name="addrEupMyeonDongNm" value="<c:out value="${addrEupMyeonDongNm}"/>" style="display:none;"/>

<input type="text" id="latudCont" name="latudCont" value="<c:out value="${latudCont}"/>" style="display:none;"/>
<input type="text" id="lotudCont" name="lotudCont" value="<c:out value="${lotudCont}"/>" style="display:none;"/>

<input type="text" id="postNo" name="postNo" value="<c:out value="${postNo}"/>" style="display:none;"/>
<input type="text" id="roadnmBasAddrCont" name="roadnmBasAddrCont" value="<c:out value="${roadnmBasAddrCont}"/>" style="display:none;"/>
<input type="text" id="areaBasAddrCont" name="areaBasAddrCont" value="<c:out value="${areaBasAddrCont}"/>" style="display:none;"/>

<input type="text" id="hmnetId" name="hmnetId" value="<c:out value="${hmnetId}"/>" style="display:none;"/>
<input type="text" id="heatTpCd" name="heatTpCd" value="<c:out value="${heatTpCd}"/>" style="display:none;"/>

<input type="text" id="excwkBizcoNm" name="excwkBizcoNm" value="<c:out value="${excwkBizcoNm}"/>" style="display:none;"/>
<input type="text" id="qryRangeCont" name="qryRangeCont" value="<c:out value="${qryRangeCont}"/>" style="display:none;"/>
<input type="text" id="enrgMeasYmd" name="enrgMeasYmd" value="<c:out value="${enrgMeasYmd}"/>" style="display:none;"/>
<input type="text" id="crerId" name="crerId" value="<c:out value="${crerId}"/>" style="display:none;"/>

<input type="text" id="day" name="day" value="1" style="display:none;"/>


    <div id="container" class="step1">

        <div class="container">

            <div class="top_area">
                <h2 class="tit">단지 신규등록</h2>
                <ul class="location">
                    <li>단지 관리</li>
                    <li>단지정보 관리</li>
                    <li>단지목록</li>
                    <li>단지 신규등록</li>
                </ul>
            </div>

            <div class="progress_wrap">
                <ul>
                    <li class="on"><span class="no">1</span><span class="txt">단지개요</span></li>
                    <li><span class="no">2</span><span class="txt">세대별 평형</span></li>
                    <li><span class="no">3</span><span class="txt">기타</span></li>
                </ul>
            </div>

            <div class="table_wrap2">
                <div class="tbl_top_area">
                    <div class="right_area"><span class="star mr10"></span>필수입력 항목</div>
                </div>
                <table class="table2">
                    <colgroup>
                        <col style="width:150px"/>
                        <col />
                        <col style="width:150px"/>
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>단지명칭 <sup class="star"></sup></th>
                            <td><input type="text" id="houscplxNm" name="houscplxNm" class="form-control" maxlength="50"/></td>
                            <th>화면표시단지명 <sup class="star"></sup></th>
                            <td><input type="text" id="screenHouscplxNm" name="screenHouscplxNm" class="form-control" maxlength="50"/></td>
                        </tr>
                        <tr>
                            <th>단지주소 <sup class="star"></sup></th>
                            <td colspan="3">
                                <div class="depth2_tr w400">
                                    <div class="th w70">우편번호</div>
                                    <div class="td">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="post" name="post" disabled />
                                            <div class="input-group-append">
                                                <input type="button" onClick="goPopup()"; class="btn btn-gray btn-sm" value="주소입력"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="depth2_tr">
                                    <div class="th w70">도로명</div>
                                    <div class="td"><input type="text" id="roadnm" name="roadnm" class="form-control" disabled /></div>
                                </div>
                                <div class="depth2_tr">
                                    <div class="th w70">지번</div>
                                    <div class="td"><input type="text" id="area" name="area" class="form-control" disabled /></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>대지면적(m2) <sup class="star"></sup></th>
                            <td><input type="text" id="landDimQty" name="landDimQty" value="<c:out value="${landDimQty}"/>" class="form-control" onkeyup="SetNum2(this);" maxlength="20"/></td>
                            <th>연면적(m2) <sup class="star"></sup></th>
                            <td><input type="text" id="archDimQty" name="archDimQty" value="<c:out value="${archDimQty}"/>" class="form-control" onkeyup="SetNum2(this);" maxlength="20"/></td>
                        </tr>
                        <tr>
                            <th>사업승인일</th>
                            <td><input type="text" class="form-control inp_calendar" id="busiApprYmd" name="busiApprYmd" value="<c:out value="${busiApprYmd}"/>"/></td>
                            <th>사업준공일 <sup class="star"></sup></th>
                            <td><input type="text" class="form-control inp_calendar" id="busiConendYmd" name="busiConendYmd" value="<c:out value="${busiConendYmd}"/>"/></td>
                        </tr>
                        <script type="text/javascript">
                            $('#busiApprYmd').datepicker({
                                format: "yyyymmdd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#busiConendYmd').datepicker({
                                format: "yyyymmdd",
                                language: "ko",
                                autoclose: true
                            });
                        </script>
                        <tr>
                            <th>총 세대수 <sup class="star"></sup></th>
                            <td><input type="text" id="wholeHsholdCnt" name="wholeHsholdCnt" value="<c:out value="${wholeHsholdCnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></td>
                            <th>총 동수 <sup class="star"></sup></th>
                            <td><input type="text" id="wholeDongCnt" name="wholeDongCnt" value="<c:out value="${wholeDongCnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></td>
                        </tr>
                        <tr>
                            <th>지하/지상 <sup class="star"></sup></th>
                            <td colspan="3">
                            	<div class="depth2_tr">
                            		<div class="th w50">지하층</div>
                            		<div class="td"><input type="text" id="lwstUngrFlrcnt" name="lwstUngrFlrcnt" value="<c:out value="${lwstUngrFlrcnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></div>
                            		<div class="th w50">지상층</div>
                            		<div class="td"><input type="text" id="hgstAbgrFlrcnt" name="hgstAbgrFlrcnt" value="<c:out value="${hgstAbgrFlrcnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></div>
                            	</div>
                            </td>
                        </tr>
                        <tr>
                            <th>홈넷서버종류 <sup class="star"></sup></th>
                            <td>
                                <select name="tpcd" id="tpcd" class="custom-select" onchange="chang();">
                                    <option value="null">선택</option>
                                    <option value="HOUSCPLX_SVR">단지서버</option>
                                    <option value="UNIFY_SVR">통합서버</option>
                                </select>
                            </td>
                            <th>홈넷서버 <sup class="star"></sup></th>
                            <td>
                                <select name="hmnet" id="hmnet" class="custom-select" onchange="hmnetsvrcheck();">
                                    <option value="null">선택</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>주차대수 <sup class="star"></sup></th>
                            <td colspan="3">
                                <div class="depth2_tr">
                                    <div class="th w40">전체</div>
                                    <div class="td"><input type="text" id="wholeWlLcnt" name="wholeWlLcnt" value="<c:out value="${wholeWlLcnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></div>
                                    <div class="th w50">세대당</div>
                                    <div class="td"><input type="text" id="hsholdWlLcnt" name="hsholdWlLcnt" value="<c:out value="${hsholdWlLcnt}"/>" class="form-control" onkeyup="SetNum2(this);" maxlength="10"/></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>시공사</th>
                            <td id="bizco_nm">(주)대우건설</td>
                            <th>난방방식 <sup class="star"></sup></th>
                            <td>
                                <select name="heat" id="heat" class="custom-select">
                                    <option value="null">선택</option>
                                <c:forEach items="${heatTypeList}" var="heatList" varStatus="status">
                                    <option value="<c:out value="${heatList.commCd}"/>"><c:out value="${heatList.commCdNm}"/></option>
                                </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="tbl_btm_area2">
                <div class="right_area">
                    <input type="button" onclick="back();" class="btn btn-gray" value="취소"/>
                    <input type="button" onclick="next();" class="btn btn-bluegreen" value="다음"/>
                </div>
            </div>
        </div>
    </div>

    <div id="container" class="step2" style="display:none">

        <div class="container">
        <div class="top_area">
                <h2 class="tit">단지 신규등록</h2>
                <ul class="location">
                    <li>단지 관리</li>
                    <li>단지정보 관리</li>
                    <li>단지목록</li>
                    <li>단지 신규등록</li>
                </ul>
            </div>
            <div class="progress_wrap">
                <ul>
                    <li><span class="no">1</span><span class="txt">단지개요</span></li>
                    <li class="on"><span class="no">2</span><span class="txt">세대별 평형</span></li>
                    <li><span class="no">3</span><span class="txt">기타</span></li>
                </ul>
            </div>


            <div class="table_wrap2">
                <table class="table2">
                    <colgroup>
                        <col style="width:150px"/>
                        <col />
                        <col style="width:150px"/>
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>엑셀파일</th>
                            <td colspan="3">
                                <div class="custom-file">
                                    <input type="file" class="custom-file-input" id="upload" value="파일찾기" aria-describedby="inputGroupFileAddon01" name="files[]" accept=".xls, .xlsx">
                                    <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                </div>
                                <script type="text/javascript">
                                    $('#upload').on('change',function(){
                                        var tableDestroy = $('#table1').DataTable();
                                        tableDestroy.destroy();
                                        var fileName = $(this).val();
                                        $(this).next('.custom-file-label').html(fileName);
                                    })
                                </script>
                                <script type="text/javascript">
                                    var ExcelToJSON = function() {

                                    this.parseExcel = function(file) {
                                    var reader = new FileReader();

                                    reader.onload = function(e) {
                                      var data = e.target.result;
                                      var workbook = XLSX.read(data, {
                                        type: 'binary'
                                      });
                                      workbook.SheetNames.forEach(function(sheetName) {
                                        var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                                        var json_object = JSON.stringify(XL_row_object);
                                        var data = new Object();
                                        data = JSON.parse(json_object);

                                        dataArray(data);
                                      })
                                    };

                                    reader.onerror = function(ex) {
                                      console.log(ex);
                                    };

                                    reader.readAsBinaryString(file);
                                  };
                              };

                              function handleFileSelect(evt) {
                                var files = evt.target.files; // FileList object
                                var xl2json = new ExcelToJSON();

                                var filesProfile = files[0];

                                if (filesProfile != undefined) {
                                    var fileSize = filesProfile.size;
                                    var maxSize = 500 * 1024 * 1024;

                                    if(fileSize > maxSize){
                                       alert("첨부파일 사이즈는 500MB 이내로 등록 가능합니다. ");
                                       return false;
                                    }

                                    var fileType = filesProfile.type;

                                    if (fileType != "application/vnd.ms-excel" && fileType != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" && fileType != "application/haansoftxls") {
                                       alert("첨부파일은 엑셀(.xls, .xlsx)만 등록 가능합니다. ");
                                       return false;
                                    } else if (fileType == "application/vnd.ms-excel" || fileType == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" || fileType == "application/haansoftxls") {
                                       xl2json.parseExcel(filesProfile);
                                    }
                                } else if (filesProfile == undefined) {
                                    return false;
                                }
                              }

                             document.getElementById('upload').addEventListener('change', handleFileSelect, false);


                            </script>
                            <div style="text-align:right;" class="right_area">
                                <button style="margin-top:10px;" class="btn btn-gray btn-sm" type="button" onclick="fileDownload('세대별평형_엑셀등록양식.xls','<c:url value="/files/household_sample.xls"/>')">샘플양식 다운로드</button>
                            </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="table_wrap2">
                <table class="table" id="table1">
                    <thead>
                        <tr>
                            <th scope="col">동</th>
                            <th scope="col">호</th>
                            <th scope="col">전용면적</th>
                            <th scope="col">타입</th>
                        </tr>
                    </thead>
                    <tbody style="text-align:center" id="databody">

                    </tbody>
                </table>
                <script type="text/javascript">
                    $('#table1').DataTable({
                        "order": [],
                        "bLengthChange" : false,
                        "dom": '<<t>>',
                        "language": {
                            "info": "Total <span>_TOTAL_</span>건",
                            "infoEmpty":"Total <span>0</span>건",
                            "emptyTable": "데이터가 없습니다."
                        }
                    });
                </script>
            </div>
            <div class="tbl_btm_area2">
                <div class="right_area">
                    <input type="button" class="btn btn-brown" onclick="back();" value="취소"/>
                    <input type="button" class="btn btn-brown" onclick="page_back();" value="이전"/>
                    <input type="button" class="btn btn-brown" onclick="next2();" value="다음"/>
                </div>
            </div>
        </div>
    </div>

    <div id="container" class="step3" style="display:none;">

        <div class="container">
        <div class="top_area">
                <h2 class="tit">단지 신규등록</h2>
                <ul class="location">
                    <li>단지 관리</li>
                    <li>단지정보 관리</li>
                    <li>단지목록</li>
                    <li>단지 신규등록</li>
                </ul>
            </div>


            <div class="progress_wrap">
                <ul>
                    <li><span class="no">1</span><span class="txt">단지개요</span></li>
                    <li><span class="no">2</span><span class="txt">세대별 평형</span></li>
                    <li class="on"><span class="no">3</span><span class="txt">기타</span></li>
                </ul>
            </div>

            <div class="table_wrap2">
                <div class="tbl_top_area">
                    <div class="right_area"><span class="star mr10"></span>필수입력 항목</div>
                </div>
                <table class="table2">
                    <colgroup>
                        <col style="width:160px"/>
                        <col />
                        <col style="width:160px"/>
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>단지 반경 설정 (m) <sup class="star"></sup></th>
                            <td colspan="3"><input type="text" class="form-control" id="qry_cont" onkeyup="SetNum(this);"/></td>
                        </tr>
                        <tr>
                            <th>에너지 단위 및 그래프 최대값 설정 <sup class="star"></sup></th>
                            <td colspan="3">
                                <div class="depth2_tr">
                                    <div class="th w45">전기</div>
                                    <div class="td w160"><input type="text" id="elct" class="form-control d-inline-block w90 mr5" onkeyup="SetNum(this);"/> kWh</div>
                                    <div class="th w45">온수</div>
                                    <div class="td">
                                        <input type="text" id="hotwtr" class="form-control d-inline-block align-middle w90 mr5" onkeyup="SetNum(this);"/>
                                        <select name="" id="hotwtr_cd" class="custom-select d-inline-block align-middle w80">
                                            <option value="null">선택</option>
                                            <option value="MCAL">mcal</option>
                                            <option value="M3">㎥</option>
                                        </select>
                                    </div>
                                    <div class="th w45">난방</div>
                                    <div class="td">
                                        <input type="text" id="heat_val" class="form-control d-inline-block align-middle w90 mr5" onkeyup="SetNum(this);"/>
                                        <select name="" id="heat_cd" class="custom-select d-inline-block align-middle w80">
                                            <option value="null">선택</option>
                                            <option value="MCAL">mcal</option>
                                            <option value="M3">㎥</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="depth2_tr">
                                    <div class="th w45">가스</div>
                                    <div class="td w160"><input type="text" id="gas" class="form-control d-inline-block w90 mr5" onkeyup="SetNum(this);"/> m<sup>3</sup></div>
                                    <div class="th w45">수도</div>
                                    <div class="td"><input type="text" id="wtrspl" class="form-control d-inline-block w90 mr5" onkeyup="SetNum(this);"/> m<sup>3</sup></div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="sub_tit">
                <h3 class="tit">연계 웹/앱</h3>
            </div>
            <div class="table_wrap">
                <table class="table" id="table3">
                    <thead>
                        <tr>
                            <th scope="col" style="width:300px">구분</th>
                            <th scope="col" style="width:300px">서비스명</th>
                            <th scope="col" style="width:15px"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <select name="" id="webapplist_0" class="custom-select" onchange="select(this, this.id);">
                                    <option value="all">선택</option>
                                </select>
                            </td>
                            <td>
                                <select name="" id="servicelist_0" class="custom-select">
                                    <option value="all">선택</option>
                                </select>
                            </td>
                            <td>
                                <button type="button" id="serviceLinkDelete" class="btn btn-add-circle ml10 deleteRow"><i class="fas fa-minus-circle"></i></button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="tbl_btm_area no_paging"> <!-- table에 페이징없을시 no_paging추가 -->
                    <div class="left_area">
                        <button type="button" class="btn btn-add addRow"><i class="fas fa-plus-square"></i>추가</button>
                    </div>
                </div>
            </div>
            <input type="text" id="webappnum" value="0" style="display:none;"/>
            <script type="text/javascript">
                var tables = $('#table3').DataTable({
                    "order": [],
                    "bLengthChange" : false,
                    "dom": '<<t>>',
                    "language": {
                        "info": "Total <span>_TOTAL_</span>건",
                        "infoEmpty":"Total <span>0</span>건",
                        "emptyTable": "데이터가 없습니다."
                    },

                });
                $(document).on('click','.addRow',function(){
                    var num = $("#webappnum").val();
                    num = Number(num);
                    num+=1;
                    $("#webappnum").val(num);

                    tables.row.add( [
                        '<select name="" id="webapplist_'+num+'" class="custom-select" onchange="select(this, this.id);">' +
                            '<option value="all">선택</option>'+
                        '</select>',
                        '<select name="" id="servicelist_'+num+'" class="custom-select">' +
                            '<option value="all">선택</option>'+
                        '</select>',

                        '<button type="button" class="btn btn-add-circle ml10 deleteRow"><i class="fas fa-minus-circle"></i></button>'
                    ] ).draw( false );
                    webapplist(num);
                });
                $(document).on('click','.deleteRow',function(){
                    tables.row( $(this).parents('tr') ).remove().draw();
                });
            </script>

            <div class="tbl_btm_area2 line">
                <div class="right_area">
                    <input type="button" class="btn btn-gray" onclick="back();" value="취소"/>
                    <input type="button" class="btn btn-gray" onclick="page_back2();" value="이전"/>
                    <input type="button" class="btn btn-bluegreen" onclick="insert();" value="등록"/>
                </div>
            </div>

        </div>

    </div>

    <textarea class="form-control" style="display:none;" rows=35 cols=120 id="xlx_json"></textarea>
</form:form>