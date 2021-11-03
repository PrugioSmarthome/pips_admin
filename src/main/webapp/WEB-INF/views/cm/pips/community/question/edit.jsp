<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">

<script type="text/javascript">
    $(document).ready(function(){
        var firstsubtitle = "<c:out value="${questionDetail.qstAnsrTitle}"/>";
        firstsubtitle = firstsubtitle.replace(/&lt;/g,"<");
        firstsubtitle = firstsubtitle.replace(/&gt;/g,">");
        $("#subtitle").val(firstsubtitle);
    });

    function change_select(e){
        var ch = $(e).val();
        if(ch == "choice2"){inputadd(2,"N");$("#isQuestionDetailChange").val("Y");}
        if(ch == "choice3"){inputadd(3,"N");$("#isQuestionDetailChange").val("Y");}
        if(ch == "choice4"){inputadd(4,"N");$("#isQuestionDetailChange").val("Y");}
        if(ch == "choice2p"){inputadd(2,"Y");$("#isQuestionDetailChange").val("Y");}
        if(ch == "choice3p"){inputadd(3,"Y");$("#isQuestionDetailChange").val("Y");}
        if(ch == "choice4p"){inputadd(4,"Y");$("#isQuestionDetailChange").val("Y");}
        if(ch == "null"){$("#input_area").empty();$("#isQuestionDetailChange").val("Y");}
    }

    function inputadd(num,e){
        if(e == "N"){
            $("#input_area").empty();
            for(var i = 1 ; i <= num ; i++){
                $("#input_area").append("<div class='depth2_tr'><div class='th w30'>"+i+".</div><div class='td'><input type='text' name='cont' class='form-control' placeholder='"+i+"번 선택지를 입력해주세요.'/></div></div>");
            }
        }else{
            $("#input_area").empty();
            for(var i = 1 ; i <= num ; i++){
                $("#input_area").append("<div class='depth2_tr'><div class='th w30'>"+i+".</div><div class='td'><input type='text' name='cont' class='form-control' placeholder='"+i+"번 선택지를 입력해주세요.'/></div></div>");
            }
            var etcnum = num+1;
            $("#input_area").append("<div class='depth2_tr'><div class='th w30'>"+etcnum+".</div><div class='td'>기타의견</div></div>");
        }
    }

    function temp_save_btn(){
        var cnt = $("input[name=cont]").length;
        var RegExp = /[\{\}\[\]\/;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
        var title = $("#title").val();
        var subtitle = $("#subtitle").val();
        if($("#title").val() == ""){
            alert("제목을 입력해주세요.");
            return;
        }
        if($("#startime").val() == ""){
            alert("시작날짜를 입력해주세요.");
            return;
        }
        if($("#endtime").val() == ""){
            alert("종료날짜를 입력해주세요.");
            return;
        }
        if($("#choice").val() == "null"){
            alert("설문 유형을 선택해 주세요.");
            return;
        }
        if($("#subtitle").val() == ""){
            alert("설문명을 입력해주세요.");
            return;
        }
        if(RegExp.test(title) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if(RegExp.test($("#subtitle").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        title = title.replace(/</g,"&lt;");
        title = title.replace(/>/g,"&gt;");
        subtitle = subtitle.replace(/</g,"&lt;");
        subtitle = subtitle.replace(/>/g,"&gt;");
        var flag = true;
        var qstArray = new Array();
        var regexpcheck;
        if(cnt > 0){
            for(var i = 0 ; i < cnt ; i++){
                var cont = $("input[name=cont]").eq(i).val();
                if(cont == ""){
                    flag = false;
                }else{
                    var jsonObject = new Object();
                    var ansrcont = $("input[name=cont]").eq(i).val();
                    if(RegExp.test(ansrcont) == true){
                        regexpcheck = false;
                    }
                    ansrcont = ansrcont.replace(/</g,"&lt;");
                    ansrcont = ansrcont.replace(/>/g,"&gt;");
                    jsonObject.qstItmAnsrCont = ansrcont;
                    jsonObject.editerId = "<c:out value="${session_user.userId}"/>";
                    if($("#isQuestionDetailChange").val() == "N"){
                        jsonObject.qstItmNo = $("input[name=itmno]").eq(i).val();
                    }
                    qstArray.push(jsonObject);
                }
            }
            if($("#choice").val() == "choice2p" || $("#choice").val() == "choice3p" || $("#choice").val() == "choice4p"){
                var jsonObject = new Object();
                jsonObject.qstItmAnsrCont = "기타의견";
                if($("#isQuestionDetailChange").val() == "N"){
                    jsonObject.qstItmNo = $("#etc_itmno").val();
                }
                jsonObject.editerId = "<c:out value="${session_user.userId}"/>";
                qstArray.push(jsonObject);
            }
        }
        var qstItmAnsrContList = JSON.stringify(qstArray);
        if(flag == false){
            alert("선택지 내용을 입력해주세요.");
            return;
        }
        if(regexpcheck == false){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }

        $("#qstTitle").val(title);
        $("#qstStDt").val($("#startime").val().replace(/\-/gi,''));
        $("#qstEdDt").val($("#endtime").val().replace(/\-/gi,''));
        $("#qstItmQuestCont").val(subtitle);
        if($("#isQuestionDetailChange").val() == "N"){
            $("#qstItmAnsrContList").val(qstItmAnsrContList);
        }else{
            $("#newQstItmAnsrContJsonArray").val(qstItmAnsrContList);
        }

        if($("#choice").val() == "choice2"){$("#qstTpCd").val("1");}
        if($("#choice").val() == "choice3"){$("#qstTpCd").val("3");}
        if($("#choice").val() == "choice4"){$("#qstTpCd").val("5");}
        if($("#choice").val() == "choice2p"){$("#qstTpCd").val("2");}
        if($("#choice").val() == "choice3p"){$("#qstTpCd").val("4");}
        if($("#choice").val() == "choice4p"){$("#qstTpCd").val("6");}


        $("#form_edit").submit();

    }

    function back(){
        window.history.back();
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">설문조사 수정</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>설문조사 관리</li>
                <li>설문조사 목록</li>
                <li>설문조사 상세</li>
                <li>설문조사 수정</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col style="width:340px"/>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>등록일</th>
                        <td colspan="3"><fmt:formatDate value="${questionDetail.crDt}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <th>제목</th>
                        <td colspan="3"><input type="text" id="title" class="form-control" value="<c:out value="${questionDetail.qstTitle}"/>"/></td>
                    </tr>
                    <tr>
                        <th>참여기간</th>
                        <td>
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" id="startime" name="start" value="<c:out value="${questionDetail.qstStDt}"/>"/>
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" id="endtime" name="end" value="<c:out value="${questionDetail.qstEdDt}"/>"/>
                            </div>
                            <script type="text/javascript">
                            $('#starttime').datepicker({
                                format: "yyyy-mm-dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#endtime').datepicker({
                                format: "yyyy-mm-dd",
                                language: "ko",
                                autoclose: true
                            });
                            </script>
                        </td>
                        <th>상태</th>
                        <td><c:out value="${questionDetail.qstStsCdNm}"/></td>
                    </tr>
                    <tr>
                        <th>설문유형</th>
                        <td colspan="3">
                            <select name="" id="choice" class="custom-select w250" onchange="change_select(this);">
                                <option value="null">선택</option>
                                <c:choose>
                                    <c:when test="${questionDetail.qstTpCd == '1'}">
                                        <option value="choice2" selected>2지선다</option>
                                        <option value="choice3">3지선다</option>
                                        <option value="choice4">4지선다</option>
                                        <option value="choice2p">2지선다 + 기타</option>
                                        <option value="choice3p">3지선다 + 기타</option>
                                        <option value="choice4p">4지선다 + 기타</option>
                                    </c:when>
                                    <c:when test="${questionDetail.qstTpCd == '2'}">
                                        <option value="choice2">2지선다</option>
                                        <option value="choice3">3지선다</option>
                                        <option value="choice4">4지선다</option>
                                        <option value="choice2p" selected>2지선다 + 기타</option>
                                        <option value="choice3p">3지선다 + 기타</option>
                                        <option value="choice4p">4지선다 + 기타</option>
                                    </c:when>
                                    <c:when test="${questionDetail.qstTpCd == '3'}">
                                        <option value="choice2">2지선다</option>
                                        <option value="choice3">3지선다</option>
                                        <option value="choice4">4지선다</option>
                                        <option value="choice2p">2지선다 + 기타</option>
                                        <option value="choice3p" selected>3지선다 + 기타</option>
                                        <option value="choice4p">4지선다 + 기타</option>
                                    </c:when>
                                    <c:when test="${questionDetail.qstTpCd == '4'}">
                                        <option value="choice2">2지선다</option>
                                        <option value="choice3">3지선다</option>
                                        <option value="choice4">4지선다</option>
                                        <option value="choice2p">2지선다 + 기타</option>
                                        <option value="choice3p" selected>3지선다 + 기타</option>
                                        <option value="choice4p">4지선다 + 기타</option>
                                    </c:when>
                                    <c:when test="${questionDetail.qstTpCd == '5'}">
                                        <option value="choice2">2지선다</option>
                                        <option value="choice3">3지선다</option>
                                        <option value="choice4" selected>4지선다</option>
                                        <option value="choice2p">2지선다 + 기타</option>
                                        <option value="choice3p">3지선다 + 기타</option>
                                        <option value="choice4p">4지선다 + 기타</option>
                                    </c:when>
                                    <c:when test="${questionDetail.qstTpCd == '6'}">
                                        <option value="choice2">2지선다</option>
                                        <option value="choice3">3지선다</option>
                                        <option value="choice4">4지선다</option>
                                        <option value="choice2p">2지선다 + 기타</option>
                                        <option value="choice3p">3지선다 + 기타</option>
                                        <option value="choice4p" selected>4지선다 + 기타</option>
                                    </c:when>
                                </c:choose>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td colspan="3">
                            <div><input type="text" id="subtitle" class="form-control"/></div>
                            <div class="mt10" id="input_area">
                                <c:forEach items="${questionItemList}" var="list" varStatus="status">
                                    <c:choose>
                                        <c:when test="${list.qstItmAnsrCont != '기타의견'}">
                                            <div class='depth2_tr'>
                                                <div class='th w30'><c:out value="${status.count}"/>.</div>
                                                <div class='td'>
                                                    <input type='text' name='cont' class='form-control' value="<c:out value="${list.qstItmAnsrCont}"/>"/>
                                                    <input type='text' name='itmno' class='form-control' value="<c:out value="${list.qstItmNo}"/>" style="display:none;"/>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class='depth2_tr'><div class='th w30'>${status.count}.</div><div class='td'>기타의견</div></div>
                                            <input type='text' id="etc_itmno" class='form-control' value="<c:out value="${list.qstItmNo}"/>" style="display:none;"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="back();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="temp_save_btn();">저장</button>
            </div>
        </div>

    </div>

</div>


<form:form id="form_edit" action="/cm/community/editQuestionAction" name="edit" commandName="edit" method="post">
    <input type="text" id="qstNo" name="qstNo" value="<c:out value="${questionDetail.qstNo}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstTitle" name="qstTitle" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstStDt" name="qstStDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstEdDt" name="qstEdDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstTpCd" name="qstTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstItmQuestCont" name="qstItmQuestCont" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstItmAnsrContList" name="qstItmAnsrContList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="isQuestionDetailChange" name="isQuestionDetailChange" value="N" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="newQstItmAnsrContJsonArray" name="newQstItmAnsrContJsonArray" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${questionDetail.houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>