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
    //목록버튼
    function back(){
        //window.history.back();
        location.href = "/cm/community/question/list";
    }


    //설문조사 삭제버튼
    function del_(e){
        var conf = confirm("해당 설문조사를 삭제 하시겠습니까?");
        if(conf == true){
            $("#qstNo").val(e);
            $("#form_del").submit();
        }
    }

    //수정버튼
    function qedit(e,h){
        $("#qstNo_").val(e);
        $("#houscplxCd").val(h);
        $("#form_edit").submit();
    }

    //세대별투표내역 버튼
    function on_modal(e){
        //Ajax 들어갈 부분
        var param = new Object();
        param.qstNo = e;

        $.ajax({
                url: '/cm/community/question/completion/modal',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    $("#body_list").empty();
                    $.each(data, function(i, item){
                        var cJson = new Object();
                        var donghoseArray = item.hsholdId.split(".");
                        var ansr;
                        if(item.qstEtcAnsr == "" || item.qstEtcAnsr == undefined){
                            ansr = "-";
                        }else{
                            ansr = item.qstEtcAnsr;
                        }
                        $("#body_list").append("<tr>"+
                                                    "<td class='text-center'>"+donghoseArray[0]+"동"+donghoseArray[1]+"호</td>"+
                                                    "<td class='text-center'>"+item.qstItmNo+"</td>"+
                                                    "<td class='text-center'>"+ansr+"</td>"+
                                                "</tr>");

                    });
                },
                error: function(e){
                    console.log("에러");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
        $("#modal1").modal();
    }

    //엑셀 다운로드 버튼
    function excel(e){
        var list = new Array();

        var param = new Object();
        param.qstNo = e;
        $.ajax({
                url: '/cm/community/question/completion/modal',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    $.each(data, function(i, item){
                        var cJson = new Object();
                        var donghoseArray = item.hsholdId.split(".");
                        var ansr;
                        if(item.qstEtcAnsr == "" || item.qstEtcAnsr == undefined){
                            ansr = "-";
                        }else{
                            ansr = item.qstEtcAnsr;
                        }
                        cJson.세대 = donghoseArray[0];
                        cJson.선택번호 = item.qstItmNo;
                        cJson.기타의견 = ansr;
                        list.push(cJson);
                    });
                },
                error: function(e){
                    console.log("에러");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });


        var hJson = JSON.stringify(list);

        console.log(hJson)
    }

    //강제설문종료 버튼
    function end_btn(e){
        var conf = confirm("해당 설문조사를  강제 종료 하시겠습니까?");
        if(conf == true){
            $("#_qstNo_").val(e);
            $("#qstStsCd").val("4");
            $("#form_open").submit();
        }
    }

    //설문게시 버튼
    function qopen(e){
        var conf = confirm("해당 설문을 게시 하시겠습니까?");
        if(conf == true){
            $("#_qstNo_").val(e);
            $("#qstStsCd").val("2");
            $("#form_open").submit();
        }
    }
</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">설문조사 상세</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>설문조사 관리</li>
                <li>설문조사 목록</li>
                <li>설문조사 상세</li>
            </ul>
        </div>
        <c:if test="${questionDetail.qstStsCdNm == '진행 전'}">
            <div class="tbl_btm_area2">
                <div class="left_area">
                    <button class="btn btn-brown" type="button" onclick="del_('<c:out value="${questionDetail.qstNo}"/>');">삭제</button>
                </div>
            </div>
        </c:if>
        <c:if test="${questionDetail.qstStsCdNm == '진행 중'}">
            <div class="tbl_btm_area2">
                <div class="left_area">
                    <button class="btn btn-brown" type="button" onclick="end_btn('<c:out value="${questionDetail.qstNo}"/>');">강제설문종료</button>
                </div>
            </div>
        </c:if>

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
                        <td colspan="3"><fmt:formatDate value="${questionDetail.crDt}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                        <tr>
                            <th>단지명</th>
                            <td colspan="3"><c:out value="${questionDetail.houscplxNm}"/></td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>제목</th>
                        <td colspan="3"><c:out value="${questionDetail.qstTitle}"/></td>
                    </tr>
                    <tr>
                        <th>참여기간</th>
                        <td><c:out value="${questionDetail.qstStDt}"/> ~ <c:out value="${questionDetail.qstEdDt}"/></td>
                        <th>상태</th>
                        <c:choose>
                            <c:when test="${questionDetail.qstStsCdNm == '진행 전'}">
                                <td>진행전</td>
                            </c:when>
                            <c:otherwise>
                                <td><c:out value="${questionDetail.qstStsCdNm}"/>(<c:out value="${questionDetail.totalMemCnt}"/>명중 <c:out value="${questionDetail.votedMemCnt}"/>명 참여)</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <th>설문유형</th>
                        <td colspan="3"><c:out value="${questionDetail.qstTpCdNm}"/></td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td colspan="3">
                            <div class="color_brown"><c:out value="${questionDetail.qstAnsrTitle}"/></div>
                            <c:choose>
                                <c:when test="${questionDetail.qstStsCdNm == '진행 전'}">
                                    <c:forEach items="${questionItemList}" var="list" varStatus="status">
                                        <c:set var="percent" value="${list.qstItmAnsrCnt/questionDetail.totalMemCnt*100}"/>
                                        <div class="mt10">${status.count}. <c:out value="${list.qstItmAnsrCont}"/></div>
                                    </c:forEach>
                                    <c:if test="${questionDetail.qstTpCd == '2' || questionDetail.qstTpCd == '4' || questionDetail.qstTpCd == '6'}">
                                        <div class="txt_overflow_box mt10 h150" style="overflow:auto;"></div>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="percent" value="0"/>
                                    <c:forEach items="${questionItemList}" var="list" varStatus="status">
                                        <c:set var="percent" value="${list.qstItmAnsrCnt/questionDetail.totalMemCnt*100}"/>
                                        <div class="mt10">${status.count}. <c:out value="${list.qstItmAnsrCont}"/> (<c:out value="${list.qstItmAnsrCnt}"/>명 선택 | <c:out value="${percent}"/>%)</div>
                                    </c:forEach>
                                    <c:if test="${questionDetail.qstTpCd == '2' || questionDetail.qstTpCd == '4' || questionDetail.qstTpCd == '6'}">
                                        <div class="txt_overflow_box mt10 h150" style="overflow:auto;">
                                            <c:forEach items="${questionEtcAnswerList}" var="list" varStatus="status">
                                                ${status.count}. <c:out value="${list.qstEtcAnsr}"/></br>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <c:if test="${questionDetail.qstStsCd != '1'}">
                        <button class="btn btn-bluegreen" type="button" onclick="on_modal('<c:out value="${questionDetail.qstNo}"/>');">세대별 투표내역</button>
                    </c:if>
                </c:if>
                <c:if test="${userGroupName eq 'COMPLEX_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                    <c:if test="${questionDetail.qstStsCd == '1'}">
                        <button class="btn btn-bluegreen" type="button" onclick="qopen('<c:out value="${questionDetail.qstNo}"/>');">설문게시</button>
                        <button class="btn btn-bluegreen" type="button" onclick="qedit('<c:out value="${questionDetail.qstNo}"/>','<c:out value="${questionDetail.houscplxCd}"/>');">수정</button>
                    </c:if>
                </c:if>
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
            </div>
        </div>

    </div>

</div>

<form:form id="form_del" action="/cm/community/deleteQuestionAction" name="del" commandName="del" method="post">
    <input type="text" id="qstNo" name="qstNo" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_edit" action="/cm/community/question/edit" name="edit" commandName="edit" method="post">
    <input type="text" id="qstNo_" name="qstNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_open" action="/cm/community/editQuestionStsCdAction" name="edit" commandName="edit" method="post">
    <input type="text" id="_qstNo_" name="qstNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstStsCd" name="qstStsCd" style="width:0;height:0;visibility:hidden"/>
</form:form>


<!-- 세대별 투표내역 모달 -->
<div class="modal " id="modal1" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered " role="document">
		<div class="modal-content">
			<!-- 모달헤더 -->
			<div class="modal-header">
				<h5 class="modal-title">세대별 투표내역</h5>
				<button type="button" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
			</div>
			<!-- //모달헤더 -->

			<!-- 모달바디 -->
			<div class="modal-body">


				<!-- 테이블영역 -->
				<div class="table_wrap mt-4">
					<table class="table" id="table4">
						<thead>
							<tr>
								<th scope="col">세대</th>
								<th scope="col">선택번호</th>
								<th scope="col">기타의견</th>
							</tr>
						</thead>
						<tbody id="body_list">

                        </tbody>
					</table>

				</div>

				<!-- //테이블영역 -->

			</div>
			<!-- //모달바디 -->
		</div>
	</div>
</div>