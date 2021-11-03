<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="/WEB-INF/tag/ntels.tld" prefix="ntels" %>

<script  type="text/javascript">


$(document).ready(function() {

	var selectMenuNo = "${selectedMenu.selectMenuNo}";
	var selectMenuItem=$("#"+selectMenuNo);

	selectMenuItem.addClass("active");

	selectMenuItem.parent().parent().css("display","block");
	selectMenuItem.parent().addClass("active");
	selectMenuItem.parent().parent().parent().addClass("active");

	selectMenuItem.parent().parent().parent().children("a").children("span").removeClass('open');
	selectMenuItem.parent().parent().parent().children("a").children("span").addClass('close');
	selectMenuItem.parent().css("display","block");


	selectMenuItem.parent().parent().css("display","block");
	selectMenuItem.parent().parent().parent().parent().css("display","block");


	selectMenuItem.parent().parent().parent().parent().parent().addClass("active");
	selectMenuItem.parent().parent().parent().parent().parent().children("a").children("span").removeClass('open');
	selectMenuItem.parent().parent().parent().parent().parent().children("a").children("span").addClass('close');


	if(selectMenuItem.parent().parent().parent().children("a").hasClass("plus")){
		selectMenuItem.parent().parent().parent().children("a").removeClass("plus");
		selectMenuItem.parent().parent().parent().children("a").addClass("minus");
		selectMenuItem.parent().parent().parent().children("ul").css("display","block");

	}

});



$("nav > ul.dep01 > li > a").live('click', function(event) {

	var a = $(this);
	/* 선택된 1번째 뎁스를 열고 닫을 때 적용됨 */
	if(a.parent("li").hasClass('active') ){
		if(a.parent("li").children("ol").css("display")=='block'){
			a.parent().children("ol").css("display","none");
			a.children("span").removeClass('close');
			a.children("span").addClass("open");
			return;
		}else{
			a.parent().children("ol").css("display","block");
		}
	}
	/* 일단 모든 1번째 뎁스 메뉴를 닫아버림 */
	if($("nav > ul.dep01 > li").children("ol").size()>0){
		$("nav > ul.dep01 > li > ol").css("display","none");
		$("nav > ul.dep01 > li > a span").removeClass('close');
		$("nav > ul.dep01 > li > a span").addClass("open");


		/* span 상태에 따라 하위 메뉴를 열고 닫음 */
		var span = a.children("span");

		if(span.hasClass("open")){
			a.parent().children("ol").css("display","block");
			span.removeClass('open');
			span.addClass("close");
		}else{

			a.parent().children("ol").css("display","none");
			span.removeClass('close');
			span.addClass("open");
		}
	}
});


$("nav ul.dep01 .dep02 li a").live('click', function(event) {
	/* 선택된 2번째 뎁스를 열고 닫을 때 적용됨 */
	var a = $(this);
	if(a.parent("li").children("ul").size()>0){
		if(a.hasClass("plus")){
			a.parent().children("ul").css("display","block");
			a.parent().children("a").removeClass('plus');
		}else{
			a.parent().children("ul").css("display","none");
			a.addClass("plus");
		}
	}
});
</script>

<nav>
	<ntels:menu topMenuNo="${selectedMenu.paramMenuNo}" selectMenuNo="${selectedMenu.selectMenuNo}" menuClass='${selectedMenu.menuClass}'/>
</nav>
