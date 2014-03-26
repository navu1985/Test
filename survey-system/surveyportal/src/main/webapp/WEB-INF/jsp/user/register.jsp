<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/includes.jsp"%>
<%@ taglib uri="/WEB-INF/custom-tld/elfunctions.tld" prefix="dc"%>
<link href="static/portalcss/login.css" rel="stylesheet">
<title><spring:message code="label.account.registration"/></title>
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
					<div class="panel-heading"><spring:message code="label.account.registration"/></div>
				  	<div class="panel-body">
				 	
				 		<form:form id="registrationForm" action="signup" method="POST" commandName="registerUserDto">
							
							<!--  username-->
							<div class="control-group">
								<label class="control-label bold"><spring:message code="label.emailid"/></label>
								<div class="controls">
										<span class="inline-block">
											<input class="controls-element smalldevice-element" type="text" name="userName" value="${dc:formatStringForHtmlDisplay(registerUserDto.userName)}" placeholder="Email" >
											<form:errors  cssClass="alert alert-error visible-desktop" path="userName"/>
										</span>
									<form:errors cssClass="alert alert-error hidden-desktop" path="userName" />
								</div>
							</div>
							<!--  /username-->

							<!--  question -->
							<div class="control-group">
								<label class="control-label bold"> 
									<spring:message	code="label.securityquestion" /> 1:
								</label>
								<div class="controls">
									<span class="inline-block">
										<select name="questionOne" id="questionOne" class="controls-element smalldevice-element-select">
											<option value="">	<spring:message code="label.securityquestion1" /> </option>
											<c:forEach items="${registerUserDto.securityQuestionsList}" var="question">
												<c:if test="${question.questionId eq  registerUserDto.questionOne}">
													<option value="${question.questionId}" selected="selected">${question.questionText}</option>
												</c:if>
												<c:if test="${question.questionId ne  registerUserDto.questionOne}">
													<option value="${question.questionId}" >${question.questionText}</option>
												</c:if>
											</c:forEach>
										</select>
										<form:errors cssClass="alert alert-error visible-desktop" path="questionOne" />
									</span>
									<form:errors cssClass="alert alert-error hidden-desktop" path="questionOne" />
								</div>
							</div>
							<!--  question -->


							<div class="control-group">
								<label class="control-label bold">
									<spring:message code="label.answer"/>
								</label>
								<div class="controls">
									<span class="inline-block">
										<input type="text" class="controls-element smalldevice-element" name="answerOne" value="${dc:formatStringForHtmlDisplay(registerUserDto.answerOne)}" placeholder="Answer">
										<form:errors cssClass="alert alert-error visible-desktop" path="answerOne" />
									</span>
									<form:errors cssClass="alert alert-error hidden-desktop" path="answerOne" />
								</div>
							</div>

							<div class="control-group">
								<label class="control-label bold">
									<spring:message	code="label.securityquestion" /> 2:
								</label>
								<div class="controls">
									<span class="inline-block">
										<select name="questionTwo" id="questionTwo" class="controls-element smalldevice-element-select">
											<option value="">	<spring:message code="label.securityquestion2" /> </option>
											<c:forEach items="${registerUserDto.securityQuestionsList}" var="question">
												<c:if test="${question.questionId eq  registerUserDto.questionTwo}">
													<option value="${question.questionId}" selected="selected">${question.questionText}</option>
												</c:if>
												<c:if test="${question.questionId ne  registerUserDto.questionTwo}">
													<option value="${question.questionId}" >${question.questionText}</option>
												</c:if>
											</c:forEach>
										</select>
										<form:errors cssClass="alert alert-error visible-desktop" path="questionTwo" />
									</span>
									<form:errors cssClass="alert alert-error hidden-desktop" path="questionTwo" />
								</div>
						 	</div>
						  
						  	<div class="control-group">
								<label class="control-label bold">
									<spring:message code="label.answer"/>
								</label>
								<div class="controls">
									<span class="inline-block">
										<input type="text" class="controls-element smalldevice-element" name="answerTwo" value="${dc:formatStringForHtmlDisplay(registerUserDto.answerTwo)}" placeholder="Answer">
										<form:errors cssClass="alert alert-error visible-desktop" path="answerTwo" />
									</span>
									<form:errors cssClass="alert alert-error hidden-desktop" path="answerTwo" />
								</div>
							</div>
					  
					  
							<div class="control-group">
									<label class="control-label bold">
										<spring:message	code="label.securityquestion" /> 3:
									</label>
									<div class="controls">
										<span class="inline-block">
											<select name="questionThree" id="questionThree" class="controls-element smalldevice-element-select">
												<option value="">	<spring:message code="label.securityquestion3" /> </option>
												<c:forEach items="${registerUserDto.securityQuestionsList}" var="question">
													<c:if test="${question.questionId eq  registerUserDto.questionThree}">
														<option value="${question.questionId}" selected="selected">${question.questionText}</option>
													</c:if>
													<c:if test="${question.questionId ne  registerUserDto.questionThree}">
														<option value="${question.questionId}" >${question.questionText}</option>
													</c:if>
												</c:forEach>
											</select>
											<form:errors cssClass="alert alert-error visible-desktop" path="questionThree" />
										</span>
										<form:errors cssClass="alert alert-error hidden-desktop" path="questionThree" />
									</div>
							</div>
					 
							<div class="control-group">
									<label class="control-label bold">
										<spring:message code="label.answer"/>
									</label>
									<div class="controls">
										<span class="inline-block">
											<input class="controls-element smalldevice-element" type="text" name="answerThree" value="${dc:formatStringForHtmlDisplay(registerUserDto.answerThree)}" placeholder="Answer">
											<form:errors cssClass="alert alert-error visible-desktop" path="answerThree" />
										</span>
										<form:errors cssClass="alert alert-error hidden-desktop" path="answerThree" />
									</div>
							</div>
					  
							<div class="control-group">
								<div class="controls">
									<div><a  href="<c:url value="/"/>" class="button btn btn-custom"><spring:message code="label.back" /></a></div>
									<div style="margin-right: 65px;"><a id="registerUser" class="button btn btn-custom"><spring:message code="label.createacoount"/></a></div>
									
								</div>
							</div>			
				  
						</form:form>
				  </div>
				</div>
				<!-- /panel -->
		</div>
		<!-- /content -->

	</div>
	<!-- /regirationContainer-->
</body>
<script type="text/javascript" src="static/js/registerJs.js"></script>
</html>



