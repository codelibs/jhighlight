package org.codelibs.jhighlight.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.codelibs.jhighlight.highlighter.JavaHighlighter;
import org.junit.Test;

public class JavaXhtmlRendererTest {

    @Test
    public void testRenderSimpleJavaCode_Fragment() throws IOException {
        String code = "public class Test { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain span elements", result.contains("<span"));
        assertTrue("Should contain CSS classes", result.contains("class="));
    }

    @Test
    public void testRenderSimpleJavaCode_FullDocument() throws IOException {
        String code = "public class Test { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", false);

        assertNotNull(result);
        assertTrue("Should contain DOCTYPE", result.contains("DOCTYPE"));
        assertTrue("Should contain html tag", result.contains("<html"));
        assertTrue("Should contain style tag", result.contains("<style"));
        assertTrue("Should contain body tag", result.contains("<body"));
    }

    @Test
    public void testRenderJavaKeywords() throws IOException {
        String code = "public static void main(String[] args) { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Result should not be empty", result.length() > 0);
        assertTrue("Should contain span elements", result.contains("<span"));
    }

    @Test
    public void testRenderJavaComments() throws IOException {
        String code = "// This is a comment\n" +
                      "/* Multi-line\n" +
                      "   comment */\n" +
                      "public class Test { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain comment styles", result.contains("comment"));
    }

    @Test
    public void testRenderJavaDocComment() throws IOException {
        String code = "/**\n" +
                      " * JavaDoc comment\n" +
                      " * @param test\n" +
                      " */\n" +
                      "public void method(int test) { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Result should not be empty", result.length() > 0);
    }

    @Test
    public void testRenderJavaStrings() throws IOException {
        String code = "String s = \"Hello World\";";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        // The string literal should be in the output (quotes are part of the literal)
        assertTrue("Should contain String keyword", result.contains("String"));
        assertTrue("Should contain string literal", result.contains("\"Hello") || result.contains("&quot;Hello"));
    }

    @Test
    public void testRenderWithSpecialCharacters() throws IOException {
        String code = "String s = \"<html>&amp;</html>\";";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        // Special characters should be HTML encoded
        assertTrue("Should encode special characters",
                   result.contains("&lt;") || result.contains("&amp;"));
    }

    @Test
    public void testRenderComplexJavaClass() throws IOException {
        String code = "package com.example;\n\n" +
                      "import java.util.List;\n\n" +
                      "public class Example {\n" +
                      "    private int value;\n\n" +
                      "    public Example(int value) {\n" +
                      "        this.value = value;\n" +
                      "    }\n" +
                      "}";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Example.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain package", result.contains("package"));
        assertTrue("Should contain import", result.contains("import"));
        assertTrue("Should contain class", result.contains("class"));
    }

    @Test
    public void testRenderUsingStreams() throws IOException {
        String code = "public class Test { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        ByteArrayInputStream in = new ByteArrayInputStream(code.getBytes("UTF-8"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        renderer.highlight("Test.java", in, out, "UTF-8", true);

        String result = out.toString("UTF-8");
        assertNotNull(result);
        assertTrue("Result should not be empty", result.length() > 0);
        assertTrue("Should contain span elements", result.contains("<span"));
    }

    @Test
    public void testRenderEmptyCode() throws IOException {
        String code = "";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
    }

    @Test
    public void testRenderWithTabs() throws IOException {
        String code = "public class Test {\n" +
                      "\tpublic void method() {\n" +
                      "\t\tSystem.out.println(\"test\");\n" +
                      "\t}\n" +
                      "}";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        // Tabs should be converted to spaces
        assertFalse("Should not contain tab characters", result.contains("\t"));
    }

    @Test
    public void testRenderJavaAnnotations() throws IOException {
        String code = "@Override\n" +
                      "@Test\n" +
                      "public void testMethod() { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain annotations", result.contains("Override"));
        assertTrue("Should contain annotations", result.contains("Test"));
    }

    @Test
    public void testRenderMultipleClasses() throws IOException {
        String code = "class ClassOne { }\n" +
                      "class ClassTwo { }\n" +
                      "class ClassThree { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertNotNull(result);
        assertTrue("Should contain all class names",
                   result.contains("ClassOne") &&
                   result.contains("ClassTwo") &&
                   result.contains("ClassThree"));
    }

    // ===== CSS Class Mapping Tests =====

    @Test
    public void testGetCssClass_PlainStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.PLAIN_STYLE);
        assertEquals("java_plain", cssClass);
    }

    @Test
    public void testGetCssClass_KeywordStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.KEYWORD_STYLE);
        assertEquals("java_keyword", cssClass);
    }

    @Test
    public void testGetCssClass_TypeStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.TYPE_STYLE);
        assertEquals("java_type", cssClass);
    }

    @Test
    public void testGetCssClass_OperatorStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.OPERATOR_STYLE);
        assertEquals("java_operator", cssClass);
    }

    @Test
    public void testGetCssClass_SeparatorStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.SEPARATOR_STYLE);
        assertEquals("java_separator", cssClass);
    }

    @Test
    public void testGetCssClass_LiteralStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.LITERAL_STYLE);
        assertEquals("java_literal", cssClass);
    }

    @Test
    public void testGetCssClass_CommentStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.JAVA_COMMENT_STYLE);
        assertEquals("java_comment", cssClass);
    }

    @Test
    public void testGetCssClass_JavadocCommentStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.JAVADOC_COMMENT_STYLE);
        assertEquals("java_javadoc_comment", cssClass);
    }

    @Test
    public void testGetCssClass_JavadocTagStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(JavaHighlighter.JAVADOC_TAG_STYLE);
        assertEquals("java_javadoc_tag", cssClass);
    }

    @Test
    public void testGetCssClass_UnknownStyle() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        String cssClass = renderer.getCssClass(99); // Unknown style
        assertNull("Unknown style should return null", cssClass);
    }

    @Test
    public void testGetDefaultCssStyles() {
        JavaXhtmlRenderer renderer = new TestableJavaXhtmlRenderer();
        Map defaultCss = renderer.getDefaultCssStyles();

        assertNotNull("Default CSS should not be null", defaultCss);
        assertTrue("Should contain h1 style", defaultCss.containsKey("h1"));
        assertTrue("Should contain code style", defaultCss.containsKey("code"));
        assertTrue("Should contain java_plain style", defaultCss.containsKey(".java_plain"));
        assertTrue("Should contain java_keyword style", defaultCss.containsKey(".java_keyword"));
        assertTrue("Should contain java_type style", defaultCss.containsKey(".java_type"));
        assertTrue("Should contain java_operator style", defaultCss.containsKey(".java_operator"));
        assertTrue("Should contain java_separator style", defaultCss.containsKey(".java_separator"));
        assertTrue("Should contain java_literal style", defaultCss.containsKey(".java_literal"));
        assertTrue("Should contain java_comment style", defaultCss.containsKey(".java_comment"));
        assertTrue("Should contain java_javadoc_comment style", defaultCss.containsKey(".java_javadoc_comment"));
        assertTrue("Should contain java_javadoc_tag style", defaultCss.containsKey(".java_javadoc_tag"));
    }

    @Test
    public void testRenderedOutputContainsCssClasses() throws IOException {
        String code = "public class Test { int x = 5; }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        assertTrue("Should contain java_keyword class", result.contains("java_keyword"));
        assertTrue("Should contain java_type class", result.contains("java_type"));
        assertTrue("Should contain java_literal class", result.contains("java_literal"));
    }

    @Test
    public void testRenderedOutputContainsProperSpanTags() throws IOException {
        String code = "public int value;";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", true);

        // Verify span structure
        assertTrue("Should contain opening span with class",
                   result.contains("<span class=\"java_keyword\">public</span>"));
        assertTrue("Should contain type span",
                   result.contains("<span class=\"java_type\">int</span>"));
    }

    @Test
    public void testCssClassesInFullDocument() throws IOException {
        String code = "public class Test { }";
        JavaXhtmlRenderer renderer = new JavaXhtmlRenderer();

        String result = renderer.highlight("Test.java", code, "UTF-8", false);

        // In full document mode, CSS definitions should be in style tag
        assertTrue("Should contain style definitions", result.contains("<style"));
        assertTrue("Should contain java_keyword definition",
                   result.contains(".java_keyword"));
    }

    // Helper class to access protected methods
    private static class TestableJavaXhtmlRenderer extends JavaXhtmlRenderer {
        @Override
        public String getCssClass(int style) {
            return super.getCssClass(style);
        }

        @Override
        public Map getDefaultCssStyles() {
            return super.getDefaultCssStyles();
        }
    }
}
