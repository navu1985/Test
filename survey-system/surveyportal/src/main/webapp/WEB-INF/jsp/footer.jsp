<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<tr class="tipAdded">
	<td colspan="2">
		<div>
			<spring:message code="label.index.pleasecontact" />
			<a style="color: black;  "><%=getServletContext().getAttribute("surveyAdminEmail")%>
			</a>
			<%-- <spring:message code="label.index.ifyouhaveanyquestion" /> --%>
		</div>
	</td>
</tr>
