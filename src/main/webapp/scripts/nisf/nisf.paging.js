/**
 * @fileoverview ui 화면의 paging 번호에서 이동 처리
 * @author
 * @version 0.1
 * @since 2011.10.01
 */

/**
 * 페이지 이동  (get 방식)
 *
 * @param target	결과가 출력될 div id
 * @param ur	호출 url
 * @param page	이동 페이지
 * @param perPage	페이지당 출력 article 수
 * @param perSize	페이지 이동 버튼 갯수
 */
function goPage(target,url,page,perPage,perSize){
	var param = '';
	param+="page="+page;
	if(perPage!=null)param+="&perPage="+perPage;
	if(perSize!=null)param+="&perSize="+perSize;

	var nlen = arguments.length;
	var i = 5;
	for(i; i < nlen; i++){
		var arg_id = arguments[i].id;
		var temp_char = document.getElementById(arg_id)+'';
		param += "&"+arg_id+'='+document.getElementById(arg_id).value;
		//param += "&"+arguments[i].id+'='+arguments[i].value;
	}
	$.get(url, param, function(data) {
		$(target).html(data);
	});
}

/**
 * 페이지 이동 (port 방식)
 *
 * @param target	결과가 출력될 div id
 * @param ur	호출 url
 * @param page	이동 페이지
 * @param perPage	페이지당 출력 article 수
 * @param perSize	페이지 이동 버튼 갯수
 */
function goPostPage(target,url,page,perPage,perSize){
	var param = '';
	param+="page="+page;
	if(perPage!=null)param+="&perPage="+perPage;
	if(perSize!=null)param+="&perSize="+perSize;

	var nlen = arguments.length;
	var i = 0;
	if(nlen > 5){
		var params = arguments[5].split(",");

		for(i; i < params.length; i++){
			param += "&"+params[i]+'='+document.getElementById(params[i]).value;
		}
	}

	console.log(param);
	
	$.post(url, param, function(data) {
		$(target).html(data);
	});
}
