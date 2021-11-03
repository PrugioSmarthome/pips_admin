/**
 * @fileoverview 공통 include js
 * @version 0.1
 * @since 2014.04.08
 */
/*document.write('<script type="text/javascript" src="/scripts/nisf/nisf.menupage.js?v=1"></script>');
document.write('<script type="text/javascript" src="/scripts/nisf/nisf.paging.js?v=1"></script>');
document.write('<script type="text/javascript" src="/scripts/nisf/nisf.passwd.js?v=1"></script>');
document.write('<script type="text/javascript" src="/scripts/nisf/nisf.selectbox.js?v=1"></script>');*/


var uiData = null;
var listData = null;

/**
 * chrome 의 console.log 출력에 대해 IE에서 대처하기 위함
 */
if (!('console' in window)) {
	var names = [ 'log', 'debug', 'info', 'warn', 'error', 'assert', 'dir',
			'dirxml', 'group', 'groupEnd', 'time', 'timeEnd', 'count', 'trace',
			'profile', 'profileEnd' ];
	window.console = {};
	for (var i = 0; i < names.length; ++i)
		window.console[names[i]] = function() {
		};
} else {
	/* if it exists but doesn't contain all the same methods....silly ie */
	var names = [ 'log', 'debug', 'info', 'warn', 'error', 'assert', 'dir',
			'dirxml', 'group', 'groupEnd', 'time', 'timeEnd', 'count', 'trace',
			'profile', 'profileEnd' ];
	for (var i = 0; i < names.length; ++i)
		if (!window.console[names[i]])
			window.console[names[i]] = function() {
			};
};


/**
 * str의 ID에 해당하는 DOM 객체를 Return
 *
 * @param str
 * @returns
 */
function getObj(str){
	return document.getElementById(str);
}

/**
 * getData UI Data 수집하여 json형태로 만드는 기능
 * @param tgtId : 데이타를 수집할 id(string)
 */
function getData(tgtId){

	var input = $('input');
	var textarea = $('textarea');
	var radio = $('input');
	var checkbox = $('input');
	var check = $('input');
	var objectName = '';
	var bufferString = '';

	$.each(input, function(index){

		bufferString = '';

		if( $(this).attr('id') != null && $(this).attr('type') != 'radio' && $(this).attr('type') != 'checkbox'){

			objectName = $(this).attr('id');

			if(typeof tgtId == 'string' && tgtId != '' && tgtId != objectName.replace('_Val', '')){
				return;
			}

			bufferString += 'uiData.';
			bufferString += objectName.replace('_Val', '');
			bufferString += ' = "';
			if($(this).val() != null){
				bufferString += $(this).val().replace(/\\/gi, "\\\\").replace(/\"/gi, "\\\"");
			}else{
				bufferString += $(this).val();
			}
			bufferString += '"';

//			console.log(bufferString);

			eval(bufferString);
		}
	});

	$.each(textarea, function(index){

		bufferString = '';

		if( $(this).attr('id') != null){

			objectName = $(this).attr('id');

			if(typeof tgtId == 'string' && tgtId != '' && tgtId != objectName){
				return;
			}

			bufferString += 'uiData.';
			bufferString += objectName;
			bufferString += ' = "';
			if($(this).val() != null){
				bufferString += $(this).val().replace(/\\/gi, "\\\\").replace(/\"/gi, "\\\"").replace(/\n/g, "\\n");
			}else{
				bufferString += $(this).val();
			}
			//bufferString += $(this).val().replace(/\n/g, "\\n");
			bufferString += '"';

			try{
				eval(bufferString);
			}catch(e){
				//ignore (ie 8 version smart editor 처리 버그)
			}
		}
	});

	$.each(radio, function(index){
		bufferString = '';
		if( $(this).attr('name') != null && $(this).attr('type') == 'radio'){
			objectName = $(this).attr('name');

			if(typeof tgtId == 'string' && tgtId != '' && tgtId != objectName){
				return;
			}

			bufferString += 'uiData.';
			bufferString += objectName;
			bufferString += ' = "';
			bufferString += $(this).val();
			bufferString += '"';

			eval(bufferString);
		}else if( $(this).attr('id') != null && $(this).attr('type') == 'radio'){
			objectName = $(this).attr('id');

			if(typeof tgtId == 'string' && tgtId != '' && tgtId != objectName){
				return;
			}

			bufferString += 'uiData.';
			bufferString += objectName;
			bufferString += ' = "';
			bufferString += $(this).val();
			bufferString += '"';

			eval(bufferString);
		}

	});

	$.each(checkbox, function(key, value){
		if( $(this).attr('id') != null && $(this).attr('type') == 'checkbox'){
			objectName = $(this).attr('id');
			if(typeof tgtId == 'string' && tgtId != '' && tgtId != objectName){
				return;
			}
			eval('uiData.' + objectName + '=[]');
		}
	});

	$.each(check, function(index){
		bufferString = '';
		if( $(this).attr('id') != null && $(this).attr('type') == 'checkbox'){
			objectName = $(this).attr('id');

			if(typeof tgtId == 'string' && tgtId != '' && tgtId != objectName){
				return;
			}

			bufferString += 'uiData.';
			bufferString += objectName;
			bufferString += ' = ["';

			$.each($('[id=\'' + objectName + '\']:checked'), function(idx){
				if(idx > 0){
					bufferString += '","';
				}
				bufferString += $(this).val();
			});
			bufferString += '"]';

			//console.log(bufferString);
			eval(bufferString);
		}
	});
//	console.log(uiData);
}

/**
 * setData Json형태의 데이타를 UI에 setting 한다.
 * @param jsonData 화면에 setting할 json data
 */
function setData(jsonData){

	// UI 데이터 동기화
	uiData = jsonData;

//	console.log(uiData);

	var input = $('input');
	var textarea = $('textarea');
	var select = $('.selectlist');
	var radio = $('.ip_radio');
	var text = $('span');
	var check = $('.ip_chk');

	var objectName = '';
	var bufferString = '';

	$.each(input, function(key, value){

		if( $(this).attr('id') != null && $(this).attr('type') != 'radio' && $(this).attr('type') != 'checkbox' ){

			objectName = $(this).attr('id');

			if($('[id=\''+objectName +'\']').length == 1){
				bufferString = eval('uiData.' + objectName);

				if( typeof bufferString != 'undefined' && bufferString != null){
					$(this).val(bufferString);
				}
			}
			/* 다중처리는 미개발
 			else{
				for(var i = 0; i < len; i++){
					bufferString = eval('uiData.' + objectName);
					console.log('bufferString:' + bufferString);

					if( typeof bufferString != 'undefined' && bufferString != null){
						$('[id=\''+objectName +'\']:eq('+i+')').val('aaaaaa');
					}
				}
			}*/
		}
	});

	$.each(textarea, function(key, value){

		if( $(this).attr('id') != null ){

			objectName = $(this).attr('id');
			if($('[id=\''+objectName +'\']').length == 1){
				bufferString = eval('uiData.' + objectName);

				if( typeof bufferString != 'undefined' && bufferString != null){
					$(this).val(bufferString);
				}
			}
		}
	});

	$.each(select, function(key, value){

		if( $(this).attr('id') != null ){

			objectName = $(this).attr('id');

			if($('[id=\''+objectName +'\']').length == 1){
				bufferString = eval('uiData.' + objectName);

				if( typeof bufferString != 'undefined' && bufferString != null ){
					$(this).find('.bt .val').text($(this).find('#' +bufferString+ ' a').text());
					$(this).find('.ip_selhide').val(bufferString);
				}
			}
		}
	});

	$.each(radio, function(index){

		if( $(this).attr('name') != null && $(this).attr('type') == 'radio'){
			objectName = $(this).attr('name');
			bufferString = eval('uiData.' + objectName);

			if( typeof bufferString != 'undefined' && bufferString != null ){

				if( $(this).val() == bufferString ){
					$('input[name=\'' + objectName +'\']:input[value=' +bufferString+ ']').prop('checked', true);
				}
			}
		}else if( $(this).attr('id') != null && $(this).attr('type') == 'radio'){
			objectName = $(this).attr('id');
			//console.log(objectName);
			bufferString = eval('uiData.' + objectName);

			if( typeof bufferString != 'undefined' && bufferString != null ){

				if( $(this).val() == bufferString ){
					$('[id=\'' + objectName + '\']:input[value=' +bufferString+ ']').prop('checked', true);
					//$('input[name=' + objectName +']:input[value=' +bufferString+ ']').attr('checked', true);
				}
			}
		}
	});

	$.each(check, function(key, value){

		if( $(this).attr('id') != null && $(this).attr('type') == 'checkbox'){

			objectName = $(this).attr('id');
			bufferString = eval('uiData.' + objectName);

			if( typeof bufferString != 'undefined' && bufferString != null){
				if( typeof bufferString == 'string'){
					if( $(this).val() == bufferString ){
						$('[id=\'' + objectName + '\']:input[value=' +bufferString+ ']').prop('checked', true);
					}
				}else{
				    for( var k = 0; k < bufferString.length; ++k ) {
						if( $(this).val() == bufferString[k] ){
							$('[id=\'' + objectName + '\']:input[value=' +bufferString[k]+ ']').prop('checked', true);
						}
				    }
				}

			}
		}
	});

	$.each(text, function(index, object){

		if(typeof $(this).attr('id') != 'undefined'){

			objectName = $(this).attr('id');
			bufferString = eval('uiData.' + objectName);

			if( typeof bufferString != 'undefined' && bufferString != null ){
//				console.log(objectName);
				$(this).text(bufferString);
			}
		}

	});
}

/**
 * 로딩 이미지 view
 *
 * @param isView view여부
 */
function viewLoading(isView){
	if(isView == true){
		$("#dimWhite").css('visibility', 'visible');
		$("#loadZone").css('visibility', 'visible');
	}else{
		$("#dimWhite").css('visibility', 'hidden');
		$("#loadZone").css('visibility', 'hidden');
	}
}

/**
 * layer 모달 팝업 출력
 *
 * @param msg
 * @param isView
 */
function popupModalLayer(isView, title, msg){
	$("#popupModalLayerTitle").html(title);
	$("#popupModalLayerMsg").html(msg);

	if(isView == true){
		$("#dimBlack").css('visibility', 'visible');
		$("#popupModalLayer").css('visibility', 'visible');
	}else{
		$("#dimBlack").css('visibility', 'hidden');
		$("#popupModalLayer").css('visibility', 'hidden');
		$("#popupModalLayerTitle").html('');
		$("#popupModalLayerMsg").html('');
	}
}

/**
 * popup windows
 *
 * @param url
 * @param width
 * @param height
 */
function popupWindow(url, width, height, data, method){
	window.open('','popWin','scrollbars=yes,width='+width+',height='+height);

    // url과 data를 입력받음
    if( url && data ){
        // data 는  string 또는 array/object 를 파라미터로 받는다.
        data = typeof data == 'string' ? data : $.param(data);

        // 파라미터를 form의  input으로 만든다.
        var inputs = '';
        $.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request를 보낸다.
        $('<form action="'+ url +'" target="popWin" method="'+ (method||'post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
    };
}