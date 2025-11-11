package org.codelibs.jhighlight.renderer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        assertTrue("Should contain string content", result.contains("Hello World"));
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
}
