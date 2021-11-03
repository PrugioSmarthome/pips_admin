<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
var flag = "N";
    $(document).ready(function(){
        homenetlist("<c:out value="${housingCplx.svrTpCd}"/>","Y");
        $("#heat").val("<c:out value="${housingCplx.heatTpCd}"/>");

    });

    //취소
    function back(){
        window.history.back();
    }


    function goPopup(){
    	// 주소검색을 수행할 팝업 페이지를 호출합니다.
    	// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrCoordUrl.do)를 호출하게 됩니다.
    	var pop = window.open("/cm/housingcplx/info/search/addressService","pop","width=570,height=420, scrollbars=yes, resizable=yes");
    }

    function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo,entX,entY){
        var sido = "";
        var sigungu = "";
        sigungu = sggNm.split(" ");
        if(siNm == "서울특별시"){sido = "서울"}
        if(siNm == "부산광역시"){sido = "부산"}
        if(siNm == "대구광역시"){sido = "대구"}
        if(siNm == "인천광역시"){sido = "인천"}
        if(siNm == "광주광역시"){sido = "광주"}
        if(siNm == "대전광역시"){sido = "대전"}
        if(siNm == "울산광역시"){sido = "울산"}
        if(siNm == "경기도"){sido = "경기"}
        if(siNm == "강원도"){sido = "강원"}
        if(siNm == "충청북도"){sido = "충북"}
        if(siNm == "충청남도"){sido = "충남"}
        if(siNm == "전라북도"){sido = "전북"}
        if(siNm == "전라남도"){sido = "전남"}
        if(siNm == "경상북도"){sido = "경북"}
        if(siNm == "경상남도"){sido = "경남"}
        if(siNm == "제주특별자치도"){sido = "제주"}
        if(siNm == "세종특별자치시"){sido = "세종"}

        if(sigungu.length > 1){
            $("#sigunguname").val(sigungu[0]);
        }else{
            $("#sigunguname").val(sggNm);
        }

        $("#postnum").val(zipNo);
        $("#post").val(zipNo);
        $("#roadname").val(roadAddrPart1);
        $("#roadnm").val(roadAddrPart1);
        $("#areaname").val(jibunAddr);
        $("#area").val(jibunAddr);
        $("#sidoname").val(sido);
        $("#eupmyeondongname").val(emdNm);

        $("#lac").val(entX);
        $("#loc").val(entY);
        flag = "Y";
    }

    //홈넷서버 리스트 호출
    function homenetlist(e,yn){
        var param = new Object();
        param.svrTpCd = e;
        var hId;
        if(yn == "Y"){
            hId = "<c:out value="${housingCplx.hmnetId}"/>";
        }else{
            hId = "NOID";
        }

        $.ajax({
            url: '/cm/housingcplx/info/homenet/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#hmnet").empty();
                $("#hmnet").append("<option value='null'>선택</option>");
                console.log(data);
                $.each(data, function(i, item){
                    if(hId == item.hmnetId){
                        $("#hmnet").append("<option value='"+item.hmnetId+"' selected='selected'>"+item.hmnetNm+"</option>");
                    }else{
                        $("#hmnet").append("<option value='"+item.hmnetId+"'>"+item.hmnetNm+"</option>");
                    }

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

    //홈넷서버 중복체크
    function hmnetsvrcheck(){
        if($("#tpcd").val() == "HOUSCPLX_SVR"){
            var param = new Object();
            param.svrTpCd = $("#tpcd").val();
            param.hmnetId = $("#hmnet").val();

            $.ajax({
                url: '/cm/housingcplx/info/checkHomenetInfo',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    console.log(data);
                    if(data.status == true){
                        alert("선택하신 홈넷서버는 사용이 가능합니다.");

                    }
                    if(data.status == false){
                        alert(data.params[0].msg);
                        var Cd = "<c:out value="${housingCplx.svrTpCd}"/>";
                        if($("#tpcd").val() == Cd){
                            $("#hmnet").val("<c:out value="${housingCplx.hmnetId}"/>");

                        }else{
                            $("#hmnet").val("null");
                        }

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
    }

    //수정
    function modify(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var RegExp2 = /[\,;:|*~`!\-_+┼<>@\#$%&\'\"\\\=]/gi;
        if(RegExp.test($("#houscplxNm").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if(RegExp2.test($("#screenHouscplxNm").val()) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }
        if($("#hmnet").val() == "null"){
            alert("홈넷서버를 선택해 주세요");
            return;
        }
        if($("#houscplxNm").val() == ""){
            alert("단지명을 입력해주세요.");
            return;
        }
        if($("#screenHouscplxNm").val() == ""){
            alert("화면표시단지명을 입력해주세요.");
            return;
        }
        if($("#landDimQty").val() == ""){
            alert("대지면적을 입력해주세요.");
            return;
        }
        if($("#archDimQty").val() == ""){
            alert("연면적을 입력해주세요.");
            return;
        }
        if($("#busiApprYmd").val() == ""){
            alert("사업승인일을 입력해주세요.");
            return;
        }
        if($("#busiConendYmd").val() == ""){
            alert("사업준공일을 입력해주세요.");
            return;
        }
        if($("#wholeHsholdCnt").val() == ""){
            alert("총 세대수를 입력해주세요.");
            return;
        }
        if($("#wholeDongCnt").val() == ""){
            alert("총 동수를 입력해주세요.");
            return;
        }
        if($("#lwstUngrFlrcnt").val() == ""){
            alert("지하층을 입력해주세요.");
            return;
        }
        if($("#hgstAbgrFlrcnt").val() == ""){
            alert("지상층을 입력해주세요.");
            return;
        }
        if($("#tpcd").val() == "null"){
            alert("홈넷서버 종류를 선택해주세요.");
            return;
        }
        if($("#hmnet").val() == "null"){
            alert("홈넷서버를 선택해주세요.");
            return;
        }
        if($("#wholeWlLcnt").val() == ""){
            alert("전체 주차대수를 입력해주세요.");
            return;
        }
        if($("#hsholdWlLcnt").val() == ""){
            alert("세대당 주차대수를 입력해주세요.");
            return;
        }
        if($("#heat").val() == "null"){
            alert("난방방식을 선택해주세요.");
            return;
        }
        $("#honm").val($("#houscplxNm").val());
        $("#schonm").val($("#screenHouscplxNm").val());
        $("#landdq").val($("#landDimQty").val());
        $("#archdq").val($("#archDimQty").val());
        $("#busiay").val($("#busiApprYmd").val());
        $("#busicy").val($("#busiConendYmd").val());
        $("#wholehc").val($("#wholeHsholdCnt").val());
        $("#wholedc").val($("#wholeDongCnt").val());
        $("#lwstuf").val($("#lwstUngrFlrcnt").val());
        $("#hgstaf").val($("#hgstAbgrFlrcnt").val());
        $("#hmid").val($("#hmnet").val());
        $("#heatcd").val($("#heat").val());
        $("#wwl").val($("#wholeWlLcnt").val());
        $("#hwl").val($("#hsholdWlLcnt").val());
        $("#flagYn").val(flag);
        $("#form_edit").submit();

    }
    //서버종류 선택
    function chang(){

        homenetlist($("#tpcd").val(),"N");
    }

    function SetNum(obj){
        val=obj.value;
        re=/[^0-9]/gi;
        obj.value=val.replace(re,"");
    }

    function SetNum2(obj){
        val=obj.value;
        re=/[^0-9-"."]/gi;
        obj.value=val.replace(re,"");
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">단지개요</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>단지개요</li>
                <li>단지개요 수정</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <div class="tbl_top_area">
                <div class="right_area"><span class="star mr10"></span>필수입력 항목</div>
            </div>
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>단지명칭 <sup class="star"></sup></th>
                        <td><input type="text" id="houscplxNm" name="houscplxNm" value="<c:out value="${housingCplx.houscplxNm}"/>" class="form-control" /></td>
                        <th>화면표시단지명 <sup class="star"></sup></th>
                        <td><input type="text" id="screenHouscplxNm" name="screenHouscplxNm" value="<c:out value="${housingCplx.screenHouscplxNm}"/>" class="form-control" /></td>
                    </tr>
                    <tr>
                        <th>단지주소 <sup class="star"></sup></th>
                        <td colspan="3">
                            <div class="depth2_tr w400">
                                <div class="th w70">우편번호</div>
                                <div class="td">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="post" value="<c:out value="${housingCplx.postNo}"/>" name="post" disabled />
                                        <div class="input-group-append">
                                            <input type="button" onClick="goPopup()"; class="btn btn-gray btn-sm" value="주소입력"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w70">도로명</div>
                                <div class="td"><input type="text" id="roadnm" name="roadnm" value="<c:out value="${housingCplx.roadnmBasAddrCont}"/>" class="form-control" disabled /></div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w70">지번</div>
                                <div class="td"><input type="text" id="area" name="area" value="<c:out value="${housingCplx.areaBasAddrCont}"/>" class="form-control" disabled /></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>대지면적(m2) <sup class="star"></sup></th>
                        <td><input type="text" id="landDimQty" name="landDimQty" value="<c:out value="${housingCplx.landDimQty}"/>" class="form-control" onkeyup="SetNum2(this);" maxlength="20"/></td>
                        <th>연면적(m2) <sup class="star"></sup></th>
                        <td><input type="text" id="archDimQty" name="archDimQty" value="<c:out value="${housingCplx.archDimQty}"/>" class="form-control" onkeyup="SetNum2(this);" maxlength="20"/></td>
                    </tr>
                    <tr>
                        <th>사업승인일</th>
                        <td><input type="text" class="form-control inp_calendar" id="busiApprYmd" name="busiApprYmd" value="<c:out value="${housingCplx.busiApprYmd}"/>"/></td>
                        <th>사업준공일 <sup class="star"></sup></th>
                        <td><input type="text" class="form-control inp_calendar" id="busiConendYmd" name="busiConendYmd" value="<c:out value="${housingCplx.busiConendYmd}"/>"/></td>
                    </tr>
                    <script type="text/javascript">
                        $('#busiApprYmd').datepicker({
                            format: "yyyymmdd",
                            language: "ko",
                            autoclose: true
                        });
                        $('#busiConendYmd').datepicker({
                            format: "yyyymmdd",
                            language: "ko",
                            autoclose: true
                        });
                    </script>
                    <tr>
                        <th>총 세대수 <sup class="star"></sup></th>
                        <td><input type="text" id="wholeHsholdCnt" name="wholeHsholdCnt" value="<c:out value="${housingCplx.wholeHsholdCnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></td>
                        <th>총 동수 <sup class="star"></sup></th>
                        <td><input type="text" id="wholeDongCnt" name="wholeDongCnt" value="<c:out value="${housingCplx.wholeDongCnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></td>
                    </tr>
                    <tr>
                        <th>지하/지상 <sup class="star"></sup></th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w50">지하층</div>
                                <div class="td"><input type="text" id="lwstUngrFlrcnt" name="lwstUngrFlrcnt" value="<c:out value="${housingCplx.lwstUngrFlrcnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></div>
                                <div class="th w50">지상층</div>
                                <div class="td"><input type="text" id="hgstAbgrFlrcnt" name="hgstAbgrFlrcnt" value="<c:out value="${housingCplx.hgstAbgrFlrcnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>홈넷서버 <sup class="star"></sup></th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w100">홈넷서버종류</div>
                                    <select name="tpcd" id="tpcd" class="custom-select" onchange="chang();">
                                        <option value="null">선택</option>
                                        <c:choose>
                                            <c:when test="${housingCplx.svrTpCd eq 'UNIFY_SVR'}">
                                                <option value="HOUSCPLX_SVR">단지서버</option>
                                                <option value="UNIFY_SVR" selected="selected">통합서버</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="HOUSCPLX_SVR" selected="selected">단지서버</option>
                                                <option value="UNIFY_SVR">통합서버</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                <div class="td">
                                </div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w100">홈넷서버이름</div>
                                <div class="td">
                                    <select name="hmnet" id="hmnet" class="custom-select" onchange="hmnetsvrcheck();">
                                        <option value="null">선택</option>
                                    </select>
                                </div>
                            </div>
                            <div class="depth2_tr">
                                <div class="th w100">홈넷사</div>
                                <div class="td" id="bName"><c:out value="${housingCplx.bizcoNm}"/></div>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <th>주차대수 <sup class="star"></sup></th>
                        <td colspan="3">
                            <div class="depth2_tr">
                                <div class="th w40">전체</div>
                                <div class="td"><input type="text" id="wholeWlLcnt" name="wholeWlLcnt" value="<c:out value="${housingCplx.wholeWlLcnt}"/>" class="form-control" onkeyup="SetNum(this);" maxlength="11"/></div>
                                <div class="th w50">세대당</div>
                                <div class="td"><input type="text" id="hsholdWlLcnt" name="hsholdWlLcnt" value="<c:out value="${housingCplx.hsholdWlLcnt}"/>" class="form-control" onkeyup="SetNum2(this);" maxlength="10"/></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>시공사</th>
                        <td id="bizco_nm">(주)대우건설</td>
                        <th>난방방식 <sup class="star"></sup></th>
                        <td>
                            <select name="heat" id="heat" class="custom-select">
                                <option value="null">선택</option>
                                <c:forEach items="${heatTypeList}" var="heatList" varStatus="status">
                                    <option value="<c:out value="${heatList.commCd}"/>"><c:out value="${heatList.commCdNm}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>

                </tbody>
            </table>
        </div>
        <div class="tbl_btm_area2">
            <div class="right_area">
                <input type="button" class="btn btn-gray" onClick="back();" value="취소"/>
                <input type="button" class="btn btn-bluegreen" onClick="modify();" value="저장"/>
            </div>
        </div>

    </div>

</div>
<form:form action="/cm/housingcplx/info/editHousingCplxIntroAction" id="form_edit" name="housingCplx" commandName="housingCplx" method="post">

<input type="text" id="honm" name="houscplxNm" value="<c:out value="${housingCplx.houscplxNm}"/>" style="display:none;"/>
<input type="text" id="schonm" name="screenHouscplxNm" value="<c:out value="${housingCplx.screenHouscplxNm}"/>" style="display:none;"/>
<input type="text" id="roadname" name="roadnmBasAddrCont" value="<c:out value="${housingCplx.roadnmBasAddrCont}"/>" style="display:none;"/>
<input type="text" id="areaname" name="areaBasAddrCont" value="<c:out value="${housingCplx.areaBasAddrCont}"/>" style="display:none;"/>
<input type="text" id="postnum" name="postNo" value="<c:out value="${housingCplx.postNo}"/>" style="display:none;"/>
<input type="text" id="sidoname" name="addrSiDoNm" value="<c:out value="${housingCplx.addrSiDoNm}"/>" style="display:none;"/>
<input type="text" id="sigunguname" name="addrSiGunGuNm" value="<c:out value="${housingCplx.addrSiGunGuNm}"/>" style="display:none;"/>
<input type="text" id="eupmyeondongname" name="addrEupMyeonDongNm" value="<c:out value="${housingCplx.addrEupMyeonDongNm}"/>" style="display:none;"/>
<input type="text" id="landdq" name="landDimQty" value="<c:out value="${housingCplx.landDimQty}"/>" style="display:none;"/>
<input type="text" id="archdq" name="archDimQty" value="<c:out value="${housingCplx.archDimQty}"/>" style="display:none;"/>
<input type="text" id="busiay" name="busiApprYmd" value="<c:out value="${housingCplx.busiApprYmd}"/>" style="display:none;"/>
<input type="text" id="busicy" name="busiConendYmd" value="<c:out value="${housingCplx.busiConendYmd}"/>" style="display:none;"/>
<input type="text" id="wholehc" name="wholeHsholdCnt" value="<c:out value="${housingCplx.wholeHsholdCnt}"/>" style="display:none;"/>
<input type="text" id="wholedc" name="wholeDongCnt" value="<c:out value="${housingCplx.wholeDongCnt}"/>" style="display:none;"/>
<input type="text" id="lwstuf" name="lwstUngrFlrcnt" value="<c:out value="${housingCplx.lwstUngrFlrcnt}"/>" style="display:none;"/>
<input type="text" id="hgstaf" name="hgstAbgrFlrcnt" value="<c:out value="${housingCplx.hgstAbgrFlrcnt}"/>" style="display:none;"/>
<input type="text" id="hmid" name="hmnetId" value="<c:out value="${housingCplx.hmnetId}"/>" style="display:none;"/>
<input type="text" id="heatcd" name="heatTpCd" value="<c:out value="${housingCplx.heatTpCd}"/>" style="display:none;"/>
<input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${housingCplx.houscplxCd}"/>" style="display:none;"/>
<input type="text" id="lac" name="latudCont" value="<c:out value="${housingCplx.latudCont}"/>" style="display:none;"/>
<input type="text" id="loc" name="lotudCont" value="<c:out value="${housingCplx.lotudCont}"/>" style="display:none;"/>
<input type="text" id="wmx" name="wrMeasXCoorNo" value="<c:out value="${housingCplx.wrMeasXCoorNo}"/>" style="display:none;"/>
<input type="text" id="wmy" name="wrMeasYCoorNo" value="<c:out value="${housingCplx.wrMeasYCoorNo}"/>" style="display:none;"/>

<input type="text" id="wwl" name="wholeWlLcnt" value="<c:out value="${housingCplx.wholeWlLcnt}"/>" style="display:none;"/>
<input type="text" id="hwl" name="hsholdWlLcnt" value="<c:out value="${housingCplx.hsholdWlLcnt}"/>" style="display:none;"/>

<input type="text" id="flagYn" name="flag" style="display:none;"/>
</form:form>

<form:form id="mgtoview" action="/cm/housingcplx/info/adddress/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${housingcplx.houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>