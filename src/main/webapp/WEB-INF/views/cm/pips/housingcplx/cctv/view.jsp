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
        if(e == "size"){
            $("#sizecd").val(n);
            $("#size").submit();
        }
        if(e == "mgto"){
            $("#mgtocd").val(n);
            $("#mgto").submit();
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
    //목록으로
    function back(){
        location.href = "/cm/housingcplx/info/list";
    }
    //수정 화면으로
    function edit(e){
        $("#cctvedit").val(e);
        $("#form_edit").submit();
    }
</script>
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">CCTV</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>CCTV</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click('summary','${houscplxCd}')"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','${houscplxCd}')"><a href="#">우리 단지 알림</a></li>
                <li onclick="tab_click('location','${houscplxCd}')"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','${houscplxCd}')"><a href="#">타입별 평면도</a></li>
                <li onclick="tab_click('size','${houscplxCd}')"><a href="#">세대별 평형</a></li>
                <li onclick="tab_click('mgto','${houscplxCd}')"><a href="#">관리실/경비실</a></li>
                <li class="on"><a href="#">CCTV</a></li>
                <li onclick="tab_click('facilities','${houscplxCd}')"><a href="#">시설업체정보</a></li>
                <li onclick="tab_click('etc','${houscplxCd}')"><a href="#">기타</a></li>
            </ul>
        </div>

        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">CCTV명</th>
                        <th scope="col">설명</th>
                        <th scope="col">URL</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cctvInfo}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center">${list.cctvNm}</td>
                            <td>${list.cont}</td>
                            <td>${list.urlCont}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area no_paging">
                <div class="right_area">
                    <button class="btn btn-brown" onclick="back();">목록</button>
                    <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                        <button class="btn btn-bluegreen" onclick="edit('<c:out value="${houscplxCd}"/>');">수정</button>
                    </c:if>
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
                "columnDefs": [
                    { "width": "18%", "targets": 0 },
                    { "width": "25%", "targets": 1 }
                ]
            });
        </script>

    </div>

</div>


<form:form id="form_edit" action="/cm/housingcplx/info/cctv/edit" name="housingcplx" commandName="housingcplx" method="post">
    <input type="text" id="cctvedit" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="summary" action="/cm/housingcplx/info/intro/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="summarycd" name="houscplxCd" value="${housingcplx.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="notice" action="/cm/housingcplx/info/notice/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="noticecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="nptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="location" action="/cm/housingcplx/info/plot/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="locationcd" name="houscplxCd" value="${housingCplxPtype.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="lptype" name="ptypeTpCd" value="${housingCplxPtype.ptypeTpCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="floor" action="/cm/housingcplx/info/floor/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="floorcd" name="houscplxCd" value="${housingCplxPtype.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="fptype" name="ptypeTpCd" value="${housingCplxPtype.ptypeTpCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="size" action="/cm/housingcplx/info/householdPtype/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="sizecd" name="houscplxCd" value="${housingcplx.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="mgto" action="/cm/housingcplx/info/address/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="mgtocd" name="houscplxCd" value="${housingcplx.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="facilities" action="/cm/housingcplx/info/facilityInfo/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="facilitiescd" name="houscplxCd" value="${housingcplx.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="etc" action="/cm/housingcplx/info/etc/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="etccd" name="houscplxCd" value="${housingcplx.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
</form:form>