<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    #table1_info{
        visibility : hidden;
    }

</style>
<script type="text/javascript">
    $(document).ready(function(){

    });

    function tab_click(){
        location.href = "/cm/system/device/list";
    }

    //취소 버튼
    function cancel(){
        window.history.back();
    }

    function modify(index){

        var deviceArray = $("input[name='check']").length;
        var deviceData = new Array(deviceArray);
        var deviceList = $("input[name='check']");

        for(var i=0; i<deviceArray; i++){
            if(deviceList[i].checked == true) {
                deviceData[i] = $("input[name='check']")[i].value+"_Y";
            }else{
                deviceData[i] = $("input[name='check']")[i].value+"_N";
            }
        }

        $("#deviceArray").val(deviceData);
        $("#form_edit").submit();
    }

    function OnChange(e){
        var table = $('#table1').DataTable();

        table.search($(e).val()).draw();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">장치 설정 상세</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>장치 설정</li>
                <li>장치 설정 상세</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click()"><a href="#">단지별 설정</a></li>
                <li class="on"><a href="#">세대별 설정</a></li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:46%"/>
                    <col style="width:8%"/>
                    <col style="width:14%"/>
                    <col style="width:8%"/>
                    <col style="width:14%"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>단지명</th>
                        <td><c:out value="${houscplxNm_}"/></td>
                        <th>동</th>
                        <td><c:out value="${dong}"/></td>
                        <th>호</th>
                        <td><c:out value="${hose}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br/>

        <div>
            <div class="tbl_top_area">
                <div class="left_area">
                    <label>장치타입</label>
                    <select class="custom-select" onchange="OnChange(this);" style="width:20%;margin-left:10px;">
                        <option value="">전체</option>
                        <option value="조명">조명</option>
                        <option value="가스밸브">가스밸브</option>
                        <option value="에어컨">에어컨</option>
                        <option value="난방">난방</option>
                        <option value="환기 장치">환기장치</option>
                        <option value="대기전력">대기전력</option>
                        <option value="전동커튼">전동커튼</option>
                        <option value="일괄스위치">일괄스위치</option>
                    </select>
                </div>
            </div>
        </div>
        <br/>

        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">장치타입</th>
                        <th scope="col">장치이름</th>
                        <th scope="col">댁내위치</th>
                        <th scope="col">제조사번호</th>
                        <th scope="col">시리얼번호</th>
                        <th scope="col">모델번호</th>
                        <th scope="col">화면표시여부</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceDetail}" var="list" varStatus="status">
                        <tr>
                            <td><c:out value="${list.deviceTpCdNm}"/></td>
                            <td><c:out value="${list.deviceNm}"/></td>
                            <td><c:out value="${list.locaNm}"/></td>
                            <td><c:out value="${list.mnfcoNm}"/></td>
                            <td><c:out value="${list.serlNo}"/></td>
                            <td><c:out value="${list.mdlNm}"/></td>
                            <c:choose>
                                <c:when test="${list.screenYn eq 'Y'}">
                                    <td class="text-center">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" name="check" id="screenYn_${status.index}" value="<c:out value="${list.deviceId}"/>/<c:out value="${list.wpadId}"/>" checked />
                                            <label class="custom-control-label" for="screenYn_${status.index}"></label>
                                        </div>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-center">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" name="check" id="screenYn_${status.index}" value="<c:out value="${list.deviceId}"/>/<c:out value="${list.wpadId}"/>" />
                                            <label class="custom-control-label" for="screenYn_${status.index}"></label>
                                        </div>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="modify();">저장</button>
            </div>
        </div>

        <script type="text/javascript">
            $('#table1').DataTable({
                "order": [],
                "bLengthChange" : false,
                "paging" : false,
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

<form:form id="form_edit" action="/cm/system/device/hsholdViewAction" name="edit" commandName="edit" method="post">
    <input type="text" id="deviceArray" name="deviceArray" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxNm" name="houscplxNm" value="<c:out value="${houscplxNm}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="dongNo" name="dongNo" value="<c:out value="${dongNo}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="hoseNo" name="hoseNo" value="<c:out value="${hoseNo}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>