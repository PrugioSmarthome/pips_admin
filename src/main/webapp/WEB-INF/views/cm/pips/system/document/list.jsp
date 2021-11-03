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
        var grpTpCd = "<c:out value="${lnkAtchFileGrpTpCd}"/>";
        if(grpTpCd == ""){
            $("#grpTpCd_").val("all");
            $("#lnkAtchFileGrpTpCd").val("all");
        }else{
            $("#grpTpCd_").val(grpTpCd);
            $("#lnkAtchFileGrpTpCd").val(grpTpCd);
        }

        var tpCd = "<c:out value="${lnkAtchFileTpCd}"/>";
        if(tpCd == ""){
            $("#tpCd_").val("all");
            $("#lnkAtchFileTpCd").val("all");
        }else{
            $("#tpCd_").val(tpCd);
            $("#lnkAtchFileTpCd").val(tpCd);
        }

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

        $("#start_").val($("#start").val().replace(/\./gi,''));
        $("#end_").val($("#end").val().replace(/\./gi,''));
        $("#grpTpCd_").val($("#lnkAtchFileGrpTpCd").val());
        $("#tpCd_").val($("#lnkAtchFileTpCd").val());
        $("#form_search").submit();
    }

    function btn_add() {
        location.href = "/cm/system/document/add";
    }

    function detail_view(no) {
        $("#lnkAtchFileId").val(no);
        $("#form_detal").submit();
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">문서관리 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>문서 관리</li>
                <li>문서 목록</li>
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
                            <select name="lnkAtchFileGrpTpCd" id="lnkAtchFileGrpTpCd" class="custom-select">
                                <option value="all">전체</option>
                                <option value="MANUAL">매뉴얼</option>
                                <option value="LIVING_GUIDE">리빙가이드북</option>
                            </select>
                        </td>
                        <th>· 서비스명</th>
                        <td style="width:250px;">
                            <select name="lnk_atch_file_tp_cd" id="lnkAtchFileTpCd" class="custom-select">
                                <option value="all">전체</option>
                                <option value="SIMPLE_MANUAL">간편 안내 가이드</option>
                                <option value="DETAIL_MANUAL">상세 안내 가이드</option>
                                <option value="LIVING_GUIDE">리빙 가이드</option>
                            </select>
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
                        <th scope="col">문서종류</th>
                        <th scope="col">대상단지</th>
                        <th scope="col">노출여부</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${documentList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkAtchFileId}"/>');"><fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkAtchFileId}"/>');"><c:out value="${list.lnkAtchFileGrpTpCdNm}"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkAtchFileId}"/>');"><c:out value="${list.lnkAtchFileTpCdNm}"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.lnkAtchFileId}"/>');"><c:out value="${list.houscplxNm}"/></a></td>
                        <c:choose>
                            <c:when test="${list.useYn eq 'Y'}">
                                <td class="text-center">YES</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">NO</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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

<form:form id="form_search" action="/cm/system/document/list" name="detail" commandName="detail" method="post">
    <input type="text" id="start_" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="end_" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="grpTpCd_" name="lnkAtchFileGrpTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="tpCd_" name="lnkAtchFileTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detal" action="/cm/system/document/view" name="detail" commandName="detail" method="post">
    <input type="text" id="lnkAtchFileId" name="lnkAtchFileId" style="width:0;height:0;visibility:hidden"/>
</form:form>
