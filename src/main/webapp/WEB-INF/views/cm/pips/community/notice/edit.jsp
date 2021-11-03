<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">


<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/editor.css" />">
<script type="text/javascript">
    $(document).ready(function(){
        $('#cont').summernote({
            height: 300,
            popover: {
            image: [],
            link: [],
            air: []
            }
        });
    });
    function back(){
        window.history.back();
    }
    function update_btn(){
        var RegExp = /[\{\}\[\]\/;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
        var title = $("#title").val();
        if($("#title").val() == ""){
            alert("제목을 입력해주세요.");
            return;
        }
        if($("#cont").val() == ""){
            alert("내용을 입력해주세요.");
            return;
        }
        if(RegExp.test(title) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        title = title.replace(/</g,"&lt;");
        title = title.replace(/>/g,"&gt;");
        var str = $("#hmnetNotiCont").val();

        var newText = str.replace(/(<([^>]+)>)/ig,"");
        $("#title_").val(title);
        $("#cont_").val($("#cont").val());
        $("#hmnetCont").val(newText);
        $("#form_noti_edit").submit();
    }
    function filedel(){
        $("#isAttachFileRemove").val("Y");
        $("#isAttachFileChange").val("Y");
        var element = document.getElementById("file_del_btn");
        $(".custom-file-label").html("");
        element.style.display = "none";
    }
</script>
<form:form action="/cm/community/editNoticeAction" id="form_noti_edit" name="housingCplx" commandName="housingCplx" method="post" enctype="multipart/form-data">
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">단지 공지사항 수정</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>단지 공지사항 관리</li>
                <li>단지 공지사항 목록</li>
                <li>단지 공지사항 상세</li>
                <li>단지 공지사항 수정</li>
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
                        <td colspan="3"><fmt:formatDate value="${noticeDetail.crDt}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <th>제목</th>
                        <td colspan="3"><input type="text" class="form-control" id="title" value="<c:out value="${noticeDetail.title}"/>"/></td>
                    </tr>
                    <tr>
                        <th>상태</th>
                        <td colspan="3"><c:out value="${noticeDetail.tlrncYnNm}"/></td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td colspan="3">
                            <textarea id="cont">${noticeDetail.cont}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>홈넷공지내용</th>
                        <td colspan="3">
                            <c:choose>
                                <c:when test="${noticeDetail.hmnetNotiCont eq ''}">
                                    <textarea style="width:100%;height:150px;" id="hmnetNotiCont"></textarea>
                                </c:when>
                                <c:otherwise>
                                    <textarea style="width:100%;height:150px;" id="cont"><c:out value="${noticeDetail.hmnetNotiCont}"/></textarea>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test="${empty noticeFileList}">
                            <tr>
                                <th>첨부파일</th>
                                <td colspan="3">
                                    <div class="custom-file">
                                        <input type="file" class="custom-file-input" id="inputGroupFile01" name="inputGroupFile01" aria-describedby="inputGroupFileAddon01" value="<c:out value="${community.file}"/>" accept=".png, .jpg, .jpeg, .gif, .doc, .docx, .xls, .xlsx, .hwp, .ppt, .pptx, .txt"/>
                                        <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                    </div>
                                    <script type="text/javascript">
                                        $('#inputGroupFile01').on('change',function(){
                                            var fileName = $(this).val();
                                            $(this).next('.custom-file-label').html(fileName);
                                            $("#isAttachFileChange").val("Y");
                                        })
                                    </script>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${noticeFileList}" var="list" varStatus="status">
                                <tr>
                                    <th>첨부파일</th>
                                    <td colspan="3">
                                        <div class="custom-file">
                                            <input type="file" class="custom-file-input" id="inputGroupFile01" name="inputGroupFile01" aria-describedby="inputGroupFileAddon01" value="<c:out value="${community.file}"/>" accept=".png, .jpg, .jpeg, .gif, .doc, .docx, .xls, .xlsx, .hwp, .ppt, .pptx, .txt"/>
                                            <label class="custom-file-label" for="inputGroupFile01"><c:out value="${list.orgnlFileNm}"/></label>
                                        </div>
                                        <script type="text/javascript">
                                            $('#inputGroupFile01').on('change',function(){
                                                var fileName = $(this).val();
                                                $(this).next('.custom-file-label').html(fileName);
                                                $("#isAttachFileChange").val("Y");
                                            })
                                        </script>
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
                <button class="btn btn-brown" type="button" onclick="back();">취소</button>
                <button class="btn btn-brown" type="button" onclick="update_btn();">저장</button>
            </div>
            <div class="left_area">
                <button class="btn btn-brown" type="button" onclick="filedel();" id="file_del_btn">첨부파일 삭제</button>
            </div>
        </div>
    </div>
</div>
<input type="text" id="title_" name="title" style="display:none;"/>
<input type="text" id="cont_" name="cont" style="display:none;"/>
<input type="text" id="hmnetCont" name="hmnetNotiCont" style="display:none;"/>
<input type="text" id="isAttachFileChange" name="isAttachFileChange" value="N" style="display:none;"/>
<input type="text" id="isAttachFileRemove" name="isAttachFileRemove" value="N" style="display:none;"/>
<input type="text" id="blltNo" name="blltNo" value="<c:out value="${noticeDetail.blltNo}"/>" style="display:none;"/>
<input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${noticeDetail.houscplxCd}"/>" style="display:none;"/>
</form:form>