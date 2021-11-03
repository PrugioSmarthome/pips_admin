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
        location.href="/cm/mongo/test/add";
    }

    function btn_add2() {
        location.href="/cm/mongo/test/iframe";
    }

    function btn_add3() {
        location.href="/cm/mongo/test/redirect";
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


    //단지목록 팝업에서 단지를 선택했을경우
    function selectbtn(e){
        var str = e.getAttribute("id");

        var strarray = str.split("_");
        $("#houscplxNm").val(strarray[1]);
        $("#txtHouscplxNm").val(strarray[1]);
        $("#houscplxCd").val(strarray[0]);

        $("#closebtn").click();
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
            <h2 class="tit">테스트</h2>
        </div>
        <div class="search_area">
        </div>

        <div class="table_wrap">

            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" onclick="btn_add(); return false;"><i class="fas fa-plus-square">mongo</i></button>
                    <button class="btn btn-add" onclick="btn_add2(); return false;"><i class="fas fa-plus-square">iframe</i></button>
                    <button class="btn btn-add" onclick="btn_add3(); return false;"><i class="fas fa-plus-square">redirect</i></button>
                </div>
            </div>

            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">hshold_id</th>
                        <th scope="col">ymd</th>
                        <th scope="col">일반 관리비</th>
                        <th scope="col">청소비</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${resultList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center"><c:out value="${list.hshold_id}"/></td>
                        <td class="text-center"><c:out value="${list.ymd}"/></td>
                        <td class="text-center"><c:out value="${list.service_charge}"/></td>
                        <td class="text-center"><c:out value="${list.maintenance_cost}"/></td>
                    </tr>
                 </c:forEach>
                </tbody>
            </table>
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

</form:form>

<form:form id="form_detal" action="/cm/system/user/view" name="detail" commandName="detail" method="post">
    <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
</form:form>
