<%@ taglib uri="/WEB-INF/custom-tld/elfunctions.tld" prefix="dc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="now" class="java.util.Date"/>
<sec:authentication property="principal.userId" var="principalUserId"/>
<c:set var="activetemp" value="ACTIVE"/>

<c:set var="relatedPolicyFlag" value="false"/>
<c:set var="policyDocumentUrl" value=""/>
<c:set var="openStatus" value="OPEN" />
<c:set var="saveLaterStatus" value="SAVE_LATER" />

<c:if test="${! empty surveys}">
	<div id="panelsurveytable" class="panel panel-primary">
		<div class="panel-heading bold"><spring:message	code="label.mypendingsurvey"/></div>
		<div class="panel-body">
			<div class="table-responsive">
				<c:if test="${!empty message }">
					<div class="error">
						${message}
					</div>
				</c:if>
				
				<table id="surveytable" class="tablesorter" style="min-width: 650px;">
					<thead>
					<tr>
						<th><spring:message	code="label.surveyName"/></th>
						<th><spring:message	code="label.assignedOn"/></th>
						<th><spring:message	code="label.dueOn"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${surveys}" var="survey" varStatus="varSurvey">
						<tr <c:if test="${survey.status eq openStatus}">class="bold"</c:if>>
							
							<td>	  
									<a href="<c:url value="#surveys/${survey.surveyId}/questions/1"/>">${dc:formatStringForHtmlDisplay(survey.surveyName)}</a> 
							</td>
							<td>
								<fmt:formatDate pattern="MM/dd/yyyy"value="${survey.surveyAssignDate}" />
							</td>
							<td <c:if test="${survey.surveyDueDate lt now}">style="color: red"</c:if>>
									<fmt:formatDate pattern="MM/dd/yyyy" value="${survey.surveyDueDate}" />
							</td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="3">
								<div class="surveypager" style="float: right;">
									<span class="pagedisplay" style="float: left; padding-top: 5px;"></span> 
									<span class="right">
										<a href="javascript:void(0)" data-page="1" class="first tablepagination-first"><span><img src="static/images/first.png" /></span></a>
										<span class="prev tablepagination">
											<img src="static/images/prev.png" />
										</span>
										<span class="pagecount"></span>
										&nbsp;<span class="next tablepagination">
											<img src="static/images/next.png" />
										</span>
										<a class="last tablepagination"  href="javascript:void(0)" data-page="${fn:length(policies)}"><span><img src="static/images/last.png" /></span></a>
									</span>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>	
						
			</div>	
		</div>
	</div>
</c:if>


	<c:if test="${! empty policies}">
		<div id="panelpolicytable" class="panel panel-primary">
		  <div  class="panel-heading bold"><spring:message	code="label.pending.policies.procedures"/></div>
		  <div class="panel-body">
		  
		 	 <div class="table-responsive">
		    <table id="policytable" class="tablesorter" style="min-width: 650px;">
			<thead>
			<tr>
				<th><spring:message	code="label.name"/></th>
				<th><spring:message	code="label.type"/></th>
				<th><spring:message	code="label.issuingDepartment"/></th>
				<th><spring:message	code="label.topic"/></th>
				<th><spring:message	code="label.dueOn"/></th>
				<th><spring:message	code="label.relatedDocuments"/></th>
			</tr>
			</thead>
			<tbody>
					<c:forEach items="${policies}" var="policy" varStatus="varPolicy">
						<tr>
								<td class="wrapword">
									<div style="float: left; padding-right: 8px; width: 50px; ">
										<a class="policy mouse" policyId-ack="false" policyId-data="${fn:escapeXml(policy.policyId)}"  policydocId-data="${fn:escapeXml(policy.policyDocumentDescriptor.policyDocumentId)}">
											<img class="imageborder" width="50px" height="58px" src="policies/policy/${fn:escapeXml(policy.policyId)}/thumbnail?_=${now}"/>
										</a>
									</div>
									<div <c:forEach items="${policy.userPolicies}" var="userPolicy"><c:if test="${userPolicy.noOfTimesAccessed eq 0 }">style="font-weight:bold;"</c:if></c:forEach>>
									 		 <a class="policy mouse" policyId-ack="false" policyId-data="${fn:escapeXml(policy.policyId)}"  policydocId-data="${fn:escapeXml(policy.policyDocumentDescriptor.policyDocumentId)}">
												${dc:formatStringForHtmlDisplay(policy.policyName)} 
										     </a> 
										     <br/>ID: ${fn:escapeXml(policy.applicationPolicyId)}
										     <br/>Version Date: <fmt:formatDate pattern="MM/dd/yyyy" value="${policy.policyEffectiveDate}" />
									</div>
								</td> 
								<td class="wrapword">${fn:escapeXml(policy.policyType)}</td>
								<td class="wrapword">${fn:escapeXml(policy.issuingDepartment)}</td>
								<td class="wrapword">${fn:escapeXml(policy.policyTopic)}</td>
								<td class="wrapword">
									<c:forEach items="${policy.userPolicies}" var="userPolicy">
										<div <c:if test="${userPolicy.policyDueDate lt now}">style="color: red"</c:if>><fmt:formatDate pattern="MM/dd/yyyy" value="${userPolicy.policyDueDate}" /></div>	
									</c:forEach>
								</td>
								<td class="wrapword">
									<c:set value="true" var="isfirst"/>
									<c:forEach items="${policy.relatedPolicies}" var="relatedpolicy" varStatus="varindex">
										<c:forEach items="${relatedpolicy.userPolicies}" var="userRelatedPolicy">
											<c:if test="${activeStatus eq relatedpolicy.policyStatus}">
												<c:if test="${isfirst eq false}">
													,
												</c:if>
												<a class="policy mouse"  policyId-ack="${fn:escapeXml(userRelatedPolicy.isAcknowledge)}" policyId-data="${fn:escapeXml(relatedpolicy.policyId)}"  policydocId-data="${fn:escapeXml(relatedpolicy.policyDocumentDescriptor.policyDocumentId)}">${dc:formatStringForHtmlDisplay(relatedpolicy.policyName)}</a>
												<c:set value="false" var="isfirst"/>
											</c:if>
											
										</c:forEach>
									</c:forEach>
								</td>
						</tr>
					</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="6">
								<div class="policypager" style="float: right;"> 
										<span class="pagedisplay" style="float: left; padding-top: 5px;"></span>
										<span class="right">
											<a href="javascript:void(0)" data-page="1" class="first tablepagination-first"><span><img src="static/images/first.png" /></span></a>
											<span class="prev tablepagination">
												<img src="static/images/prev.png" />
											</span>
											<span class="pagecount"></span>
											&nbsp;<span class="next tablepagination">
												<img src="static/images/next.png" />
											</span>
											<a class="last tablepagination"  href="javascript:void(0)" data-page="${fn:length(policies)}"><span><img src="static/images/last.png" /></span></a>
										</span>
								</div>
							</td>
						</tr>
					</tfoot>
				</table>
				
				
			</div>	
		  </div>
		</div>
	</c:if>

<c:if test="${empty surveys and  empty policies}">
	<div id="nodata">${dc:formatStringForHtmlDisplay(noMessageData)}</div>
</c:if>
<script type="text/javascript" src="<c:url value="/static/portaljs/survey-search-sort.js"/>"></script>