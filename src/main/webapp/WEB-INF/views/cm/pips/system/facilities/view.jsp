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

        var str = "<c:out value="${bizcoNm}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        str = str.replace(/&#37;/g,"%");
        $("#search_text").val(str);
        $("#txtHouscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#search_cd").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxCdGoList").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxCdGoListNm").val("<c:out value="${houscplxNm}"/>");
        var houscplxCdGoList = "<c:out value="${houscplxCdGoList}"/>";
        var houscplxCdGoListNm = "<c:out value="${houscplxCdGoListNm}"/>";
        if(houscplxCdGoListNm != ""){
            $("#txtHouscplxNm").val(houscplxCdGoListNm);
            $("#search_cd").val(houscplxCdGoList);
            $("#houscplxCdGoList").val("<c:out value="${houscplxCdGoList}"/>");
            $("#houscplxCdGoListNm").val("<c:out value="${houscplxCdGoListNm}"/>");
        }
        $("#start").val("<c:out value="${startCrDt}"/>");
        $("#end").val("<c:out value="${endCrDt}"/>");
    });

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");

        var strarray = str.split("_");
        $("#txtHouscplxNm").val(strarray[1]);
        $("#search_nm").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#search_cd").val("");
        }else{
            $("#search_cd").val(strarray[0]);
        }


        $("#closebtn").click();
    }

    function add(e){
        $("#add_cd").val(e);
        $("#form_add").submit();
    }

    //리스트 선택시 상세 화면으로 이동
    function click_row(rows){

        $("#facltBizcoId").val(rows);
        $("#form_detail").submit();
    }

	function modify_for_modal(){
        $("#edit_facltBizcoId").val($("#facltBizcoId").val());
        $("#form_modal").submit();
	}

	function cancel_for_modal(){
		$("#modal3").modal('hide');
	}

    function delete_for_modal(){
        $("#delete_facltBizcoId").val($("#facltBizcoId").val());
        $("#form_del").submit();
    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }
    //검색버튼
    function search_btn(){
        var str = $("#search_text").val();
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        str = str.replace(/%/g,"&#37;");

        var start = $("#start").val();
        var end = $("#end").val();

        $("#search_start_day").val(start.replace(/\./gi,''));
        $("#search_end_day").val(end.replace(/\./gi,''));
        $("#search_name").val(str);
        $("#search_btn_on").val("Y");
        $("#form_search").submit();
    }
    //엑셀다운로드할 dataList Ajax 호출부분
    function excel_data_array(){
        var param = new Object();
        var list = new Array();

        var str = $("#search_text").val();
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        str = str.replace(/%/g,"&#37;");
        var start = $("#start").val();
        var end = $("#end").val();
        start = start.replace(/\./gi,'');
        end = end.replace(/\./gi,'');

        param.houscplxCd = $("#search_cd").val();
        param.startCrDt = start;
        param.endCrDt = end;
        param.bizcoNm =  str;


        $.ajax({
            url: '/cm/system/facility/excel/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                var jsonstring = JSON.stringify(data);
                excelExport(jsonstring);
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }
    //엑셀 다운로드 함수
    function excelExport(list){
        var jsonList = JSON.parse(list);
        var cont = jsonList.length;
        var dataArray = new Array();
        for(var i = 0 ; i < cont ; i++){
            var cJson = new Object();
            cJson.대공종 = jsonList[i].twbsNm;
            cJson.업체명 = jsonList[i].bizcoNm;
            cJson.공사내역 = jsonList[i].conCont;
            cJson.성명 = jsonList[i].perchrgNm;
            cJson.휴대폰 = jsonList[i].mphoneNo;
            cJson.사무실 = jsonList[i].offcPhoneNo;
            cJson.팩스 = jsonList[i].faxNo;
            dataArray.push(cJson);
        }
        var hJson = JSON.stringify(dataArray);

        var param = new Object();
        param.title = "시설업체 목록";
        param.tableHeader = "['대공종', '업체명', '공사내역', '성명', '휴대폰', '사무실', '팩스']";
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
                link.download= today+"_시설업체목록.xlsx";
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
</script>

<div id="container">


    <div class="container">

        <div class="top_area">
            <h2 class="tit">시설업체 목록</h2>
            <ul class="location">
                <li>시설업체 관리</li>
                <li>시설업체 정보 관리</li>
                <li>시설업체 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:13%"/>
                    <col style="width:40%"/>
                    <col style="width:10%"/>
                    <col style="width:40%"/>
                    <col style="width:10%"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="txtHouscplxNm" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1">단지선택</button>
                                </div>
                            </div>
                        </td>
                        <th>· 등록일</th>
                        <td>
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="start" id="start"/>
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" name="end" id="end"/>
                            </div>
                            <script type="text/javascript">
                            $('#start').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#end').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <th>· 시설업체명</th>
                        <td>
                            <input type="text" class="form-control" id="search_text"/>
                        </td>
                        <td colspan="4">
                            <button type="button" class="btn btn-brown btn-sm" onclick="search_btn();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>



        <div class="table_wrap">
            <c:if test="${not empty facilityInfoList}">
                <div class="tbl_top_area">
                    <div class="right_area">
                        <button class="btn btn-green btn-sm" type="button" onclick="excel_data_array();"><i class="fas fa-table"></i>Export</button>
                    </div>
                </div>
            </c:if>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">사용단지</th>
                        <th scope="col">구분</th>
                        <th scope="col">대공종</th>
                        <th scope="col">업체명</th>
                        <th scope="col">공사내역</th>
                        <th scope="col">연락망</th>
                        <th scope="col" style="display:none"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${facilityInfoList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center"><c:out value="${list.houscplxNm}"/></td>
                            <td class="text-center"><c:out value="${list.facltBizcoTpNm}"/></td>
                            <td class="text-center"><c:out value="${list.twbsNm}"/></td>
                            <td class="text-center bizco-nm"><a href="#" onclick="click_modal('${status.index}','<c:out value="${list.facltBizcoId}"/>');"><c:out value="${list.bizcoNm}"/></a></td>
                            <td class="text-center"><c:out value="${list.conCont}"/></td>
                            <td class="text-center"><button class="btn btn-bluegreen" type="button" onclick="click_row('<c:out value="${list.facltBizcoId}"/>');">연락망정보</button></td>
                            <td class="text-center" style="display:none"><c:out value="${list.bizcoNm}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script type="text/javascript">
            var table = $('#table1').DataTable({
                "order": [],
                "bLengthChange" : false,
                "dom": '<i<t>p>',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "데이터가 없습니다."
                }
            });

            function click_modal(index, facltBizcoId) {
                $("#facltBizcoId").val(facltBizcoId);
                $("#facltBizcoTpNm").val(table.row(index).data()[1]);
                $("#twbsNm").val(table.row(index).data()[2]);
                $("#bizcoNm").val(table.row(index).data()[6]);
                $("#conCont").val(table.row(index).data()[4]);
                $("#modal3").modal();
            }
        </script>

        <div class="tbl_btm_area2 no_paging">
            <div class="right_area">
                <c:if test="${empty facilityInfoList}">
                    <c:if test="${search_check eq 'N'}">
                        <!--
                        <button class="btn btn-bluegreen" type="button" onclick="add('<c:out value="${houscplxCd}"/>');">등록</button>
                        -->
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
</div>

<form:form id="form_search" action="/cm/system/facility/list" name="facilityInfo" commandName="facilityInfo" method="post">
      <input type="text" id="search_cd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="search_nm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="search_name" name="bizcoNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="search_start_day" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="search_end_day" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="search_btn_on" name="search_check" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_add" action="/cm/system/facility/add" name="facilityInfo" commandName="facilityInfo" method="post">
      <input type="text" id="add_cd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detail" action="/cm/system/facility/detailView" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="facltBizcoId" name="facltBizcoId" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCdGoList" name="houscplxCdGoList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCdGoListNm" name="houscplxCdGoListNm" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_del" action="/cm/system/facility/deleteFacilityInfoAction" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="delete_facltBizcoId" name="facltBizcoId" class="form-control" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_modal" action="/cm/system/facility/editFacilityInfoAction" name="facilityInfo" commandName="facilityInfo" method="post">
<input type="text" id="edit_facltBizcoId" name="facltBizcoId" class="form-control" style="width:0;height:0;visibility:hidden"/>
<div class="modal" id="modal3" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered " role="document">
		<div class="modal-content edit-popup">
			<!-- 모달헤더 -->
			<div class="modal-header">
				<h5 class="modal-title">시설업체 기본정보 수정 및 삭제</h5>
				<button type="button" class="close" data-dismiss="modal" type="button"><img src="/images/btn_x.png" alt="" /></button>
			</div>
			<!-- //모달헤더 -->

			<!-- 모달바디 -->
			<div class="modal-body">
				<div class="table_wrap2">
					<table class="table2">
						<colgroup>
							<col style="width:150px"/>
							<col />
						</colgroup>
						<tbody>
							<tr>
							    <th>구분</th>
								<td><input type="text" id="facltBizcoTpNm" name="facltBizcoTpNm" class="form-control"/></td>
							</tr>
							<tr>
							    <th>대공종</th>
								<td><input type="text" id="twbsNm" name="twbsNm" class="form-control"/></td>
							</tr>
							<tr>
							    <th>업체명</th>
								<td><input type="text" id="bizcoNm" name="bizcoNm" class="form-control"/></td>
							</tr>
							<tr>
							    <th>공사내역</th>
								<td><input type="text" id="conCont" name="conCont" class="form-control"/></td>
								<td style="display:none"><input type="text" id="facltBizcoId" name="facltBizcoId" class="form-control"/></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="tbl_btm_area2">
				    <div class="left_area">
                        <button class="btn btn-gray" onclick="delete_for_modal();" type="button">삭제</button>
                    </div>
					<div class="right_area">
						<button class="btn btn-gray" onclick="cancel_for_modal();" type="button">취소</button>
						<button class="btn btn-bluegreen" onclick="modify_for_modal();" type="button">수정</button>
					</div>
				</div>
			</div>
			<!-- //모달바디 -->
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