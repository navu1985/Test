<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="panel panel-primary">
	<div class="panel-heading"><spring:message code="label.application.settings"/></div>
	<div class="panel-body">
		<form:form id="application" method="POST" commandName="applicationDto">
			<spring:hasBindErrors name="applicationDto">
			  <meta name="data-applicationDto-valid">
			</spring:hasBindErrors> 
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr class="tipAdded">
							<td><label><spring:message	code="label.applicationId"/> </label></td>
							<td>${applicationDto.applicationIdentifier}_${fn:escapeXml(applicationDto.dbGuid)}<input type="hidden" id="applicationIdentifier" name="applicationIdentifier" value="${fn:escapeXml(applicationDto.applicationIdentifier)}"  /></td>
						</tr>
					</thead>
					<tbody>
						
						<tr class="tipAdded">
							<td><label><spring:message	code="label.guid"/></label></td>
							<td>${fn:escapeXml(applicationDto.dbGuid)}<input type="hidden" id="dbGuid" name="dbGuid" value="${fn:escapeXml(applicationDto.dbGuid)}"  /></td>
						</tr>
						
						<tr class="tipAdded">
							<td><label><spring:message code="label.client"/>  <spring:message code="label.name"/></label></td>
							<td>
								<input type="text" id="applicationName" class="smalldevice-elemen margin-top7t" name="applicationName" value="${fn:escapeXml(applicationDto.applicationName)}" />
								<c:if test="${applicationDto.id ne null}">
									<input type="hidden" id="id"  name="id" value="${applicationDto.id}" />
								</c:if>
								<form:errors cssClass="alert alert-error  inline-block margin-top7" path="applicationName" />
							</td>
						</tr>
						
						<tr class="tipAdded">
							<td><label><spring:message code="label.contactPerson"/></label></td>
							<td>
								<input type="text" id="contactPerson"  class="smalldevice-element margin-top7" name="contactPerson" value="${fn:escapeXml(applicationDto.contactPerson)}" />
								<form:errors cssClass="alert alert-error inline-block"  path="contactPerson" />
							</td>
						</tr>
						
						<tr class="tipAdded">
							<td><label><spring:message code="label.contactEmail"/></label></td>
							<td>
								<input type="text" id="contactEmail"  class="smalldevice-element margin-top7" name="contactEmail" value="${fn:escapeXml(applicationDto.contactEmail)}" />
								<form:errors cssClass="alert alert-error  inline-block " path="contactEmail" />
							</td>
						</tr>
						
						<tr class="tipAdded">
							<td><label><spring:message code="label.contactPhone"/></label></td>
							<td>
								<input type="text" id="contactPhone" class="smalldevice-element margin-top7" name="contactPhone" value="${fn:escapeXml(applicationDto.contactPhone)}" /> 
								<form:errors cssClass="alert alert-error  inline-block"  path="contactPhone" />
							</td>
						</tr>
						
						<tr class="tipAdded">
							<td>&nbsp;</td>
							<td><a id="addapplication" class="btn btn-custom" onclick="addPassport();"><spring:message code="label.save"/></a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</form:form>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/static/portaljs/admin.js"/>"></script>



