<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<link href="static/portalcss/login.css" rel="stylesheet">
<title><spring:message code="label.registration"/></title>
</head>
<body>

	<!--  Regiration Container-->
	<div class="registration-container stacked">

		<!-- Content -->
		<div class="content clearfix">
				
				<!-- Passport logo -->
				<div align="center">
					<img src="static/images/datacertlogo.png" alt="Passport">
				</div>
				
				<!--  Regiration Panel-->
				<div class="panel panel-primary">
					<div class="panel-heading"><span class="bold">Account Registration Successfull</span></div>
				  	<div class="panel-body">
				  		
				  		<div class="alert alert-success">
				  			<h4><spring:message code="message.registrationSuccess"/></h4>
				  		</div>
				  		
				  			
				  		<div class="control-group">
								<div class="controls">
									<div><a  href="<c:url value="/"/>" class="button btn btn-custom"><spring:message code="label.back" /></a></div>
								</div>
						</div>
				 	
				  </div>
				</div>
				<!-- /panel -->
		</div>
		<!-- /content -->

	</div>
	<!-- /regirationContainer-->
</body>
</html>



		