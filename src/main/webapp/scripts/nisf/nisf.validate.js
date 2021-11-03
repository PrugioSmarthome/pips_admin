/**
 * 
 * validate.js
 * 
 * 
 */

/**
 * password validate 정의
 */
jQuery.validator.addMethod("password", function(value, element) {
	var rule =  /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	return this.optional(element) || rule.test(value);}, 
	jQuery.validator.format("Please enter a valid passwrod.")
);

/**
 * Email validate 정의
 */
jQuery.validator.addMethod("email", function(value, element) {
	var rule = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	return this.optional(element) || rule.test(value);}, 
	jQuery.validator.format("Please enter a valid email address.")
);

/**
 * Letters, numbers validate 정의
 */
jQuery.validator.addMethod("alphanumeric", function(value, element) {
	var rule = /^[a-zA-Z0-9_]+$/i;
	return this.optional(element) || rule.test(value);}, 
	jQuery.validator.format("Letters, numbers or underscores only please.")
);

/**
 * alphabet validate 정의
 */
jQuery.validator.addMethod("alphabet", function(value, element) {
	var rule = /^[a-zA-Z]+$/i;
	return this.optional(element) || rule.test(value);}, 
	jQuery.validator.format("Letters, only please.")
);

/**
 * 한글 validate 정의
 */
jQuery.validator.addMethod("hangle", function(value, element) {
	var rule = /^[\uAC00-\uD7A3]+$/i;
	return this.optional(element) || rule.test(value);}, 
	jQuery.validator.format("한글만 입력")
);

/**
 * string의 byte로 minimum length validate 정의
 */
jQuery.validator.addMethod("minbyte", function(value, element, param) {
	return this.optional(element) || CM_getStrByte(value) >= param;}, 
	jQuery.validator.format("Please enter at least {0} characters byte.")
);

/**
 * string의 byte로 maximum length validate 정의
 */
jQuery.validator.addMethod("maxbyte", function(value, element, param) {
	return this.optional(element) || CM_getStrByte(value) <= param;}, 
	jQuery.validator.format("Please enter no more than {0} byte.")
);

/**
 * string의 byte로 range length validate 정의
 */
jQuery.validator.addMethod("rangebyte", function(value, element, param) {
	return this.optional(element) || ( CM_getStrByte(value) >= param[0] && CM_getStrByte(value) <= param[1] );}, 
	jQuery.validator.format("Please enter a value between {0} and {1} byte long.")
);

/**
 * yyyymmdd format의 date validate 정의
 */
jQuery.validator.addMethod("yyyymmdd", function(value, element) {
	return this.optional(element) || (new dateUtil()).isValidte(value);}, 
	jQuery.validator.format("Date invalid.")
);

/**
 * checkEmailRule email의 정합성 확인
 * @param email : email
 */
function checkEmailRule(email){	
	var emailRule = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	return emailRule.test(email);
}

/**
 * checkPasswordRule 패스워드의 정합성 확인
 * @param password : password
 */
function checkPasswordRule(password){	
	var passwordRule =  /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;		
	return passwordRule.test(password);
}
