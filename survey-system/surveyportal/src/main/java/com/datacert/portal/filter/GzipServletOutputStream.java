package com.datacert.portal.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

// Copied from http://nagiworld.net/oldblog/2007/04/gzipfilter.html
class GzipServletOutputStream extends ServletOutputStream
{
	private GZIPOutputStream os;

	private ServletOutputStream sos;

	private HttpServletResponse response;

	public GzipServletOutputStream(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void write(int i) throws IOException {
		init();
		os.write(i);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		init();
		os.write(b, off, len);
	}

	public void finish() throws IOException {
		if (os != null) {
			os.flush();
			os.finish();
		}
	}

	@Override
	public void flush() throws IOException {
		if (os != null) {
			os.flush();
		}
		if (sos != null) {
			sos.flush();
		}
	}

	@Override
	public void close() throws IOException {
		if (os != null) {
			os.close();
		}
	}

	public void reset() {
		os = null;
	}

	private void init() throws IOException {
		if (os != null) {
			return;
		}
		response.setHeader("Content-Encoding", "gzip");
		sos = response.getOutputStream();
		os = new PatchedGzipOutputStream(sos);
	}
}

class PatchedGzipOutputStream extends GZIPOutputStream
{
	public PatchedGzipOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	public void finish() throws IOException {
		try {
			super.finish();
		} finally {
			
			def.end();
		}
	}
}
