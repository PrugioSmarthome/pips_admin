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
    $(document).ready(function(){

    });

    function btn_search(){
        $("#outcomDateStart").val($("#start").val().replace(/\./gi,''));
        $("#outcomDateEnd").val($("#end").val().replace(/\./gi,''));

        $("#form_search").submit();
    }

    //엑셀 다운로드
    function excel(){
        var list = new Array();

        <c:forEach items="${failureOccurrenceList}" var="failureOccurrenceList" varStatus="status">
            var cJson = new Object();

            cJson.홈넷사 = "<c:out value="${failureOccurrenceList.homenetNm}"/>";
            cJson.단지명 = "<c:out value="${failureOccurrenceList.houscplxNm}"/>";
            cJson.발생건수 = "<c:out value="${failureOccurrenceList.resultList}"/>";

            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "장애 발생 목록";
        param.tableHeader = "['홈넷사', '단지명', '발생건수']";

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
                link.download= today+"_장애발생목록.xlsx";
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
            <h2 class="tit">장애 발생 목록</h2>
            <ul class="location">
                <li>통계</li>
                <li>장애 발생 현황</li>
                <li>장애 발생 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:40%"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 기간</th>
                        <td style="width:350px">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="start" id="start" value="<c:out value="${outcomDateStart}"/>" />
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" name="end" id="end" value="<c:out value="${outcomDateEnd}"/>" />
                            </div>
                            <script type="text/javascript">
                            $('#start').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#end').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            </script>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br/>

        <div class="table_wrap">
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:20%"/>
                    <col style="width:60%"/>
                    <col style="width:20%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">홈넷사</th>
                        <th scope="col">단지명</th>
                        <th scope="col">발생건수</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${failureOccurrenceList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><c:out value="${list.homenetNm}"/></td>
                        <td class="text-center"><c:out value="${list.houscplxNm}"/></td>
                        <td class="text-center"><c:out value="${list.resultList}"/></td>
                    </tr>
                 </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="javascript:excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $('#table1').DataTable({
                "order": [],
                "bLengthChange" : false,
                "dom": '<<t>p>',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "조회된 데이터가 없습니다."
                },
            });
        </script>
    </div>
</div>

<form:form id="form_search" action="/cm/system/failureOccurrence/list" name="info" commandName="info" method="post">
      <input type="text" id="outcomDateStart" name="outcomDateStart" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="outcomDateEnd" name="outcomDateEnd" style="width:0;height:0;visibility:hidden"/>
</form:form>
