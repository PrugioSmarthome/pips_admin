<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript" charset="utf-8" src="https://cdn.datatables.net/colreorder/1.5.4/js/dataTables.colReorder.min.js"></script>

<script type="text/javascript">
    $(document).ready(function(){

        localStorage.setItem('userInfo', btoa("<c:out value="${userId}"/>"+"/"+"<c:out value="${time}"/>"+"/"+"<c:out value="${userGroupName}"/>"));

        $('#user_table').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "데이터가 없습니다."
            },
            "colReorder": {
                'allowReorder': false
            }

        });

        var groupName = '<c:out value="${groupName}"/>';
        var userId = '<c:out value="${userId}"/>';
        if(groupName == "MULTI_COMPLEX_ADMIN"){
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
        }else{
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

        $("#select_name").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#searchCd").val("<c:out value="${houscplxCd}"/>");

        var start = "<c:out value="${startCrDt}"/>";
        var end = "<c:out value="${endCrDt}"/>";
        if(start != ""){
            $("#startCrDt").val(start);
            var day1 = start.substring(0,4);
            var day2 = start.substring(4,6);
            var day3 = start.substring(6,8);
            $('#start').val(day1+"."+day2+"."+day3);
        }
        if(end != ""){
            $("#endCrDt").val(end);
            var day1 = end.substring(0,4);
            var day2 = end.substring(4,6);
            var day3 = end.substring(6,8);
            $('#end').val(day1+"."+day2+"."+day3);
        }
        $("#Yn").val("<c:out value="${delYn}"/>");
        var stc = "<c:out value="${svrTpCd}"/>";
        $("#home_id").val(stc);
        $("#hmnetId").val(stc);
    });

    //단지 선택 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");
        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#select_name").val(strarray[1]);
        if(strarray[1] == "전체"){
            $("#searchCd").val("");
        }else{
            $("#searchCd").val(strarray[0]);
        }

        $("#closebtn").click();
    }


    function click_row(row){
        $("#houscplxCd").val(row);
        $("#flag").val("Y");
        $("#form_list").submit();
    }

    function search_btn(){
        //시작날짜
        $("#startCrDt").val($("#start").val().replace(/\./gi,''));
        //종료날짜
        $("#endCrDt").val($("#end").val().replace(/\./gi,''));
        //활성화 = N, 비활성화 = Y
        $("#delYn").val($("#Yn").val());
        //홈넷서버
        $("#hmnetId").val($("#home_id").val());

        $("#form_search").submit();
    }

    function register(){
        location.href = "/cm/housingcplx/info/add";
    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

    //엑셀 다운로드
    function excel(){
        var list = new Array();
        <c:forEach items="${housingCplxList}" var="housingCplx" varStatus="status">
            var cJson = new Object();
            cJson.등록일 = '<fmt:formatDate value="${housingCplx.crDt}" pattern="yyyy-MM-dd"/>';
            <c:if test="${housingCplx.delYn eq 'N'}">
                cJson.단지상태 = '활성화';
            </c:if>
            <c:if test="${housingCplx.delYn eq 'Y'}">
                cJson.단지상태 = '비활성화';
            </c:if>
            cJson.단지코드 = "<c:out value="${housingCplx.houscplxCd}"/>";
            cJson.단지명 = "<c:out value="${housingCplx.houscplxNm}"/>";
            <c:if test="${housingCplx.bizcoCd eq 'COMAX'}">
                cJson.홈넷서버 = '코맥스';
            </c:if>
            <c:if test="${housingCplx.bizcoCd eq 'ICONTROLS'}">
                cJson.홈넷서버 = 'HDC 아이콘트롤스';
            </c:if>
            <c:if test="${housingCplx.bizcoCd eq 'HYUNDAITEL'}">
                cJson.홈넷서버 = '현대통신';
            </c:if>
            <c:if test="${housingCplx.bizcoCd eq 'KOCOM'}">
                cJson.홈넷서버 = '코콤';
            </c:if>
            <c:if test="${housingCplx.bizcoCd eq 'OTHER'}">
                cJson.홈넷서버 = '기타';
            </c:if>

            list.push(cJson);
        </c:forEach>


        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "단지목록";
        param.tableHeader = "['등록일', '단지상태', '단지코드', '단지명', '홈넷서버']";
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
                link.download= today+"_단지목록.xlsx";
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
            <h2 class="tit">단지목록</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>단지목록</li>
            </ul>
        </div>
        <div class="search_area">
            <table>
                <tbody>
                    <c:if test="${groupName eq 'MULTI_COMPLEX_ADMIN'}">
                        <colgroup>
                            <col style="width:7%"/>
                            <col style="width:35%"/>
                            <col style="width:7%"/>
                            <col style="width:39%"/>
                            <col style="width:12%"/>
                        </colgroup>
                     </c:if>
                    <tr>
                        <th>등록일</th>
                        <td style="width:350px;">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" id="start" name="start" />
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" id="end" name="end" />
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
                    <c:if test="${groupName ne 'MULTI_COMPLEX_ADMIN'}">
                        <th>단지상태</th>
                        <td>
                            <select class="custom-select" id="Yn">
                                <option value="all">전체</option>
                                <option value="N">활성화</option>
                                <option value="Y">비활성화</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    </c:if>
                        <th>단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" id="select_name" class="form-control" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1">단지선택</button>
                                </div>
                            </div>
                        </td>
                        <c:if test="${groupName ne 'MULTI_COMPLEX_ADMIN'}">
                        <th>서버</th>
                        <td>
                            <select class="custom-select" id="home_id">
                                <option value="all">전체</option>
                                <option value="HOUSCPLX_SVR">단지서버</option>
                                <option value="UNIFY_SVR">통합서버</option>
                            </select>
                        </td>
                        </c:if>

                        <td>
                            <div class="input-group">
                                <div class="input-group-append">
                                    <button class="btn btn-brown btn-sm" type="button" onclick="search_btn();">검색</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <c:if test="${groupName ne 'MULTI_COMPLEX_ADMIN'}">
        <div class="table_wrap">
            <div class="tbl_top_area no_count">
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="register();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
        </c:if>
            <table class="table" id="user_table">
                <thead>
                    <tr>
                        <th scope="col">등록일</th>
                        <th scope="col">단지상태</th>
                        <th scope="col">단지코드</th>
                        <th scope="col">단지명</th>
                        <th scope="col">홈넷서버</th>
                    </tr>
                </thead>
                <tbody style="text-align:center;">
                    <c:forEach items="${housingCplxList}" var="housingCplx" varStatus="status">
                    <tr>
                        <td><a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><fmt:formatDate value="${housingCplx.crDt}" pattern="yyyy-MM-dd HH:mm" /></a></td>
                        <td>
                          <c:choose>
                          <c:when test="${housingCplx.delYn eq 'N'}">
                              <a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="활성화" /></a>
                          </c:when>
                          <c:when test="${housingCplx.delYn eq 'Y'}">
                              <a href="" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="비활성화" /></a>
                          </c:when>
                          <c:otherwise>
                              <c:out value="" />
                          </c:otherwise>
                          </c:choose>
                        </td>
                        <td><a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="${housingCplx.houscplxCd}"/></a></td>
                        <td><a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="${housingCplx.houscplxNm}"/></a></td>
                        <td>
                          <c:choose>
                          <c:when test="${housingCplx.bizcoCd eq 'COMAX'}">
                              <a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="코맥스" /></a>
                          </c:when>
                          <c:when test="${housingCplx.bizcoCd eq 'ICONTROLS'}">
                              <a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="HDC 아이콘트롤스" /></a>
                          </c:when>
                          <c:when test="${housingCplx.bizcoCd eq 'HYUNDAITEL'}">
                              <a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="현대통신" /></a>
                          </c:when>
                          <c:when test="${housingCplx.bizcoCd eq 'KOCOM'}">
                              <a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="코콤" /></a>
                          </c:when>
                          <c:when test="${housingCplx.bizcoCd eq 'OTHER'}">
                              <a href="#" onclick="click_row('<c:out value="${housingCplx.houscplxCd}"/>');"><c:out value="기타" /></a>
                          </c:when>
                          <c:otherwise>
                              <c:out value="" />
                          </c:otherwise>
                          </c:choose>
                        </td>
                    </tr>
                    </c:forEach>
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

<form:form id="form_list" action="/cm/housingcplx/info/intro/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="flag" name="flag" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_search" action="/cm/housingcplx/info/list" name="housingcplx" commandName="housingcplx" method="post">
    <input type="text" id="delYn" name="delYn" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="hmnetId" name="svrTpCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxNm" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="searchCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="startCrDt" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="endCrDt" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
</form:form>