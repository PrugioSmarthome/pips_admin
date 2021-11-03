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

    var name = '<c:out value="${session_user.userGroupName}"/>';
    if(name == "COMPLEX_ADMIN"){
        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
    }

        var userId = '<c:out value="${userId}"/>';
        if(name == "MULTI_COMPLEX_ADMIN"){
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
                    "url":"/cm/common/housingcplx/multiList",
                    "dataSrc":"",
                    "data":{"userId" : userId,
                            "noAll": "Y"}
                    },
                    columns:[
                        {"data":"houscplxCd",
                                "render":function(data, type, row, meta){
                                    var nm = row['houscplxNm'];
                                    return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='선택' onclick='selectbtn(this)'/>";
                                }
                        },
                        {"data":"houscplxNm",
                            "render":function(data, type, row, meta){
                                return row["houscplxNm"];
                            }
                        }
                    ]
            });
        }
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

        var houscplxNm = $("#houscplxNm").val();
        if(houscplxNm == "" || houscplxNm == null){
            alert("단지를 선택해주세요.");
            return;
        }

        var fileext = document.getElementById('upload').value;
        fileext = fileext.slice(fileext.indexOf(".") + 1).toLowerCase();

        if(fileext != "csv"){
            alert("csv파일만 등록할 수 있습니다.");
            return;
        }

        var csv = data.split(",");
        var line = data.split("\n");

        var lengthCheck = line[0].split(",");
        if(lengthCheck.length != 73){
            alert("파일 양식(길이)이 기존과 달라 등록할 수 없습니다.");
            return;
        }

        var input = "";
        var flag = "true";
        var logs = "";

        var result = "<table id='maintenanceFeeListTable' class='table' style='text-align:center;'><colgroup><col style='width:35%'/>";
        result += "<col style='width:10%'/><col style='width:10%'/><col style='width:15%'/><col style='width:15%'/><col style='width:15%'/>";
        result += "</colgroup><thead><tr><th>단지명</th><th>동</th><th>호</th><th>납기내</th><th>납기후</th><th>부과년월</th></tr></thead><tbody>";

        for(var i=1; i<line.length-1; i++){
            var csvData = line[i].split(",");

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
            result += "<td>"+houscplxNm+"</td>";
            result += "<td>"+csvData[1]+"</td>";
            result += "<td>"+csvData[2]+"</td>";
            result += "<td>"+csvData[39]+"</td>";
            result += "<td>"+csvData[40]+"</td>";
            result += "<td>"+csvData[67]+"</td>";
            result += "<tr>";

            input += line[i] + String.fromCharCode(26);

        }

        if(flag == "false"){
            alert("파일 양식("+logs+")이 기존과 달라 등록할 수 없습니다.");
            return;
        }

        result += "</tbody></table>";

        $("#maintenanceFeeList").empty();
        $("#maintenanceFeeList").append(result);

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
            <h2 class="tit">단지 관리비 신규등록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지 관리비 관리</li>
                <li>단지 관리비 등록</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col style="width:66%"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>단지명</th>
                        <td><input type="text" class="form-control" disabled id="houscplxNm"/></td>
                        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                        <td><button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1">단지선택</button></td>
                        </c:if>
                    </tr>
                </tbody>
            </table>
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

        <div id="maintenanceFeeList" style="width: 100%; height:auto; max-height:700px; overflow-y:auto; overflow-x: hidden;">
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

<form:form id="form_add" action="/cm/maintenance/fee/insertAction" name="add" commandName="add" method="post">
      <input type="text" id="csvData" name="csvData" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>