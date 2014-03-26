package com.datacert.portal.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Copied from http://nagiworld.net/oldblog/2007/04/gzipfilter.html (see GZIPFilter.java)
public class GzipFilter implements Filter {
	private static final String WL_GZIP_FILTER = "nagiworld.net.filters.gzip";

	public void init(FilterConfig cfg) {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// Case 1: Let non-http request just pass through
		if (!(response instanceof HttpServletResponse) || !(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		// Case2 : Already filtered ?
		if (req.getAttribute(WL_GZIP_FILTER) != null) {
			chain.doFilter(request, response);
			return;
		}

		req.setAttribute(WL_GZIP_FILTER, "true");

		boolean needsCompression = false;
		for (@SuppressWarnings("unchecked")
		Enumeration<Object> enum_ = req.getHeaders("Accept-Encoding"); enum_.hasMoreElements();) {
			String name = (String) enum_.nextElement();
			if (name.indexOf("gzip") != -1) {
				needsCompression = true;
			}
		}

		// Case 3: Request doesnt support gzip
		if (!needsCompression) {
			chain.doFilter(request, response);
			return;
		}

		GzipResponseWrapper wrapper = new GzipResponseWrapper(res);
		try {
			chain.doFilter(request, wrapper);
			wrapper.flushBuffer();
		} finally {
			wrapper.finish();
		}
	}

	public void destroy() {
	}
}
