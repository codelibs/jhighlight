package org.codelibs.jhighlight.renderer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

/**
 * Integration tests for all language renderers
 */
public class RendererIntegrationTest {

    @Test
    public void testCppRenderer_Fragment() throws IOException {
        String code = "#include <iostream>\n" +
                      "int main() {\n" +
                      "    std::cout << \"Hello World\" << std::endl;\n" +
                      "    return 0;\n" +
                      "}";
        CppXhtmlRenderer renderer = new CppXhtmlRenderer();

        String result = renderer.highlight("test.cpp", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain span elements", result.contains("<span"));
        assertTrue("Should contain code content", result.length() > 0);
    }

    @Test
    public void testCppRenderer_FullDocument() throws IOException {
        String code = "int main() { return 0; }";
        CppXhtmlRenderer renderer = new CppXhtmlRenderer();

        String result = renderer.highlight("test.cpp", code, "UTF-8", false);

        assertNotNull(result);
        assertTrue("Should contain DOCTYPE", result.contains("DOCTYPE"));
        assertTrue("Should contain html tag", result.contains("<html"));
    }

    @Test
    public void testGroovyRenderer_Fragment() throws IOException {
        String code = "class Person {\n" +
                      "    String name\n" +
                      "    def greet() {\n" +
                      "        return 'Hello'\n" +
                      "    }\n" +
                      "}";
        GroovyXhtmlRenderer renderer = new GroovyXhtmlRenderer();

        String result = renderer.highlight("test.groovy", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain span elements", result.contains("<span"));
        assertTrue("Should contain code content", result.length() > 0);
    }

    @Test
    public void testGroovyRenderer_Simple() throws IOException {
        String code = "def x = 5\n" +
                      "println x";
        GroovyXhtmlRenderer renderer = new GroovyXhtmlRenderer();

        String result = renderer.highlight("test.groovy", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Result should not be empty", result.length() > 0);
    }

    @Test
    public void testJavaScriptRenderer_Fragment() throws IOException {
        String code = "function greet(name) {\n" +
                      "    return 'Hello, ' + name;\n" +
                      "}";
        JavaScriptXhtmlRenderer renderer = new JavaScriptXhtmlRenderer();

        String result = renderer.highlight("test.js", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain span elements", result.contains("<span"));
        assertTrue("Should contain code content", result.length() > 0);
    }

    @Test
    public void testJavaScriptRenderer_Traditional() throws IOException {
        String code = "function add(a, b) { return a + b; }\n" +
                      "function Person(name) {\n" +
                      "    this.name = name;\n" +
                      "    this.greet = function() {\n" +
                      "        return 'Hello';\n" +
                      "    };\n" +
                      "}";
        JavaScriptXhtmlRenderer renderer = new JavaScriptXhtmlRenderer();

        String result = renderer.highlight("test.js", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Result should not be empty", result.length() > 0);
    }

    @Test
    public void testXmlRenderer_Fragment() throws IOException {
        String code = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "<root>\n" +
                      "    <element attr=\"value\">content</element>\n" +
                      "</root>";
        XmlXhtmlRenderer renderer = new XmlXhtmlRenderer();

        String result = renderer.highlight("test.xml", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain span elements", result.contains("<span"));
        assertTrue("Should contain code content", result.length() > 0);
    }

    @Test
    public void testXmlRenderer_HtmlDocument() throws IOException {
        String code = "<!DOCTYPE html>\n" +
                      "<html>\n" +
                      "<head><title>Test</title></head>\n" +
                      "<body>\n" +
                      "    <h1>Hello World</h1>\n" +
                      "</body>\n" +
                      "</html>";
        XmlXhtmlRenderer renderer = new XmlXhtmlRenderer();

        String result = renderer.highlight("test.html", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain code content", result.length() > 0);
    }

    @Test
    public void testXmlRenderer_WithCDATA() throws IOException {
        String code = "<script><![CDATA[\n" +
                      "function test() { return true; }\n" +
                      "]]></script>";
        XmlXhtmlRenderer renderer = new XmlXhtmlRenderer();

        String result = renderer.highlight("test.xml", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Result should not be empty", result.length() > 0);
    }

    @Test
    public void testAllRenderers_HandleEmptyInput() throws IOException {
        String emptyCode = "";

        // Test all renderers with empty input
        Renderer[] renderers = new Renderer[] {
            new JavaXhtmlRenderer(),
            new CppXhtmlRenderer(),
            new GroovyXhtmlRenderer(),
            new JavaScriptXhtmlRenderer(),
            new XmlXhtmlRenderer()
        };

        String[] names = new String[] {
            "test.java", "test.cpp", "test.groovy", "test.js", "test.xml"
        };

        for (int i = 0; i < renderers.length; i++) {
            String result = renderers[i].highlight(names[i], emptyCode, "UTF-8", true);
            assertNotNull("Renderer " + i + " should handle empty input", result);
        }
    }

    @Test
    public void testAllRenderers_HandleWhitespace() throws IOException {
        String whitespace = "   \n\t\n  ";

        Renderer[] renderers = new Renderer[] {
            new JavaXhtmlRenderer(),
            new CppXhtmlRenderer(),
            new GroovyXhtmlRenderer(),
            new JavaScriptXhtmlRenderer(),
            new XmlXhtmlRenderer()
        };

        String[] names = new String[] {
            "test.java", "test.cpp", "test.groovy", "test.js", "test.xml"
        };

        for (int i = 0; i < renderers.length; i++) {
            String result = renderers[i].highlight(names[i], whitespace, "UTF-8", true);
            assertNotNull("Renderer " + i + " should handle whitespace", result);
        }
    }

    @Test
    public void testAllRenderers_HandleSpecialCharacters() throws IOException {
        String specialChars = "<>&\"'";

        Renderer[] renderers = new Renderer[] {
            new JavaXhtmlRenderer(),
            new CppXhtmlRenderer(),
            new GroovyXhtmlRenderer(),
            new JavaScriptXhtmlRenderer(),
            new XmlXhtmlRenderer()
        };

        String[] names = new String[] {
            "test.java", "test.cpp", "test.groovy", "test.js", "test.xml"
        };

        for (int i = 0; i < renderers.length; i++) {
            String result = renderers[i].highlight(names[i], specialChars, "UTF-8", true);
            assertNotNull("Renderer " + i + " should handle special characters", result);
            // Special characters should be encoded in HTML
            assertTrue("Should encode special characters",
                       result.contains("&lt;") || result.contains("&gt;") ||
                       result.contains("&amp;") || result.contains("&quot;"));
        }
    }

    @Test
    public void testAllRenderers_FragmentVsFullDocument() throws IOException {
        String code = "test";

        Renderer[] renderers = new Renderer[] {
            new JavaXhtmlRenderer(),
            new CppXhtmlRenderer(),
            new GroovyXhtmlRenderer(),
            new JavaScriptXhtmlRenderer(),
            new XmlXhtmlRenderer()
        };

        String[] names = new String[] {
            "test.java", "test.cpp", "test.groovy", "test.js", "test.xml"
        };

        for (int i = 0; i < renderers.length; i++) {
            String fragment = renderers[i].highlight(names[i], code, "UTF-8", true);
            String fullDoc = renderers[i].highlight(names[i], code, "UTF-8", false);

            assertNotNull("Fragment should not be null", fragment);
            assertNotNull("Full document should not be null", fullDoc);

            // Full document should be longer (contains HTML structure)
            assertTrue("Full document should be longer than fragment for renderer " + i,
                       fullDoc.length() > fragment.length());

            // Full document should contain HTML structure
            assertTrue("Full document should contain DOCTYPE for renderer " + i,
                       fullDoc.contains("DOCTYPE"));
        }
    }
}
