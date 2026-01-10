package org.codelibs.jhighlight.renderer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * Tests for multi-byte character (UTF-8, CJK) handling in renderers.
 * This is a critical feature as the project specifically fixed multi-byte character issues.
 */
public class RendererEncodingTest {

    @Test
    public void testHighlightUTF8ChineseCharacters() throws IOException {
        String code = "public class Test {\n" +
                      "    // Chinese comment: \u4e2d\u6587\u6ce8\u91ca\n" +
                      "    String message = \"\u4f60\u597d\u4e16\u754c\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain Chinese characters", result.contains("\u4e2d\u6587"));
        assertTrue("Should contain HTML structure", result.contains("<html"));
    }

    @Test
    public void testHighlightUTF8JapaneseCharacters() throws IOException {
        String code = "public class Test {\n" +
                      "    // Japanese comment: \u65e5\u672c\u8a9e\u30b3\u30e1\u30f3\u30c8\n" +
                      "    String greeting = \"\u3053\u3093\u306b\u3061\u306f\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain Japanese Hiragana", result.contains("\u3053\u3093\u306b\u3061\u306f"));
        assertTrue("Should contain Japanese Katakana", result.contains("\u30b3\u30e1\u30f3\u30c8"));
    }

    @Test
    public void testHighlightUTF8KoreanCharacters() throws IOException {
        String code = "public class Test {\n" +
                      "    // Korean comment: \ud55c\uad6d\uc5b4 \uc8fc\uc11d\n" +
                      "    String message = \"\uc548\ub155\ud558\uc138\uc694\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain Korean characters", result.contains("\ud55c\uad6d\uc5b4"));
    }

    @Test
    public void testHighlightUTF8EmojisInComments() throws IOException {
        String code = "public class Test {\n" +
                      "    // Emoji comment: \ud83d\ude00 \ud83d\udc4d \u2764\n" +
                      "    String emoji = \"\ud83c\udf89\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        // Just verify it processes without throwing exceptions
        assertTrue("Should contain code element", result.contains("<code"));
    }

    @Test
    public void testHighlightMixedEncodingContent() throws IOException {
        String code = "public class MultiLang {\n" +
                      "    // English: Hello World\n" +
                      "    // Chinese: \u4f60\u597d\n" +
                      "    // Japanese: \u3053\u3093\u306b\u3061\u306f\n" +
                      "    // Korean: \uc548\ub155\n" +
                      "    // German: Gr\u00fc\u00df Gott\n" +
                      "    // French: Bonjour \u00e0 tous\n" +
                      "    String mixed = \"Hello \u4e16\u754c \u4e16\u754c\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("MultiLang.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain German umlaut", result.contains("\u00fc") || result.contains("&uuml;"));
    }

    @Test
    public void testHighlightXmlWithUTF8() throws IOException {
        String code = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "<root>\n" +
                      "    <message lang=\"zh\">\u4f60\u597d</message>\n" +
                      "    <message lang=\"ja\">\u3053\u3093\u306b\u3061\u306f</message>\n" +
                      "    <message lang=\"ko\">\uc548\ub155</message>\n" +
                      "</root>";

        XmlXhtmlRenderer renderer = new XmlXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("config.xml", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should process XML with UTF-8", result.contains("<html"));
    }

    @Test
    public void testHighlightGroovyWithUTF8() throws IOException {
        String code = "class Test {\n" +
                      "    // Groovy with Chinese: \u4e2d\u6587\n" +
                      "    def greet() { '\u4f60\u597d' }\n" +
                      "}";

        GroovyXhtmlRenderer renderer = new GroovyXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.groovy", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
    }

    @Test
    public void testHighlightCppWithUTF8() throws IOException {
        String code = "#include <iostream>\n" +
                      "// C++ with Japanese: \u65e5\u672c\u8a9e\n" +
                      "int main() {\n" +
                      "    std::cout << \"\u3053\u3093\u306b\u3061\u306f\" << std::endl;\n" +
                      "    return 0;\n" +
                      "}";

        CppXhtmlRenderer renderer = new CppXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("main.cpp", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
    }

    @Test
    public void testHighlightJavaScriptWithUTF8() throws IOException {
        String code = "// JavaScript with Korean: \ud55c\uad6d\uc5b4\n" +
                      "function greet() {\n" +
                      "    return '\uc548\ub155\ud558\uc138\uc694';\n" +
                      "}";

        JavaScriptXhtmlRenderer renderer = new JavaScriptXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("script.js", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
    }

    @Test
    public void testHighlightWithNullEncoding() throws IOException {
        String code = "public class Test { }";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Null encoding should default to system default
        renderer.highlight("Test.java", input, output, null, false);

        String result = output.toString();
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain HTML", result.contains("<html"));
    }

    @Test
    public void testHighlightFragmentWithUTF8() throws IOException {
        String code = "public class Test {\n" +
                      "    String msg = \"\u4f60\u597d\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // Test fragment mode with UTF-8
        renderer.highlight("Test.java", input, output, "UTF-8", true);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain code element", result.contains("<code"));
    }

    @Test
    public void testHighlightLongUnicodeString() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("public class LongUnicode {\n");
        sb.append("    String text = \"");
        // Add a long string of Chinese characters
        for (int i = 0; i < 100; i++) {
            sb.append("\u4e2d\u6587");
        }
        sb.append("\";\n}");

        String code = sb.toString();

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("LongUnicode.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should handle long unicode strings", result.length() > 0);
    }

    @Test
    public void testHighlightSpecialHTMLCharactersWithUTF8() throws IOException {
        String code = "public class Test {\n" +
                      "    // Special chars: < > & \" '\n" +
                      "    String html = \"<div>&amp;</div>\";\n" +
                      "    String chinese = \"\u4e2d\u6587\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        // HTML special chars should be escaped
        assertTrue("Should escape < to &lt;", result.contains("&lt;"));
        assertTrue("Should escape > to &gt;", result.contains("&gt;"));
    }

    @Test
    public void testHighlightUnicodeIdentifiers() throws IOException {
        // Java supports Unicode identifiers
        String code = "public class Test {\n" +
                      "    int \u5f00\u59cb = 0; // Chinese identifier meaning 'start'\n" +
                      "    String \u540d\u524d = \"test\"; // Chinese identifier meaning 'name'\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "UTF-8", false);

        String result = output.toString("UTF-8");
        assertNotNull("Result should not be null", result);
        assertTrue("Should contain Chinese identifiers",
                   result.contains("\u5f00\u59cb") || result.contains("&#"));
    }

    @Test
    public void testHighlightWithISO8859Encoding() throws IOException {
        // Test with ISO-8859-1 encoding for European characters
        String code = "public class Test {\n" +
                      "    // German: Gr\u00fc\u00df Gott\n" +
                      "    // French: Caf\u00e9\n" +
                      "    String text = \"\u00e9\u00e8\u00ea\u00eb\";\n" +
                      "}";

        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();
        ByteArrayInputStream input = new ByteArrayInputStream(code.getBytes("ISO-8859-1"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        renderer.highlight("Test.java", input, output, "ISO-8859-1", false);

        String result = output.toString("ISO-8859-1");
        assertNotNull("Result should not be null", result);
    }
}
