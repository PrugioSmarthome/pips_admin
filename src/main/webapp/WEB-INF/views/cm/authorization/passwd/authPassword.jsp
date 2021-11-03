<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    #list_table_filter{
        visibility:hidden;
        width:0;
        height:0;
    }
</style>
<script type="text/javascript">
    var msg_check = "false";
    var msg_check2 = "false";
    var timestop;
    var isAuthcheck;
    $(document).ready(function(){
        $('#list_table').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            ajax:{
                "url":"/cm/common/housingcplx/list",
                "dataSrc":""
                },
                columns:[
                    {"data":"houscplxNm"},
                    {"data":"houscplxCd",
                            "render":function(data, type, row){
                                var nm = row['houscplxNm'];
                                return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                            }
                    }
                ]
        });
    });


    //단지 선택 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#houscplxCd").val(strarray[0]);
        $("#closebtn").click();
    }

    function telauth(){
        if(isValid() == false){
            return;
        }

        authPasswordAction();
    }

    function authPasswordAction (){
        var param = new Object();
        param.userId = $("#userId").val();
        param.houscplxCd = $("#houscplxCd").val();
        param.telNo = $("#telNo").val();
        param.userGroupName = $("#groupName").val();
        param.useLanguage = 'KR-ko';

        $.ajax({
            url:  '/cm/authorization/passwd/getAuthAction',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){

                var dataInfo = JSON.stringify(data);
                dataInfo = JSON.parse(dataInfo);

                if (dataInfo == undefined) {
                    alert("에러가 발생하였습니다. 다시 시도해주세요.");
                    return false;
                }

                if (dataInfo.status == true) {
                    console.log("status is true");
                    alert("인증번호를 전송했습니다.");
                    msg_check = "true";
                    msg_check2 = "true";
                    $("#callbtn").val("인증번호 재전송");
                    //3분 타이머 함수 호출
                    countdown( "countdown", 3, 0);

                } else if (dataInfo.status == false) {
                    if($("#groupName").val() == "COMPLEX_ADMIN"){
                        alert(dataInfo.params[0].msg);
                    }else{
                        alert("시스템관리자 아이디가 아닙니다.");
                    }

                }

                console.log(dataInfo.params);
            },
            error: function(e){
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }


    //타이머
    function countdown( elementName, minutes, seconds )
    {
        var element, endTime, hours, mins, msLeft, time;

        function twoDigits( n )
        {
            return (n <= 9 ? "0" + n : n);
        }

        function updateTimer()
        {
            msLeft = endTime - (+new Date);
            if ( msLeft < 1000 ) {
                alert("입력시간 초과 되었습니다. ‘인증번호 재전송’ 버튼을 눌러주세요");
                msg_check = "false";
            } else {
                time = new Date( msLeft );
                hours = time.getUTCHours();
                mins = time.getUTCMinutes();
                element.innerHTML = (hours ? hours + ':' + twoDigits( mins ) : mins) + ':' + twoDigits( time.getUTCSeconds() );
                timestop = setTimeout( updateTimer, time.getUTCMilliseconds() + 500 );
            }
        }

        element = document.getElementById( elementName );
        endTime = (+new Date) + 1000 * (60*minutes + seconds) + 500;
        updateTimer();
    }



    function isValid(){
        if($("#userId").val() == ""){
            alert("아이디가 없습니다.");
            return false;
        }

        if($("#phonenum1").val() == "" || $("#phonenum2").val() == ""){
            alert("전화번호가 없습니다.");
            return false;
        }else{
            var tel = $("#selectphonenum").val()+$("#phonenum1").val()+$("#phonenum2").val();
            $("#telNo").val(tel);
        }
        return true;
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }
    //확인버튼
    function Okbtn(){
        if(msg_check2 == "false"){
            alert("입력하신 아이디, 단지명과 일치하는 회원정보가  없습니다. 확인 후 다시 시도해 주세요");
            return;
        }
        if(msg_check == "false"){
            alert("휴대폰 본인확인이 완료되지 않았습니다. 휴대폰 본인확인을 진행해 주세요.");
            return;
        }
        $("#authPassword").submit();
    }

    //인증번호 확인
    function numcheck(){
        if($("#authCode").val() == "" || $("#authCode").val().length < 6){
            alert("휴대폰 문자로 전송된 인증번호 6자리를 입력해 주세요");
            return;
        }
        if(msg_check == "false"){
            alert("입력시간 초과 되었습니다. ‘인증번호 재전송’ 버튼을 눌러주세요");
            return;
        }

        var param = new Object();
        param.authCode = $("#authCode").val();
        param.userId = $("#userId").val();
        param.houscplxCd = $("#houscplxCd").val();
        param.telNo = $("#telNo").val();
        param.userGroupName = $("#groupName").val();

        param.useLanguage = 'KR-ko';

        $.ajax({
            url:  '/cm/authorization/passwd/verifyAuthAction',
            type: 'POST',
            data: param,

            success: function(data){
                var dataInfo = JSON.stringify(data);
                dataInfo = JSON.parse(dataInfo);

                if (dataInfo == undefined) {
                    alert("에러가 발생하였습니다. 다시 시도해주세요.");
                    return false;
                }

                if (dataInfo.status == true) {
                    msg_check = "true";
                    document.getElementById("callbtn").disabled = true;
                    document.getElementById("phonenum1").disabled = true;
                    document.getElementById("phonenum2").disabled = true;
                    document.getElementById("selectphonenum").disabled = true;
                    document.getElementById("authCodecheck").disabled = true;
                    document.getElementById("authCode").disabled = true;
                    clearTimeout(timestop);
                    document.getElementById("countdown").innerHTML = "";
                    alert("인증되었습니다.");
                } else if (dataInfo.status == false) {
                    console.log("인증번호가 다릅니다 다시 입력해주세요.");
                }
            },
            error: function(e){
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }

    function change_action(e){
        if($(e).val() == "SYSTEM_ADMIN" || $(e).val() == "SUB_SYSTEM_ADMIN"){
            var li_style = document.getElementById("user_check");
            li_style.style.display = "none";
        }
        if($(e).val() == "COMPLEX_ADMIN"){
            var li_style = document.getElementById("user_check");
            li_style.style.display = "";
        }
    }

</script>

<form:form action="/cm/authorization/passwd/authPasswordAction" name="authPassword" commandName="authPassword" method="post">
    <div class="login_wrap">
        <div class="logo">
            <div><img src="/images/h1_logo.png" alt="PRUGIO" /></div>
            <div>스마트홈 플랫폼 앱 Admin</div>
        </div>
        <div class="in pt100">
            <h1 class="tit">비밀번호 재설정</h1>
            <ul class="inp_list">
                <li>
                    <h2 class="tit">사용자그룹선택</h2>
                    <div class="inp">
                        <div class="input-group">
                            <div class="w220">
                                <select name="" id="groupName" class="custom-select" onchange="change_action(this);">
                                    <c:forEach items="${userGroupList}" var="list" varStatus="status">
                                        <option value="${list.userGroupName}">${list.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </li>
                <li>
                    <h2 class="tit">아이디</h2>
                    <div class="inp"><input type="text" class="form-control" id="userId" name="userId" value="${authPassword.userId}"/></div>
                </li>
                <li id="user_check" style="display:none">
                    <h2 class="tit">단지명</h2>
                    <div class="inp">
                        <div class="input-group">
                            <input type="text" class="form-control" disabled id="houscplxNm" name="houscplxNm" />
                            <input type="button" class="btn btn-gray btn-sm ml10" value="단지선택" data-toggle="modal" data-target="#modal1"/>
                            <input type="text" id="houscplxCd" name="houscplxCd" value="${authPassword.houscplxCd}" style="width:0;height:0;visibility:hidden"/>
                        </div>
                    </div>
                </li>
                <li>
                    <h2 class="tit">휴대폰 본인확인</h2>
                    <div class="inp">
                        <div class="input-group">
                            <div class="w70">
                                <select id="selectphonenum" name="selectphonenum" class="custom-select">
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                    <option value="016">016</option>
                                    <option value="017">017</option>
                                    <option value="019">019</option>
                                </select>
                            </div>
                            <span class="bul_space_sm">-</span>
                            <input type="text" class="form-control" id="phonenum1" name="phonenum1" type="tel" maxlength="4"/>
                            <span class="bul_space_sm">-</span>
                            <input type="text" class="form-control" id="phonenum2" name="phonenum2" type="tel" maxlength="4"/>
                            <input type="button" class="btn btn-sm2 btn-bluegreen ml10" id="callbtn" value="인증번호 전송" onclick="telauth();"/>
                            <input id="telNo" name="telNo" value="${authPassword.telNo}" style="visibility:hidden"/>
                        </div>
                    </div>
                    <div class="inp">
                        <div class="input-group">
                            <input type="text" class="form-control" id="authCode" name="authCode" value="${authPassword.authCode}"/>
                            <input type="button"id="authCodecheck" class="btn btn-sm2 btn-bluegreen ml10" onclick="numcheck();" value="인증번호 확인"/>
                        </div>
                        <label id="countdown" class="time"></label>
                        <div class="txt_noti2">
                            1. 관리자정보에 등록하신 휴대폰 번호를 입력 후 인증번호 전송<br />2. 수신한 인증번호를 입력 후 인증번호 확인
                        </div>
                    </div>
                </li>
            </ul>
            <div class="btn_area clearfix">
                <div class="float-right">
                    <input type="button" onClick="location.href='/cm/login'" class="btn btn-gray" value="취소"/>
                    <input type="button" class="btn btn-bluegreen" onclick="Okbtn();" value="확인"/>
                </div>
            </div>
        </div>
    </div>
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
                    <!-- 검색영역 -->
                    <div class="search_area">
                        <table>
                            <colgroup>
                                <col style="width:70px"/>
                                <col />
                                <col style="width:95px"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>· 검색어</th>
                                    <td><input type="text" class="form-control" id="search_text"/></td>
                                    <td class="text-right"><button type="button" id="search_btn" onclick="list_search();" class="btn btn-brown btn-sm">검색</button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //검색영역 -->

                    <!-- 테이블상단 -->
                    <div class="tbl_top_info mt-4">

                    </div>
                    <!-- //테이블상단 -->

                    <!-- 테이블 -->
                    <div class="table_wrap mt-3">
                        <table class="table" id="list_table" style="width:100%;text-align:center;">
                            <thead>
                                <tr>
                                    <th scope="col">항목</th>
                                    <th scope="col">선택</th>
                                </tr>
                            </thead>

                        </table>
                    </div>
                    <!-- //테이블 -->
                </div>
                <!-- //모달바디 -->
            </div>
        </div>
    </div>