<%@page contentType="application/json;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form id="frm" name="frm" action="http://localhost:8999/v1/redirect/test" method="post">
    <input type="hidden" id="flashKey" name="flashKey" value="${flashKey}" />
</form>

<script type="text/javascript">

$(document).ready(function(){

	$("#frm").submit();

});

</script>


<div id="container">

</div>
