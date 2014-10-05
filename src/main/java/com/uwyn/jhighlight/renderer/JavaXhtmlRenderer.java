/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: JavaXhtmlRenderer.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.renderer;

import com.uwyn.jhighlight.highlighter.ExplicitStateHighlighter;
import com.uwyn.jhighlight.highlighter.JavaHighlighter;
import com.uwyn.jhighlight.renderer.XhtmlRenderer;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates highlighted syntax in XHTML from Java source.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public class JavaXhtmlRenderer extends XhtmlRenderer
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
			case JavaHighlighter.PLAIN_STYLE:
				return "java_plain";
			case JavaHighlighter.KEYWORD_STYLE:
				return "java_keyword";
			case JavaHighlighter.TYPE_STYLE:
				return "java_type";
			case JavaHighlighter.OPERATOR_STYLE:
				return "java_operator";
			case JavaHighlighter.SEPARATOR_STYLE:
				return "java_separator";
			case JavaHighlighter.LITERAL_STYLE:
				return "java_literal";
			case JavaHighlighter.JAVA_COMMENT_STYLE:
				return "java_comment";
			case JavaHighlighter.JAVADOC_COMMENT_STYLE:
				return "java_javadoc_comment";
			case JavaHighlighter.JAVADOC_TAG_STYLE:
				return "java_javadoc_tag";
		}
		
		return null;
	}
	
	protected ExplicitStateHighlighter getHighlighter()
	{
		JavaHighlighter highlighter = new JavaHighlighter();
		highlighter.ASSERT_IS_KEYWORD = true;
		
		return highlighter;
	}
}

