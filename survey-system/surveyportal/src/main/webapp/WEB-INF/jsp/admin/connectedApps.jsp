<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set var="connected" value="${connectionStatus}" />

<div class="panel panel-primary">
	<div class="panel-heading bold"> <spring:message code="label.connected"/> <spring:message code="label.application"/></div>
	<div class="panel-body">
		
		<div class="table-responsive">
			<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<th><spring:message	code="label.applicationId"/></th>
					<th><spring:message	code="label.guid"/></th>
					<th><spring:message code="label.client"/>  <spring:message code="label.name"/></th>
					<th><spring:message code="label.contactPerson"/></th> 
					<th><spring:message code="label.contactEmail"/></th>
					<th><spring:message code="label.contactPhone"/></th>
					<th><spring:message code="label.connectionSatus"/></th>
				</tr>
			</thead>
			<tbody>
					<c:forEach items="${appList}" var="application">
					 <tr>
						<td class="wrapword">
							${fn:escapeXml(application.applicationIdentifier)}
						</td>
						
						<td class="wrapword">
							<div data-dbGuid="${fn:escapeXml(application.id)}">${fn:escapeXml(application.dbGuid)}</div>
						</td> 
						
						<td class="wrapword">
							${fn:escapeXml(application.applicationName)}
						</td>
						
						<td class="wrapword">
							${fn:escapeXml(application.contactPerson)}
						</td>
						
						<td class="wrapword">
							${fn:escapeXml(application.contactEmail)}
						</td>
						
						<td class="wrapword">
							${fn:escapeXml(application.contactPhone)}
						</td>
						
						<td>
							<div statusContentId="${application.id}">
								<div><b>${application.connectionStatus}</b></div>
							
								<c:if test="${application.connectionStatus eq connected}">
									<div><a id="disable" stopPassportId="${application.id}" class="btn btn-custom" onclick="disable(this);"><spring:message code="label.application.disable"/></a></div>
								</c:if>
							
								<c:if test="${application.connectionStatus ne connected}">
									<div>
										<a href="#editApplication/${application.id}"><img class="handPointer"  title="Edit" editPassportId="${application.id}" src='<c:url value="static/images/editPencil.png"/>' ></a>
										<div style="display:inline; line-height:3">
											<a id="enable" startPassportId="${application.id}" class="btn btn-custom" onclick="enable(this);"><spring:message code="label.application.enable"/></a>
											<a id="resetguid" resetPassportId="${application.id}" class="btn btn-custom" onclick="resetguid(this);"><spring:message code="label.resetGuid"/></a>
										</div> 
									 </div>
								</c:if>
							</div>
						</td>
		
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
	</div>
</div>
<script type="text/javascript" src="<c:url value="/static/portaljs/admin.js"/>"></script>