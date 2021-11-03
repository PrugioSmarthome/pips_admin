<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/browser.js" />"></script>
<style type="text/css">
   .modal-content {
       width: 720px;
       height: 360px;
       margin:0 auto;
       display:table;
       position: absolute;
       left: 0;
       right:0;
       top: 50%;
       border:1px solid;
       -webkit-transform:translateY(-50%);
       -moz-transform:translateY(-50%);
       -ms-transform:translateY(-50%);
       -o-transform:translateY(-50%);
       transform:translateY(-50%);
    }
</style>
<script type="text/javascript">

    $(document).ready(function() {

        var agent = navigator.userAgent.toLowerCase();
        var chromeCheck = true;

        if(agent.indexOf("opr") !== -1){
            chromeCheck = false;
        }else if(agent.indexOf("edge") !== -1){
            chromeCheck = false;
        }else if(agent.indexOf("edg") !== -1){
            chromeCheck = false;
        }else if(!(agent.indexOf("chrome") !== -1)){
            chromeCheck = false;
        }

        if(!chromeCheck){
            if(agent.indexOf("trident") !== -1){
                alert("Internet Explorer에서는 정상적으로 동작이 되지 않을 수 있습니다. 가급적이면 Chrome을 이용해 주시기 바랍니다.");
            }else{
                alert("Internet Explorer에서는 정상적으로 동작이 되지 않을 수 있습니다.\n가급적이면 Chrome을 이용해 주시기 바랍니다.");
            }
        }

        $("#text_nm").keydown(function(key) {
            if (key.keyCode == 13) {
                login();
            }
        });
    });

    function browserCheck(e){
        console.log(e);
    }
</script>
<form name="frmMenuHandle"></form>
<form class="form-signin" name="formLogin" method="post" action="/cm/login">
    <div class="login_wrap">
        <div class="logo">
            <div><img src="/images/h1_logo.png" alt="PRUGIO" /></div>
            <div>스마트홈 플랫폼 앱 관리자</div>
        </div>
        <div class="in pt150">
            <h1 class="tit">LOGIN</h1>
            <div class="txt1">단지 관리자 비밀번호 분실 시 [비밀번호 재설정] 버튼을<br />클릭하여 비밀번호를 재설정 하시기 바랍니다.</div>
            <ul class="inp_list">
                <li>
                    <h2 class="tit">아이디</h2>
                    <div class="inp"><input type="text" class="form-control" id="text_id" name="text_id" value="sysadmin"/></div>
                </li>
                <li>
                    <h2 class="tit">비밀번호</h2>
                    <div class="inp"><input type="password" class="form-control" id="text_nm" name="text_nm" value="eodnS2020!@"/></div>
                </li>
            </ul>
            <div class="btn_area clearfix">
                <div class="float-left"><a href="/cm/authorization/passwd/authPassword" class="link">비밀번호 재설정</a></div>
                <div class="float-right"><input type="button" class="btn btn-bluegreen" value="로그인" onclick="javascript:login();"/></div>

            </div>
        </div>
    </div>

    <div class="modal fade" id="modal1" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-dialog-centered " role="document">
            <div class="modal-content" id="modal-intro">
                <div class="modal-header">
                    <h5 class="modal-title">안내사항</h5>
                    <button type="button" id="closebtn" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt=""></button>
                </div>

                <div class="modal-body">
                    <div class="search_area">
                        <span>
                            IE 환경에서는 시스템을 원활하게 사용할 수 없습니다.
                            Chrome 환경에서 시스템을 이용해주시는것을 권장드립니다.
                            <a href="https://www.google.com/intl/ko/chrome/">크롬 다운로드</a>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>