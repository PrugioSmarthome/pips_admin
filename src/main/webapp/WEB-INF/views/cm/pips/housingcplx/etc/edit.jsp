<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<script type="text/javascript">
    $(document).ready(function () {
        var day = '<c:out value="${etcInfo.enrgMeasYmd}"/>';
        $("#day").val(day);

    });

    function webapplist(num){
        $.ajax({
            url: '/cm/housingcplx/info/serviceLinkType/list',
            type: 'POST',
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    $("#addwebapplist_"+num+"").append("<option value='"+item.commCd+"'>"+item.commCdNm+"</option>")
                })
            },
            error: function(e){
            },
            complete: function() {
            }
        });
    }

    function banrlist(num){
        $.ajax({
            url: '/cm/housingcplx/info/bannerInfo/list',
            type: 'POST',
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    $("#addbannerlist_"+num+"").append("<option value='"+item.blltNo+"'>"+item.orgnlFileNm+' / '+item.linkUrlCont+"</option>")
                })
            },
            error: function(e){
            },
            complete: function() {
            }
        });
    }

    function select(n,e){
        var param = new Object();
        param.lnkSvcGrpTpCd = $(n).val();
        var str = e.split("_");

        $.ajax({
            url: '/cm/housingcplx/info/serviceLinkInfo/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                if(str[0] == "webapplist"){
                    $("#servicelist_"+str[1]+"").empty();
                    $("#servicelist_"+str[1]+"").append("<option value='null'>선택</option>");
                }else{
                    $("#addservicelist_"+str[1]+"").empty();
                    $("#addservicelist_"+str[1]+"").append("<option value='null'>선택</option>");
                }
                $.each(data, function(i, item){
                    if(str[0] == "webapplist"){
                        $("#servicelist_"+str[1]+"").append("<option value='"+item.lnkSvcId+"'>"+item.lnkSvcNm+"</option>")
                    }else{
                        $("#addservicelist_"+str[1]+"").append("<option value='"+item.lnkSvcId+"'>"+item.lnkSvcNm+"</option>")
                    }
                })
            },
            error: function(e){
            },
            complete: function() {
            }
        });
    }


    function list(num,cd){
        $.ajax({
            url: '/cm/housingcplx/info/serviceLinkType/list',
            type: 'POST',
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    if(cd == item.commCd){
                        $("#webapplist_"+num+"").append("<option value='"+item.commCd+"' selected>"+item.commCdNm+"</option>")
                    }else{
                        $("#webapplist_"+num+"").append("<option value='"+item.commCd+"'>"+item.commCdNm+"</option>")
                    }

                })
            },
            error: function(e){
            },
            complete: function() {
            }
        });
    }

    function sublist(num,cd,id){
        var param = new Object();
        param.lnkSvcGrpTpCd = cd;

        $.ajax({
            url: '/cm/housingcplx/info/serviceLinkInfo/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    if(id == item.lnkSvcId){
                        $("#servicelist_"+num+"").append("<option value='"+item.lnkSvcId+"' selected>"+item.lnkSvcNm+"</option>")
                    }else{
                        $("#servicelist_"+num+"").append("<option value='"+item.lnkSvcId+"'>"+item.lnkSvcNm+"</option>")
                    }
                })
            },
            error: function(e){
            },
            complete: function() {
            }
        });
    }

    function bannerlist(num,cd){
        $.ajax({
            url: '/cm/housingcplx/info/bannerInfo/list',
            type: 'POST',
            dataType : "json",
            success: function(data){
                $.each(data, function(i, item){
                    if(cd == item.blltNo){
                        $("#bannerlist_"+num+"").append("<option value='"+item.blltNo+"' selected>"+item.orgnlFileNm+' / '+item.linkUrlCont+"</option>")
                    }else{
                        $("#bannerlist_"+num+"").append("<option value='"+item.blltNo+"'>"+item.orgnlFileNm+' / '+item.linkUrlCont+"</option>")
                    }

                })
            },
            error: function(e){
            },
            complete: function() {
            }
        });
    }


    function back(){
        window.history.back();
    }

    function submit_btn(){
        if($("#range").val() == ""){
            alert("단지 반경을 입력해주세요.");
            return;
        }
        if($("#elct").val() == ""){
            alert("전기 값 을 입력해주세요.");
            return;
        }
        if($("#hotwtr").val() == ""){
            alert("온수 값 을 입력해주세요.");
            return;
        }
        if($("#heat").val() == ""){
            alert("난방 값 을 입력해주세요.");
            return;
        }
        if($("#gas").val() == ""){
            alert("가스 값 을 입력해주세요.");
            return;
        }if($("#wtrspl").val() == ""){
             alert("수도 값 을 입력해주세요.");
             return;
        }

        var cnt = $("select[name=webappList]").length;
        var flag1;
        var flag2;
        for(var i = 0 ; i < cnt ; i++){
            var text1 = $("select[name='webappList']").eq(i).val();
            var text2 = $("select[name='serviceList']").eq(i).val();
            if(text1 == "null"){
                flag1 = "NO";
            }
            if(text2 == "null"){
                flag2 = "NO";
            }
        }
        if(flag1 == "NO"){
            alert("구분을 선택해주세요.");
            return;
        }
        if(flag2 == "NO"){
            alert("서비스명을 선택해주세요.");
            return;
        }

        var cnt2 = $("select[name=bannerList]").length;
        var flag3;
        var flag4;
        for(var i = 0 ; i < cnt2 ; i++){
            var text3 = $("select[name='bannerList']").eq(i).val();
            if(text3 == "null"){
                flag3 = "NO";
            }
            for(var j = i+1 ; j < cnt2 ; j++){
                if(text3 == $("select[name='bannerList']").eq(j).val()){
                    flag4 = "NO";
                }
            }
        }
        if(flag3 == "NO"){
            alert("배너를 선택해주세요.");
            return;
        }
        if(flag4 == "NO"){
            alert("같은 배너를 중복하여 사용할 수 없습니다.");
            return;
        }


        $("#measymd").val($("#day").val());
        $("#rangecont").val($("#range").val());

        var engArray = new Array();
        var elctObject = new Object();
        elctObject.enrgTpCd = "ELCT";
        elctObject.enrgUntCd = "KWH";
        elctObject.enrgMaxQty = $("#elct").val();
        elctObject.crerId = '<c:out value="${session_user.userName}"/>';
        engArray.push(elctObject);

        var gasObject = new Object();
        gasObject.enrgTpCd = "GAS";
        gasObject.enrgUntCd = "M3";
        gasObject.enrgMaxQty = $("#gas").val();
        gasObject.crerId = '<c:out value="${session_user.userName}"/>';
        engArray.push(gasObject);

        var heatObject = new Object();
        heatObject.enrgTpCd = "HEAT";
        heatObject.enrgUntCd = $("#heat_select").val();
        heatObject.enrgMaxQty = $("#heat").val();
        heatObject.crerId = '<c:out value="${session_user.userName}"/>';
        engArray.push(heatObject);

        var hotwtrObject = new Object();
        hotwtrObject.enrgTpCd = "HOTWTR";
        hotwtrObject.enrgUntCd = $("#hotwtr_select").val();
        hotwtrObject.enrgMaxQty = $("#hotwtr").val();
        hotwtrObject.crerId = '<c:out value="${session_user.userName}"/>';
        engArray.push(hotwtrObject);

        var wtrsplObject = new Object();
        wtrsplObject.enrgTpCd = "WTRSPL";
        wtrsplObject.enrgUntCd = "M3";
        wtrsplObject.enrgMaxQty = $("#wtrspl").val();
        wtrsplObject.crerId = '<c:out value="${session_user.userName}"/>';
        engArray.push(wtrsplObject);
        var engList = JSON.stringify(engArray);
        $("#unitlist").val(engList);


        var listcnt = $("select[name=webappList]").length;
        var linkArray = new Array();
        for(var i = 0 ; i < listcnt ; i++){
            var jsonObject = new Object();
            jsonObject.lnkSvcId = $("select[name='serviceList']").eq(i).val();
            jsonObject.crerId = '<c:out value="${session_user.userName}"/>';
            linkArray.push(jsonObject);
        }
        var linkJsonList = JSON.stringify(linkArray);
        $("#linklist").val(linkJsonList);

        var listcnt2 = $("select[name=bannerList]").length;
        var banrArray = new Array();
        for(var i = 0 ; i < listcnt2 ; i++){
            var jsonObject2 = new Object();
            jsonObject2.blltNo = $("select[name='bannerList']").eq(i).val();
            jsonObject2.crerId = '<c:out value="${session_user.userName}"/>';
            banrArray.push(jsonObject2);
        }
        var banrJsonList = JSON.stringify(banrArray);
        $("#banrlist").val(banrJsonList);

        $("#form_edit").submit();

}
</script>
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">기타</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>기타</li>
                <li>기타 수정</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:160px"/>
                    <col />
                    <col style="width:160px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>단지 반경 설정 (m)</th>
                        <td colspan="3"><input id="range" type="number" class="form-control" value="<c:out value="${etcInfo.qryRangeCont}"/>"/></td>
                    </tr>
                    <tr>
                        <th>에너지 단위 및 그래프 최대값 설정</th>
                        <td colspan="3">
                            <div class="depth2_tr">
                            <c:forEach items="${energyInfo}" var="info" varStatus="status">
                                <c:choose>
                                    <c:when test="${info.enrgTpCd eq 'ELCT'}">
                                        <div class="th w45">전기</div>
                                        <div class="td w160"><input type="number" id="elct" class="form-control d-inline-block w90 mr5" value="<c:out value="${info.enrgMaxQty}"/>" /> kWh</div>
                                    </c:when>
                                    <c:when test="${info.enrgTpCd eq 'HOTWTR'}">
                                        <div class="th w45">온수</div>
                                        <div class="td">
                                            <input type="number" id="hotwtr" class="form-control d-inline-block align-middle w90 mr5" value="<c:out value="${info.enrgMaxQty}"/>" />
                                            <select name="" id="hotwtr_select" class="custom-select d-inline-block align-middle w80">
                                                <option value="null">선택</option>
                                                <c:if test="${info.enrgUntCd eq 'MCAL'}">
                                                    <option value="MCAL" selected>mcal</option>
                                                    <option value="M3">㎥</option>
                                                </c:if>
                                                <c:if test="${info.enrgUntCd eq 'M3'}">
                                                    <option value="MCAL">mcal</option>
                                                    <option value="M3" selected>㎥</option>
                                                </c:if>
                                            </select>
                                        </div>
                                    </c:when>
                                    <c:when test="${info.enrgTpCd eq 'HEAT'}">
                                        <div class="th w45">난방</div>
                                        <div class="td">
                                            <input type="number" id="heat" class="form-control d-inline-block align-middle w90 mr5" value="<c:out value="${info.enrgMaxQty}"/>" />
                                            <select name="" id="heat_select" class="custom-select d-inline-block align-middle w80">
                                                <option value="null">선택</option>
                                                <c:if test="${info.enrgUntCd eq 'MCAL'}">
                                                    <option value="MCAL" selected>mcal</option>
                                                    <option value="M3">㎥</option>
                                                </c:if>
                                                <c:if test="${info.enrgUntCd eq 'M3'}">
                                                    <option value="MCAL">mcal</option>
                                                    <option value="M3" selected>㎥</option>
                                                </c:if>
                                            </select>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            </div>
                            <div class="depth2_tr">
                                <c:forEach items="${energyInfo}" var="info" varStatus="status">
                                    <c:choose>
                                        <c:when test="${info.enrgTpCd eq 'GAS'}">
                                            <div class="th w45">가스</div>
                                            <div class="td w160"><input type="number" id="gas" class="form-control d-inline-block w90 mr5" value="<c:out value="${info.enrgMaxQty}"/>" /> m<sup>3</sup></div>
                                        </c:when>
                                        <c:when test="${info.enrgTpCd eq 'WTRSPL'}">
                                            <div class="th w45">수도</div>
                                            <div class="td"><input type="number" id="wtrspl" class="form-control d-inline-block w90 mr5" value="<c:out value="${info.enrgMaxQty}"/>" /> m<sup>3</sup></div>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
        <div class="sub_tit">
            <h3 class="tit">연계 웹/앱</h3>
        </div>
        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">구분</th>
                        <th scope="col">서비스명</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${serviceLinkInfo}" var="info" varStatus="status">
                    <tr>
                        <td>
                            <select name="webappList" id="webapplist_${status.index}" class="custom-select" onchange="select(this, this.id);">
                                <option value="null">선택</option>
                            </select>
                        </td>
                        <td>
                            <select name="serviceList" id="servicelist_${status.index}" class="custom-select" >
                                <option value="null">선택</option>
                            </select>
                        </td>
                        <td><button class="btn btn-add-circle deleteRow"><i class="fas fa-minus-circle"></i></button></td>
                    </tr>
                    <script>list('${status.index}','<c:out value="${info.lnkSvcGrpTpCd}"/>')</script>
                    <script>sublist('${status.index}','<c:out value="${info.lnkSvcGrpTpCd}"/>','<c:out value="${info.lnkSvcId}"/>')</script>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area no_paging">
                <div class="left_area">
                    <button class="btn btn-add addRow"><i class="fas fa-plus-square"></i>추가</button>
                </div>
            </div>
            <input type="text" id="webappnum" value="0" style="display:none;"/>
        </div>
        <script type="text/javascript">
            var tables = $('#table1').DataTable({
                        "order": [],
                        "bLengthChange" : false,
                        "dom": '<<t>>',
                        "language": {
                            "info": "Total <span>_TOTAL_</span>건",
                            "infoEmpty":"Total <span>0</span>건",
                            "emptyTable": "데이터가 없습니다."
                        },
                        "columnDefs": [
                            { "width": "18%", "targets": 0 },
                            { "width": "6%", "targets": 2 }
                        ]
            });
            $(document).on('click','.addRow',function(){
                var num = $("#webappnum").val();
                num = Number(num);
                num+=1;
                $("#webappnum").val(num);

                tables.row.add( [
                    '<select name="webappList" id="addwebapplist_'+num+'" class="custom-select" onchange="select(this, this.id);">' +
                        '<option value="null">선택</option>'+
                    '</select>',
                    '<select name="serviceList" id="addservicelist_'+num+'" class="custom-select">' +
                        '<option value="null">선택</option>'+
                    '</select>',

                    '<button type="button" class="btn btn-add-circle deleteRow"><i class="fas fa-minus-circle"></i></button>'
                ] ).draw( false );
                webapplist(num);
            });
            $(document).on('click','.deleteRow',function(){
                tables.row( $(this).parents('tr') ).remove().draw();
            });
        </script>

        <div class="sub_tit">
            <h3 class="tit">배너</h3>
        </div>
        <div class="table_wrap">
            <table class="table" id="table2">
                <thead>
                    <tr>
                        <th style="width:95%" scope="col">파일명 / 링크정보</th>
                        <th style="width:5%" scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${bannerInfo}" var="list" varStatus="status">
                        <tr>
                            <td>
                                <select name="bannerList" id="bannerlist_${status.index}" class="custom-select">
                                    <option value="null">선택</option>
                                </select>
                            </td>
                            <td><button class="btn btn-add-circle deleteRowBanr"><i class="fas fa-minus-circle"></i></button></td>
                        </tr>
                        <script>bannerlist('${status.index}','<c:out value="${list.blltNo}"/>')</script>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area no_paging">
                <div class="left_area">
                    <button class="btn btn-add addRowBanr"><i class="fas fa-plus-square"></i>추가</button>
                </div>
            </div>
            <input type="text" id="bannernum" value="0" style="display:none;"/>
        </div>
        <script type="text/javascript">
            var tables2 = $('#table2').DataTable({
                        "order": [],
                        "bLengthChange" : false,
                        "dom": '<<t>>',
                        "language": {
                            "info": "Total <span>_TOTAL_</span>건",
                            "infoEmpty":"Total <span>0</span>건",
                            "emptyTable": "데이터가 없습니다."
                        },
                        "columnDefs": [
                            { "width": "95%", "targets": 0 },
                            { "width": "5%", "targets": 1 }
                        ]
            });
            $(document).on('click','.addRowBanr',function(){
                var num2 = $("#bannernum").val();
                num2 = Number(num2);
                num2+=1;
                $("#bannernum").val(num2);

                tables2.row.add( [
                    '<select name="bannerList" id="addbannerlist_'+num2+'" class="custom-select">' +
                        '<option value="null">선택</option>'+
                    '</select>',
                    '<button type="button" class="btn btn-add-circle deleteRowBanr"><i class="fas fa-minus-circle"></i></button>'
                ] ).draw( false );
                banrlist(num2);
            });
            $(document).on('click','.deleteRowBanr',function(){
                tables2.row( $(this).parents('tr') ).remove().draw();
            });
        </script>

        </c:if>

        <div class="tbl_btm_area2 line">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="back();">취소</button>
                <button class="btn btn-bluegreen" type="button" onclick="submit_btn();">저장</button>
            </div>
        </div>

    </div>

</div>

<form:form id="form_edit" action="/cm/housingcplx/info/editEtcAction" name="housingcplx" commandName="housingcplx" method="post">
    <input type="text" id="hcd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="measymd" name="enrgMeasYmd" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="rangecont" name="qryRangeCont" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="linklist" name="serviceLinkList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="unitlist" name="energyUnitList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="banrlist" name="bannerList" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="day" name="day" style="width:0;height:0;visibility:hidden"/>
</form:form>