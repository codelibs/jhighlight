package org.codelibs.jhighlight.highlighter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class JavaScriptHighlighterTest {

    @Test
    public void testHighlightSimpleJavaScript() throws IOException {
        String code = "var x = 5;";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have multiple tokens", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaScriptKeywords() throws IOException {
        String code = "var x = 1; function test() { if (true) { return x; } }";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for keywords", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaScriptFunction() throws IOException {
        String code = "function greet(name) {\n" +
                      "    return 'Hello, ' + name;\n" +
                      "}";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for function", tokenCount > 5);
    }

    @Test
    public void testHighlightJavaScriptComments() throws IOException {
        String code = "// Single line comment\n" +
                      "/* Multi-line\n" +
                      "   comment */\n" +
                      "var x = 5;";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for comments", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaScriptObject() throws IOException {
        String code = "var obj = {\n" +
                      "    name: 'John',\n" +
                      "    age: 30,\n" +
                      "    greet: function() {\n" +
                      "        alert('Hello');\n" +
                      "    }\n" +
                      "};";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for object literal", tokenCount > 10);
    }

    @Test
    public void testHighlightJavaScriptArray() throws IOException {
        String code = "var arr = [1, 2, 3, 'four', true];";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for array", tokenCount > 5);
    }

    @Test
    public void testHighlightJavaScriptStrings() throws IOException {
        String code = "var str1 = 'Hello';\n" +
                      "var str2 = \"World\";";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for strings", tokenCount > 0);
    }

    @Test
    public void testHighlightJavaScriptNumbers() throws IOException {
        String code = "var a = 123; var b = 45.67;";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for numbers", tokenCount > 0);
    }

    @Test
    public void testHighlightComplexJavaScript() throws IOException {
        String code = "// Calculator module\n" +
                      "function Calculator() {\n" +
                      "    this.add = function(a, b) {\n" +
                      "        return a + b;\n" +
                      "    };\n" +
                      "    this.multiply = function(a, b) {\n" +
                      "        return a * b;\n" +
                      "    };\n" +
                      "}\n" +
                      "var calc = new Calculator();\n" +
                      "var result = calc.add(5, 3);";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have many tokens for complex code", tokenCount > 20);
    }
}
