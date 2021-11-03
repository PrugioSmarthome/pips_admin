<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){


    });

    //취소버튼
    function cancel(){
        window.history.back();
    }

    function formsubmit(){

        if($("#csvData").val() == "" || $("#csvData").val() == null){
            alert("등록할 파일을 선택해주세요.");
            return;
        }

        $("#form_add").submit();
    }

     function csvFileUpload(data){


        var csv = data.split(",");
        var line = data.split("\n");

        var input = "";
        var flag = "true";
        var logs = "";

        var result = "<table id='mongoTestListTable' class='table' style='text-align:center;'><colgroup><col style='width:35%'/>";
        result += "<col style='width:10%'/><col style='width:10%'/><col style='width:15%'/><col style='width:15%'/><col style='width:15%'/>";
        result += "</colgroup><thead><tr><th>단지명</th><th>동</th><th>호</th><th>납기내</th><th>납기후</th><th>부과년월</th></tr></thead><tbody>";

        for(var i=1; i<line.length-1; i++){
            var csvData = line[i].split(",");

            if(csvData[0] == "" || csvData[0] == null){
                flag = "false";
                logs = "단지명";
                break;
            }
            if(csvData[1] == "" || csvData[1] == null){
                flag = "false";
                logs = "동";
                break;
            }
            if(csvData[2] == "" || csvData[2] == null){
                flag = "false";
                logs = "호";
                break;
            }
            if(csvData[67] == "" || csvData[67] == null){
                flag = "false";
                logs = "부과년월";
                break;
            }

            result += "<tr>";
            result += "<td>"+csvData[0]+"</td>";
            result += "<td>"+csvData[1]+"</td>";
            result += "<td>"+csvData[2]+"</td>";
            result += "<td>"+csvData[39]+"</td>";
            result += "<td>"+csvData[40]+"</td>";
            result += "<td>"+csvData[67]+"</td>";
            result += "<tr>";

            input += line[i] + String.fromCharCode(26);

        }

        result += "</tbody></table>";

        $("#mongoTestList").empty();
        $("#mongoTestList").append(result);

        $("#csvData").val(input);

     }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //단지 선택 팝업에서 단지를 선택했을경우
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
        if(strarray[1] != "전체"){
            selectList("dong");
        }
    }

</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">테스트 등록</h2>
            <ul class="location">
                <li>테스트 등록</li>
                <li>테스트 등록</li>
                <li>테스트 등록</li>
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
                        <th>CSV파일</th>
                        <td colspan="3">
                            <div class="custom-file">
                                <input type="file" onchange="readFile(this)" class="custom-file-input" id="upload" value="파일찾기" aria-describedby="inputGroupFileAddon01" name="files[]" accept=".csv">
                                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                            </div>
                            <script type="text/javascript">

                            $('#upload').on('change',function(){
                                var fileName = $(this).val();
                                $(this).next('.custom-file-label').html(fileName);
                            })

                            function readFile(FILE_ELEMENT){
                                const READER = new FileReader();
                                READER.readAsText(FILE_ELEMENT.files[0], "EUC-KR");

                                READER.onload = function () {
                                    var data = READER.result;
                                    //console.log(data);
                                    csvFileUpload(data);
                                }

                            }

                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br/>

        <div id="mongoTestList" style="width: 100%; height:auto; max-height:700px; overflow-y:auto; overflow-x: hidden;">
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">등록</button>
            </div>
        </div>
    </div>
</div>

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

<form:form id="form_add" action="/cm/mongo/test/insertAction2" name="add" commandName="add" method="post">
      <input type="text" id="csvData" name="csvData" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>