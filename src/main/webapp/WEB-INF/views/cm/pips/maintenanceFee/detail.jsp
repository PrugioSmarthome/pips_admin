<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });

    function cancel(){
        window.history.back();
    }

</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">단지 관리비 상세</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지 관리비 관리</li>
                <li>단지 관리비 상세</li>
            </ul>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:10%"/>
                    <col style="width:28%"/>
                    <col style="width:8%"/>
                    <col style="width:11%"/>
                    <col style="width:8%"/>
                    <col style="width:11%"/>
                    <col style="width:8%"/>
                    <col style="width:16%"/>
                </colgroup>
                <tbody>
                    <tr>
                        <th>단지명</th>
                        <td><c:out value="${maintenanceFeeDetail.houscplxNm}"/></td>
                        <th>동</th>
                        <td><c:out value="${maintenanceFeeDetail.dongNo}"/></td>
                        <th>호</th>
                        <td><c:out value="${maintenanceFeeDetail.hoseNo}"/></td>
                        <th>날짜</th>
                        <td><c:out value="${maintenanceFeeDetail.yr}"/>년 <c:out value="${maintenanceFeeDetail.mm}"/>월</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="table_wrap2">
            <table class="table2">
                <colgroup>
                    <col style="width:150px"/>
                    <col />
                    <col style="width:150px"/>
                    <col />
                </colgroup>
                <tbody>
                    <tr>
                        <th>일반 관리비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.genMgmCstQty}"/></td>
                        <th>청소비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.cleanCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>소독비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.dfCstQty}"/></td>
                        <th>승강기유지비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.elevCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>수선 유지비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.repairMtCstQty}"/></td>
                        <th>장기수선충당금</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.longRepCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>선관위운영비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.cemcCstQty}"/></td>
                        <th>가수금</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.susCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>경비비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.expCstQty}"/></td>
                        <th>대표회의 운영비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.repMtCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>건물보험료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.builPreCstQty}"/></td>
                        <th>위탁관리수수료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.conMgmCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>일자리지원차감</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.jobSupCstQty}"/></td>
                        <th>세대 전기료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.hsholdElctCstQty}"/></td>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                        <th>공동전기료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.commElctCstQty}"/></td>
                        <th>승강기 전기료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.elevElctCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>TV 수신료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.tvCstQty}"/></td>
                        <th>세대수도료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.hsholdWtrsplCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>공동수도료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.commWtrsplCstQty}"/></td>
                        <th>세대 난방비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.hsholdHeatCstQty}"/></td>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                        <th>기본 열요금</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.basicHeatCstQty}"/></td>
                        <th>공동 열요금</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.commHeatCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>세대급탕비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.hsholdHotwtrCstQty}"/></td>
                        <th>생활폐기물수수료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.wastCommisionCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>전기차전기료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.eleccarElctCstQty}"/></td>
                        <th>세대출입카드</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.hsholdAccCardCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>납기전 관리비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.beforeMgmcstQty}"/></td>
                        <th>납기후 관리비</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.afterMgmcstQty}"/></td>
                    </tr>
                    <tr>
                        <th>전기 사용 량</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.elctUseQty}"/></td>
                        <th>온수 사용 량</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.hotwtrUseQty}"/></td>
                    </tr>
                    <tr>
                        <th>수도 사용 율</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.wtrsplUseRate}"/></td>
                        <th>난방 사용 량</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.heatUseQty}"/></td>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                        <th>가스 사용 율</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.gasUseRate}"/></td>
                        <th>당월부과금액</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentMgmCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>미납금액</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.unpaidMgmCstQty}"/></td>
                        <th>미납연체</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.unpaidArrMgmCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>납기후 연체료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.overdueMgmCstQty}"/></td>
                        <th>전기전월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.beforeElctQty}"/></td>
                    </tr>
                    <tr>
                        <th>전기당월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentElctQty}"/></td>
                        <th>온수전월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.beforeHotwtrQty}"/></td>
                    </tr>
                    <tr>
                        <th>온수당월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentHotwtrQty}"/></td>
                        <th>수도전월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.beforeWtrsplQty}"/></td>
                    </tr>
                    <tr>
                        <th>수도당월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentWtrsplQty}"/></td>
                        <th>난방전월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.beforeHeatQty}"/></td>
                    </tr>
                    <tr>
                    </tr>
                    <tr>
                        <th>난방당월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentHeatQty}"/></td>
                        <th>가스전월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.beforeGasQty}"/></td>
                    </tr>
                    <tr>
                        <th>가스당월지침</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentGasQty}"/></td>
                        <th>관리비소계</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.sumMgmCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>징수대행</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.agencyMgmCstQty}"/></td>
                        <th>당월후연체료</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.currentAfterUnpaidCstQty}"/></td>
                    </tr>
                    <tr>
                        <th>전기할인요금</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.elctDiscountCstQty}"/></td>
                        <th>수도할인요금</th>
                        <td><fmt:formatNumber type="number" value="${maintenanceFeeDetail.wtrsplDiscountCstQty}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button class="btn btn-gray" type="button" onclick="cancel();">목록</button>
            </div>
        </div>

    </div>

</div>