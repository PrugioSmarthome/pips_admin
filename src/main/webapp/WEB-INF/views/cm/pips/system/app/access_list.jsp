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
        initLoadTable();
    });

    function initLoadTable() {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
        var yyyy = today.getFullYear();

        if(dd < 10) {
            dd='0'+dd
        }
        if(mm < 10) {
            mm='0'+mm
        }

        $("#startCrDt").val(yyyy + "." + mm + "." + dd);
        $("#endCrDt").val(yyyy + "." + mm + "." + dd);
        $("#userTpCd").val("ALL");

        $('#table1').dataTable({
            "order": [],
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            "ajax" : {
                "url":"ajax/list",
                "type":"POST",
                "data": function(data) {
                    var sendData = new Object();
                    sendData.startCrDt = $("#startCrDt").val().replace(/\./gi,'');
                    sendData.endCrDt = $("#endCrDt").val().replace(/\./gi,'');
                    sendData.userTpCd = $("#userTpCd").val();
                    sendData.userId = $("#userId").val();
                    sendData.houscplxCd = $("#houscplxCd").val();

                    $.extend(sendData, data);
                    return sendData;
                }
            },
            "serverSide": true,
            "paging": true,
            "pageLength": 10,
            "processing": true,
            "columns" : [
                {data: "crDt"},
                {data: "loginYn"},
                {data: "userId"},
                {data: "userTpCd"},
                {data: "houscplxNm"},
                {data: "dongNo"},
                {data: "hoseNo"}
            ],
            "columnDefs" : [{
                "targets": 0,
                "className": "text-center",
                "render": function(data) {
                    console.log("data : ", data);
                    var year = data.substr(0, 4);
                    var month = data.substr(4, 2);
                    var day = data.substr(6, 2);
                    var hh = data.substr(8, 2);
                    var mm = data.substr(10, 2);
                    var ss = data.substr(12, 2);

                    return year + "." + month + "." + day + " " + hh + ":" + mm + ":" + ss;
                }
            }, {
                "targets": 1,
                "className": "text-center",
                "render": function(data) {
                    if (data == 'Y')
                        return "로그인";
                    else
                        return "로그아웃";
                }
            }, {
                "targets": [2, 3, 4, 5, 6],
                "className": "text-center"
            }]
        });
    }

    //단지선택 버튼 클릭시 단지리스트 팝업
    function houscplxNm_popup(){
        //Ajax 들어갈 부분
        $.ajax({
                url: '/cm/common/housingcplx/list',
                type: 'POST',
                dataType : "json",
                success: function(data){
                $("#householdDeviceList").empty();
                $.each(data, function(i, item){
                    $("#householdDeviceList").append("<tr><td class='text-center'>"+item.houscplxNm+"</td><td class='text-center'><input class='btn btn-gray btn-sm' type='button' id='"+item.houscplxCd+"_"+item.houscplxNm+"' value='선택' onclick='selectbtn(this)'/></td></tr>");
                })

                },
                error: function(e){
                    console.log("에러");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");

        console.log("str : ", str);

        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#houscplxCd").val(strarray[0]);

        $("#closebtn").click();

    }
    //검색버튼을 눌렀을경우
    function btn_search(){
        $('#table1').DataTable().ajax.reload();
    }

    //검색버튼 눌렀을경우 유효성 체크
    function isValid(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var userid = $("#userId").val();
        if($("#houscplxNm").val() == ""){
            alert("단지명을 선택해주세요.");
            return false;
        }
        if(RegExp.test(userid) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return false;
        }

        return true;
    }

    function excel(){
        var list = new Array();
        var resultList = $('#table1').DataTable().rows().data();

        for (var i = 0; i < resultList.length; i++) {
            var crDt = resultList[i].crDt;
            var year = crDt.substr(0, 4);
            var month = crDt.substr(4, 2);
            var day = crDt.substr(6, 2);
            var hh = crDt.substr(8, 2);
            var mm = crDt.substr(10, 2);
            var ss = crDt.substr(12, 2);

            var cJson = new Object();
            cJson.접근일시 = year + "." + month + "." + day + " " + hh + ":" + mm + ":" + ss;

            if (resultList[i].loginYn == 'Y') {
                cJson.구분 = '로그인';
            } else {
                cJson.구분 = '로그아웃';
            }
            cJson.아이디 = resultList[i].userId;
            cJson.유형 = resultList[i].userTpCd;
            if (resultList[i].houscplxNm == null) {
                cJson.단지명 = '';
            } else {
                cJson.단지명 = resultList[i].houscplxNm;
            }

            if (resultList[i].dongNo == null) {
                cJson.동 = '';
            } else {
                cJson.동 = resultList[i].dongNo;
            }

            if (resultList[i].hoseNo == null) {
                cJson.호 = '';
            } else {
                cJson.호 = resultList[i].hoseNo;
            }

            list.push(cJson);
        }

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "사용자앱 접속이력 목록";
        param.tableHeader = "['접근일시', '구분', '아이디', '유형', '단지명', '동', '호']";
        param.tableData = hJson;

        var date = new Date();
        var year = date.getFullYear();
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
          month = "0" + month;
        }
        if(day.length == 1){
          day = "0" + day;
        }
        var today = year + "" + month + "" + day;

        $.ajax({
            url: '/cm/common/excel/download',
            type: 'POST',
            data: param,
            traditional: true,
            xhrFields: {
                responseType: 'blob'
            },
            success: function(blob){
                console.log(blob.size);
                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_사용자앱 접속이력 목록.xlsx";
                link.click();
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">사용자앱 접속이력 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>사용자 접속이력</li>
                <li>사용자앱 접속이력 목록</li>
            </ul>
        </div>
        <form name="searchForm">
        <input type="hidden" id="houscplxCd" name="houscplxCd" />
        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 접근일시</th>
                        <td style="width:350px">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="startCrDt" id="startCrDt" value="<c:out value="${startCrDt}"/>"/>
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" name="endCrDt" id="endCrDt" value="<c:out value="${endCrDt}"/>"/>
                            </div>
                            <script type="text/javascript">
                            $('#startCrDt').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#endCrDt').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            </script>
                        </td>
                        <th>· 아이디</th>
                        <td>
                            <input type="text" id="userId" name="userId" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="houscplxNm" name="houscplxNm" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" onclick="houscplxNm_popup();">단지선택</button>
                                </div>
                            </div>
                        </td>
                        <th>· 유형</th>
                        <td>
                            <select name="userTpCd" id="userTpCd" class="custom-select">
                                <option value="ALL" <c:if test="${userTpCd eq 'ALL'}">selected</c:if>>전체</option>
                                <option value="RESIDENT" <c:if test="${userTpCd eq 'RESIDENT'}">selected</c:if>>입주자</option>
                                <option value="NORMAL" <c:if test="${userTpCd eq 'NORMAL'}">selected</c:if>>일반회원</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </form>

        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">접근일시</th>
                        <th scope="col">구분</th>
                        <th scope="col">아이디</th>
                        <th scope="col">유형</th>
                        <th scope="col">단지명</th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
        </div>

    </div>
</div>

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
                    <div class="total"></div>
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
                        <tbody id="householdDeviceList">
                        </tbody>
                    </table>
                </div>
                <!-- //테이블 -->
            </div>
            <!-- //모달바디 -->
        </div>
    </div>
</div>