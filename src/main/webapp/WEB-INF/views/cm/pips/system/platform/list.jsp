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

        var platformTpNm = "<c:out value="${platformTpNm}"/>";
        var platformNm = "<c:out value="${platformNm}"/>";
        var platformCompany = "<c:out value="${platformCompany}"/>";
        $("#platformTpNm_").val(platformTpNm);
        $("#platformNm_").val(platformNm);
        $("#platformCompany_").val(platformCompany);
    });

    //리스트 클릭 상세뷰
    function detailview(id){
        $("#platformId").val(id);
        $("#form_detail").submit();
    }

    //검색 버튼
    function btn_search(){
        $("#platformTpNm").val($("#platformTpNm_").val());
        $("#platformNm").val($("#platformNm_").val());
        $("#platformCompany").val($("#platformCompany_").val());

        $("#form_search").submit();
    }

    function add(){
        location.href = "/cm/system/platform/add";
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">타 플랫폼 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>타 플랫폼 관리</li>
                <li>타 플랫폼 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 종류</th>
                        <td>
                            <input type="text" id="platformTpNm_" class="form-control" />
                        </td>
                        <th>· 플랫폼 이름</th>
                        <td>
                            <input type="text" id="platformNm_" class="form-control" />
                        </td>
                        <th>· 연동사</th>
                        <td>
                            <input type="text" id="platformCompany_" class="form-control" />
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
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                    <col style="width:13%"/>
                    <col style="width:17%"/>
                    <col style="width:10%"/>
                    <col style="width:15%"/>
                    <col style="width:15%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">종류</th>
                        <th scope="col">플랫폼 이름</th>
                        <th scope="col">플랫폼 ID</th>
                        <th scope="col">플랫폼 인증 Key</th>
                        <th scope="col">연동사</th>
                        <th scope="col">접속 URL</th>
                        <th scope="col">Noti URL</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${platformList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformTpNm}</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformNm}</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformId}</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformAuthKey}</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformCompany}</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformUrl}</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.platformId}"/>');">${list.platformNotiUrl}</a></td>
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
<form:form id="form_detail" action="/cm/system/platform/view" name="detail" commandName="detail" method="post">
      <input type="text" id="platformId" name="platformId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/system/platform/list" name="list" commandName="list" method="post">
      <input type="text" id="platformTpNm" name="platformTpNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="platformNm" name="platformNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="platformCompany" name="platformCompany" style="width:0;height:0;visibility:hidden"/>
</form:form>