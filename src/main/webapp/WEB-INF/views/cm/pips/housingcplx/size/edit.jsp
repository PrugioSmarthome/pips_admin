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
    function OnChange(e){
        var table = $('#table1').DataTable();
        table.search($(e).val()).draw();
    }

    function submit_btn(){

        var dongCnt = $("input[name=dong]").length;
        var hoseCnt = $("input[name=hose]").length;
        var qtyCnt = $("input[name=qty]").length;
        var typeCnt = $("input[name=type]").length;

        for(var i = 1 ; i < dongCnt ; i++){
            if($("input[name=dong]").eq(i).val().length < 4){
            alert("동을 4자리 형식으로 입력해주세요.");
            return;
            }
        }
        for(var i = 1 ; i < hoseCnt ; i++){
            if($("input[name=hose]").eq(i).val().length < 4){
            alert("호를 4자리 형식으로 입력해주세요.");
            return;
            }
        }
        for(var i = 1 ; i < qtyCnt ; i++){
            if($("input[name=qty]").eq(i).val() == ""){
            alert("전용면적을 입력해주세요.");
            return;
            }
        }
        for(var i = 1 ; i < typeCnt ; i++){
            if($("input[name=type]").eq(i).val() == ""){
            alert("타입을 입력해주세요.");
            return;
            }
        }

        var cnt = $("input[name=dong]").length;
        var finalArray = new Array();
        for(var i = 0 ; i < cnt ; i++){
            var jsonObject = new Object();
            jsonObject.dongNo = $("input[name='dong']").eq(i).attr("value");
            jsonObject.hoseNo = $("input[name='hose']").eq(i).attr("value");
            var type = $("input[name='type']").eq(i).attr("value");
            if(type == "" || type == null){
                jsonObject.ptypeNm = "-";
            }else{
                jsonObject.ptypeNm = type;
            }
            jsonObject.dimQty = $("input[name='qty']").eq(i).attr("value");
            jsonObject.crerId = "<c:out value="${session_user.userId}"/>";
            finalArray.push(jsonObject);
        }
        var householdList = JSON.stringify(finalArray);

        $("#householdList").val(householdList);
        $("#form_modify").submit();
    }


    function del(e){
        var conf = confirm("삭제하시겠습니까?");
        if(conf == true){
            $("#hsholdId").val(e);
            $("#form_del").submit();
        }
    }

    function alldel(){
        var conf = confirm("전체삭제 실행 시 데이터 원복이 불가 합니다. 등록한 데이터를 모두 삭제 하시겠습니까?");
        if(conf == true){
            $("#form_all_del").submit();
        }
    }

    function add(){
        var table = $('#table1').DataTable();
            table.row.add([
                    "<input type='text' name='dong' class='form-control' maxlength='4'/>",
                    "<input type='text' name='hose' class='form-control' maxlength='4'/>",
                    "<input type='text' name='qty' class='form-control' maxlength='20'/>",
                    "<input type='text' name='type' class='form-control' maxlength='20'/>",
                    "<button class='btn btn-brown deleteRow' type='button'>삭제</button>"
            ]).draw();
    }

    function dataArray(datas){
        var zero = "0";
        var zero2 = "00";
        var zero3 = "000";
        var table = $('#table1').DataTable({
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
        for(var i = 0 ; i < datas.length ; i++){
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
            var type = datas[i].타입;
            if(type == "" || type == null){
                type = "-";
            }else{
                type = datas[i].타입;
            }
            table.row.add([
                    "<input type='text' name='dong' disabled value='"+dong+"' class='form-control'/>",
                    "<input type='text' name='hose' disabled value='"+hose+"' class='form-control'/>",
                    "<input type='text' name='qty' value='"+datas[i].전용면적+"' class='form-control'/>",
                    "<input type='text' name='type' value='"+type+"' class='form-control'/>",
                    "<button class='btn btn-brown deleteRow' type='button'>삭제</button>"
            ]).draw();
        }
     }

     function back(){
        window.history.back();
     }
</script>

<div id="container">
    <div class="container">

        <div class="top_area">
            <h2 class="tit">세대별 평형 수정</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>세대별 평형</li>
                <li>세대별 평형 수정</li>
            </ul>
        </div>
        <c:if test="${householdCnt == '0'}">
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
                                    <input type="file" class="custom-file-input" id="upload" value="파일찾기" aria-describedby="inputGroupFileAddon01" name="files[]" accept=".xls, .xlsx"/>
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
        </c:if>



        <div class="table_wrap">
            <c:choose>
                <c:when test="${householdCnt == '0'}">
                    <div class="tbl_top_area no_count">
                        <div class="left_area">
                            <button class="btn btn-gray" type="button" onclick="alldel();">전체삭제</button>
                        </div>
                        <div class="right_area">
                        	<button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tbl_top_area no_count">
                        <div class="left_area">

                        </div>
                        <div class="right_area">
                        	<button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">전용면적 (m<sup>2</sup>)</th>
                        <th scope="col">타입</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody style="text-align:center;">
                    <c:forEach items="${household}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center"><input type="text" disabled name="dong" class="form-control" value="<c:out value="${list.dongNo}"/>" maxlength="4"/></td>
                            <td class="text-center"><input type="text" disabled name="hose" class="form-control" value="<c:out value="${list.hoseNo}"/>" maxlength="4"/></td>
                            <td class="text-center"><input type="text" name="qty" class="form-control" value="<c:out value="${list.dimQty}"/>" maxlength="20"/></td>
                            <td class="text-center"><input type="text" name="type" class="form-control" value="<c:out value="${list.ptypeNm}"/>" maxlength="20"/></td>
                            <c:choose>
                                <c:when test="${list.userCnt == '0'}">
                                    <td class="text-center"><button class="btn btn-brown" type="button" onclick="del('<c:out value="${list.hsholdId}"/>');">삭제</button></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="text-center"></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area no_paging">
                <div class="right_area">
                    <button class="btn btn-brown" type="button" onclick="back();">취소</button>
                    <button class="btn btn-bluegreen" type="button" onclick="submit_btn()">저장</button>
                </div>
            </div>
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

    </div>

</div>

<form:form id="form_modify" action="/cm/housingcplx/info/editHouseholdPtypeAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="householdList" name="householdList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="pagename" name="pagename" value="update" style="display:none;"/>
</form:form>

<form:form id="form_del" action="/cm/housingcplx/info/deleteHouseholdPtypeAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="isAll" name="isAll" value="N" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_all_del" action="/cm/housingcplx/info/deleteHouseholdPtypeAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="allCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="isAll" name="isAll" value="Y" style="width:0;height:0;visibility:hidden"/>
</form:form>