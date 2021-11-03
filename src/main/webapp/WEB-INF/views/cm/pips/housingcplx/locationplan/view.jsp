<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    .file-upload-indicator,
    .kv-file-upload
    {
        display:none;
    }

</style>

<script type="text/javascript">
    $(document).ready(function() {

    })
    function tab_click(e,n){
        if(e == "summary"){
            $("#summarycd").val(n);
            $("#summary").submit();
        }
        if(e == "notice"){
            $("#noticecd").val(n);
            var type = "HOUSCPLX_INFO";
            $("#nptype").val(type);
            $("#notice").submit();
        }
        if(e == "floor"){
            $("#floorcd").val(n);
            var type = "FLOOR_PLAN";
            $("#fptype").val(type);
            $("#floor").submit();
        }
        if(e == "size"){
            $("#sizecd").val(n);
            $("#size").submit();
        }
        if(e == "mgto"){
            $("#mgtocd").val(n);
            $("#mgto").submit();
        }
        if(e == "cctv"){
            $("#cctvcd").val(n);
            $("#cctv").submit();
        }
        if(e == "facilities"){
            $("#facilitiescd").val(n);
            $("#facilities").submit();
        }
        if(e == "etc"){
            $("#etccd").val(n);
            $("#etc").submit();
        }
    }

    //목록 버튼
    function list_back(){
        location.href = "/cm/housingcplx/info/list";
    }

    //수정버튼
    function modify(e){
        $("#houscplxCd").val(e);
        $("#ptypeTpCd").val("PLOT_PLAN");
        $("#form_list").submit();
    }
</script>

<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">단지배치도</h2>
            <ul class="location">
                <li>단지 관리</li>
                <li>단지정보 관리</li>
                <li>단지배치도</li>
            </ul>
        </div>

        <div class="tab_wrap">
            <ul>
                <li onclick="tab_click('summary','<c:out value="${houscplxCd}"/>')"><a href="#">단지개요</a></li>
                <li onclick="tab_click('notice','<c:out value="${houscplxCd}"/>')"><a href="#">우리 단지 알림</a></li>
                <li class="on"><a href="#">단지배치도</a></li>
                <li onclick="tab_click('floor','<c:out value="${houscplxCd}"/>')"><a href="#">타입별 평면도</a></li>
                <li onclick="tab_click('size','<c:out value="${houscplxCd}"/>')"><a href="#">세대별 평형</a></li>
                <li onclick="tab_click('mgto','<c:out value="${houscplxCd}"/>')"><a href="#">관리실/경비실</a></li>
                <li onclick="tab_click('cctv','<c:out value="${houscplxCd}"/>')"><a href="#">CCTV</a></li>
                <li onclick="tab_click('facilities','<c:out value="${houscplxCd}"/>')"><a href="#">시설업체정보</a></li>
                <li onclick="tab_click('etc','<c:out value="${houscplxCd}"/>')"><a href="#">기타</a></li>
            </ul>
        </div>
        <c:choose>
            <c:when test="${not empty plotPlanList}">
                <div id="carousel" class="carousel slide" data-ride="carousel" >
                    <ol class="carousel-indicators">
                        <c:forEach items="${plotPlanList}" var="list" varStatus="status">
                            <c:choose>
                                <c:when test="${list.ordNo eq '0'}">
                                    <li data-target="#carousel" data-slide-to="<c:out value="${list.ordNo}"/>" class="active"></li>
                                </c:when>
                                <c:otherwise>
                                    <li data-target="#carousel" data-slide-to="<c:out value="${list.ordNo}"/>"></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ol>
                    <div class="carousel-inner">
                        <c:forEach items="${plotPlanList}" var="list" varStatus="status">
                            <c:choose>
                                <c:when test="${list.ordNo eq '0'}">
                                    <div class="carousel-item text-center active">
                                        <div class="img_wrap">
                                            <img class="d-inline-block" src="<c:out value="${list.reducePlnfigFileUrlCont}"/>" alt="First slide">
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="carousel-item text-center">
                                        <div class="img_wrap">
                                            <img class="d-inline-block" src="<c:out value="${list.reducePlnfigFileUrlCont}"/>" alt="First slide">
                                        </div>
                                    </div>
                                </c:otherwise>

                            </c:choose>
                        </c:forEach>
                    </div>
                    <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                <script type="text/javascript">
                $(function(){
                    $('#carousel').carousel({
                        interval: false
                    });
                });
                </script>
            </c:when>
            <c:otherwise>
                <h2 style="text-align:center;color:gray">단지배치도 이미지를 등록해주세요</h2>
            </c:otherwise>
        </c:choose>


        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:set var="userGroupName" value="${fn:escapeXml(session_user.userGroupName)}"/>
                <c:if test="${userGroupName eq 'SYSTEM_ADMIN' || userGroupName eq 'SUB_SYSTEM_ADMIN'}">
                    <button class="btn btn-brown" type="button" onclick="list_back();">목록</button>
                </c:if>
                <c:choose>
                    <c:when test="${not empty plotPlanList}">
                        <button class="btn btn-bluegreen" type="button" onclick="modify('<c:out value="${houscplxCd}"/>');">수정</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-bluegreen" data-toggle="modal" data-target="#modal4">등록</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>


<form:form id="summary" action="/cm/housingcplx/info/intro/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="summarycd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="notice" action="/cm/housingcplx/info/notice/view" name="housingCplxPtype" commandName="housingCplxPtype" method="post">
      <input type="text" id="noticecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="nptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="floor" action="/cm/housingcplx/info/floor/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="floorcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="fptype" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="size" action="/cm/housingcplx/info/householdPtype/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="sizecd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="mgto" action="/cm/housingcplx/info/address/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="mgtocd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="cctv" action="/cm/housingcplx/info/cctv/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="cctvcd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="facilities" action="/cm/housingcplx/info/facilityInfo/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="facilitiescd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>
<form:form id="etc" action="/cm/housingcplx/info/etc/view" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="etccd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
</form:form>


<form:form id="form_list" action="/cm/housingcplx/info/plot/edit" name="housingcplx" commandName="housingcplx" method="post">
      <input type="text" id="houscplxCd" name="houscplxCd" style="width:0;height:0;visibility:hidden"/>
      <input type="text" id="ptypeTpCd" name="ptypeTpCd" style="width:0;height:0;visibility:hidden"/>
</form:form>


<div class="modal" id="modal4" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered size_wide" role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">단지배치도 등록</h5>
                <button type="button" class="close" data-dismiss="modal"><img src="/images/btn_x.png" alt="" /></button>
            </div>
            <!-- //모달헤더 -->

				<!-- 모달바디 -->
				<div class="modal-body">
					<div class="container">
						<div class="row">
							<div class="col-lg-12 col-sm-12 col-11 main-section">
                                <div class="form-group">
                                    <div class="file-loading">
                                        <input id="file" type="file" name="file[]" multiple="multiple" class="file" data-overwrite-initial="false" data-min-file-count="1" accept=".png, .jpg, .jpeg, .gif">
                                    </div>
                                </div>
							</div>
						</div>
					</div>
					<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet">
					<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all" rel="stylesheet" type="text/css"/>
					<link rel="stylesheet" type="text/css" href="<c:url value="/styles/fileupload.css" />">
					<style type="text/css">
                        #ptype-nm, #ptype-dim-qty, #sup-dim-qty, #link-url {
                            display:none;
                        }
                        .modal-body {
                          padding: 1px;
                        }
                        .kv-file-zoom btn .btn-kv .btn-default .btn-outline-secondary{
                            display:none;
                        }
					</style>
					<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
					<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/bootstrap-fileinput/fileinput.js" />"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.4.7/themes/fa/theme.js" type="text/javascript"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" type="text/javascript"></script>
					<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" type="text/javascript"></script>
					<script type="text/javascript">
					    var houscplxCd = '<c:out value="${houscplxCd}"/>';

						$("#file").fileinput({
						    language: "kr",
						    uploadAsync: false,
							theme: 'fa',
							uploadUrl: "/cm/housingcplx/info/addHousingCplxImageInfoAction?houscplxCd=" + houscplxCd + "&ptypeTpCd=PLOT_PLAN",
							uploadExtraData: function() {
							    var fileName = document.getElementsByName("file[]")[0]["files"];
                                var fileNames;

                                for(i=0; i<fileName.length; i++) {
                                    if (i == 0) {
                                        fileNames = fileName[i]["name"];
                                    } else {
                                        fileNames += "/" + fileName[i]["name"];
                                    }
                                    console.log(fileNames);
                                }

                                return {
                                            _fileNames: fileNames
                                        };
							},

							allowedFileExtensions: ['jpg', 'png', 'gif'],
							overwriteInitial: false,
							maxFileSize:209715200,
							maxFilesNum: 10,
							multiple: true,
							multipart: true,
							slugCallback: function (filename) {
								return filename.replace('(', '_').replace(']', '_');
							},
							fileActionSettings:{
							    showZoom: false,
							}
						});

						$('#file').on('filebatchuploaderror', function(event, data, previewId, index) {
                            alert("배치도 이미지 업로드중 장애가 발생하였습니다.");
                            console.log(event);
                            console.log(data);
                            console.log(previewId);
                            console.log(index);
                            location.href = "/cm/housingcplx/info/plot/view?houscplxCd=" + houscplxCd + "&ptypeTpCd=PLOT_PLAN";
                        });

                        $('#file').on('filebatchuploadsuccess', function(event, data, previewId, index) {
                            alert("배치도 이미지 업로드가 완료되었습니다.");
                            console.log(event);
                            console.log(data);
                            console.log(previewId);
                            console.log(index);
                            location.href = "/cm/housingcplx/info/plot/view?houscplxCd=" + houscplxCd + "&ptypeTpCd=PLOT_PLAN";
                        });
					</script>
				</div>
				<!-- //모달바디 -->
			</div>
		</div>
	</div>
	<c:set var="houscplxCd" value="${houscplxCd}"/>

