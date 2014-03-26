<!DOCTYPE html>
<html lang="en">
<meta name="data-webApp-loginPage">
<head>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<link href="static/portalcss/login.css" rel="stylesheet">

<title><spring:message code="label.loginHeader"/></title>
</head>
<body>
	<div class="account-container stacked">

		<div class="content clearfix">
			
			<form action="<c:url value="/j_spring_security_check"/>" method="post">
		
				<div align="center"><img src="static/images/datacertlogo.png" alt="Passport" ></div>
				<div class="login-fields">
					
					<%if (request.getParameter("error") != null) {%>
						<div  class="alert alert-error" style="line-height: 25px"><spring:message	code="label.login.error"/></div>
					<%}%>
					
					<%if (request.getParameter("disabled") != null) {%>
						<div  class="alert alert-error" style="line-height: 25px"><spring:message	code="label.account.locked"/></div>
					<%}%>

					<div class="field">
						<spring:message	code="label.signin" />
					</div>
					
					<div class="field">
						<label for="username"><spring:message	code="label.login.username" />:</label>
						<input type="text"	id="username" name="j_username" value="" placeholder="<spring:message	code="label.login.username" />"	class="username-field">
					</div>
					
					<!-- /field -->

					<div class="field">
						<label for="password"><spring:message	code="label.password" />:</label> <input type="password"
							id="password" name="j_password" value="" placeholder="<spring:message	code="label.password" />"
							class="password-field">
					</div>
					
					<!-- /password -->

				</div>
				<!-- /login-fields -->

				<div class="login-actions">

					<%-- <span class="login-checkbox"> <input id="Field" name="Field"
						type="checkbox" class="field login-checkbox" value="First Choice"
						tabindex="4"> <label class="choice" for="Field"><spring:message code="label.keep"/></label>
					</span> --%>
					<input class="button btn btn-large btn-custom" type="submit" value="Sign In"/>
					
				</div>
				<div><p class="smalltext"><spring:message code="label.donot.have.account"/><a href="<c:url value="/signup"/>"> <spring:message	code="label.signup"/></a></p></div>
				<div><p class="smalltext"><spring:message code="label.forgot.password"/> <a href="<c:url value="/forgetpassword"/>"><spring:message code="label.resetithere"/></a></p></div>
				<div><p class="smalltext"><spring:message code="label.need.help"/><a href="mailto:<%=getServletContext().getAttribute("surveyAdminEmail")%>" > <%=getServletContext().getAttribute("surveyAdminEmail")%></a></p></div>
				
				<!-- .actions -->
			</form>

		</div>
		<!-- /content -->
	</div>
	<!-- /account-container -->
</body>
</html>




