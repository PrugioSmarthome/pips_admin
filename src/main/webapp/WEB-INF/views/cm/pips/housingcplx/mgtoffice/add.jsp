<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.datetimepicker.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.datetimepicker.css" />">

<script type="text/javascript">
var count = 0;
    $(document).ready(function(){

    });


    function mgadd(){
        var table = $('#table1').DataTable();
        table.row.add([
                "<div class='input-group'><input name='tel1' type='number' class='form-control' /><span class='bul_space'>-</span><input name='tel2' type='number' class='form-control' /><span class='bul_space'>-</span><input name='tel3' type='number' class='form-control' /></div>",
                "<input name='rem_' type='text' class='form-control' />",
                "<button class='btn btn-add-circle ml10 deleteRow'><i class='fas fa-minus-circle'></i></button>"
        ]).draw();
    }

    function scadd(){
        var table = $('#table2').DataTable();
            table.row.add([
                    "<div class='input-group'><input name='sctel1' type='number' class='form-control' /><span class='bul_space'>-</span><input name='sctel2' type='number' class='form-control' /><span class='bul_space'>-</span><input name='sctel3' type='number' class='form-control' /></div>",
                    "<input name='screm_' type='text' class='form-control' />",
                    "<button class='btn btn-add-circle ml10 deleteRow2'><i class='fas fa-minus-circle'></i></button>"
            ]).draw();
    }

    function smsadd(){
        var table = $('#table3').DataTable();
        table.row.add([
                "<div class='input-group'><input name='smstel1' type='number' class='form-control' /><span class='bul_space'>-</span><input name='smstel2' type='number' class='form-control' /><span class='bul_space'>-</span><input name='smstel3' type='number' class='form-control' /></div>",
                "<div class='input-group'><div class='w220'><input id='stime_"+count+"' name='Starttime' type='time' class='form-control' /></div><span class='bul_space'>~</span><div class='w220'><input id='etime_"+count+"' name='Endtime' type='time' class='form-control' /></div></div>",
                "<button class='btn btn-add-circle ml10 deleteRow3'><i class='fas fa-minus-circle'></i></button>"
        ]).draw();

        var d = new Date(),
        h = d.getHours(),
        m = d.getMinutes();
        if(h < 10) h = '0' + h;
        if(m < 10) m = '0' + m;
        $("#stime_"+count+"").val(h + ':' + m);
        $("#etime_"+count+"").val(h + ':' + m);
        count++;
    }

    function submit_btn(){
        var nRegExp = /[\{\}\[\]\/;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
        var cnt = $("input[name=rem_]").length;
        var cnt2 = $("input[name=screm_]").length;
        var cnt3 = $("input[name=Starttime]").length;

        var emptycheck1 = true;
        var emptycheck2 = true;
        var emptycheck3 = true;

        for(var i = 0 ; i < cnt ; i++){
            var tel1 = $("input[name='tel1']").eq(i).attr("value");
            var tel2 = $("input[name='tel2']").eq(i).attr("value");
            var tel3 = $("input[name='tel3']").eq(i).attr("value");

            if(tel1 == ""){emptycheck1 = false}
            if(tel2 == ""){emptycheck1 = false}
            if(tel3 == ""){emptycheck1 = false}
        }
        for(var i = 0 ; i < cnt2 ; i++){
            var tel1 = $("input[name='sctel1']").eq(i).attr("value");
            var tel2 = $("input[name='sctel2']").eq(i).attr("value");
            var tel3 = $("input[name='sctel3']").eq(i).attr("value");

            if(tel1 == ""){emptycheck2 = false}
            if(tel2 == ""){emptycheck2 = false}
            if(tel3 == ""){emptycheck2 = false}
        }
        for(var i = 0 ; i < cnt3 ; i++){
            var tel1 = $("input[name='smstel1']").eq(i).attr("value");
            var tel2 = $("input[name='smstel2']").eq(i).attr("value");
            var tel3 = $("input[name='smstel3']").eq(i).attr("value");

            if(tel1 == ""){emptycheck3 = false}
            if(tel2 == ""){emptycheck3 = false}
            if(tel3 == ""){emptycheck3 = false}
        }
        if(cnt == 0 && cnt2 == 0 && cnt3 == 0 && $("#desccon").val() == "" && $("#scdesccon").val() == ""){
            alert("등록하실 내용을 추가해주세요.");
            return;
        }
        if(emptycheck1 == false){alert("관리실 연락처를 입력해주세요."); return;}
        if(emptycheck2 == false){alert("경비실 연락처를 입력해주세요."); return;}
        if(emptycheck3 == false){alert("생활불편신고 연락처를 입력해주세요."); return;}

        var regexpcheck;
        for(var i = 0 ; i < cnt ; i++){
            var content = $("input[name='rem_']").eq(i).attr("value");
            if(nRegExp.test(content) == true){
                regexpcheck = false;
            }
        }
        for(var i = 0 ; i < cnt2 ; i++){
            var content = $("input[name='screm_']").eq(i).attr("value");
            if(nRegExp.test(content) == true){
                regexpcheck = false;
            }
        }


        if(regexpcheck == false){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }


        var finalArray = new Array();
        var descArray = new Array();
        for(var i = 0 ; i < cnt ; i++){
            var jsonObject = new Object();
            jsonObject.rem = $("input[name='rem_']").eq(i).attr("value");
            jsonObject.caddrCont = $("input[name='tel1']").eq(i).attr("value")+"-"+$("input[name='tel2']").eq(i).attr("value")+"-"+$("input[name='tel3']").eq(i).attr("value");
            finalArray.push(jsonObject);
        }
        var mgmtOfcList = JSON.stringify(finalArray);

        var scArray = new Array();
        for(var i = 0 ; i < cnt2 ; i++){
            var jsonObject = new Object();
            jsonObject.rem = $("input[name='screm_']").eq(i).attr("value");
            jsonObject.caddrCont = $("input[name='sctel1']").eq(i).attr("value")+"-"+$("input[name='sctel2']").eq(i).attr("value")+"-"+$("input[name='sctel3']").eq(i).attr("value");
            scArray.push(jsonObject);
        }
        var scrtOfcList = JSON.stringify(scArray);

        var smsArray = new Array();
        for(var i = 0 ; i < cnt3 ; i++){
            var jsonObject = new Object();
            jsonObject.caddrCont = $("input[name='smstel1']").eq(i).attr("value")+"-"+$("input[name='smstel2']").eq(i).attr("value")+"-"+$("input[name='smstel3']").eq(i).attr("value");
            jsonObject.cntctStime = $("input[name='Starttime']").eq(i).attr("value").replace(":","");
            jsonObject.cntctEtime = $("input[name='Endtime']").eq(i).attr("value").replace(":","");
            smsArray.push(jsonObject);
        }
        var lifeIcvncOfcList = JSON.stringify(smsArray);

        if(nRegExp.test($("#desccon").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if(nRegExp.test($("#scdesccon").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }

        var descJsonObject = new Object();
        descJsonObject.cont =$("#desccon").val();
        descJsonObject.workTpCd = "MGMT_OFC";
        descArray.push(descJsonObject);

        var descJsonObject2 = new Object();
        descJsonObject2.cont =$("#scdesccon").val();
        descJsonObject2.workTpCd = "SCRT_OFC";
        descArray.push(descJsonObject2);
        var descList = JSON.stringify(descArray);


        if(mgmtOfcList.length > 0){
            $("#isMgmtOfc").val("Y");
        }
        if(scrtOfcList.length > 0){
            $("#isScrtOfc").val("Y");
        }
        if(lifeIcvncOfcList.length > 0){
            $("#isLifeIcvncOfc").val("Y");
        }

        $("#mgmtOfcList").val(mgmtOfcList);
        $("#scrtOfcList").val(scrtOfcList);
        $("#lifeIcvncOfcList").val(lifeIcvncOfcList);
        $("#descriptionList").val(descList);


        $("#form_add").submit();
    }

    function back() {
        window.history.back();
    }
</script>

<div id="container">


    <div class="container">

        <div class="top_area">
            <h2 class="tit">관리실/경비실 등록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>단지정보</li>
                <li>관리실/경비실 등록</li>
            </ul>
        </div>

        <div class="sub_tit">
            <h3 class="tit">관리실 연락처</h3>
        </div>
        <div class="depth2_tr type2">
            <div class="th w60">· 설명</div>
            <div class="td"><input type="text" id="desccon" class="form-control" /></div>
        </div>
        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-add" onclick="mgadd();"><i class="fas fa-plus-square"></i></button>
            </div>
        </div>
        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">연락처</th>
                        <th scope="col">연락처 제목</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <script type="text/javascript">
            var tables = $('#table1').DataTable({
                        "bLengthChange" : false,
                        "paging": false,
                        "ordering": false,
                        "info": false,
                        "filter": false,
                        "lengthChange": false,
                        "order": false,
                        "language": {
                            "info": "Total <span>_TOTAL_</span>건",
                            "infoEmpty":"Total <span>0</span>건",
                            "emptyTable": "데이터가 없습니다."
                        },
                        "columnDefs": [
                            { "width": "34%", "targets": 0 },
                            { "width": "58%", "targets": 1 },
                            { "width": "8%", "targets": 2 }
                        ]
            });
            $(document).on('click','.deleteRow',function(){
                tables.row( $(this).parents('tr') ).remove().draw();
            });
        </script>

        <div class="sub_tit">
            <h3 class="tit">경비실 연락처</h3>
        </div>
        <div class="depth2_tr type2">
            <div class="th w60">· 설명</div>
            <div class="td"><input type="text" id="scdesccon" class="form-control" /></div>
        </div>
        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-add" onclick="scadd();"><i class="fas fa-plus-square"></i></button>
            </div>
        </div>
        <div class="table_wrap">
            <table class="table" id="table2">
                <thead>
                    <tr>
                        <th scope="col">연락처</th>
                        <th scope="col">연락처 제목</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <script type="text/javascript">
            var tables2 = $('#table2').DataTable({
                        "bLengthChange" : false,
                        "paging": false,
                        "ordering": false,
                        "info": false,
                        "filter": false,
                        "lengthChange": false,
                        "order": false,
                        "language": {
                            "info": "Total <span>_TOTAL_</span>건",
                            "infoEmpty":"Total <span>0</span>건",
                            "emptyTable": "데이터가 없습니다."
                        },
                        "columnDefs": [
                            { "width": "34%", "targets": 0 },
                            { "width": "58%", "targets": 1 },
                            { "width": "8%", "targets": 2 }
                        ]
            });
            $(document).on('click','.deleteRow2',function(){
                tables2.row( $(this).parents('tr') ).remove().draw();
            });
        </script>

        <div class="sub_tit">
            <h3 class="tit">생활불편신고 SMS 연락처</h3>
        </div>
        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-add" onclick="smsadd();"><i class="fas fa-plus-square"></i></button>
            </div>
        </div>
        <div class="table_wrap">
            <table class="table" id="table3">
                <thead>
                    <tr>
                        <th scope="col">연락처</th>
                        <th scope="col">연락 가능 시간</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>

            </table>
        </div>
        <script type="text/javascript">
            var tables3 = $('#table3').DataTable({
                "bLengthChange" : false,
                "paging": false,
                "ordering": false,
                "info": false,
                "filter": false,
                "lengthChange": false,
                "order": false,
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "데이터가 없습니다."
                },
                "columnDefs": [
                    { "width": "34%", "targets": 0 },
                    { "width": "58%", "targets": 1 },
                    { "width": "8%", "targets": 2 }
                ]
            });
            $(document).on('click','.deleteRow3',function(){
                tables3.row( $(this).parents('tr') ).remove().draw();
            });
        </script>

        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-gray" onclick="back();">취소</button>
                <button class="btn btn-bluegreen" onclick="submit_btn();">저장</button>
            </div>
        </div>

    </div>

</div>

<form:form id="form_add" action="/cm/housingcplx/info/addAddressAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="mgmtOfcList" name="mgmtOfcList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="scrtOfcList" name="scrtOfcList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="lifeIcvncOfcList" name="lifeIcvncOfcList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="descriptionList" name="descriptionList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="isMgmtOfc" name="isMgmtOfc" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="isScrtOfc" name="isScrtOfc" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="isLifeIcvncOfc" name="isLifeIcvncOfc" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>