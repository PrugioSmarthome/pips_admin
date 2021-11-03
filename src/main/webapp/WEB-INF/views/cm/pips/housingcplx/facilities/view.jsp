<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">

    function tab_click(e,n){
        if(e == "summary"){
            $("#summarycd").val(n);
            $("#summary").submit();
        }
        if(e == "notice"){
            $("#noticecd").val(n);
            var type = "HOUSCPLX_INFO";
            $("#nptype").val(type);
            $("#notice").submit();
        }
        if(e == "location"){
            $("#locationcd").val(n);
            var type = "PLOT_PLAN";
            $("#lptype").val(type);
            $("#location").submit();
        }
        if(e == "floor"){
            $("#floorcd").val(n);
            var type = "FLOOR_PLAN";
            $("#fptype").val(type);
            $("#floor").submit();
        }
        if(e == "size"){
            $("#sizecd").val(n);
            $("#size").submit();
        }
        if(e == "mgto"){
            $("#mgtocd").val(n);
            $("#mgto").submit();
        }
        if(e == "cctv"){
            $("#cctvcd").val(n);
            $("#cctv").submit();
        }
        if(e == "etc"){
            $("#etccd").val(n);
            $("#etc").submit();
        }
    }

    function list_back(){
        location.href = "/cm/housingcplx/info/list";
    }

    function add(e){
        $("#add_cd").val(e);
        $("#form_add").submit();
    }

    //리스트 선택시 상세 화면으로 이동
    function click_row(cd,rows){
        $("#faclthouscplxCd").val(cd);
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
        $("#delete_isAll").val("N");
        $("#delete_facltBizcoId").val($("#facltBizcoId").val());
        $("#form_del").submit();
    }

    function deleteAll(houscplxCd) {
        if (confirm("모든 시설업체 및 연락처 정보를 삭제하시겠습니까?")) {
            $("#delete_cd").val(houscplxCd);
            $("#delete_isAll").val("Y");
            $("#form_del").submit();
        }
    }

    //엑셀다운로드할 dataList Ajax 호출부분
    function excel_data_array(){
        var param = new Object();
        param.houscplxCd = '<c:out value="${houscplxCd}"/>';
        $.ajax({
            url: '/cm/housingcplx/info/facilityInfo/excel/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                var jsonstring = JSON.stringify(data);
                //var jsondata = JSON.parse(jsonstring);

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
            <h2 class="tit">시설업체정보 상세</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>시설업체정보 상세 </li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click('summary','<c:out value="${houscplxCd}"/>')"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','<c:out value="${houscplxCd}"/>')"><a href="#">우리 단지 알림</a></li>
                <li onclick="tab_click('location','<c:out value="${houscplxCd}"/>')"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','<c:out value="${houscplxCd}"/>')"><a href="#">타입별 평면도</a></li>
                <li onclick="tab_click('size','<c:out value="${houscplxCd}"/>')"><a href="#">세대별 평형</a></li>
                <li onclick="tab_click('mgto','<c:out value="${houscplxCd}"/>')"><a href="#">관리실/경비실</a></li>
                <li onclick="tab_click('cctv','<c:out value="${houscplxCd}"/>')"><a href="#">CCTV</a></li>
                <li class="on"><a href="#">시설업체정보</a></li>
                <li onclick="tab_click('etc','<c:out value="${houscplxCd}"/>')"><a href="#">기타</a></li>
            </ul>
        </div>

        <c:choose>
            <c:when test="${not empty facilityInfoList}">
                <div class="table_wrap">
                    <div class="tbl_top_area">
                        <div class="right_area">
                            <button class="btn btn-green btn-sm" type="button" onclick="excel_data_array();"><i class="fas fa-table"></i>Export</button>
                            <button class="btn btn-gray" type="button" onclick="deleteAll('<c:out value="${houscplxCd}"/>');">전체삭제</button>
                        </div>
                    </div>
                    <div style="align:right; margin-bottom:10px;"><span style="color: red; text-align:center;">추가 및 재등록시 데이터를 전체삭제 후 등록해주십시오. (전체삭제 전 엑셀 Export 필수)</span></div>
                    <table class="table" id="table1">
                        <thead>
                            <tr>
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
                                    <td class="text-center"><c:out value="${list.facltBizcoTpNm}"/></td>
                                    <td class="text-center"><c:out value="${list.twbsNm}"/></td>
                                    <td class="text-center bizco-nm"><a href="#" onclick="click_modal('${status.index}','<c:out value="${list.facltBizcoId}"/>');"><c:out value="${list.bizcoNm}"/></a></td>
                                    <td class="text-center"><c:out value="${list.conCont}"/></td>
                                    <td class="text-center"><button class="btn btn-bluegreen" type="button" onclick="click_row('<c:out value="${houscplxCd}"/>','<c:out value="${list.facltBizcoId}"/>');">연락망정보</button></td>
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
                        $("#facltBizcoTpNm").val(table.row(index).data()[0]);
                        $("#twbsNm").val(table.row(index).data()[1]);
                        $("#bizcoNm").val(table.row(index).data()[5]);
                        $("#conCont").val(table.row(index).data()[3]);
                        $("#modal3").modal();
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2 style="text-align:center;color:gray">시설업체정보를 등록해주세요.</h2>
            </c:otherwise>
        </c:choose>

        <div class="tbl_btm_area2 no_paging">
            <div class="right_area">
                <c:if test="${empty facilityInfoList}">
                    <button class="btn btn-bluegreen" type="button" onclick="add('<c:out value="${houscplxCd}"/>');">등록</button>
                </c:if>
            </div>
        </div>

    </div>

</div>
<form:form id="form_add" action="/cm/housingcplx/info/facilityInfo/add" name="facilityInfo" commandName="facilityInfo" method="post">
      <input type="text" id="add_cd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detail" action="/cm/housingcplx/info/facilityInfo/detailView" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="facltBizcoId" name="facltBizcoId" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="faclthouscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_del" action="/cm/housingcplx/info/deleteFacilityInfoAction" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="delete_isAll" name="isAll" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delete_facltBizcoId" name="facltBizcoId" class="form-control" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delete_cd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_modal" action="/cm/housingcplx/info/editFacilityInfoAction" name="facilityInfo" commandName="facilityInfo" method="post">
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

<form:form id="summary" action="/cm/housingcplx/info/intro/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="summarycd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="notice" action="/cm/housingcplx/info/notice/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="noticecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="nptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="location" action="/cm/housingcplx/info/plot/view" name="plot" commandName="plot" method="post">
      <input type="text" id="locationcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="lptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="floor" action="/cm/housingcplx/info/floor/view" name="floor" commandName="floor" method="post">
      <input type="text" id="floorcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="fptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="size" action="/cm/housingcplx/info/householdPtype/view" name="householdPtype" commandName="householdPtype" method="post">
      <input type="text" id="sizecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="mgto" action="/cm/housingcplx/info/address/view" name="address" commandName="address" method="post">
      <input type="text" id="mgtocd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="cctv" action="/cm/housingcplx/info/cctv/view" name="cctv" commandName="cctv" method="post">
      <input type="text" id="cctvcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="etc" action="/cm/housingcplx/info/etc/view" name="etc" commandName="etc" method="post">
      <input type="text" id="etccd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>