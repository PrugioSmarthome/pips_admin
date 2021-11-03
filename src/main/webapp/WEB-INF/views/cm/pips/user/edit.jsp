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

    function btn_save() {
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var expUrl = /^http[s]?\:\/\//i;
        var name = $("#userNm").val();
        var number = $("#mphoneNo").val();
        var mail = $("#emailNm").val();
        if(RegExp.test(name) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if(RegExp.test(number) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if(expUrl.test(mail) == true){
            alert('올바른 이메일 형식이 아닙니다.');
            return;
        }
        $("#form_edit").submit();
    }

    function btn_represent() {
        var representId = '';
        <c:forEach items="${userFamilyList}" var="list" varStatus="status">
            if ('${list.fmlyTpCd}' == 'REPRESENTATIVE') {
                representId = "<c:out value="${list.userId}"/>";
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
                } else {
                    alert("실패했습니다.");
                }

                location.reload();
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
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
                "userId" : "<c:out value="${userDetail.userId}"/>",
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
                console.log("에러");
                console.log(e.responseText.trim());
                alert("실패했습니다.");
                location.reload();
            }
        });
    }

</script>
<form:form id="form_edit" action="/cm/pips/user/editUserAction" method="post" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">회원정보 수정</h2>
            <ul class="location">
                <li>회원 관리</li>
                <li>회원정보 관리</li>
                <li>회원정보 목록</li>
                <li>회원정보 수정</li>
            </ul>
        </div>

        <c:choose>
            <c:when test="${hsholdId eq ''}">
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
                                <th>아이디</th>
                                <td>${userDetail.userId}</td>
                                <th>이름</th>
                                <td>
                                    <div class="input-group">
                                        <input type="text" id="userNm" name="userNm" class="form-control" value="<c:out value="${userDetail.userNm}"/>"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>회원구분</th>
                                <td>
                                    <div class="depth2_tr w430">
                                        <div class="td w80">일반회원</div>
                                    </div>
                                </td>
                                <th>휴대폰 번호</th>
                                <td>
                                    <div class="input-group">
                                        <input type="number" id="mphoneNo" name="mphoneNo" class="form-control" value="<c:out value="${userDetail.mphoneNo}"/>"/>

                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <th>이메일 주소</th>
                                <td colspan="3">
                                    <div class="input-group">
                                        <input type="text" id="emailNm" name="emailNm" class="form-control" value="<c:out value="${userDetail.emailNm}"/>"/>

                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>디바이스 버전</th>
                                <td><c:out value="${userDetail.oprsNm}"/> <c:out value="${userDetail.oprsVer}"/></td>
                                <th>로그인 허용</th>
                                <td>
                                    <div class="depth2_tr">
                                        <div class="td w180">
                                            <c:choose>
                                                <c:when test="${userDetail.useYn eq 'Y'}">
                                                    <div class="custom-control custom-radio d-inline-block mr-4">
                                                        <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                                        <label class="custom-control-label" for="radio1">No</label>
                                                    </div>
                                                    <div class="custom-control custom-radio d-inline-block">
                                                        <input type="radio" checked class="custom-control-input" value="Y" id="radio2" name="radio">
                                                        <label class="custom-control-label" for="radio2">Yes</label>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="custom-control custom-radio d-inline-block mr-4">
                                                        <input type="radio" checked class="custom-control-input" value="N" id="radio1" name="radio">
                                                        <label class="custom-control-label" for="radio1">No</label>
                                                    </div>
                                                    <div class="custom-control custom-radio d-inline-block">
                                                        <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio">
                                                        <label class="custom-control-label" for="radio2">Yes</label>
                                                    </div>
                                                </c:otherwise>
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
                                <c:if test="${userDetail.certfTpCd eq 'NORMAL'}">
                                    <td>일반</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'GOOGLE'}">
                                    <td>구글</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'FACEBOOK'}">
                                    <td>페이스북</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'KAKAO'}">
                                    <td>카카오</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'NAVER'}">
                                    <td>네이버</td>
                                </c:if>
                                <th>디바이스 모델명</th>
                                <td><c:out value="${userDetail.mphoneMdl}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
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
                                <td>${userDetail.userId}</td>
                                <th>이름</th>
                                <td>
                                    <div class="input-group">
                                        <input type="text" id="userNm" name="userNm" class="form-control" value="<c:out value="${userDetail.userNm}"/>"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>회원구분</th>
                                <td colspan="3">
                                    <div class="depth2_tr w430">
                                        <c:if test="${userDetail.fmlyTpCd eq 'FAMILY'}">
                                            <div class="td w80">구성원</div>
                                        </c:if>
                                        <c:if test="${userDetail.fmlyTpCd eq 'REPRESENTATIVE'}">
                                            <div class="td w80">가족대표</div>
                                        </c:if>

                                        <div class="th w90">가족대표변경</div>
                                        <div class="td">
                                            <div class="input-group">
                                                <select name="selFamily" id="selFamily" class="custom-select">
                                                    <option value="null">선택</option>
                                                    <c:forEach items="${userFamilyList}" var="list" varStatus="status">
                                                        <c:choose>
                                                            <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                                                                <option value="<c:out value="${list.userId}"/>" selected><c:out value="${list.userNm}"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="<c:out value="${list.userId}"/>"><c:out value="${list.userNm}"/></option>
                                                            </c:otherwise>
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
                                <td>
                                    <div class="input-group">
                                        <input type="text" id="mphoneNo" name="mphoneNo" class="form-control" value="<c:out value="${userDetail.mphoneNo}"/>"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>이메일 주소</th>
                                <td colspan="3">
                                    <div class="input-group">
                                        <input type="text" id="emailNm" name="emailNm" class="form-control" value="<c:out value="${userDetail.emailNm}"/>"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>디바이스 버전</th>
                                <td><c:out value="${userDetail.oprsNm}"/> <c:out value="${userDetail.oprsVer}"/></td>
                                <th>로그인 허용</th>
                                <td>
                                    <div class="depth2_tr">
                                        <c:choose>
                                            <c:when test="${userDetail.useYn eq 'Y'}">
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                                    <label class="custom-control-label" for="radio1">No</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" checked class="custom-control-input" value="Y" id="radio2" name="radio">
                                                    <label class="custom-control-label" for="radio2">Yes</label>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" checked class="custom-control-input" value="N" id="radio1" name="radio">
                                                    <label class="custom-control-label" for="radio1">No</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio">
                                                    <label class="custom-control-label" for="radio2">Yes</label>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="td">
                                            <button class="btn btn-gray btn-sm" type="button" onclick="btn_useYn();">저장</button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>SNS 연동</th>
                                <c:if test="${userDetail.certfTpCd eq 'NORMAL'}">
                                    <td>일반</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'GOOGLE'}">
                                    <td>구글</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'FACEBOOK'}">
                                    <td>페이스북</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'KAKAO'}">
                                    <td>카카오</td>
                                </c:if>
                                <c:if test="${userDetail.certfTpCd eq 'NAVER'}">
                                    <td>네이버</td>
                                </c:if>
                                <th>디바이스 모델명</th>
                                <td><c:out value="${userDetail.mphoneMdl}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-brown" type="button" onclick="back();">목록</button>
                <button class="btn btn-gray" type="button" onclick="btn_save();">저장</button>
            </div>
        </div>

    </div>

</div>
<input type="hidden" id="userId" name="userId" value="<c:out value="${userDetail.userId}"/>" />
<input type="text" id="houscplxCdGoList" name="houscplxCdGoList" value="<c:out value="${houscplxCdGoList}"/>" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="houscplxCdGoListNm" name="houscplxCdGoListNm" value="<c:out value="${houscplxCdGoListNm}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>