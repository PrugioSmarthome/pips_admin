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
        $("#divHouscplx").hide();
        $("#btnHouscplx").hide();


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
                "info": "Total <span>_TOTAL_</span>???",
                "infoEmpty":"Total <span>0</span>???",
                "emptyTable": "????????? ???????????? ????????????."
            },
            ajax:{
                "url":"/cm/common/housingcplx/list",
                "dataSrc":""
                },
                columns:[
                    {"data":"houscplxNm",
                        "render":function(data, type, row, meta){
                            if(meta.row == 0){
                                return "??????";
                            }else{
                                return row["houscplxNm"];
                            }
                        }
                    },
                    {"data":"houscplxCd",
                            "render":function(data, type, row, meta){
                                if(meta.row == 0){
                                    return "<input class='btn btn-gray btn-sm' type='button' id='_??????' value='??????' onclick='selectbtn(this)'/>";
                                }else{
                                    var nm = row['houscplxNm'];
                                    return "<input class='btn btn-gray btn-sm' type='button' id='"+data+"_"+nm+"' value='??????' onclick='selectbtn(this)'/>";
                                }
                            }
                    }
                ]
        });

    });

    //????????????
    function cancel(){
        location.href = "/cm/system/document/list";
    }

    //??????????????? ???????????????
    function isValid(){
        var RegExp = /[\,;:|*~`!^\-_+???<>@\#$%&\'\"\\\=]/gi;
        var fileCheck = document.getElementById("inputGroupFile01").value;
        var grpTpCd = $("#selLnkAtchFileGrpTpCd").val();
        var tpCd =  $("#selLnkAtchFileTpCd").val();

        if(grpTpCd == "NULL"){
            alert("????????? ??????????????????.");
            return false;
        }

        if(tpCd == "NULL"){
            alert("??????????????? ??????????????????.");
            return false;
        }

        if(grpTpCd != "NULL") {
            if (grpTpCd == "MANUAL") {
                if (tpCd != "SIMPLE_MANUAL" && tpCd != "DETAIL_MANUAL") {
                    alert("????????? ??????????????? ?????? ???????????????.");
                    return false;
                }
            } else if (grpTpCd == "TERMS") {
                if (tpCd != "USE_TERMS" && tpCd != "PRIVACY_TERMS" && tpCd != "LOCATION_TERMS") {
                    alert("?????? ??????????????? ?????? ???????????????.");
                    return false;
                }
            }
        }

        if(!fileCheck){
            alert("????????? ????????? ??????????????????.");
            return false;
        }

        var fileSize = document.getElementById("inputGroupFile01").files[0].size;

        var maxSize = 500 * 1024 * 1024;

        if(fileSize > maxSize){
           alert("???????????? ???????????? 500MB ????????? ?????? ???????????????. ");
           return false;
        }

        return true;
    }

    function formsubmit(){
        if(isValid() == false){
            return;
        }
        $("#lnkAtchFileGrpTpCd").val($("#selLnkAtchFileGrpTpCd").val());
        $("#lnkAtchFileTpCd").val($("#selLnkAtchFileTpCd").val());
        $("#useYn").val($("input[name='radio']:checked").val());
        $("#delYn").val("N");

        if ($("#selComplex").val() == 'PART') {
            $("#householdList").val(JSON.stringify(selHouscplxCd));
            $("#svcNotiTpCd").val("PART");
        } else {
            $("#householdList").val('');
            $("#svcNotiTpCd").val("ALL");
        }

        $("#form_add").submit();
    }

    //???????????? ?????? ????????? ??????????????? ??????
    function houscplxNm_popup(){
        $("#modal1").css({'opacity':1, 'filter':'alpha(opacity=1)'});
    }

    //???????????? ???????????? ????????? ??????????????????
    function selectbtn(e){
        var str = e.getAttribute("id");

        var strarray = str.split("_");
        taggle.add(strarray[1]);
        selHouscplxCd.push(str);

        $("#closebtn").click();

    }

    //?????? ??????????????? ??????
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    function back(){
        window.history.back();
    }

</script>
<form:form id="form_add" action="/cm/system/document/addDocumentAction" method="post" enctype="multipart/form-data" commandName="detail">
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">?????? ????????????</h2>
            <ul class="location">
                <li>????????? ??????</li>
                <li>?????? ??????</li>
                <li>?????? ????????????</li>
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
                        <th>??????</th>
                        <td>
                            <select name="selLnkAtchFileGrpTpCd" id="selLnkAtchFileGrpTpCd" class="custom-select">
                                <option value="NULL">??????</option>
                                <option value="MANUAL">?????????</option>
                                <option value="GUIDE">????????? ?????????</option>
                                <option value="LIVING_GUIDE">??????????????????</option>
                                <option value="INTRO_GUIDE">?????????????????????</option>
                            </select>
                        </td>
                        <th>????????????</th>
                        <td>
                            <select name="selLnkAtchFileTpCd" id="selLnkAtchFileTpCd" class="custom-select">
                                <option value="NULL">??????</option>
                                <option value="SIMPLE_MANUAL">?????? ?????? ?????????</option>
                                <option value="DETAIL_MANUAL">?????? ?????? ?????????</option>
                                <option value="LIVING_GUIDE">?????? ?????????</option>
                                <option value="INTRO_GUIDE">?????????????????????</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th rowspan="2">??????</th>
                        <td>
                            <select name="selComplex" id="selComplex" class="custom-select">
                                <option value="ALL" <c:if test="${documentDetail.svcNotiTpCd eq 'ALL'}">selected</c:if>>??????</option>
                                <option value="PART" <c:if test="${documentDetail.svcNotiTpCd eq 'PART'}">selected</c:if>>????????????</option>
                            </select>
                        </td>
                        <td colspan="2">
                            <div id="btnHouscplx">
                                <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" onclick="houscplxNm_popup();">????????????</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <div id="divHouscplx"></div>
                        </td>
                    </tr>
                    <tr>
                        <th>?????????</th>
                        <td colspan="3">
                            <div class="custom-control custom-radio d-inline-block mr-3">
                                <input type="radio" class="custom-control-input" value="N" id="radio1" name="radio">
                                <label class="custom-control-label" for="radio1">No</label>
                            </div>
                            <div class="custom-control custom-radio d-inline-block">
                                <input type="radio" class="custom-control-input" value="Y" checked id="radio2" name="radio">
                                <label class="custom-control-label" for="radio2">Yes</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>????????????</th>
                        <td colspan="3">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputGroupFile01" value="????????????" name="inputGroupFile01" aria-describedby="inputGroupFileAddon01" accept=".pdf"/>
                                <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                            </div>
                            <script type="text/javascript">
                                $('#inputGroupFile01').on('change',function(){
                                    var fileName = $(this).val();
                                    $(this).next('.custom-file-label').html(fileName);
                                    $("#isAttachFile").val("Y");
                                })
                            </script>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">??????</button>
                <button class="btn btn-bluegreen" type="button" onclick="formsubmit();">??????</button>
            </div>
        </div>
    </div>
</div>

    <input type="text" id="lnkAtchFileGrpTpCd" name="lnkAtchFileGrpTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="lnkAtchFileTpCd" name="lnkAtchFileTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="useYn" name="useYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="delYn" name="delYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="isAttachFile" id="isAttachFile" style="width:0;height:0;visibility:hidden"/>
    <input type="text" name="householdList" id="householdList" style="width:0;height:0;visibility:hidden"/>
</form:form>

<!-- ???????????? ?????? -->
<div class="modal fade" id="modal1" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered " role="document">
        <div class="modal-content">
            <!-- ???????????? -->
            <div class="modal-header">
                <h5 class="modal-title">????????????</h5>
                <button type="button" id="closebtn" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //???????????? -->

            <!-- ???????????? -->
            <div class="modal-body">
                <!-- ???????????? -->
                <div class="search_area">
                    <table>
                        <colgroup>
                            <col style="width:70px"/>
                            <col />
                            <col style="width:95px"/>
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>?? ?????????</th>
                                <td><input type="text" class="form-control" id="search_text"/></td>
                                <td class="text-right"><button type="button" id="search_btn" onclick="list_search();" class="btn btn-brown btn-sm">??????</button></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //???????????? -->

                <!-- ??????????????? -->
                <div class="tbl_top_info mt-4">
                    <div class="total"></div>
                </div>
                <!-- //??????????????? -->

                <!-- ????????? -->
                <div class="table_wrap mt-3">
                    <table class="table" id="list_table" style="width:100%;text-align:center;">
                        <thead>
                            <tr>
                                <th scope="col">??????</th>
                                <th scope="col">??????</th>
                            </tr>
                        </thead>
                        <tbody id="householdDeviceList">
                        </tbody>
                    </table>
                </div>
                <!-- //????????? -->
            </div>
            <!-- //???????????? -->
        </div>
    </div>
</div>