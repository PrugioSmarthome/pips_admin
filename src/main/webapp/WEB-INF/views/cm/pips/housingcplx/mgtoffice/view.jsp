<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">

<script type="text/javascript">
    $(document).ready(function(){
        $('#table1').DataTable({
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
            }
        });
        $('#table2').DataTable({
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
            }
        });
        $('#table3').DataTable({
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
            }
        });
    });

    function back(){
        location.href = "/cm/housingcplx/info/list";
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
        if(e == "size"){
            $("#sizecd").val(n);
            $("#size").submit();
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

    function editview(e){
        $("#editcd").val(e);
        $("#edit_form").submit();

    }

    function add(e){
        $("#addcd").val(e);
        $("#add_form").submit();
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">관리실/경비실</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>관리실/경비실</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click('summary','<c:out value="${houscplxCd}"/>')"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','<c:out value="${houscplxCd}"/>')"><a href="#">우리 단지 알림</a></li>
                <li onclick="tab_click('location','<c:out value="${houscplxCd}"/>')"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','<c:out value="${houscplxCd}"/>')"><a href="#">타입별 평면도</a></li>
                <li onclick="tab_click('size','<c:out value="${houscplxCd}"/>')"><a href="#">세대별 평형</a></li>
                <li class="on"><a href="#">관리실/경비실</a></li>
                <li onclick="tab_click('cctv','<c:out value="${houscplxCd}"/>')"><a href="#">CCTV</a></li>
                <li onclick="tab_click('facilities','<c:out value="${houscplxCd}"/>')"><a href="#">시설업체정보</a></li>
                <li onclick="tab_click('etc','<c:out value="${houscplxCd}"/>')"><a href="#">기타</a></li>
            </ul>
        </div>
            <div class="sub_tit">
                <h3 class="tit">관리실 연락처</h3>
            </div>
            <div class="depth2_tr type2">
                <div class="th w60">· 설명</div>
                <c:set var="doneLoop" value="true"/>
                <c:set var="MGMT_OFC_NULL" value="false"/>
                <c:forEach items="${housingCplxCaddrDescription}" var="list" varStatus="status">
                    <c:choose>
                        <c:when test="${empty list.workpTpCd}">
                            <div class="td"><input type="text" class="form-control" placeholder="관리실 설명이 없습니다." disabled/></div>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${list.workpTpCd == 'MGMT_OFC'}">
                                <c:if test="${list.cont == ''}">
                                    <div class="td"><input type="text" class="form-control" placeholder="관리실 설명이 없습니다." disabled/></div>
                                    <c:set var="MGMT_OFC_NULL" value="true"/>
                                </c:if>
                                <c:if test="${list.cont != ''}">
                                    <div class="td"><input type="text" class="form-control" value="<c:out value="${list.cont}"/>" disabled/></div>
                                </c:if>
                                <c:set var="doneLoop" value="false"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${doneLoop eq 'true'}">
                    <div class="td"><input type="text" class="form-control" placeholder="관리실 설명이 없습니다." disabled/></div>
                </c:if>
            </div>
            <div class="table_wrap">
                <table class="table" id="table1">
                    <thead>
                        <tr>
                            <th style="width:30%;">연락처</th>
                            <th style="width:70%;">연락처 제목</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${housingCplxCaddr}" var="list" varStatus="status">
                            <c:if test="${list.workpTpCd == 'MGMT_OFC'}">
                                <tr>
                                    <td class="text-center">
                                        <div class="input-group">
                                            <input type="text" value="<c:out value="${list.caddrCont}"/>" class="form-control" disabled/>
                                        </div>
                                    </td>
                                    <td><input type="text" value="<c:out value="${list.rem}"/>" class="form-control" disabled/></td>
                                </tr>
                            </c:if>
                        </c:forEach>

                    </tbody>
                </table>
            </div>


            <div class="sub_tit">
                <h3 class="tit">경비실 연락처</h3>
            </div>
            <div class="depth2_tr type2">
                <div class="th w60">· 설명</div>
                <c:set var="doneLoop2" value="true"/>
                <c:set var="SCRT_OFC_NULL" value="false"/>
                <c:forEach items="${housingCplxCaddrDescription}" var="list" varStatus="status">
                    <c:choose>
                        <c:when test="${empty list.workpTpCd}">
                            <div class="td"><input type="text" class="form-control" placeholder="경비실 설명이 없습니다." disabled/></div>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${list.workpTpCd == 'SCRT_OFC'}">
                                <c:if test="${list.cont == ''}">
                                    <div class="td"><input type="text" class="form-control" placeholder="경비실 설명이 없습니다." disabled/></div>
                                    <c:set var="SCRT_OFC_NULL" value="true"/>
                                </c:if>
                                <c:if test="${list.cont != ''}">
                                    <div class="td"><input type="text" class="form-control" value="<c:out value="${list.cont}"/>" disabled/></div>
                                </c:if>
                                <c:set var="doneLoop2" value="false"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${doneLoop2 eq 'true'}">
                    <div class="td"><input type="text" class="form-control" placeholder="경비실 설명이 없습니다." disabled/></div>
                </c:if>
            </div>
            <div class="table_wrap">
                <table class="table" id="table2">
                    <thead>
                        <tr>
                            <th style="width:30%;">연락처</th>
                            <th style="width:70%;">연락처 제목</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach items="${housingCplxCaddr}" var="list" varStatus="status">
                            <c:if test="${list.workpTpCd == 'SCRT_OFC'}">
                                <tr>
                                    <td class="text-center">
                                        <div class="input-group">
                                            <input type="text" value="<c:out value="${list.caddrCont}"/>" class="form-control" disabled/>
                                        </div>
                                    </td>
                                    <td><input type="text" value="<c:out value="${list.rem}"/>" class="form-control" disabled/></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="sub_tit">
                <h3 class="tit">생활불편신고 SMS 연락처</h3>
            </div>
            <div class="table_wrap">
                <table class="table" id="table3">
                    <thead>
                        <tr>
                            <th style="width:30%;">연락처</th>
                            <th style="width:70%;">연락 가능 시간</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach items="${housingCplxCaddr}" var="list" varStatus="status">
                            <c:if test="${list.workpTpCd == 'LIFE_ICVNC_OFC'}">
                                <tr>
                                    <td class="text-center">
                                        <div class="input-group">
                                            <input type="text" value="<c:out value="${list.caddrCont}"/>" class="form-control" disabled/>
                                        </div>
                                    </td>
                                    <td>
                                        <c:set value="${fn:substring(list.cntctStime, 0, 2)}" var="stime1"/>
                                        <c:set value="${fn:substring(list.cntctStime, 2, 4)}" var="stime2"/>
                                        <c:set value="${fn:substring(list.cntctEtime, 0, 2)}" var="etime1"/>
                                        <c:set value="${fn:substring(list.cntctEtime, 2, 4)}" var="etime2"/>
                                        <input type="text" value="<c:out value="${stime1}"/>:<c:out value="${stime2}"/> ~ <c:out value="${etime1}"/>:<c:out value="${etime2}"/>" class="form-control" disabled/>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>

                    </tbody>
                </table>
            </div>

        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <button class="btn btn-gray" type="button" onclick="back();">목록</button>
                </c:if>
                <c:choose>
                    <c:when test="${empty housingCplxCaddr && empty housingCplxCaddrDescription}">
                        <button class="btn btn-bluegreen" onclick="add('<c:out value="${houscplxCd}"/>');">등록</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-bluegreen" type="button" onclick="editview('<c:out value="${houscplxCd}"/>');">수정</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<form:form id="edit_form" action="/cm/housingcplx/info/address/edit" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="editcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="add_form" action="/cm/housingcplx/info/address/add" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="addcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
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
<form:form id="size" action="/cm/housingcplx/info/householdPtype/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="sizecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
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