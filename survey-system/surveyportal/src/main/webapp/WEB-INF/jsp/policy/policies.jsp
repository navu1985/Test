<%@ taglib uri="/WEB-INF/custom-tld/elfunctions.tld" prefix="dc"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="now" class="java.util.Date"/>
<sec:authentication property="principal.userId" var="principalUserId"/>
<c:set var="relatedPolicyFlag" value="false"/>

<div id="panelpolicytable" class="panel panel-primary">
  <div class="panel-heading bold">My Policies and Procedures</div>
  <div class="panel-body">
  
  	<!-- <form> -->
	    Show documents containing: 
	    <div style="display: inline-block; line-height: 3;">
	        <input style="margin-top: 10px; " class="smalldevice-element" id="policyFullTextInput" type="text">
	        <button  id="policyFullTextSearch" type="button" class="btn">Search</button>
	    </div>
	<!-- </form> -->
		
	<c:if test ="${empty  policies}">
 				<div>No Records to Display.</div>
	</c:if>
	
	<c:if test ="${!empty  policies}">
 			<div class="table-responsive">
    	<table id="policytable" class="tablesorter" style="word-wrap: break-word;">
    		
    		<c:if test ="${! empty  policies}">
				<thead>
				<tr>
					<th><spring:message	code="label.name"/></th>
					<th><spring:message	code="label.type"/></th>
					<th><spring:message	code="label.issuingDepartment"/></th>
					<th><spring:message	code="label.topic"/></th>
					<th><spring:message	code="label.relatedDocuments"/></th>
				</tr>
				</thead>
				<tbody>
						<c:forEach items="${policies}" var="policy" varStatus="varPolicy">
							<tr>
									<td>
										 <div style="float: left; padding-right: 8px; width: 50px;">
										 	<a class="policy mouse" policyId-ack="true" policyId-data="${fn:escapeXml(policy.policyId)}"  policydocId-data="${fn:escapeXml(policy.policyDocumentDescriptor.policyDocumentId)}">
										 		<img class="imageborder img-responsive" src="policies/policy/${fn:escapeXml(policy.policyId)}/thumbnail?_=${now}"/>
									 		</a>
										 </div>
										<div >
										 		 <a class="policy mouse" policyId-ack="true" policyId-data="${fn:escapeXml(policy.policyId)}"  policydocId-data="${fn:escapeXml(policy.policyDocumentDescriptor.policyDocumentId)}">${dc:formatStringForHtmlDisplay(policy.policyName)}</a> 
											     <br/>ID: ${fn:escapeXml(policy.applicationPolicyId)}
											     <br/>Version Date: <fmt:formatDate pattern="MM/dd/yyyy" value="${policy.policyEffectiveDate}" />
										</div>
									</td> 
									<%-- <td>${fn:escapeXml(policy.applicationPolicyId)}</td> --%>
									<td>${fn:escapeXml(policy.policyType)}</td>
									<td>${fn:escapeXml(policy.issuingDepartment)}</td>
									<td>${fn:escapeXml(policy.policyTopic)}</td>
									<%-- <td><fmt:formatDate pattern="MM/dd/yyyy" value="${policy.policyEffectiveDate}" /></td> --%>
									<td>
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
						<td colspan="7">
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
			</c:if>
		</table>
	</div>	
	</c:if>
  	 
  </div>
</div>
<div id="acknowledged-policies-count" class="hide">${ackPoliciesCount}</div>
<script type="text/javascript" src="<c:url value="/static/portaljs/policy-search-sort.js"/>"></script>