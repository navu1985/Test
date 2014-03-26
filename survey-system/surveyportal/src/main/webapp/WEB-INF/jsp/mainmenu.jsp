<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<link href="static/portalcss/application.css" rel="stylesheet">
<title><spring:message code="label.title"/></title>
</head>
<body>

	<!-- Top Header   -->
	<div id="header" class="navbar navbar-static-top ">
		  <div class="navbar-inner">
		  
		  			<!-- Header Image    -->
		  			<a class="brand mobile-width-headerimage" href="#"><img src="static/images/datacertlogo.png" alt="Passport"></a>
			       
			       <!-- Responsive Button for Mobiles and Tablets-->
			        <button data-target=".nav-collapse" data-toggle="collapse" class="btn btn-inverse btn-navbar " type="button">
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			        </button>
			        
			        <!-- Right Menu-->
			        <div class="collapse nav-collapse">
			        	<ul class="nav navbar-nav navbar-right pull-right">
					        <li><a href="#resetpassword"><spring:message code="label.changePassword"/></a> </li>
			  				<li class="divider-vertical"></li>
					        <li><a href="j_spring_security_logout"><spring:message code="label.logout"/></a> </li>
					        <li class="divider-vertical"></li>
			        	</ul>
			       </div>
		 </div>
	</div>
	<!-- Top Header Ends-->
	
	<div class="container-fluid sidepanel">
	    <div class="row-fluid">
		    <!-- <div class="span3" style="min-width: 320px"> -->
		    <div id="leftpanel-parent" class="span3">
		    	<!--Sidebar content-->
		    	<ul id="leftpanel" class="nav nav-tabs nav-stacked">
					<li id="profile-view"></li>
					<li id="pendingitems" class="data-popover" data-title="<spring:message code="label.mypendingitems"/>"  data-content="<spring:message code='message.popover.myPendingItems'/>">  <a href="#"><i class="icon-large icon-inbox"></i>&nbsp; <spring:message code="label.mypendingitems"/><span id="pendingcount" class="badge pull-right"></span></a></li>
					<li id="acknowledgedpolicies" class="data-popover" data-title="<spring:message code="label.mypolicyprocedures"/>" data-content="<spring:message code='message.popover.policyProcedures'/>"><a href="#policies"><i class="icon-large icon-folder-open"></i>&nbsp; <spring:message code="label.mypolicyprocedures"/></a></li>
					<li id="submitInquiry"><a><i class="icon-large icon-question-sign"></i>&nbsp; <spring:message code="label.submitInquiry"/></a></li>
					<li id="reportanissue"><a><i class="icon-large icon-comment"></i>&nbsp; <spring:message code="label.reportanissue"/></a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li id="admin"><a href="#admin"><i class="icon-large icon-wrench"></i>&nbsp;  <spring:message code="label.admin"/></a></li>
					</sec:authorize>
					
				</ul>
		    	<!--Sidebar content-->
		    	
		    </div>
		    
		   <div class="span9">
		    	<div class="container well" >
					
					<!--  Surveys panel-->
					<div id="content-view"></div>		    	
					<!--  Surveys panel-->
					<div id="content-view-modal"></div>
					
				</div>
		   </div>
	    </div>
		
    </div>
    <!-- Footer Starts   -->
	<div id="footer" class="center profilesmalltext">
			Copyright &copy; Datacert 2014. All Rights Reserved.
	</div>
	<!-- Footer Ends   -->    

<%@ include file="/WEB-INF/jsp/javaScriptIncludes.jsp"%>		
<script type="text/javascript" src="static/portaljs/mainmenu.js"></script>
<script type="text/javascript" src="static/portaljs/application.js"/></script>
</body>
</html>




