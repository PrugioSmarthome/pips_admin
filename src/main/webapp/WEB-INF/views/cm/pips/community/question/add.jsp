<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    .dataTables_length {
            float: right !important;
    }
</style>
<script type="text/javascript">
    $(document).ready(function(){

        var userId = '<c:out value="${userId}"/>';
        $('#list_table').DataTable({
            "order": [],
            "bLengthChange": true,
            "lengthMenu": [10, 30, 50, 100],
            "pageLength" : 30,
            "dom": 'iltp',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다.",
                "lengthMenu" : "_MENU_ 개씩 보기"
            },
            ajax:{
                "url":"/cm/common/housingcplx/multiList",
                "dataSrc":"",
                "data":{"userId" : userId,
                        "noAll": "Y"}
                },
                columns:[
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                var nm = row['houscplxNm'];
                                return "<div class='custom-control custom-checkbox'><input type='checkbox' name='box_chk' class='custom-control-input' id='"+data+"_"+nm+"' value='"+data+"_"+nm+"'><label class='custom-control-label' for='"+data+"_"+nm+"'></label></div>";
                            }
                    },
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            return row["houscplxNm"];
                        }
                    }
                ]
        });

        $("#list_table").css("width","100%")

    });

    function change_select(e){
        var ch = $(e).val();
        if(ch == "choice2"){inputadd(2,"N");}
        if(ch == "choice3"){inputadd(3,"N");}
        if(ch == "choice4"){inputadd(4,"N");}
        if(ch == "choice2p"){inputadd(2,"Y");}
        if(ch == "choice3p"){inputadd(3,"Y");}
        if(ch == "choice4p"){inputadd(4,"Y");}
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

    function temp_save_btn(e){
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

                    qstArray.push(jsonObject);
                }
            }
            if($("#choice").val() == "choice2p" || $("#choice").val() == "choice3p" || $("#choice").val() == "choice4p"){
                var jsonObject = new Object();
                jsonObject.qstItmAnsrCont = "기타의견";
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
        if($("#multiYn").val() == "N"){
            alert("적용할 단지를 선택해주세요.");
            return;
        }

        $("#qstTitle").val(title);
        $("#qstStDt").val($("#startime").val().replace(/\./gi,''));
        $("#qstEdDt").val($("#endtime").val().replace(/\./gi,''));
        $("#qstItmQuestCont").val(subtitle);
        $("#qstItmAnsrContList").val(qstItmAnsrContList);
        if($("#choice").val() == "choice2"){$("#qstTpCd").val("1");}
        if($("#choice").val() == "choice3"){$("#qstTpCd").val("3");}
        if($("#choice").val() == "choice4"){$("#qstTpCd").val("5");}
        if($("#choice").val() == "choice2p"){$("#qstTpCd").val("2");}
        if($("#choice").val() == "choice3p"){$("#qstTpCd").val("4");}
        if($("#choice").val() == "choice4p"){$("#qstTpCd").val("6");}
        $("#qstStsCd").val(e);
        $("#form_add").submit();

    }

    //단지선택창 열기
    function danjiSelect(){
        $("#modal1").modal();
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('box_chk');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
            }
        }
    }

    //단지선택
    function danjiCheck(){
        var checkArray = new Array();
        var danjiArray = new Array();
        $("input[name=box_chk]:checked").each(function() {
            var val = $(this).val().split("_");
            checkArray.push(val[1]);
            danjiArray.push(val[0]);
        })

        if(checkArray[0] == null){
            alert("단지를 선택해주세요.");
            return;
        }

        var result = "<table id='danjiListTable' class='table' style='text-align:center;'><thead><tr><th style='text-align:center;'>관리 단지명</th></tr></thead><tbody>";
        for(var i=0;i<checkArray.length;i++){
            result += "<tr><td style='text-align:center;'>"+checkArray[i]+"</td></tr>";
        }
        result += "</tbody></table>";

        $("#danjiList").empty();
        $("#danjiList").append(result);

        $("#multiYn").val("Y");
        $("#danjiArray").val(danjiArray);

        $("#modal1").modal('hide');
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">설문조사 신규등록</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>설문조사 관리</li>
                <li>설문조사 목록</li>
                <li>설문조사 신규등록</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>제목</th>
                        <td><input type="text" id="title" class="form-control" /></td>
                    </tr>
                    <tr>
                        <th>참여기간</th>
                        <td>
                            <div class="input-daterange input-group w450" id="datepicker">
                                <input type="text" class="form-control inp_calendar" id="startime" name="start" />
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" id="endtime" name="end" />
                            </div>
                            <script type="text/javascript">
                                $('#startime').datepicker({
                                    format: "yyyy.mm.dd",
                                    language: "ko",
                                    autoclose: true
                                });
                                $('#endtime').datepicker({
                                    format: "yyyy.mm.dd",
                                    language: "ko",
                                    autoclose: true
                                });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <th>설문유형</th>
                        <td>
                            <select name="" id="choice" class="custom-select w250" onchange="change_select(this);">
                                <option value="null">선택</option>
                                <option value="choice2">2지선다</option>
                                <option value="choice3">3지선다</option>
                                <option value="choice4">4지선다</option>
                                <option value="choice2p">2지선다 + 기타</option>
                                <option value="choice3p">3지선다 + 기타</option>
                                <option value="choice4p">4지선다 + 기타</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td>
                            <div><input type="text" id="subtitle" class="form-control" placeholder="설문명을 입력해주세요."/></div>
                            <div class="mt10" id="input_area">

                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
            <br/>
            <button style="width:920px;" class="btn btn-gray btn-sm" type="button" data-toggle="modal" onclick="javascript:danjiSelect();">단지선택</button>
            <div id="danjiList" style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;">
            </div>
            <input type="text" name="multiYn" id="multiYn" value="N" style="width:0;height:0;visibility:hidden"/>
        </c:if>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button">취소</button>
                <button class="btn btn-gray" type="button" onclick="temp_save_btn('1');">임시저장</button>
                <button class="btn btn-bluegreen" type="button" onclick="temp_save_btn('2');">설문게시</button>
            </div>
        </div>

    </div>

</div>


<form:form id="form_add" action="/cm/community/addQuestionAction" name="report" commandName="report" method="post">
    <input type="text" id="qstStsCd" name="qstStsCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstTitle" name="qstTitle" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstStDt" name="qstStDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstEdDt" name="qstEdDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstTpCd" name="qstTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstItmQuestCont" name="qstItmQuestCont" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="qstItmAnsrContList" name="qstItmAnsrContList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="danjiArray" name="danjiArray" style="width:0;height:0;visibility:hidden"/>
</form:form>


<!-- 단지선택 팝업 -->
<div class="modal fade" id="modal1" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered " role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">단지선택</h5>
                <button type="button" id="closebtn" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

            <!-- 모달바디 -->
            <div class="modal-body">
            <div class="container" style="width:518px; height:422px">
            <div class="row" style="width:518px; height:422px">
            <div class="col-lg-12 col-sm-12 col-11 main-section" style="width:518px; height:422px">
                <!-- 검색영역 -->
                <div class="search_area" style="width:488px; height:98px">
                    <table>
                        <colgroup>
                            <col style="width:70px"/>
                            <col />
                            <col style="width:95px"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>검색어</th>
                                <td><input type="text" class="form-control" id="search_text"/></td>
                                <td class="text-right"><button type="button" id="search_btn" onclick="list_search();" class="btn btn-brown btn-sm">검색</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //검색영역 -->

                <!-- 테이블상단 -->
                <div class="tbl_top_info mt-4" style="width:488px;">
                    <div class="total"></div>
                </div>
                <!-- //테이블상단 -->

                <!-- 테이블 -->
                <div style="width: 488px; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap mt-3">
                    <table class="table" id="list_table" style="text-align:center;">
                        <thead>
                            <tr>
                                <th scope="col" style="text-align:center;">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="checkAll" onclick="javascript:checkall();">
                                        <label class="custom-control-label" for="checkAll"></label>
                                    </div>
                                </th>
                                <th scope="col" style="text-align:center;">항목</th>
                            </tr>
                        </thead>
                        <tbody id="householdDeviceList">
                        </tbody>
                    </table>
                </div>
                <!-- //테이블 -->
            </div>
            </div>
            </div>
            </div>
            <!-- //모달바디 -->
        <button class="btn btn-gray" type="button" onclick="javascript:danjiCheck()">선택완료</button>
        </div>
    </div>
</div>