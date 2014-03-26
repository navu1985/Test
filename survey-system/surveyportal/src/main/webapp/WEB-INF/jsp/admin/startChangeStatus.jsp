<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div><b><spring:message code="label.application.enabled"/></b></div>
<div>
	<a id="disable" stopPassportId="${applicationId}" class="btn btn-custom" onclick="disable(this);"><spring:message code="label.application.disable"/></a>
</div>
