<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<link href="static/portalcss/login.css" rel="stylesheet">
<title>Passport Portal</title>
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
					<div class="panel-heading">ddddddChange Password</div>
				  	<div class="panel-body">
				  			
				  			<div id="errorMessage" style="padding-bottom: 3px;"></div>
				 			<form class="form-horizontal" id="changepasswordnew" action="changepassword" method="post">
				 				
				 				<div class="form-group">
									<label class="col-lg-2 control-label">
										<spring:message code="label.new"/> <spring:message code="label.password"/>
									</label>
									<div class="col-lg-10">
										<input type="password" id="password" name="password" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">
										<spring:message code="label.confirm"/> <spring:message code="label.password"/>
									</label>
									<div class="col-lg-10">
										<input type="password" id="confirmpassword"	name="confirmpassword" />
									</div>
								</div>
						  
						  		<div style=" margin-left: 30%; padding-top: 10px;">
						  			<div>
                                        <input type="hidden" name="userid" value="${userid}"/>
										<input type="hidden" name="urlIdentifier" value="${urlIdentifier}"/>
                                        <button  type="submit" class="btn btn-custom"><spring:message	code="label.submit"/></button>
                                        <a  href="<c:url value="/"/>" class="btn btn-custom">Cancel</a>
						  			</div>
							    </div>
							</form>
				  </div>
				</div>
				<!-- /panel -->
		</div>
		<!-- /content -->

	</div>
	<!-- /Container-->
</body>
<script type="text/javascript" src="<c:url value="/static/portaljs/changepassword.js"/>"></script>
</html>





