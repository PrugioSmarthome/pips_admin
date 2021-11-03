<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });
    function back(){
        window.history.back();
    }
    function edit_page(e,h){
        $("#blltNo").val(e);
        $("#houscplxCd").val(h);
        $("#form_edit").submit();

    }
    function onnoti(e, multiHouscplxCd){
        var conf = confirm("해당 공지사항을 게시 하시겠습니까?");
        if(conf == true){
            $("#bllt").val(e);
            $("#tlrncYn_").val("Y");
            $("#multiHouscplxCd").val(multiHouscplxCd);
            $("#form_Yn").submit();
        }
    }
    function offnoti(e, multiHouscplxCd){
        var conf = confirm("해당 공지사항 게시를 취소 하시겠습니까?");
        if(conf == true){
            $("#bllt").val(e);
            $("#tlrncYn_").val("N");
            $("#multiHouscplxCd").val(multiHouscplxCd);
            $("#form_Yn").submit();
        }
    }
    function notidelete(e){
        var conf = confirm("해당 공지사항을 삭제 하시겠습니까?");
        if(conf == true){
            $("#blltNo_").val(e);
            $("#form_delete").submit();
        }
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">단지 공지사항 상세</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>단지 공지사항 관리</li>
                <li>단지 공지사항 목록</li>
                <li>단지 공지사항 상세</li>
            </ul>
        </div>
        <div class="table_wrap2">
            <div class="tbl_top_area" style="margin-bottom:10px;">
                <div class="left_area">
                    <button class="btn btn-gray" type="button" onclick="notidelete('<c:out value="${noticeDetail.blltNo}"/>');">삭제</button>
                </div>
            </div>
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>등록일</th>
                        <td colspan="3"><fmt:formatDate value="${noticeDetail.crDt}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <tr>
                        <th>단지명</th>
                        <td colspan="3"><c:out value="${noticeDetail.houscplxNm}"/></td>
                    </tr>
                    </c:if>
                    <tr>
                        <th>제목</th>
                        <td colspan="3"><c:out value="${noticeDetail.title}"/></td>
                    </tr>
                    <tr>
                        <th>상태</th>
                        <td colspan="3"><c:out value="${noticeDetail.tlrncYnNm}"/></td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td colspan="3">
                            ${noticeDetail.cont}
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${empty noticeFileList}">
                            <tr>
                                <th>첨부파일</th>
                                <td colspan="3">
                                    <div class="clearfix">
                                        <div class="float-left align_middle">파일없음</div>
                                    </div>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${noticeFileList}" var="list" varStatus="status">
                                <tr>
                                    <th>첨부파일</th>
                                    <td colspan="3">
                                        <div class="clearfix">
                                            <div class="float-left align_middle"><c:out value="${list.orgnlFileNm}"/></div>
                                            <div class="float-right">
                                                <button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${list.orgnlFileNm}"/>', '<c:out value="${list.fileUrl}"/>');">다운로드</button>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:choose>
                    <c:when test="${noticeDetail.tlrncYnNm eq '게시완료'}">
                        <button class="btn btn-brown" type="button" onclick="offnoti('<c:out value="${noticeDetail.blltNo}"/>','<c:out value="${noticeDetail.houscplxCd}"/>');">게시취소</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-brown" type="button" onclick="onnoti('<c:out value="${noticeDetail.blltNo}"/>','<c:out value="${noticeDetail.houscplxCd}"/>');">공지게시</button>
                    </c:otherwise>
                </c:choose>
                <button class="btn btn-brown" type="button" onclick="edit_page('<c:out value="${noticeDetail.blltNo}"/>','<c:out value="${noticeDetail.houscplxCd}"/>');">수정</button>
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
            </div>
        </div>
    </div>
</div>


<form:form id="form_edit" action="/cm/community/notice/edit" name="edit" commandName="edit" method="post">
    <input type="text" id="blltNo" name="blltNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_Yn" action="/cm/community/editNoticePublishAction" name="edit" commandName="edit" method="post">
    <input type="text" id="bllt" name="blltNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="tlrncYn_" name="tlrncYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="multiHouscplxCd" name="multiHouscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_delete" action="/cm/community/deleteNoticeAction" name="edit" commandName="edit" method="post">
    <input type="text" id="blltNo_" name="blltNo" style="width:0;height:0;visibility:hidden"/>
</form:form>



<div class="modal" id="modal4" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered size_wide" role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">첨부파일 이미지</h5>
                <button type="button" class="close" data-dismiss="modal"><img src="../images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

            <!-- 모달바디 -->
            <div class="modal-body">
                <div class="text-center"><img class="mw-100 mh-100" src="../images/@img1.png" alt="" /></div>
            </div>
            <!-- //모달바디 -->
        </div>
    </div>
</div>