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
    #table1_paginate{
        visibility : hidden;
    }
</style>
<script type="text/javascript">
    function OnChange(e){
        var table = $('#table1').DataTable();
        table.search($(e).val()).draw();
    }

    function modify(houscplxCd, facltBizcoId){
        $("#mod_houscplxCd").val(houscplxCd);
        $("#mod_facltBizcoId").val(facltBizcoId);
        $("#form_modify").submit();
    }
</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">시설업체 목록</h2>
            <ul class="location">
                <li>시설업체 관리</li>
                <li>시설업체 정보 관리</li>
                <li>시설업체정보 연락처 상세</li>
            </ul>
        </div>

        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">담당자</th>
                        <th scope="col">휴대폰</th>
                        <th scope="col">사무실</th>
                        <th scope="col">팩스</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty facilityInfoCaddrList}">
                        <c:forEach items="${facilityInfoCaddrList}" var="list" varStatus="status">
                            <tr>
                                <td class="text-center"><c:out value="${list.perchrgNm}"/></td>
                                <td class="text-center"><c:out value="${list.mphoneNo}"/></td>
                                <td class="text-center"><c:out value="${list.offcPhoneNo}"/></td>
                                <td class="text-center"><c:out value="${list.faxNo}"/></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <div class="table_wrap">
                <div class="tbl_btm_area">
                    <div class="right_area">
                        <c:choose>
                            <c:when test="${not empty facilityInfoCaddrList}">
                                <button class="btn btn-bluegreen" type="button" onclick="modify('<c:out value="${houscplxCd}"/>', '<c:out value="${facltBizcoId}"/>');">수정</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-bluegreen" type="button" onclick="modify('<c:out value="${houscplxCd}"/>', '<c:out value="${facltBizcoId}"/>');">등록</button>
                            </c:otherwise>
                        </c:choose>
                        <button class="btn btn-gray" type="button" onclick="list();">업체목록</button>
                    </div>
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

            function list(){
                window.history.back();
            }
        </script>

    </div>

</div>
<form:form id="form_modify" action="/cm/system/facility/detailEdit" name="facility" commandName="facility" method="post">
      <input type="text" id="mod_houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="mod_facltBizcoId" name="facltBizcoId" value="<c:out value="${facltBizcoId}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCdGoList" name="houscplxCdGoList" value="<c:out value="${houscplxCdGoList}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCdGoListNm" name="houscplxCdGoListNm" value="<c:out value="${houscplxCdGoListNm}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>
