<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">


<script type="text/javascript">

    function list(num,cd){
        $.ajax({
            url: '/cm/housingcplx/info/cctv/list',
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

    function rowAdd(){
        var table = $('#table1').DataTable();
            table.row.add([
                    "<input type='text' name='cctvNm' class='form-control'/>",
                    "<input type='text' name='cont' class='form-control'/>",
                    "<input type='text' name='urlCont' class='form-control'/>",
                     "<button class='btn btn-add-circle deleteRow'><i class='fas fa-minus-circle''></i></button>"
            ]).draw();
    }

    function back(){
        window.history.back();
    }
    function submit_btn(){
        var cnt = $("input[name=cctvNm]").length;
        var finalArray = new Array();
        for(var i = 0 ; i < cnt ; i++){
            var jsonObject = new Object();
            if($("input[name='cctvNm']").eq(i).attr("value") == null ||  $("input[name='cctvNm']").eq(i).attr("value") == "") {
                alert('CCTV 이름은 필수 항목입니다.');
                return;
            }
            jsonObject.cctvNm = $("input[name='cctvNm']").eq(i).attr("value");
            jsonObject.cont = $("input[name='cont']").eq(i).attr("value");
            jsonObject.urlCont = $("input[name='urlCont']").eq(i).attr("value");
            if($("input[name='urlCont']").eq(i).attr("value") == null ||  $("input[name='urlCont']").eq(i).attr("value") == "") {
                alert('CCTV URL은 필수 항목입니다.');
                return;
            }
            jsonObject.crerId = "<c:out value="${session_user.userId}"/>";
            finalArray.push(jsonObject);
        }
        var cctvList = JSON.stringify(finalArray);
        $("#cctvList").val(cctvList);

        $("#form_edit").submit();

    }
</script>
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">CCTV 수정</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>CCTV</li>
                <li>CCTV 수정</li>
            </ul>
        </div>

        <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
        <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
        <div class="table_wrap">
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">CCTV명</th>
                        <th scope="col">설명</th>
                        <th scope="col">URL</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${cctvInfo}" var="info" varStatus="status">
                    <tr>
                        <td class="text-center"><input type="text" name="cctvNm" class="form-control" value="<c:out value="${info.cctvNm}"/>"/></td>
                        <td class="text-center"><input type="text" name="cont" class="form-control" value="<c:out value="${info.cont}"/>"/></td>
                        <td class="text-center"><input type="text" name="urlCont" class="form-control" value="<c:out value="${info.urlCont}"/>"/></td>
                        <td><button class="btn btn-add-circle deleteRow"><i class="fas fa-minus-circle"></i></button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area no_paging">
                <div class="left_area">
                   <button class="btn btn-add" type="button" onclick="rowAdd();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <input type="text" id="cctvnum" value="0" style="display:none;"/>
        </div>
        <script type="text/javascript">
            var tables = $('#table1').DataTable({
                        "order": [],
                        "bLengthChange" : false,
                        "paging" : false,
                        "dom": '<<t>>',
                        "language": {
                            "info": "Total <span>_TOTAL_</span>건",
                            "infoEmpty":"Total <span>0</span>건",
                            "emptyTable": "데이터가 없습니다."
                        },
                        "columnDefs": [
                            { "width": "25%", "targets": 0 },
                            { "width": "30%", "targets": 1 },
                            { "width": "40%", "targets": 2 },
                            { "width": "5%", "targets": 3 }
                        ]
            });
            $(document).on('click','.addRow',function(){
                var num = $("#cctvnum").val();
                num = Number(num);
                num+=1;
                $("#cctvnum").val(num);

                tables.row.add( [
                           '<td class="text-center"><input type="text" name="qty" class="form-control"/></td>',
                           '<td class="text-center"><input type="text" name="qty" class="form-control"/></td>',
                           '<td class="text-center"><input type="text" name="qty" class="form-control" }"/></td>',
                           '<button type="button" class="btn btn-add-circle deleteRow"><i class="fas fa-minus-circle"></i></button>'
                ] ).draw( false );
                webapplist(num);
            });
            $(document).on('click','.deleteRow',function(){
                tables.row( $(this).parents('tr') ).remove().draw();
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

<form:form id="form_edit" action="/cm/housingcplx/info/editCCTVAction" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="cctvList" name="cctvList" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="houscplxCd" name="houscplxCd" value="<c:out value="${houscplxCd}"/>" style="width:0;height:0;visibility:hidden"/>
</form:form>