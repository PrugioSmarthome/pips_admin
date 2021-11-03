/**
 * @fileoverview 비밀번호 변경 화면에서 사용
 * @version 0.1
 * @since 2011.10.01  
 */


/**
 * 비밀번호 유효성 체크
 * 
 * @param pwd
 * @returns {Boolean}
 */
function isValidPassword(pwd){
	ERR_MSG = "";
	
    var alpaBig= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var alpaSmall= "abcdefghijklmnopqrstuvwxyz";
    var num = "01234567890";

    // 비밀번호는 8자리이상
    if(pwd.length < 10){
        ERR_MSG = "비밀번호는 반드시 10자 이상 입력해야 합니다.";
        return false;
    }
    if(isNum(pwd)){
        ERR_MSG = "비밀번호는 반드시 알파벳을 하나 이상 포함해야 합니다.";
        return false;
    }
    
    if(isUpper(pwd)){
        ERR_MSG = "비밀번호는 알파벳 대문자와 하나 이상의 숫자, 소문자를 포함해야 합니다.";
        return false;
    }
    
    if(isLower(pwd)){
        ERR_MSG = "비밀번호는 알파벳 소문자와 하나 이상의 숫자, 대문자를 포함해야 합니다.";
        return false;
    }    
    
    for(var i=0;i < alpaBig.length - pwd.length+1;i++){
        if(alpaBig.substring(i,i+pwd.length) == pwd)
        {
            ERR_MSG = "ABCDEF처럼 연속된 문자는 사용할 수 가 없습니다.";
            return false;
        }
    }
    if (pwd.indexOf(' ') > -1) {
        ERR_MSG = "공백은 입력할 수 없습니다.";
        return false;
    }
    for(i=0;i < alpaSmall.length - pwd.length+1;i++){
        if(alpaSmall.substring(i,i+pwd.length) == pwd)
        {
            ERR_MSG = "abcdef처럼 연속된 문자는 사용할 수 가 없습니다.";
            return false;
        }
    }
    for(i=1;i < pwd.length;i++){
        if(pwd.substring(0,1) != pwd.substring(i,i+1) )
            return true;
    }
    ERR_MSG = "비밀번호는 같은 문자만 연속해서 입력할 수 없습니다";
    return false;
}
 
/**
 * 숫자만 사용했는지 확인
 * 
 * @param str
 * @returns {Boolean}
 */ 
function isNum(str){
    for(var idx=0;idx < str.length;idx++){
        if(str.charAt(idx) < '0' || str.charAt(idx) > '9'){
            return false;
        }
    }
    return true;
}

/**
 * 대문자만으로 사용했는지 체크
 * 
 * @param str
 * @returns {Boolean}
 */
function isUpper(str){
    for(var idx=0;idx < str.length;idx++){
        if(str.charAt(idx) < 'A' || str.charAt(idx) > 'Z'){
            return false;
        }
    }
    return true;
}

/**
 * 소문자만으로 사용했는지 체크
 * 
 * @param str
 * @returns {Boolean}
 */
function isLower(str){
    for(var idx=0;idx < str.length;idx++){
        if(str.charAt(idx) < 'a' || str.charAt(idx) > 'z'){
            return false;
        }
    }
    return true;
}