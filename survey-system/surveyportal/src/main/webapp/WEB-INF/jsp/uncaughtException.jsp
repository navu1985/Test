<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%-- <%@ include file="/WEB-INF/jsp/header.jsp" %>
 --%>
<h2/>Internal error</h2>
<p/>

<% 
try {
	// The Servlet spec guarantees this attribute will be available
	Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception"); 

	if (exception != null) {
		if (exception instanceof ServletException) {
			// It's a ServletException: we should extract the root cause
			ServletException sessionex = (ServletException) exception;
			Throwable rootCause = sessionex.getRootCause();
			if (rootCause == null)
				rootCause = sessionex;
			out.println("** Root cause is: "+ rootCause.getMessage());
			rootCause.printStackTrace(new java.io.PrintWriter(out)); 
		}
		else {
			// It's not a ServletException, so we'll just show it
			exception.printStackTrace(new java.io.PrintWriter(out)); 
		}
	} 
	else  {
    	out.println("No error information available");
	} 

	// Display cookies
	out.println("\nCookies:\n");
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
    	for (int i = 0; i < cookies.length; i++) {
      		out.println(cookies[i].getName() + "=[" + cookies[i].getValue() + "]");
		}
	}
	    
} catch (Exception ex) { 
	ex.printStackTrace(new java.io.PrintWriter(out));
}
%>

<p/>
<br/>


<%-- <%@ include file="/WEB-INF/jsp/footer.jsp" %>
 --%>