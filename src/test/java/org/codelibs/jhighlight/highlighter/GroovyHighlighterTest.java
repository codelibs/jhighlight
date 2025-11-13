package org.codelibs.jhighlight.highlighter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class GroovyHighlighterTest {

    private static final int MAX_TOKENS = 1000; // Prevent infinite loops

    @Test
    public void testHighlightSimpleGroovyCode() throws IOException {
        String code = "def message = 'Hello World'";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have multiple tokens", tokenCount > 0);
    }

    @Test
    public void testHighlightGroovyKeywords() throws IOException {
        String code = "def class interface trait as in";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for keywords", tokenCount > 0);
    }

    @Test
    public void testHighlightGroovyClosure() throws IOException {
        String code = "list.each { item ->\n" +
                      "    println item\n" +
                      "}";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for closure", tokenCount > 0);
    }

    @Test
    public void testHighlightGroovyClass() throws IOException {
        String code = "class Person {\n" +
                      "    String name\n" +
                      "    int age\n" +
                      "    \n" +
                      "    def greet() {\n" +
                      "        return 'Hello'\n" +
                      "    }\n" +
                      "}";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for class definition", tokenCount > 5);
    }

    @Test
    public void testHighlightGroovyStrings() throws IOException {
        String code = "def name = 'World'\n" +
                      "def message = 'Hello ' + name";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for strings", tokenCount > 0);
    }

    @Test
    public void testHighlightGroovyComments() throws IOException {
        String code = "// Single line comment\n" +
                      "/* Multi-line\n" +
                      "   comment */\n" +
                      "def x = 5";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for comments", tokenCount > 0);
    }

    @Test
    public void testHighlightGroovyCollections() throws IOException {
        String code = "def list = [1, 2, 3]\n" +
                      "def map = [key1: 'value1']";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for collections", tokenCount > 5);
    }

    @Test
    public void testHighlightGroovyAnnotations() throws IOException {
        String code = "@Override\n" +
                      "def method() { }";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for annotations", tokenCount > 0);
    }

    @Test
    public void testHighlightComplexGroovyScript() throws IOException {
        String code = "class Calculator {\n" +
                      "    def add(a, b) {\n" +
                      "        return a + b\n" +
                      "    }\n" +
                      "    \n" +
                      "    def multiply(a, b) {\n" +
                      "        return a * b\n" +
                      "    }\n" +
                      "}\n" +
                      "def calc = new Calculator()\n" +
                      "def result = calc.add(5, 3)";
        GroovyHighlighter highlighter = new GroovyHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have many tokens for complex script", tokenCount > 20);
    }
}
