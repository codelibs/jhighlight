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
        String code = "var let const function if else while for return";
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
    public void testHighlightJavaScriptArrowFunction() throws IOException {
        String code = "const add = (a, b) => a + b;";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for arrow function", tokenCount > 0);
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
        String code = "const obj = {\n" +
                      "    name: 'John',\n" +
                      "    age: 30,\n" +
                      "    greet: function() {\n" +
                      "        console.log('Hello');\n" +
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
        String code = "const arr = [1, 2, 3, 'four', true];";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for array", tokenCount > 5);
    }

    @Test
    public void testHighlightJavaScriptClass() throws IOException {
        String code = "class Person {\n" +
                      "    constructor(name) {\n" +
                      "        this.name = name;\n" +
                      "    }\n" +
                      "    \n" +
                      "    greet() {\n" +
                      "        return `Hello, ${this.name}`;\n" +
                      "    }\n" +
                      "}";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for class", tokenCount > 10);
    }

    @Test
    public void testHighlightJavaScriptTemplateLiteral() throws IOException {
        String code = "const name = 'World';\n" +
                      "const message = `Hello, ${name}!`;";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
        }
        assertTrue("Should have tokens for template literal", tokenCount > 0);
    }

    @Test
    public void testHighlightComplexJavaScript() throws IOException {
        String code = "// Calculator module\n" +
                      "class Calculator {\n" +
                      "    add(a, b) {\n" +
                      "        return a + b;\n" +
                      "    }\n" +
                      "    \n" +
                      "    multiply(a, b) {\n" +
                      "        return a * b;\n" +
                      "    }\n" +
                      "}\n\n" +
                      "const calc = new Calculator();\n" +
                      "console.log(calc.add(5, 3));\n" +
                      "console.log(calc.multiply(4, 2));";
        JavaScriptHighlighter highlighter = new JavaScriptHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have many tokens for complex code", tokenCount > 30);
    }
}
