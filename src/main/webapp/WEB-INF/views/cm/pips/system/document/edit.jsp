<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">

<script src="<c:url value="/scripts/taggle.js" />"></script>
<style>
    .textarea {
      width: 100%;
      height: 300px;
      border: 1px solid red;
    }

    .taggle_list {
      float: left;
      padding: 0;
      margin: 0;
      width: 100%;
    }

    .taggle_input {
      border: none;
      outline: none;
      font-size: 16px;
      font-weight: 300;
    }

    .taggle_list li {
      float: left;
      display: inline-block;
      white-space: nowrap;
      font-weight: 500;
      margin-bottom: 5px;
    }

    .taggle_list .taggle {
      margin-right: 8px;
      background: #E2E1DF;
      padding: 5px 10px;
      border-radius: 3px;
      position: relative;
      cursor: pointer;
      transition: all .3s;
      -webkit-animation-duration: 1s;
              animation-duration: 1s;
      -webkit-animation-fill-mode: both;
              animation-fill-mode: both;
    }

    .taggle_list .taggle_hot {
      background: #cac8c4;
    }

    .taggle_list .taggle .close {
      font-size: 1.1rem;
      position: absolute;
      top: 10px;
      right: 3px;
      text-decoration: none;
      padding: 0;
      line-height: 0.5;
      color: #ccc;
      color: rgba(0, 0, 0, 0.2);
      padding-bottom: 4px;
      display: none;
      border: 0;
      background: none;
      cursor: pointer;
    }

    .taggle_list .taggle:hover {
      padding: 5px;
      padding-right: 15px;
      background: #ccc;
      transition: all .3s;
    }

    .taggle_list .taggle:hover > .close {
      display: block;
    }

    .taggle_list .taggle .close:hover {
      color: #990033;
    }

    .taggle_placeholder {
      position: absolute;
      color: #CCC;
      top: 12px;
      left: 8px;
      transition: opacity, .25s;
      -webkit-user-select: none;
         -moz-user-select: none;
          -ms-user-select: none;
              user-select: none;
    }

    .taggle_input {
      padding: 8px;
      padding-left: 0;
      float: left;
      margin-top: -5px;
      background: none;
      width: 100%;
      max-width: 100%;
    }

    .taggle_sizer {
      padding: 0;
      margin: 0;
      position: absolute;
      top: -500px;
      z-index: -1;
      visibility: hidden;
    }
    .note-toolbar{
        background-color:#ddd;
    }

</style>
<script type="text/javascript">
    var taggle;
    var selHouscplxCd = new Array();

    $(document).ready(function() {
        taggle = new Taggle('divHouscplx', {
            onTagRemove: function(event, tag) {
                for (var i = 0; i < selHouscplxCd.length; i++) {
                    var arrCd = selHouscplxCd[i].split("_");

                    if (arrCd[1] == tag) {
                        selHouscplxCd.splice(i, 1);
                    }
                }
            }
        });

        if ('${documentDetail.houscplxCd}' != '') {
            $("#divHouscplx").show();
            $("#btnHouscplx").show();
        } else {
            $("#divHouscplx").hide();
            $("#btnHouscplx").hide();
/*
            <c:forEach items="${houscplxList}" var="list" varStatus="status">
                var tagCd = '<c:out value="${list.houscplxCd}"/>';
                var tagNm = '<c:out value="${list.houscplxNm}"/>';

                taggle.add(tagNm);
                selHouscplxCd.push(tagCd + "_" + tagNm);
            </c:forEach>
*/
        }


        $('#selComplex').change(function() {

            if ($("#selComplex").val() == 'PART') {
                $("#divHouscplx").show();
                $("#btnHouscplx").show();
            }

            if ($("#selComplex").val() == 'ALL') {
                $("#divHouscplx").hide();
                $("#btnHouscplx").hide();
            }
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

    });

    //취소버튼
    function cancel(){
        location.href = "/cm/system/document/list";
    }

    //등록하기전 유효성체크
    function isValid(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var fileCheck = document.getElementById("inputGroupFile01").value;
        var isNewfile = $("#isNewFile").val();

        if($("#selLnkAtchFileGrpTpCd").val() == "NULL"){
            alert("구분을 선택해주세요.");
            return false;
        }

        if($("#selLnkAtchFileTpCd").val() == "NULL"){
            alert("문서종류를 선택해주세요.");
            return false;
        }

        if (isNewfile == "Y") {
            if(!fileCheck){
                alert("첨부할 문서를 선택해주세요.");
                return false;
            }

            var fileSize = document.getElementById("inputGroupFile01").files[0].size;
            var maxSize = 500 * 1024 * 1024;

            if(fileSize > maxSize){
               alert("첨부파일 사이즈는 500MB 이내로 등록 가능합니다. ");
               return false;
            }
        }

        return true;
    }

    function formsubmit(){
        if(isValid() == false){
            return;
        }
        $("#useYn").val($("input[name='radio']:checked").val());
        $("#delYn").val("N");

        if ($("#selComplex").val() == 'PART') {
            $("#householdList").val(JSON.stringify(selHouscplxCd));
            $("#svcNotiTpCd").val("PART");
        } else {
            $("#householdList").val('');
            $("#svcNotiTpCd").val("ALL");
        }

        $("#form_edit").submit();
    }

    //단지선택 버튼 클릭시 단지리스트 팝업
    function houscplxNm_popup(){
        $("#modal1").css({'opacity':1, 'filter':'alpha(opacity=1)'});
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");

        var strarray = str.split("_");
        taggle.add(strarray[1]);
        selHouscplxCd.push(str);

        $("#closebtn").click();

    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    function back(){
        window.history.back();
    }

</script>
<form:form id="form_edit" action="/cm/system/document/editDocumentAction" method="post" enctype="multipart/form-data" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">문서 수정</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>문서 관리</li>
                <li>문서 수정</li>
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
                        <th>구분</th>
                        <td><c:out value="${documentDetail.lnkAtchFileGrpTpCdNm}"/></td>
                        <th>서비스명</th>
                        <td><c:out value="${documentDetail.lnkAtchFileTpCdNm}"/></td>
                    </tr>
                    <tr>
                        <th>노출여부</th>
                        <td colspan="3">
                            <c:choose>
                                <c:when test="${documentDetail.useYn eq 'Y'}">
                                    <div class="custom-control custom-radio d-inline-block mr-3">
                                        <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                        <label class="custom-control-label" for="radio1">No</label>
                                    </div>
                                    <div class="custom-control custom-radio d-inline-block">
                                        <input type="radio" class="custom-control-input" value="Y" checked id="radio2" name="radio">
                                        <label class="custom-control-label" for="radio2">Yes</label>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="custom-control custom-radio d-inline-block mr-3">
                                        <input type="radio" class="custom-control-input" value="N" checked id="radio1" name="radio">
                                        <label class="custom-control-label" for="radio1">No</label>
                                    </div>
                                    <div class="custom-control custom-radio d-inline-block">
                                        <input type="radio" class="custom-control-input" value="Y" id="radio2" name="radio">
                                        <label class="custom-control-label" for="radio2">Yes</label>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <th rowspan="2">대상</th>
                        <td>
                            <select name="selComplex" id="selComplex" class="custom-select">
                                <option value="ALL" <c:if test="${empty documentDetail.houscplxCd}">selected</c:if>>전체</option>
                                <option value="PART" <c:if test="${not empty documentDetail.houscplxCd}">selected</c:if>>특정단지</option>
                            </select>
                        </td>
                        <td colspan="2">
                            <div id="btnHouscplx">
                                <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" onclick="houscplxNm_popup();">단지선택</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <div id="divHouscplx"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>파일정보</th>
                        <td colspan="3">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputGroupFile01" value="파일찾기" name="inputGroupFile01" aria-describedby="inputGroupFileAddon01" accept=".pdf"/>
                                <label class="custom-file-label" for="inputGroupFile01"><c:out value="${documentDetail.orgnlFileNm}"/></label>
                            </div>
                            <script type="text/javascript">
                                $('#inputGroupFile01').on('change',function(){
                                    var fileName = $(this).val();
                                    $(this).next('.custom-file-label').html(fileName);
                                    $("#isNewFile").val("Y");
                                })
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">저장</button>
            </div>
        </div>

    </div>

</div>
    <input type="text" id="grpTpCd" name="lnkAtchFileGrpTpCd" value="<c:out value="${documentDetail.lnkAtchFileGrpTpCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="tpCd" name="lnkAtchFileTpCd" value="<c:out value="${documentDetail.lnkAtchFileTpCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="lnkAtchFileId" name="lnkAtchFileId" value="<c:out value="${documentDetail.lnkAtchFileId}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="useYn" name="useYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delYn" name="delYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="isAttachFile" id="isAttachFile" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="isNewFile" id="isNewFile" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="householdList" id="householdList" style="width:0;height:0;visibility:hidden"/>
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