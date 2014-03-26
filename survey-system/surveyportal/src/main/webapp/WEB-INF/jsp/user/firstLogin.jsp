<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="modal hide fade">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3>First time here?</h3>
  </div>
  <div class="modal-body">
    <p>First time here? Take a minute to view and edit your profile.</p>
  </div>
  <div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-custom">Close</a>
  </div>
</div>
<script>
$(function(){
	$(".modal").modal();
	$.get('loginedOnce',{ "_": $.now() });
});
</script>
