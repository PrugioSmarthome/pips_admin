<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">

<script type="text/javascript">
    var count = 0;
    $(document).ready(function(){

    });

    function back(){
        window.history.back();
    }

    function add(){
        var table = $('#table1').DataTable();
        table.row.add([
                "<div class='input-group'><input name='newtel1' type='number' class='form-control' /><span class='bul_space'>-</span><input name='newtel2' type='number' class='form-control' /><span class='bul_space'>-</span><input name='newtel3' type='number' class='form-control' /></div>",
                "<input name='newrem_' type='text' class='form-control' />",
                "<button class='btn btn-add-circle ml10 newdeleteRow'><i class='fas fa-minus-circle'></i></button>"
        ]).draw();
    }

    function scadd(){
        var table = $('#table2').DataTable();
            table.row.add([
                    "<div class='input-group'><input name='newsctel1' type='number' class='form-control' /><span class='bul_space'>-</span><input name='newsctel2' type='number' class='form-control' /><span class='bul_space'>-</span><input name='newsctel3' type='number' class='form-control' /></div>",
                    "<input name='newscrem_' type='text' class='form-control' />",
                    "<button class='btn btn-add-circle ml10 newdeleteRow2'><i class='fas fa-minus-circle'></i></button>"
            ]).draw();
    }

    function smsadd(){
        var table = $('#table3').DataTable();
        table.row.add([
                "<div class='input-group'><input name='newsmstel1' type='number' class='form-control' /><span class='bul_space'>-</span><input name='newsmstel2' type='number' class='form-control' /><span class='bul_space'>-</span><input name='newsmstel3' type='number' class='form-control' /></div>",
                "<div class='input-group'><div class='w220'><input id='stime_"+count+"' name='newStarttime' type='time' class='form-control' /></div><span class='bul_space'>~</span><div class='w220'><input id='etime_"+count+"' name='newEndtime' type='time' class='form-control' /></div></div>",
                "<button class='btn btn-add-circle ml10 newdeleteRow3'><i class='fas fa-minus-circle'></i></button>"
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
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var cnt = $("input[name=rem_]").length;
        var newcnt = $("input[name=newrem_]").length;
        var cnt2 = $("input[name=screm_]").length;
        var newcnt2 = $("input[name=newscrem_]").length;
        var cnt3 = $("input[name=Starttime]").length;
        var newcnt3 = $("input[name=newStarttime]").length;
        var mgmtArray = new Array();
        var descArray = new Array();
        var regexpcheck;
        for(var i = 0 ; i < cnt ; i++){
            var jsonObject = new Object();
            var content = $("input[name='rem_']").eq(i).attr("value");
            if(RegExp.test(content) == true){
                regexpcheck = false;
            }
            jsonObject.rem = $("input[name='rem_']").eq(i).attr("value");
            jsonObject.caddrCont = $("input[name='tel1']").eq(i).attr("value")+"-"+$("input[name='tel2']").eq(i).attr("value")+"-"+$("input[name='tel3']").eq(i).attr("value");
            jsonObject.ordNo = $("input[name='ord_']").eq(i).attr("value");
            jsonObject.isNew = "N";
            mgmtArray.push(jsonObject);
        }
        if(newcnt > 0){
            for(var i = 0 ; i < newcnt ; i++){
                var newjsonObject = new Object();
                var content = $("input[name='newrem_']").eq(i).attr("value");
                if(RegExp.test(content) == true){
                    regexpcheck = false;
                }
                newjsonObject.rem = $("input[name='newrem_']").eq(i).attr("value");
                newjsonObject.caddrCont = $("input[name='newtel1']").eq(i).attr("value")+"-"+$("input[name='newtel2']").eq(i).attr("value")+"-"+$("input[name='newtel3']").eq(i).attr("value");
                newjsonObject.ordNo = "";
                newjsonObject.isNew = "Y";
                mgmtArray.push(newjsonObject);
            }
        }
        var mgmtOfcList = JSON.stringify(mgmtArray);

        var scArray = new Array();
        for(var i = 0 ; i < cnt2 ; i++){
            var jsonObject = new Object();
            var content = $("input[name='screm_']").eq(i).attr("value");
            if(RegExp.test(content) == true){
                regexpcheck = false;
            }
            jsonObject.rem = $("input[name='screm_']").eq(i).attr("value");
            jsonObject.caddrCont = $("input[name='sctel1']").eq(i).attr("value")+"-"+$("input[name='sctel2']").eq(i).attr("value")+"-"+$("input[name='sctel3']").eq(i).attr("value");
            jsonObject.ordNo = $("input[name='sord_']").eq(i).attr("value");
            jsonObject.isNew = "N";
            scArray.push(jsonObject);
        }
        if(newcnt2 > 0){
            for(var i = 0 ; i < newcnt2 ; i++){
                var newjsonObject = new Object();
                var content = $("input[name='newscrem_']").eq(i).attr("value");
                if(RegExp.test(content) == true){
                    regexpcheck = false;
                }
                newjsonObject.rem = $("input[name='newscrem_']").eq(i).attr("value");
                newjsonObject.caddrCont = $("input[name='newsctel1']").eq(i).attr("value")+"-"+$("input[name='newsctel2']").eq(i).attr("value")+"-"+$("input[name='newsctel3']").eq(i).attr("value");
                newjsonObject.ordNo = "";
                newjsonObject.isNew = "Y";
                scArray.push(newjsonObject);
            }
        }
        var scrtOfcList = JSON.stringify(scArray);

        if(regexpcheck == false){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }

        var smsArray = new Array();
        for(var i = 0 ; i < cnt3 ; i++){
            var jsonObject = new Object();
            jsonObject.caddrCont = $("input[name='smstel1']").eq(i).attr("value")+"-"+$("input[name='smstel2']").eq(i).attr("value")+"-"+$("input[name='smstel3']").eq(i).attr("value");
            jsonObject.cntctStime = $("input[name='Starttime']").eq(i).attr("value").replace(":","");
            jsonObject.cntctEtime = $("input[name='Endtime']").eq(i).attr("value").replace(":","");
            jsonObject.ordNo = $("input[name='smsord_']").eq(i).attr("value");
            jsonObject.isNew = "N";
            smsArray.push(jsonObject);
        }
        if(newcnt3 > 0){
            for(var i = 0 ; i < newcnt3 ; i++){
                var newjsonObject = new Object();
                newjsonObject.caddrCont = $("input[name='newsmstel1']").eq(i).attr("value")+"-"+$("input[name='newsmstel2']").eq(i).attr("value")+"-"+$("input[name='newsmstel3']").eq(i).attr("value");
                newjsonObject.cntctStime = $("input[name='newStarttime']").eq(i).attr("value").replace(":","");
                newjsonObject.cntctEtime = $("input[name='newEndtime']").eq(i).attr("value").replace(":","");
                newjsonObject.ordNo = "";
                newjsonObject.isNew = "Y";
                smsArray.push(newjsonObject);
            }
        }
        var lifeIcvncOfcList = JSON.stringify(smsArray);

        if(RegExp.test($("#desccon").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if(RegExp.test($("#scdesccon").val()) == true){
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


        $("#mgmtOfcList").val(mgmtOfcList);
        $("#scrtOfcList").val(scrtOfcList);
        $("#lifeIcvncOfcList").val(lifeIcvncOfcList);
        $("#descriptionList").val(descList);

        $("#form_add").submit();
    }

    function del_btn(ord,tpcd){

        var del_check = confirm("삭제 하시겠습니까?");

        if(del_check == true){
            var tables = $('#table1').DataTable();
            var tables2 = $('#table2').DataTable();
            var tables3 = $('#table3').DataTable();
            $(document).on('click','.deleteRow',function(){
                tables.row( $(this).parents('tr') ).remove().draw();
            });
            $(document).on('click','.deleteRow2',function(){
                tables2.row( $(this).parents('tr') ).remove().draw();
            });
            $(document).on('click','.deleteRow3',function(){
               tables3.row( $(this).parents('tr') ).remove().draw();
           });
            $("#workTpCd").val(tpcd);
            $("#ordNo").val(ord)
            $("#form_del").submit();
        }
    }
</script>

<div id="container">


    <div class="container">

        <div class="top_area">
            <h2 class="tit">관리실/경비실 수정</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>관리실/경비실</li>
                <li>관리실/경비실 수정</li>
            </ul>
        </div>

        <div class="sub_tit">
            <h3 class="tit">관리실 연락처</h3>
        </div>
        <div class="depth2_tr type2">
            <div class="th w60">· 설명</div>
            <c:set var="doneLoop" value="true"/>
            <c:forEach items="${housingCplxCaddrDescription}" var="list" varStatus="status">
                <c:choose>
                    <c:when test="${empty list.workpTpCd}">
                        <div class="td"><input type="text" id="desccon" class="form-control" /></div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${list.workpTpCd == 'MGMT_OFC'}">
                            <div class="td"><input type="text" id="desccon" class="form-control" value="<c:out value="${list.cont}"/>" /></div>
                            <c:set var="doneLoop" value="false"/>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${doneLoop eq 'true'}">
                <div class="td"><input type="text" id="desccon" class="form-control" /></div>
            </c:if>
        </div>
        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
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
                <c:forEach items="${housingCplxCaddr}" var="list" varStatus="status">
                    <c:if test="${list.workpTpCd == 'MGMT_OFC'}">
                    <c:set var="telnum" value="${fn:split(list.caddrCont,'-')}"/>
                        <tr>
                            <td class="text-center">
                                <div class="input-group">
                                    <input type="number" name="tel1" class="form-control" value="<c:out value="${telnum[0]}"/>"/>
                                    <span class="bul_space">-</span>
                                    <input type="number" name="tel2" class="form-control" value="<c:out value="${telnum[1]}"/>"/>
                                    <span class="bul_space">-</span>
                                    <input type="number" name="tel3" class="form-control" value="<c:out value="${telnum[2]}"/>"/>
                                </div>
                            </td>
                            <td><input type="text" name="rem_" class="form-control" value="<c:out value="${list.rem}"/>"/></td>
                            <td><button class="btn btn-add-circle ml10 deleteRow" onclick="del_btn('<c:out value="${list.ordNo}"/>','MGMT_OFC');"><i class="fas fa-minus-circle"></i></button><input style="display:none" type="text" name="ord_" class="form-control" value="<c:out value="${list.ordNo}"/>"/></td>
                        </tr>
                    </c:if>
                </c:forEach>
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
            $(document).on('click','.newdeleteRow',function(){
                tables.row( $(this).parents('tr') ).remove().draw();
            });
        </script>

        <div class="sub_tit">
            <h3 class="tit">경비실 연락처</h3>
        </div>
        <div class="depth2_tr type2">
            <div class="th w60">· 설명</div>
            <c:set var="doneLoop" value="true"/>
            <c:forEach items="${housingCplxCaddrDescription}" var="list" varStatus="status">
                <c:choose>
                    <c:when test="${empty list.workpTpCd}">
                        <div class="td"><input type="text" id="scdesccon" class="form-control" /></div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${list.workpTpCd == 'SCRT_OFC'}">
                            <div class="td"><input type="text" id="scdesccon" class="form-control" value="<c:out value="${list.cont}"/>" /></div>
                            <c:set var="doneLoop" value="false"/>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${doneLoop eq 'true'}">
                <div class="td"><input type="text" id="scdesccon" class="form-control" /></div>
            </c:if>
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
                    <c:forEach items="${housingCplxCaddr}" var="list" varStatus="status">
                        <c:if test="${list.workpTpCd == 'SCRT_OFC'}">
                        <c:set var="telnum_" value="${fn:split(list.caddrCont,'-')}"/>
                            <tr>
                                <td class="text-center">
                                    <div class="input-group">
                                        <input type="number" name="sctel1" class="form-control" value="<c:out value="${telnum_[0]}"/>"/>
                                        <span class="bul_space">-</span>
                                        <input type="number" name="sctel2" class="form-control" value="<c:out value="${telnum_[1]}"/>"/>
                                        <span class="bul_space">-</span>
                                        <input type="number" name="sctel3" class="form-control" value="<c:out value="${telnum_[2]}"/>"/>
                                    </div>
                                </td>
                                <td><input type="text" name="screm_" class="form-control" value="<c:out value="${list.rem}"/>"/></td>
                                <td><button class="btn btn-add-circle ml10 deleteRow2" onclick="del_btn('<c:out value="${list.ordNo}"/>','SCRT_OFC');"><i class="fas fa-minus-circle"></i></button><input type="text" style="display:none" name="sord_" class="form-control" value="<c:out value="${list.ordNo}"/>"/></td>
                            </tr>
                        </c:if>
                    </c:forEach>
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
            $(document).on('click','.newdeleteRow2',function(){
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
                <c:set var="count" value="0" />
                <c:forEach items="${housingCplxCaddr}" var="list" varStatus="status">
                    <c:if test="${list.workpTpCd == 'LIFE_ICVNC_OFC'}">
                    <c:set var="_telnum" value="${fn:split(list.caddrCont,'-')}"/>
                        <tr>
                            <td class="text-center">
                                <div class="input-group">
                                    <input type="number" name="smstel1" class="form-control" value="<c:out value="${_telnum[0]}"/>"/>
                                    <span class="bul_space">-</span>
                                    <input type="number" name="smstel2" class="form-control" value="<c:out value="${_telnum[1]}"/>"/>
                                    <span class="bul_space">-</span>
                                    <input type="number" name="smstel3" class="form-control" value="<c:out value="${_telnum[2]}"/>"/>
                                </div>
                            </td>
                            <td>
                                <c:set value="${fn:substring(list.cntctStime, 0, 2)}" var="stime1"/>
                                <c:set value="${fn:substring(list.cntctStime, 2, 4)}" var="stime2"/>
                                <c:set value="${fn:substring(list.cntctEtime, 0, 2)}" var="etime1"/>
                                <c:set value="${fn:substring(list.cntctEtime, 2, 4)}" var="etime2"/>
                                <div class='input-group'>
                                	<div class='w220'>
                                		<input name='Starttime' type='time' class='form-control' value="<c:out value="${stime1}"/>:<c:out value="${stime2}"/>"/>
                                	</div>
                                	<span class='bul_space'>~</span>
                                	<div class='w220'>
                                		<input name='Endtime' type='time' class='form-control' value="<c:out value="${etime1}"/>:<c:out value="${etime2}"/>"/>
                                	</div>
                                </div>
                            </td>
                            <td><button class="btn btn-add-circle ml10 deleteRow3" onclick="del_btn('<c:out value="${list.ordNo}"/>','LIFE_ICVNC_OFC');"><i class="fas fa-minus-circle"></i></button><input type="text" style="display:none" name="smsord_" class="form-control" value="<c:out value="${list.ordNo}"/>"/></td>
                        </tr>
                        <c:set var="count" value="${count + 1}" />
                    </c:if>
                </c:forEach>
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
            $(document).on('click','.newdeleteRow3',function(){
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

<form:form id="form_add" action="/cm/housingcplx/info/editAddressAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="mgmtOfcList" name="mgmtOfcList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="scrtOfcList" name="scrtOfcList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="lifeIcvncOfcList" name="lifeIcvncOfcList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="descriptionList" name="descriptionList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="form_del" action="/cm/housingcplx/info/deleteAddressAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="workTpCd" name="workTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="ordNo" name="ordNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="count" name="count" value="<c:out value="${count}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="isAll" name="isAll" value="N" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>
