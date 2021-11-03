<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    #user_table_filter{
        visibility : hidden;
        width : 0;
        height : 0;
    }
    #table1_info{
        visibility : hidden;
    }
    #table1_paginate{
        visibility : hidden;
    }
</style>
<script type="text/javascript">
    function OnChange(e){
        var table = $('#table1').DataTable();
        table.search($(e).val()).draw();
    }

    function dataArray(data){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var insertArray = new Array();
        var regexpcheck;
        for(var i = 0 ; i < data.length ; i++){
            var newcJson = new Object();
            if(RegExp.test(data[i].성명) == true){
                regexpcheck = false;
            }
            newcJson.per = data[i].성명;
            newcJson.mp = data[i].휴대폰;
            newcJson.offc = data[i].사무실;
            newcJson.fax = data[i].팩스;
            newcJson.facltBizcoCaddrId = "";

            insertArray.push(newcJson);
        }

        if(regexpcheck == false){
            alert('성명에 특수문자는 사용할 수 없습니다.');
            return;
        }

        var hJson = JSON.stringify(insertArray);
        $("#facilityInfoCaddrList").val(hJson);

        $('#table1').DataTable({
            // 표시 건수기능 숨기기
            lengthChange: false,
            // 검색 기능 숨기기
            searching: false,
            // 정렬 기능 숨기기
            ordering: false,
            // 정보 표시 숨기기
            info: false,
            // 페이징 기능 숨기기
            paging: false,
            data: data,
            columns:[
                    {data: '성명'},
                    {data: '휴대폰'},
                    {data: '사무실'},
                    {data: '팩스'}
            ]
        });
        $("#eximport_check").val("Y");
    }

    function add(){
        var table = $('#table1').DataTable();
            table.row.add([
                    "<input type='text' name='newperchrgNm' class='form-control' maxlength='50'/>",
                    "<input type='text' name='newmphoneNo' class='form-control'/>",
                    "<input type='text' name='newoffcPhoneNo' class='form-control'/>",
                    "<input type='text' name='newfaxNo' class='form-control'/>",
                    "<button class='btn btn-brown deleteRow' type='button'>삭제</button>"
            ]).draw();
    }

    function save() {
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var check = $("#eximport_check").val();
        if(check == "Y"){
            $("#editForm").submit();
        }else{
            var cnt = $("input[name=perchrgNm]").length;
            var newcnt = $("input[name=newperchrgNm]").length;
            var ArrayList = new Array();

            if(cnt > 0){
                for(var i = 0 ; i < cnt ; i++){
                    var jsonObject = new Object();
                    jsonObject.per = $("input[name='perchrgNm']").eq(i).attr("value");
                    jsonObject.mp = $("input[name='mphoneNo']").eq(i).attr("value");
                    jsonObject.offc = $("input[name='offcPhoneNo']").eq(i).attr("value");
                    jsonObject.fax = $("input[name='faxNo']").eq(i).attr("value");
                    jsonObject.facltBizcoCaddrId = $("input[name='facltBizcoCaddrId']").eq(i).attr("value");
                    ArrayList.push(jsonObject);
                }
            }

            if(newcnt > 0){
                for(var i = 0 ; i < newcnt ; i++){
                    var newjsonObject = new Object();
                    newjsonObject.per = $("input[name='newperchrgNm']").eq(i).attr("value");
                    newjsonObject.mp = $("input[name='newmphoneNo']").eq(i).attr("value");
                    newjsonObject.offc = $("input[name='newoffcPhoneNo']").eq(i).attr("value");
                    newjsonObject.fax = $("input[name='newfaxNo']").eq(i).attr("value");
                    newjsonObject.facltBizcoCaddrId = "";
                    ArrayList.push(newjsonObject);
                }
            }

            var facilityInfoCaddrList = JSON.stringify(ArrayList);

            $("#facilityInfoCaddrList").val(facilityInfoCaddrList);
            $("#editForm").submit();
        }
    }

    function deleteOne(facltBizcoId, facltBizcoCaddrId) {
        $("#delete_isAll").val("N");
        $("#delete_facltBizcoId").val(facltBizcoId);
        $("#delete_facltBizcoCaddrId").val(facltBizcoCaddrId);
        $("#delForm").submit();
    }

    function deleteAll(id) {
        $("#delete_isAll").val("Y");
        $("#delete_facltBizcoId").val(id);
        $("#delForm").submit();
    }

    function cancel(id){

        $("#facltBizcoId").val(id);
        $("#detailForm").submit();
    }
</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">시설업체정보 연락처 상세수정</h2>
            <ul class="location">
                <li>시설업체 관리</li>
                <li>시설업체 정보 관리</li>
                <li>시설업체정보 연락처 상세수정 </li>
            </ul>
        </div>

        <c:if test="${empty facilityInfoCaddrList}">
            <div class="table_wrap2" id="eximport">
                <table class="table2">
                    <colgroup>
                        <col style="width:150px"/>
                        <col />
                        <col style="width:150px"/>
                        <col />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>엑셀파일</th>
                            <td colspan="3">
                                <div class="custom-file">
                                    <input type="file" class="custom-file-input" id="upload" value="파일찾기" aria-describedby="inputGroupFileAddon01" name="files[]" accept=".xls, .xlsx">
                                    <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                </div>
                                <script type="text/javascript">
                                    $('#upload').on('change',function(){
                                        var tableDestroy = $('#table1').DataTable();
                                        tableDestroy.destroy();
                                        var fileName = $(this).val();
                                        $(this).next('.custom-file-label').html(fileName);
                                    })

                                    var ExcelToJSON = function() {
                                      this.parseExcel = function(file) {
                                        var reader = new FileReader();

                                        reader.onload = function(e) {
                                          var data = e.target.result;
                                          var workbook = XLSX.read(data, {
                                            type: 'binary'
                                          });
                                          workbook.SheetNames.forEach(function(sheetName) {
                                            // Here is your object
                                            var XL_row_object = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
                                            var json_object = JSON.stringify(XL_row_object);
                                            var data = new Object();
                                            data = JSON.parse(json_object);

                                            //jQuery('#xlx_json').val( json_object );
                                            dataArray(data);
                                          })
                                        };

                                        reader.onerror = function(ex) {

                                        };

                                        reader.readAsBinaryString(file);
                                      };
                                    };

                                    function handleFileSelect(evt) {
                                        var files = evt.target.files; // FileList object
                                        var xl2json = new ExcelToJSON();
                                        xl2json.parseExcel(files[0]);
                                    }

                                    document.getElementById('upload').addEventListener('change', handleFileSelect, false);
                                </script>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:if>

        <div class="table_wrap">
            <div class="tbl_top_area no_count">
                <div class="left_area">
                    <button class="btn btn-gray" type="button" onclick="deleteAll('<c:out value="${facltBizcoId}"/>');">전체삭제</button>
                </div>
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <c:choose>
                        <c:when test="${not empty facilityInfoCaddrList}">
                            <tr>
                                <th scope="col">성명</th>
                                <th scope="col">휴대폰</th>
                                <th scope="col">사무실</th>
                                <th scope="col">팩스</th>
                                <th scope="col"></th>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <th scope="col">성명</th>
                                <th scope="col">휴대폰</th>
                                <th scope="col">사무실</th>
                                <th scope="col">팩스</th>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </thead>
                <tbody style="text-align:center">
                    <c:choose>
                        <c:when test="${not empty facilityInfoCaddrList}">
                            <c:forEach items="${facilityInfoCaddrList}" var="list" varStatus="status">
                                <tr>
                                    <td class="text-center"><input type="text" id="perchrgNm" name="perchrgNm" value="<c:out value="${list.perchrgNm}"/>" class="form-control" maxlength="50"/></td>
                                    <td class="text-center"><input type="text" id="mphoneNo" name="mphoneNo" value="<c:out value="${list.mphoneNo}"/>" class="form-control" /></td>
                                    <td class="text-center"><input type="text" id="offcPhoneNo" name="offcPhoneNo" value="<c:out value="${list.offcPhoneNo}"/>" class="form-control" /></td>
                                    <td class="text-center"><input type="text" id="faxNo" name="faxNo" value="<c:out value="${list.faxNo}"/>" class="form-control" /></td>
                                    <td class="text-center"><button class="btn btn-brown" type="button" onclick="deleteOne('<c:out value="${facltBizcoId}"/>', '<c:out value="${list.facltBizcoCaddrId}"/>');">삭제</button></td>
                                </tr>
                                <input type="text" id="facltBizcoCaddrId" name="facltBizcoCaddrId" value="<c:out value="${list.facltBizcoCaddrId}"/>" style="display:none;"/>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        <script type="text/javascript">
            var tables = $('#table1').DataTable({
                            "bLengthChange" : false,
                            "paging": false,
                            "ordering": false,
                            "info": false,
                            "filter": false,
                            "lengthChange": false,
                            "order": false,
                            "language": {
                                "info": "Total <span>_TOTAL_</span>건",
                                "infoEmpty":"Total <span>0</span>건",
                                "emptyTable": "데이터가 없습니다."
                            }
            });
            $(document).on('click','.deleteRow',function(){
                tables.row( $(this).parents('tr') ).remove().draw();
            });
        </script>
        <div class="tbl_btm_area" style="text-align: right; margin-top: 15px;">
            <div class="right_area">
                <button class="btn btn-bluegreen" type="button" onclick="save();">저장</button>
                <button class="btn btn-gray" type="button" onclick="cancel('<c:out value="${facltBizcoId}"/>');">취소</button>
            </div>
        </div>
    </div>

</div>
<input type="text" id="eximport_check" name="eximport_check" value="" style="width:0;height:0;visibility:hidden"/>
<form:form id="detailForm" action="/cm/system/facility/detailView" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="facltBizcoId" name="facltBizcoId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="editForm" action="/cm/system/facility/editFacilityInfoCaddrAction" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="facilityInfoCaddrList" name="facilityInfoCaddrList" style="display:none;"/>
    <input type="text" id="edit_houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="display:none;"/>
    <input type="text" id="edit_facltBizcoId" name="facltBizcoId" value="<c:out value="${facltBizcoId}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCdGoList" name="houscplxCdGoList" value="<c:out value="${houscplxCdGoList}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCdGoListNm" name="houscplxCdGoListNm" value="<c:out value="${houscplxCdGoListNm}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="delForm" action="/cm/system/facility/deleteFacilityInfoCaddrAction" name="facilityInfo" commandName="facilityInfo" method="post">
    <input type="text" id="delete_isAll" name="isAll" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delete_facltBizcoId" name="facltBizcoId" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delete_facltBizcoCaddrId" name="facltBizcoCaddrId" style="width:0;height:0;visibility:hidden"/>
</form:form>