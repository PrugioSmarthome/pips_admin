<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/jszip.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.8.0/xlsx.js"></script>

<script type="text/javascript">
    $(document).ready(function () {

    });

     function dataArray(datas){
        var Jsonlist = JSON.stringify(datas);
        var qJsonArray = new Array();
        var zero = "0";
        var zero2 = "00";
        var zero3 = "000";
        for(var i = 0 ; i < datas.length ; i++){
            var cJson = new Object();
            var dong = datas[i].동;
            var hose = datas[i].호;

            if(dong.length < 4){dong = zero+dong;}
            else if(dong.length < 3){dong = zero2+dong;}
            else if(dong.length < 2){dong = zero3+dong;}
            else{dong = datas[i].동;}
            if(hose.length < 4){hose = zero+hose;}
            else if(hose.length < 3){hose = zero2+hose;}
            else if(hose.length < 2){hose = zero3+hose;}
            else{hose = datas[i].호;}
            cJson.dongNo = dong;
            cJson.hoseNo = hose;
            var type  = datas[i].타입;
            if(type == "" || type == null){
                type  = "-";
            }else{
                type  = datas[i].타입;
            }
            cJson.ptypeNm = type;
            cJson.dimQty = datas[i].전용면적;
            cJson.crerId = "<c:out value="${session_user.userId}"/>";
            qJsonArray.push(cJson);
        }
        var hJson = JSON.stringify(qJsonArray);
        $("#householdList").val(hJson);

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
                data:datas,
                columns:[
                        {"data": '동',
                            "render":function(data, type, row, meta){
                                var dong = row['동'];
                                var newdong;
                                if(dong.length < 4){newdong = zero+dong;}
                                else if(dong.length < 3){newdong = zero2+dong;}
                                else if(dong.length < 2){newdong = zero3+dong;}
                                else{newdong = dong;}
                                return newdong;
                            }
                        },
                        {"data": '호',
                            "render":function(data, type, row, meta){
                                var hose = row["호"];
                                var newhose;
                                if(hose.length < 4){newhose = zero+hose;}
                                else if(hose.length < 3){newhose = zero2+hose;}
                                else if(hose.length < 2){newhose = zero3+hose;}
                                else{newhose = hose;}
                                return newhose;
                            }
                        },
                        {"data": '전용면적'},
                        {"data": '타입',
                            "render":function(data, type, row, meta){
                                var type = row["타입"];
                                if(type == "" || type == null){
                                    type = "-";
                                }
                                return type;
                            }
                        }
                ]
            });
     }

     function back(){
        location.href = "/cm/housingcplx/info/householdPtype/view";
     }

     function save_btn(){
        $("#size_add").submit();
     }
</script>


<div id="container">

    <div class="container">
        <div class="top_area">
            <h2 class="tit">세대별 평형 등록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>세대별 평형</li>
                <li>세대별 평형 등록</li>
            </ul>
        </div>

        <div class="table_wrap2">
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
                            </script>
                            <script type="text/javascript">
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
                                    //console.log(JSON.parse(json_object));
                                    //jQuery('#xlx_json').val( json_object );
                                    dataArray(data);
                                  })
                                };

                                reader.onerror = function(ex) {
                                  console.log(ex);
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
        <div class="table_wrap2">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">전용면적</th>
                        <th scope="col">타입</th>
                    </tr>
                </thead>
                <tbody style="text-align:center" id="databody">

                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <input type="button" class="btn btn-brown" onclick="back();" value="취소"/>
                <input type="button" class="btn btn-brown" onclick="save_btn();" value="저장"/>
            </div>
        </div>
    </div>
</div>

<form:form action="/cm/housingcplx/info/editHouseholdPtypeAction" id="size_add" name="housingCplx" commandName="housingCplx" method="post">
    <input type="text" id="householdList" name="householdList" style="display:none;"/>
    <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="display:none;"/>
    <input type="text" id="pagename" name="pagename" value="add" style="display:none;"/>
</form:form>