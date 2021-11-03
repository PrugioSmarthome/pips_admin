<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    #user_table_filter{
        visibility : hidden;
        width : 0;
        height : 0;
    }
    #table1_info{
        visibility : hidden;
    }

</style>
<script type="text/javascript">
    $(document).ready(function(){

    });
    function backbtn(){
        window.history.back();
    }

    function OnChange(e){
        var table = $('#table1').DataTable();

        table.search($(e).val()).draw();
    }

    //엑셀 다운로드
    function excel(){
        var list = new Array();

        <c:forEach items="${householdDeviceDetail}" var="householdDeviceDetail" varStatus="status">
            var cJson = new Object();

            cJson.장치타입 = "${householdDeviceDetail.deviceTpCdNm}";
            cJson.장치이름 = "${householdDeviceDetail.deviceNm}";
            cJson.댁내위치 = "${householdDeviceDetail.locaNm}";
            cJson.제조사번호 = "${householdDeviceDetail.mnfcoNm}";
            cJson.시리얼번호 = "${householdDeviceDetail.serlNo}";
            cJson.모델번호 = "${householdDeviceDetail.mdlNm}";
            cJson.기능 = "${householdDeviceDetail.deviceAttrFunc}";

            list.push(cJson);
        </c:forEach>
        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "세대장치 상세";
        param.tableHeader = "['장치타입', '장치이름', '댁내위치', '제조사번호', '시리얼번호', '모델번호', '기능']";
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
                console.log(blob.size);
                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_세대장치상세.xlsx";
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
</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">세대장치 관리 상세</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>세대장치 관리</li>
                <li>세대장치 목록</li>
                <li>세대장치 관리 상세</li>
            </ul>
        </div>
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
                        <option value="환기장치">환기장치</option>
                        <option value="대기전력">대기전력</option>
                        <option value="전동커튼">전동커튼</option>
                        <option value="일괄스위치">일괄스위치</option>
                    </select>
                </div>
            </div>
        </div>


        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">장치타입</th>
                        <th scope="col">장치이름</th>
                        <th scope="col">댁내위치</th>
                        <th scope="col">제조사번호</th>
                        <th scope="col">시리얼번호</th>
                        <th scope="col">모델번호</th>
                        <th scope="col">기능</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${householdDeviceDetail}" var="householdDeviceDetail" varStatus="status">
                        <tr>
                            <td><c:out value="${householdDeviceDetail.deviceTpCdNm}"/></td>
                            <td><c:out value="${householdDeviceDetail.deviceNm}"/></td>
                            <td><c:out value="${householdDeviceDetail.locaNm}"/></td>
                            <td><c:out value="${householdDeviceDetail.mnfcoNm}"/></td>
                            <td><c:out value="${householdDeviceDetail.serlNo}"/></td>
                            <td><c:out value="${householdDeviceDetail.mdlNm}"/></td>
                            <td><c:out value="${householdDeviceDetail.deviceAttrFunc}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-gray" type="button" onclick="backbtn();">목록</button>
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

            });
        </script>

    </div>

</div>