/*
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 */
package com.uwyn.jhighlight.renderer;

/** Backward-compatibility factory delegating to {@link org.codelibs.jhighlight.renderer.XhtmlRendererFactory}.
 *
 * @deprecated Use {@link org.codelibs.jhighlight.renderer.XhtmlRendererFactory} instead.
 */
@Deprecated // for backward compatibility
public class XhtmlRendererFactory {

    /** Creates a new {@link XhtmlRendererFactory}. */
    public XhtmlRendererFactory() {}

    /** Identifier for the GROOVY renderer type. */
    public static final String GROOVY = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.GROOVY;

    /** Identifier for the JAVA renderer type. */
    public static final String JAVA = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.JAVA;

    /** Identifier for the BEANSHELL renderer type. */
    public static final String BEANSHELL = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.BEANSHELL;

    /** Identifier for the BSH renderer type. */
    public static final String BSH = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.BSH;

    /** Identifier for the XML renderer type. */
    public static final String XML = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.XML;

    /** Identifier for the XHTML renderer type. */
    public static final String XHTML = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.XHTML;

    /** Identifier for the LZX renderer type. */
    public static final String LZX = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.LZX;

    /** Identifier for the HTML renderer type. */
    public static final String HTML = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.HTML;

    /** Identifier for the CPP renderer type. */
    public static final String CPP = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CPP;

    /** Identifier for the CXX renderer type. */
    public static final String CXX = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CXX;

    /** Identifier for the CPLUSPLUS renderer type. */
    public static final String CPLUSPLUS = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CPP;

    /** Returns a renderer for the given type.
     *
     * @param type the renderer type identifier.
     * @return a renderer for the given type.
     */
    public static Renderer getRenderer(String type) {
        return org.codelibs.jhighlight.renderer.XhtmlRendererFactory
                .getRenderer(type);
    }

}
