<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="openStatus" value="OPEN" />
<c:set var="saveLaterStatus" value="SAVE_LATER" />

<div class="panel panel-primary">
	<div class="panel-heading">My Pending Surveys</div>
	<div class="panel-body">
		<div class="table-responsive">
			<c:if test="${!empty message }">
				<div class="error">
					${message}
				</div>
			</c:if>
			<table class="table table-hover table-bordered">
				<tr>
					<th><spring:message	code="label.surveyName"/></th>
					<th><spring:message	code="label.assignedOn"/></th>
					<th><spring:message	code="label.dueOn"/></th>
					<th><spring:message	code="label.client"/></th>
				</tr>
				<c:forEach items="${surveys}" var="survey">
					<tr	class="bold">
						<td><a
							href="<c:url value="#surveys/${survey.surveyId}/questions/1"/>">${survey.surveyName}</a>
						</td>
						<td><fmt:formatDate pattern="MM/dd/yyyy" value="${survey.surveyAssignDate}" /></td>
						<c:choose>
							<c:when test="${survey.surveyDueDate lt now}">
								<td style="color: red"><fmt:formatDate pattern="MM/dd/yyyy"
										value="${survey.surveyDueDate}" /></td>
							</c:when>
							<c:otherwise>
								<td><fmt:formatDate pattern="MM/dd/yyyy"
										value="${survey.surveyDueDate}" /></td>
							</c:otherwise>
						</c:choose>
						<td>
							<c:if test="${empty survey.passport.applicationName}">
									-
							</c:if>
							<c:if test="${!empty survey.passport.applicationName}">
									${survey.passport.applicationName}
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>			
		</div>	
	</div>
</div>
