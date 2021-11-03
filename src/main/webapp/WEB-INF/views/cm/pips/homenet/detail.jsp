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
    function back(){
        window.history.back();
    }
    function modify(id){
        $("#homenetId").val(id);
        $("#form_edit").submit();
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">홈넷서버 상세</h2>
            <ul class="location">
                <li>홈넷서버 관리</li>
                <li>홈넷서버 관리</li>
                <li>홈넷서버 목록</li>
                <li>홈넷서버 상세</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>종류</th>
                        <c:choose>
                        <c:when test="${homenetDetail.svrTpCd eq 'UNIFY_SVR'}">
                            <td>통합서버</td>
                        </c:when>
                        <c:when test="${homenetDetail.svrTpCd eq 'HOUSCPLX_SVR'}">
                            <td>단지서버</td>
                        </c:when>
                        </c:choose>
                        <th>홈넷서버이름</th>
                        <td><c:out value="${homenetDetail.hmnetNm}"/></td>
                    </tr>
                    <tr>
                        <th>인증 Key</th>
                        <td><c:out value="${homenetDetail.hmnetKeyCd}"/></td>
                        <th>홈넷사</th>
                        <c:choose>
                        <c:when test="${homenetDetail.bizcoCd eq 'COMAX'}">
                        <td>코맥스</td>
                        </c:when>
                        <c:when test="${homenetDetail.bizcoCd eq 'KOCOM'}">
                        <td>코콤</td>
                        </c:when>
                        <c:when test="${homenetDetail.bizcoCd eq 'HYUNDAITEL'}">
                        <td>현대통신</td>
                        </c:when>
                        <c:when test="${homenetDetail.bizcoCd eq 'ICONTROLS'}">
                        <td>HDC 아이콘트롤스</td>
                        </c:when>
                        <c:when test="${homenetDetail.bizcoCd eq 'OTHER'}">
                        <td>기타</td>
                        </c:when>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>홈넷서버 ID</th>
                        <td><c:out value="${homenetDetail.hmnetId}"/></td>
                        <th>사용여부</th>
                        <c:choose>
                        <c:when test="${homenetDetail.useYn eq 'Y'}">
                        <td>사용</td>
                        </c:when>
                        <c:when test="${homenetDetail.useYn eq 'N'}">
                        <td>정지</td>
                        </c:when>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>연동상태</th>
                        <td><c:out value="${homenetDetail.stsCd}"/></td>
                        <th>Keep alive<br />주기 (초)</th>
                        <td><c:out value="${homenetDetail.keepAliveCycleCont}"/></td>
                    </tr>
                    <tr>
                        <th>장치상태<br />전송주기 (초)</th>
                        <td><c:out value="${homenetDetail.datTrnsmCycleCont}"/></td>
                        <th>제어타임아웃<br />전송주기 (초)</th>
                        <td><c:out value="${homenetDetail.ctlExprtnCycleCont}"/></td>
                    </tr>
                    <tr>
                        <th>도메인</th>
                        <td colspan="3"><c:out value="${homenetDetail.urlCont}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
                <button class="btn btn-bluegreen" type="button" onclick="modify('<c:out value="${homenetDetail.hmnetId}"/>')">수정</button>
            </div>
        </div>
    </div>
</div>
<form:form id="form_edit" action="/cm/homenet/info/edit" name="edit" commandName="edit" method="post">
      <input type="text" id="homenetId" name="homenetId" style="width:0;height:0;visibility:hidden"/>
</form:form>