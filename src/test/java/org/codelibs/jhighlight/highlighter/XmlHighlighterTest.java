package org.codelibs.jhighlight.highlighter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class XmlHighlighterTest {

    private static final int MAX_TOKENS = 1000; // Prevent infinite loops

    @Test
    public void testHighlightSimpleXml() throws IOException {
        String code = "<root></root>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have multiple tokens", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightXmlWithAttributes() throws IOException {
        String code = "<element attr=\"value\" id=\"test\"></element>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for attributes", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightXmlWithContent() throws IOException {
        String code = "<message>Hello World</message>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for content", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightXmlWithComment() throws IOException {
        String code = "<!-- This is a comment -->\n" +
                      "<element>content</element>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for comments", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightXmlDeclaration() throws IOException {
        String code = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "<root></root>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for declaration", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightXmlCDATA() throws IOException {
        String code = "<script><![CDATA[\n" +
                      "function test() {\n" +
                      "    return true;\n" +
                      "}\n" +
                      "]]></script>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for CDATA", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightNestedXml() throws IOException {
        String code = "<parent>\n" +
                      "    <child attr=\"value\">\n" +
                      "        <grandchild>text</grandchild>\n" +
                      "    </child>\n" +
                      "</parent>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for nested elements", tokenCount > 5);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightSelfClosingTag() throws IOException {
        String code = "<element attr=\"value\" />";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have tokens for self-closing tag", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightHtml() throws IOException {
        String code = "<!DOCTYPE html>\n" +
                      "<html>\n" +
                      "<head>\n" +
                      "    <title>Test Page</title>\n" +
                      "</head>\n" +
                      "<body>\n" +
                      "    <h1>Hello World</h1>\n" +
                      "    <p>This is a paragraph.</p>\n" +
                      "</body>\n" +
                      "</html>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have many tokens for HTML", tokenCount > 10);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightComplexXml() throws IOException {
        String code = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                      "<!-- Configuration file -->\n" +
                      "<configuration>\n" +
                      "    <database>\n" +
                      "        <host>localhost</host>\n" +
                      "        <port>3306</port>\n" +
                      "        <name>testdb</name>\n" +
                      "    </database>\n" +
                      "    <settings>\n" +
                      "        <setting key=\"timeout\" value=\"30\" />\n" +
                      "        <setting key=\"retries\" value=\"3\" />\n" +
                      "    </settings>\n" +
                      "</configuration>";
        XmlHighlighter highlighter = new XmlHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            assertTrue("Token length should be positive", length > 0);
            index += length;
            tokenCount++;
        }
        assertTrue("Should have many tokens for complex XML", tokenCount > 20);
        assertTrue("Should consume entire input", index == code.length());
    }
}
