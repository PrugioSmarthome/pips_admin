<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">

    function back(){
        location.href = "/cm/household/user/list";
    }

    function btn_represent() {
        var representId = '';
        <c:forEach items="${familyList}" var="list" varStatus="status">
            if ('<c:out value="${list.fmlyTpCd}"/>' == 'REPRESENTATIVE') {
                representId = '<c:out value="${list.userId}"/>';
            }
        </c:forEach>

        $.ajax({
            url: '/cm/pips/user/editRepresent',
            type: 'GET',
            data : {
                "beforeUserId" : representId,
                "afterUserId" :$("#selFamily").val()
            },
            success: function(data){


                if (data == '200') {
                    alert("성공했습니다.");
                    window.location.reload();
                } else {
                    alert("실패했습니다.");
                }

                location.reload();
            },
            error: function(e){

                alert("실패했습니다.");
                location.reload();
            }
        });
    }

    function btn_useYn() {
        $.ajax({
            url: '/cm/pips/user/editUseYn',
            type: 'GET',
            data : {
                "userId" : '<c:out value="${userDetail.userId}"/>',
                "useYn" : $("input[name='radio']:checked").val()
            },
            success: function(data){


                if (data == '200') {
                    alert("성공했습니다.");

                } else {
                    alert("실패했습니다.");
                }

                location.reload();
            },
            error: function(e){

                alert("실패했습니다.");
                location.reload();
            }
        });
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">세대정보 수정</h2>
            <ul class="location">
                <li>세대관리</li>
                <li>세대정보 관리</li>
                <li>세대정보 목록</li>
                <li>세대정보 관리 수정</li>
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
                        <th>단지</th>
                        <td><c:out value="${userDetail.houscplxNm}"/></td>
                        <th>세대</th>
                        <td><c:out value="${userDetail.dongNo}"/>동 <c:out value="${userDetail.hoseNo}"/>호</td>
                    </tr>
                    <tr>
                        <th>아이디</th>
                        <td><c:out value="${userDetail.userId}"/></td>
                        <th>이름</th>
                        <td><c:out value="${userDetail.userNm}"/></td>
                    </tr>
                    <tr>
                        <th>회원구분</th>
                        <td colspan="3">
                            <div class="depth2_tr w430">
                                <div class="td w80">가족대표</div>
                                <div class="th w90">가족대표변경</div>
                                <div class="td">
                                    <div class="input-group">
                                        <select name="selFamily" id="selFamily" class="custom-select">
                                        <c:forEach items="${familyList}" var="list" varStatus="status">
                                            <c:choose>
                                                <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                                                    <option value="<c:out value="${list.userId}"/>" selected><c:out value="${list.userNm}"/></option>
                                                </c:when>
                                                <c:when test="${list.fmlyTpCd eq 'FAMILY'}">
                                                    <option value="<c:out value="${list.userId}"/>"><c:out value="${list.userNm}"/></option>
                                                </c:when>
                                            </c:choose>
                                        </c:forEach>
                                        </select>
                                        <div class="input-group-append">
                                            <button class="btn btn-gray btn-sm" type="button" onclick="btn_represent();">저장</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>입주자 등록</th>
                        <td><fmt:formatDate value="${userDetail.hsholdCompoDt}" pattern="yyyy-MM-dd"/></td>
                        <th>휴대폰 번호</th>
                        <td><c:out value="${userDetail.mphoneNo}"/></td>
                    </tr>
                    <tr>
                        <th>이메일 주소</th>
                        <td colspan="3"><c:out value="${userDetail.emailNm}"/></td>
                    </tr>
                    <tr>
                        <th>디바이스 버전</th>
                        <td><c:out value="${userDetail.oprsNm}"/> <c:out value="${userDetail.oprsVer}"/></td>
                        <th>로그인 허용</th>
                        <td>
                            <div class="depth2_tr">
                                <div class="td w180" style="width:178px;">
                                <c:choose>
                                    <c:when test="${userDetail.useYn eq 'Y'}">
                                        <div class="custom-control custom-radio d-inline-block mr-4">
                                            <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio" />
                                            <label class="custom-control-label" for="radio1">No</label>
                                        </div>
                                        <div class="custom-control custom-radio d-inline-block">
                                            <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio" checked/>
                                            <label class="custom-control-label" for="radio2">Yes</label>
                                        </div>
                                    </c:when>
                                    <c:when test="${userDetail.useYn eq 'N'}">
                                        <div class="custom-control custom-radio d-inline-block mr-4">
                                            <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio" checked />
                                            <label class="custom-control-label" for="radio1">No</label>
                                        </div>
                                        <div class="custom-control custom-radio d-inline-block">
                                            <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio"/>
                                            <label class="custom-control-label" for="radio2">Yes</label>
                                        </div>
                                    </c:when>
                                </c:choose>
                                </div>
                                <div class="td">
                                    <button class="btn btn-gray btn-sm" type="button" onclick="btn_useYn();">저장</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>SNS 연동</th>
                        <td><c:out value="${userDetail.certfTpCd}"/></td>
                        <th>디바이스 모델명</th>
                        <td><c:out value="${userDetail.mphoneMdl}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-brown" onclick="back();">목록</button>
            </div>
        </div>

    </div>

</div>