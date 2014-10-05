/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: GroovyXhtmlRenderer.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.renderer;

import com.uwyn.jhighlight.highlighter.ExplicitStateHighlighter;
import com.uwyn.jhighlight.highlighter.GroovyHighlighter;
import com.uwyn.jhighlight.renderer.XhtmlRenderer;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates highlighted syntax in XHTML from Groovy source.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public class GroovyXhtmlRenderer extends XhtmlRenderer
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
			case GroovyHighlighter.PLAIN_STYLE:
				return "java_plain";
			case GroovyHighlighter.KEYWORD_STYLE:
				return "java_keyword";
			case GroovyHighlighter.TYPE_STYLE:
				return "java_type";
			case GroovyHighlighter.OPERATOR_STYLE:
				return "java_operator";
			case GroovyHighlighter.SEPARATOR_STYLE:
				return "java_separator";
			case GroovyHighlighter.LITERAL_STYLE:
				return "java_literal";
			case GroovyHighlighter.JAVA_COMMENT_STYLE:
				return "java_comment";
			case GroovyHighlighter.JAVADOC_COMMENT_STYLE:
				return "java_javadoc_comment";
			case GroovyHighlighter.JAVADOC_TAG_STYLE:
				return "java_javadoc_tag";
		}
		
		return null;
	}
	
	protected ExplicitStateHighlighter getHighlighter()
	{
		return new GroovyHighlighter();
	}
}

