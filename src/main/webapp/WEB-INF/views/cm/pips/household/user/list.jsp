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
        selectList();

        $("#dongNo").val("<c:out value="${dongNo}"/>");
        $("#hoseNo").val("<c:out value="${hoseNo}"/>");
        $("#name").val("<c:out value="${userNm}"/>");
    });


    //동,호 리스트 가져오기
    function selectList(){
        var param = new Object();
        param.delYn = "N";
        param.houscplxCd = "<c:out value="${session_user.houscplxCd}"/>";

        var dong = "<c:out value="${dongNo}"/>";
        var hose = "<c:out value="${hoseNo}"/>";

        $.ajax({
            url: '/cm/common/household/list',
            type: 'POST',
            data: param,
            dataType : "json",
            success: function(data){
                $("#search_text").value = "";
                $("#dong").empty();
                $("#hose").empty();
                if(dong == "all"){
                    $("#dong").append("<option value='all' selected>전체</option>");
                }else{
                    $("#dong").append("<option value='all'>전체</option>");
                }
                if(hose == "all"){
                    $("#hose").append("<option value='all' selected>전체</option>");
                }else{
                    $("#hose").append("<option value='all'>전체</option>");
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
                     if(hose == item.hoseNo){
                        $("#hose").append("<option selected value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                     }else{
                        $("#hose").append("<option value='"+item.hoseNo+"'>"+item.hoseNo+"</option>");
                     }

                })
            },
            error: function(e){

            },
            complete: function() {
            }
        });
    }



    //검색버튼을 눌렀을경우
    function btn_search(){
        $("#dongNo").val($("#dong").val());
        $("#hoseNo").val($("#hose").val());
        $("#userNm").val($("#name").val());

        $("#form_search").submit();
    }

    function excel(){
        var list = new Array();

        <c:forEach items="${pipsUserList}" var="list" varStatus="status">
            var cJson = new Object();
            cJson.동 = "<c:out value="${list.dongNo}"/>";
            cJson.호 = "<c:out value="${list.hoseNo}"/>";
            cJson.아이디 = "<c:out value="${list.userId}"/>";
            cJson.이름 = "<c:out value="${list.userNm}"/>";
            <c:choose>
                <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                    cJson.회원구분 = "가족대표";
                </c:when>
                <c:when test="${list.fmlyTpCd eq 'FAMILY'}">
                    cJson.회원구분 = "구성원";
                </c:when>
                <c:when test="${list.fmlyTpCd eq ''}">
                    cJson.회원구분 = "일반회원";
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${list.useYn eq 'Y'}">
                    cJson.로그인허용 = "YES";
                </c:when>
                <c:when test="${list.useYn eq 'N' || list.useYn eq ''}">
                    cJson.로그인허용 = "NO";
                </c:when>
            </c:choose>
            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);


        var param = new Object();
        param.title = "회원정보 목록";
        param.tableHeader = "['동', '호', '아이디', '이름', '회원구분', '로그인허용']";
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
                link.download= today+"_회원정보 목록.xlsx";
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
            <h2 class="tit">세대정보 목록</h2>
            <ul class="location">
                <li>세대 관리</li>
                <li>세대정보 관리</li>
                <li>세대정보 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>· 동</th>
                        <td>
                            <select name="dong" id="dong" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                        <th>· 호</th>
                        <td>
                            <select name="hose" id="hose" class="custom-select">
                                <option value="all">전체</option>
                            </select>
                        </td>
                        <th>· 이름</th>
                        <td>
                            <input type="text" class="form-control" id="name"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-brown btn-sm" onclick="btn_search();">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">
            <div class="tbl_top_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button" onclick="excel();"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="checkAll">
                                <label class="custom-control-label" for="checkAll"></label>
                            </div>
                        </th>
                        <th scope="col">동</th>
                        <th scope="col">호</th>
                        <th scope="col">아이디</th>
                        <th scope="col">이름</th>
                        <th scope="col">회원구분</th>
                        <th scope="col">로그인허용</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${pipsUserList}" var="list" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="check1">
                                <label class="custom-control-label" for="check1"></label>
                            </div>
                        </td>
                        <c:choose>
                            <c:when test="${list.dongNo eq ''}">
                                <td class="text-center">-</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><c:out value="${list.dongNo}"/></td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.hoseNo eq ''}">
                                <td class="text-center">-</td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center"><c:out value="${list.hoseNo}"/></td>
                            </c:otherwise>
                        </c:choose>

                        <td class="text-center"><c:out value="${list.userId}"/></td>
                        <td class="text-center"><c:out value="${list.userNm}"/></td>

                        <c:choose>
                            <c:when test="${list.fmlyTpCd eq 'REPRESENTATIVE'}">
                                <td class="text-center">가족대표</td>
                            </c:when>
                            <c:when test="${list.fmlyTpCd eq 'FAMILY'}">
                                <td class="text-center">구성원</td>
                            </c:when>
                            <c:when test="${list.fmlyTpCd eq ''}">
                                <td class="text-center">일반회원</td>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${list.useYn eq 'Y'}">
                                <td class="text-center">YES</td>
                            </c:when>
                            <c:when test="${list.useYn eq 'N' || list.useYn eq ''}">
                                <td class="text-center">NO</td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-bluegreen" type="button">입주민탈퇴</button>
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

<form:form id="form_search" action="/cm/household/user/list" name="info" commandName="info" method="post">
      <input type="text" id="dongNo" name="dongNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="hoseNo" name="hoseNo" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="userNm" name="userNm" style="width:0;height:0;visibility:hidden"/>
</form:form>