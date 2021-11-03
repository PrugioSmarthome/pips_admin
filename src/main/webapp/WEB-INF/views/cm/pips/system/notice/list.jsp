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

        $("#start").val("<c:out value="${start}"/>");
        $("#startCrDt").val("<c:out value="${start}"/>");
        $("#end").val("<c:out value="${end}"/>");
        $("#endCrDt").val("<c:out value="${end}"/>");
        var str = "<c:out value="${title}"/>";
        str = str.replace(/&lt;/g,"<");
        str = str.replace(/&gt;/g,">");
        $("#title_").val(str);
        $("#title").val(str);
    });

    function add(){
        location.href = "/cm/system/notice/add";
    }

    function detailview(no){
        $("#blltNo").val(no);
        $("#form_detail").submit();
    }

    function btn_yn(num, tlrncYn, houscplxCdList){
        if (tlrncYn == "Y") {
            alert("이미 게시완료한 공지사항 입니다.");
            return;
        }

        var conf = confirm("해당 공지사항을 게시 하시겠습니까?");
        if(conf == true){
            $("#tlrncYn").val('Y');
            $("#blltNo_").val(num);
            $("#houscplxCdList").val(houscplxCdList);
            $("#form_open").submit();
        }
    }

    function btn_search(){
        var RegExp = /[\,;:|*~`!^\-_+┼<>@\#$%&\'\"\\\=]/gi;
        var str = $("#title_").val();
        if(RegExp.test(str) == true){
            alert('특수문자는 사용할 수 없습니다.');
            return;
        }

        str = str.replace(/</g,"&lt;");
        str = str.replace(/>/g,"&gt;");

        $("#title").val(str);
        $("#startCrDt").val($("#start").val().replace(/\./gi,''));
        $("#endCrDt").val($("#end").val().replace(/\./gi,''));

        $("#form_list").submit();
    }

    function excel(){
        var list = new Array();
        <c:forEach items="${noticeList}" var="list" varStatus="status">
            var cJson = new Object();
            cJson.등록일 = '<fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/>';
            cJson.제목 = '<c:out value="${list.title}"/>';
            cJson.상태 = '<c:out value="${list.tlrncYnNm}"/>';

            list.push(cJson);
        </c:forEach>

        var hJson = JSON.stringify(list);

        var param = new Object();
        param.title = "서비스 공지사항";
        param.tableHeader = "['등록일', '제목', '상태']";

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
                link.download= today+"_서비스공지사항.xlsx";
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
            <h2 class="tit">서비스 공지사항 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>서비스 공지사항</li>
                <li>서비스 공지사항 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 등록일</th>
                        <td style="width:350px;">
                            <div class="input-daterange input-group" id="datepicker">
                                <input type="text" class="form-control inp_calendar" name="start" id="start" value="<c:out value="${start}"/>" />
                                <span class="bul_space">~</span>
                                <input type="text" class="form-control inp_calendar" name="end" id="end" value="<c:out value="${end}"/>" />
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
                            <input type="text" id="title_" class="form-control" value="<c:out value="${title}"/>" />
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
                    <button class="btn btn-add" type="button" onclick="add();"><i class="fas fa-plus-square"></i></button>
                </div>
            </div>
            <table class="table" id="table1">
                <thead>
                    <tr>
                        <th scope="col">등록일</th>
                        <th scope="col">제목</th>
                        <th scope="col">상태</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${noticeList}" var="list" varStatus="status">
                        <tr>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.blltNo}"/>');"><fmt:formatDate value="${list.crDt}" pattern="yyyy-MM-dd"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.blltNo}"/>');"><c:out value="${list.title}"/></a></td>
                            <td class="text-center"><a href="#" onclick="detailview('<c:out value="${list.blltNo}"/>');"><c:out value="${list.tlrncYnNm}"/></a></td>
                            <c:if test="${list.tlrncYn eq 'Y'}">
                                <td class="text-center">-</td>
                            </c:if>
                            <c:if test="${list.tlrncYn eq 'N'}">
                                <td class="text-center"><button class="btn btn-gray btn-sm" type="button" onclick="btn_yn('<c:out value="${list.blltNo}"/>', '<c:out value="${list.tlrncYn}"/>', '<c:out value="${list.houscplxCdList}"/>');">공지게시</button></td>
                            </c:if>

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
                "columnDefs": [
                    { "width": "20%", "targets": 0 },
                    { "width": "15%", "targets": 2 },
                    { "width": "15%", "targets": 3 }
                ],

            });
        </script>
    </div>
</div>

<form:form id="form_list" action="/cm/system/notice/list" name="notice" commandName="notice" method="post">
      <input type="text" id="title" name="title" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="startCrDt" name="startCrDt" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="endCrDt" name="endCrDt" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_detail" action="/cm/system/notice/view" name="detail" commandName="detail" method="post">
    <input type="text" id="blltNo" name="blltNo" style="width:0;height:0;visibility:hidden"/>
</form:form>

<form:form id="form_open" action="/cm/system/notice/editNoticePublishAction" name="open" commandName="open" method="post">
    <input type="text" id="tlrncYn" name="tlrncYn" value="N" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="blltNo_" name="blltNo" style="width:0;height:0;visibility:hidden"/>
    <input type="text" id="houscplxCdList" name="houscplxCdList" style="width:0;height:0;visibility:hidden"/>
</form:form>
