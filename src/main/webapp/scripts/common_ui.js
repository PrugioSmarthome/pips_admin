$(document).ready(function(){

	$('#nav .btn_mypage .btn_pop').click(function(e){
		e.preventDefault();
		$(this).closest('.btn_mypage').addClass('on')
	});
	$('#nav .btn_mypage .btn_close').click(function(e){
		e.preventDefault();
		$(this).closest('.btn_mypage').removeClass('on')
	});
	$('#nav .btn_mypage .btn-gray').click(function(e){
        e.preventDefault();
        $(this).closest('.btn_mypage').removeClass('on')
    });
});