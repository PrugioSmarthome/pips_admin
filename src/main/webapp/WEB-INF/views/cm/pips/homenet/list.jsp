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

        var svrTpCd = "<c:out value="${svrTpCd}"/>";
        var bizcoCd = "<c:out value="${bizcoCd}"/>";
        var stsCd = "<c:out value="${stsCd}"/>";
        $("#SvrCd").val(svrTpCd);
        $("#hmnet_name").val(bizcoCd);
        $("#StsCd").val(stsCd);
    });
    //리스트 클릭 상세뷰
    function detailview(id){
        $("#homenetId").val(id);
        $("#form_detail").submit();
    }
    //검색 버튼
    function btn_search(){
        $("#svrTpCd").val($("#SvrCd").val());
        $("#bizcoCd").val($("#hmnet_name").val());
        $("#stsCd").val($("#StsCd").val());

        $("#form_search").submit();
    }
    //엑셀 다운로드
    function excel(){
        var list = new Array();

        <c:forEach items="${homenetList}" var="list" varStatus="status">
            var cJson = new Object();

            <c:choose>
                <c:when test="${list.svrTpCd eq 'UNIFY_SVR'}">
                    cJson.종류 = "통합서버";
                </c:when>
                <c:when test="${list.svrTpCd eq 'HOUSCPLX_SVR'}">
                    cJson.종류 = "단지서버";
                </c:when>
            </c:choose>
            cJson.홈넷서버이름 = "<c:out value="${list.hmnetNm}"/>";
            cJson.인증Key = "<c:out value="${list.hmnetKeyCd}"/>";

            <c:choose>
                <c:when test="${list.bizcoCd eq 'COMAX'}">
                    cJson.홈넷사 = "코맥스";
                </c:when>
                <c:when test="${list.bizcoCd eq 'KOCOM'}">
                    cJson.홈넷사 = "코콤";
                </c:when>
                <c:when test="${list.bizcoCd eq 'HYUNDAITEL'}">
                    cJson.홈넷사 = "현대통신";
                </c:when>
                <c:when test="${list.bizcoCd eq 'ICONTROLS'}">
                    cJson.홈넷사 = "HDC아이콘트롤스";
                </c:when>
                <c:when test="${list.bizcoCd eq 'OTHER'}">
                    cJson.홈넷사 = "기타";
                </c:when>
            </c:choose>
            cJson.도메인 = "<c:out value="${list.urlCont}"/>";
            <c:choose>
                <c:when test="${list.useYn eq 'Y'}">
                    cJson.사용여부 = "사용";
                </c:when>
                <c:when test="${list.useYn eq 'N'}">
                    cJson.사용여부 = "정지";
                </c:when>
            </c:choose>
            cJson.연동상태 = "<c:out value="${list.stsCd}"/>";

            list.push(cJson);
        </c:forEach>
        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "홈넷서버 목록";
        param.tableHeader = "['종류', '홈넷서버이름', '인증Key', '홈넷사', '도메인', '사용여부', '연동상태']";
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
                link.download= today+"_홈넷서버목록.xlsx";
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

    function add(){
        location.href = "/cm/homenet/info/add";
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">홈넷서버 목록</h2>
            <ul class="location">
                <li>홈넷서버 관리</li>
                <li>홈넷서버 정보 관리</li>
                <li>홈넷서버 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 종류</th>
                        <td>
                            <select name="SvrCd" id="SvrCd" class="custom-select">
                                <option value="all">전체</option>
                                <option value="UNIFY_SVR">통합서버</option>
                                <option value="HOUSCPLX_SVR">단지서버</option>
                            </select>
                        </td>
                        <th>· 홈넷사</th>
                        <td>
                            <select name="hmnet_name" id="hmnet_name" class="custom-select">
                                <option value="all">전체</option>
                                <c:forEach items="${bizcocdList}" var="list" varStatus="status">
                                    <option value="<c:out value="${list.commCd}"/>"><c:out value="${list.commCdNm}"/></option>
                                </c:forEach>

                            </select>
                        </td>
                        <th>· 연동상태</th>
                        <td>
                            <select name="StsCd" id="StsCd" class="custom-select">
                                <option value="all">전체</option>
                                <option value="OK">사용</option>
                                <option value="NOK">정지</option>
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
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <colgroup>
                    <col style="width:8%"/>
                    <col />
                    <col />
                    <col />
                    <col style="width:8%"/>
                    <col />
                    <col style="width:8%"/>
                    <col style="width:8%"/>
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">종류</th>
                        <th scope="col">홈넷서버 ID</th>
                        <th scope="col">홈넷서버 이름</th>
                        <th scope="col">인증 Key</th>
                        <th scope="col">홈넷사</th>
                        <th scope="col">도메인</th>
                        <th scope="col">사용여부</th>
                        <th scope="col">연동상태</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${homenetList}" var="list" varStatus="status">
                        <tr>
                        <c:choose>
                            <c:when test="${list.svrTpCd eq 'UNIFY_SVR'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">통합서버</a></td>
                            </c:when>
                            <c:when test="${list.svrTpCd eq 'HOUSCPLX_SVR'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">단지서버</a></td>
                            </c:when>
                        </c:choose>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');"><c:out value="${list.hmnetId}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');"><c:out value="${list.hmnetNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');"><c:out value="${list.hmnetKeyCd}"/></a></td>
                        <c:choose>
                            <c:when test="${list.bizcoCd eq 'COMAX'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">코맥스</a></td>
                            </c:when>
                            <c:when test="${list.bizcoCd eq 'KOCOM'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">코콤</a></td>
                            </c:when>
                            <c:when test="${list.bizcoCd eq 'HYUNDAITEL'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">현대통신</a></td>
                            </c:when>
                            <c:when test="${list.bizcoCd eq 'ICONTROLS'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">HDC 아이콘트롤스</a></td>
                            </c:when>
                            <c:when test="${list.bizcoCd eq 'OTHER'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">기타</a></td>
                            </c:when>
                        </c:choose>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');"><c:out value="${list.urlCont}"/></a></td>
                        <c:choose>
                            <c:when test="${list.useYn eq 'Y'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">사용</a></td>
                            </c:when>
                            <c:when test="${list.useYn eq 'N'}">
                                <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">정지</a></td>
                            </c:when>
                        </c:choose>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.hmnetId}"/>');">${list.stsCd}</a></td>
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

            });
        </script>
    </div>
</div>
<form:form id="form_detail" action="/cm/homenet/info/view" name="detail" commandName="detail" method="post">
      <input type="text" id="homenetId" name="homenetId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/homenet/info/list" name="list" commandName="list" method="post">
      <input type="text" id="svrTpCd" name="svrTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="bizcoCd" name="bizcoCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="stsCd" name="stsCd" style="width:0;height:0;visibility:hidden"/>
</form:form>