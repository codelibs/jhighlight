/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: XhtmlRenderer.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.renderer;

import java.io.*;

import com.uwyn.jhighlight.JHighlightVersion;
import com.uwyn.jhighlight.highlighter.ExplicitStateHighlighter;
import com.uwyn.jhighlight.tools.ExceptionUtils;
import com.uwyn.jhighlight.tools.StringUtils;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Provides an abstract base class to perform source code to XHTML syntax
 * highlighting.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public abstract class XhtmlRenderer implements Renderer
{
	/**
	 * Transforms source code that's provided through an
	 * <code>InputStream</code> to highlighted syntax in XHTML and writes it
	 * back to an <code>OutputStream</code>.
	 * <p>If the highlighting has to become a fragment, no CSS styles will be
	 * generated.
	 * <p>For complete documents, there's a collection of default styles that
	 * will be included. It's possible to override these by changing the
	 * provided <code>jhighlight.properties</code> file. It's best to look at
	 * this file in the JHighlight archive and modify the styles that are
	 * there already.
	 *
	 * @param name The name of the source file.
	 * @param in The input stream that provides the source code that needs to
	 * be transformed.
	 * @param out The output stream to which to resulting XHTML should be
	 * written.
	 * @param encoding The encoding that will be used to read and write the
	 * text.
	 * @param fragment <code>true</code> if the generated XHTML should be a
	 * fragment; or <code>false</code> if it should be a complete page
	 * @see #highlight(String, String, String, boolean)
	 * @since 1.0
	 */
	public void highlight(String name, InputStream in, OutputStream out, String encoding, boolean fragment)
	throws IOException
	{
		ExplicitStateHighlighter highlighter = getHighlighter();
		
		Reader isr;
		Writer osw;
		if (null == encoding)
		{
			isr = new InputStreamReader(in);
			osw = new OutputStreamWriter(out);
		}
		else
		{
			isr = new InputStreamReader(in, encoding);
			osw = new OutputStreamWriter(out, encoding);
		}
		
		BufferedReader r = new BufferedReader(isr);
		BufferedWriter w = new BufferedWriter(osw);
		
		if (fragment)
		{
			w.write(getXhtmlHeaderFragment(name));
		}
		else
		{
			w.write(getXhtmlHeader(name));
		}
		
		String line;
		String token;
		int length;
		int style;
		String css_class;
		int previous_style = 0;
		boolean newline = false;
		while ((line = r.readLine()) != null)
		{
			line += "\n";
			line = StringUtils.convertTabsToSpaces(line, 4);
			
			// should be optimized by reusing a custom LineReader class
			Reader lineReader = new StringReader(line);
			highlighter.setReader(lineReader);
			int index = 0;
			while (index < line.length())
			{
				style = highlighter.getNextToken();
				length = highlighter.getTokenLength();
				token = line.substring(index, index + length);
				
				if (style != previous_style ||
					newline)
				{
					css_class = getCssClass(style);
					
					if (css_class != null)
					{
						if (previous_style != 0 && !newline)
						{
							w.write("</span>");
						}
						w.write("<span class=\"" + css_class + "\">");
						
						previous_style = style;
					}
				}
				newline = false;					
				w.write(StringUtils.replace(StringUtils.encodeHtml(StringUtils.replace(token, "\n", "")), " ", "&nbsp;"));
				
				index += length;
			}
			
			w.write("</span><br />\n");
			newline = true;
		}
		
		if (!fragment) w.write(getXhtmlFooter());
		
		w.flush();
		w.close();
	}
	
	/**
	 * Transforms source code that's provided through a
	 * <code>String</code> to highlighted syntax in XHTML and returns it
	 * as a <code>String</code>.
	 * <p>If the highlighting has to become a fragment, no CSS styles will be
	 * generated.
	 *
	 * @param name The name of the source file.
	 * @param in The input string that provides the source code that needs to
	 * be transformed.
	 * @param encoding The encoding that will be used to read and write the
	 * text.
	 * @param fragment <code>true</code> if the generated XHTML should be a
	 * fragment; or <code>false</code> if it should be a complete page
	 * or <code>false</code> if it should be a complete document
	 * @return the highlighted source code as XHTML in a string
	 * @see #highlight(String, InputStream, OutputStream, String, boolean)
	 * @since 1.0
	 */
	public String highlight(String name, String in, String encoding, boolean fragment)
	throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		highlight(name, new StringBufferInputStream(in), out, encoding, fragment);
		return out.toString(encoding);
	}
		
	/**
	 * Returns a map of all the CSS styles that the renderer requires,
	 * together with default definitions for them.
	 *
	 * @return The map of CSS styles.
	 * @since 1.0
	 */
	protected abstract Map getDefaultCssStyles();
	
	/**
	 * Looks up the CSS class identifier that corresponds to the syntax style.
	 *
	 * @param style The syntax style.
	 * @return The requested CSS class identifier; or
	 * <p><code>null</code> if the syntax style isn't supported.
	 * @since 1.0
	 */
	protected abstract String getCssClass(int style);
	
	/**
	 * Returns the language-specific highlighting lexer that should be used
	 *
	 * @return The requested highlighting lexer.
	 * @since 1.0
	 */
	protected abstract ExplicitStateHighlighter getHighlighter();
	
	/**
	 * Returns all the CSS class definitions that should appear within the
	 * <code>style</code> XHTML tag.
	 * <p>This should support all the classes that the
	 * <code>getCssClass(int)</code> method returns.
	 *
	 * @return The CSS class definitions
	 * @see #getCssClass(int)
	 * @since 1.0
	 */
	protected String getCssClassDefinitions()
	{
		StringBuffer css = new StringBuffer();
		
		Properties properties = new Properties();
		
		URL jhighlighter_props = getClass().getClassLoader().getResource("jhighlight.properties");
		if (jhighlighter_props != null)
		{
			try
			{
				URLConnection connection = jhighlighter_props.openConnection();
				connection.setUseCaches(false);
				InputStream is = connection.getInputStream();
				
				try
				{
					properties.load(is);
				}
				finally
				{
					is.close();
				}
			}
			catch (IOException e)
			{
				Logger.getLogger("com.uwyn.jhighlight").warning("Error while reading the '" + jhighlighter_props.toExternalForm() + "' resource, using default CSS styles.\n" + ExceptionUtils.getExceptionStackTrace(e));
			}
		}
		
		Iterator it = getDefaultCssStyles().entrySet().iterator();
		Map.Entry entry;
		while (it.hasNext())
		{
			entry = (Map.Entry)it.next();
			
			String key = (String)entry.getKey();
			
			css.append(key);
			css.append(" {\n");
			
			if (properties.containsKey(key))
			{
				css.append(properties.get(key));
			}
			else
			{
				css.append(entry.getValue());
			}
			
			css.append("\n}\n");
		}
		
		return css.toString();
	}
	
	/**
	 * Returns the XHTML header that preceedes the highlighted source code.
	 * <p>It will integrate the CSS class definitions and use the source's
	 * name to indicate in XHTML which file has been highlighted.
	 *
	 * @param name The name of the source file.
	 * @return The constructed XHTML header.
	 * @since 1.0
	 */
	protected String getXhtmlHeader(String name)
	{
		if (null == name)
		{
			name = "";
		}
		
		return
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
			"                      \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
			"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" +
			"<head>\n" +
			"    <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n" +
			"    <meta name=\"generator\" content=\"JHighlight v"+JHighlightVersion.getVersion()+" (http://jhighlight.dev.java.net)\" />\n" +
			"    <title>" + StringUtils.encodeHtml(name) + "</title>\n" +
			"    <link rel=\"Help\" href=\"http://jhighlight.dev.java.net\" />\n" +
			"    <style type=\"text/css\">\n" +
			getCssClassDefinitions() +
			"    </style>\n" +
			"</head>\n" +
			"<body>\n" +
			"<h1>" + StringUtils.encodeHtml(name) + "</h1>" +
			"<code>";
	}
	
	/**
	 * Returns the XHTML header that preceedes the highlighted source code for
	 * a fragment.
	 *
	 * @param name The name of the source file.
	 * @return The constructed XHTML header.
	 * @since 1.0
	 */
	protected String getXhtmlHeaderFragment(String name)
	{
		if (null == name)
		{
			name = "";
		}
		
		return "<!-- "+name+" : generated by JHighlight v"+JHighlightVersion.getVersion()+" (http://jhighlight.dev.java.net) -->\n";
	}
	
	/**
	 * Returns the XHTML footer that nicely finishes the file after the
	 * highlighted source code.
	 *
	 * @return The requested XHTML footer.
	 * @since 1.0
	 */
	protected String getXhtmlFooter()
	{
		return "</code>\n</body>\n</html>\n";
		
	}
}
