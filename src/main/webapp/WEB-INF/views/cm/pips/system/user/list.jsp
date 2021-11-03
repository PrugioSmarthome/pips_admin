<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

        $("#txtHouscplxNm").val('<c:out value="${houscplxNm}"/>');
    });

    function btn_search(){
        var RegExp = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
        var str = $("#userName").val();
        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");
        $("#userName").val(str);
        $("#startCrDt").val($("#startCrDt").val().replace(/\./gi,''));
        $("#endCrDt").val($("#endCrDt").val().replace(/\./gi,''));
        $("#txtHouscplxNm").val($("#houscplxNm").val());
        $("#form_search").submit();
    }

    function btn_add() {
        location.href="/cm/system/user/add";
    }

    function detail_view(no) {
        $("#userId").val(no);
        $("#form_detal").submit();
    }

    function multi_view(no) {

        var result = "";
        $.ajax({
            type: "post",
            url: '/cm/system/user/multiDanjiList',
            async:false,
            dataType: 'JSON',
            data: {"userId" : no},
            success: function(data){
                $.each(data, function(idx, val) {
                    result += "<tr><td>"+val.houscplxNm+"</td></tr>";
                });
            }
        });

        $("#multiDanjiList").empty();
        $("#multiDanjiList").append(result);

    }

    //단지선택 버튼 클릭시 단지리스트 팝업
    function houscplxNm_popup(){
        //Ajax 들어갈 부분
        $.ajax({
                url: '/cm/common/housingcplx/list',
                type: 'POST',
                dataType : "json",
                success: function(data){

                $("#householdDeviceList").empty();
                $.each(data, function(i, item){
                    $("#householdDeviceList").append("<tr><td class='text-center'>"+item.houscplxNm+"</td><td class='text-center'><input class='btn btn-gray btn-sm' type='button' id='"+item.houscplxCd+"_"+item.houscplxNm+"' value='선택' onclick='selectbtn(this)'/></td></tr>");
                })

                },
                error: function(e){
                    console.log("에러");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
    }

    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");

        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#txtHouscplxNm").val(strarray[1]);
        $("#houscplxCd").val(strarray[0]);

        $("#closebtn").click();
    }

    function excel(){
        var list = new Array();

        <c:forEach items="${userList}" var="list" varStatus="status">
            var cJson = new Object();
            cJson.등록일 = '<fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/>';
            cJson.사용자ID = '<c:out value="${list.userId}"/>';
            cJson.사용자이름 = '<c:out value="${list.userName}"/>';
            cJson.사용자그룹 = '<c:out value="${list.userGroupName}"/>';
            cJson.관리단지명 = '<c:out value="${list.houscplxNm}"/>';
            cJson.활성화 = '<c:out value="${list.initAccount}"/>';

            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "사용자 목록";
        param.tableHeader = "['등록일', '사용자ID', '사용자이름', '사용자그룹', '관리단지명', '활성화']";
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
                console.log(blob.size);
                var link=document.createElement('a');
                link.href=window.URL.createObjectURL(blob);
                link.download= today+"_사용자 목록.xlsx";
                link.click();
            },
            error: function(e){
                console.log("에러");
                console.log(e.responseText.trim());
            },
            complete: function() {
            }
        });
    }
    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }

</script>

<form:form id="form_search" action="/cm/system/user/list" name="detail" commandName="detail" method="post">
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">사용자 목록</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>사용자 정보 관리</li>
                <li>사용자 목록</li>
            </ul>
        </div>
        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 등록일</th>
                        <td style="width:350px">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="startCrDt" id="startCrDt" value="<c:out value="${startCrDt}"/>" />
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" name="endCrDt" id="endCrDt" value="<c:out value="${endCrDt}"/>" />
                            </div>
                            <script type="text/javascript">
                            $('#startCrDt').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            $('#endCrDt').datepicker({
                                format: "yyyy.mm.dd",
                                language: "ko",
                                autoclose: true
                            });
                            </script>
                        </td>
                        <th>· 이름</th>
                        <td style="width:250px;">
                            <input type="text" id="userName" name="userName" class="form-control" value="<c:out value="${userName}"/>" />
                        </td>
                    </tr>
                    <tr>
                        <th>· 단지명</th>
                        <td>
                            <div class="input-group">
                                <input type="text" class="form-control" id="txtHouscplxNm" name="txtHouscplxNm" disabled />
                                <div class="input-group-append">
                                    <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" onclick="houscplxNm_popup();">단지선택</button>
                                </div>
                            </div>
                        </td>
                        <th>· 사용자 그룹</th>
                        <td style="width:250px;">
                            <select name="userGroupId" id="userGroupId" class="custom-select">
                                <option value="ALL">전체</option>
                                <option value="002" <c:if test="${userGroupId eq '002'}">selected</c:if>>시스템 관리자</option>
                                <option value="003" <c:if test="${userGroupId eq '003'}">selected</c:if>>단지 관리자</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">

            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" onclick="btn_add(); return false;"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">등록일</th>
                        <th scope="col">사용자 ID</th>
                        <th scope="col">사용자 이름</th>
                        <th scope="col">사용자 그룹명</th>
                        <th scope="col">관리 단지명</th>
                        <th scope="col">활성화</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');"><fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/></a>
                        </td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');"><c:out value="${list.userId}"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');"><c:out value="${list.userName}"/></a></td>
                        <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');"><c:out value="${list.description}"/></a></td>
                        <c:choose>
                            <c:when test="${list.userGroupName eq 'MULTI_COMPLEX_ADMIN'}">
                                <td class="text-center"><a href="#" data-toggle="modal" data-target="#modal2" onclick="multi_view('<c:out value="${list.userId}"/>');">멀티 단지</a></td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');"><c:out value="${list.houscplxNm}"/></a></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.initAccount eq 'N'}">
                                <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');">Y</a></td>
                            </c:when>
                            <c:when test="${list.initAccount eq 'Y'}">
                                <td class="text-center"><a href="#" onclick="detail_view('<c:out value="${list.userId}"/>');">N</a></td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $('#table1').DataTable({
                "order": [],
                "bLengthChange" : false,
                "dom": '<i<t>p>',
                "language": {
                    "info": "Total <span>_TOTAL_</span>건",
                    "infoEmpty":"Total <span>0</span>건",
                    "emptyTable": "조회된 데이터가 없습니다."
                },

            });
        </script>
    </div>
</div>

<input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
<input type="text" id="houscplxNm" name="houscplxNm" value="<c:out value="${houscplxNm}"/>" style="width:0;height:0;visibility:hidden"/>
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

<!-- 멀티 단지 관리자 단지 리스트 팝업 -->
<div class="modal" id="modal2" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered " role="document">
		<div class="modal-content edit-popup">
			<!-- 모달헤더 -->
			<div class="modal-header">
				<h5 class="modal-title">멀티 단지 리스트</h5>
				<button type="button" class="close" data-dismiss="modal" type="button"><img src="/images/btn_x.png" alt="" /></button>
			</div>
			<!-- //모달헤더 -->

			<!-- 모달바디 -->
			<div class="modal-body">
				<div style="width: 100%; height:auto; max-height:300px; overflow-y:auto; overflow-x: hidden;">
				    <table class="table">
				        <tbody id="multiDanjiList">
				        </tbody>
				    </table>
				</div>
			</div>
			<!-- //모달바디 -->
		</div>
	</div>
</div>

<form:form id="form_detal" action="/cm/system/user/view" name="detail" commandName="detail" method="post">
    <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_add" action="/cm/system/user/add" name="detail" commandName="detail" method="post">
</form:form>
