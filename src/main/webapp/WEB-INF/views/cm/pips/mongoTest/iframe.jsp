<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<script type="text/javascript">
    $(document).ready(function(){

    });

</script>

<form:form id="form_search" action="/cm/system/user/list" name="detail" commandName="detail" method="post">
<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">iframe</h2>
        </div>
        <div class="search_area">
        </div>

        <iframe src="/cm/housingcplx/info/list" style="width:100%; height:800px"></iframe>


    </div>
</div>

</form:form>

<form:form id="form_detal" action="/cm/system/user/view" name="detail" commandName="detail" method="post">
    <input type="text" id="userId" name="userId" style="width:0;height:0;visibility:hidden"/>
</form:form>
