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
            <li class="on"><a href="#">개인정보 처리방침</a></li>
            <li onclick="tab_click('locationPolicy','')"><a href="#">위치조회 서비스 이용약관</a></li>
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
                        <!-- 개인정보처리방침 시작 -->



<div><(주)대우건설>('푸르지오 스마트홈'이하 '제공서비스'라 한다.)은(는) 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다.</div>

<div><(주)대우건설>('푸르지오 스마트홈') 은(는) 회사는 개인정보처리방침을 개정하는 경우 서비스 공지사항(또는 개별공지)을 통하여 공지할 것입니다.</div>

<div>○ 본 방침은 2020년 11월 26일부터 시행됩니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제1조. 개인정보의 처리 목적</div>
<div>(주)대우건설>('푸르지오 스마트홈'이하 '제공서비스'라 한다.)은(는) 개인정보를 다음의 목적을 위하여 최소한으로 개인정보를 처리하고 있습니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며, 이용 목적이 변경될 시에는 「개인정보 보호법」 제18조에 따라 사전 동의를 구할 예정입니다.</div>
<div>&nbsp;</div>

<div>1. 제공서비스 회원가입 및 관리</div>
<div>회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 각종 고지·통지, 고충처리, 분쟁 조정을 위한 기록 보존 등을 목적으로 개인정보를 처리합니다.</div>
<div>&nbsp;</div>

<div>2. 민원사무 처리</div>
<div>민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락·통지, 처리결과 통보 등을 목적으로 개인정보를 처리합니다.</div>
<div>&nbsp;</div>

<div>3. 재화 또는 서비스 제공</div>
<div>서비스 제공, 콘텐츠 제공, 맞춤 서비스 제공, 본인인증 등을 목적으로 개인정보를 처리합니다.</div>
<div>&nbsp;</div>

<div>4. 마케팅 및 광고에의 활용</div>
<div>신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 , 제휴서비스로 인한 서비스기회 제공, 인구통계학적 특성에 따른 서비스 제공 및 광고 게재 , 서비스의 유효성 확인, 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계 등을 목적으로 개인정보를 처리합니다.</div>
<div>&nbsp;</div>

<div>5. 개인영상정보</div>
<div>범죄의 예방 및 수사, 시설안전 및 화재예방 등을 목적으로 개인정보를 처리합니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제2조. 수집 및 이용 하는 개인정보 항목</div>
<div><(주)대우건설>('prugio.com'이하 '푸르지오 스마트홈')은(는) 다음의 개인정보 항목을 수집 및 이용하고 있습니다.</div>
<div>1. 필수항목 : 서비스 이용 기록, 접속 로그, 접속 IP 정보, 로그인 ID</div>
<div>2. 선택항목 : 이메일, 휴대전화번호, 자택주소, 이름</div>
<div>&nbsp;</div>

<div class="policySubTitle">제3조. 개인정보의 처리 및 보유 기간</div>

<div><(주)대우건설>('푸르지오 스마트홈')은(는) 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의 받은 개인정보 보유,이용기간 내에서 개인정보를 처리,보유합니다.</div>
<div>&nbsp;</div>

<div>1. 제공서비스 회원가입 및 관리</div>
<div>제공서비스 회원가입 및 관리와 관련한 개인정보는 수집.이용에 관한 동의일로부터 <준 영구>까지 위 이용목적을 위하여 보유.이용됩니다.</div>
<div>&nbsp;</div>
<div>보유근거 : 서비스 이용을 위한 회원 가입 및 관리</div>

<div>2. 민원사무 처리</div>
<div>민원사무 처리와 관련한 개인정보는 수집.이용에 관한 동의일로부터 <3년>까지 위 이용목적을 위하여 보유.이용됩니다.</div>
<div>보유근거 : 관련법령</div>
<div>관련법령 : 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년</div>
<div>&nbsp;</div>



<div class="policySubTitle">제4조. 개인정보의 제3자 제공에 관한 사항</div>

<div><<(주)대우건설>('prugio.com'이하 '푸르지오 스마트홈')은(는) 원칙적으로 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공하며, 다음의 경우를 제외하고는 정보주체의 사전 동의 없이는 본래의 목적 범위를 초과하여 처리하거나 제3자에게 제공하지 않습니다.</div>
<div>1. 정보주체로부터 별도의 동의를 받는 경우</div>
<div>2. 다른 법률에 특별한 규정이 있는 경우</div>
<div>3. 정보주체 또는 그 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우</div>
<div>4. 통계작성 및 학술연구 등의 목적을 위하여 필요한 경우로서 특정 개인을 알아볼 수 없는 형태로 개인정보를 제공하는 경우</div>
<div>5. 범죄의 수사와 공소의 제기 및 유지를 위하여 필요한 경우</div>
<div>6. 법원의 재판업무 수행을 위하여 필요한 경우</div>
<div>7. 형 및 감호, 보호처분의 집행을 위하여 필요한 경우</div>
<div>8. 유료 제휴서비스 제공에 따른 요금 정산을 위하여 필요한 경우</div>
<div>&nbsp;</div>


<div><(주)대우건설>('prugio.com')은(는) 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.</div>
<div>&nbsp;</div>


<div>1. 제공받는 자 : 단지 관리사무소, 푸르지오서비스 대우에스티(주), 삼성전자(주), (주)KT, (주)아파트너, (주)세차왕, 째깍악어(주), 미소 등</div>
<div>2. 제공하는 정보  : 이메일, 휴대전화번호, 자택주소, 이름, 서비스 이용 기록, 접속 로그, 쿠키, 접속 IP 정보</div>
<div>3. 제공받는 자의 보유, 이용기간: 3년</div>
<div>&nbsp;</div>


<div class="policySubTitle">제5조. 정보주체와 법정대리인의 권리·의무 및 행사방법</div>

<div>1. 정보주체(만 14세 미만인 경우에는 법정대리인을 말함)는 (주)대우건설에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다.</div>
<div>2. 제1항에 따른 권리 행사는 (주)대우건설에 대해 개인정보 보호법 시행령 제41조제1항에 따라 서면, 전자우편, FAX 등을 통하여 하실 수 있으며 (주)대우건설은(는) 이에 대해 지체 없이 조치하겠습니다.</div>
<div>3. 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.</div>
<div>4. 개인정보 열람 및 처리정지 요구는 개인정보보호법 제35조 제54항, 제37조 제2항에 의하여 정보주체의 권리가 제한 될 수 있습니다.</div>
<div>5. 개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</div>
<div>6. (주)대우건설은(는) 정보주체 권리에 따른 열람의 요구·정정·삭제의 요구·처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제6조. 개인정보의 파기</div>

<div><(주)대우건설>('푸르지오 스마트홈')은(는) 원칙적으로 개인정보 보존기간이 경과하거나, 처리목적이 달성된 경우에는 지체 없이 해당 개인정보를 파기합니다. 다만, 다른 법령에 따라 보존하여야 하는 경우에는 그러지 않을 수 있습니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.</div>

<div>1. 파기절차</div>
<div>이용자가 입력한 정보는 목적 달성 후 내부 방침 및 기타 관련 법령에 따라 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.</div>
<div>&nbsp;</div>

<div>2. 파기기한</div>
<div>이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</div>
<div>&nbsp;</div>

<div>3. 파기방법</div>
<div>전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다.</div>
<div>종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.</div>
<div>&nbsp;</div>


<div class="policySubTitle">제7조. 개인정보 자동 수집 장치의 설치•운영 및 거부에 관한 사항</div>

<div>(주)대우건설 은 정보주체의 이용정보를 저장하고 수시로 불러오는 ‘쿠키’를 사용하지 않습니다.</div>
<div>&nbsp;</div>

<div class="policySubTitle">제8조. 개인정보 보호책임자 작성</div>

<div>(주)대우건설(‘prugio.com’이하 ‘푸르지오 스마트홈)은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.</div>
<div>&nbsp;</div>


<div>▶ 개인정보 보호책임자</div>
<div>성명 : 김중회</div>
<div>소속 : 주택건축사업본부</div>
<div>직급 : 상무</div>
<div>&nbsp;</div>

<div>▶ 개인정보 보호 담당부서</div>
<div>부서명 : 주택건축CS팀</div>
<div>전자우편 : prugiosmart@daewooenc.com</div>
<div>&nbsp;</div>

<div>정보주체께서는 (주)대우건설(‘prugio.com’이하 ‘푸르지오 스마트홈) 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. (주)대우건설(‘prugio.com’이하 ‘푸르지오 스마트홈) 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.</div>
<div>&nbsp;</div>


<div class="policySubTitle">제9조. 개인정보 처리방침 변경</div>

<div>이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.</div>
<div>&nbsp;</div>


<div class="policySubTitle">제10조. 개인정보의 안전성 확보 조치</div>

<div><(주)대우건설>('푸르지오 스마트홈')은(는) 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 다음과 같이 하고 있습니다.</div>

<div>1. 정기적인 자체 감사 실시</div>
<div>개인정보 취급 관련 안정성 확보를 위해 정기적으로 자체 감사를 실시하고 있습니다.</div>
<div>&nbsp;</div>

<div>2. 개인정보 취급 직원의 최소화 및 교육</div>
<div>개인정보를 취급하는 직원을 지정하고 최소화 하여 필수 의무교육 이수 후 개인정보를 관리하는 방침을 시행하고 있습니다.</div>
<div>&nbsp;</div>

<div>3. 내부관리계획의 수립 및 시행</div>
<div>개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</div>
<div>&nbsp;</div>

<div>4. 해킹 등에 대비한 기술적 대책</div>
<div><(주)대우건설>('푸르지오 스마트홈')은 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신·점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적/물리적으로 감시 및 차단하고 있습니다.</div>
<div>&nbsp;</div>

<div>5. 개인정보의 암호화</div>
<div>이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.</div>
<div>&nbsp;</div>

<div>6. 접속기록의 보관 및 위변조 방지</div>
<div>개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관, 관리하고 있으며, 접속 기록이 위변조 및 도난, 분실되지 않도록 보안기능 사용하고 있습니다.</div>
<div>&nbsp;</div>

<div>7. 개인정보에 대한 접근 제한</div>
<div>개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</div>
<div>&nbsp;</div>

<div>8. 문서보안을 위한 잠금장치 사용</div>
<div>개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 별도의 지정된 장소에 보관하고 있습니다.</div>
<div>&nbsp;</div>

<div>9. 비인가자에 대한 출입 통제</div>
<div>개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.</div>
<div>&nbsp;</div>


<div class="policySubTitle">제11조. 기타 개인정보 처리에 관한 방침</div>

<div>1. 링크사이트 제공 방침</div>
<div>㈜대우건설 ('푸르지오 스마트홈') 이용자에게 다른 회사의 웹사이트 또는 자료에 대한 링크를 제공할 수 있으며, 푸르지오 스마트홈 서비스를 통해 제3자가 개발한 제품 및 서비스를 사용할 수 있습니다. 이 경우 ㈜대우건설은 제3자의 사이트, 자료, 제품 및 서비스에 대한 아무런 통제권이 없으므로 그로부터 제공받는 제품 및 서비스나 자료의 유용성에 대해 책임질 수 없으며 보증할 수 없습니다.
푸르지오 스마트홈이 포함하고 있는 링크를 클릭(click)하여 타 사이트(site)의 페이지로 옮겨갈 경우 해당 사이트의 개인정보보호정책은 회사와 무관하므로 새로 방문한 사이트의 정책을 검토해 보시기 바랍니다. </div>
<div>&nbsp;</div>



                        <!-- 개인정보처리방침 종료 -->
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
