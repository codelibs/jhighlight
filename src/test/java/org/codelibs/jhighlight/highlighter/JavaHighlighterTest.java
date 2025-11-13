package org.codelibs.jhighlight.highlighter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class JavaHighlighterTest {

    private static final int MAX_TOKENS = 1000; // Prevent infinite loops

    @Test
    public void testHighlightSimpleJavaCode() throws IOException {
        String code = "public class Test { }";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have at least one token", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaKeywords() throws IOException {
        String code = "public static void if else while for return";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have multiple tokens for keywords", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaMethod() throws IOException {
        String code = "public void testMethod() {\n" +
                      "    System.out.println(\"Hello\");\n" +
                      "}";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have multiple tokens", tokenCount > 5);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaComments() throws IOException {
        String code = "// Single line comment\n" +
                      "/* Multi-line\n" +
                      "   comment */\n" +
                      "int x = 5;";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have tokens for comments and code", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaStrings() throws IOException {
        String code = "String s = \"Hello World\";";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have tokens for string literal", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaNumbers() throws IOException {
        String code = "int a = 123; double b = 45.67; float c = 89.0f;";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have tokens for numbers", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaAnnotations() throws IOException {
        String code = "@Override\n" +
                      "@Test\n" +
                      "public void method() { }";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have tokens for annotations", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightComplexJavaClass() throws IOException {
        String code = "package com.example;\n\n" +
                      "import java.util.List;\n\n" +
                      "/**\n" +
                      " * JavaDoc comment\n" +
                      " */\n" +
                      "public class Example {\n" +
                      "    private int value;\n\n" +
                      "    public Example(int value) {\n" +
                      "        this.value = value;\n" +
                      "    }\n\n" +
                      "    public int getValue() {\n" +
                      "        return value;\n" +
                      "    }\n" +
                      "}";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have many tokens for complex class", tokenCount > 20);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightEmptyString() throws IOException {
        String code = "";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        // Empty string means index is already at length, no tokens to consume
        int tokenCount = 0;
        int index = 0;
        while (index < code.length() && tokenCount < MAX_TOKENS) {
            highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            index += length;
            tokenCount++;
        }
        assertEquals("Empty string should produce no tokens", 0, tokenCount);
    }

    @Test
    public void testHighlightWhitespaceOnly() throws IOException {
        String code = "   \n\t\n  ";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaGenerics() throws IOException {
        String code = "List<String> list = new ArrayList<>();";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have tokens for generics", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }

    @Test
    public void testHighlightJavaLambda() throws IOException {
        String code = "list.forEach(item -> System.out.println(item));";
        JavaHighlighter highlighter = new JavaHighlighter();
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
        assertTrue("Should have tokens for lambda", tokenCount > 0);
        assertTrue("Should consume entire input", index == code.length());
    }
}
