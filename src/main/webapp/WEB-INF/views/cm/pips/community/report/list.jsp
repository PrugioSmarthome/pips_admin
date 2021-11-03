<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){
        $('#table1').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "데이터가 없습니다."
            }
        });

        var name = '<c:out value="${session_user.userGroupName}"/>';
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
                    "data":{"userId" : userId}
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
        }else {
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
        }

        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#list_houscplxNm_").val("<c:out value="${houscplxNm}"/>");
        $("#list_houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#delyn").val("<c:out value="${delYn}"/>");
        $("#delYn_").val("<c:out value="${delYn}"/>");
        var start = "<c:out value="${startCrDt}"/>";
        var end = "<c:out value="${endCrDt}"/>";
        if(start != ""){
            $("#startCrDt").val(start);
            $('#start').val(start);
        }
        if(end != ""){
            $("#endCrDt").val(end);
            $('#end').val(end);
        }
        var str = "<c:out value="${qstTitle}"/>";

        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");

        $("#qstTitle").val(str);
        $("#title_").val(str);
    });


    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#list_houscplxCd").val("");
        }else{
            $("#list_houscplxCd").val(strarray[0]);
        }

        $("#closebtn").click();

    }
    //검색버튼을 눌렀을경우
    function btn_search(){
        var RegExp = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

        var name =  "<c:out value="${session_user.userGroupName}"/>";
        var str = '';
        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            str = $("#title_").val();
        } else {
            str = $("#title").val();
        }

        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }

        if (str != '') {
            str = str.replace(/</g,"&lt;");
            str = str.replace(/>/g,"&gt;");
        }

        $("#title").val(str);
        $("#delYn_").val($("#delyn").val());
        $("#startCrDt").val($("#start").val().replace(/\./gi,''));
        $("#endCrDt").val($("#end").val().replace(/\./gi,''));
        $("#list_houscplxNm_").val($("#houscplxNm").val());

        $("#form_list").submit();
    }
    //검색버튼 눌렀을경우 유효성 체크
    function isValid(){
        if($("#houscplxNm").val() == ""){
            alert("단지명을 선택해주세요.");
            return false;
        }

        return true;
    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    function detailview(cd,no,dong,hose){
        $("#houscplxCd").val(cd);
        $("#blltNo").val(no);

        var hsholdId = cd + "." + dong + "." + hose
        $("#hsholdId").val(hsholdId);

        $("#form_detail").submit();
    }

    //엑셀 다운로드
    function excel(){
        var list = new Array();
        var name =  "<c:out value="${session_user.userGroupName}"/>";

        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            <c:forEach items="${reportList}" var="list" varStatus="status">
                var cJson = new Object();
                var title = "<c:out value="${list.title}"/>";
                title = title.replace(/</g,"&lt;");
                title = title.replace(/>/g,"&gt;");
                cJson.등록일 = '<fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/>';
                cJson.단지명 = '<c:out value="${list.houscplxNm}"/>';
                cJson.세대 = '<c:out value="${list.reportDongNo}"/>동<c:out value="${list.reportHoseNo}"/>호';
                cJson.유형 = '<c:out value="${list.blltTpDtlCdNm}"/>';
                cJson.제목 = title;
                cJson.상태 = '<c:out value="${list.blltStsCdNm}"/>';

                list.push(cJson);
            </c:forEach>
        } else {
            <c:forEach items="${reportList}" var="list" varStatus="status">
                var cJson = new Object();
                var title = "<c:out value="${list.title}"/>";
                title = title.replace(/</g,"&lt;");
                title = title.replace(/>/g,"&gt;");
                cJson.등록일 = '<fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/>';
                cJson.세대 = '<c:out value="${list.reportDongNo}"/>동<c:out value="${list.reportHoseNo}"/>호';
                cJson.유형 = '<c:out value="${list.blltTpDtlCdNm}"/>';
                cJson.제목 = title;
                cJson.상태 = '<c:out value="${list.blltStsCdNm}"/>';

                list.push(cJson);
            </c:forEach>
        }

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "생활불편신고 목록";
        if(name == "SYSTEM_ADMIN" || name == "SUB_SYSTEM_ADMIN"){
            param.tableHeader = "['등록일', '단지명', '세대', '유형', '제목', '상태']";
        }else{
            param.tableHeader = "['등록일', '세대', '유형', '제목', '상태']";
        }

        param.tableData = hJson;

        var date = new Date();
        var year = date.getFullYear();
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
          month = "0" + month;
        }
        if(day.length == 1){
          day = "0" + day;
        }
        var today = year + "" + month + "" + day;

        $.ajax({
            url: '/cm/common/excel/download',
            type: 'POST',
            data: param,
            traditional: true,
            xhrFields: {
                responseType: 'blob'
            },
            success: function(blob){

                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_생활불편신고 목록.xlsx";
                link.click();
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">생활불편신고 목록</h2>
            <ul class="location">
                <li>단지 커뮤니티 관리</li>
                <li>생활불편신고 관리</li>
                <li>생활불편신고 목록</li>
            </ul>
        </div>
        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
            <div class="search_area">
                <table>
                    <tbody>
                        <tr>
                            <th>· 등록일</th>
                            <td style="width:350px">
                                <div class="input-daterange input-group" id="datepicker">
                                    <input type="text" class="form-control inp_calendar" name="start" id="start"/>
                                    <span class="bul_space">~</span>
                                    <input type="text" class="form-control inp_calendar" name="end" id="end"/>
                                </div>
                                <script type="text/javascript">
                                    $('#start').datepicker({
                                        format: "yyyy.mm.dd",
                                        language: "ko",
                                        autoclose: true
                                    });
                                    $('#end').datepicker({
                                        format: "yyyy.mm.dd",
                                        language: "ko",
                                        autoclose: true
                                    });

                                </script>
                            </td>
                            <th>· 단지상태</th>
                            <td style="width:250px;">
                                <select name="delyn" id="delyn" class="custom-select">
                                    <option value="N">활성화</option>
                                    <option value="Y">비활성화</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>· 단지명</th>
                            <td style="width:250px;">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="houscplxNm" disabled />
                                    <div class="input-group-append">
                                        <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
                                    </div>
                                </div>
                            </td>
                            <th>· 제목</th>
                            <td>
                                <input type="text" id="title_" class="form-control" />
                            </td>
                            <td>
                                <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
            <div class="search_area">
                <table>
                    <tbody>
                        <tr>
                            <th>· 등록일</th>
                            <td style="width:350px">
                                <div class="input-daterange input-group" id="datepicker">
                                    <input type="text" class="form-control inp_calendar" name="start" id="start"/>
                                    <span class="bul_space">~</span>
                                    <input type="text" class="form-control inp_calendar" name="end" id="end"/>
                                </div>
                                <script type="text/javascript">
                                    $('#start').datepicker({
                                        format: "yyyy.mm.dd",
                                        language: "ko",
                                        autoclose: true
                                    });
                                    $('#end').datepicker({
                                        format: "yyyy.mm.dd",
                                        language: "ko",
                                        autoclose: true
                                    });

                                </script>
                            </td>
                        </tr>
                        <tr>
                            <th>· 단지명</th>
                            <td style="width:250px;">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="houscplxNm" disabled />
                                    <div class="input-group-append">
                                        <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
                                    </div>
                                </div>
                            </td>
                            <th>· 제목</th>
                            <td>
                                <input type="text" id="title_" class="form-control" />
                            </td>
                            <td>
                                <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
            <div class="search_area">
                <table>
                    <tbody>
                        <tr>
                            <th>· 등록일</th>
                            <td style="width:350px">
                                <div class="input-daterange input-group" id="datepicker">
                                    <input type="text" class="form-control inp_calendar" name="start" id="start"/>
                                    <span class="bul_space">~</span>
                                    <input type="text" class="form-control inp_calendar" name="end" id="end"/>
                                </div>
                                <script type="text/javascript">
                                    $('#start').datepicker({
                                        format: "yyyy.mm.dd",
                                        language: "ko",
                                        autoclose: true
                                    });
                                    $('#end').datepicker({
                                        format: "yyyy.mm.dd",
                                        language: "ko",
                                        autoclose: true
                                    });
                                </script>
                            </td>
                            <th>· 제목</th>
                            <td style="width:250px;">
                                <input type="text" id="title" class="form-control" />
                            </td>
                            <td>
                                <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </c:if>


        <div class="table_wrap">

            <table class="table" id="table1">
                <thead>
                    <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                        <tr>
                            <th scope="col">등록일</th>
                            <th scope="col">단지명</th>
                            <th scope="col">세대</th>
                            <th scope="col">유형</th>
                            <th scope="col">제목</th>
                            <th scope="col">상태</th>
                        </tr>
                    </c:if>
                    <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                        <tr>
                            <th scope="col">등록일</th>
                            <th scope="col">세대</th>
                            <th scope="col">유형</th>
                            <th scope="col">제목</th>
                            <th scope="col">상태</th>
                        </tr>
                    </c:if>
                </thead>
                <tbody>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN' || userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                    <c:forEach items="${reportList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.houscplxNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.reportDongNo}"/>동<c:out value="${list.reportHoseNo}"/>호</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.blltTpDtlCdNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.title}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.blltStsCdNm}"/></a></td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${userGroupName eq 'COMPLEX_ADMIN'}">
                    <c:forEach items="${reportList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.reportDongNo}"/>동<c:out value="${list.reportHoseNo}"/>호</a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.blltTpDtlCdNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.title}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.houscplxCd}"/>','<c:out value="${list.blltNo}"/>','<c:out value="${list.reportDongNo}"/>','<c:out value="${list.reportHoseNo}"/>')"><c:out value="${list.blltStsCdNm}"/></a></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
        </div>

    </div>
</div>
<form:form id="form_detail" action="/cm/community/report/view" name="detail" commandName="detail" method="post">
    <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="blltNo" name="blltNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="hsholdId" name="hsholdId" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="form_list" action="/cm/community/report/list" name="report" commandName="report" method="post">
      <input type="text" id="list_houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="list_houscplxNm_" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="title" name="title" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="delYn_" name="delYn" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="startCrDt" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="endCrDt" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
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