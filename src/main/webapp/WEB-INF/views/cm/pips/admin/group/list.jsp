<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>시스템 관리자 회원정보관리</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/dataTables.bootstrap.min.css" type="text/css" />
<script type="text/javascript" charset="utf-8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" charset="utf-8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf-8" src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap.min.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
    $(document).ready(function() {
        $('#user_table').DataTable();
    } );
</script>
<body>
<table id="user_table" class="table table-striped table-bordered" style="width:75%">
    <thead>
        <tr>
            <th>단지명</th>
            <th>동</th>
            <th>호</th>
            <th>아이디</th>
            <th>이름</th>
            <th>회원구분</th>
            <th>로그인 허용</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${userList}" var="user" varStatus="status">
        <tr>
            <td>${user.houscplx_nm}</td>
            <td>${user.dong_no}</td>
            <td>${user.hose_no}</td>
            <td>${user.user_id}</td>
            <td>${user.user_nm}</td>
            <td>${user.fmly_tp_cd}</td>
            <td>${user.use_yn}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
