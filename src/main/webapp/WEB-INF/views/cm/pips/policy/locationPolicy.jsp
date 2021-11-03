<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<style>
    .policyTitle {
        font-weight : bold;
        font-size : 15px;
    }
    .policySubTitle {
        font-weight : bold;
        font-size : 13px;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {

    });

    //뒤로가기
    function back(){
        location.href = "/cm/policy";
    }
    function tab_click(e,n){
        if(e == "policy"){
            $("#policy").submit();
        }
        if(e == "privacy"){
            $("#privacy").submit();
        }
        if(e == "locationPolicy"){
            $("#locationPolicy").submit();
        }
        if(e == "faq"){
            $("#faq").submit();
        }
    }
</script>

<div id="nav" class="fixed-top">
    <div class="in">
        <h1 class="logo"><a href="/cm/policy"><img src="/images/h1_logo.png" alt="" /></a></h1>
    </div>
</div>


<div class="container">
    <div class="top_area" style="margin-top:100px">&nbsp;</div>


    <div class="tab_wrap">
        <ul>
            <li onclick="tab_click('policy','')"><a href="#">서비스 이용약관</a></li>
            <li onclick="tab_click('privacy','')"><a href="#">개인정보 처리방침</a></li>
            <li class="on"><a href="#">위치조회 서비스 이용약관</a></li>
            <li onclick="tab_click('faq','')"><a href="#">FAQ</a></li>
        </ul>
    </div>

    <div class="table_wrap">
        <table class="table">
            <colgroup>
                <col />
            </colgroup>
            <tbody>
                <tr>
                    <td>
                        <div class="depth_tr">
                        <!-- 위치기반 서비스 이용약관 시작 -->

<div class="policySubTitle">제 1 조 (목적)</div>
<div>본 약관은 주식회사 대우건설(이하 "회사")이 제공하는 위치기반서비스에 대해 회사와 위치기반서비스를 이용하는 개인위치정보주체(이하 "이용자")간의 권리·의무 및 책임사항, 기타 필요한 사항 규정을 목적으로 합니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 2 조 (이용약관의 효력 및 변경)</div>
<div>①본 약관은 이용자가 본 약관에 동의하고 회사가 정한 절차에 따라 위치기반서비스의 이용자로 등록됨으로써 효력이 발생합니다.</div>
<div>②회사는 법률이나 위치기반서비스의 변경사항을 반영하기 위한 목적 등으로 약관을 수정할 수 있습니다.</div>
<div>③약관이 변경되는 경우 회사는 변경사항을 최소 7일 전에 회사의 홈페이지 등 기타 공지사항 페이지를 통해 게시합니다.</div>
<div>④단, 개정되는 내용이 이용자 권리의 중대한 변경이 발생하는 경우에는 30일 전에 게시하도록 하겠습니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 3 조 (약관 외 준칙)</div>
<div>이 약관에 명시되지 않은 사항에 대해서는 위치 정보의 보호 및 이용 등에 관한 법률, 위치정보법, 전기통신사업법, 정보통신망 이용 촉진및 보호 등에 관한 법률 등 관계법령 및 회사가 정한 지침 등의 규정에 따릅니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 4 조 (서비스의 내용)</div>
<div>회사는 직접 수집하거나 위치정보사업자로부터 수집한 이용자의 현재 위치 또는 현재 위치가 포함된 지역을 이용하여 아래와 같은 위치기반서비스를 제공합니다.</div>

<div>①위치정보를 활용한 정보 검색결과 및 콘텐츠를 제공하거나 추천</div>
<div>③위치기반의 컨텐츠 분류를 위한 콘텐츠 태깅(Geotagging))</div>
<div>④위치기반의 맞춤형 정보 알림</div>

<div class="policySubTitle">제 5 조 (서비스 이용요금)</div>
<div>회사가 제공하는 위치기반서비스는 인터넷 포털사에서 제공받아 무료이나, 포털사의 정책에 따라 유료서비스로 전환 가능합니다.</div>
<div>단, 무선 서비스 이용 시 발생하는 데이터 통신료는 별도이며, 이용자가 가입한 각 이동통신사의 정책에 따릅니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 6 조 (서비스 이용의 제한·중지)</div>
<div>①회사는 위치기반서비스사업자의 정책변경 등과 같이 회사의 제반사정 또는 법률상의 이유로 위치기반서비스를 유지할 수 없는 경우 위치기반서비스의 전부 또는 일부를 제한·변경·중지할 수 있습니다.</div>
<div>②단, 위 항에 의한 위치기반서비스 중단의 경우 회사는 사전에 회사 홈페이지 등 기타 공지사항 페이지를 통해 공지하거나 이용자에게 통지합니다.</div>
<div>③ 서비스의 이용은 연중무휴 1일 24시간을 원칙으로 합니다. 단, 회사의 업무상이나 기술상의 이유로 서비스가 일시 중지될 수 있으며, 운영상의 목적으로 회사가 정한 기간에는 서비스가 일시 중지될 수 있습니다.</div>
<div>④ 위치정보는 관련 기술의 발전에 따라 오차가 발생할 수 있습니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 7 조 (개인위치정보주체의 권리)</div>
<div>①이용자는 언제든지 개인위치정보의 수집·이용·제공에 대한 동의 전부 또는 일부를 유보할 수 있습니다.</div>
<div>②이용자는 언제든지 개인위치정보의 수집·이용·제공에 대한 동의 전부 또는 일부를 철회할 수 있습니다. 이 경우 회사는 지체 없이 철회된 범위의 개인위치정보 및 위치정보 수집·이용·제공사실 확인자료를 파기합니다.</div>
<div>③이용자는 개인위치정보의 수집·이용·제공의 일시적인 중지를 요구할 수 있으며, 이 경우 회사는 이를 거절할 수 없고 이를 충족하는 기술적 수단을 마련합니다.</div>
<div>④이용자는 회사에 대하여 아래 자료에 대한 열람 또는 고지를 요구할 수 있으며, 해당 자료에 오류가 있는 경우에는 정정을 요구할 수 있습니다. 이 경우 정당한 사유 없이 요구를 거절하지 않습니다.</div>
<div>1.이용자에 대한 위치정보 수집·이용·제공사실 확인자료</div>
<div>2.이용자의 개인위치정보가 위치정보의 보호 및 이용 등에 관한 법률 또는 다른 법령의 규정에 의하여 제3자에게 제공된 이유 및 내용</div>
<div>⑤이용자는 권리행사를 위해 본 약관 제14조의 연락처를 이용하여 회사에 요청할 수 있습니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 8 조 (개인위치정보의 이용 또는 제공)</div>
<div>①회사는 개인위치정보를 이용하여 위치기반서비스를 제공하는 경우 본 약관에 고지하고 동의를 받습니다.</div>
<div>②회사는 이용자의 동의 없이 개인위치정보를 제3자에게 제공하지 않으며, 제3자에게 제공하는 경우에는 제공받는 자 및 제공목적을 사전에 이용자에게 고지하고 동의를 받습니다.</div>
<div>③회사는 개인위치정보를 이용자가 지정하는 제3자에게 제공하는 경우 개인위치정보를 수집한 통신단말장치로 매회 이용자에게 제공받는 자, 제공일시 및 제공목적을 즉시 통지합니다.</div>
<div>④단, 아래의 경우 이용자가 미리 특정하여 지정한 통신단말장치 또는 전자우편주소, 온라인게시 등으로 통지합니다.</div>
<div>1.개인위치정보를 수집한 당해 통신단말장치가 문자, 음성 또는 영상의 수신기능을 갖추지 아니한 경우</div>
<div>2.이용자의 개인위치정보를 수집한 통신단말장치 외의 통신단말장치 또는 전자우편주소, 온라인게시 등으로 통보할 것을 미리 요청한 경우</div>
<div>⑤회사는 위치정보의 보호 및 이용 등에 관한 법률 제16조 제2항에 근거하여 개인위치정보 수집·이용·제공사실 확인자료를 자동으로 기록·보존하며, 해당 자료는 6개월간 보관합니다.</div>
<div>&nbsp;</div>

<div>[위치정보 제공 현황 자세히 보기]</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 9 조 (법정대리인의 권리)</div>
<div>회사는 14세 미만의 이용자에 대해서는 개인위치정보를 이용한 위치기반서비스 제공 및 개인위치정보의 제3자 제공에 대한 동의를 이용자 및 이용자의 법정대리인으로부터 동의를 받아야 합니다. 이 경우 법정대리인은 본 약관 제7조에 의한 이용자의 권리를 모두 가집니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 10 조 (8세 이하의 아동 동의 보호의무자의 권리)</div>
<div>①회사는 아래의 경우에 해당하는 자(이하 “8세 이하의 아동 등”)의 위치정보의 보호 및 이용 등에 관한 법률 제26조2항에 해당하는 자(이하 “보호의무자”)가 8세 이하의 아동 등의 생명 또는 신체보호를 위하여 개인위치정보의 이용 또는 제공에 동의하는 경우에는 본인의 동의가 있는 것으로 봅니다.</div>
<div>1.8세 이하의 아동</div>
<div>2.피성년후견인</div>
<div>3.장애인복지법 제2조제2항제2호에 따른 정신적 장애를 가진 사람으로서 장애인고용촉진 및 직업재활법 제2조제2호에 따른 중증장애인에 해당하는 사람(장애인복지법 제32조에 따라 장애인 등록을 한 사람만 해당한다)</div>
<div>②8세 이하의 아동 등의 생명 또는 신체의 보호를 위하여 개인위치정보의 이용 또는 제공에 동의를 하고자 하는 보호의무자는 서면동의서에 보호의무자임을 증명하는 서면을 첨부하여 회사에 제출하여야 합니다.</div>
<div>③보호의무자는 8세 이하의 아동 등의 개인위치정보 이용 또는 제공에 동의하는 경우 본 약관 제7조에 의한 이용자의 권리를 모두 가집니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 11 조 (손해배상)</div>
<div>① 고객 또는 위치기반서비스사업자가 본 약관의 규정을 위반함으로 인하여 회사에 손해가 발생하게 되는 경우, 본 약관을 위반한 당사자는 회사에 발생하는 모든 손해를 배상하여야 합니다.</div>
<div>② 고객 또는 위치기반서비스사업자의 불법행위나 본 약관 위반행위로 인하여 회사가 제3자로부터 손해배상청구 또는 소송을 비롯한 각종 이의제기를 받는 경우 당해 불법행위 또는 약관 위반행위를 한 당사자는 자신의 책임과 비용으로 회사를 면책시켜야 하며, 회사가 면책되지 못한 경우 당해 고객은 그로 인하여 회사에 발생한 손해를 배상하여야 합니다.</div>
<div>③ 회사의 위치정보법 제15조 내지 제26조의 규정을 위반한 행위 또는 회사가 제공하는 서비스로 인하여 고객에게 손해가 발생한 경우 회사가 고의 또는 과실이 없음을 입증하지 아니하면 고객의 손해에 대해 책임을 지며, 그 책임의 범위는 통상손해에 한합니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 12 조 (면책)</div>
<div>①회사는 다음 각 호의 경우로 위치기반서비스를 제공할 수 없는 경우 이로 인하여 이용자에게 발생한 손해에 대해서는 책임을 부담하지 않습니다.</div>
<div>1.천재지변 또는 이에 준하는 불가항력의 상태가 있는 경우</div>
<div>2.위치기반서비스 제공을 위하여 회사와 서비스 제휴계약을 체결한 제3자의 고의적인 서비스 방해가 있는 경우</div>
<div>3.이용자의 귀책사유로 위치기반서비스 이용에 장애가 있는 경우</div>
<div>4.제1호 내지 제3호를 제외한 기타 회사의 고의·과실이 없는 사유로 인한 경우</div>
<div>②회사는 위치기반서비스 및 위치기반서비스에 게재된 정보, 자료, 사실의 신뢰도, 정확성 등에 대해서는 보증을 하지 않으며 이로 인해 발생한 이용자의 손해에 대하여는 책임을 부담하지 아니합니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 13 조 (분쟁의 조정 및 기타)</div>
<div>①회사는 위치정보와 관련된 분쟁의 해결을 위해 이용자와 성실히 협의합니다.</div>
<div>②전항의 협의에서 분쟁이 해결되지 않은 경우, 회사와 이용자는 위치정보의 보호 및 이용 등에 관한 법률 제28조의 규정에 의해 방송통신위원회에 재정을 신청하거나, 개인정보보호법 제43조의 규정에 의해 개인정보 분쟁조정위원회에 조정을 신청할 수 있습니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 14 조 (회사의 주소 및 연락처)</div>
<div>회사의 상호, 주소 및 연락처는 아래와 같습니다.</div>
<div>&nbsp;</div>

<div>상호 : (주)대우건설</div>
<div>대표 : 김형</div>
<div>주소 : 서울시 중구 을지로 170</div>
<div>대표전화 : 02-2288-3114</div>
<div>&nbsp;</div>

<div>부칙</div>
<div class="policySubTitle">제 1 조 (시행일)</div>
<div>본 약관은 2020년 11월 26일부터 시행됩니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제 2 조 (위치정보관리책임자 정보)</div>
<div>회사는 개인위치정보를 적절히 관리·보호하고, 이용자의 불만을 원활히 처리할 수 있도록 실질적인 책임을 질 수 있는 지위에 있는 자를 위치정보관리책임자로 지정해 운영하고 있으며, 아래와 같이 위치정보관리책임자를 지정하고 있습니다. </div>
<div>&nbsp;</div>

<div>▶ 개인정보 보호책임자</div>
<div>성명 : 안상태</div>
<div>소속 : 주택건축사업본부</div>
<div>직급 : 상무</div>
<div>&nbsp;</div>

<div>▶ 개인정보 보호 담당부서</div>
<div>부서명 : 고객지원팀</div>
<div>&nbsp;</div>

                        <!-- 위치기반 서비스 이용약관 종료 -->
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="tbl_btm_area">
        <div class="right_area">

        </div>
    </div>

</div>


<form:form id="policy" action="/cm/policy" name="policy" commandName="policy" method="post">
</form:form>

<form:form id="privacy" action="/cm/privacy" name="privacy" commandName="privacy" method="post">
</form:form>

<form:form id="locationPolicy" action="/cm/locationPolicy" name="locationPolicy" commandName="locationPolicy" method="post">
</form:form>

<form:form id="faq" action="/cm/faq" name="faq" commandName="faq" method="post">
</form:form>
