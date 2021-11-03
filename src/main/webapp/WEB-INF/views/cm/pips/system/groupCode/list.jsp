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

    var groupCodeArray = new Array();

    $(document).on('change', 'input[name="check"]', function(){
        if($(this).is(":checked")){
            var val = $(this).val();
            groupCodeArray.push(val);
        } else{
            var val = $(this).val();
            for(let i = 0; i < groupCodeArray.length; i++) {
              if(groupCodeArray[i] == val) {
                groupCodeArray.splice(i, 1);
                i--;
              }
            }
        }
    });

    $(document).ready(function(){
        $('#table1').DataTable({
            "order": [],
            "bLengthChange" : false,
            "dom": '<i<t>p>',
            "language": {
                "info": "Total <span>_TOTAL_</span>건",
                "infoEmpty":"Total <span>0</span>건",
                "emptyTable": "조회된 데이터가 없습니다."
            },
            "columnDefs": [
                { "width": "6%", "targets": 0 },
                { "width": "20%", "targets": 1 },
                { "orderable": false, "targets": 0 }
            ]
        });

        $("#commCdGrpCd_").val("<c:out value ="${commCdGrpCd}"/>");
        $("#commCdGrpNm").val("<c:out value ="${commCdGrpNm}"/>");
    });

    //검색버튼을 눌렀을경우
    function btn_search(){
        var commCdGrpCd = $("#commCdGrpCd_").val();
        if(commCdGrpCd == "all"){
            commCdGrpCd = "";
        }

        $("#commCdGrpCd__").val(commCdGrpCd);
        $("#form_list").submit();
    }

    //체크박스 전체선택
    function checkall(){
        var check = document.getElementsByName('check');
        var checkall = document.getElementById("checkAll");

        if(checkall.checked){
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = true;
                groupCodeArray.push(check[i].value);
            }
        }else{
            for(var i = 0 ; i < check.length ; i++){
                check[i].checked = false;

                for(let j = 0; j < groupCodeArray.length; j++) {
                    if(groupCodeArray[j] == check[i].value) {
                        groupCodeArray.splice(j, 1);
                        j--;
                    }
                }
            }
        }
    }

    function btn_delete(){

        $("#groupCodeArray").val(groupCodeArray);

        if(groupCodeArray == null || groupCodeArray == ""){
            alert("삭제할 목록을 선택해주세요.");
            return;
        }

        if (confirm("하위 공통 코드도 삭제됩니다. 삭제하시겠습니까?")) {
            $("#form_delete").submit();
        }
    }

    function add(){
        location.href = "/cm/system/groupCode/add";
    }

    //리스트 클릭 수정
    function detailview(commCdGrpCd){
        $("#commCdGrpCd").val(commCdGrpCd);
        $("#form_edit").submit();
    }

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">그룹코드 목록</h2>
            <ul class="location">
                <li>시스템관리</li>
                <li>그룹코드 관리</li>
                <li>그룹코드 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:15%"/>
                    <col style="width:50%"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 그룹 코드 이름</th>
                        <td>
                            <select class="custom-select" id="commCdGrpCd_">
                                <option value="all">전체</option>
                                <c:forEach items="${groupCodeNameList}" var="groupCodeNameList" varStatus="status">
                                    <option value="<c:out value="${groupCodeNameList.commCdGrpCd}"/>"><c:out value="${groupCodeNameList.commCdGrpNm}"/>(<c:out value="${groupCodeNameList.commCdGrpCd}"/>)</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td colspan="4">
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll" onclick="checkall();">
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">그룹코드명</th>
                        <th scope="col">그룹코드</th>
                        <th scope="col">그룹코드 설명</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${groupCodeList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input" name="check" id="check${status.index}" value="<c:out value="${list.commCdGrpCd}"/>"/>
                                    <label class="custom-control-label" for="check${status.index}"></label>
                                </div>
                            </td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.commCdGrpCd}"/>');"><c:out value="${list.commCdGrpNm}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.commCdGrpCd}"/>');"><c:out value="${list.commCdGrpCd}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.commCdGrpCd}"/>');"><c:out value="${list.rem}"/></a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-bluegreen" type="button" onclick="btn_delete()">삭제</button>
                </div>
            </div>
        </div>

    </div>
</div>

<form:form id="form_list" action="/cm/system/groupCode/list" name="list" commandName="list" method="post">
      <input type="text" id="commCdGrpCd__" name="commCdGrpCd__" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_delete" action="/cm/system/groupCode/delete" name="delete" commandName="delete" method="post">
      <input type="text" id="groupCodeArray" name="groupCodeArray" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_edit" action="/cm/system/groupCode/edit" name="edit" commandName="edit" method="post">
      <input type="text" id="commCdGrpCd" name="commCdGrpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
