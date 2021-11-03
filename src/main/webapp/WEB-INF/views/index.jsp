<form name="frm"></form>
<script type="text/javascript">
	document.frm.action="<%=request.getContextPath()%>/cm/login";
	document.frm.method="post";
	document.frm.submit();
</script>