<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-menu-editor.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"/>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-iconpicker.min.css" />">
<style type="text/css">
    .card.custom {
        background-color: #f5f3f2;
    }

    .border-primary {
        border-color: #848c94!important;
    }

    .bg-primary.custom {
        background-color: #5b7684!important;
    }

    .sortableListsOpener.btn-success {
        color: #fff;
        background-color: #5b7684;
        border-color: #5b7684;
    }
</style>
<script type="text/javascript" src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>
<script type="text/javascript" src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-menu-editor.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/fontawesome5-3-1.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/bootstrap-iconpicker.min.js" />"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#userGroupId").change(function() {
            var selGroupId = $("#userGroupId").val();

            if (selGroupId == undefined || selGroupId == '' || selGroupId == 'NULL') {
                alert("사용자 그룹을 선택해주세요.");
                return;
            }

            getAuthMenuData(selGroupId);
        });

        var iconPickerOptions = {searchText: "Buscar...", labelHeader: "{0}/{1}"};
        var sortableListOptions = {
            placeholderCss: {'background-color': "#cccccc"}
        };

        var editor = new MenuEditor('myEditor', {listOptions: sortableListOptions, iconPicker: iconPickerOptions}, false);
        editor.setForm($('#frmEdit'));
        editor.setUpdateButton($('#btnUpdate'));

        $("#btnAuthUpdate").click(function(){
            var checkedAuthTypeValue;

            var isTypeAll = $("#auth_type_all").is(':checked');
            var isTypeRead = $("#auth_type_read").is(':checked');
            var isTypeDenied = $("#auth_type_denied").is(':checked');

            if (isTypeAll == true && isTypeRead == false && isTypeDenied == false) {
                checkedAuthTypeValue = "A";
            } else if (isTypeRead == true  && isTypeAll == false && isTypeDenied == false) {
                checkedAuthTypeValue = "R";
            } else if (isTypeDenied == true  && isTypeAll == false && isTypeRead == false) {
                checkedAuthTypeValue = "N";
            }

            $("#auth_type").val(checkedAuthTypeValue);

            editor.update();
        });

        $('#btnAuthSave').on('click', function () {
            var str = editor.getString();
            var selGroupId = $("#userGroupId").val();

            var param = new Object();
            param.authMenuList = str;
            param.groupId = selGroupId;

            $.ajax({
                url: '/cm/system/authorization/editAuthMenuAction',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    var data = data;

                    if (data.status == true) {
                        alert("권한목록이 수정되었습니다.");
                        getAuthMenuData(selGroupId);
                    } else if (data.status == false) {
                        alert("권한목록을 수정 중 실패하였습니다.");
                        return ;
                    }
                },
                error: function(e){
                    alert("권한목록을 수정 중 실패하였습니다.");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
        });

        function getAuthMenuData(groupId) {
            var param = new Object();
            param.userGroupId = groupId;

            $.ajax({
                url: '/cm/system/authorization/authMenuList',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    editor.setData(data);

                    var upButton = document.getElementsByClassName('btnUp');
                    var downButton = document.getElementsByClassName('btnDown');
                    var inButton = document.getElementsByClassName('btnIn');
                    var outButton = document.getElementsByClassName('btnOut');
                    var removeButton = document.getElementsByClassName('btnRemove');
                    var editButton = document.getElementsByClassName('btnEdit');

                    hiddenButtonAll(upButton);
                    hiddenButtonAll(downButton);
                    hiddenButtonAll(inButton);
                    hiddenButtonAll(outButton);
                    hiddenButtonAll(removeButton);

                    styleButtonAll(editButton);
                },
                error: function(e){
                    alert("권한목록을 가져오는데 실패했습니다.");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
        }

        function hiddenButtonAll(target) {
            for(i=0; i<target.length; i++) {
                target[i].style.display = 'none';
            }
        }

        function styleButtonAll(target) {
            for(i=0; i<target.length; i++) {
                target[i].style.marginRight = '20px';
                target[i].style.borderRadius = '2px';
            }
        }
    });

    function addUserGroup() {
        var checkedAuthTypeValue;

        var isAll = $("#auth_all").is(':checked');
        var isRead = $("#auth_read").is(':checked');
        var isDeny = $("#auth_denied").is(':checked');

        if (isAll == true && isRead == false && isDeny == false) {
            checkedAuthTypeValue = "A";
        } else if (isRead == true  && isAll == false && isDeny == false) {
            checkedAuthTypeValue = "R";
        } else if (isDeny == true  && isAll == false && isRead == false) {
            checkedAuthTypeValue = "N";
        }

        $("#userAuthType").val("N");
        $("#form_modal").submit();
    }

    function deleteUserGroup() {
        var selGroupId = $("#userGroupId").val();

        if (selGroupId == undefined || selGroupId == '' || selGroupId == 'NULL') {
            alert("사용자 그룹을 선택해주세요.");
            return;
        }

        if (confirm("해당 사용자 그룹에 관리자가 속해있을 경우 삭제가 되지 않을 수 있습니다. 삭제처리 하시겠습니까?")) {
            $("#delete_group").val(selGroupId);
            $("#form_del").submit();
        }
    }
</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">권한 그룹 정보</h2>
            <ul class="location">
                <li>사용자 관리</li>
                <li>권한 관리</li>
                <li>권한 그룹 정보</li>
            </ul>
        </div>

        <div class="tbl_top_area" style="margin-bottom:10px;text-align:right">
            <div class="right_area">
                <button class="btn btn-gray btn-sm" type="button" data-toggle="modal" data-target="#modal3">사용자그룹 추가</button>
                <button class="btn btn-gray btn-sm" type="button" onclick="deleteUserGroup();" >사용자그룹 삭제</button>
            </div>
        </div>

        <table class="table2" style="margin-bottom: 30px;">
            <colgroup>
                <col style="width:150px">
                <col>
            </colgroup>
            <tbody>
                <tr>
                    <th><h5>사용자 그룹</h5></th>
                    <td>
                        <select name="userGroupId" id="userGroupId" class="custom-select">
                            <option value="NULL">선택</option>
                            <c:forEach items="${userGroupList}" var="list" varStatus="status">
                                <option value="${list.userGroupId}">${list.description}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="tbl_top_area" style="margin-bottom:10px;text-align:right">
            <div class="right_area">
                <span style="color:red;">권한 수정 시 상위 메뉴가 전체면 하위 메뉴는 개별 수정이 가능하며</span><br>
                <span style="color:red;">상위 메뉴가 읽기면 하위 메뉴는 읽기, 접근 불가로 수정이 가능합니다.</span><br>
                <span style="color:red;">상위 메뉴가 접근 불가 일시 하위 메뉴도 동일하게 적용됩니다.</span>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="card custom mb-3">
                    <div class="card-body">
                        <ul id="myEditor" class="sortableLists list-group">
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card border-primary mb-3">
                    <div class="card-header bg-primary custom text-white">권한 수정</div>
                    <div class="card-body">
                        <form id="frmEdit" class="form-horizontal">
                            <div class="form-group">
                                <label for="menu_name">메뉴명</label>
                                <div class="input-group">
                                    <input style="display:none;" type="text" class="form-control item-menu" name="menu_no" id="menu_no" disabled>
                                    <input style="display:none;" type="text" class="form-control item-menu" name="display_order" id="display_order" disabled>
                                    <input type="text" class="form-control item-menu" name="menu_name" id="menu_name" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="auth_type">권한종류</label>
                                <input style="display:none;" type="text" class="form-control item-menu" id="auth_type" name="auth_type">
                                <div class="form-check">
                                  <input class="form-check-input item-menu" type="radio" name="auth_type_radio" id="auth_type_all" value="A">
                                  <label class="form-check-label" for="auth_type_all">
                                    <i class="fab fa-autoprefixer"></i>전체(읽기/등록/수정/삭제)
                                  </label>
                                </div>
                                <div class="form-check">
                                  <input class="form-check-input item-menu" type="radio" name="auth_type_radio" id="auth_type_read" value="R">
                                  <label class="form-check-label" for="auth_type_read">
                                    <i class="fab fa-readme"></i>읽기
                                  </label>
                                </div>
                                <div class="form-check">
                                  <input class="form-check-input item-menu" type="radio" name="auth_type_radio" id="auth_type_denied" value="N">
                                  <label class="form-check-label" for="auth_type_denied">
                                    접근불가
                                  </label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer">
                        <button type="button" id="btnAuthUpdate" class="btn btn-bluegreen"><i class="fas fa-sync-alt"></i>수정</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button id="btnAuthSave" type="button" class="btn btn-bluegreen"><i class="fas fa-check-square"></i>저장</button>
            </div>
        </div>

    </div>
</div>

<form:form id="form_modal" action="/cm/system/authorization/addUserGroupAction" name="userGroupInfo" commandName="userGroupInfo" method="post">
<div class="modal" id="modal3" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered " role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">사용자 그룹 등록</h5>
                <button type="button" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->
            <div style="margin-left: 20px;">
               <h6>그룹 등록시 단지정보 관리메뉴를 제외한 초기 모든 메뉴 권한은 접근불가 상태입니다. </h6>
            </div>

            <!-- 모달바디 -->
            <div class="modal-body">
                <div class="table_wrap2">
                    <table class="table2">
                        <colgroup>
                            <col style="width:150px"/>
                            <col />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>사용자 그룹</th>
                                <td>
                                    <select class="custom-select" id="userGroupName" name="userGroupName">
                                        <option value="SYSTEM_ADMIN" selected="selected">시스템관리자</option>
                                        <option value="COMPLEX_ADMIN">단지관리자</option>
                                        <option value="SUB_SYSTEM_ADMIN">서브관리자</option>
                                    </select>
                                </td>
                            </tr>
                            <tr style="display:none;">
                                <th>권한</th>
                                <td>
                                    <div class="col">
                                        <div class="custom-control custom-radio d-inline-block mr-3">
                                            <input type="radio" class="custom-control-input" value="A" id="auth_all" name="userAuthType">
                                            <label class="custom-control-label" for="auth_all">전체(읽기/등록/수정/삭제)</label>
                                        </div>
                                        <div class="custom-control custom-radio d-inline-block mr-3">
                                            <input type="radio" class="custom-control-input" value="R" id="auth_read" name="userAuthType">
                                            <label class="custom-control-label" for="auth_read">읽기</label>
                                        </div>
                                        <div class="custom-control custom-radio d-inline-block">
                                            <input type="radio" class="custom-control-input" value="N" id="auth_denied" name="userAuthType" checked>
                                            <label class="custom-control-label" for="auth_denied">접근불가(선택시 단지정보 관리메뉴는 접근이 가능합니다.)</label>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>사용자 그룹명</th>
                                <td><input type="text" class="form-control" id="userGroupDescription" name="userGroupDescription" maxlength="100"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tbl_btm_area2">
                    <div class="right_area">
                        <button class="btn btn-gray" type="button" data-dismiss="modal">취소</button>
                        <button class="btn btn-bluegreen" type="button" onclick="addUserGroup();">등록</button>
                    </div>
                </div>
            </div>
            <!-- //모달바디 -->
        </div>
    </div>
</div>
</form:form>

<form:form id="form_del" action="/cm/system/authorization/deleteUserGroupAction" name="userGroupInfo" commandName="userGroupInfo" method="post">
    <input type="text" id="delete_group" name="userGroupId" style="width:0;height:0;visibility:hidden"/>
</form:form>