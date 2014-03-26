<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="now" class="java.util.Date"/>
<sec:authentication var="user" property="principal" />
<div class="padding5">
  <div>
  	<div style="width: 110px; float: left; border-radius:4px;" >
  		<c:if test="${!empty userProfile}">
  			<img class="imageborder" style="height: 76px; width: 110px;"  width="110px" height="84px" src="<c:url value="profile/${userProfile.userProfileId}/image?_=${now}"/>" alt="">
  		</c:if>
  		<c:if test="${empty userProfile}">
  			<img width="110px" height="84px" style="border-radius:4px;" class="profilepicsmall" src="<c:url value="/static/portalimages/unknown.gif"/>" alt="">
  		</c:if>
  	</div>
  	<div class="mobile-desktop-profile-text">
  		<div >
  		&nbsp;
  		<span data-target="profile" href="javascript: void(0);">
		  		<c:if test="${!empty userProfile.firstName}">
		  			<b>${userProfile.firstName} ${userProfile.lastName}</b>
		  		</c:if>
		  		<c:if test="${empty userProfile.firstName}">
		  			<b>User Name</b>
		  		</c:if>
		</span>
		<br>&nbsp;
		<c:if test="${user.firstLogin}">
			<a data-target="profile" href="javascript: void(0);" style="color: gray; font-size: 10px;" class="profile-popover" data-placement="right" data-title="First time Here?" data-content="First time here? Take a minute to view and edit your profile.">
		    	View my profile page
			</a>
			<script>$.get('loginedOnce',{ "_": $.now() });</script>	
		</c:if>
		<c:if test="${!user.firstLogin}">
			<a data-target="profile" href="javascript: void(0);" style="color: gray; font-size: 10px;">View my profile page</a>
		</c:if>
		<div class="profilesmalltext">
		   <p>&nbsp;Policies/Procedures Acknowledged: ${ackPoliciesCount}/${unAckPoliciesCount+ackPoliciesCount+retiredPoliciesCount} <br/>
		   &nbsp;Surveys Completed: ${completedSurveysCount}/${totalSurveysCount}</p>
		 </div>
	</div>
  </div>
  	</div>
 </div>

 <script>
 	$('a[data-target="profile"]').click(function(){
		$.get('profile',{ "_": $.now() }).success(function(data) {
			if (data.indexOf("data-webApp-loginPage") != -1) {
				window.location = "login";
			} else {
				$("#content-view-modal").hide().html(data).fadeIn('fast');
			}
		});
	});
 </script>
 
 	