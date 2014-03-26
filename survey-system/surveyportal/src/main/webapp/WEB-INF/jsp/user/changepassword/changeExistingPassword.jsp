<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="panel panel-primary">
	<div class="panel-heading"><spring:message code="label.reset" /> <spring:message code="label.password" /></div>
  	<div class="panel-body">
		<c:if test="${!empty error }">
			<div class="alert alert-error">
				${error}
			</div>
		</c:if>
		<c:if test="${!empty success}">
			<div class="alert alert-success">
				${success}
			</div>
		</c:if>
		
		 <form:form class="form-horizontal" id="changeexistingpassword" action="changeexistingpassword" commandName="changePasswordDto">
			<div style="margin-bottom: 8px;">
				<label class="control-label form-element" style="padding-right: 8px;">
					<spring:message code="label.old" /> <spring:message	code="label.password" />
				</label>
				<div class="form-element inline" >
					<input type="password" id="oldpassword" name="oldpassword"/> 
					<form:errors cssClass="alert alert-error hidden-phone" path="oldpassword"/>
				</div>
				<form:errors cssClass="alert alert-error visible-phone" path="oldpassword"/>
			</div>
			
			<div class="form-group" style="margin-bottom: 8px;">
				<label class="control-label form-element" style="padding-right: 8px;">
					<spring:message code="label.new" /> <spring:message code="label.password" />
				</label>
				<div class="form-element inline">
					<input type="password" id="password" name="password" />  
					<form:errors cssClass="alert alert-error hidden-phone" path="password"/>
				</div>
				<form:errors cssClass="alert alert-error visible-phone" path="password"/>
			</div>
			
			<div class="form-group" style="margin-bottom: 8px;">
				<div class="col-lg-10">
					<label class="control-label form-element" style="padding-right: 8px;">
						<spring:message code="label.confirm" />	<spring:message	code="label.password" />
					</label>
				</div>
				<div class="form-element inline">
					<input type="password" id="confirmpassword"	name="confirmpassword" /> 
					<form:errors cssClass="alert alert-error hidden-phone" path="confirmpassword"/>
				</div>
				<form:errors cssClass="alert alert-error visible-phone" path="confirmpassword"/>
			</div>
			
	  
	  		<div class="form-group" style="padding-left: 13%;">
				<a onclick="updatepassword();" class="btn btn-custom">Submit</a>
				<a onclick="goBack();" class="btn btn-custom back" >Back</a>
		    </div>
		</form:form>  
  	</div>
</div>
