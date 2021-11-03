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
        var iconPickerOptions = {searchText: "Buscar...", labelHeader: "{0}/{1}"};
        var sortableListOptions = {
            placeholderCss: {'background-color': "#cccccc"}
        };

        var editor = new MenuEditor('myEditor', {listOptions: sortableListOptions, iconPicker: iconPickerOptions}, true);
        editor.setForm($('#frmEdit'));
        editor.setUpdateButton($('#btnUpdate'));

        $("#btnMenuUpdate").click(function(){
            var depth = $("ul.pl-0").has("ul.pl-0").length;

            if (depth > 0) {
                alert("3단계 메뉴구조로 수정할 수 없습니다.");
                return false;
            }

            var menuTargetType;
            var isAdmin = $('input:checkbox[id="menu_target_system"]').is(":checked");
            var isComplex = $('input:checkbox[id="menu_target_complex"]').is(":checked");
            var menuName = $("#menu_name").val();
            var viewPath = $("#view_path").val();
            var menuNo = $("#menu_no").val();
            var upMenuNo = $("#up_menu_no").val();

            if (menuName == "") {
                alert("메뉴명은 필수입니다.");
                return false;
            }

            if (isAdmin == true && isComplex == true) {
                menuTargetType = "ALL";
            } else if (isAdmin == true && isComplex == false) {
                menuTargetType = "SYSTEM";
            } else if (isAdmin == false && isComplex == true) {
                menuTargetType = "COMPLEX";
            } else if (isAdmin == false && isComplex == false) {
                menuTargetType = "NONE";
            }

            $("#menu_target_type").val(menuTargetType);

            var param = new Object();
            param.menuTargetType = menuTargetType;
            param.menuName = menuName;
            param.viewPath = viewPath;
            param.menuNo = menuNo;
            param.upMenuNo = upMenuNo;

            $.ajax({
                url: '/cm/system/menu/editMenuItemInfoAction',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    var data = data;

                    if (data.status == true) {
                        alert("메뉴정보가 수정되었습니다.");
                        getMenuItemList();
                    } else if (data.status == false) {
                        alert("메뉴정보 수정 중 실패하였습니다.");
                        return ;
                    }
                },
                error: function(e){
                    alert("메뉴정보 수정 중 실패하였습니다.");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });

            editor.update();
        });

        $('#btnMenuAdd').click(function(){
            var menuName = $("#menu_name").val();
            var viewPath = $("#view_path").val();
            var menuTargetType;
            var isAdmin = $('input:checkbox[id="menu_target_system"]').is(":checked");
            var isComplex = $('input:checkbox[id="menu_target_complex"]').is(":checked");

            if (menuName == "") {
                alert("메뉴명은 필수입니다.");
                return false;
            }

            if (isAdmin == true && isComplex == true) {
                menuTargetType = "ALL";
            } else if (isAdmin == true && isComplex == false) {
                menuTargetType = "SYSTEM";
            } else if (isAdmin == false && isComplex == true) {
                menuTargetType = "COMPLEX";
            } else if (isAdmin == false && isComplex == false) {
                menuTargetType = "NONE";
            }

            var param = new Object();
            param.menuTargetType = menuTargetType;
            param.menuName = menuName;
            param.viewPath = viewPath;

            $.ajax({
                url: '/cm/system/menu/addMenuItemAction',
                type: 'POST',
                data: param,
                dataType : "json",
                success: function(data){
                    var data = data;

                    if (data.status == true) {
                        alert("메뉴가 추가되었습니다.");
                        getMenuItemList();
                    } else if (data.status == false) {
                        alert("메뉴추가 중 실패하였습니다.");
                        return ;
                    }
                },
                error: function(e){
                    alert("메뉴추가 중 실패하였습니다.");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
        });

        $('#btnMenuSave').on('click', function () {
            var depth = $("ul.pl-0").has("ul.pl-0").length;

            if (depth > 0) {
                alert("3단계 메뉴구조로 저장할 수 없습니다.");
                return false;
            }

            if (confirm("메뉴 순서 정보를 변경하시겠습니까?")) {
                var str = editor.getString();

                var param = new Object();
                param.menuItemList = str;

                $.ajax({
                    url: '/cm/system/menu/editMenuItemOrderAction',
                    type: 'POST',
                    data: param,
                    dataType : "json",
                    success: function(data){
                        var data = data;

                        if (data.status == true) {
                            alert("메뉴목록이 수정되었습니다.");
                            getMenuItemList();
                        } else if (data.status == false) {
                            if (data.code == "EDIT_DATA_ERROR") {
                                alert("메뉴목록 수정 중 실패하였습니다.");
                            } else if (data.code == "EDIT_DATA_EMPTY") {
                                alert("수정할 메뉴목록 정보가 없습니다. 메뉴 불러오기 후 수정해주세요");
                            }
                            return false;
                        }
                    },
                    error: function(e){
                        alert("메뉴목록을 수정 중 실패하였습니다.");
                        console.log(e.responseText.trim());
                    },
                    complete: function() {
                    }
                });
            }
        });

        function getMenuItemList() {
            $.ajax({
                url: '/cm/system/menu/getMenuItemList',
                type: 'POST',
                dataType : "json",
                success: function(data){
                    editor.setData(data);

                    var removeButton = document.getElementsByClassName('btnRemove');

                    styleButtonAll(removeButton);
                },
                error: function(e){
                    alert("메뉴목록을 가져오는데 실패했습니다.");
                    console.log(e.responseText.trim());
                },
                complete: function() {
                }
            });
        }

        $('#btnMenuReload').on('click', function () {
            getMenuItemList();
        });

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

</script>

<div id="container">
    <div class="container">
        <div class="top_area">
            <h2 class="tit">메뉴 목록</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>메뉴 관리</li>
                <li>메뉴 목록</li>
            </ul>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="card custom mb-3">
                    <div class="card-header"><h5 class="float-left">관리자 메뉴</h5>
                        <div class="float-right">
                            <button id="btnMenuReload" type="button" class="btn btn-outline-secondary">
                                <i class="fa fa-play"></i>메뉴 불러오기</button>
                        </div>
                    </div>
                    <div class="card-body">
                        <ul id="myEditor" class="sortableLists list-group">
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card border-primary mb-3">
                    <div class="card-header bg-primary custom text-white">메뉴 수정</div>
                    <div class="card-body">
                        <form id="frmEdit" class="form-horizontal">
                            <div class="form-group">
                                <label for="menu_name">메뉴명</label>
                                <div class="input-group">
                                    <input style="display:none;" type="text" class="form-control item-menu" name="menu_no" id="menu_no" disabled>
                                    <input style="display:none;" type="text" class="form-control item-menu" name="up_menu_no" id="up_menu_no" disabled>
                                    <input style="display:none;" type="text" class="form-control item-menu" name="display_order" id="display_order" disabled>
                                    <input style="display:none;" type="text" class="form-control item-menu" name="text" id="text">
                                    <input type="text" class="form-control item-menu" name="menu_name" id="menu_name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="view_path">메뉴 URL</label>
                                <input style="display:none;" type="text" class="form-control item-menu" name="href" id="href">
                                <input type="text" class="form-control item-menu" id="view_path" name="view_path">
                            </div>
                            <div class="form-group">
                                <label for="menu_target">적용대상 (단지 관리자로 선택시 읽기 권한으로 적용됩니다.)</label>
                                <div class="form-group">
                                    <input type="text" style="display:none;" class="form-control item-menu" id="menu_target_type" name="menu_target_type">
                                    <div class="form-check form-check-inline">
                                      <input class="form-check-input" type="checkbox" id="menu_target_system" value="SYSTEM_ADMIN">
                                      <label class="form-check-label" for="menu_target_system">시스템 관리자</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                      <input class="form-check-input" type="checkbox" id="menu_target_complex" value="COMPLEX_ADMIN">
                                      <label class="form-check-label" for="menu_target_complex">단지 관리자</label>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer">
                        <button type="button" id="btnMenuAdd" class="btn btn-bluegreen"><i class="fas fa-plus"></i> 메뉴추가</button>
                        <button type="button" id="btnMenuUpdate" class="btn btn-bluegreen"><i class="fas fa-sync-alt"></i> 메뉴수정</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <button id="btnMenuSave" type="button" class="btn btn-bluegreen"><i class="fas fa-check-square"></i>저장</button>
            </div>
        </div>

    </div>
</div>
