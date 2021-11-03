<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">

    var manufacturerByDeviceArray = new Array();

    $(document).on('change', 'input[name="check"]', function(){
        if($(this).is(":checked")){
            var val = $(this).val();
            manufacturerByDeviceArray.push(val);
        } else{
            var val = $(this).val();
            for(let i = 0; i < manufacturerByDeviceArray.length; i++) {
              if(manufacturerByDeviceArray[i] == val) {
                manufacturerByDeviceArray.splice(i, 1);
                i--;
              }
            }
        }
    });

    $(document).ready(function(){
        $('#table1').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            "columnDefs": [
                { "width": "6%", "targets": 0 },
                { "width": "20%", "targets": 1 },
                { "orderable": false, "targets": 0 }
            ]
        });

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
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            if(meta.row == 0){
                                return "전체";
                            }else{
                                return row["houscplxNm"];
                            }
                        }
                    },
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                if(meta.row == 0){
                                    return "<input class='btn btn-gray btn-sm' type='button' id='_전체' value='선택' onclick='selectbtn(this)'/>";
                                }else{
                                    var nm = row['houscplxNm'];
                                    return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                                }
                            }
                    }
                ]
        });

        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm_").val("<c:out value="${houscplxNm}"/>");
        $("#deviceTpCd_").val("<c:out value="${deviceTpCd}"/>");

    });


    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#houscplxNm_").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#houscplxCd").val("");
        }else{
            $("#houscplxCd").val(strarray[0]);
        }

        $("#closebtn").click();
    }

    //검색버튼을 눌렀을경우
    function btn_search(){

        var deviceTpCd = $("#deviceTpCd_").val();
        if(deviceTpCd == "all"){
            deviceTpCd = "";
        }

        $("#deviceTpCd").val(deviceTpCd);
        $("#form_list").submit();
    }
    
    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('check');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                manufacturerByDeviceArray.push(check[i].value);
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;

                for(let j = 0; j < manufacturerByDeviceArray.length; j++) {
                    if(manufacturerByDeviceArray[j] == check[i].value) {
                        manufacturerByDeviceArray.splice(j, 1);
                        j--;
                    }
                }
            }
        }
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    function excel(){
        var list = new Array();
        <c:forEach items="${manufacturerByDeviceList}" var="manufacturerByDeviceList" varStatus="status">
            var cJson = new Object();
            cJson.단지명 = "<c:out value="${manufacturerByDeviceList.houscplxNm}"/>";
            cJson.장치유형 = "<c:out value="${manufacturerByDeviceList.deviceTpCd}"/>";
            cJson.제조사 = "<c:out value="${manufacturerByDeviceList.commCdNm}"/>";

            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "장치별제조사목록";

        param.tableHeader = "['단지명', '장치유형', '제조사']";

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

                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_장치별제조사목록.xlsx";
                link.click();
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }

    function btn_delete(){

        $("#manufacturerByDeviceArray").val(manufacturerByDeviceArray);

        if(manufacturerByDeviceArray == null || manufacturerByDeviceArray == ""){
            alert("삭제할 목록을 선택해주세요.");
            return;
        }

        $("#form_delete").submit();
    }

    function add(){
        location.href = "/cm/system/manufacturerByDevice/add";
    }

    //리스트 클릭 수정
    function detailview(houscplxCd, deviceTpCd, deviceMfCd){
        $("#houscplxCdEdit").val(houscplxCd);
        $("#deviceTpCdEdit").val(deviceTpCd);
        $("#deviceMfCdEdit").val(deviceMfCd);
        $("#form_edit").submit();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">장치별 제조사 목록</h2>
            <ul class="location">
                <li>장치 및 편의시설 설정</li>
                <li>장치별 제조사 관리</li>
                <li>장치별 제조사 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:45%"/>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" id="houscplxNm_" class="form-control" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
                                </div>
                            </div>
                        </td>
                        <th>· 장치 타입</th>
                        <td>
                            <select class="custom-select" id="deviceTpCd_">
                                <option value="all">전체</option>
                                <option value="LIGHTS">조명</option>
                                <option value="HEATING">난방</option>
                                <option value="AIRCON">에어컨</option>
                                <option value="VENTILATOR">환기</option>
                                <option value="CURTAIN">전동 커튼</option>
                                <option value="SMART_CONSENT">대기전력</option>
                                <option value="GASLOCK">가스밸브</option>
                            </select>
                        </td>
                        <td colspan="4">
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll" onclick="checkall();">
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">단지명</th>
                        <th scope="col">장치유형</th>
                        <th scope="col">제조사</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${manufacturerByDeviceList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${list.houscplxCd}"/>/<c:out value="${list.deviceTpCdOrgn}"/>/<c:out value="${list.deviceMfCd}"/>"/>
                                    <label class="custom-control-label" for="check${status.index}"></label>
                                </div>
                            </td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.deviceTpCdOrgn}"/>','<c:out value="${list.deviceMfCd}"/>');"><c:out value="${list.houscplxNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.deviceTpCdOrgn}"/>','<c:out value="${list.deviceMfCd}"/>');"><c:out value="${list.deviceTpCd}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.deviceTpCdOrgn}"/>','<c:out value="${list.deviceMfCd}"/>');"><c:out value="${list.commCdNm}"/></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="left_area">
                    <button class="btn btn-bluegreen" type="button" onclick="btn_delete()">삭제</button>
                </div>
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
        </div>

    </div>
</div>

<form:form id="form_list" action="/cm/system/manufacturerByDevice/list" name="list" commandName="list" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="deviceTpCd" name="deviceTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_delete" action="/cm/system/manufacturerByDevice/delete" name="delete" commandName="delete" method="post">
      <input type="text" id="manufacturerByDeviceArray" name="manufacturerByDeviceArray" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_edit" action="/cm/system/manufacturerByDevice/edit" name="edit" commandName="edit" method="post">
      <input type="text" id="houscplxCdEdit" name="houscplxCdEdit" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="deviceTpCdEdit" name="deviceTpCdEdit" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="deviceMfCdEdit" name="deviceMfCdEdit" style="width:0;height:0;visibility:hidden"/>
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