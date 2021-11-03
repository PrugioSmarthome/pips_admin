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

        var str = "<c:out value="${lnkSvcNm}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        str = str.replace(/&#37;/g,"%");
        $("#lnknm").val(str);
        $("#title").val(str);
        var cd = "<c:out value="${lnkSvcGrpTpCd}"/>";
        if(cd == ""){
            $("#lnktpcd").val("all");
        }else{
            $("#lnktpcd").val(cd);
        }
        $("#lnkcd").val("<c:out value="${lnkSvcGrpTpCd}"/>");
        var start = "<c:out value="${startCrDt}"/>";
        var end = "<c:out value="${endCrDt}"/>";
        if(start != ""){
            $("#start_").val(start);
            $("#start").val(start);
        }
        if(end != ""){
            $("#end_").val(end);
            $("#end").val(end);
        }
    });

    function btn_search(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var str = $("#title").val();
        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return false;
        }
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        str = str.replace(/%/g,"&#37;");
        $("#start_").val($("#start").val().replace(/\./gi,''));
        $("#end_").val($("#end").val().replace(/\./gi,''));
        $("#lnkcd").val($("#lnktpcd").val());
        $("#lnknm").val(str);
        $("#form_search").submit();
    }

    function btn_add() {
        location.href = "/cm/system/serviceLink/add";
    }

    function detail_view(no) {
        $("#lnkSvcId").val(no);
        $("#form_detal").submit();
    }

    function excel(){
        var list = new Array();

        <c:forEach items="${serviceLinkList}" var="list" varStatus="status">
            var cJson = new Object();
            cJson.등록일 = '<fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/>';
            cJson.구분 = '<c:out value="${list.commCdNm}"/>';
            cJson.서비스명 = '<c:out value="${list.lnkSvcNm}"/>';

            if ('${list.useYn}' == 'Y') {
                cJson.활성화 = 'YES';
            } else {
                cJson.활성화 = 'NO';
            }

            list.push(cJson);

        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "연계 웹,앱 목록";
        param.tableHeader = "['등록일', '구분', '서비스명', '활성화']";
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

        console.log("param : ", param);

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
                link.download= today+"_연계 웹,앱 목록.xlsx";
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
            <h2 class="tit">연계 웹/앱 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>연계 웹/앱 관리</li>
                <li>연계 웹/앱 목록</li>
            </ul>
        </div>
        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 등록일</th>
                        <td style="width:350px">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="start" id="start"/>
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" name="end" id="end"/>
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
                        <th style="visibility:hidden;">· 구분</th>
                        <td style="width:250px;visibility:hidden;">
                            <select class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>· 구분</th>
                        <td style="width:250px;">
                            <select name="lnktpcd" id="lnktpcd" class="custom-select">
                                <option value="all">전체</option>
                                <c:forEach items="${typeList}" var="list" varStatus="status">
                                    <option value="<c:out value="${list.commCd}"/>"><c:out value="${list.commCdNm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                        <th>· 서비스명</th>
                        <td style="width:250px;">
                            <input type="text" id="title" class="form-control" />
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">

            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" onclick="btn_add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">등록일</th>
                        <th scope="col">구분</th>
                        <th scope="col">서비스명</th>
                        <th scope="col">활성화</th>
                        <th scope="col">정렬순서</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${serviceLinkList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkSvcId}"/>');"><fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkSvcId}"/>');"><c:out value="${list.commCdNm}"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkSvcId}"/>');"><c:out value="${list.lnkSvcNm}"/></a></td>
                        <c:choose>
                            <c:when test="${list.useYn eq 'Y'}">
                                <td class="text-center">YES</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">NO</td>
                            </c:otherwise>
                        </c:choose>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkSvcId}"/>');"><c:out value="${list.lnkOrdNo}"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" onclick="excel();"><i class="fas fa-table"></i>Export</button>
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

<form:form id="form_search" action="/cm/system/serviceLink/list" name="detail" commandName="detail" method="post">
    <input type="text" id="start_" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="end_" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="lnkcd" name="lnkSvcGrpTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="lnknm" name="lnkSvcNm" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detal" action="/cm/system/serviceLink/view" name="detail" commandName="detail" method="post">
    <input type="text" id="lnkSvcId" name="lnkSvcId" style="width:0;height:0;visibility:hidden"/>
</form:form>
