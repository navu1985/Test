<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<link href="static/portalcss/login.css" rel="stylesheet">
<title><spring:message code="label.forgot.password"/></title>
</head>
<body>
	<!-- Container-->
	<div class="registration-container stacked">

		<!-- Content -->
		<div class="content clearfix">
				
				<!-- Passport logo -->
				<div align="center">
					<img src="static/images/datacertlogo.png" alt="Passport">
				</div>
				
				<!--  Regiration Panel-->
				<div class="panel panel-primary">
					<div class="panel-heading"><spring:message code="label.forgot.password"/></div>
				  	<div class="panel-body">
				 			<form name="forgetPasswordForm" class="form-horizontal" id="forgetPasswordForm" method="POST" action="<c:url value="/forgetpassword"/>">
							    <fieldset>
							    	<c:if test="${!empty error}">
				 						<div class="control-group" style="margin-bottom: 0px">
				 								<div class="controls alert alert-error" style="margin-bottom: 5px">
				 									${error}
				 								</div>
				 						</div>
									</c:if>
					                <div class="control-group">
					                    <label class="control-label"><spring:message code="label.enter"/> <spring:message code="label.email"/></label>
					                    <div class="controls">
					                        <input class="smalldevice-element focused" type="text" id="username" name="username" placeholder="username">
					                    </div>
					                </div>
					                <div style="/*  margin-left: 30%; padding-top: 10px; */ text-align: center;">
						  			<div>
						  				<a  href="<c:url value="/"/>" class="btn btn-custom"><spring:message code="label.back" /></a>
                                        <button  type="submit" class="btn btn-custom" id="forgotSubmit" ><spring:message  code="label.next"/> </button>
						  			</div>
							    </div>
					            </fieldset>
							</form>
				  </div>
				</div>
				<!-- /panel -->
		</div>
		<!-- /content -->

	</div>
	<!-- /Container-->
</body>

</html>




