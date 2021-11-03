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
    //취소버튼
    function cancel(){
        location.href = "/cm/homenet/info/list";
    }
    //등록하기전 유효성체크
    function isValid(){
        var expUrl = /^http[s]?\:\/\//i;
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        if($("#isValid").val() == "NULL"){
            alert("서버종류를 선택해주세요.");
            return false;
        }
        if($("#bzcd").val() == "NULL"){
            alert("홈넷사를 선택해주세요.");
            return false;
        }
        if(expUrl.test($("#url").val()) == false || $("#url").val() == ""){
            alert("올바른 도메인주소를 넣어주세요.");
            return false;
        }
        if($("#hmname").val() == ""){
            alert("홈넷서버이름을 입력해주세요.");
            return false;
        }

        if(RegExp.test($("#hmname").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return false;
        }

        return true;
    }

    function formsubmit(){
        if(isValid() == false){
            return;
        }

        $("#svrTpCd").val($("#svrcd").val());
        $("#hmnetNm").val($("#hmname").val());
        $("#bizcoCd").val($("#bzcd").val());
        $("#urlCont").val($("#url").val());
        $("#keepAliveCycleCont").val($("#kacc").val());
        $("#datTrnsmCycleCont").val($("#dtcc").val());
        $("#ctlExprtnCycleCont").val($("#cecc").val());
        $("#useYn").val($("input[name='radio']:checked").val());
        $("#delYn").val("N");
        $("#crerId").val("<c:out value="${session_user.userId}"/>");
        $("#editerId").val("<c:out value="${session_user.userId}"/>");

        $("#form_add").submit();
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">홈넷서버 신규등록</h2>
            <ul class="location">
                <li>홈넷서버 관리</li>
                <li>홈넷서버 관리</li>
                <li>홈넷서버 목록</li>
                <li>홈넷서버 신규등록</li>
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
                        <th>종류</th>
                        <td>
                            <select name="svrcd" id="svrcd" class="custom-select">
                                <option value="NULL">선택</option>
                                <option value="UNIFY_SVR">통합서버</option>
                                <option value="HOUSCPLX_SVR">단지서버</option>
                            </select>
                        </td>
                        <th>홈넷서버이름</th>
                        <td><input type="text" name="hmname" id="hmname" class="form-control" maxlength="50"/></td>
                    </tr>
                    <tr>
                        <th>홈넷사</th>
                        <td>
                            <select name="bzcd" id="bzcd" class="custom-select">
                                <option value="NULL">선택</option>
                            <c:forEach items="${bizcocdList}" var="list" varStatus="status">
                                <option value="<c:out value="${list.commCd}"/>"><c:out value="${list.commCdNm}"/></option>
                            </c:forEach>
                            </select>
                        </td>
                        <th>도메인</th>
                        <td><input type="text" name="url" id="url" class="form-control" /></td>
                    </tr>
                    <tr>
                        <th>사용여부</th>
                        <td>
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="N" checked id="radio1" name="radio">
                                <label class="custom-control-label" for="radio1">정지</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio">
                                <label class="custom-control-label" for="radio2">사용</label>
                            </div>
                        </td>
                        <th>Keep alive<br />주기 (초)</th>
                        <td><input type="text" class="form-control" id="kacc" name="kacc" value="60" disabled/></td>
                    </tr>
                    <tr>
                        <th>장치상태<br />전송주기 (초)</th>
                        <td><input type="text" class="form-control" id="dtcc" name="dtcc" value="-1" disabled/></td>
                        <th>제어타임아웃<br />전송주기 (초)</th>
                        <td><input type="text" class="form-control" id="cecc" name="cecc" value="60" disabled/></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">등록</button>
            </div>
        </div>
    </div>
</div>

<form:form id="form_add" action="/cm/homenet/info/insertAction" name="add" commandName="add" method="post">
      <input type="text" id="svrTpCd" name="svrTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hmnetNm" name="hmnetNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="bizcoCd" name="bizcoCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="urlCont" name="urlCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="keepAliveCycleCont" name="keepAliveCycleCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="datTrnsmCycleCont" name="datTrnsmCycleCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="ctlExprtnCycleCont" name="ctlExprtnCycleCont" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="useYn" name="useYn" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="delYn" name="delYn" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="crerId" name="crerId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="editerId" name="editerId" style="width:0;height:0;visibility:hidden"/>
</form:form>