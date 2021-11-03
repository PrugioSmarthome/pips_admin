<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });

    function check(e){
        if($(e).is(":checked") == true){
            $("#text").attr('disabled', true);
            $("#text").val("");
            var element = document.getElementById("answer_tr");
            element.style.display = "none";
        }else{
            $("#text").attr('disabled', false);
            var element = document.getElementById("answer_tr");
            element.style.display = "";
        }
    }

    function back(){
        location.href = "/cm/community/report/list";
    }

    function save_btn(name){
        var RegExp = /[\{\}\[\]\/;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
        if($("#check1").is(":checked") == false && $("#text").val() == ""){
            alert("처리중 체크 또는 답변을 작성해 주세요.");
            return;
        }
        if(RegExp.test($("#text").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if($("#check1").is(":checked") == true){
            $("#blltStsCd").val("PROCESSING");
        }else{
            $("#blltStsCd").val("COMPLETE");
        }

        var conf = confirm("작성한 내용으로 답변을 등록 하시겠습니까?");
        if(conf == true){
            $("#reportUserId").val(name);
            $("#comment").val($("#text").val());

            $("#form_list").submit();
        }
    }

</script>
<form:form id="form_list" action="/cm/community/editReportCommentAction" name="community" commandName="community" method="post" enctype="multipart/form-data">
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">생활불편신고 상세</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>생활불편신고 관리</li>
                <li>생활불편신고 목록</li>
                <li>생활불편신고 상세</li>
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
                        <th>등록일</th>
                        <td colspan="3"><fmt:formatDate value="${reportDetail.crDt}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                        <c:choose>
                            <c:when test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                                <th>단지명</th>
                                <td><c:out value="${reportDetail.houscplxNm}"/></td>
                                <th>세대</th>
                                <td><c:out value="${reportDetail.reportDongNo}"/>동 <c:out value="${reportDetail.reportHoseNo}"/>호</td>
                            </c:when>
                            <c:otherwise>
                                <th>세대</th>
                                <td colspan="3"><c:out value="${reportDetail.reportDongNo}"/>동 <c:out value="${reportDetail.reportHoseNo}"/>호</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>유형</th>
                        <td colspan="3"><c:out value="${reportDetail.blltTpDtlCdNm}"/></td>
                    </tr>
                    <tr>
                        <th>제목</th>
                        <td colspan="3"><c:out value="${reportDetail.title}"/></td>
                    </tr>
                    <tr>
                        <th>상태</th>
                        <td><c:out value="${reportDetail.blltStsCdNm}"/></td>
                        <th>작성자</th>
                        <td><c:out value="${reportDetail.reportUserNm}"/></td>
                    </tr>
                    <tr>
                        <c:set value="${fn:substring(reportDetail.reportMphoneNo, 0, 3)}" var="tel1"/>
                        <c:set value="${fn:substring(reportDetail.reportMphoneNo, 3, 7)}" var="tel2"/>
                        <c:set value="${fn:substring(reportDetail.reportMphoneNo, 7, 11)}" var="tel3"/>
                        <th>연락처</th>
                        <td><c:out value="${tel1}"/>-<c:out value="${tel2}"/>-<c:out value="${tel3}"/></td>
                        <th>SMS 전송 여부</th>
                        <c:choose>
                            <c:when test="${reportDetail.smsYn eq 'Y'}">
                                <td>YES</td>
                            </c:when>
                            <c:otherwise>
                                <td>NO</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td colspan="3">
                            <c:out value="${reportDetail.cont}"/>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${empty reportDetailFile}">
                            <tr>
                                <th>신고자 첨부파일</th>
                                <td colspan="3">
                                    파일없음
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${reportDetailFile}" var="list" varStatus="status">
                                <tr>
                                    <th>신고자 첨부파일</th>
                                    <td colspan="3">
                                        <div class="clearfix">
                                            <div class="float-left align_middle"><c:out value="${list.orgnlFileNm}"/></div>
                                            <div class="float-right"><button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${list.orgnlFileNm}"/>','<c:out value="${list.fileUrl}"/>')">다운로드</button></div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                        <c:if test="${reportDetail.blltStsCd eq 'COMPLETE'}">
                            <tr>
                                <th>답변</th>
                                <td colspan="3"><c:out value="${reportDetail.reportAnsr}"/></td>
                            </tr>
                            <tr>
                                <th>답변관련 첨부파일</th>
                                <td colspan="3">
                                <c:choose>
                                    <c:when test="${empty reportDetailAnswerFile}">
                                        파일없음
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${reportDetailAnswerFile}" var="list" varStatus="status">
                                            <div class="clearfix">
                                                <div class="float-left align_middle"><c:out value="${list.orgnlFileNm}"/></div>
                                                <div class="float-right"><button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${list.orgnlFileNm}"/>','<c:out value="${list.fileUrl}"/>')">다운로드</button></div>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>

                    <c:if test="${userGroupName eq 'COMPLEX_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                        <c:choose>
                            <c:when test="${reportDetail.blltStsCd eq 'COMPLETE'}">
                                <tr>
                                    <th>답변</th>
                                    <td colspan="3"><c:out value="${reportDetail.reportAnsr}"/></td>
                                </tr>
                                <tr>
                                    <th>답변관련 첨부파일</th>
                                    <td colspan="3">
                                    <c:choose>
                                        <c:when test="${empty reportDetailAnswerFile}">
                                            파일없음
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${reportDetailAnswerFile}" var="list" varStatus="status">
                                                <div class="clearfix">
                                                    <div class="float-left align_middle"><c:out value="${list.orgnlFileNm}"/></div>
                                                    <div class="float-right"><button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${list.orgnlFileNm}"/>','<c:out value="${list.fileUrl}"/>')">다운로드</button></div>
                                                </div>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <th>답변</th>
                                    <c:if test="${reportDetail.blltStsCd eq 'PROCESSING'}">
                                        <td colspan="3">
                                            <div class="custom-control custom-checkbox d-inline-block mr-3">
                                                <input type="checkbox" onclick="check(this);" class="custom-control-input" id="check1" checked />
                                                <label class="custom-control-label" for="check1">처리중으로만 표시하고 나중에 답변  (체크 시 신고주민 화면에  ‘처리중’ 으로 표시 됨니다)</label>
                                            </div>
                                            <textarea id="text" style="width:100%;height:100px;" disabled="disabled"></textarea>
                                        </td>
                                    </c:if>
                                    <c:if test="${reportDetail.blltStsCd eq 'REGISTER'}">
                                        <td colspan="3">
                                            <div class="custom-control custom-checkbox d-inline-block mr-3">
                                                <input type="checkbox" onclick="check(this);" class="custom-control-input" id="check1">
                                                <label class="custom-control-label" for="check1">처리중으로만 표시하고 나중에 답변  (체크 시 신고주민 화면에  ‘처리중’ 으로 표시 됨니다)</label>
                                            </div>
                                            <textarea id="text" style="width:100%;height:100px;"></textarea>
                                        </td>
                                    </c:if>
                                </tr>
                                <c:if test="${reportDetail.blltStsCd eq 'PROCESSING'}">
                                    <tr id="answer_tr" style="display:none">
                                        <th>답변관련 첨부파일</th>
                                        <td colspan="3">
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" name="inputGroupFile01" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01" accept=".png, .jpg, .jpeg, .gif, .doc, .docx, .xls, .xlsx, .hwp, .ppt, .pptx, .txt, .pdf">
                                                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                            </div>
                                            <script type="text/javascript">
                                                $('#inputGroupFile01').on('change',function(){
                                                    var fileName = $(this).val();
                                                    $(this).next('.custom-file-label').html(fileName);
                                                    $("#isAttachFile").val("Y");
                                                })
                                            </script>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${reportDetail.blltStsCd eq 'REGISTER'}">
                                    <tr id="answer_tr">
                                        <th>답변관련 첨부파일</th>
                                        <td colspan="3">
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" name="inputGroupFile01" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01" accept=".png, .jpg, .jpeg, .gif, .doc, .docx, .xls, .xlsx, .hwp, .ppt, .pptx, .txt">
                                                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                            </div>
                                            <script type="text/javascript">
                                                $('#inputGroupFile01').on('change',function(){
                                                    var fileName = $(this).val();
                                                    $(this).next('.custom-file-label').html(fileName);
                                                    $("#isAttachFile").val("Y");
                                                })
                                            </script>
                                        </td>
                                    </tr>
                                </c:if>

                            </c:otherwise>
                        </c:choose>
                    </c:if>

                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:if test="${userGroupName eq 'COMPLEX_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                    <c:if test="${reportDetail.blltStsCd != 'COMPLETE'}">
                        <button class="btn btn-brown" type="button" onclick="save_btn('<c:out value="${reportDetail.reportUserId}"/>');">저장</button>
                    </c:if>
                </c:if>
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
            </div>
        </div>
    </div>
</div>
<input type="text" id="reportUserId" name="reportUserId" style="display:none;"/>
<input type="text" id="isProcessing" name="isProcessing" value="Y" style="display:none;"/>
<input type="text" id="comment" name="comment" style="display:none;"/>
<input type="text" id="isAttachFile" name="isAttachFile" style="display:none;"/>
<input type="text" id="blltStsCd" name="blltStsCd" style="display:none;"/>
<input type="text" id="blltNo" name="blltNo" value="<c:out value="${reportDetail.blltNo}"/>" style="display:none;"/>
<input type="text" id="blltGrpNo" name="blltGrpNo" value="<c:out value="${reportDetail.blltNo}"/>" style="display:none;"/>
<input type="text" id="blltTpDtlCd" name="blltTpDtlCd" value="<c:out value="${reportDetail.blltTpDtlCd}"/>" style="display:none;"/>
<input type="text" id="hsholdIdRedirect" name="hsholdIdRedirect" value="<c:out value="${hsholdId}"/>" style="display:none;"/>
</form:form>