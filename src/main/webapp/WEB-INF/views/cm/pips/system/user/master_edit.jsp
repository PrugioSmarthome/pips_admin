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
        location.href = "/cm/system/userMaster/list";
    }

    function btn_save() {
        var masterYn =$('input[name=radio0]:checked').val();
        $("#masterYn").val(masterYn);

        $("#form_edit").submit();
    }

</script>
<form:form id="form_edit" action="/cm/system/userMaster/editUserAction" method="post" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">마스터 계정 관리</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>마스터 계정 관리</li>
                <li>마스터 계정 목록</li>
                <li>마스터 계정 수정</li>
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
                                <td><c:out value="${userDetail.userNm}"/></td>
                            </tr>
                            <tr>
                                <th>회원구분</th>
                                <td>
                                    <div class="depth2_tr w430">
                                        <div class="td w80">일반회원</div>
                                    </div>
                                </td>
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
                                        <div class="td w180">
                                            <c:choose>
                                                <c:when test="${userDetail.useYn eq 'Y'}">
                                                    <div class="custom-control custom-radio d-inline-block mr-4">
                                                        <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio" disabled />
                                                        <label class="custom-control-label" for="radio1">No</label>
                                                    </div>
                                                    <div class="custom-control custom-radio d-inline-block">
                                                        <input type="radio" checked class="custom-control-input" value="Y" id="radio2" name="radio" disabled />
                                                        <label class="custom-control-label" for="radio2">Yes</label>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="custom-control custom-radio d-inline-block mr-4">
                                                        <input type="radio" checked class="custom-control-input" value="N" id="radio1" name="radio" disabled />
                                                        <label class="custom-control-label" for="radio1">No</label>
                                                    </div>
                                                    <div class="custom-control custom-radio d-inline-block">
                                                        <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio" disabled />
                                                        <label class="custom-control-label" for="radio2">Yes</label>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
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
                            <tr>
                                <th>마스터 계정</th>
                                <td colspan="3">
                                    <div class="depth2_tr w430">
                                        <div class="td w180">
                                            <div class="custom-control custom-radio d-inline-block mr-4">
                                                <input type="radio" class="custom-control-input" value="N" id="radio3" name="radio0" <c:if test="${userDetail.masterYn eq 'N'}"> checked </c:if> />
                                                <label class="custom-control-label" for="radio3">No</label>
                                            </div>
                                            <div class="custom-control custom-radio d-inline-block">
                                                <input type="radio" class="custom-control-input" value="Y" id="radio4" name="radio0" <c:if test="${userDetail.masterYn eq 'Y'}"> checked </c:if> />
                                                <label class="custom-control-label" for="radio4">Yes</label>
                                            </div>
                                        </div>
                                    </div>
                                </td>
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
                                <td><c:out value="${userDetail.userNm}"/></td>
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

                                        <div class="th w90">가족대표</div>
                                        <div class="td">
                                            <div class="input-group">
                                                <select name="selFamily" id="selFamily" class="custom-select" disabled style='background-color:#EAEAEA' onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect;'>
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
                                        <c:choose>
                                            <c:when test="${userDetail.useYn eq 'Y'}">
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio" disabled />
                                                    <label class="custom-control-label" for="radio1">No</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" checked class="custom-control-input" value="Y" id="radio2" name="radio" disabled />
                                                    <label class="custom-control-label" for="radio2">Yes</label>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" checked class="custom-control-input" value="N" id="radio1" name="radio" disabled />
                                                    <label class="custom-control-label" for="radio1">No</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio" disabled />
                                                    <label class="custom-control-label" for="radio2">Yes</label>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
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
                            <tr>
                                <th>마스터 계정</th>
                                <td colspan="3">
                                    <div class="depth2_tr w430">
                                        <div class="td w180">
                                            <div class="custom-control custom-radio d-inline-block mr-4">
                                                <input type="radio" class="custom-control-input" value="N" id="radio3" name="radio0" <c:if test="${userDetail.masterYn eq 'N'}"> checked </c:if> />
                                                <label class="custom-control-label" for="radio3">No</label>
                                            </div>
                                            <div class="custom-control custom-radio d-inline-block">
                                                <input type="radio" class="custom-control-input" value="Y" id="radio4" name="radio0" <c:if test="${userDetail.masterYn eq 'Y'}"> checked </c:if> />
                                                <label class="custom-control-label" for="radio4">Yes</label>
                                            </div>
                                        </div>
                                    </div>
                                </td>
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
<input type="text" id="masterYn" name="masterYn" style="width:0;height:0;visibility:hidden"/>
</form:form>