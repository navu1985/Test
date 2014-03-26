<%@ taglib uri="/WEB-INF/custom-tld/elfunctions.tld" prefix="dc"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div  class="panel panel-primary">
	<div class="panel-heading bold"><spring:message code='label.configuremessages'/></div>
	<div class="panel-body">
		<div class="table-responsive border-msg-conf">
			<table>
				<tbody>
					<tr>
						<td class="bold"><spring:message code='label.nopolicymessage'/>:</td>
						<td>
							<div  id="editmessagediv">${dc:formatStringForHtmlDisplay(settings.noDataMessage)}</div>
							<input type="hidden" id="id" name="id" value="${fn:escapeXml(settings.id)}"/>
							<input type="hidden" id="adminEmail" name="adminEmail" value="${fn:escapeXml(settings.adminEmail)}"/>
						</td>
						
					</tr>
					<tr>
						<td class="bold"><spring:message code='label.submitaninquiry'/> &nbsp;</td>
						<td>
							<input name="showReportAnIssue" type="checkbox"  disabled="disabled" <c:if test="${settings.showSubmitAnInquiry eq true}">checked="checked"</c:if>/>
						</td>
					</tr>
					
					<tr>
						<td class="bold"><spring:message code='label.sidebarlinkreportanissue'/></td>
						<td>
							<input name="showReportAnIssue" type="checkbox"  disabled="disabled" <c:if test="${settings.showReportAnIssue eq true}">checked="checked"</c:if>/>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td><a href="#editAdminSettings" class="btn btn-custom"><spring:message code='label.edit'/></a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>