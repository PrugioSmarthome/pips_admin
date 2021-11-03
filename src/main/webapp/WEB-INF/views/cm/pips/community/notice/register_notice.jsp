<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">

<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/editor.css" />">
<style>
    .dataTables_length {
            float: right !important;
    }
</style>
<script type="text/javascript">
    $(document).ready(function() {
        $('#cont').summernote({
              height: 300,
              popover: {
              image: [],
              link: [],
              air: []
              }
        });

        var userId = '<c:out value="${userId}"/>';
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
                "url":"/cm/common/housingcplx/multiList",
                "dataSrc":"",
                "data":{"userId" : userId,
                        "noAll": "Y"}
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

        $("#list_table").css("width","100%")

    });

    function form_submit(flag){
        var cont = $('#cont').summernote('code');
        var RegExp = /[\{\}\[\]\/;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;
        var str = $("#hmnetNotiCont").val();
        var title = $("#title").val();
        var newText = str.replace(/(<([^>]+)>)/ig,"");
        if($("#title").val() == ""){
            alert("제목을 입력해주세요.");
            return;
        }
        if($("#cont").val() == ""){
            alert("내용을 입력해주세요.");
            return;
        }
        if(RegExp.test(title) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if($("#multiYn").val() == "N"){
            alert("적용할 단지를 선택해주세요.");
            return;
        }

        title = title.replace(/</g,"&lt;");
        title = title.replace(/>/g,"&gt;");
        $("#title").val(title);

        $("#hmnetNotiCont").val(newText);
        $("#tlrncYn").val(flag);
        $("#form_list").submit();
    }

    function parseMe(value) {
        return value.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //단지선택창 열기
    function danjiSelect(){
        $("#modal1").modal();
    }

    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('box_chk');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;
            }
        }
    }

    //단지선택
    function danjiCheck(){
        var checkArray = new Array();
        var danjiArray = new Array();
        $("input[name=box_chk]:checked").each(function() {
            var val = $(this).val().split("_");
            checkArray.push(val[1]);
            danjiArray.push(val[0]);
        })

        if(checkArray[0] == null){
            alert("단지를 선택해주세요.");
            return;
        }

        var result = "<table id='danjiListTable' class='table' style='text-align:center;'><thead><tr><th style='text-align:center;'>관리 단지명</th></tr></thead><tbody>";
        for(var i=0;i<checkArray.length;i++){
            result += "<tr><td style='text-align:center;'>"+checkArray[i]+"</td></tr>";
        }
        result += "</tbody></table>";

        $("#danjiList").empty();
        $("#danjiList").append(result);

        $("#multiYn").val("Y");
        $("#danjiArray").val(danjiArray);

        $("#modal1").modal('hide');
    }

    //취소버튼
    function back(){
        window.history.back();
    }
</script>
<form:form id="form_list" action="/cm/community/addNoticeAction" name="community" commandName="community" method="post" enctype="multipart/form-data">
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">단지 공지사항 신규등록</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>단지 공지사항 관리</li>
                <li>단지 공지사항 목록</li>
                <li>단지 공지사항 신규등록</li>
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
                        <th>제목</th>
                        <td><input type="text" class="form-control" id="title" name="title" value="<c:out value="${community.title}"/>"/></td>
                    </tr>
                    <tr>
                        <th>공지내용</th>
                        <td><textarea name="cont" id="cont" value="${community.cont}"></textarea></td>
                    </tr>
                    <tr>
                        <th>홈넷공지내용</th>
                        <td><textarea style="width:100%;height:150px;" name="hmnetNotiCont" id="hmnetNotiCont" value="<c:out value="${community.hmnetNotiCont}"/>"></textarea></td>
                    </tr>
                    <tr>
                        <th>첨부파일</th>
                        <td>
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputGroupFile01" name="inputGroupFile01" aria-describedby="inputGroupFileAddon01" value="${community.file}" accept=".png, .jpg, .jpeg, .gif, .doc, .docx, .xls, .xlsx, .hwp, .ppt, .pptx, .txt"/>
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

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
            <br/>
            <button style="width:919px;" class="btn btn-gray btn-sm" type="button" data-toggle="modal" onclick="javascript:danjiSelect();">단지선택</button>
            <div id="danjiList" style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;">
            </div>
            <input type="text" name="multiYn" id="multiYn" value="N" style="width:0;height:0;visibility:hidden"/>
        </c:if>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="back();">취소</button>
                <button class="btn btn-gray" type="button" onclick="form_submit('N');">임시저장</button>
                <button class="btn btn-bluegreen" type="button" onclick="form_submit('Y');">공지게시</button>
            </div>
        </div>
        <input type="text" name="tlrncYn" id="tlrncYn" style="width:0;height:0;visibility:hidden"/>
        <input type="text" name="isAttachFile" id="isAttachFile" style="width:0;height:0;visibility:hidden"/>
        <input type="text" name="houscplxNm" id="houscplxNm" style="width:0;height:0;visibility:hidden"/>
        <input type="text" name="houscplxCd" id="houscplxCd" style="width:0;height:0;visibility:hidden"/>
        <input type="text" name="danjiArray" id="danjiArray" style="width:0;height:0;visibility:hidden"/>
    </div>
</div>
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
            <div class="container" style="width:518px; height:422px">
            <div class="row" style="width:518px; height:422px">
            <div class="col-lg-12 col-sm-12 col-11 main-section" style="width:518px; height:422px">
                <!-- 검색영역 -->
                <div class="search_area" style="width:488px; height:98px">
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
                <div class="tbl_top_info mt-4" style="width:488px;">
                    <div class="total"></div>
                </div>
                <!-- //테이블상단 -->

                <!-- 테이블 -->
                <div style="width: 488px; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;" class="table_wrap mt-3">
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