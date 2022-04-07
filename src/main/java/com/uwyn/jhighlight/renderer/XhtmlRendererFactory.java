/*
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 */
package com.uwyn.jhighlight.renderer;

@Deprecated // for backward compatibility
public class XhtmlRendererFactory {

    public static final String GROOVY = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.GROOVY;

    public static final String JAVA = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.JAVA;

    public static final String BEANSHELL = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.BEANSHELL;

    public static final String BSH = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.BSH;

    public static final String XML = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.XML;

    public static final String XHTML = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.XHTML;

    public static final String LZX = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.LZX;

    public static final String HTML = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.HTML;

    public static final String CPP = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CPP;

    public static final String CXX = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CXX;

    public static final String CPLUSPLUS = org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CPP;

    public static Renderer getRenderer(String type) {
        return org.codelibs.jhighlight.renderer.XhtmlRendererFactory
                .getRenderer(type);
    }

}
