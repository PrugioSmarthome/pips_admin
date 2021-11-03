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
        $('#table1').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
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

        var dayStart = "<c:out value="${startCrDt}"/>";
        var dayEnd = "<c:out value="${endCrDt}"/>";
        if(dayStart != ""){
            $("#startCrDt").val("<c:out value="${startCrDt}"/>");
            var day1 = dayStart.substring(0,4);
            var day2 = dayStart.substring(4,6);
            var day3 = dayStart.substring(6,8);
            $('#start').val(day1+"."+day2+"."+day3);
        }
        if(dayEnd != ""){
            $("#endCrDt").val("<c:out value="${endCrDt}"/>");
            var day1 = dayEnd.substring(0,4);
            var day2 = dayEnd.substring(4,6);
            var day3 = dayEnd.substring(6,8);
            $('#end').val(day1+"."+day2+"."+day3);
        }

        $("#houscplxCd").val("<c:out value="${houscplxCd}"/>");
        $("#houscplxNm_").val("<c:out value="${houscplxNm}"/>");
        $("#houscplxNm").val("<c:out value="${houscplxNm}"/>");
        $("#dongNo").val("<c:out value="${dongNo}"/>");
        $("#hoseNo").val("<c:out value="${hoseNo}"/>");
        var str = "<c:out value="${userNm}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        $("#userNm").val(str);
        $("#name").val(str);
        $("#apprStsCd").val("<c:out value="${apprStsCd}"/>");
        $("#StsCd").val("<c:out value="${apprStsCd}"/>");
        var name = "<c:out value="${houscplxNm}"/>";
        var searchingYn = "<c:out value="${searchingYn}"/>";
        if(name != "" || name != "전체"){
            if(searchingYn == "Y"){
                selectList("searchingYn");
            }else{
                selectList();
            }
        }
    });



    //단지명 선택했을경우 동,호 리스트 가져오기
    function selectList(gubun){
        var param = new Object();
        param.delYn = "N";
        param.houscplxCd = $("#houscplxCd").val();

        var dong = "<c:out value="${dongNo}"/>";
        var hose = "<c:out value="${hoseNo}"/>";

        $.ajax({
            url: '/cm/common/household/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#search_text").value = "";
                if(gubun == "dong"){
                    $("#dong").empty();
                    if(dong == "all" || dong == ""){
                        $("#dong").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#dong").append("<option value='all'>전체</option>");
                    }
                    var temp = new Array();
                    $.each(data, function(i, item){
                        if ($.inArray(item.dongNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.dongNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[i]){
                                $("#dong").append("<option selected value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                            }else{
                                $("#dong").append("<option value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                            }
                        }
                    })
                } else if(gubun == "hose"){
                    $("#hose").empty();
                    if(hose == "all" || hose == ""){
                        $("#hose").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#hose").append("<option value='all'>전체</option>");
                    }
                    var temp = new Array();
                    $.each(data, function(i, item){
                        if (item.dongNo == $("#dong").val() && $.inArray(item.hoseNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.hoseNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[i]){
                                $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                            }else{
                                $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                            }
                        }
                    })
                } else if(gubun == "searchingYn"){
                    $("#dong").empty();
                    $("#hose").empty();
                    if(dong == "all" || dong == ""){
                        $("#dong").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#dong").append("<option value='all'>전체</option>");
                    }
                    if(hose == "all" || hose == ""){
                        $("#hose").append("<option value='all' selected>전체</option>");
                    }else{
                        $("#hose").append("<option value='all'>전체</option>");
                    }
                    var j = 0;
                    var k = 0;
                    var temp = new Array();
                    var temp1 = new Array();

                    $.each(data, function(i, item){
                         if ($.inArray(item.dongNo, temp) == -1) {  // temp 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp.push(item.dongNo);              // temp 배열에 값을 넣는다.
                            if(dong == temp[j]){
                                $("#dong").append("<option selected value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                                j++;
                            }else{
                                $("#dong").append("<option value='"+item.dongNo+"'>"+item.dongNo+"</option>");
                                j++;
                            }
                         }
                        if (item.dongNo == $("#dong").val() && $.inArray(item.hoseNo, temp1) == -1) {  // temp1 에서 값을 찾는다.  //값이 없을경우(-1)
                            temp1.push(item.hoseNo);              // temp 배열에 값을 넣는다.
                            if(hose == temp1[k]){
                                $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                                k++;
                            }else{
                                $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                                k++;
                            }
                        }
                    })
                }

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
    //검색버튼을 눌렀을경우
    function btn_search(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var str = $("#name").val();
        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");

        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        $("#userNm").val(str);
        $("#startCrDt").val($("#start").val().replace(/\./gi,''));
        $("#endCrDt").val($("#end").val().replace(/\./gi,''));
        $("#apprStsCd").val($("#StsCd").val());
        $("#houscplxNm_").val($("#houscplxNm").val());
        $("#form_list").submit();
    }

    //모달 단지리스트 검색
    function list_search(){
        var text = $("#search_text").val();
        $('#list_table').DataTable().search(text).draw();
    }


    //엑셀 다운로드
    function excel(){
        var list = new Array();
        <c:forEach items="${groupRecord}" var="record" varStatus="status">
            var cJson = new Object();
            cJson.승인반려일자 = '<fmt:formatDate value="${record.apprDemDt}" pattern="yyyy-MM-dd"/>';
            cJson.단지명 = "<c:out value="${record.houscplxNm}"/>";
            cJson.동 =  "<c:out value="${record.dongNo}"/>";
            cJson.호 = "<c:out value="${record.hoseNo}"/>";
            cJson.아이디 = "<c:out value="${record.userId}"/>";
            cJson.이름 = "<c:out value="${record.userNm}"/>";
            <c:choose>
                <c:when test="${record.apprStsCd eq 'APPROVAL'}">
                    cJson.상태 = "승인";
                </c:when>
                <c:when test="${record.apprStsCd eq 'REJECT'}">
                    cJson.상태 = "반려";
                </c:when>
            </c:choose>
            cJson.가입유형 = "<c:out value="${record.certfTpCd}"/>";
            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "가족대표 승인반려 이력 목록";
        param.tableHeader = "['승인반려일자', '단지명', '동', '호', '아이디', '이름', '상태', '가입유형']";
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
                link.download= today+"_가족대표 승인반려 이력 목록.xlsx";
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

    function dong_change(){
        selectList("hose");
//        $("#hose").val("all");
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">가족대표 승인/반려 이력 목록</h2>
            <ul class="location">
                <li>가족대표 승인반려 이력</li>
                <li>가족대표 승인/반려 이력 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <tbody>
					<tr>
						<th style="width:130px;">· 승인/반려 일자</th>
						<td style="width:400px;">
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
						<th>· 상태</th>
                        <td style="width:100px;">
                            <select name="StsCd" id="StsCd" class="custom-select">
                                <option value="all">전체</option>
								<option value="APPROVAL">승인</option>
								<option value="REJECT">반려</option>
                            </select>
                        </td>
						<th style="visibility:hidden">· 상태</th>
                        <td style="width:100px;visibility:hidden">
                            <select class="custom-select">
                                <option value="">전체</option>
								<option value="">승인</option>
								<option value="">반려</option>
                            </select>
                        </td>
					</tr>
					<tr>
						<th>· 단지명</th>
						<td style="width:100px;">
							<div class="input-group">
								<input type="text" id="houscplxNm" name="houscplxNm" class="form-control" disabled />
								<div class="input-group-append">
									<button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal1" >단지선택</button>
								</div>
							</div>
						</td>
						<th>· 동</th>
                        <td style="width:100px;">
                            <select name="dong" id="dong" class="custom-select" onchange="dong_change();">
                                <option value="all">전체</option>
                            </select>
                        </td>
						<th>· 호</th>
                        <td style="width:100px;">
                            <select name="hose" id="hose" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
					</tr>
                    <tr>
						<th>· 이름</th>
                        <td style="width:200px;">
                            <input type="text" id="name" class="form-control" />
                        </td>
						<td>
                            <button type="button" class="btn btn-brown btn-sm" style="width:70px;" onclick="btn_search();">검색</button>
                        </td>
					</tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">승인/반려 일자</th>
                        <th scope="col">승인/반려 요청 일자</th>
                        <th scope="col">단지명</th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">상태</th>
                        <th scope="col">가입유형</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${groupRecord}" var="record" varStatus="status">
                    <tr>
                        <td class="text-center"><fmt:formatDate value="${record.apprDt}" pattern="yyyy-MM-dd"/></td>
                        <td class="text-center"><fmt:formatDate value="${record.apprDemDt}" pattern="yyyy-MM-dd"/></td>
                        <td class="text-center"><c:out value="${record.houscplxNm}"/></td>
                        <td class="text-center"><c:out value="${record.dongNo}"/></td>
                        <td class="text-center"><c:out value="${record.hoseNo}"/></td>
                        <td class="text-center"><c:out value="${record.userId}"/></td>
                        <td class="text-center"><c:out value="${record.userNm}"/></td>
                        <c:choose>
                            <c:when test="${record.apprStsCd eq 'APPROVAL'}">
                                <td class="text-center">승인</td>
                            </c:when>
                            <c:when test="${record.apprStsCd eq 'REJECT'}">
                                <td class="text-center">반려</td>
                            </c:when>
                        </c:choose>
                        <td class="text-center"><c:out value="${record.certfTpCd}"/></td>
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

<form:form id="form_list" action="/cm/household/groupRecord/search" name="record" commandName="record" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxNm_" name="houscplxNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userNm" name="userNm" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="startCrDt" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="endCrDt" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="apprStsCd" name="apprStsCd" style="width:0;height:0;visibility:hidden"/>
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