<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-1.11.2.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-3.4.1.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-ui-1.11.3.custom.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-ui.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-migrate-1.2.1.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.validate.1.13.1.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/additional-methods.1.13.1.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.form.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.simplemodal.1.4.4.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/bootstrap.bundle.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dynatree.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/bootstrap-datepicker.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/bootstrap-datepicker.ko.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/common_ui.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf.common.js?v=1" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.menupage.js?v=2" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.paging.js?v=3" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.passwd.js?v=1" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.selectbox.js?v=1" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/sockjs/sockjs.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/stomp/stomp.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/filesaver/FileSaver.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/notification.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/login.js" />"></script>
<script type="text/javascript">
    var current_user = '<c:out value="${session_user.userId}"/>'
    var url = '<c:out value="${stompInfo.webUrl}"/>'
    var userId = '<c:out value="${stompInfo.webUserId}"/>'
    var messageInfo = decodeMessage('<c:out value="${stompInfo.webMessage}"/>');

    var webSocketClient;

    function subscribeMessage() {
        var messages = messageInfo.split(":");

        if (webSocketClient != null || webSocketClient != undefined) {
            disconnectWebSocketClient(webSocketClient);
        }

        webSocketClient = initWebSocketClient(url);
        initWebSocket(webSocketClient, userId, messages[1]);
    }

    if (current_user != null && current_user != '' && current_user != undefined &&
        url != undefined && userId != undefined && messageInfo != undefined) {
        subscribeMessage();
        setInterval(subscribeMessage, 60 * 1000);
    }

    function checkServerStatus() {
        var webServiceStatus = checkServerConnection();

        if (!webServiceStatus && current_user != null && current_user != '') {
            console.log("Server Restart or Down");
            alert("재로그인 해주십시오.");
            location.href = "/cm/login";
        }
    }

    checkServerStatus();
    setInterval(checkServerStatus, 10 * 1000);

    function checkSocketConnection() {
        var isWebAlive = checkServerConnection();

        if (isWebAlive) {
            console.log("isWebAlive is true");

            if (current_user != null && current_user != '' && current_user != undefined &&
                url != undefined && userId != undefined && messageInfo != undefined) {
                subscribeMessage();
                setInterval(subscribeMessage, 60 * 1000);
            }
        } else {
            console.log("isWebAlive is false");
            console.log("retryInitWebSocket failure");
        }
    }

    function checkServerConnection(){
        var xhr = new XMLHttpRequest();
        var origin = window.location.origin;
        var fileUrl = origin + "/images/h1_logo.png";
        var r = Math.round(Math.random() * 10000);
        xhr.open('HEAD', fileUrl + "?subins=" + r, false);

        try {
            xhr.send();

            if (xhr.status >= 200 && xhr.status < 304) {
                return true;
            } else {
                return false;
            }
        } catch (e) {
            return false;
        }
    }

    function decodeMessage(str) {
        return decodeURIComponent(atob(str).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
    }

	function fileDownload(fileName, fileUrl) {
		var oReq = new XMLHttpRequest();
		// The Endpoint of your server
		var URLToPDF = fileUrl;

		// Configure XMLHttpRequest
		oReq.open("GET", URLToPDF, true);

		// Important to use the blob response type
		oReq.responseType = "blob";

		// When the file request finishes
		// Is up to you, the configuration for error events etc.
		oReq.onload = function() {
			// Once the file is downloaded, open a new window with the PDF
			// Remember to allow the POP-UPS in your browser
			var file = new Blob([oReq.response], {
				type: 'application/pdf'
			});

			// Generate file download directly in the browser !
			saveAs(file, fileName);
		};

		oReq.send();
	}

</script>
