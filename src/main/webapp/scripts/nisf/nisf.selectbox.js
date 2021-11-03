/**
 * @fileoverview combobox 옵션 목록 추출
 * @version 0.1
 * @since 2011.10.01
 */

/**
 * select tag 의 옵션 목록
 *
 * @param target 목록적용 select id
 * @param command 목록 종류
 * @param option option항목 (A=all, S=select, N=null)
 */
function getOptionItem(tid,command,option){
	var param = 'command='+command;

	var target = document.getElementById(tid);
	var nlen = arguments.length;
	var i = 3;
	for(i; i < nlen; i++){
		var arg_id = arguments[i];
		param += "&"+arg_id+'='+document.getElementById(arg_id).value;
	}
	if('A'==option)$(target).html('<option value="all">전체</option>');
	if('S'==option)$(target).html('<option value="">선택</option>');
	if('N'==option)$(target).html('');

	if(''==command){
		return;
	}

	$.post("/cm/common/list", param, function(data) {
		//if(data.error!=null)alert(data.error);
		var array = data;
		var option;

		$.each(array,function(index,appObj) {
			if(command != "user") {
				option = $('<option value="'+appObj.ID+'">'+appObj.NAME+'</option>');
			} else {
				//option = $('<option value="'+appObj.ID+'">['+ appObj.ID + ":" + appObj.NAME+']</option>');
				option = $('<option value="'+appObj.ID+'">'+ appObj.ID + " / " + appObj.NAME+'</option>');
			}
			$(target).append(option);
		});
	});
}