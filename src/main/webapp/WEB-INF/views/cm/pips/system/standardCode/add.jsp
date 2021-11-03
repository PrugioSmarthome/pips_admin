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
                "url":"/cm/system/standardCode/groupStandardCodeNameList",
                "dataSrc":""
                },
                columns:[
                    {"data":"commCdGrpNm",
                        "render":function(data, type, row, meta){
                                var commCdGrpCd = row['commCdGrpCd'];
                                return row["commCdGrpNm"]+"("+commCdGrpCd+")";
                        }
                    },
                    {"data":"commCdGrpCd",
                            "render":function(data, type, row, meta){
                                var nm = row['commCdGrpNm'];
                                return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"#"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                            }
                    }
                ]
        });


    });

    //취소버튼
    function cancel(){
        window.history.back();
    }

    //공통코드목록 팝업에서 공통코드를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("#");
        $("#commCdGrpCd").val(strarray[0]);
        $("#commCdGrpNm").val(strarray[1]);
        $("#commCdGrpNm_").val(strarray[1]+"("+strarray[0]+")");

        $("#closebtn").click();
    }

    function formsubmit(){

        if($("#commCdGrpCd").val() == null || $("#commCdGrpCd").val() == ""){
            alert("그룹코드를 선택해주세요.");
            return;
        }
        if($("#commCd_").val() == null || $("#commCd_").val() == ""){
            alert("공통코드를 입력해주세요.");
            return;
        }

        var commCdGrpCd = $("#commCdGrpCd").val();
        var commCd = $("#commCd_").val();
        var commCdNm = $("#commCdNm_").val();
        var rem = $("#rem_").val();

        var flag = "false";
        $.ajax({
             url: '/cm/system/standardCode/checkStandardCode',
             type: 'POST',
             data: {"commCdGrpCd": commCdGrpCd,
                    "commCd": commCd},
             dataType : "text",
             async: false,
             success: function(data){
                 if(data == "true"){
                    alert("이미 등록된 공통코드입니다.");
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

        $("#commCdGrpCd").val(commCdGrpCd);
        $("#commCd").val(commCd);
        $("#commCdNm").val(commCdNm);
        $("#rem").val(rem);

        $("#form_add").submit();

    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">공통코드 등록</h2>
            <ul class="location">
                <li>시스템관리</li>
                <li>공통코드 관리</li>
                <li>공통코드 등록</li>
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
                        <th>그룹코드명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" id="commCdGrpNm_" class="form-control" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1">그룹코드선택</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>공통코드</th>
                        <td>
                            <input type="text" name="commCd_" id="commCd_" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>공통코드명</th>
                        <td>
                            <input type="text" name="commCdNm_" id="commCdNm_" class="form-control" />
                        </td>
                    </tr>
                    <tr>
                        <th>공통 코드 설명</th>
                        <td>
                            <input type="text" name="rem_" id="rem_" class="form-control" />
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

<form:form id="form_add" action="/cm/system/standardCode/addStandardCode" name="addGroupCode" commandName="addGroupCode" method="post">
      <input type="text" id="commCdGrpNm" name="commCdGrpNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="commCdGrpCd" name="commCdGrpCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="commCdNm" name="commCdNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="commCd" name="commCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="rem" name="rem" style="width:0;height:0;visibility:hidden"/>
</form:form>

    <!-- 그룹코드 선택 팝업 -->
    <div class="modal fade" id="modal1" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered " role="document">
            <div class="modal-content">
                <!-- 모달헤더 -->
                <div class="modal-header">
                    <h5 class="modal-title">그룹코드 선택</h5>
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
