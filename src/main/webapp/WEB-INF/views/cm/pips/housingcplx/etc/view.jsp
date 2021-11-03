<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/file-saver@2.0.2/dist/FileSaver.min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<script type="text/javascript">
    $(document).ready(function(){
        var jsonarraylist = ${lnkSvcInfoList};
        var cnt = jsonarraylist.length
        var webArray = new Array();
        var appArray = new Array();
        for(var i = 0 ; i < cnt ; i++){
            if(jsonarraylist[i].type.indexOf("WEB") != -1){
                webArray.push(jsonarraylist[i]);
            }else{
                appArray.push(jsonarraylist[i]);
            }
        }

        if(appArray.length > 0){
            var cnt = appArray.length;
            for(var i = 0 ; i < cnt ; i++){
                var name;
                if(appArray[i].type == "PRUGIO_APP"){
                    name = "푸르지오 APP";
                }else{
                    name = "추가서비스 APP";
                }
                var filename = appArray[i].file_nm;
                var fileurl = appArray[i].file_url;
                $("#table_area").append('<tr>'+
                                         '<th>서비스명</th>'+
                                         '<td>'+
                                         '<div class="depth2_tr">'+
                                         '<div class="th w100">'+name+'</div>'+
                                         '<div class="td">'+appArray[i].lnk_svc_nm+'</div>'+
                                         '</div>'+
                                         '</td>'+
                                         '</tr>');
            }
        }


        if(webArray.length > 0){
            var cnt = webArray.length;
            for(var i = 0 ; i < cnt ; i++){
                var name;
                if(webArray[i].type == "PRUGIO_WEB"){
                    name = "푸르지오 WEB";
                }else{
                    name = "추가서비스 WEB";
                }

                var webfilename = webArray[i].file_nm;
                var webfileurl = webArray[i].file_url;
                $("#table_area").append('<tr>'+
                                         '<th>서비스명</th>'+
                                         '<td>'+
                                         '<div class="depth2_tr">'+
                                         '<div class="th w100">'+name+'</div>'+
                                         '<div class="td">'+webArray[i].lnk_svc_nm+'</div>'+
                                         '</div>'+
                                         '</td>'+
                                         '</tr>');
            }
        }
    });

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
        if(e == "cctv"){
            $("#cctvcd").val(n);
            $("#cctv").submit();
        }
        if(e == "facilities"){
            $("#facilitiescd").val(n);
            $("#facilities").submit();
        }
    }
    //목록으로
    function back(){
        location.href = "/cm/housingcplx/info/list";
    }
    //수정 화면으로
    function edit(e){
        $("#etcedit").val(e);
        $("#form_edit").submit();
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">기타</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>기타</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click('summary','<c:out value="${houscplxCd}"/>')"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','<c:out value="${houscplxCd}"/>')"><a href="#">우리 단지 알림</a></li>
                <li onclick="tab_click('location','<c:out value="${houscplxCd}"/>')"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','<c:out value="${houscplxCd}"/>')"><a href="#">타입별 평면도</a></li>
                <li onclick="tab_click('size','<c:out value="${houscplxCd}"/>')"><a href="#">세대별 평형</a></li>
                <li onclick="tab_click('mgto','<c:out value="${houscplxCd}"/>')"><a href="#">관리실/경비실</a></li>
                <li onclick="tab_click('cctv','<c:out value="${houscplxCd}"/>')"><a href="#">CCTV</a></li>
                <li onclick="tab_click('facilities','<c:out value="${houscplxCd}"/>')"><a href="#">시설업체정보</a></li>
                <li class="on"><a href="#">기타</a></li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:160px"/>
                    <col />
                    <col style="width:160px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>단지 반경 설정 (m)</th>
                        <td colspan="3"><c:out value="${etcInfo.qryRangeCont}"/></td>
                    </tr>
                    <tr>
                        <th>에너지 단위 및 그래프 최대값 설정</th>
                        <td colspan="3">
                            <div class="depth2_tr">
                            <c:forEach items="${energyInfo}" var="info" varStatus="status">
                                <c:choose>
                                    <c:when test="${info.enrgTpCd eq 'ELCT'}">
                                        <div class="th w60">전기</div>
                                        <div class="td"><c:out value="${info.enrgMaxQty}"/> kWh</div>
                                    </c:when>
                                    <c:when test="${info.enrgTpCd eq 'HOTWTR'}">
                                        <div class="th w60">온수</div>
                                        <c:if test="${info.enrgUntCd eq 'MCAL'}">
                                            <div class="td"><c:out value="${info.enrgMaxQty}"/> mcal</div>
                                        </c:if>
                                        <c:if test="${info.enrgUntCd eq 'M3'}">
                                            <div class="td"><c:out value="${info.enrgMaxQty}"/> m<sup>3</sup></div>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${info.enrgTpCd eq 'HEAT'}">
                                        <div class="th w60">난방</div>
                                        <c:if test="${info.enrgUntCd eq 'MCAL'}">
                                            <div class="td"><c:out value="${info.enrgMaxQty}"/> mcal</div>
                                        </c:if>
                                        <c:if test="${info.enrgUntCd eq 'M3'}">
                                            <div class="td"><c:out value="${info.enrgMaxQty}"/> m<sup>3</sup></div>
                                        </c:if>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            </div>
                            <div class="depth2_tr">
                            <c:forEach items="${energyInfo}" var="info" varStatus="status">
                                <c:choose>
                                    <c:when test="${info.enrgTpCd eq 'GAS'}">
                                        <div class="th w60">가스</div>
                                        <div class="td"><c:out value="${info.enrgMaxQty}"/> m<sup>3</sup></div>
                                    </c:when>
                                    <c:when test="${info.enrgTpCd eq 'WTRSPL'}">
                                        <div class="th w60">수도</div>
                                        <div class="td"><c:out value="${info.enrgMaxQty}"/> m<sup>3</sup></div>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                                <div class="th w60"></div>
                                <div class="td"></div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
        <div class="sub_tit">
            <h3 class="tit">연계 앱 / 웹</h3>
        </div>

        <table class="table2">
            <colgroup>
                <col style="width:160px"/>
                <col />
            </colgroup>
            <tbody id="table_area">

            </tbody>
        </table>

        <div class="sub_tit">
            <h3 class="tit">배너</h3>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th scope="col">파일명</th>
                    <th scope="col">링크정보</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty bannerInfo}">
                        <tr>
                            <td colspan="2" style="text-align:center;">등록된 배너가 없습니다.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${bannerInfo}" var="list" varStatus="status">
                            <tr>
                                <td><c:out value="${list.orgnlFileNm}"/></td>
                                <td><c:out value="${list.linkUrlCont}"/></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        </c:if>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <button class="btn btn-brown" onclick="back();">목록</button>
                </c:if>
                <button class="btn btn-bluegreen" onclick="edit('<c:out value="${houscplxCd}"/>');">수정</button>
            </div>
        </div>

    </div>

</div>

<form:form id="form_edit" action="/cm/housingcplx/info/etc/edit" name="housingcplx" commandName="housingcplx" method="post">
    <input type="text" id="etcedit" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
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
<form:form id="mgto" action="/cm/housingcplx/info/address/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="mgtocd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="cctv" action="/cm/housingcplx/info/cctv/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="cctvcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="facilities" action="/cm/housingcplx/info/facilityInfo/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="facilitiescd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>