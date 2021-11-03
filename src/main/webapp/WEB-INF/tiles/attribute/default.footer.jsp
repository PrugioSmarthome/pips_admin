<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<footer>
		<address>Copyright (c) nTels, All right reserved.</address>
	</footer>

	<style>
		.dim_black {
			height: 100%;
			width: 100%;
			position: fixed;
			left: 0;
			top: 0;
			z-index: 1 !important;
			background-color: black;
			filter: alpha(opacity = 75); /* internet explorer */
			-khtml-opacity: 0.75; /* khtml, old safari */
			-moz-opacity: 0.75; /* mozilla, netscape */
			opacity: 0.75; /* fx, safari, opera */
			visibility: hidden;
		}

		.dim_white {
			height: 100%;
			width: 100%;
			position: fixed;
			left: 0;
			top: 0;
			z-index: 1 !important;
			background-color: white;
			filter: alpha(opacity = 75); /* internet explorer */
			-khtml-opacity: 0.75; /* khtml, old safari */
			-moz-opacity: 0.75; /* mozilla, netscape */
			opacity: 0.75; /* fx, safari, opera */
			visibility: hidden;
		}

		.dialog_wrapper {
			width: 100%;
			top: 0px;
			left: 0px;
			position: absolute;
			z-index: 5;
			display: block;
			visibility: hidden;
		}

		.loading {
			font: 13px Arial;
			display: block;
			width: 50px;
			height: 50px;
			padding: 60px 0 0 0;
			margin: 290px auto 0 auto;
			background: url(/images/loading.gif) no-repeat
		}

		.dialog {
			width: 400px;
			height: 200px;
			margin: 290px auto 0 auto;
			padding: 40px;
			background-color: #fff;
			border: 1px solid #ccc;
			color: #333;
		}
	</style>

	<div id="dimBlack" class="dim_black"  title="Event">
	</div>

	<div id="dimWhite" class="dim_white"  title="Event">
	</div>

	<div id="loadZone" class="dialog_wrapper">
		<span class="loading">loading</span>
	</div>


	<div id="popupModalLayer" class="dialog_wrapper">
	    <div class="dialog">
	    	title : <span id="popupModalLayerTitle"></span><br>
			msg : <span id="popupModalLayerMsg"></span><br>
			<input type="button" id="btn_layer_confirm" onclick="popupModalLayer(false, '', '');" value="확인"/>
			<input type="button" id="btn_layer_cancel" onclick="popupModalLayer(false, '', '');" value="취소"/>
		</div>
	</div>