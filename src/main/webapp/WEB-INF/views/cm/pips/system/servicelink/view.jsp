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

        var houscplxNm = "<c:out value="${serviceLinkDetailInfo.houscplxNm}"/>";
        var houscplxNmVal = houscplxNm.split(",");

        var result = "<tr><th rowspan='"+houscplxNmVal.length+"'>적용단지</th>";
        result += "<td>"+houscplxNmVal[0]+"</td></tr>"
        for(var i=1;i<houscplxNmVal.length;i++){
            result += "<tr><td>"+houscplxNmVal[i]+"</td></tr>";
        }

        $("#houscplxNmList").append(result);

        var tpcd = "<c:out value="${serviceLinkDetailInfo.lnkSvcGrpTpCd}"/>";

        if(tpcd == "ADDTION_APP" || tpcd == "PRUGIO_APP"){
            $("#app").show();
            $("#web").hide();
        }else{
            $("#web").show();
            $("#app").hide();
        }
    });

    function list() {
        location.href = "/cm/system/serviceLink/list";
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
            <h2 class="tit">연계 웹/앱 상세</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>연계 웹/앱 관리</li>
                <li>연계 웹/앱 상세</li>
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
                        <c:if test="${serviceLinkDetailInfo.lnkSvcGrpTpCd eq 'ADDTION_APP'}">
                            <td>추가 서비스 APP</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.lnkSvcGrpTpCd eq 'ADDTION_WEB'}">
                            <td>추가 서비스 WEB</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.lnkSvcGrpTpCd eq 'PRUGIO_APP'}">
                            <td>푸르지오 APP</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.lnkSvcGrpTpCd eq 'PRUGIO_WEB'}">
                            <td>푸르지오 WEB</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.lnkSvcGrpTpCd eq 'GREENERY_APP'}">
                            <td>그리너리라운지 APP</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.lnkSvcGrpTpCd eq 'GREENERY_WEB'}">
                            <td>그리너리라운지 WEB</td>
                        </c:if>
                        <th>서비스명</th>
                        <td><c:out value="${serviceLinkDetailInfo.lnkSvcNm}"/></td>
                    </tr>
                    <tr>
                        <th>담당자</th>
                        <td><c:out value="${serviceLinkDetailInfo.perchrgNm}"/></td>
                        <th>사무실 전화</th>
                        <td><c:out value="${serviceLinkDetailInfo.offcPhoneNo}"/></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><c:out value="${serviceLinkDetailInfo.emailNm}"/></td>
                        <th>활성화</th>
                        <c:if test="${serviceLinkDetailInfo.useYn eq 'Y'}">
                            <td>YES</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.useYn eq 'N'}">
                            <td>NO</td>
                        </c:if>
                    </tr>
                    <tr>
                        <th>메인화면 표시</th>
                        <c:if test="${serviceLinkDetailInfo.mainScreenYn eq 'Y'}">
                            <td>YES</td>
                        </c:if>
                        <c:if test="${serviceLinkDetailInfo.mainScreenYn eq 'N'}">
                            <td>NO</td>
                        </c:if>
                        <th>정렬순서</th>
                        <td><c:out value="${serviceLinkDetailInfo.lnkOrdNo}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap2" id="app" style="display:none;" >
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th rowspan="2">스토어 URL</th>
                        <td>Android</td>
                        <td><c:out value="${url_android_cont}"/></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><c:out value="${url_ios_cont}"/></td>
                    </tr>
                    <tr>
                        <th rowspan="2">스키마</th>
                        <td>Android</td>
                        <td><c:out value="${schema_android_cont}"/></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><c:out value="${schema_ios_cont}"/></td>
                    </tr>
                    <tr>
                        <th rowspan="2">Deep Link</th>
                        <td>Android</td>
                        <td><c:out value="${deeplink_android_cont}"/></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><c:out value="${deeplink_ios_cont}"/></td>
                    </tr>
                    <tr>
                        <th rowspan="2">App Id</th>
                        <td>Android</td>
                        <td><c:out value="${app_id_android_cont}"/></td>
                    </tr>
                    <tr>
                        <td>IOS</td>
                        <td><c:out value="${app_id_ios_cont}"/></td>
                    </tr>
                    <tr>
                        <th>아이콘 이미지</th>
                        <td colspan="2">
                            <c:choose>
                                <c:when test="${empty serviceLinkDetailInfo}">
                                    <div class="clearfix">
                                        <div class="float-left align_middle">파일없음</div>
                                        <div class="float-right"><button class="btn btn-gray btn-sm">다운로드</button></div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="clearfix">
                                        <div class="float-left align_middle"><c:out value="${serviceLinkDetailInfo.orgnlFileNm}"/></div>
                                        <div class="float-right"><button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${serviceLinkDetailInfo.orgnlFileNm}"/>','<c:out value="${serviceLinkDetailInfo.fileUrlCont}"/>')">다운로드</button></div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>


        <div class="table_wrap2" id="web" style="display:none;">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>URL</th>
                        <td style="width:100%;"><c:out value="${url_web_cont}"/></td>
                    </tr>
                    <tr>
                        <th>아이콘 이미지</th>
                        <td colspan="2">
                            <c:choose>
                                <c:when test="${empty serviceLinkDetailInfo}">
                                    <div class="clearfix">
                                        <div class="float-left align_middle">파일없음</div>
                                        <div class="float-right"><button class="btn btn-gray btn-sm">다운로드</button></div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="clearfix">
                                        <div class="float-left align_middle"><c:out value="${serviceLinkDetailInfo.orgnlFileNm}"/></div>
                                        <div class="float-right"><button class="btn btn-gray btn-sm" type="button" onclick="fileDownload('<c:out value="${serviceLinkDetailInfo.orgnlFileNm}"/>','<c:out value="${serviceLinkDetailInfo.fileUrlCont}"/>')">다운로드</button></div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody id="houscplxNmList"></tbody>
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

<form:form id="form_edit" action="/cm/system/serviceLink/edit" name="detail" commandName="detail" method="post">
      <input type="text" id="svcId" name="lnkSvcId" value="<c:out value="${serviceLinkDetailInfo.lnkSvcId}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_del" action="/cm/system/serviceLink/deleteServiceLinkAction" name="detail" commandName="detail" method="post">
      <input type="text" id="svcId" name="lnkSvcId" value="<c:out value="${serviceLinkDetailInfo.lnkSvcId}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>