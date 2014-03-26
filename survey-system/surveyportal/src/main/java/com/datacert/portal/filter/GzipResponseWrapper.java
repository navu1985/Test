package com.datacert.portal.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// Copied from http://nagiworld.net/oldblog/2007/04/gzipfilter.html (see
// GZIPResponseWrapper.java)
class GzipResponseWrapper extends HttpServletResponseWrapper
{
	private ServletOutputStream stream;
	private PrintWriter writer;
	private GzipServletOutputStream gstream;
	private final HttpServletResponse response;

	public GzipResponseWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}

	@Override
	public void setContentType(String type) {
		response.setHeader("Content-Encoding", "gzip");
		super.setContentType(type);
	}

	@Override
	public void setContentLength(int len) {
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("strict servlet API : cannot call getOutputStream after getWriter() ");
		}
		if (stream == null) {
			stream = createOutputStream();
		}
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (stream != null) {
			throw new IllegalStateException("strict servlet API : cannot call getWriter after getOutputStream() ");
		}
		if (writer == null) {
			writer = new PrintWriter(new OutputStreamWriter(createOutputStream(), response.getCharacterEncoding()));
		}
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (gstream == null) {
			return;
		}
		if (writer != null) {
			writer.flush();
		} else if (stream != null) {
			stream.flush();
		}
		super.flushBuffer();
	}

	@Override
	public void resetBuffer() {
		super.resetBuffer();
		if (gstream != null) {
			gstream.reset();
		}
	}

	void finish() throws IOException {
		if (gstream == null) {
			return;
		}
		gstream.finish();
	}

	private ServletOutputStream createOutputStream() {
		if (gstream == null) {
			gstream = new GzipServletOutputStream(response);
		}
		return gstream;
	}

	// ensure Content-Type is always set through setContentType()
	@Override
	public void setHeader(String name, String value) {
		if (name.toLowerCase().equals("content-type")) {
			setContentType(value);
		} else {
			super.setHeader(name, value);
		}
	}

	// ensure Content-Type is always set through setContentType()
	@Override
	public void addHeader(String name, String value) {
		if (name.toLowerCase().equals("content-type")) {
			setContentType(value);
		} else {
			super.addHeader(name, value);
		}
	}

}
