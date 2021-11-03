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
    li {
        list-style : none;
    }
    a {
        color : #666;
        text-decoration : none;
    }

    .faq ul .question a {
        display : block;
        border-bottom : 1px solid #ccc;
        padding : 8px 10px;
        color : #222;
        font-weight : bold;
    }

    .faq ul .answer {
        display : none;
        border-bottom : 1px solid #ccc;
        padding : 8px 10px;
        color : #444;
        line-height : 1.6;
    }

    .faq ul .article .answer:target {
        display : block;
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

    function answer(n){
        if($("#"+n).is(":visible")){
            $("#"+n).css("display", "none");
        } else {
            $("#"+n).css("display", "block");
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
            <li onclick="tab_click('locationPolicy','')"><a href="#">위치조회 서비스 이용약관</a></li>
            <li class="on"><a href="#">FAQ</a></li>
        </ul>
    </div>

    <div class="faq">

    <ul>
        <li class="article">
            <p class="question"><a onclick="answer('a1')">Q : 푸르지오 스마트홈은 어떤 서비스인가요?</a></p>
            <p class="answer" id="a1">A : 푸르지오 입주민의 더 편리한 생활을 위해 제공되는 서비스입니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a2')">Q : SNS 계정 로그인할때 해당 SNS에 꼭 가입을 해야하나요?</a></p>
            <p class="answer" id="a2">A : 카카오톡, 네이버, 구글 등 해당 서비스 이용이 가능해야 간편 로그인을 할 수 있습니다.
                                          SNS 계정 가입이 싫거나 별도 회원가입이 하고 싶을 때는 스마트홈 아이디로 가입을 하시면 됩니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a3')">Q : 제어를 하면 로딩중이거나 제어에 실패했다는 메시지가 떠요.</a></p>
            <p class="answer" id="a3">A : 제어는 월패드의 단지 서버와 통신하여 실행이 됩니다. 단지서버가 외부에서 접근이 안되거나 단지
                                          서버의 고정 IP가 없을 경우에 제어에 실패할 수도 있습니다.
                                          또한, 제어하고자 하는 장치가 많은 경우(일괄 제어, 사용자 모드 등) 실제 제어가 실행되는 속도가
                                          느린 경우에도 발생할 수 있습니다. </p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a4')">Q : 에너지 사용량이 월패드 또는 관리비 내역서와 달라요.</a></p>
            <p class="answer" id="a4">A : 에너지 사용량은 앱 사용하는 시점부터 매일 누적되어 표시가 됩니다. 앱 사용 첫 달은 누적 사용량이
                                          다를 수 있으며, 이후에는 동일한 사용량이 표시됩니다. 그리고 관리비 부과되는 에너지 사용량은
                                          과금되는 사용량의 차이로 약간 다를 수 있습니다.</p>
        </li>
        <!--li class="article">
            <p class="question"><a onclick="answer('a5')">Q : 태블릿 PC에서도 사용할 수 있나요?</a></p>
            <p class="answer" id="a5">A : 푸르지오 스마트홈은 태블릿 PC 지원을 하지 않습니다. 향후에 개선을 통해 태블릿 PC에 적용여부를 검토하려고 합니다.</p>
        </li-->
        <li class="article">
            <p class="question"><a onclick="answer('a6')">Q : 단지 CCTV가 나오지 않아요.</a></p>
            <p class="answer" id="a6">A : CCTV 출력오류는 CCTV 제공업체와 푸르지오 스마트홈 서버 통신이 원할하지 않을때 발생할 수 있습니다. 또한 인터넷 사정에 의해 원할하지 않을 수 있습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a7')">Q : 사용자 모드는 몇 개까지 저장할 수 있나요?</a></p>
            <p class="answer" id="a7">A : 사용자 모드는 최대 8개까지 저장할 수 있습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a8')">Q : 한 세대당 몇 개의 계정을 가질 수 있나요?</a></p>
            <p class="answer" id="a8">A : 세대당 계정은 제한이 없습니다. 다만 최초에 가족 대표 계정이 생성되어야 하고, 세대 구성원은 가족 대표에게 승인을 받아 사용할 수 있습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a9')">Q : 로그인이 안됩니다.</a></p>
            <p class="answer" id="a9">A : 네트워크 또는 WI-FI 통신이 불안정하여 발생할 수 있습니다. 인터넷 상태를 점검 해보신 후에 다시 접속해보시기 바랍니다. 조치 후에도 발생하는 경우에는 SNS 로그인 상태 확인 먼저 부탁드리고, 그래도 발생하는 경우에는 콜센터 또는 prugiosmart@daewooenc.com 으로 단지명, 동,호수, 로그인 아이디와 함께 문의 주시면 해결해드리도록 하겠습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a10')">Q : 제어 기능이 작동되지 않아요</a></p>
            <p class="answer" id="a10">A : 푸르지오 스마트홈 앱 뿐만 아니라 세대 내에 있는 Wall Pad에서도 조작이 안되면 홈넷사로 문의를 주시면 됩니다. 그 외의 경우에는 콜센터 또는 prugiosmart@daewooenc.com 으로 단지명, 동,호수, 로그인 아이디와 함께 문의 주시면 해결해드리도록 하겠습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a11')">Q : 다른 푸르지오 단지와 메뉴와 기능이 달라요.</a></p>
            <p class="answer" id="a11">A : 입주시 안내된 각 단지별 서비스와 특성에 따라 일부 메뉴가 다를 수 있습니다. 또한 단지에 적용된 Wall Pad 기능에 따라 메뉴 및 기능이 다를 수 있습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a12')">Q : 푸시 알림이 이상해요.</a></p>
            <p class="answer" id="a12">A : 앱 내의 푸시 알림 설정 메뉴를 통해 알림이 설정되어 있어도 알림이 안오는 경우에는 “휴대폰-설정-앱-알림“ 부분에 알림 허용 여부를 확인해주시기 바랍니다. 이후에도 문제가 발생하는 경우에는 콜센터 또는 prugiosmart@daewooenc.com 으로 단지명, 동,호수, 로그인 아이디와 함께 문의 주시면 해결해드리도록 하겠습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a13')">Q : 휴대폰을 바꿨어요.</a></p>
            <p class="answer" id="a13">A : 푸르지오 스마트홈 앱을 다시 다운받으신 후에 기존에 사용하시던 아이디로 로그인 하시면 기존에 사용하시던대로 사용하실 수 있습니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a14')">Q : 단지 찾기에서 해당 단지가 없어요.</a></p>
            <p class="answer" id="a14">A : 푸르지오 스마트홈은 2020년 4월 이후 입주단지, 그리고 홈네트워크가 구성된 단지만 가능합니다.
                                          스마트홈 서비스가 확대되면 서비스 공지사항으로 안내드리오니 향후 공지를 확인해주시기 바랍니다.</p>
        </li>
        <li class="article">
            <p class="question"><a onclick="answer('a15')">Q : 오래된 폰에서도 동작을 하나요?</a></p>
            <p class="answer" id="a15">A : 푸르지오 스마트홈은 안드로이드 5.0/IOS 10.0 버전 이상에 최적화 되어 있습니다.
                                          (지원 이하 기종 또는 외산 폰의 경우는 기능 오류, 화면 깨짐 등의 현상이 발생할 수 있습니다.)</p>
        </li>

    </ul>

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
