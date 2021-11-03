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
    $(document).ready(function(){
        var list = new Array();
        <c:forEach items="${householdDongList}" var="list" varStatus="status">
            var dongno = "${list.dongNo}";
            list.push(dongno);
        </c:forEach>
        var uniq = list.reduce(function(a,b){
            if (a.indexOf(b) < 0 ) a.push(b);
            return a;
          },[]);

         for(var i = 0 ; i < uniq.length ; i++){
            $("#dong").append("<option value='"+uniq[i]+"'>"+uniq[i]+"</option>");
         }

         var dong = "${dongNo}";
         $("#dong").val(dong);
    })

    function OnChange(e){
        var dong = $(e).val();
        var hcd = "<c:out value="${houscplxCd}"/>";
        $("#searchcd").val(hcd);
        $("#searchdong").val(dong);
        $("#form_search").submit();
    }

    function tab_click(e,n){
        if(e == "summary"){
            $("#summarycd").val(n);
            $("#summary").submit();
        }
        if(e == "notice"){
            $("#noticecd").val(n);
            var type = "HOUSCPLX_INFO";
            $("#nptype").val(type);
            $("#notice").submit();
        }
        if(e == "location"){
            $("#locationcd").val(n);
            var type = "PLOT_PLAN";
            $("#lptype").val(type);
            $("#location").submit();
        }
        if(e == "floor"){
            $("#floorcd").val(n);
            var type = "FLOOR_PLAN";
            $("#fptype").val(type);
            $("#floor").submit();
        }
        if(e == "mgto"){
            $("#mgtocd").val(n);
            $("#mgto").submit();
        }
        if(e == "cctv"){
            $("#cctvcd").val(n);
            $("#cctv").submit();
        }
        if(e == "facilities"){
            $("#facilitiescd").val(n);
            $("#facilities").submit();
        }
        if(e == "etc"){
            $("#etccd").val(n);
            $("#etc").submit();
        }
    }

    function modify(e){
        $("#sizecd").val(e);
        $("#form_modify").submit();
    }

    function exbtn(){
        var list = new Array();

        <c:forEach items="${household}" var="list" varStatus="status">
            var cJson = new Object();
            cJson.동 = "<c:out value="${list.dongNo}"/>";
            cJson.호 = "<c:out value="${list.hoseNo}"/>";
            cJson.전용면적 = "<c:out value="${list.dimQty}"/>";
            cJson.타입 = "<c:out value="${list.ptypeNm}"/>";
            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "세대별 평형";
        param.tableHeader = "['동', '호', '전용면적', '타입']";
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
                link.download= today+"_세대별평형.xlsx";
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

    function list_back(){
        location.href = "/cm/housingcplx/info/list";
    }

    function add(e){
        $("#add_cd").val(e);
        $("#form_size_add").submit();
    }
</script>

<div id="container">
    <div class="container">

        <div class="top_area">
            <h2 class="tit">세대별 평형</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>세대별 평형</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click('summary','<c:out value="${houscplxCd}"/>')"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','<c:out value="${houscplxCd}"/>')"><a href="#">우리 단지 알림</a></li>
                <li onclick="tab_click('location','<c:out value="${houscplxCd}"/>')"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','<c:out value="${houscplxCd}"/>')"><a href="#">타입별 평면도</a></li>
                <li class="on"><a href="#">세대별 평형</a></li>
                <li onclick="tab_click('mgto','<c:out value="${houscplxCd}"/>')"><a href="#">관리실/경비실</a></li>
                <li onclick="tab_click('cctv','<c:out value="${houscplxCd}"/>')"><a href="#">CCTV</a></li>
                <li onclick="tab_click('facilities','<c:out value="${houscplxCd}"/>')"><a href="#">시설업체정보</a></li>
                <li onclick="tab_click('etc','<c:out value="${houscplxCd}"/>')"><a href="#">기타</a></li>
            </ul>
        </div>
        <c:choose>
            <c:when test="${not empty household}">
                <div class="table_wrap">
                    <div class="tbl_top_area no_count">
                        <div class="left_area">
                            <div class="depth2_tr w220">
                                <div class="th w20">동</div>
                                <div class="td">
                                    <select name="dong" id="dong" class="custom-select" onchange="OnChange(this)">
                                        <option value="all">전체</option>

                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="right_area">
                            <button class="btn btn-green btn-sm" type="button" onclick="exbtn();"><i class="fas fa-table"></i>Export</button>
                        </div>
                    </div>
                    <table class="table" id="table1">
                        <thead>
                            <tr>
                                <th scope="col">동</th>
                                <th scope="col">호</th>
                                <th scope="col">전용면적 (m<sup>2</sup>)</th>
                                <th scope="col">타입</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${household}" var="list" varStatus="status">
                                <tr>
                                    <td class="text-center"><c:out value="${list.dongNo}"/></td>
                                    <td class="text-center"><c:out value="${list.hoseNo}"/></td>
                                    <td class="text-center"><c:out value="${list.dimQty}"/></td>
                                    <td class="text-center"><c:out value="${list.ptypeNm}"/></td>
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
                            "emptyTable": "데이터가 없습니다."
                        },
                    });
                </script>
            </c:when>
            <c:otherwise>
                <h2 style="text-align:center;color:gray">세대별 평형을 등록해주세요.</h2>
            </c:otherwise>
        </c:choose>

        <div class="tbl_btm_area2 no_paging">
            <div class="right_area">
                <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <button class="btn btn-brown" type="button" onclick="list_back();">목록</button>
                </c:if>
                <c:choose>
                    <c:when test="${not empty household}">
                        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                            <button class="btn btn-bluegreen" type="button" onclick="modify('<c:out value="${houscplxCd}"/>');">수정</button>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-bluegreen" type="button" onclick="add('<c:out value="${houscplxCd}"/>');">등록</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>

</div>
<form:form id="form_modify" action="/cm/housingcplx/info/householdPtype/edit" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="sizecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/housingcplx/info/householdPtype/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="searchcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="searchdong" name="dongNo" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_size_add" action="/cm/housingcplx/info/householdPtype/add" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="add_cd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="summary" action="/cm/housingcplx/info/intro/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="summarycd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="notice" action="/cm/housingcplx/info/notice/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="noticecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="nptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="location" action="/cm/housingcplx/info/plot/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="locationcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="lptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="floor" action="/cm/housingcplx/info/floor/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="floorcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="fptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="mgto" action="/cm/housingcplx/info/address/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="mgtocd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="cctv" action="/cm/housingcplx/info/cctv/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="cctvcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="facilities" action="/cm/housingcplx/info/facilityInfo/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="facilitiescd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="etc" action="/cm/housingcplx/info/etc/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="etccd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>