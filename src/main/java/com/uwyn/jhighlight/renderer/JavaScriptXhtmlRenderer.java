/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>.
 * 					   Ulf Dittmer (ulf[remove] at ulfdittmer dot com)
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: JavaScriptXhtmlRenderer.java 3431 2006-08-02 04:09:28Z gbevin $
 */
package com.uwyn.jhighlight.renderer;

import com.uwyn.jhighlight.highlighter.ExplicitStateHighlighter;
import com.uwyn.jhighlight.highlighter.JavaScriptHighlighter;
import com.uwyn.jhighlight.renderer.XhtmlRenderer;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates highlighted syntax in XHTML from Java source.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @author Ulf Dittmer (ulf[remove] at ulfdittmer dot com)
 * @version $Revision: 3431 $
 * @since 1.1
 */
public class JavaScriptXhtmlRenderer extends XhtmlRenderer
{
	public final static HashMap DEFAULT_CSS = new HashMap() {{
			put("h1",
				"font-family: sans-serif; " +
				"font-size: 16pt; " +
				"font-weight: bold; " +
				"color: rgb(0,0,0); " +
				"background: rgb(210,210,210); " +
				"border: solid 1px black; " +
				"padding: 5px; " +
				"text-align: center;");
			
			put("code",
				"color: rgb(0,0,0); " +
				"font-family: monospace; " +
				"font-size: 12px; " +
				"white-space: nowrap;");
			
			put(".java_plain",
				"color: rgb(0,0,0);");
			
			put(".java_keyword",
				"color: rgb(0,0,0); " +
				"font-weight: bold;");
			
			put(".java_type",
				"color: rgb(0,44,221);");
			
			put(".java_operator",
				"color: rgb(0,124,31);");
			
			put(".java_separator",
				"color: rgb(0,33,255);");
			
			put(".java_literal",
				"color: rgb(188,0,0);");
			
			put(".java_comment",
				"color: rgb(147,147,147); " +
				"background-color: rgb(247,247,247);");
			
			put(".java_javadoc_comment",
				"color: rgb(147,147,147); " +
				"background-color: rgb(247,247,247); " +
				"font-style: italic;");
			
			put(".java_javadoc_tag",
				"color: rgb(147,147,147); " +
				"background-color: rgb(247,247,247); " +
				"font-style: italic; " +
				"font-weight: bold;");
		}};
	
	protected Map getDefaultCssStyles()
	{
		return DEFAULT_CSS;
	}
	
	protected String getCssClass(int style)
	{
		switch (style)
		{
			case JavaScriptHighlighter.PLAIN_STYLE:
				return "java_plain";
			case JavaScriptHighlighter.KEYWORD_STYLE:
				return "java_keyword";
			case JavaScriptHighlighter.OPERATOR_STYLE:
				return "java_operator";
			case JavaScriptHighlighter.SEPARATOR_STYLE:
				return "java_separator";
			case JavaScriptHighlighter.LITERAL_STYLE:
				return "java_literal";
			case JavaScriptHighlighter.JAVA_COMMENT_STYLE:
				return "java_comment";
		}
		
		return null;
	}
	
	protected ExplicitStateHighlighter getHighlighter()
	{
		return new JavaScriptHighlighter();
	}
}

