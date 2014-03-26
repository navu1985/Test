<%@ taglib uri="/WEB-INF/custom-tld/elfunctions.tld" prefix="dc"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div  class="panel panel-primary">
	<div class="panel-heading bold"><spring:message code='label.configuremessages'/></div>
	<div class="panel-body">
		<form id="editSetting" class="form-horizontal" action="" method="post">
			<div class="control-group">
				<label class="control-label">
					<spring:message code='label.nopolicymessage'/>
				</label>
				<div class="controls">
					<textarea style="margin-right:2px;" class="textarealength smalldevice-element" rows="4" id="noDataMessage" name="noDataMessage">${dc:formatStringForHtmlDisplay(settings.noDataMessage)}</textarea>
					&nbsp;
					<input type="hidden" id="id" name="id" value="${settings.id}"/>
					<input type="hidden" id="adminEmail" name="adminEmail" value="${settings.adminEmail}"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					<spring:message code='label.submitaninquiry'/>
				</label>
				<div class="controls">
					<input name="showSubmitAnInquiry" type="checkbox" <c:if test="${settings.showSubmitAnInquiry eq true}">checked="checked"</c:if>/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					<spring:message code='label.sidebarlinkreportanissue'/>
				</label>
				<div class="controls">
					<input name="showReportAnIssue" type="checkbox"  <c:if test="${settings.showReportAnIssue eq true}">checked="checked"</c:if>/>
				</div>
			</div>
		 	<div class="control-group">
				<div class="controls">
					<div style="margin-right: 160px; padding-top: 10px;"><a id="updateAdminSettings" class="btn btn-custom"><spring:message code="label.save"/></a></div>
				</div>
			</div>	
		
		</form>
</div>
</div>
<script type="text/javascript">
$("#editSetting").validate({
	rules : {
		noDataMessage:{
			required: true,
			maxlength:250
		}
	},
	messages : {
		noDataMessage : {
			required : "<spring:message code='message.notBlank'/>",
			maxlength: "<spring:message code='message.lengthCannotBe250'/>"
		}
	},
	errorClass : "alert-error validate-error",
	errorElement : "div",
	onkeyup : false,
	highlight : function(element, errorClass, validClass) {
		$(element).parents('.control-group');
	},
	unhighlight : function(element, errorClass, validClass) {
		$(element).parents('.control-group');
	}
});

$('#updateAdminSettings').click(function(){
	if($("#editSetting").valid()){
		$.post('admin/updateSetting', $("#editSetting").serialize()).success(function(data) {
				$('#submitInquiry').show();
				$('#reportanissue').show();
				app.navigate('#admin',true);
		});	
	}
});
</script>