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
                "url":"/cm/common/housingcplx/listNotAll",
                "dataSrc":""
                },
                columns:[
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            return row["houscplxNm"];
                        }
                    },
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                var nm = row['houscplxNm'];
                                return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                            }
                    }
                ]
        });

        $('#list_table2').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            ajax:{
                "url":"/cm/system/manufacturerByDevice/deviceMfList",
                "dataSrc":""
                },
                columns:[
                    {"data":"commCdNm",
                        "render":function(data, type, row, meta){
                                return row["commCdNm"];
                        }
                    },
                    {"data":"commCd",
                            "render":function(data, type, row, meta){
                                var nm = row['commCdNm'];
                                return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn2(this)'/>";
                            }
                    }
                ]
        });

    });

    //취소버튼
    function cancel(){
        window.history.back();
    }

    function formsubmit(){

        if($("#houscplxCd").val() == null || $("#houscplxCd").val() == ""){
            alert("단지를 선택해주세요.");
            return;
        }
        if($("#deviceTpCd_").val() == "all"){
            alert("장치 종류를 선택해주세요.");
            return;
        }
        if($("#deviceMfCd").val() == null || $("#deviceMfCd").val() == ""){
            alert("장치 제조사를 선택해주세요.");
            return;
        }

        var houscplxCd = $("#houscplxCd").val();
        var deviceTpCd = $("#deviceTpCd_").val();
        var deviceMfCd = $("#deviceMfCd").val();
        var flag = "false";
        $.ajax({
             url: '/cm/system/manufacturerByDevice/checkDeviceMf',
             type: 'POST',
             data: {"houscplxCd": houscplxCd,
                    "deviceTpCd": deviceTpCd,
                    "deviceMfCd": deviceMfCd
                   },
             dataType : "text",
             async: false,
             success: function(data){
                 if(data == "true"){
                    alert("이미 등록된 제조사입니다.");
                    flag = "true";
                 }
             },
             error: function(e){
                 console.log("에러");
                 console.log(e.responseText.trim());
             },
             complete: function() {
             }
        });
        if(flag == "true"){
            return;
        }

        $("#deviceTpCd").val($("#deviceTpCd_").val());

        $("#form_add").submit();
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //모달 장치제조사 리스트 검색
    function list_search2(){
        var text = $("#search_text2").val();
        $('#list_table2').DataTable().search(text).draw();
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#houscplxCd").val("");
        }else{
            $("#houscplxCd").val(strarray[0]);
        }

        $("#closebtn").click();
    }

    //장치제조사목록 팝업에서 장치제조사를 선택했을경우
    function selectbtn2(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#deviceMfCd").val(strarray[0]);
        $("#deviceMfNm").val(strarray[1]);

        $("#closebtn2").click();
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">장치별 제조사 등록</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>장치별 제조사 관리</li>
                <li>장치별 제조사 등록</li>
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
                        <th>단지</th>
                        <td>
                            <div class="input-group">
                                <input type="text" id="houscplxNm" class="form-control" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>장치 종류</th>
                        <td>
                            <select name="deviceTpCd_" id="deviceTpCd_" class="custom-select">
                                <option value="all">선택</option>
                                <option value="LIGHTS">조명</option>
                                <option value="HEATING">난방</option>
                                <option value="AIRCON">에어컨</option>
                                <option value="VENTILATOR">환기</option>
                                <option value="CURTAIN">전동 커튼</option>
                                <option value="SMART_CONSENT">대기전력</option>
                                <option value="GASLOCK">가스밸브</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>장치 제조사</th>
                        <td>
                            <div class="input-group">
                                <input type="text" id="deviceMfNm" class="form-control" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal2" >장치제조사 선택</button>
                                </div>
                            </div>
                        </td>
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

<form:form id="form_add" action="/cm/system/manufacturerByDevice/addManufacturerByDevice" name="addManufacturerByDevice" commandName="addManufacturerByDevice" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="deviceTpCd" name="deviceTpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="deviceMfCd" name="deviceMfCd" style="width:0;height:0;visibility:hidden"/>
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

    <!-- 장치제조사 선택 팝업 -->
    <div class="modal fade" id="modal2" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered " role="document">
            <div class="modal-content">
                <!-- 모달헤더 -->
                <div class="modal-header">
                    <h5 class="modal-title">장치제조사 선택</h5>
                    <button type="button" id="closebtn2" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
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
                                    <td><input type="text" class="form-control" id="search_text2"/></td>
                                    <td class="text-right"><button type="button" id="search_btn2" onclick="list_search2();" class="btn btn-brown btn-sm">검색</button></td>
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
                        <table class="table" id="list_table2" style="width:100%;text-align:center;">
                            <thead>
                                <tr>
                                    <th scope="col">항목</th>
                                    <th scope="col">선택</th>
                                </tr>
                            </thead>
                            <tbody id="householdDeviceList2">
                            </tbody>
                        </table>
                    </div>
                    <!-- //테이블 -->
                </div>
                <!-- //모달바디 -->
            </div>
        </div>
    </div>