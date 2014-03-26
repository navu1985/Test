<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div><b><spring:message code="label.application.disabled"/></b></div>
<div>
	<a href="#editApplication/${applicationId}"><img class="handPointer"  title="Edit" editPassportId="${applicationId}" src='<c:url value="static/images/editPencil.png"/>' ></a>
	<div style="display:inline; line-height:3">
		<a id="enable" startPassportId="${applicationId}" class="btn btn-custom" onclick="enable(this);"><spring:message code="label.application.enable"/></a>
		<a id="resetguid" resetPassportId="${applicationId}" class="btn btn-custom" onclick="resetguid(this);">Reset GUID</a>
	</div>
</div>
