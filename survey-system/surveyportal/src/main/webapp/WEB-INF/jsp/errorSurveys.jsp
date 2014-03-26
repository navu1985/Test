<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="panel panel-primary">
	<div class="panel-heading bold">Response Error Tracking</div>
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th><spring:message	code="label.surveyName"/></th>
						<th><spring:message	code="label.username"/></th>
						<th><spring:message	code="label.lastError"/></th>
						<th><spring:message	code="label.resetStatus"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${errorSurveys }" var="errorSurvey">
						 <tr>
							<td>${errorSurvey.survey.surveyName}</td>
							<td>${errorSurvey.user.username}</td> 
							<td>${errorSurvey.lastError}</td>
							<td><a href="<c:url value="#resetSurvey/${errorSurvey.survey.surveyId}/user/${errorSurvey.user.userId}"/>"><spring:message	code="label.reset"/></a></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>	
