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

    // ===== Token Type Validation Tests =====

    @Test
    public void testKeywordStyleForPublic() throws IOException {
        String code = "public";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("'public' should be KEYWORD_STYLE", JavaHighlighter.KEYWORD_STYLE, style);
        assertEquals("Token length should match", code.length(), highlighter.getTokenLength());
    }

    @Test
    public void testKeywordStyleForClass() throws IOException {
        String code = "class";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("'class' should be KEYWORD_STYLE", JavaHighlighter.KEYWORD_STYLE, style);
    }

    @Test
    public void testKeywordStyleForControlFlow() throws IOException {
        String[] keywords = {"if", "else", "while", "for", "do", "switch", "case", "break", "continue", "return"};

        for (String keyword : keywords) {
            JavaHighlighter highlighter = new JavaHighlighter();
            highlighter.setReader(new StringReader(keyword));

            byte style = highlighter.getNextToken();
            assertEquals("'" + keyword + "' should be KEYWORD_STYLE", JavaHighlighter.KEYWORD_STYLE, style);
        }
    }

    @Test
    public void testTypeStyleForPrimitiveTypes() throws IOException {
        String[] types = {"int", "long", "short", "byte", "float", "double", "boolean", "char", "void"};

        for (String type : types) {
            JavaHighlighter highlighter = new JavaHighlighter();
            highlighter.setReader(new StringReader(type));

            byte style = highlighter.getNextToken();
            assertEquals("'" + type + "' should be TYPE_STYLE", JavaHighlighter.TYPE_STYLE, style);
        }
    }

    @Test
    public void testLiteralStyleForStringLiteral() throws IOException {
        String code = "\"Hello World\"";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("String literal should be LITERAL_STYLE", JavaHighlighter.LITERAL_STYLE, style);
        assertEquals("Token should cover entire string", code.length(), highlighter.getTokenLength());
    }

    @Test
    public void testLiteralStyleForCharLiteral() throws IOException {
        String code = "'a'";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("Char literal should be LITERAL_STYLE", JavaHighlighter.LITERAL_STYLE, style);
    }

    @Test
    public void testLiteralStyleForNumbers() throws IOException {
        String[] numbers = {"123", "45.67", "0xFF", "0b1010"};

        for (String number : numbers) {
            JavaHighlighter highlighter = new JavaHighlighter();
            highlighter.setReader(new StringReader(number));

            byte style = highlighter.getNextToken();
            assertEquals("'" + number + "' should be LITERAL_STYLE", JavaHighlighter.LITERAL_STYLE, style);
        }
    }

    @Test
    public void testCommentStyleForSingleLineComment() throws IOException {
        // Test that single-line comments are tokenized (verifies no infinite loop)
        String code = "// This is a comment\n";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int index = 0;
        boolean foundComment = false;
        while (index < code.length()) {
            byte style = highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            if (style == JavaHighlighter.JAVA_COMMENT_STYLE) {
                foundComment = true;
            }
            index += length;
        }
        assertTrue("Should find comment style in single-line comment", foundComment);
    }

    @Test
    public void testCommentStyleForMultiLineComment() throws IOException {
        // Test that multi-line comments are tokenized
        String code = "/* This is\na multi-line\ncomment */";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int index = 0;
        boolean foundComment = false;
        while (index < code.length()) {
            byte style = highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            if (style == JavaHighlighter.JAVA_COMMENT_STYLE) {
                foundComment = true;
            }
            index += length;
        }
        assertTrue("Should find comment style in multi-line comment", foundComment);
    }

    @Test
    public void testJavadocCommentStyle() throws IOException {
        // Test that Javadoc comments are tokenized
        String code = "/** Javadoc */";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int index = 0;
        boolean foundJavadoc = false;
        while (index < code.length()) {
            byte style = highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            if (style == JavaHighlighter.JAVADOC_COMMENT_STYLE) {
                foundJavadoc = true;
            }
            index += length;
        }
        assertTrue("Should find javadoc comment style", foundJavadoc);
    }

    @Test
    public void testOperatorStyle() throws IOException {
        // Test operators excluding '/' which can be ambiguous with comments
        String[] operators = {"+", "-", "*", "=", "==", "!=", "<", ">", "&&", "||"};

        for (String op : operators) {
            JavaHighlighter highlighter = new JavaHighlighter();
            highlighter.setReader(new StringReader(op));

            byte style = highlighter.getNextToken();
            assertEquals("'" + op + "' should be OPERATOR_STYLE", JavaHighlighter.OPERATOR_STYLE, style);
        }
    }

    @Test
    public void testDivisionOperator() throws IOException {
        // Test division operator in context to avoid confusion with comments
        String code = "a / b";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        boolean foundOperator = false;
        int index = 0;
        while (index < code.length()) {
            byte style = highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            if (style == JavaHighlighter.OPERATOR_STYLE) {
                foundOperator = true;
            }
            index += length;
        }
        assertTrue("Should find operator style for division", foundOperator);
    }

    @Test
    public void testSeparatorStyle() throws IOException {
        String[] separators = {"(", ")", "{", "}", "[", "]", ";", ",", "."};

        for (String sep : separators) {
            JavaHighlighter highlighter = new JavaHighlighter();
            highlighter.setReader(new StringReader(sep));

            byte style = highlighter.getNextToken();
            assertEquals("'" + sep + "' should be SEPARATOR_STYLE", JavaHighlighter.SEPARATOR_STYLE, style);
        }
    }

    @Test
    public void testPlainStyleForIdentifier() throws IOException {
        String code = "myVariable";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("Identifier should be PLAIN_STYLE", JavaHighlighter.PLAIN_STYLE, style);
    }

    @Test
    public void testMixedTokenTypes() throws IOException {
        String code = "public int x = 5;";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        // public
        byte style1 = highlighter.getNextToken();
        assertEquals("'public' should be KEYWORD_STYLE", JavaHighlighter.KEYWORD_STYLE, style1);
        assertEquals(6, highlighter.getTokenLength());

        // space
        byte style2 = highlighter.getNextToken();
        assertEquals("space should be PLAIN_STYLE", JavaHighlighter.PLAIN_STYLE, style2);

        // int
        byte style3 = highlighter.getNextToken();
        assertEquals("'int' should be TYPE_STYLE", JavaHighlighter.TYPE_STYLE, style3);
    }

    @Test
    public void testAssertKeywordWithFlag() throws IOException {
        String code = "assert";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.ASSERT_IS_KEYWORD = true;
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("'assert' with flag should be KEYWORD_STYLE",
                     JavaHighlighter.KEYWORD_STYLE, style);
    }

    @Test
    public void testAssertWithoutFlag() throws IOException {
        String code = "assert";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.ASSERT_IS_KEYWORD = false;
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("'assert' without flag should be PLAIN_STYLE",
                     JavaHighlighter.PLAIN_STYLE, style);
    }

    @Test
    public void testLiteralStyleForBooleans() throws IOException {
        String[] booleans = {"true", "false"};

        for (String bool : booleans) {
            JavaHighlighter highlighter = new JavaHighlighter();
            highlighter.setReader(new StringReader(bool));

            byte style = highlighter.getNextToken();
            assertEquals("'" + bool + "' should be LITERAL_STYLE", JavaHighlighter.LITERAL_STYLE, style);
        }
    }

    @Test
    public void testLiteralStyleForNull() throws IOException {
        String code = "null";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        byte style = highlighter.getNextToken();
        assertEquals("'null' should be LITERAL_STYLE", JavaHighlighter.LITERAL_STYLE, style);
    }

    @Test
    public void testStateTransitionIntoComment() throws IOException {
        // Test that code with embedded comments is tokenized correctly
        String code = "x /* comment */ y";
        JavaHighlighter highlighter = new JavaHighlighter();
        highlighter.setReader(new StringReader(code));

        int index = 0;
        boolean foundPlain = false;
        boolean foundComment = false;
        while (index < code.length()) {
            byte style = highlighter.getNextToken();
            int length = highlighter.getTokenLength();
            if (style == JavaHighlighter.PLAIN_STYLE) {
                foundPlain = true;
            }
            if (style == JavaHighlighter.JAVA_COMMENT_STYLE) {
                foundComment = true;
            }
            index += length;
        }
        assertTrue("Should find plain style for identifiers", foundPlain);
        assertTrue("Should find comment style for comment", foundComment);
    }
}
