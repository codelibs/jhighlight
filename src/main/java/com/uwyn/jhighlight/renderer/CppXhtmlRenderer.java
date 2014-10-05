/*
 * Copyright 2006 Arnout Engelen <arnouten[remove] at bzzt dot net>
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: CppXhtmlRenderer.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.renderer;

import com.uwyn.jhighlight.highlighter.CppHighlighter;
import com.uwyn.jhighlight.highlighter.ExplicitStateHighlighter;
import com.uwyn.jhighlight.renderer.XhtmlRenderer;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates highlighted syntax in XHTML from Cpp source.
 *
 * @author Arnout Engelen (arnouten[remove] at bzzt dot net)
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 0$
 */
public class CppXhtmlRenderer extends XhtmlRenderer
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
			
			put(".cpp_plain",
				"color: rgb(0,0,0);");
			
			put(".cpp_keyword",
				"color: rgb(0,0,0); " +
				"font-weight: bold;");
			
			put(".cpp_type",
				"color: rgb(0,44,221);");
			
			put(".cpp_operator",
				"color: rgb(0,124,31);");
			
			put(".cpp_separator",
				"color: rgb(0,33,255);");
			
			put(".cpp_literal",
				"color: rgb(188,0,0);");
			
			put(".cpp_comment",
				"color: rgb(147,147,147); " +
				"background-color: rgb(247,247,247);");
			
			put(".cpp_doxygen_comment",
				"color: rgb(147,147,147); " +
				"background-color: rgb(247,247,247); " +
				"font-style: italic;");
			
			put(".cpp_doxygen_tag",
				"color: rgb(147,147,147); " +
				"background-color: rgb(247,247,247); " +
				"font-style: italic; " +
				"font-weight: bold;");
			
			put(".cpp_preproc",
				"color: purple;");
		}};
	
	protected Map getDefaultCssStyles()
	{
		return DEFAULT_CSS;
	}
		
	protected String getCssClass(int style)
	{
		switch (style)
		{
			case CppHighlighter.PLAIN_STYLE:
				return "cpp_plain";
			case CppHighlighter.KEYWORD_STYLE:
				return "cpp_keyword";
			case CppHighlighter.TYPE_STYLE:
				return "cpp_type";
			case CppHighlighter.OPERATOR_STYLE:
				return "cpp_operator";
			case CppHighlighter.SEPARATOR_STYLE:
				return "cpp_separator";
			case CppHighlighter.LITERAL_STYLE:
				return "cpp_literal";
			case CppHighlighter.CPP_COMMENT_STYLE:
				return "cpp_comment";
			case CppHighlighter.DOXYGEN_COMMENT_STYLE:
				return "cpp_doxygen_comment";
			case CppHighlighter.DOXYGEN_TAG_STYLE:
				return "cpp_doxygen_tag";
			case CppHighlighter.PREPROC_STYLE:
				return "cpp_preproc";
		}
		
		return null;
	}
	
	protected ExplicitStateHighlighter getHighlighter()
	{
		return new CppHighlighter();
	}
}

