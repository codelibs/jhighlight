package org.codelibs.jhighlight.renderer;

import static org.codelibs.jhighlight.renderer.XhtmlRendererFactory.CPP;
import static org.codelibs.jhighlight.renderer.XhtmlRendererFactory.GROOVY;
import static org.codelibs.jhighlight.renderer.XhtmlRendererFactory.JAVA;
import static org.codelibs.jhighlight.renderer.XhtmlRendererFactory.HTML;
import static org.codelibs.jhighlight.renderer.XhtmlRendererFactory.XML;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class XhtmlRendererFactoryTest {

    @Test
    public void testGetRenderer_Java() {
        Renderer renderer = XhtmlRendererFactory.getRenderer(JAVA);
        assertNotNull("Java renderer should not be null", renderer);
        assertTrue("Should be JavaXhtmlRenderer instance",
                   renderer instanceof JavaXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_Cpp() {
        Renderer renderer = XhtmlRendererFactory.getRenderer(CPP);
        assertNotNull("C++ renderer should not be null", renderer);
        assertTrue("Should be CppXhtmlRenderer instance",
                   renderer instanceof CppXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_Groovy() {
        Renderer renderer = XhtmlRendererFactory.getRenderer(GROOVY);
        assertNotNull("Groovy renderer should not be null", renderer);
        assertTrue("Should be GroovyXhtmlRenderer instance",
                   renderer instanceof GroovyXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_Html() {
        Renderer renderer = XhtmlRendererFactory.getRenderer(HTML);
        assertNotNull("HTML renderer should not be null", renderer);
        assertTrue("Should be XmlXhtmlRenderer instance",
                   renderer instanceof XmlXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_Xml() {
        Renderer renderer = XhtmlRendererFactory.getRenderer(XML);
        assertNotNull("XML renderer should not be null", renderer);
        assertTrue("Should be XmlXhtmlRenderer instance",
                   renderer instanceof XmlXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_ByExtension_Java() {
        Renderer renderer = XhtmlRendererFactory.getRenderer("test.java");
        assertNotNull(renderer);
        assertTrue(renderer instanceof JavaXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_ByExtension_Groovy() {
        Renderer renderer = XhtmlRendererFactory.getRenderer("test.groovy");
        assertNotNull(renderer);
        assertTrue(renderer instanceof GroovyXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_ByExtension_Html() {
        Renderer renderer = XhtmlRendererFactory.getRenderer("test.html");
        assertNotNull(renderer);
        assertTrue(renderer instanceof XmlXhtmlRenderer);

        renderer = XhtmlRendererFactory.getRenderer("test.htm");
        assertNotNull(renderer);
        assertTrue(renderer instanceof XmlXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_ByExtension_Xml() {
        Renderer renderer = XhtmlRendererFactory.getRenderer("test.xml");
        assertNotNull(renderer);
        assertTrue(renderer instanceof XmlXhtmlRenderer);

        renderer = XhtmlRendererFactory.getRenderer("test.xhtml");
        assertNotNull(renderer);
        assertTrue(renderer instanceof XmlXhtmlRenderer);
    }

    @Test
    public void testGetRenderer_UnknownType() {
        Renderer renderer = XhtmlRendererFactory.getRenderer("UNKNOWN");
        assertNull("Unknown type should return null", renderer);
    }

    @Test
    public void rendererOnCpp() throws IOException {
        Renderer renderer = XhtmlRendererFactory.getRenderer(CPP);
        String in = "int value = 10;";
        String codeAsHtml = renderer.highlight("test.cpp", in, "utf-8", false);
        assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n"
                        + "                      \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n"
                        + "    <meta name=\"generator\" content=\"JHighlight v1.1 (http://jhighlight.dev.java.net)\" />\n"
                        + "    <title>test.cpp</title>\n"
                        + "    <link rel=\"Help\" href=\"http://jhighlight.dev.java.net\" />\n"
                        + "    <style type=\"text/css\">\n" + ".cpp_plain {\n"
                        + "color: rgb(0,0,0);\n" + "}\n"
                        + ".cpp_doxygen_comment {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247); font-style: italic;\n"
                        + "}\n" + ".cpp_comment {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247);\n"
                        + "}\n" + "code {\n"
                        + "color: rgb(0,0,0); font-family: monospace; font-size: 12px; white-space: nowrap;\n"
                        + "}\n" + ".cpp_operator {\n"
                        + "color: rgb(0,124,31);\n" + "}\n"
                        + ".cpp_doxygen_tag {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247); font-style: italic; font-weight: bold;\n"
                        + "}\n" + "h1 {\n"
                        + "font-family: sans-serif; font-size: 16pt; font-weight: bold; color: rgb(0,0,0); background: rgb(210,210,210); border: solid 1px black; padding: 5px; text-align: center;\n"
                        + "}\n" + ".cpp_literal {\n" + "color: rgb(188,0,0);\n"
                        + "}\n" + ".cpp_preproc {\n" + "color: purple;\n"
                        + "}\n" + ".cpp_keyword {\n"
                        + "color: rgb(0,0,0); font-weight: bold;\n" + "}\n"
                        + ".cpp_separator {\n" + "color: rgb(0,33,255);\n"
                        + "}\n" + ".cpp_type {\n" + "color: rgb(0,44,221);\n"
                        + "}\n" + "    </style>\n" + "</head>\n" + "<body>\n"
                        + "<h1>test.cpp</h1><code><span class=\"cpp_type\">int</span><span class=\"cpp_plain\">&nbsp;</span><span class=\"cpp_keyword\">value</span><span class=\"cpp_plain\">&nbsp;</span><span class=\"cpp_operator\">=</span><span class=\"cpp_plain\">&nbsp;</span><span class=\"cpp_literal\">10</span><span class=\"cpp_separator\">;</span><span class=\"cpp_plain\"></span><br />\n"
                        + "</code>\n" + "</body>\n" + "</html>\n",
                codeAsHtml);
    }

    @Test
    public void rendererOnGroovy() throws IOException {
        Renderer renderer = XhtmlRendererFactory.getRenderer(GROOVY);
        String in = "int value = 10;";
        String codeAsHtml = renderer.highlight("test.groovy", in, "utf-8",
                false);
        assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n"
                        + "                      \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n"
                        + "    <meta name=\"generator\" content=\"JHighlight v1.1 (http://jhighlight.dev.java.net)\" />\n"
                        + "    <title>test.groovy</title>\n"
                        + "    <link rel=\"Help\" href=\"http://jhighlight.dev.java.net\" />\n"
                        + "    <style type=\"text/css\">\n" + "code {\n"
                        + "color: rgb(0,0,0); font-family: monospace; font-size: 12px; white-space: nowrap;\n"
                        + "}\n" + ".java_plain {\n" + "color: rgb(0,0,0);\n"
                        + "}\n" + ".java_keyword {\n"
                        + "color: rgb(0,0,0); font-weight: bold;\n" + "}\n"
                        + ".java_javadoc_tag {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247); font-style: italic; font-weight: bold;\n"
                        + "}\n" + "h1 {\n"
                        + "font-family: sans-serif; font-size: 16pt; font-weight: bold; color: rgb(0,0,0); background: rgb(210,210,210); border: solid 1px black; padding: 5px; text-align: center;\n"
                        + "}\n" + ".java_type {\n" + "color: rgb(0,44,221);\n"
                        + "}\n" + ".java_literal {\n" + "color: rgb(188,0,0);\n"
                        + "}\n" + ".java_javadoc_comment {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247); font-style: italic;\n"
                        + "}\n" + ".java_operator {\n"
                        + "color: rgb(0,124,31);\n" + "}\n"
                        + ".java_separator {\n" + "color: rgb(0,33,255);\n"
                        + "}\n" + ".java_comment {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247);\n"
                        + "}\n" + "    </style>\n" + "</head>\n" + "<body>\n"
                        + "<h1>test.groovy</h1><code><span class=\"java_type\">int</span><span class=\"java_plain\">&nbsp;value&nbsp;</span><span class=\"java_operator\">=</span><span class=\"java_plain\">&nbsp;</span><span class=\"java_literal\">10</span><span class=\"java_separator\">;</span><span class=\"java_plain\"></span><br />\n"
                        + "</code>\n" + "</body>\n" + "</html>\n",
                codeAsHtml);
    }

    @Test
    public void rendererOnJava() throws IOException {
        Renderer renderer = XhtmlRendererFactory.getRenderer(JAVA);
        String in = "int value = 10;";
        String codeAsHtml = renderer.highlight("test.java", in, "utf-8", false);
        assertEquals(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n"
                        + "                      \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />\n"
                        + "    <meta name=\"generator\" content=\"JHighlight v1.1 (http://jhighlight.dev.java.net)\" />\n"
                        + "    <title>test.java</title>\n"
                        + "    <link rel=\"Help\" href=\"http://jhighlight.dev.java.net\" />\n"
                        + "    <style type=\"text/css\">\n" + "code {\n"
                        + "color: rgb(0,0,0); font-family: monospace; font-size: 12px; white-space: nowrap;\n"
                        + "}\n" + ".java_plain {\n" + "color: rgb(0,0,0);\n"
                        + "}\n" + ".java_keyword {\n"
                        + "color: rgb(0,0,0); font-weight: bold;\n" + "}\n"
                        + ".java_javadoc_tag {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247); font-style: italic; font-weight: bold;\n"
                        + "}\n" + "h1 {\n"
                        + "font-family: sans-serif; font-size: 16pt; font-weight: bold; color: rgb(0,0,0); background: rgb(210,210,210); border: solid 1px black; padding: 5px; text-align: center;\n"
                        + "}\n" + ".java_type {\n" + "color: rgb(0,44,221);\n"
                        + "}\n" + ".java_literal {\n" + "color: rgb(188,0,0);\n"
                        + "}\n" + ".java_javadoc_comment {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247); font-style: italic;\n"
                        + "}\n" + ".java_operator {\n"
                        + "color: rgb(0,124,31);\n" + "}\n"
                        + ".java_separator {\n" + "color: rgb(0,33,255);\n"
                        + "}\n" + ".java_comment {\n"
                        + "color: rgb(147,147,147); background-color: rgb(247,247,247);\n"
                        + "}\n" + "    </style>\n" + "</head>\n" + "<body>\n"
                        + "<h1>test.java</h1><code><span class=\"java_type\">int</span><span class=\"java_plain\">&nbsp;value&nbsp;</span><span class=\"java_operator\">=</span><span class=\"java_plain\">&nbsp;</span><span class=\"java_literal\">10</span><span class=\"java_separator\">;</span><span class=\"java_plain\"></span><br />\n"
                        + "</code>\n" + "</body>\n" + "</html>\n",
                codeAsHtml);
    }
}
