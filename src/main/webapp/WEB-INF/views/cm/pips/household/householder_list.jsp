<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            },
            "columnDefs": [
                { "width": "6%", "targets": 0 },
                { "width": "20%", "targets": 1 },
                { "orderable": false, "targets": 0 }
            ]
        });
    });

    function click_row(id){

        location.href = "/cm/household/modify?userId="+id;
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">회원 관리</h2>
            <ul class="location">
                <li>세대 관리</li>
                <li>세대정보 관리</li>
                <li>세대정보 목록</li>
            </ul>
        </div>

        <div class="search_area">
            <table>
                <tbody>
                    <tr>
                        <th>· 동</th>
                        <td style="width:200px;">
                            <select name="" id="" class="custom-select">
                                <option value="">전체</option>
                            </select>
                        </td>
                        <th>· 호</th>
                        <td style="width:200px;">
                            <select name="" id="" class="custom-select">
                                <option value="">전체</option>
                            </select>
                        </td>
                        <th>· 이름</th>
                        <td style="width:200px;">
                            <input type="text" class="form-control" />
                        </td>
                        <td style="width:100px;">
                            <button type="button" class="btn btn-brown btn-sm">검색</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap">

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
                        <th scope="col">로그인 허용</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${PipsUser}" var="pipsUser" varStatus="status">
                    <tr>
                        <td class="text-center">
                            <div class="custom-control custom-checkbox">
                                <input type="checkbox" class="custom-control-input" id="check1">
                                <label class="custom-control-label" for="check1"></label>
                            </div>
                        </td>
                        <td class="text-center"><a href="#" onclick="click_row('${pipsUser.userId}')">${pipsUser.dongNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('${pipsUser.userId}')">${pipsUser.hoseNo}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('${pipsUser.userId}')">${pipsUser.userId}</a></td>
                        <td class="text-center"><a href="#" onclick="click_row('${pipsUser.userId}')">${pipsUser.userNm}</a></td>
                        <c:choose>
                        <c:when test="${pipsUser.fmlyTpCd eq 'REPRESENTATIVE'}">
                            <td class="text-center">가족대표</td>
                        </c:when>
                        <c:when test="${pipsUser.fmlyTpCd eq 'FAMILY'}">
                            <td class="text-center">구성원</td>
                        </c:when>
                        </c:choose>
                        <c:choose>
                        <c:when test="${pipsUser.useYn eq 'Y'}">
                            <td class="text-center">YES</td>
                        </c:when>
                        <c:when test="${pipsUser.useYn eq 'N'}">
                            <td class="text-center">NO</td>
                        </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="tbl_btm_area">
                <div class="right_area">
                    <button class="btn btn-green btn-sm" type="button"><i class="fas fa-table"></i>Export</button>
                </div>
            </div>
        </div>

    </div>
</div>