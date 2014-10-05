/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: XhtmlRendererFactory.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.renderer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides a single point of entry to instantiate Xhtml renderers.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public abstract class XhtmlRendererFactory
{
	public final static String GROOVY = "groovy";
	public final static String JAVA = "java";
	public final static String BEANSHELL = "beanshell";
	public final static String BSH = "bsh";
	public final static String XML = "xml";
	public final static String XHTML = "xhtml";
	public final static String LZX = "lzx";
	public final static String HTML = "html";
	public final static String CPP = "cpp";
	public final static String CXX = "cxx";
	public final static String CPLUSPLUS = "c++";
	
	private final static Map RENDERERS_CLASSNAMES = new HashMap() {{
			put(GROOVY, GroovyXhtmlRenderer.class.getName());
			put(JAVA, JavaXhtmlRenderer.class.getName());
			put(BEANSHELL, JavaXhtmlRenderer.class.getName());
			put(BSH, JavaXhtmlRenderer.class.getName());
			put(XML, XmlXhtmlRenderer.class.getName());
			put(XHTML, XmlXhtmlRenderer.class.getName());
			put(LZX, XmlXhtmlRenderer.class.getName());
			put(HTML, XmlXhtmlRenderer.class.getName());
			put(CPP, CppXhtmlRenderer.class.getName());
			put(CXX, CppXhtmlRenderer.class.getName());
			put(CPLUSPLUS, CppXhtmlRenderer.class.getName());
		}};
	
	/**
	 * Instantiates an instance of a known <code>XhtmlRenderer</code> according to
	 * the type that's provided.
	 *
	 * @param type The type of renderer, look at the static variables of this
	 * class to see which ones are supported.
	 * @return an instance of the <code>XhtmlRenderer</code> that corresponds to the type; or
	 * <p><code>null</code> if the type wasn't known
	 * @since 1.0
	 */
	public static Renderer getRenderer(String type)
	{
		String classname = (String)RENDERERS_CLASSNAMES.get(type.toLowerCase());
		if (null == classname)
		{
			return null;
		}
		
		try
		{
			Class klass = Class.forName(classname);
			return (Renderer)klass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returned a set with all the supported XHTML renderer types.
	 *
	 * @return a <code>Set</code> with the supported XHTML renderer types as strings.
	 * @since 1.0
	 */
	public static Set getSupportedTypes()
	{
		return Collections.unmodifiableSet(RENDERERS_CLASSNAMES.keySet());
	}
}
