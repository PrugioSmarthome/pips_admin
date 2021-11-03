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

    //엑셀 다운로드
    function excel(){
        var list = new Array();
        <c:forEach items="${statisticsList}" var="list" varStatus="status">
            var cJson = new Object();

            cJson.단지명 = "<c:out value="${list.houscplxNm}"/>";
            cJson.총세대수 = "<c:out value="${list.holdCnt}"/>";
            cJson.App가입세대수 = "<c:out value="${list.holdUserCnt}"/>";
            cJson.App가입구성원수 = "<c:out value="${list.userCnt}"/>";

            list.push(cJson);
        </c:forEach>


        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "입주민 현황 통계";
        param.tableHeader = "['단지명', '총세대수', 'App가입세대수', 'App가입구성원수']";

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
                link.download= today+"_입주민현황통계.xlsx";
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
            <h2 class="tit">입주민 현황 목록</h2>
            <ul class="location">
                <li>통계</li>
                <li>입주민 현황 통계</li>
                <li>입주민 현황 목록</li>
            </ul>
        </div>

        <div class="table_wrap">
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:40%"/>
                    <col style="width:20%"/>
                    <col style="width:20%"/>
                    <col style="width:20%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">단지명</th>
                        <th scope="col">총세대수</th>
                        <th scope="col">App가입 세대수</th>
                        <th scope="col">App가입 구성원수</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${statisticsList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><c:out value="${list.houscplxNm}"/></td>
                        <td class="text-center"><c:out value="${list.holdCnt}"/></td>
                        <td class="text-center"><c:out value="${list.holdUserCnt}"/></td>
                        <td class="text-center"><c:out value="${list.userCnt}"/></td>
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
