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

        var houscplxCd = "<c:out value="${houscplxCd}"/>";
        $("#houscplxCd").val(houscplxCd);
        var houscplxCd_search = "<c:out value="${houscplxCd_search}"/>";
        $("#houscplxCd_search").val(houscplxCd_search);

    });

    function tab_click(){
        location.href = "/cm/system/device/hsholdList";
    }

    //취소 버튼
    function cancel(){
        window.history.back();
    }

    function modify(){

        var deviceArray = $("input[name='check']").length;
        var deviceData = new Array(deviceArray);
        var deviceList = $("input[name='check']");

        for(var i=0; i<deviceArray; i++){
            if(deviceList[i].checked == true) {
                deviceData[i] = $("input[name='check']")[i].value+"/Y";
            }else{
                deviceData[i] = $("input[name='check']")[i].value+"/N";
            }
        }

        $("#deviceArray").val(deviceData);

        $("#form_modify").submit();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">장치 설정 수정</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>장치 설정</li>
                <li>장치 설정 수정</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li class="on"><a href="#">단지별 설정</a></li>
                <li onclick="tab_click()"><a href="#">세대별 설정</a></li>
            </ul>
        </div>

        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th rowspan="2" style="vertical-align:middle;">홈넷사</th>
                        <th rowspan="2" style="vertical-align:middle;">단지이름</th>
                        <th colspan="8" style="vertical-align:middle;">장치 타입</th>
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
                    <tr>
                        <c:choose>
                            <c:when test="${deviceDetail.bizcoCd eq 'COMAX'}">
                                <td class="text-center">코맥스</td>
                            </c:when>
                            <c:when test="${deviceDetail.bizcoCd eq 'KOCOM'}">
                                <td class="text-center">코콤</td>
                            </c:when>
                            <c:when test="${deviceDetail.bizcoCd eq 'HYUNDAITEL'}">
                                <td class="text-center">현대통신</td>
                            </c:when>
                            <c:when test="${deviceDetail.bizcoCd eq 'ICONTROLS'}">
                                <td class="text-center">HDC 아이콘트롤스</td>
                            </c:when>
                            <c:when test="${deviceDetail.bizcoCd eq 'OTHER'}">
                                <td class="text-center">기타</td>
                            </c:when>
                        </c:choose>

                        <td class="text-center"><c:out value="${deviceDetail.houscplxNm}"/></td>

                        <c:choose>
                            <c:when test="${deviceDetail.lightsYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="lights" value="LIGHTS" checked />
                                        <label class="custom-control-label" for="lights"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.lightsYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="lights" value="LIGHTS" />
                                        <label class="custom-control-label" for="lights"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.gaslockYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="gaslock" value="GASLOCK" checked />
                                        <label class="custom-control-label" for="gaslock"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.gaslockYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="gaslock" value="GASLOCK" />
                                        <label class="custom-control-label" for="gaslock"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.airconYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="aircon" value="AIRCON" checked />
                                        <label class="custom-control-label" for="aircon"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.airconYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="aircon" value="AIRCON" />
                                        <label class="custom-control-label" for="aircon"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.heatingYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="heating" value="HEATING" checked />
                                        <label class="custom-control-label" for="heating"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.heatingYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="heating" value="HEATING" />
                                        <label class="custom-control-label" for="heating"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.ventilatorYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="ventilator" value="VENTILATOR" checked />
                                        <label class="custom-control-label" for="ventilator"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.ventilatorYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="ventilator" value="VENTILATOR" />
                                        <label class="custom-control-label" for="ventilator"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.smartConsentYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="smartConsent" value="SMART_CONSENT" checked />
                                        <label class="custom-control-label" for="smartConsent"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.smartConsentYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="smartConsent" value="SMART_CONSENT" />
                                        <label class="custom-control-label" for="smartConsent"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.curtainYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="curtain" value="CURTAIN" checked />
                                        <label class="custom-control-label" for="curtain"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.curtainYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="curtain" value="CURTAIN" />
                                        <label class="custom-control-label" for="curtain"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${deviceDetail.lightSwitchYn eq 'Y'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="lightSwitch" value="LIGHT_SWITCH" checked />
                                        <label class="custom-control-label" for="lightSwitch"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:when test="${deviceDetail.lightSwitchYn eq 'N'}">
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" name="check" id="lightSwitch" value="LIGHT_SWITCH" />
                                        <label class="custom-control-label" for="lightSwitch"></label>
                                    </div>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <div class="custom-control custom-checkbox">
                                    </div>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="modify();">저장</button>
            </div>
        </div>

    </div>
</div>


<form:form id="form_modify" action="/cm/system/device/editAction" name="edit" commandName="edit" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd_search" name="houscplxCd_search" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="deviceArray" name="deviceArray" style="width:0;height:0;visibility:hidden"/>
</form:form>

