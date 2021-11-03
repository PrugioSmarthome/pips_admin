<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/dataTables.rowReorder.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/rowReorder.dataTables.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    .dataTables_length {
            float: right !important;
    }
</style>
<script type="text/javascript">

    $(document).on('change', 'input[name="box_chk"]', function(){
        var danjiArrayListArray = $("#danjiArrayList").val();
        var danjiArrayList = danjiArrayListArray.split(",");
        if($(this).is(":checked")){
            var val = $(this).val();
            danjiArrayList.push(val);
        } else{
            var val = $(this).val();
            for(let i = 0; i < danjiArrayList.length; i++) {
              if(danjiArrayList[i] == val) {
                danjiArrayList.splice(i, 1);
                i--;
              }
            }
        }
        $("#danjiArrayList").val(danjiArrayList);
    });

    $(document).on('change', 'input[name="box_chk2"]', function(){
        var danjiArrayList2Array = $("#danjiArrayList2").val();
        var danjiArrayList2 = danjiArrayList2Array.split(",");
        if($(this).is(":checked")){
            var val = $(this).val();
            danjiArrayList2.push(val);
        } else{
            var val = $(this).val();
            for(let i = 0; i < danjiArrayList2.length; i++) {
              if(danjiArrayList2[i] == val) {
                danjiArrayList2.splice(i, 1);
                i--;
              }
            }
        }
        $("#danjiArrayList2").val(danjiArrayList2);
    });


    var taggle;
    var selHouscplxCd = new Array();

    $(document).ready(function() {

        $('#modal0').on('hidden.bs.modal', function (e) {
            $(".modal-backdrop").remove();
            $('#file').fileinput('reset');
            $("#search_text").val("");
            $("#search_btn").click();
            $("#danjiList").empty();
            var check1 = document.getElementsByName('box_chk');
            var checkall1 = document.getElementById("checkAll");
            for(var i = 0 ; i < check1.length ; i++){
                check1[i].checked = false;
                checkall1.checked = false;
            }
        });

        $('#modal3').on('hidden.bs.modal', function (e) {
            $(".modal-backdrop").remove();
            $("#search_text2").val("");
            $("#flag").val("0");
            $("#search_btn2").click();
            var check2 = document.getElementsByName('box_chk2');
            var checkall2 = document.getElementById("checkAll2");
            for(var i = 0 ; i < check2.length ; i++){
                check2[i].checked = false;
                checkall2.checked = false;
            }
        });

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
                "url":"/cm/common/housingcplx/listNotAll",
                "dataSrc":""
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

        $("#list_table").css("width","100%");

    });

   //모달 단지리스트 검색(등록)
    function list_search(){
        var text = $("#search_text").val();
         $('#list_table').DataTable().search(text).draw();
    }

   //모달 단지리스트 검색(수정)
    function list_search2(){
        var text = $("#search_text2").val();
         $('#list_table2').DataTable().search(text).draw();
    }

	function cn(){
		$("#modal3").modal('hide');
	}

	function modify_btn(){

	    if($('#houscplxNmList').is(':empty') && $("input:checkbox[name=checkNonResidentEdit]").is(":checked") == false) {
            alert("단지를 선택해주세요.");
            return;
	    }

        var blltOrdNo = $("#editBlltOrdNo").val();
        if(blltOrdNo == null || blltOrdNo == ""){
            alert("정렬순서를 입력해주세요.");
            return;
        }

        var blltOrdNoDB = $("#blltOrdNoDB").val();
        var flag = "false";

        if(blltOrdNo != blltOrdNoDB){
            $.ajax({
                 url: '/cm/system/banner/checkBannerBlltOrdNo',
                 type: 'POST',
                 data: {"blltOrdNo": blltOrdNo},
                 dataType : "text",
                 async: false,
                 success: function(data){
                     if(data == "true"){
                        alert("정렬순서가 중복되었습니다.");
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
        }

        if(flag == "true"){
            return;
        }

	    var existedFile = $("#filename").val();
	    var modifyFile = $("#inputGroupFile01").val();
	    var isFileChange = "N";

        if (modifyFile != "" && modifyFile != existedFile) {
            isFileChange = "Y";
        }

		var danjiArray = new Array();
		var check = $("#danjiArrayList2").val().split(",");
		for(var i = 0 ; i < check.length ; i++){
		    var str = check[i].split("_");
		    danjiArray.push(str[0]);
		}

        if($("input:checkbox[name=checkNonResidentEdit]").is(":checked") == true) {
            $("#nonResidentVal").val("Y");
        }else{
            $("#nonResidentVal").val("N");
        }

        $("#isFileChangeId").val(isFileChange);
        $("#requestType_mod").val("CHANGE");
        $("#danjiArray").val(danjiArray);
        $("#form_modal").submit();
	}

    function img_del(){
        var listArray = new Array();
        var jsonObject = new Object();
        jsonObject.fileNm = $("#prevFileNm").val();
        jsonObject.blltNo = $("#blltNo").val();
        listArray.push(jsonObject);
        var removeFileList = JSON.stringify(listArray);
        console.log(removeFileList);

        $("#requestType_del").val("DELETE");
        $("#removeFileList").val(removeFileList);
        $("#form_del").submit();
    }

    function back() {
        window.history.back();
    }

    //체크박스 전체선택(등록)
    function checkall(){
        var check = document.getElementsByName('box_chk');
        var checkall = document.getElementById("checkAll");

        var checkAllArray = new Array();

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                checkAllArray.push(check[i].value);
            }
            $("#danjiArrayList").val(checkAllArray);
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
            }
            $("#danjiArrayList").val("");
        }
    }

    function SetNum(obj){
        val=obj.value;
        re=/[^0-9]/gi;
        obj.value=val.replace(re,"");
    }

    //체크박스 전체선택(수정)
    function checkall2(){
        var check = document.getElementsByName('box_chk2');
        var checkall = document.getElementById("checkAll2");

        var checkAll2Array = new Array();

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                checkAll2Array.push(check[i].value);
            }
            $("#danjiArrayList2").val(checkAll2Array);
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
            }
            $("#danjiArrayList2").val("");
        }
    }

    //단지선택(등록)
    function danjiCheck(){

        var danjiArrayListVal = $("#danjiArrayList").val();
        var danjiArrayList = danjiArrayListVal.split(",");

        if(danjiArrayListVal == "" && $("input:checkbox[name=checkNonResident]").is(":checked") == false){
            alert("단지를 선택해주세요.");
            return;
        }

        for(var i=0; i<danjiArrayList.length; i++){
            if(danjiArrayList[i] == "" || danjiArrayList[i] == "undefined"){
                danjiArrayList.splice(i, 1);
            }
        }

        if(danjiArrayList[0] == "" || danjiArrayList[0] == "undefined"){
            danjiArrayList.splice(0, 1);
        }

        var result = "<table class='table' style='text-align:center;'><thead><tr><th style='text-align:center;'>단지명</th></tr></thead><tbody>";
        for(var i=0;i<danjiArrayList.length;i++){
            var listName = danjiArrayList[i].split("_");
            result += "<tr><td style='text-align:center;'>"+listName[1]+"</td></tr>";
        }
        result += "</tbody></table>";

        if(danjiArrayList[0] == null){
            result = "";
        }

        $("#danjiList").empty();
        $("#danjiList").append(result);

        $("#modal1").modal('hide');
    }

    //단지선택(수정)
    function danjiCheck2(){

        var danjiArrayList2Val = $("#danjiArrayList2").val();
        var danjiArrayList2 = danjiArrayList2Val.split(",");

        if(danjiArrayList2Val == "" && $("input:checkbox[name=checkNonResident]").is(":checked") == false){
            alert("단지를 선택해주세요.");
            return;
        }

        for(var i=0; i<danjiArrayList2.length; i++){
            if(danjiArrayList2[i] == "" || danjiArrayList2[i] == "undefined"){
                danjiArrayList2.splice(i, 1);
            }
        }

        var result = "<tr><th vertical-align: top;'>적용단지</th><td>";
        for(var i=0;i<danjiArrayList2.length;i++){
            var listName = danjiArrayList2[i].split("_");
            result += "<input type='text' value='"+listName[1]+"' class='form-control' disabled />";
        }

        result += "</td></tr>";

        if(danjiArrayList2[0] == null){
            result = "";
        }

        $("#houscplxNmList").empty();
        $("#houscplxNmList").append(result);

        $("#flag").val("1");

        if(danjiArrayList2[0] == null && $("input:checkbox[name=checkNonResidentEdit]").is(":checked") == true){
            $("#noDanjiList").val("Y");
        }

        $("#modal2").modal('hide');
    }

    function danjiSelectModify() {

        if($("#flag").val() == "1"){
            return;
        }

        var table = $('#list_table2').DataTable();
        table.destroy();

        var blltNo = $("#blltNo").val();

        $('#list_table2').DataTable({
            "order": [],
            "bLengthChange": true,
            "lengthMenu": [10, 30, 50, 100],
            "pageLength" : 50,
            "dom": 'iltp',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다.",
                "lengthMenu" : "_MENU_ 개씩 보기"
            },
            ajax:{
                "url":"/cm/common/housingcplx/danjiSelectModify",
                "async":false,
                "data": function(d){
                    d.blltNo = blltNo;
                    d.banrLnkCls = "B";
                },
                "dataSrc":""
                },
                columns:[
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                var nm = row['houscplxNm'];
                                var cnt = row['cnt'];

                                if(cnt == 1){
                                    return "<div class='custom-control custom-checkbox'><input type='checkbox' name='box_chk2' class='custom-control-input' id='"+data+"_"+nm+"Z' value='"+data+"_"+nm+"'><label class='custom-control-label' for='"+data+"_"+nm+"Z'></label></div>";
                                }else{
                                    return "<div class='custom-control custom-checkbox'><input type='checkbox' name='box_chk2' class='custom-control-input' id='"+data+"_"+nm+"Z' value='"+data+"_"+nm+"' checked><label class='custom-control-label' for='"+data+"_"+nm+"Z'></label></div>";
                                }
                            }
                    },
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            return row["houscplxNm"];
                        }
                    }
                ]

        });

        var danjiArrayList2Check = new Array();
        $("input[name=box_chk2]:checked").each(function() {
            var val = $(this).val();
            danjiArrayList2Check.push(val);
        })

        $("#danjiArrayList2").val(danjiArrayList2Check);

        $("#list_table2").css("width","100%");

    }

</script>
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">배너 수정</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>배너 관리</li>
                <li>배너 수정</li>
            </ul>
        </div>

        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th style="width:40px" scope="col">순서</th>
                        <th style="width:350px" scope="col">파일명</th>
                        <th style="width:350px" scope="col">링크정보</th>
                        <th style="display:none"></th>
                        <th style="display:none"></th>
                        <th style="display:none"></th>
                        <th style="display:none"></th>
                        <th style="display:none"></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${bannerList}" var="list" varStatus="status">
                    <tr>
                        <td><c:out value="${list.blltOrdNo}"/></td>
                        <td><c:out value="${list.orgnlFileNm}"/></td>
                        <td><c:out value="${list.linkUrlCont}"/></td>
                        <td style="display:none"><c:out value="${list.fileNm}"/></td>
                        <td style="display:none"><c:out value="${list.blltNo}"/></td>
                        <td style="display:none"><c:out value="${list.houscplxNm}"/></td>
                        <td style="display:none"><c:out value="${list.nonResident}"/></td>
                        <td style="display:none"><c:out value="${list.blltOrdNo}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area no_paging">
                <div class="left_area">
                    <button class="btn btn-add" type="button" data-toggle="modal" data-target="#modal0"><i class="fas fa-plus-square"></i>추가</button>
                </div>
                <div class="right_area">
                    <button class="btn btn-gray" type="button" onclick="back();">취소</button>
                    <!-- <button class="btn btn-bluegreen" type="button" onclick="gomodify();">저장</button> -->
                </div>
            </div>
        </div>
        <div id="result"></div>

        <script type="text/javascript">
            var table = $('#table1').DataTable({
                "bLengthChange" : true,
                "pageLength" : 50,
                "dom": '<<t>>',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "조회된 데이터가 없습니다."
                },

                rowReorder: true
            });

            table.on( 'row-reorder', function ( e, diff, edit ) {
                for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
                    var rowData = table.row( diff[i].node ).data();
                }
            });

            table.on('click','tr',function(e, diff, edit){
                $("#filename").val(table.row(this).data()[1]);
                $("#linkUrlCont").val(table.row(this).data()[2]);
                $("#prevFileNm").val(table.row(this).data()[3]);
                $("#blltNo").val(table.row(this).data()[4]);
                $("#houscplxNm").val(table.row(this).data()[5]);
                var nonResident = table.row(this).data()[6];
                $("#blltOrdNoDB").val(table.row(this).data()[7]);
                $("#editBlltOrdNo").val(table.row(this).data()[7]);

                var non_resident = "";
                if(nonResident == "1"){
                    non_resident += "<input type='checkbox' class='custom-control-input' id='checkNonResidentEdit' name='checkNonResidentEdit' checked>";
                    non_resident += "<label class='custom-control-label' for='checkNonResidentEdit'></label>";
                }else{
                    non_resident += "<input type='checkbox' class='custom-control-input' id='checkNonResidentEdit' name='checkNonResidentEdit'>";
                    non_resident += "<label class='custom-control-label' for='checkNonResidentEdit'></label>";
                }
                $("#checkNonResidentEditDiv").empty();
                $("#checkNonResidentEditDiv").append(non_resident);

                var houscplxNmVal = table.row(this).data()[5].split(",");

                var result = "<tr><th vertical-align: top;'>적용단지</th><td>";
                for(var i=0;i<houscplxNmVal.length;i++){
                    result += "<input type='text' value='"+houscplxNmVal[i]+"' class='form-control' disabled />";
                }

                result += "</td></tr>";

                if(table.row(this).data()[5] == "" || table.row(this).data()[5] == null){
                    result = "";
                }

                $("#houscplxNmList").empty();
                $("#houscplxNmList").append(result);

                $("#modal3").modal();
            })

            var finalRowResult;

            function gomodify(){
                var tableDataLength = table.data().length;
                var finalArray = new Array();

                for (var i=0, ien=tableDataLength; i<ien; i++ ) {
                    var jsonObject = new Object();
                    jsonObject.ordNo = table.data().toArray()[i][0];
                    jsonObject.prevFileNm = table.data().toArray()[i][1];
                    jsonObject.linkUrlCont = table.data().toArray()[i][2];
                    jsonObject.fileNm = table.data().toArray()[i][3];
                    jsonObject.blltNo = table.data().toArray()[i][4];
                    finalArray.push(jsonObject);
                }

                var orderFileList = JSON.stringify(finalArray);

                $("#orderFileList").val(orderFileList);
                $("#requestType").val("CHANGE");
                $("#houscplxCd").val(houscplxCd);
                $("#form_submit").submit();
            }
        </script>
    </div>
</div>
<input type="text" id="danjiArrayList" name="danjiArrayList" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="danjiArrayList2" name="danjiArrayList2" class="form-control" style="width:0;height:0;visibility:hidden"/>
<form:form id="form_submit" action="/cm/system/banner/editBannerImageOrderInfoAction" name="banner" commandName="banner" method="post">
<input type="text" id="requestType" name="requestType" value="CHANGE" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ptypeTpCd" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="orderFileList" name="orderFileList" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="form_del" action="/cm/system/banner/editBannerImageInfoAction" name="banner" commandName="banner" method="post">
<input type="text" id="requestType_del" name="requestType" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ptypeTpCd_del" name="ptypeTpCd" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="removeFileList" name="removeFileList" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ordno" name="ordno" class="form-control" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_modal" action="/cm/system/banner/editBannerImageInfoAction" enctype="multipart/form-data" name="banner" commandName="banner" method="post">
<input type="text" id="requestType_mod" name="requestType" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ptypeTpCd_mod" name="ptypeTpCd" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="isFileChangeId" name="isFileChange" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="danjiArray" name="danjiArray" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="nonResidentVal" name="nonResidentVal" value="X" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="noDanjiList" name="noDanjiList" value="N" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="blltOrdNoDB" name="blltOrdNoDB" class="form-control" style="width:0;height:0;visibility:hidden"/>
<div class="modal" id="modal3" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered " role="document">
		<div class="modal-content edit-popup">
			<!-- 모달헤더 -->
			<div class="modal-header">
				<h5 class="modal-title">파일수정 / 삭제</h5>
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
							<tr>
								<th>항목</th>
								<td>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="inputGroupFile01" name="file" aria-describedby="inputGroupFileAddon01" accept=".png, .jpg, .jpeg, .gif"/>
										<label class="custom-file-label" id="file_label" for="inputGroupFile01">Choose file</label>
									</div>
									<script type="text/javascript">
										$('#inputGroupFile01').on('change',function(){
											var fileName = $(this).val();
											$(this).next('.custom-file-label').html(fileName);
										})
									</script>
								</td>
							</tr>
							<tr>
								<th>원본파일</th>
								<td><input type="text" id="filename" name="filename" class="form-control" disabled/></td>
								<td style="display:none"><input type="text" id="requestType" name="requestType" class="form-control"/></td>
								<td style="display:none"><input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" class="form-control"/></td>
								<td style="display:none"><input type="text" id="blltNo" name="blltNo" class="form-control"/></td>
								<td style="display:none"><input type="text" id="prevFileNm" name="prevFileNm" class="form-control"/></td>
							</tr>
							<tr>
							    <th>링크정보</th>
								<td><input type="text" id="linkUrlCont" name="linkUrlCont" class="form-control"/></td>
							</tr>
					</table>
					<br/>

                    <div class="table_wrap2">
                        <table class="table2">
                        <colgroup>
                            <col style="width:30%">
                            <col style="width:14%">
                            <col style="width:25%">
                            <col style="width:31%">
                        </colgroup>
                        <tbody>
                            <tr>
                            <td colspan="4">
                                <button id="danjiSelect" style="width:100%;" class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal2" onclick="danjiSelectModify();">단지선택</button>
                            </td>
                            </tr>
                            <tr>
                            <th>비입주민여부</th>
                            <td>
                                <div class="custom-control custom-checkbox" id="checkNonResidentEditDiv">
                                </div>
                            </td>
                            <th>정렬순서</th>
                            <td>
                                <input type="text" class="form-control" id="editBlltOrdNo" name="editBlltOrdNo" onkeyup="SetNum(this);" maxlength="11"/>
                            </td>
                            </tr>
                        </tbody>
                        </table>
                    </div>

                    <br/>
					<div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap2">
                        <table class="table2">
                            <colgroup>
                                <col style="width:150px"/>
                            	<col />
                            </colgroup>
                            <tbody id="houscplxNmList"></tbody>
                        </table>
					</div>
				</div>
				<div class="tbl_btm_area2">
				    <div class="left_area">
                        <button class="btn btn-gray" onclick="img_del();" type="button">삭제</button>
                    </div>
					<div class="right_area">
						<button class="btn btn-gray" onclick="cn();" type="button">취소</button>
						<button class="btn btn-bluegreen" onclick="modify_btn();" type="button">수정</button>
					</div>
				</div>
			</div>
			<!-- //모달바디 -->
		</div>
	</div>
</div>
</form:form>

<div class="modal" id="modal0" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered size_wide" role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">배너 등록</h5>
                <button type="button" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

				<!-- 모달바디 -->
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-lg-12 col-sm-12 col-11 main-section">
                                <div class="form-group">
                                    <div class="file-loading" id="file-loading">
                                        <input id="file" type="file" name="file[]" multiple="multiple" class="file" data-overwrite-initial="false" data-min-file-count="1" accept=".png, .jpg, .jpeg, .gif">
                                    </div>
                                </div>
							</div>
						</div>
					</div>
					<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet">
					<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all" rel="stylesheet" type="text/css"/>
					<link rel="stylesheet" type="text/css" href="<c:url value="/styles/fileupload.css" />">
					<style type="text/css">
                        #ptype-nm, #ptype-dim-qty, #sup-dim-qty {
                            display:none;
                        }

                        .krajee-default.file-preview-frame {
                            height: 280px;
                        }

                        .modal-body {
                          padding: 1px;
                        }
					</style>
					<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/bootstrap-fileinput/fileinput.js" />"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.4.7/themes/fa/theme.js" type="text/javascript"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" type="text/javascript"></script>
					<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" type="text/javascript"></script>
					<script type="text/javascript">
					    var houscplxCd = '<c:out value="${houscplxCd}"/>';

						$("#file").fileinput({
						    language: "kr",
						    uploadAsync: false,
							theme: 'fa',
							uploadUrl: "/cm/system/banner/addSystemBannerImageInfoAction",
							uploadExtraData: function() {
							    var fileName = document.getElementsByName("file[]")[0]["files"];
							    var fileNames;

							    var danjiArray = new Array();

							    var check = $("#danjiArrayList").val().split(",");
							    for(var i = 0 ; i < check.length ; i++){
							        var str = check[i].split("_");
							        danjiArray.push(str[0]);
							    }

                                if($("input:checkbox[name=checkNonResident]").is(":checked") == true) {
                                    danjiArray.push("000000");
                                }

							    for(i=0; i<fileName.length; i++) {
                                    if (i == 0) {
                                        fileNames = fileName[i]["name"];
                                    } else {
                                        fileNames += "/" + fileName[i]["name"];
                                    }
                                    console.log(i);
							    }

                                var name = document.getElementsByName("linkUrl[]");
                                var linkUrlNames;

                                for(i=0; i<name.length; i++) {
                                    var linkUrlName;

                                    if (name[i].value != "") {
                                        linkUrlName = name[i].value;
                                    } else {
                                        linkUrlName = " ";
                                    }

                                    if (i == 0) {
                                        linkUrlNames = linkUrlName;
                                    } else {
                                        linkUrlNames += "," + linkUrlName;
                                    }
                                }

                                var blltOrdNo = $("#blltOrdNo").val();

								return {
								    _fileNames: fileNames,
									_linkUrlNames: linkUrlNames,
									_danjiArray: danjiArray,
									_blltOrdNo: blltOrdNo
								};
							},
							allowedFileExtensions: ['jpg', 'png', 'gif'],
							overwriteInitial: false,
							maxFileSize:209715200,
							maxFilesNum: 10,
							multiple: true,
							multipart: true,
							slugCallback: function (filename) {
								return filename.replace('(', '_').replace(']', '_');
							}
						});

						$('#file').on('filebatchpreupload', function(event, data, previewId, index) {
                            var rtn = true;

                            var danjiArray = new Array();

							var check = $("#danjiArrayList").val().split(",");
							for(var i = 0 ; i < check.length ; i++){
							    var str = check[i].split("_");
							    danjiArray.push(str[0]);
							}

                            if((danjiArray == null || danjiArray == "") && $("input:checkbox[name=checkNonResident]").is(":checked") == false){
                                alert("단지를 선택해주세요.");
                                $('#file').fileinput('cancel').fileinput('pause');
                            }

                            var blltOrdNo = $("#blltOrdNo").val();
                            if(blltOrdNo == null || blltOrdNo == ""){
                                alert("정렬순서를 입력해주세요.");
                                $('#file').fileinput('cancel').fileinput('pause');
                            }

                            var flag = "false";
                            $.ajax({
                                 url: '/cm/system/banner/checkBannerBlltOrdNo',
                                 type: 'POST',
                                 data: {"blltOrdNo": blltOrdNo},
                                 dataType : "text",
                                 async: false,
                                 success: function(data){
                                     if(data == "true"){
                                        alert("정렬순서가 중복되었습니다.");
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
                                $('#file').fileinput('cancel').fileinput('pause');
                            }

                        });

						$('#file').on('filebatchuploaderror', function(event, data, previewId, index) {
                            alert("배너 이미지 업로드중 장애가 발생하였습니다.");
                            console.log(event);
                            console.log(data);
                            console.log(previewId);
                            console.log(index);
                            location.href = "/cm/system/banner/edit";
                        });

                        $('#file').on('filebatchuploadsuccess', function(event, data, previewId, index) {
                            alert("배너 이미지 업로드가 완료되었습니다.");
                            console.log(event);
                            console.log(data);
                            console.log(previewId);
                            console.log(index);
                            location.href = "/cm/system/banner/edit";
                        });
					</script>
				</div>

                <div class="table_wrap2">
                    <table class="table2">
                    <colgroup>
                        <col style="width:40%">
                        <col style="width:19%">
                        <col style="width:9%">
                        <col style="width:16%">
                        <col style="width:16%">
                    </colgroup>
                    <tbody>
                        <tr>
                        <td>
                            <button class="btn btn-gray btn-sm" style="width:100%" type="button" data-toggle="modal" data-target="#modal1">단지선택</button>
                        </td>
                        <th>비입주민여부</th>
                        <td>
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkNonResident" name="checkNonResident">
                                <label class="custom-control-label" for="checkNonResident"></label>
                            </div>
                        </td>
                        <th>정렬순서</th>
                        <td>
                            <input type="text" class="form-control" id="blltOrdNo" name="blltOrdNo" onkeyup="SetNum(this);" maxlength="11"/>
                        </td>
                        </tr>
                    </tbody>
                    </table>
                </div>

                <div id="danjiList" style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;">
                </div>
			<!-- //모달바디 -->
		</div>
	</div>
	<c:set var="houscplxCd" value="${houscplxCd}"/>
</div>

<!-- 단지선택 팝업(등록) -->
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
            <div class="container">
            <div class="row">
            <div class="col-lg-12 col-sm-12 col-11 main-section">
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
                                <th>검색어</th>
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
                <div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap mt-3">
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

<!-- 단지선택 팝업(수정) -->
<div class="modal fade" id="modal2" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered " role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">단지선택</h5>
                <button type="button" id="closebtn2" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

            <!-- 모달바디 -->
            <div class="modal-body">
            <div class="container">
            <div class="row">
            <div class="col-lg-12 col-sm-12 col-11 main-section">
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
                                <th>검색어</th>
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
                <div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap mt-3">
                    <table class="table" id="list_table2" style="text-align:center;">
                        <thead>
                            <tr>
                                <th scope="col" style="text-align:center;">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="checkAll2" onclick="javascript:checkall2();">
                                        <label class="custom-control-label" for="checkAll2"></label>
                                    </div>
                                </th>
                                <th scope="col" style="text-align:center;">항목</th>
                            </tr>
                        </thead>
                        <tbody id="householdDeviceList2">
                        </tbody>
                    </table>
                </div>
                <!-- //테이블 -->
            </div>
            </div>
            </div>
            </div>
            <!-- //모달바디 -->
        <button class="btn btn-gray" type="button" onclick="javascript:danjiCheck2()">선택완료</button>
        </div>
    </div>
</div>
<input type="text" id="flag" name="flag" value="0" style="width:0;height:0;visibility:hidden"/>