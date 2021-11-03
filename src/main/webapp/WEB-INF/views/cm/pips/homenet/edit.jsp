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
    function modify(){
        if(isValid() == false){
            return;
        }

        $("#hmnetId").val("<c:out value="${homenetDetail.hmnetId}"/>");
        $("#hmnetNm").val($("#hname").val());
        $("#urlCont").val($("#url").val());
        $("#editerId").val("<c:out value="${session_user.userName}"/>");

        $("#form_modify").submit();
    }

    //유효성체크
    function isValid(){
        var expUrl = /^http[s]?\:\/\//i;
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        if(expUrl.test($("#url").val()) == false || $("#url").val() == ""){
            alert("올바른 도메인주소를 넣어주세요.");
            return false;
        }

        if($("#hname").val() == ""){
            alert("홈넷서버 이름을 입력해주세요.");
            return false;
        }
        if(RegExp.test($("#hmname").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return false;
        }

        return true;
    }
    //상세페이지 버튼
    function list_view(id){
        $("#homenetId").val(id);
        $("#form_detail").submit();
    }

    //사용, 정지 버튼
    function hmnet_yn(id){
        var yn = $("input[name=radio]:checked").val();
        var param = new Object();
        param.hmnetId = id;
        param.tgtTp = "device";
        param.trnsmYn = yn;


        $.ajax({
            url: '/cm/api/hmnet/conf/send',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){

                var return_data = JSON.stringify(data);
                return_data = JSON.parse(return_data);
                if(return_data.params[0].reqStatus == true){
                    alert("요청되었습니다.");
                }else{
                    alert("요청이 실패했습니다.");
                }

            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }

    function save_btn(id){
        if($("#keep").val() == ""){
            alert("Keep alive 주기(초)를 입력해주세요.");
            return;
        }
        if($("#dat").val() == ""){
            alert("장치상태 전송주기(초)를 입력해주세요.");
            return;
        }
        if($("#ctl").val() == ""){
            alert("제어타임아웃 전송주기(초)를 입력해주세요.");
            return;
        }

        $("#hmnetId_").val(id);
        $("#keepAliveCycleCont").val($("#keep").val());
        $("#datTrnsmCycleCont").val($("#dat").val());
        $("#ctlExprtnCycleCont").val($("#ctl").val());
        $("#form_modify_").submit();

    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">홈넷서버 정보 수정</h2>
            <ul class="location">
                <li>홈넷서버 관리</li>
                <li>홈넷서버 관리</li>
                <li>홈넷서버 목록</li>
                <li>홈넷서버 상세</li>
                <li>홈넷서버 정보 수정</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-gray btn-sm" type="button" onclick="modify();">저장</button>
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
                        <th>종류</th>
                        <c:choose>
                            <c:when test="${homenetDetail.svrTpCd eq 'UNIFY_SVR'}">
                                <td>통합서버</td>
                            </c:when>
                            <c:when test="${homenetDetail.svrTpCd eq 'HOUSCPLX_SVR'}">
                                <td>단지서버</td>
                            </c:when>
                        </c:choose>
                        <th>홈넷서버 이름</th>
                        <td colspan="1">
                            <div class="input-group">
                                <input type="text" id="hname" name="hname" class="form-control" value="<c:out value="${homenetDetail.hmnetNm}"/>" maxlength="50"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>홈넷서버 ID</th>
                        <td><c:out value="${homenetDetail.hmnetId}"/></td>
                        <th>인증 Key</th>
                        <td><c:out value="${homenetDetail.hmnetKeyCd}"/></td>
                    </tr>
                    <tr>
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
                        <th>연동상태</th>
                        <td><c:out value="${homenetDetail.stsCd}"/></td>
                    </tr>
                    <tr>
                        <th>도메인</th>
                        <td colspan="3">
                            <div class="input-group">
                                <input type="text" class="form-control" id="url" name="url" value="<c:out value="${homenetDetail.urlCont}"/>"/>
                            </div>
                        </td>
                    </tr>

                </tbody>
            </table>
        </div>

        <div class="sub_tit">
            <h3 class="tit">설정</h3>
        </div>
        <div class="table_wrap2">
            <c:if test="${homenetDetail.useYn eq 'Y' && homenetDetail.stsCd eq 'OK'}">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-gray btn-sm" type="button" onclick="save_btn('<c:out value="${homenetDetail.hmnetId}"/>');">저장</button>
                </div>
            </div>
            </c:if>
            <c:choose>
                <c:when test="${homenetDetail.useYn eq 'Y' && homenetDetail.stsCd eq 'OK'}">
                    <table class="table2">
                        <colgroup>
                            <col style="width:150px"/>
                            <col />
                            <col style="width:150px"/>
                            <col />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>사용여부</th>
                                <td>
                                    <div class="depth2_tr">
                                        <c:if test="${homenetDetail.useYn eq 'Y'}">
                                            <div class="td w180">
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                                    <label class="custom-control-label" for="radio1">정지</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" class="custom-control-input" value="Y" checked id="radio2" name="radio">
                                                    <label class="custom-control-label" for="radio2">사용</label>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${homenetDetail.useYn eq 'N'}">
                                            <div class="td w180">
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" class="custom-control-input" value="N" checked id="radio1" name="radio">
                                                    <label class="custom-control-label" for="radio1">정지</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio">
                                                    <label class="custom-control-label" for="radio2">사용</label>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="td">
                                            <button class="btn btn-gray btn-sm" type="button" onclick="hmnet_yn('<c:out value="${homenetDetail.hmnetId}"/>');">저장</button>
                                        </div>
                                    </div>
                                </td>
                                <th>Keep alive<br />주기 (초)</th>
                                <td>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="keep" value="<c:out value="${homenetDetail.keepAliveCycleCont}"/>"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>장치상태<br />전송주기 (초)</th>
                                <td>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dat" value="<c:out value="${homenetDetail.datTrnsmCycleCont}"/>"/>

                                    </div>
                                </td>
                                <th>제어타임아웃<br />전송주기 (초)</th>
                                <td>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="ctl" value="<c:out value="${homenetDetail.ctlExprtnCycleCont}"/>"/>

                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </c:when>

                <c:otherwise>
                    <table class="table2">
                        <colgroup>
                            <col style="width:150px"/>
                            <col />
                            <col style="width:150px"/>
                            <col />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>사용여부</th>
                                <td>
                                    <div class="depth2_tr">
                                        <c:if test="${homenetDetail.useYn eq 'Y'}">
                                            <div class="td w180">
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                                    <label class="custom-control-label" for="radio1">정지</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" class="custom-control-input" value="Y" checked id="radio2" name="radio">
                                                    <label class="custom-control-label" for="radio2">사용</label>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${homenetDetail.useYn eq 'N'}">
                                            <div class="td w180">
                                                <div class="custom-control custom-radio d-inline-block mr-4">
                                                    <input type="radio" class="custom-control-input" value="N" checked id="radio1" name="radio">
                                                    <label class="custom-control-label" for="radio1">정지</label>
                                                </div>
                                                <div class="custom-control custom-radio d-inline-block">
                                                    <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio">
                                                    <label class="custom-control-label" for="radio2">사용</label>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="td">
                                            <button class="btn btn-gray btn-sm" type="button" onclick="hmnet_yn('<c:out value="${homenetDetail.hmnetId}"/>');">저장</button>
                                        </div>
                                    </div>
                                </td>
                                <th>Keep alive<br />주기 (초)</th>
                                <td><c:out value="${homenetDetail.keepAliveCycleCont}"/></td>
                            </tr>
                            <tr>
                                <th>장치상태<br />전송주기 (초)</th>
                                <td><c:out value="${homenetDetail.datTrnsmCycleCont}"/></td>
                                <th>제어타임아웃<br />전송주기 (초)</th>
                                <td><c:out value="${homenetDetail.ctlExprtnCycleCont}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-brown" type="button" onclick="list_view('<c:out value="${homenetDetail.hmnetId}"/>');">상세페이지</button>
            </div>
        </div>

    </div>

</div>


<form:form id="form_modify" action="/cm/homenet/info/editAction" name="detail" commandName="detail" method="post">
      <input type="text" id="hmnetId" name="hmnetId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hmnetNm" name="hmnetNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="urlCont" name="urlCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="editerId" name="editerId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_modify_" action="/cm/homenet/info/confeditAction" name="detail" commandName="detail" method="post">
      <input type="text" id="hmnetId_" name="hmnetId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="keepAliveCycleCont" name="keepAliveCycleCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="datTrnsmCycleCont" name="datTrnsmCycleCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="ctlExprtnCycleCont" name="ctlExprtnCycleCont" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detail" action="/cm/homenet/info/view" name="detail" commandName="detail" method="post">
      <input type="text" id="homenetId" name="homenetId" style="width:0;height:0;visibility:hidden"/>
</form:form>