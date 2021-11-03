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
    $(document).ready(function () {

        localStorage.setItem('userInfo', btoa("<c:out value="${userId}"/>"+"/"+"<c:out value="${time}"/>"+"/"+"<c:out value="${userGroupName}"/>"));

    });

    //뒤로가기
    function back(){
        location.href = "/cm/housingcplx/info/list";
    }
    //수정
    function modify(cd){
        $("#houscplxCd").val(cd);
        $("#flag").val("N");

        $("#form_list").submit();
    }

    function tab_click(e,n){
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
        if(e == "etc"){
            $("#etccd").val(n);
            $("#etc").submit();
        }
    }


    //단지 활성화, 비활성화 버튼
    function api_call(yn,cd,hid){
        var param = new Object();
        param.hmnetId = hid;
        param.houscplxCd = cd;
        param.tgtTp = "complex";
        if(yn == "on"){
            param.trnsmYn = "Y";
        }
        if(yn == "off"){
            param.trnsmYn = "N";
        }

        $.ajax({
            url: '/cm/api/hmnet/conf/send',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                var return_data = JSON.stringify(data);
                return_data = JSON.parse(return_data);

                if(return_data.params[0].reqStatus == true){
                    alert("요청되었습니다.");
                    if(yn == "on"){
                        $("#input_off").prop('checked', false);
                        $("#input_on").prop('checked', true);
                    }
                    if(yn == "off"){
                        $("#input_off").prop('checked', true);
                        $("#input_on").prop('checked', false);
                    }
                }else{
                    alert("요청이 실패했습니다.");
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
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">단지개요</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>단지개요</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li class="on"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">우리 단지 알림</a></li>
                <li onclick="tab_click('location','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">타입별 평면도</a></li>
                <li onclick="tab_click('size','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">세대별 평형</a></li>
                <li onclick="tab_click('mgto','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">관리실/경비실</a></li>
                <li onclick="tab_click('cctv','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">CCTV</a></li>
                <li onclick="tab_click('facilities','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">시설업체정보</a></li>
                <li onclick="tab_click('etc','<c:out value="${housingCplx.houscplxCd}"/>')"><a href="#">기타</a></li>
            </ul>
        </div>
        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:15%"/>
                    <col style="width:45%"/>
                    <col style="width:15%"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                            <th>단지명칭</th>
                            <td>
                            <c:out value="${housingCplx.houscplxNm}"/>
                                <c:if test="${housingCplxCount eq '0'}">
                                    <div class="toggle_switch ml15">
                                        <c:if test="${housingCplx.delYn eq 'N'}">
                                            <div class="inp"><input type="radio" id="input_on" name="switch" checked readonly/><label onclick="api_call('on','<c:out value="${housingCplx.houscplxCd}"/>','<c:out value="${housingCplx.hmnetId}"/>');" for="switch1">활성</label></div>
                                            <div class="inp"><input type="radio" id="input_off" name="switch" readonly/><label onclick="api_call('off','<c:out value="${housingCplx.houscplxCd}"/>','<c:out value="${housingCplx.hmnetId}"/>');" for="switch2">비활성</label></div>
                                        </c:if>
                                        <c:if test="${housingCplx.delYn eq 'Y'}">
                                            <div class="inp"><input type="radio" id="input_on" name="switch" readonly/><label onclick="api_call('on','<c:out value="${housingCplx.houscplxCd}"/>','<c:out value="${housingCplx.hmnetId}"/>');" for="switch1">활성</label></div>
                                            <div class="inp"><input type="radio" id="input_off" name="switch" checked readonly/><label onclick="api_call('off','<c:out value="${housingCplx.houscplxCd}"/>','<c:out value="${housingCplx.hmnetId}"/>');" for="switch2">비활성</label></div>
                                        </c:if>
                                    </div>
                                </c:if>
                            </td>
                            <th>단지코드</th>
                            <td>${housingCplx.houscplxCd}</td>
                        </c:if>
                        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                            <th>단지명칭</th>
                            <td colspan="3">
                            <c:out value="${housingCplx.houscplxNm}"/>
                            </td>
                        </c:if>
                    </tr>
                    <tr>
                        <th>화면표시단지명</th>
                        <td colspan="3">
                            <c:out value="${housingCplx.screenHouscplxNm}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>단지주소</th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w70">우편번호</div>
                                <div class="td"><c:out value="${housingCplx.postNo}"/></div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w70">도로명</div>
                                <div class="td"><c:out value="${housingCplx.roadnmBasAddrCont}"/></div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w70">지번</div>
                                <div class="td"><c:out value="${housingCplx.areaBasAddrCont}"/></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>대지면적(m2)</th>
                        <td>
                            <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                                <c:out value="${housingCplx.landDimQty}"/>
                            </c:if>
                            <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                                <c:out value="********"/>
                            </c:if>
                        </td>
                        <th>연면적(m2)</th>
                        <td>
                            <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                                <c:out value="${housingCplx.archDimQty}"/>
                            </c:if>
                            <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                                <c:out value="********"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>사업승인일</th>
                        <td>
                            <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                                <c:out value="${housingCplx.busiApprYmd}"/>
                            </c:if>
                            <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                                <c:out value="********"/>
                            </c:if>
                        </td>
                        <th>사업준공일</th>
                        <td>
                            <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                                <c:out value="${housingCplx.busiConendYmd}"/>
                            </c:if>
                            <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                                <fmt:parseDate value="${housingCplx.busiConendYmd}" var="busiConendDate" pattern="yyyyMMdd"/>
                                <fmt:formatDate value="${busiConendDate}" pattern="yyyy-MM"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>총 세대수</th>
                        <td><c:out value="${housingCplx.wholeHsholdCnt}"/></td>
                        <th>총 동수</th>
                        <td><c:out value="${housingCplx.wholeDongCnt}"/></td>
                    </tr>
                    <tr>
                        <th>지하/지상</th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w50">지하층</div>
                                <div class="td"><c:out value="${housingCplx.lwstUngrFlrcnt}"/></div>
                                <div class="th w50">지상층</div>
                                <div class="td"><c:out value="${housingCplx.hgstAbgrFlrcnt}"/></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>홈넷서버</th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w100">홈넷서버종류</div>
                                <c:if test="${housingCplx.svrTpCd eq 'UNIFY_SVR'}">
                                    <div class="td">통합서버</div>
                                </c:if>
                                <c:if test="${housingCplx.svrTpCd eq 'HOUSCPLX_SVR'}">
                                    <div class="td">단지서버</div>
                                </c:if>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w100">홈넷서버이름</div>
                                <div class="td"><c:out value="${housingCplx.hmnetNm}"/></div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w100">홈넷사</div>
                                <div class="td"><c:out value="${housingCplx.bizcoNm}"/></div>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <th>주차대수</th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w40">전체</div>
                                <div class="td"><c:out value="${housingCplx.wholeWlLcnt}"/></div>
                                <div class="th w50">세대당</div>
                                <div class="td"><c:out value="${housingCplx.hsholdWlLcnt}"/></div>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <th>시공사</th>
                        <td id="bizco_nm"><c:out value="${housingCplx.excwkBizcoNm}"/></td>
                        <th>난방방식</th>
                        <td><c:out value="${housingCplx.heatTpNm}"/></td>
                    </tr>

                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <input type="button" onclick="back();" class="btn btn-brown" value="목록"/>
                </c:if>
                <input type="button" onclick="modify('<c:out value="${housingCplx.houscplxCd}"/>');" class="btn btn-bluegreen" value="수정">
            </div>
        </div>

    </div>

</div>

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
<form:form id="etc" action="/cm/housingcplx/info/etc/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="etccd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_list" action="/cm/housingcplx/info/intro/edit" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>