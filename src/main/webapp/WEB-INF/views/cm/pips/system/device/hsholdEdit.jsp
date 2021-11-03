<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });

    //검색 버튼
    function search(){
        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        $("#form_search").submit();
    }

    function tab_click(){
        location.href = "/cm/system/device/list";
    }

    //취소 버튼
    function cancel(){
        $("#form_list").submit();
    }

    function modify(index){
        var wpadId = $("#wpadId_"+index).val();
        var deviceArray = $("input[name='check_"+index+"']").length;
        var deviceData = new Array(deviceArray);
        var deviceList = $("input[name='check_"+index+"']");

        for(var i=0; i<deviceArray; i++){
            if(deviceList[i].checked == true) {
                deviceData[i] = $("input[name='check_"+index+"']")[i].value+"/Y";
            }else{
                deviceData[i] = $("input[name='check_"+index+"']")[i].value+"/N";
            }
        }

        $.ajax({
            url: "/cm/system/device/hsholdEditAction",
            type: "POST",
            data: {
                "wpadId": wpadId,
                "deviceArray": deviceData
            },
            success: function(data){
                if(data == "success"){
                    alert("저장성공");
                }else{
                    alert("저장실패");
                }
            },
            error: function(e){
                alert("저장실패");
            }
        });
    }

    //리스트 클릭 상세
    function detailview(wpadId){
        $("#wpadId").val(wpadId);
        $("#form_detail").submit();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area" style="width: 1400px;">
            <h2 class="tit">장치 설정 수정</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>장치 설정</li>
                <li>장치 설정 수정</li>
            </ul>
        </div>

        <div class="tab_wrap" style="width: 1400px;">
            <ul>
                <li onclick="tab_click()"><a href="#">단지별 설정</a></li>
                <li class="on"><a href="#">세대별 설정</a></li>
            </ul>
        </div>

        <div class="table_wrap" style="width: 1400px;">
            <div class="tbl_top_area">
                <div class="right_area">
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th rowspan="2" style="vertical-align:middle;">홈넷사</th>
                        <th rowspan="2" style="vertical-align:middle;">단지이름</th>
                        <th rowspan="2" style="vertical-align:middle;">동</th>
                        <th rowspan="2" style="vertical-align:middle;">호</th>
                        <th colspan="8" style="vertical-align:middle;">장치 타입</th>
                        <th rowspan="2" style="vertical-align:middle;"></th>
                    </tr>
                    <tr>
                        <th>조명</th>
                        <th>밸브</th>
                        <th>에어컨</th>
                        <th>난방</th>
                        <th>환기</th>
                        <th>대기전력</th>
                        <th>전동커튼</th>
                        <th>일괄스위치</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceDetail}" var="list" varStatus="status">
                    <input type="text" id="wpadId_${status.index}" name="wpadId" value="<c:out value="${list.wpadId}"/>" style="width:0;height:0;visibility:hidden"/>
                        <tr>
                            <c:choose>
                                <c:when test="${list.bizcoCd eq 'COMAX'}">
                                    <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');">코맥스</a></td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'KOCOM'}">
                                    <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');">코콤</a></td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'HYUNDAITEL'}">
                                    <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');">현대통신</a></td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'ICONTROLS'}">
                                    <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');">HDC 아이콘트롤스</a></td>
                                </c:when>
                                <c:when test="${list.bizcoCd eq 'OTHER'}">
                                    <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');">기타</a></td>
                                </c:when>
                            </c:choose>

                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');"><c:out value="${list.houscplxNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');"><c:out value="${list.dongNo}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.wpadId}"/>');"><c:out value="${list.hoseNo}"/></a></td>

                        <c:choose>
                            <c:when test="${list.lightsYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="lights_${status.index}" value="LIGHTS" checked />
                                        <label class="custom-control-label" for="lights_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.lightsYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="lights_${status.index}" value="LIGHTS" />
                                        <label class="custom-control-label" for="lights_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.lightsYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.gaslockYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="gaslock_${status.index}" value="GASLOCK" checked />
                                        <label class="custom-control-label" for="gaslock_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.gaslockYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="gaslock_${status.index}" value="GASLOCK" />
                                        <label class="custom-control-label" for="gaslock_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.gaslockYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.airconYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="aircon_${status.index}" value="AIRCON" checked />
                                        <label class="custom-control-label" for="aircon_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.airconYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="aircon_${status.index}" value="AIRCON" />
                                        <label class="custom-control-label" for="aircon_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.airconYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.heatingYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="heating_${status.index}" value="HEATING" checked />
                                        <label class="custom-control-label" for="heating_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.heatingYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="heating_${status.index}" value="HEATING" />
                                        <label class="custom-control-label" for="heating_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.heatingYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.ventilatorYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="ventilator_${status.index}" value="VENTILATOR" checked />
                                        <label class="custom-control-label" for="ventilator_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.ventilatorYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="ventilator_${status.index}" value="VENTILATOR" />
                                        <label class="custom-control-label" for="ventilator_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.ventilatorYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.smartConsentYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="smartConsent_${status.index}" value="SMART_CONSENT" checked />
                                        <label class="custom-control-label" for="smartConsent_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.smartConsentYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="smartConsent_${status.index}" value="SMART_CONSENT" />
                                        <label class="custom-control-label" for="smartConsent_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.smartConsentYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.curtainYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="curtain_${status.index}" value="CURTAIN" checked />
                                        <label class="custom-control-label" for="curtain_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.curtainYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="curtain_${status.index}" value="CURTAIN" />
                                        <label class="custom-control-label" for="curtain_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.curtainYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <c:choose>
                            <c:when test="${list.lightSwitchYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="lightSwitch_${status.index}" value="LIGHT_SWITCH" checked />
                                        <label class="custom-control-label" for="lightSwitch_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.lightSwitchYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check_${status.index}" id="lightSwitch_${status.index}" value="LIGHT_SWITCH" />
                                        <label class="custom-control-label" for="lightSwitch_${status.index}"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${list.lightSwitchYn eq 'X'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:when>
                        </c:choose>

                        <td>
                            <button class="btn btn-bluegreen" type="button" onclick="modify(${status.index})">저장</button>
                        </td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2" style="width: 1400px;">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
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

<form:form id="form_detail" action="/cm/system/device/hsholdView" name="detail" commandName="detail" method="post">
    <input type="text" id="wpadId" name="wpadId" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxNm" name="houscplxNm" value="<c:out value="${houscplxNm}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="dongNo" name="dongNo" value="<c:out value="${dongNo}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="hoseNo" name="hoseNo" value="<c:out value="${hoseNo}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_list" action="/cm/system/device/hsholdEditGoList" name="list" commandName="list" method="post">
    <input type="text" id="houscplxCd_" name="houscplxCd_" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxNm_" name="houscplxNm_" value="<c:out value="${houscplxNm}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="dongNo_" name="dongNo_" value="<c:out value="${dongNo}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="hoseNo_" name="hoseNo_" value="<c:out value="${hoseNo}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>
