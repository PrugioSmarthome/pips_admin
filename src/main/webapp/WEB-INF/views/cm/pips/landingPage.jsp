<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html lang="ko">

<head>
    <meta charset="utf-8">
    <title>프루지오 스마트홈</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <style type="text/css">
        html,
        body,
        .landing {
            position: relative;
            height: 100%;
            padding: 0;
            margin: 0;
        }

        .landing {
            text-align: center;
            background: url("/images/bg.png") no-repeat 50% / cover;
            font-size: 0;
        }

        .contents {
            position: absolute;
            top: 50%;
            left: 50%;
            height: 90%;
            transform: translate(-50%, -50%);
        }

        .img {
            height: 100%;
        }

        .link {
            position: absolute;
            top: 86.2%;
            right: 0;
            bottom: 5.2%;
            left: 0;
            font-size: 1px;
            color: transparent;
        }
    </style>
    <script type="text/javascript">
        var market_a = "https://smarthome.prugio.com:18889/partner/etc/prugio_smarthome.apk";
//        var market_a = "market://details?id=com.daewooenc.pips.android";
        var market_i = "https://itunes.apple.com/kr/app/id1498575738";

        var currentOS;
        var mobile = (/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase()));

        if (mobile) {
            // 유저에이전트를 불러와서 OS를 구분합니다.
            var userAgent = navigator.userAgent.toLowerCase();
            if (userAgent.search("android") > -1)
                currentOS = "android";
            else if ((userAgent.search("iphone") > -1) || (userAgent.search("ipod") > -1) ||
                (userAgent.search("ipad") > -1))
                currentOS = "ios";
            else
                currentOS = "else";
        } else {
            // 모바일이 아닐 때
            currentOS = "nomobile";
        }
        // 마켓 이동
        function goMarket() {
            if (currentOS == "android") {
                location.href = market_a;
            } else if (currentOS == "ios") {
                location.href = market_i;
            } else {
                /* 기타 OS일 때 */
                alert("스마트폰에서만 접근이 가능합니다.");
            }
        }
    </script>

</head>

<body>
    <div class="landing">
        <div class="contents">
            <img src="/images/contents.png" alt="스마트 라이프의 시작 푸르지오 스마트 홈" class="img" />
            <a href="#" class="link" onclick="return goMarket()">푸르지오 스마트홈 다운로드</a>
        </div>
    </div>
</body>

</html>