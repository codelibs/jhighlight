package org.codelibs.jhighlight.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the HighlightFilter servlet filter.
 */
public class HighlightFilterTest {

    private HighlightFilter filter;

    @Before
    public void setUp() {
        filter = new HighlightFilter();
        filter.init(null);
    }

    @Test
    public void testInitAndDestroy() {
        HighlightFilter f = new HighlightFilter();
        f.init(null);
        f.destroy();
        // Should not throw any exceptions
    }

    @Test
    public void testDoFilterWithJavasExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/src/Example.javas");
        request.setServletPath("/src/Example.javas");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("public class Example { }");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
        // Request wrapper should have modified URI
        assertEquals("/src/Example.java", chain.getLastRequestURI());
    }

    @Test
    public void testDoFilterWithXmlsExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/config/settings.xmls");
        request.setServletPath("/config/settings.xmls");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("<config><setting>value</setting></config>");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
        assertEquals("/config/settings.xml", chain.getLastRequestURI());
    }

    @Test
    public void testDoFilterWithRegularJavaExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/src/Example.java");
        request.setServletPath("/src/Example.java");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("public class Example { }");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
        // Should pass through without modification
        assertEquals("/src/Example.java", chain.getLastRequestURI());
    }

    @Test
    public void testDoFilterWithNonHttpRequest() throws IOException, ServletException {
        MockServletRequest request = new MockServletRequest();
        MockServletResponse response = new MockServletResponse();
        MockFilterChain chain = new MockFilterChain("");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called for non-HTTP requests", chain.wasCalled());
    }

    @Test
    public void testDoFilterWith404Response() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/src/NotFound.javas");
        request.setServletPath("/src/NotFound.javas");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("", HttpServletResponse.SC_NOT_FOUND);

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
    }

    @Test
    public void testDoFilterWithHtmlsExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/pages/index.htmls");
        request.setServletPath("/pages/index.htmls");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("<!DOCTYPE html><html><body>Test</body></html>");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
        assertEquals("/pages/index.html", chain.getLastRequestURI());
    }

    @Test
    public void testDoFilterWithNullExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/noextension");
        request.setServletPath("/noextension");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("some content");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
    }

    @Test
    public void testDoFilterWithUnsupportedExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/file.txts");
        request.setServletPath("/file.txts");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("plain text");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
        // txt is not supported, so it should pass through
        assertEquals("/file.txts", chain.getLastRequestURI());
    }

    @Test
    public void testDoFilterWithGroovysExtension() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/scripts/Script.groovys");
        request.setServletPath("/scripts/Script.groovys");

        MockHttpServletResponse response = new MockHttpServletResponse();

        MockFilterChain chain = new MockFilterChain("class Script { def hello() { 'world' } }");

        filter.doFilter(request, response, chain);

        assertTrue("Filter chain should be called", chain.wasCalled());
        assertEquals("/scripts/Script.groovy", chain.getLastRequestURI());
    }

    // Mock classes for testing

    private static class MockServletRequest implements ServletRequest {
        @Override public Object getAttribute(String name) { return null; }
        @Override public java.util.Enumeration getAttributeNames() { return null; }
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public void setCharacterEncoding(String env) { }
        @Override public int getContentLength() { return 0; }
        @Override public long getContentLengthLong() { return 0; }
        @Override public String getContentType() { return null; }
        @Override public jakarta.servlet.ServletInputStream getInputStream() { return null; }
        @Override public String getParameter(String name) { return null; }
        @Override public java.util.Enumeration getParameterNames() { return null; }
        @Override public String[] getParameterValues(String name) { return null; }
        @Override public java.util.Map getParameterMap() { return null; }
        @Override public String getProtocol() { return null; }
        @Override public String getScheme() { return null; }
        @Override public String getServerName() { return null; }
        @Override public int getServerPort() { return 0; }
        @Override public java.io.BufferedReader getReader() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public void setAttribute(String name, Object o) { }
        @Override public void removeAttribute(String name) { }
        @Override public java.util.Locale getLocale() { return null; }
        @Override public java.util.Enumeration getLocales() { return null; }
        @Override public boolean isSecure() { return false; }
        @Override public jakarta.servlet.RequestDispatcher getRequestDispatcher(String path) { return null; }
        @Override public int getRemotePort() { return 0; }
        @Override public String getLocalName() { return null; }
        @Override public String getLocalAddr() { return null; }
        @Override public int getLocalPort() { return 0; }
        @Override public jakarta.servlet.ServletContext getServletContext() { return null; }
        @Override public jakarta.servlet.AsyncContext startAsync() { return null; }
        @Override public jakarta.servlet.AsyncContext startAsync(ServletRequest req, ServletResponse res) { return null; }
        @Override public boolean isAsyncStarted() { return false; }
        @Override public boolean isAsyncSupported() { return false; }
        @Override public jakarta.servlet.AsyncContext getAsyncContext() { return null; }
        @Override public jakarta.servlet.DispatcherType getDispatcherType() { return null; }
        @Override public String getRequestId() { return null; }
        @Override public String getProtocolRequestId() { return null; }
        @Override public jakarta.servlet.ServletConnection getServletConnection() { return null; }
    }

    private static class MockServletResponse implements ServletResponse {
        private ByteArrayOutputStream output = new ByteArrayOutputStream();
        @Override public String getCharacterEncoding() { return "UTF-8"; }
        @Override public void setCharacterEncoding(String charset) { }
        @Override public String getContentType() { return null; }
        @Override public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override public void write(int b) { output.write(b); }
                @Override public boolean isReady() { return true; }
                @Override public void setWriteListener(jakarta.servlet.WriteListener listener) { }
            };
        }
        @Override public PrintWriter getWriter() { return new PrintWriter(new StringWriter()); }
        @Override public void setContentLength(int len) { }
        @Override public void setContentLengthLong(long len) { }
        @Override public void setContentType(String type) { }
        @Override public void setBufferSize(int size) { }
        @Override public int getBufferSize() { return 0; }
        @Override public void flushBuffer() { }
        @Override public void resetBuffer() { }
        @Override public boolean isCommitted() { return false; }
        @Override public void reset() { }
        @Override public void setLocale(java.util.Locale loc) { }
        @Override public java.util.Locale getLocale() { return null; }
    }

    private static class MockHttpServletRequest extends MockServletRequest implements HttpServletRequest {
        private String requestURI;
        private String servletPath;
        private String pathTranslated;
        private StringBuffer requestURL = new StringBuffer();

        public void setRequestURI(String uri) {
            this.requestURI = uri;
            this.requestURL = new StringBuffer("http://localhost" + uri);
        }

        public void setServletPath(String path) {
            this.servletPath = path;
            this.pathTranslated = "/var/www" + path;
        }

        @Override public String getAuthType() { return null; }
        @Override public jakarta.servlet.http.Cookie[] getCookies() { return null; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public java.util.Enumeration getHeaders(String name) { return null; }
        @Override public java.util.Enumeration getHeaderNames() { return null; }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getMethod() { return "GET"; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return pathTranslated; }
        @Override public String getContextPath() { return ""; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteUser() { return null; }
        @Override public boolean isUserInRole(String role) { return false; }
        @Override public java.security.Principal getUserPrincipal() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return requestURI; }
        @Override public StringBuffer getRequestURL() { return requestURL; }
        @Override public String getServletPath() { return servletPath; }
        @Override public jakarta.servlet.http.HttpSession getSession(boolean create) { return null; }
        @Override public jakarta.servlet.http.HttpSession getSession() { return null; }
        @Override public String changeSessionId() { return null; }
        @Override public boolean isRequestedSessionIdValid() { return false; }
        @Override public boolean isRequestedSessionIdFromCookie() { return false; }
        @Override public boolean isRequestedSessionIdFromURL() { return false; }
        @Override public boolean authenticate(HttpServletResponse response) { return false; }
        @Override public void login(String username, String password) { }
        @Override public void logout() { }
        @Override public java.util.Collection<jakarta.servlet.http.Part> getParts() { return null; }
        @Override public jakarta.servlet.http.Part getPart(String name) { return null; }
        @Override public <T extends jakarta.servlet.http.HttpUpgradeHandler> T upgrade(Class<T> handlerClass) { return null; }
    }

    private static class MockHttpServletResponse extends MockServletResponse implements HttpServletResponse {
        private int status = SC_OK;
        private String contentType;
        private int contentLength;
        private ByteArrayOutputStream output = new ByteArrayOutputStream();

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override public void write(int b) { output.write(b); }
                @Override public boolean isReady() { return true; }
                @Override public void setWriteListener(jakarta.servlet.WriteListener listener) { }
            };
        }

        public byte[] getOutputBytes() {
            return output.toByteArray();
        }

        @Override public void addCookie(jakarta.servlet.http.Cookie cookie) { }
        @Override public boolean containsHeader(String name) { return false; }
        @Override public String encodeURL(String url) { return url; }
        @Override public String encodeRedirectURL(String url) { return url; }
        @Override public void sendError(int sc, String msg) { this.status = sc; }
        @Override public void sendError(int sc) { this.status = sc; }
        @Override public void sendRedirect(String location) { }
        @Override public void sendRedirect(String location, int sc, boolean clearBuffer) { }
        @Override public void setDateHeader(String name, long date) { }
        @Override public void addDateHeader(String name, long date) { }
        @Override public void setHeader(String name, String value) { }
        @Override public void addHeader(String name, String value) { }
        @Override public void setIntHeader(String name, int value) { }
        @Override public void addIntHeader(String name, int value) { }
        @Override public void setStatus(int sc) { this.status = sc; }
        @Override public int getStatus() { return this.status; }
        @Override public String getHeader(String name) { return null; }
        @Override public java.util.Collection<String> getHeaders(String name) { return java.util.Collections.emptyList(); }
        @Override public java.util.Collection<String> getHeaderNames() { return java.util.Collections.emptyList(); }
        @Override public void setContentType(String type) { this.contentType = type; }
        @Override public void setContentLength(int len) { this.contentLength = len; }
    }

    private static class MockFilterChain implements FilterChain {
        private final String responseContent;
        private final int responseStatus;
        private boolean called = false;
        private String lastRequestURI;

        public MockFilterChain(String responseContent) {
            this(responseContent, HttpServletResponse.SC_OK);
        }

        public MockFilterChain(String responseContent, int responseStatus) {
            this.responseContent = responseContent;
            this.responseStatus = responseStatus;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response)
                throws IOException, ServletException {
            called = true;

            if (request instanceof HttpServletRequest) {
                lastRequestURI = ((HttpServletRequest) request).getRequestURI();
            }

            if (response instanceof HttpServletResponse) {
                ((HttpServletResponse) response).setStatus(responseStatus);
            }

            ServletOutputStream out = response.getOutputStream();
            out.write(responseContent.getBytes("UTF-8"));
        }

        public boolean wasCalled() {
            return called;
        }

        public String getLastRequestURI() {
            return lastRequestURI;
        }
    }
}
