function LinkSetting(obj, type, newWindow, link_val){
    var filter = "win16|win32|win64|mac|macintel";
    
    var pcweb_url = "";
    var moweb_url = "";
    var app_url = "";
    
    if(type == "coupon"){
        pcweb_url = "https://www.lotteshopping.com/coupon/couponDetail?cpnInfoNo=";
        moweb_url = "https://m.lotteshopping.com/coupon/couponDetail?cpnInfoNo=";
        app_url = "toapp:::AppViewMove:::coupon:::";  
    }else if(type == "shopping"){
        pcweb_url = "https://www.lotteshopping.com/shoppingNews/shoppingNewsDetail?shpgNewsNo=";
        moweb_url = "https://m.lotteshopping.com/shoppingNews/shoppingNewsDetail?shpgNewsNo=";
        app_url = "toapp:::AppViewMove:::shopping:::";        
    }else if(type == "saeun"){
        pcweb_url = "https://www.lotteshopping.com/thankyou/thankyouDetail?thkuNo=";
        moweb_url = "https://m.lotteshopping.com/thankyou/thankyouDetail?thkuNo=";
        app_url = "toapp:::AppViewMove:::saeun:::";        
    }else if(type == "event"){
        pcweb_url = "https://www.lotteshopping.com/event/eventDetail?entNo=";
        moweb_url = "https://m.lotteshopping.com/event/eventDetail?entNo=";
        app_url = "toapp:::AppViewMove:::event:::";        
    }else if(type == "magazine"){
        pcweb_url = "https://www.lotteshopping.com/magazine/magazineDetail?mazinNo=";
        moweb_url = "https://m.lotteshopping.com/magazine/magazineDetail?mazinNo=";
        app_url = "toapp:::AppViewMove:::magazine:::";        
    }else if(type == "BrowserOpen"){
        pcweb_url = "";
        moweb_url = "";
        app_url = "toapp:::AppViewMove:::BrowserOpen:::";        
    }else if(type == "xBrowser"){
        pcweb_url = "";
        moweb_url = "";
        app_url = "toapp:::AppViewMove:::SubModalView:::";        
    }

    
    if((newWindow != "yes") || (newWindow != "no")){
        newWindow = "yes";
    }
    
    if(navigator.platform){
        if(filter.indexOf(navigator.platform.toLowerCase()) >= 0){
            // PCWEB 인 경우
            $("#" + obj).prop("href", pcweb_url + link_val); 
            if(newWindow == "yes"){
                $("#" + obj).prop("target", "_blank");
            }
        }else{
			var browserInfo = navigator.userAgent;
            var isInIFrame = (window.location != window.parent.location);

            if(browserInfo.indexOf("LD_Android") > -1 || browserInfo.indexOf("LD_iOS") > -1){
                // 앱 링크
                $("#" + obj).prop("href", app_url + link_val);
            }else{
                // 모바일 웹 링크
                $("#" + obj).prop("href", moweb_url + link_val); 
                if(newWindow == "yes"){
                    $("#" + obj).prop("target", "_blank");
                }
            }
        }
    }
}
