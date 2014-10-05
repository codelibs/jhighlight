/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: HighlightFilter.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.servlet;

import javax.servlet.*;

import com.uwyn.jhighlight.renderer.Renderer;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import com.uwyn.jhighlight.tools.FileUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A servlet filter that offers on-the-fly syntax highlighting for Java, HTML,
 * XHTML, XML and LZX files.
 * <p>The filter should be declared in a similar fashion as this:
 * <pre>&lt;filter&gt;
 *    &lt;filter-name&gt;jhighlight&lt;/filter-name&gt;
 *    &lt;filter-class&gt;com.uwyn.jhighlight.servlet.HighlightFilter&lt;/filter-class&gt;
 *&lt;/filter&gt;
 *
 *&lt;filter-mapping&gt;
 *    &lt;filter-name&gt;jhighlight&lt;/filter-name&gt;
 *    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 *&lt;/filter-mapping&gt;</pre>
 * <p>It will respond to files with the following extensions:
 * <code>.javas</code>, <code>.htmls</code>, <code>.htms</code>,
 * <code>.xhtmls</code>, <code>.xmls</code> and <code>.lzxs</code>. These will
 * be automatically mapped to files without the last <code>s</code> in the
 * filenames. Thus, for example, a request like this:
 * <pre>http://myhost.com/folder/MySource.javas</pre>
 * <p>will retrieve this file:
 * <pre>http://myhost.com/folder/MySource.java</pre>
 * <p>The contents of this file will be automatically highlighted and the
 * resulting HTML will be served.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public final class HighlightFilter implements Filter
{
	public void init(FilterConfig filterConfig)
	{
	}
	
	public void destroy()
	{
	}
	
	public void doFilter(ServletRequest request,
						 ServletResponse response, FilterChain chain)
	throws IOException, ServletException
	{
		if (request instanceof HttpServletRequest &&
			response instanceof HttpServletResponse)
		{
			HttpServletRequest  http_request = (HttpServletRequest)request;
			HttpServletResponse http_response = (HttpServletResponse)response;
			
			Renderer renderer = null;
			String uri = http_request.getRequestURI();
			String extension = FileUtils.getExtension(uri);
			if (extension != null &&
				extension.endsWith("s"))
			{
				renderer = XhtmlRendererFactory.getRenderer(extension.substring(0, extension.length()-1));
			}
			
			if (renderer != null)
			{
				SourceRequestWrapper    request_wrapper = new SourceRequestWrapper(http_request);
				CharResponseWrapper     response_wrapper = new CharResponseWrapper(http_response);
				
				chain.doFilter(request_wrapper, response_wrapper);
				
				OutputStream out = response.getOutputStream();
				try
				{
					if (HttpServletResponse.SC_OK == response_wrapper.getStatus())
					{
						InputStream is = new ByteArrayInputStream(response_wrapper.getWrappedOutputStream().toByteArray());
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						
						String encoding = request.getCharacterEncoding();
						if (null == encoding)
						{
							encoding = "UTF-8";
						}
						
						renderer.highlight(http_request.getServletPath().substring(1), is, os, encoding, false);
						
						String highlighted = os.toString("ISO-8859-1");
						
						response.setContentType("text/html");
						response.setContentLength(highlighted.length());
						out.write(highlighted.getBytes("ISO-8859-1"));
					}
					else
					{
						out.write(response_wrapper.getWrappedOutputStream().toByteArray());
					}
				}
				finally
				{
					out.close();
				}
			}
			else
			{
				chain.doFilter(request, response);
			}
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
	
	private static class SourceRequestWrapper extends HttpServletRequestWrapper
	{
		public SourceRequestWrapper(HttpServletRequest request)
		{
			super(request);
		}
		
		public String getServletPath()
		{
			String path = super.getServletPath();
			return path.substring(0, path.length() - 1);
		}
		
		public String getPathTranslated()
		{
			String path = super.getPathTranslated();
			return path.substring(0, path.length() - 1);
		}
		
		public String getRequestURI()
		{
			String uri =  super.getRequestURI();
			return uri.substring(0, uri.length() - 1);
		}
		
		public StringBuffer getRequestURL()
		{
			StringBuffer url =  super.getRequestURL();
			url.setLength(url.length() - 1);
			return url;
		}
	}
	
	private static class CharResponseWrapper extends HttpServletResponseWrapper
	{
		private ServletOutputStreamWrapper mOutput;
		private int mStatus = HttpServletResponse.SC_OK;
		
		public ServletOutputStreamWrapper getWrappedOutputStream()
		{
			return mOutput;
		}
		
		public CharResponseWrapper(HttpServletResponse response)
		{
			super(response);
			
			mOutput = new ServletOutputStreamWrapper();
		}
		
		public ServletOutputStream getOutputStream()
		throws IOException
		{
			return mOutput;
		}
		
		public void setStatus(int status)
		{
			mStatus = status;
			
			super.setStatus(status);
		}
		
		public void sendError(int status, String msg)
		throws IOException
		{
			mStatus = status;
			
			super.sendError(status, msg);
		}
		
		public void sendError(int status)
		throws IOException
		{
			mStatus = status;
			
			super.sendError(status);
		}
		
		public int getStatus()
		{
			return mStatus;
		}
	}
	
	private static class ServletOutputStreamWrapper extends ServletOutputStream
	{
		protected ByteArrayOutputStream mOutput;
		
		public ServletOutputStreamWrapper()
		{
			mOutput = new ByteArrayOutputStream();
		}
		
		public void write(int b) throws IOException
		{
			mOutput.write(b);
		}
		
		public byte[] toByteArray()
		{
			return mOutput.toByteArray();
		}
	}
}
