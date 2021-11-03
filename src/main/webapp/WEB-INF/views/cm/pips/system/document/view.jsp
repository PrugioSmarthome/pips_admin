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

    function list() {
        location.href = "/cm/system/document/list";
    }

    function edit() {
        $("#form_edit").submit();
    }

    function del(){
        var conf = confirm("해당 정보를 삭제 하시겠습니까?");
        if(conf == true){
            $("#form_del").submit();
        }
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">문서 상세</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>문서 관리</li>
                <li>문서 상세</li>
            </ul>
        </div>
        <div class="tbl_btm_area2">
            <div class="left_area">
                <button class="btn btn-gray" onclick="del();">삭제</button>
            </div>
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
                        <th>구분</th>
                        <td><c:out value="${documentDetail.lnkAtchFileGrpTpCdNm}"/></td>
                        <th>서비스명</th>
                        <td><c:out value="${documentDetail.lnkAtchFileTpCdNm}"/></td>
                    </tr>
                    <tr>
                        <th>노출여부</th>
                        <c:if test="${documentDetail.useYn eq 'Y'}">
                            <td colspan="3">YES</td>
                        </c:if>
                        <c:if test="${documentDetail.useYn eq 'N'}">
                            <td colspan="3">NO</td>
                        </c:if>
                    </tr>
                    <tr>
                        <th>대상</th>
                        <c:choose>
                            <c:when test="${not empty documentDetail.houscplxCd}">
                                <td colspan="3"><c:out value="${documentDetail.houscplxNm}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td colspan="3">전체</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>파일정보</th>
                        <td colspan="3">
                            <c:choose>
                                <c:when test="${empty documentDetail}">
                                    <div class="clearfix">
                                        <div class="float-left align_middle">파일없음</div>
                                        <div class="float-right"><button class="btn btn-gray btn-sm">다운로드</button></div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="clearfix">
                                        <div class="float-left align_middle"><c:out value="${documentDetail.orgnlFileNm}"/></div>
                                        <div class="float-right"><button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${documentDetail.orgnlFileNm}"/>','<c:out value="${documentDetail.fileUrl}"/>')">다운로드</button></div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-brown" onclick="edit();">수정</button>
                <button class="btn btn-brown" onclick="list();">목록</button>
            </div>
        </div>

    </div>

</div>

<form:form id="form_edit" action="/cm/system/document/edit" name="detail" commandName="detail" method="post">
      <input type="text" id="fileId" name="lnkAtchFileId" value="<c:out value="${documentDetail.lnkAtchFileId}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_del" action="/cm/system/document/deleteDocumentAction" name="detail" commandName="detail" method="post">
      <input type="text" id="fileId" name="lnkAtchFileId" value="<c:out value="${documentDetail.lnkAtchFileId}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>