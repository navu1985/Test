<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="now" class="java.util.Date"/>
<sec:authentication var="user" property="principal" />

<div  class="modal hide fade in" data-backdrop="true" style="background: none repeat scroll 0 0 black;">
			 <div class="modal-profileheader">
			    <button type="button" class="close" style="color: white;" data-dismiss="modal">&times;</button>
			 </div>
			<div class="profile-modal-body">
				<div class="center">
					<c:if test="${!empty errorMessage}">
						 <div class="alert alert-error-profile">${errorMessage}</div>
					</c:if>
					<div id="error"></div>
					<div>
						<div class="pofile-page">	
							<c:if test="${empty errorMessage}">
								<c:if test="${editmode ne true}">
									<i class="profile-icon">
										<a class="wait button" id="changeProfilePic"><img src="static/images/editPencil.png"></a>
									</i>
								</c:if>
							</c:if>
							<c:if test="${!empty userProfile}">
					  			<img width="220" height="175" style="height: 175px; width: 220px;" src="<c:url value="profile/${userProfile.userProfileId}/image?_=${now}"/>" alt="">
					  		</c:if>
					  		<c:if test="${empty userProfile}">
					  			<img width="220" height="175" style="height: 175px; width: 220px;" src="<c:url value="/static/portalimages/unknown.gif"/>" alt="">
					  		</c:if>
						</div>
					</div>
					<div style="font-size: 24px; color: white">${userProfile.firstName} ${userProfile.lastName}</div>
				</div>
				<div class="center" style="color: white;">
					<div><sec:authentication property="principal.username"/></div>
					<div>&nbsp;</div>
					<div>Policies/Procedures Acknowledged: ${ackPoliciesCount}/${unAckPoliciesCount+ackPoliciesCount}</div>
					<div>Surveys Completed: ${completedSurveysCount}/${totalSurveysCount}</div>
				</div>
				
				<form id="myForm" action="profile/edit/image" method="post" enctype="multipart/form-data" style="<c:if test="${empty errorMessage}"><c:if test="${editmode ne true}">display: none;</c:if> </c:if> ">
					
					<div style="display:inline-block;">
						<div style="height: 31px;width: 90px;overflow: hidden;position: relative;cursor: pointer;float: left; background-color: #E6E6E6;  background: url('static/portalimages/portal_upload.png') no-repeat scroll #E6E6E6 0; background-clip: padding-box;-moz-background-clip: padding;  -webkit-background-clip: padding; border-radius:4px !important; margin-bottom: 3px;">
							<input id="profileImage" style="cursor: pointer;height: 100%; position:absolute;top: 0;right: 0;z-index: 99;font-size:50px;opacity: 0;-moz-opacity: 0; filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0)" name="profileImage" type="file" required="required">
						</div>
						<div style="display:inline-block;">
							<div class="inline-block"><span style="color: white;" id="fileName"></span></div>
							<div style="display:inline-block;"><input style="margin-left: 4px; height: 31px;" class="btn btn-custom" name="profileImageSubmit"  id="profileImageSubmit" value="Upload Image" type="button"></div>
							<input id="isDevice" name="isDevice" type="hidden" value="false">
						</div>
					</div>
					
				</form>
			</div>
<script>
$(function(){
	$(".modal").modal('hide');
	$(".modal").modal();
	var options = { 
		    success: function(response){
		    	$(".modal").modal('hide');
		    	$("#content-view-modal").hide().html(response).fadeIn('fast');
		    	profilepage();
		    },
			complete: function(response){},
			error: function(){}
	};
	
	$("#changeProfilePic").click(function(){
		$(this).parent().hide();
		$("#myForm").show();
	});
	
	$("#profileImage").change(function(){
		$("#error").removeClass("alert alert-error-profile").empty();
		var fileName = $(this).val().match(/[^\/\\]+$/);
		$("#fileName").text(fileName);
	});
	
	$("#profileImageSubmit").click(function(){
		var file =$("#profileImage").val();
		if(file!=""){
			//alert(navigator.userAgent);
			/* to remove option of croping in Mobile/Andriod/Iphone    */
			if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
				 $("#isDevice").val("true");
			}
			$("#myForm").ajaxSubmit(options);	
		}else{
			$(".alert").hide();
			$("#error").html("Please Select File ").fadeIn('fast');
			$("#error").addClass("alert alert-error-profile").fadeIn('fast');
		}
	});
});
</script>
</div>
