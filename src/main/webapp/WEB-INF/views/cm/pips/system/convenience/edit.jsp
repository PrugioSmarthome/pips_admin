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

    });

    function modify(){

        var convenienceArray = $("input[name='check']").length;
        var convenienceData = new Array(convenienceArray);
        var convenienceList = $("input[name='check']");

        for(var i=0; i<convenienceArray; i++){
            if(convenienceList[i].checked == true) {
                convenienceData[i] = $("input[name='check']")[i].value+"/Y";
            }else{
                convenienceData[i] = $("input[name='check']")[i].value+"/N";
            }
        }

        $("#convenienceArray").val(convenienceData);

        $("#form_modify").submit();
    }

    //취소 버튼
    function cancel(){
        window.history.back();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area" style="width:1400px;">
            <h2 class="tit">편의 시설 수정</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>편의 시설 설정</li>
                <li>편의 시설 수정</li>
            </ul>
        </div>

        <div class="table_wrap2" style="width:1400px;">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>홈넷사</th>
                        <c:choose>
                            <c:when test="${convenienceDetail.bizcoCd eq 'COMAX'}">
                                <td>코맥스</td>
                            </c:when>
                            <c:when test="${convenienceDetail.bizcoCd eq 'KOCOM'}">
                                <td>코콤</td>
                            </c:when>
                            <c:when test="${convenienceDetail.bizcoCd eq 'HYUNDAITEL'}">
                                <td>현대통신</td>
                            </c:when>
                            <c:when test="${convenienceDetail.bizcoCd eq 'ICONTROLS'}">
                                <td>HDC 아이콘트롤스</td>
                            </c:when>
                            <c:when test="${convenienceDetail.bizcoCd eq 'OTHER'}">
                                <td>기타</td>
                            </c:when>
                        </c:choose>
                        <th>단지이름</th>
                        <td><c:out value="${convenienceDetail.houscplxNm}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap" style="width:1400px;">
            <div class="tbl_top_area">
                <div class="right_area">
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th colspan="13" style="vertical-align:middle;">장치 타입</th>
                    </tr>
                    <tr>
                        <th>엘리베이터</th>
                        <th>택배</th>
                        <th>차량 진입</th>
                        <th>방문자</th>
                        <th>주차위치</th>
                        <th>방문차 등록</th>
                        <th>CCTV</th>
                        <th>가족일정</th>
                        <th>전기차 충전기</th>
                        <th>단지 정보</th>
                        <th>편의 시설</th>
                        <th>날씨 정보</th>
                        <th>삼성가전연동</th>
                    </tr>
                </thead>
                <tbody>
                <tr>
                    <c:choose>
                        <c:when test="${convenienceDetail.elevator eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="elevator" value="ELEVATOR" checked />
                                    <label class="custom-control-label" for="elevator"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="elevator" value="ELEVATOR" />
                                    <label class="custom-control-label" for="elevator"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.parcelBox eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="parcelBox" value="PARCEL_BOX" checked />
                                    <label class="custom-control-label" for="parcelBox"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="parcelBox" value="PARCEL_BOX" />
                                    <label class="custom-control-label" for="parcelBox"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.car eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="car" value="CAR" checked />
                                    <label class="custom-control-label" for="car"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="car" value="CAR" />
                                    <label class="custom-control-label" for="car"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.visitor eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="visitor" value="VISITOR" checked />
                                    <label class="custom-control-label" for="visitor"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="visitor" value="VISITOR" />
                                    <label class="custom-control-label" for="visitor"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.parkingLocation eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="parkingLocation" value="PARKING_LOCATION" checked />
                                    <label class="custom-control-label" for="parkingLocation"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="parkingLocation" value="PARKING_LOCATION" />
                                    <label class="custom-control-label" for="parkingLocation"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.visitCar eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="visitCar" value="VISIT_CAR" checked />
                                    <label class="custom-control-label" for="visitCar"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="visitCar" value="VISIT_CAR" />
                                    <label class="custom-control-label" for="visitCar"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.cctv eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="cctv" value="CCTV" checked />
                                    <label class="custom-control-label" for="cctv"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="cctv" value="CCTV" />
                                    <label class="custom-control-label" for="cctv"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.fmlySch eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="fmlySch" value="FMLY_SCH" checked />
                                    <label class="custom-control-label" for="fmlySch"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="fmlySch" value="FMLY_SCH" />
                                    <label class="custom-control-label" for="fmlySch"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.chargingPointState eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="chargingPointState" value="CHARGING_POINT_STATE" checked />
                                    <label class="custom-control-label" for="chargingPointState"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="chargingPointState" value="CHARGING_POINT_STATE" />
                                    <label class="custom-control-label" for="chargingPointState"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.houscplxInfo eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="houscplxInfo" value="HOUSCPLX_INFO" checked />
                                    <label class="custom-control-label" for="houscplxInfo"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="houscplxInfo" value="HOUSCPLX_INFO" />
                                    <label class="custom-control-label" for="houscplxInfo"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.cnvcFacl eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="cnvcFacl" value="CNVC_FACL" checked />
                                    <label class="custom-control-label" for="cnvcFacl"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="cnvcFacl" value="CNVC_FACL" />
                                    <label class="custom-control-label" for="cnvcFacl"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.weatherInfo eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="weatherInfo" value="WEATHER_INFO" checked />
                                    <label class="custom-control-label" for="weatherInfo"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="weatherInfo" value="WEATHER_INFO" />
                                    <label class="custom-control-label" for="weatherInfo"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${convenienceDetail.SElect eq 'Y'}">
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="sElect" value="S_ELECT" checked />
                                    <label class="custom-control-label" for="sElect"></label>
                                </div>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="sElect" value="S_ELECT" />
                                    <label class="custom-control-label" for="sElect"></label>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2" style="width:1400px;">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="modify();">저장</button>
            </div>
        </div>

    </div>
</div>

<form:form id="form_modify" action="/cm/system/convenience/editAction" name="detail" commandName="detail" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="convenienceArray" name="convenienceArray" style="width:0;height:0;visibility:hidden"/>
</form:form>
