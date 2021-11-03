<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.dataTables.min.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/jquery.dataTables.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/custom.css" />">
<style>
    .file-upload-indicator,
    .kv-file-upload,
    .kv-file-zoom
    {
        display:none;
    }
</style>
<script type="text/javascript">
    //목록 버튼
    function list_back(){
        location.href = "/cm/system/banner/list";
    }

    //수정버튼
    function modify(e){
        $("#form_list").submit();
    }
</script>
<div id="container">

    <div class="container">

        <div class="top_area">
            <h2 class="tit">배너 정보</h2>
            <ul class="location">
                <li>시스템 관리</li>
                <li>배너 관리</li>
                <li>배너 목록</li>
            </ul>
        </div>

        <c:choose>
            <c:when test="${not empty bannerList}">
                <div id="carousel" class="carousel slide" data-ride="carousel" >
                    <ol class="carousel-indicators">
                        <c:forEach items="${bannerList}" var="list" varStatus="status">
                            <c:choose>
                                <c:when test="${list.blltOrdNo eq '0'}">
                                    <li data-target="#carousel" data-slide-to="<c:out value="${list.blltOrdNo}"/>" class="active"></li>
                                </c:when>
                                <c:otherwise>
                                    <li data-target="#carousel" data-slide-to="<c:out value="${list.blltOrdNo}"/>"></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ol>
                    <div class="carousel-inner">
                    <c:forEach items="${bannerList}" var="list" varStatus="status">
                        <c:choose>
                            <c:when test="${list.blltOrdNo eq '0'}">
                                <div class="carousel-item text-center active">
                                    <div class="img_wrap">
                                        <img class="d-inline-block" src="<c:out value="${list.fileUrlCont}"/>" alt="First slide">
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="carousel-item text-center">
                                    <div class="img_wrap">
                                        <img class="d-inline-block" src="<c:out value="${list.fileUrlCont}"/>" alt="First slide">
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
                <h2 style="text-align:center;color:gray">등록된 배너 이미지가 없습니다. <br/>배너 이미지를 등록해주세요</h2>
            </c:otherwise>
        </c:choose>

        <div class="tbl_btm_area2">
            <div class="right_area">
                <c:choose>
                    <c:when test="${not empty bannerList}">
                        <button class="btn btn-bluegreen" type="button" onclick="modify();">수정</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-bluegreen" data-toggle="modal" data-target="#modal4">등록</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>

</div>

<form:form id="form_list" action="/cm/system/banner/edit" name="banner" commandName="banner" method="post">
</form:form>

<div class="modal" id="modal4" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered size_wide" role="document">
        <div class="modal-content">
            <!-- 모달헤더 -->
            <div class="modal-header">
                <h5 class="modal-title">배너 이미지 등록</h5>
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
                        #ptype-nm, #ptype-dim-qty, #sup-dim-qty {
                            display:none;
                        }

                        .krajee-default.file-preview-frame {
                            height: 280px;
                        }

                        .modal-body {
                          padding: 1px;
                        }
					</style>
					<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
					<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/bootstrap-fileinput/fileinput.js" />"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-fileinput/4.4.7/themes/fa/theme.js" type="text/javascript"></script>
					<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" type="text/javascript"></script>
					<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" type="text/javascript"></script>
					<script type="text/javascript">
						$("#file").fileinput({
						    language: "kr",
						    uploadAsync: false,
							theme: 'fa',
							uploadUrl: "/cm/system/banner/addSystemBannerImageInfoAction",
							uploadExtraData: function() {
							    var fileName = document.getElementsByName("file[]")[0]["files"];
							    var fileNames;

							    for(i=0; i<fileName.length; i++) {
                                    if (i == 0) {
                                        fileNames = fileName[i]["name"];
                                    } else {
                                        fileNames += "/" + fileName[i]["name"];
                                    }
                                    console.log(i);
							    }

                                var name = document.getElementsByName("linkUrl[]");
                                var linkUrlNames;

                                for(i=0; i<name.length; i++) {
                                    var linkUrlName;

                                    if (name[i].value != "") {
                                        linkUrlName = name[i].value;
                                    } else {
                                        linkUrlName = " ";
                                    }

                                    if (i == 0) {
                                        linkUrlNames = linkUrlName;
                                    } else {
                                        linkUrlNames += "," + linkUrlName;
                                    }
                                    console.log(i);
                                }

								return {
								    _fileNames: fileNames,
									_linkUrlNames: linkUrlNames,
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
							}
						});

						$('#file').on('filebatchuploaderror', function(event, data, previewId, index) {
                            alert("배너 이미지 업로드중 장애가 발생하였습니다.");
                            console.log(event);
                            console.log(data);
                            console.log(previewId);
                            console.log(index);
                            location.href = "/cm/system/banner/list";
                        });

                        $('#file').on('filebatchuploadsuccess', function(event, data, previewId, index) {
                            alert("배너 이미지 업로드가 완료되었습니다.");
                            console.log(event);
                            console.log(data);
                            console.log(previewId);
                            console.log(index);
                            location.href = "/cm/system/banner/list";
                        });
					</script>
				</div>
				<!-- //모달바디 -->
			</div>
		</div>
	</div>
