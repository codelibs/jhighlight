package org.codelibs.jhighlight.highlighter;

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
        byte token;
        while ((token = highlighter.getNextToken()) != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have at least one token", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaKeywords() throws IOException {
        String code = "public static void if else while for return";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have multiple tokens for keywords", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaMethod() throws IOException {
        String code = "public void testMethod() {\n" +
                      "    System.out.println(\"Hello\");\n" +
                      "}";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have multiple tokens", tokenCount > 5);
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
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for comments and code", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaStrings() throws IOException {
        String code = "String s = \"Hello World\";";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for string literal", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaNumbers() throws IOException {
        String code = "int a = 123; double b = 45.67; float c = 89.0f;";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for numbers", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaAnnotations() throws IOException {
        String code = "@Override\n" +
                      "@Test\n" +
                      "public void method() { }";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for annotations", tokenCount > 0);
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
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have many tokens for complex class", tokenCount > 20);
    }

    @Test
    public void testHighlightEmptyString() throws IOException {
        String code = "";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte token = highlighter.getNextToken();
        assertTrue("Empty string should produce no tokens", token == 0);
    }

    @Test
    public void testHighlightWhitespaceOnly() throws IOException {
        String code = "   \n\t\n  ";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
    }

    @Test
    public void testHighlightJavaGenerics() throws IOException {
        String code = "List<String> list = new ArrayList<>();";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for generics", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaLambda() throws IOException {
        String code = "list.forEach(item -> System.out.println(item));";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for lambda", tokenCount > 0);
    }
}
