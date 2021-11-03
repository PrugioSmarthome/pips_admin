<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-1.11.2.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-ui-1.11.3.custom.min.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery-migrate-1.2.1.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.validate.1.13.1.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/additional-methods.1.13.1.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/jquery.form.js" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/jquery/calendar/jquery.ui.datepicker-ko.js"/>"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf.common.js?v=1" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.menupage.js?v=2" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.paging.js?v=3" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.passwd.js?v=1" />"></script>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.selectbox.js?v=1" />"></script>
<%-- <script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/nisf/nisf.validate.js?v=1" />"></script> --%>
<script type="text/javascript" charset="utf-8" src="<c:url value="/scripts/tinymce/tinymce.min.js"/>"></script>
<script type="text/javascript">
tinyMCE.init({
        // General options
        mode : "textareas",
        language : 'ko',
        plugins: [
                  "advlist autolink lists link image charmap print preview anchor",
                  "searchreplace visualblocks code fullscreen",
                  "insertdatetime media table contextmenu paste imagetools"
              ],
        toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",        
});
</script>