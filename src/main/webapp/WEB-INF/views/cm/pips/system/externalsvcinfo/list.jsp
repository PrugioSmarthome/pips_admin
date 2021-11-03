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

    function view(e){
        $("#svcId").val(e);
        $("#form_detail").submit();
    }

    //추가버튼
    function add(){
        location.href = "/cm/system/externalService/add";
    }

    //엑셀 다운로드
    function excel(){
        var list = new Array();

        <c:forEach items="${serviceList}" var="list" varStatus="status">
            var cJson = new Object();
            cJson.Type = '<c:out value="${list.svcNm}"/>';
            cJson.서비스명 = '<c:out value="${list.cont}"/>';
            cJson.ID = '<c:out value="${list.userId}"/>';
            cJson.서비스Key = '<c:out value="${list.svcKeyCd}"/>';
            cJson.유효기간 = '<c:out value="${list.exprtnYmd}"/>';
            cJson.URL = '<c:out value="${list.urlCont}"/>';

            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "외부연계 관리 목록";
        param.tableHeader = "['Type', '서비스명', 'ID', '서비스Key', '유효기간', 'URL']";
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
                link.download= today+"_외부연계 관리 목록.xlsx";
                link.click();
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">외부연계 관리 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>외부연계 관리</li>
                <li>외부연계 관리 목록</li>
            </ul>
        </div>
        <div style="display:none;">
            <div class="tbl_top_area">
                <div class="left_area">
                    <label>Type</label>
                    <select class="custom-select" style="width:20%;margin-left:10px;">
                        <option>전체</option>
                    </select>
                </div>
            </div>
        </div>


        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">Type</th>
                        <th scope="col">서비스명</th>
                        <th scope="col">ID</th>
                        <th scope="col">서비스 Key</th>
                        <th scope="col">유효기간</th>
                        <th scope="col">URL</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${serviceList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><a href="#" onclick="view('<c:out value="${list.svcId}"/>');"><c:out value="${list.svcNm}"/></a></td>
                        <td class="text-center"><a href="#" onclick="view('<c:out value="${list.svcId}"/>');"><c:out value="${list.cont}"/></a></td>
                        <td class="text-center"><a href="#" onclick="view('<c:out value="${list.svcId}"/>');"><c:out value="${list.userId}"/></a></td>
                        <td class="text-center"><a href="#" onclick="view('<c:out value="${list.svcId}"/>');"><c:out value="${list.svcKeyCd}"/></a></td>
                        <td class="text-center"><a href="#" onclick="view('<c:out value="${list.svcId}"/>');"><c:out value="${list.exprtnYmd}"/></a></td>
                        <td class="text-center"><a href="#" onclick="view('<c:out value="${list.svcId}"/>');"><c:out value="${list.urlCont}"/></a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
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
                    { "width": "10%", "targets": 0 },
                    { "width": "13%", "targets": 1 },
                    { "width": "13%", "targets": 2 },
                    { "width": "25%", "targets": 3 },
                    { "width": "10%", "targets": 4 },
                    { "width": "25%", "targets": 5 }
                ],

            });
        </script>

        </div>
    </div>

    <form:form id="form_detail" action="/cm/system/externalService/view" name="detail" commandName="detail" method="post">
          <input type="text" id="svcId" name="svcId" style="width:0;height:0;visibility:hidden"/>
    </form:form>
