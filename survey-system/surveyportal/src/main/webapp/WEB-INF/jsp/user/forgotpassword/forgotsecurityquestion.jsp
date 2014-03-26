<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<link href="<c:url value="/static/portalcss/login.css"/>" rel="stylesheet">
<title>Forgot Password</title>
</head>
<body>
	<!-- Container-->
	<div class="registration-container stacked">

		<!-- Content -->
		<div class="content clearfix">
				
				<!-- Passport logo -->
				<div align="center">
					<img src="<c:url value="/static/images/datacertlogo.png"/>" alt="Passport">
				</div>
				
				<!--  Regiration Panel-->
				<div class="panel panel-primary">
					<div class="panel-heading">Forgot Password</div>
				  	<div class="panel-body">
				  	
				 			<form name="forgetsecurityquestion"  id="forgetsecurityquestion" action="<c:url value="/forgetpassword/sendPassword"/>" method="POST">
									<%if(request.getParameter("message")!=null){%>
										<div style="display: inline-block;">
											<span class="alert-error validate-error"><%out.println(request.getParameter("message") );%></span><br/>
										</div>
										
									<% }%>
									<%if(request.getParameter("emailmessage")!=null){%>
										<div><h3><%out.println(request.getParameter("emailmessage"));%></h3></div>
									<% }%>
								
								<input type="hidden" name="userid" value="${fn:escapeXml(userid)}"><br/> 
								<c:if test="${!empty qlist }">
									<br>
									<c:forEach var="listItems" items="${qlist}" varStatus="status">
										<div class="control-group">
											<label class="control-label">
												<span>${status.index+1}.<c:out value="${listItems.value}"/></span>
											</label>
											<div class="controls">
												<input id="question${status.index+1}" name="question${status.index+1}" type="hidden" value="${listItems.key}" />
												<input class="controls-element smalldevice-element" id="answer${status.index+1}" name="answer${status.index+1}" type="text"  required="required" />
											</div>
										</div>
									</c:forEach>
									
									<div class="left control-group">
											<button style="float: none;" name="submit" class="button btn btn-warning" type="submit"><spring:message code="label.submit"/></button>
											<a style="padding: 5px; float: none;" href="<c:url value="/"/>" class="button btn btn-warning"><spring:message code="label.backtologin" /></a>
									</div>		 
									 
								</c:if>
							</form>
				  </div>
				</div>
				<!-- /panel -->
		</div>
		<!-- /content -->

	</div>
	<!-- /Container-->
</body>
<script type="text/javascript" src="<c:url value="/static/portaljs/forgotpassword.js"/>"></script>
<script type="text/javascript">
$(function(){
	  var messageBundle = {
			  requiredAnswer:"<spring:message code='message.requiredAnswer'/>"
	  };
	  new forgetSecurityQuestions(jQuery,messageBundle);
});
</script>
</html>