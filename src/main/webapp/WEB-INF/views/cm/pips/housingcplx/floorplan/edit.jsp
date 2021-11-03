<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/dataTables.rowReorder.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/rowReorder.dataTables.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    #table1_paginate{
        display:none;
    }
</style>
<script type="text/javascript">
	function cn(){
		$("#modal3").modal('hide');
	}
	function modify_btn(){
	    var existedFile = $("#filename").val();
	    var modifyFile = $("#inputGroupFile01").val();
	    var ptypeDimQty = $("#ptypeDimQty").val();
	    var supDimQty = $("#supDimQty").val();

	    if(ptypeDimQty == ""){
	        alert("평형면적량을 입력해주세요.");
	        return;
	    }
	    if(supDimQty == ""){
	        alert("공급면적량을 입력해주세요.");
	        return;
	    }

	    var isFileChange = "N";

        if (modifyFile != "" && modifyFile != existedFile) {
            isFileChange = "Y";
        }

        $("#isFileChangeId").val(isFileChange);
        $("#ptypeTpCd_mod").val("FLOOR_PLAN");
        $("#requestType_mod").val("CHANGE");
        $("#form_modal").submit();
	}

    function img_del(){
        var listArray = new Array();
        var jsonObject = new Object();
        jsonObject.fileNm = $("#prevFileNm").val();
        jsonObject.ordNo = $("#ordno").val();
        listArray.push(jsonObject);
        var removeFileList = JSON.stringify(listArray);

        $("#requestType_del").val("DELETE");
        $("#ptypeTpCd_del").val("FLOOR_PLAN");
        $("#removeFileList").val(removeFileList);
        $("#form_del").submit();
    }

    //취소버튼
    function back(){
        window.history.back();
    }
</script>
<div id="container">

    <div class="container">
        <div class="top_area">
            <h2 class="tit">타입별 평면도 수정</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>타입별 평면도</li>
                <li>타입별 평면도 수정</li>
            </ul>
        </div>

        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col" style="width:50px;">순서</th>
                        <th scope="col" style="width:300px;">파일명</th>
                        <th scope="col" style="width:50px;">평형명</th>
                        <th scope="col" style="width:50px;">평형 면적량</th>
                        <th scope="col" style="width:50px;">공급 면적량</th>
                        <th style="display:none"></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${floorPlanList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center">${status.count}</td>
                        <td><c:out value="${list.stosOrgnlPlnfigNm}"/></td>
                        <td><c:out value="${list.ptypeNm}"/></td>
                        <td><c:out value="${list.ptypeDimQty}"/></td>
                        <td><c:out value="${list.supDimQty}"/></td>
                        <td style="display:none"><c:out value="${list.stosReducePlnfigNm}"/></td>
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
                "dom": '<i<t>p>',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "조회된 데이터가 없습니다."
                },
                "columnDefs": [
                    { "width": "10%", "targets": 0 },
                    { "width": "7%", "targets": 2 }
                ],
                rowReorder: true
            });

            table.on( 'row-reorder', function ( e, diff, edit ) {
                for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
                    var rowData = table.row( diff[i].node ).data();
                }
            });

            table.on('click','tr',function(e, diff, edit){
                $("#ordno").val(table.row(this).data()[0]);
                $("#filename").val(table.row(this).data()[1]);
                $("#ptypeNm").val(table.row(this).data()[2]);
                $("#ptypeDimQty").val(table.row(this).data()[3]);
                $("#supDimQty").val(table.row(this).data()[4]);
                $("#prevFileNm").val(table.row(this).data()[5]);
                $("#modal3").modal();
            })

            var finalRowResult;

            function gomodify(){
                var houscplxCd = '<c:out value="${houscplxCd}"/>';
                var tableDataLength = table.data().length;
                var finalArray = new Array();

                for (var i=0, ien=tableDataLength; i<ien; i++ ) {
                    var jsonObject = new Object();
                    jsonObject.ordNo = table.data().toArray()[i][0];
                    jsonObject.orgFileNm = table.data().toArray()[i][1];
                    jsonObject.ptypeNm = table.data().toArray()[i][2];
                    jsonObject.ptypeDimQty = table.data().toArray()[i][3];
                    jsonObject.supDimQty = table.data().toArray()[i][4];
                    jsonObject.fileNm = table.data().toArray()[i][5];
                    finalArray.push(jsonObject);
                }

                var orderFileList = JSON.stringify(finalArray);

                $("#orderFileList").val(orderFileList);
                $("#requestType").val("CHANGE");
                $("#ptypeTpCd").val("FLOOR_PLAN");
                $("#houscplxCd").val(houscplxCd);
                $("#form_submit").submit();
            }
        </script>
    </div>
</div>
<form:form id="form_submit" action="/cm/housingcplx/info/editHousingCplxImageAddInfoAction" name="housingcplx" commandName="housingcplx" method="post">
<input type="text" id="requestType" name="requestType" value="CHANGE" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ptypeTpCd" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="orderFileList" name="orderFileList" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="form_del" action="/cm/housingcplx/info/editHousingCplxImageInfoAction" name="housingcplx" commandName="housingcplx" method="post">
<input type="text" id="requestType_del" name="requestType" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ptypeTpCd_del" name="ptypeTpCd" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="removeFileList" name="removeFileList" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ordno" name="ordno" class="form-control" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_modal" action="/cm/housingcplx/info/editHousingCplxImageInfoAction" enctype="multipart/form-data" name="housingcplx" commandName="housingcplx" method="post">
<input type="text" id="requestType_mod" name="requestType" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="ptypeTpCd_mod" name="ptypeTpCd" class="form-control" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="isFileChangeId" name="isFileChange" class="form-control" style="width:0;height:0;visibility:hidden"/>
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
						<tbody>
							<tr>
								<th>항목</th>
								<td>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="inputGroupFile01" name="file" aria-describedby="inputGroupFileAddon01" accept=".png, .jpg, .jpeg, .gif">
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
								<td style="display:none"><input type="text" id="ptypeTpCd" name="ptypeTpCd" class="form-control"/></td>
								<td style="display:none"><input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" class="form-control"/></td>
								<td style="display:none"><input type="text" id="prevFileNm" name="prevFileNm" class="form-control"/></td>
							</tr>
							<tr>
							    <th>평형명</th>
								<td><input type="text" id="ptypeNm" name="ptypeNm" class="form-control"/></td>
							</tr>
							<tr>
							    <th>평형 면적량</th>
								<td><input type="text" id="ptypeDimQty" name="ptypeDimQty" class="form-control"/></td>
							</tr>
							<tr>
							    <th>공급 면적량</th>
								<td><input type="text" id="supDimQty" name="supDimQty" class="form-control"/></td>
							</tr>
						</tbody>
					</table>
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
                <div>
                    <h5 class="modal-title">타입별 평면도 등록</h5>
                </div>
                <div style="margin-top: 15px;">
                    <h6 style="color: red;">타입별 평면도 등록시 평형명, 평형면적량, 공급 면적량은 필수 값입니다.</h6>
                </div>
                <button type="button" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt=""></button>
            </div>
            <!-- //모달헤더 -->

				<!-- 모달바디 -->
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-lg-12 col-sm-12 col-11 main-section">
                                <div class="form-group">
                                    <div class="file-loading">
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
                        #link-url {
                            display:none;
                        }

                        .krajee-default.file-preview-frame {
                            height: 350px;
                        }

                        .modal-body {
                          padding: 1px;
                        }
					</style>
					<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
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
							userInputParam: true,
							uploadUrl: "/cm/housingcplx/info/addHousingCplxImageInfoAction?houscplxCd=" + houscplxCd + "&ptypeTpCd=FLOOR_PLAN",
							uploadExtraData: function() {

							    var check_ptype = document.getElementsByName("ptypeDimQty[]");
                                var check_sup = document.getElementsByName("supDimQty[]");
                                var flag1 = true;
                                var flag2 = true;
                                for(var i = 0 ; i < check_ptype.length ; i++){
                                    console.log(check_ptype[i].value);
                                    if(check_ptype[i].value == ""){
                                        flag1 = false;
                                    }
                                }
                                for(var i = 0 ; i < check_sup.length ; i++){
                                    console.log(check_sup[i].value);
                                    if(check_sup[i].value == ""){
                                        flag2 = false;
                                    }
                                }





							    var fileName = document.getElementsByName("file[]")[0]["files"];
							    var fileNames;

							    for(i=0; i<fileName.length; i++) {
                                    if (i == 0) {
                                        fileNames = fileName[i]["name"];
                                    } else {
                                        fileNames += "/" + fileName[i]["name"];
                                    }
                                    console.log(i);
							    }

                                var name = document.getElementsByName("ptypeNm[]");
                                var ptypeNames;

                                for(i=0; i<name.length; i++) {
                                    if (i == 0) {
                                        ptypeNames = name[i].value;
                                    } else {
                                        ptypeNames += "," + name[i].value;
                                    }
                                    console.log(i);
                                }

                                var ptypeDimQtyName = document.getElementsByName("ptypeDimQty[]");
                                var ptypeQtys;

                                for(i=0; i<ptypeDimQtyName.length; i++) {
                                    if (i == 0) {
                                        ptypeQtys = ptypeDimQtyName[i].value;
                                    } else {
                                        ptypeQtys += "," + ptypeDimQtyName[i].value;
                                    }
                                    console.log(i);
                                }

                                var supDimQtyName = document.getElementsByName("supDimQty[]");
                                var supQtys;

                                for(i=0; i<supDimQtyName.length; i++) {
                                    if (i == 0) {
                                        supQtys = supDimQtyName[i].value;
                                    } else {
                                        supQtys += "," + supDimQtyName[i].value;
                                    }
                                    console.log(i);
                                }

								return {
								    _fileNames: fileNames,
									_ptypeNames: ptypeNames,
									_ptypeQtys: ptypeQtys,
									_supQtys: supQtys,
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
							},
							fileActionSettings:{
                                showZoom: false,
                            }
						});

						$('#file').on('filebatchuploaderror', function(event, data, previewId, index) {
                            alert("타입별 평면도 이미지 업로드중 장애가 발생하였습니다.");
                            console.log("event: " + event);
                            console.log("data: " + data);
                            console.log("previewId: " + previewId);
                            console.log("index: " + index);
                            location.href = "/cm/housingcplx/info/floor/edit?houscplxCd=" + houscplxCd + "&ptypeTpCd=FLOOR_PLAN";
                        });

                        $('#file').on('filebatchuploadsuccess', function(event, data, previewId, index) {
                            if (data.response.uploaded == "OK") {
                                alert("타입별 평면도 이미지 업로드가 완료되었습니다.");
                            } else if (data.response.uploaded == "ERROR") {
                                alert("타입별 평면도 이미지 업로드중 장애가 발생하였습니다.");
                            }
                            console.log("event: " + event);
                            console.log("data: " + data);
                            console.log("previewId: " + previewId);
                            console.log("index: " + index);
                            location.href = "/cm/housingcplx/info/floor/edit?houscplxCd=" + houscplxCd + "&ptypeTpCd=FLOOR_PLAN";
                        });
					</script>
				</div>
				<!-- //모달바디 -->
			</div>
		</div>
	</div>
	<c:set var="houscplxCd" value="${houscplxCd}"/>

