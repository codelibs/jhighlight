package org.codelibs.jhighlight;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Tests for the JHighlight CLI entry point.
 * Note: Tests that would trigger System.exit() are excluded to avoid JVM crashes.
 */
public class JHighlightTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private PrintStream originalErr;
    private PrintStream originalOut;
    private ByteArrayOutputStream errContent;

    @Before
    public void setUp() {
        originalErr = System.err;
        originalOut = System.out;
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setErr(originalErr);
        System.setOut(originalOut);
    }

    // Note: testMainWithNoArguments is excluded because it calls System.exit(1)
    // which crashes the test JVM

    @Test
    public void testMainWithNonExistentFile() {
        try {
            JHighlight.main(new String[]{"/non/existent/file.java"});
            fail("Should throw IOException for non-existent file");
        } catch (Throwable e) {
            assertTrue("Should be IOException", e instanceof IOException);
            assertTrue("Should mention source location",
                       e.getMessage().contains("doesn't exist"));
        }
    }

    @Test
    public void testMainWithNonExistentDestDir() throws Exception {
        File javaFile = tempFolder.newFile("Test.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write("public class Test { }");
        }

        try {
            JHighlight.main(new String[]{"-d", "/non/existent/dir", javaFile.getAbsolutePath()});
            fail("Should throw IOException for non-existent destination directory");
        } catch (Throwable e) {
            assertTrue("Should be IOException", e instanceof IOException);
            assertTrue("Should mention destination directory",
                       e.getMessage().contains("destination directory"));
        }
    }

    @Test
    public void testMainWithFileAsDestDir() throws Exception {
        File javaFile = tempFolder.newFile("Test.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write("public class Test { }");
        }

        File notADir = tempFolder.newFile("notadir.txt");

        try {
            JHighlight.main(new String[]{"-d", notADir.getAbsolutePath(), javaFile.getAbsolutePath()});
            fail("Should throw IOException for file used as destination directory");
        } catch (Throwable e) {
            assertTrue("Should be IOException", e instanceof IOException);
            assertTrue("Should mention not a directory",
                       e.getMessage().contains("not a directory"));
        }
    }

    @Test
    public void testHighlightSingleJavaFile() throws Throwable {
        File javaFile = tempFolder.newFile("HelloWorld.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write("public class HelloWorld {\n");
            writer.write("    public static void main(String[] args) {\n");
            writer.write("        System.out.println(\"Hello, World!\");\n");
            writer.write("    }\n");
            writer.write("}\n");
        }

        JHighlight.main(new String[]{javaFile.getAbsolutePath()});

        File outputFile = new File(javaFile.getAbsolutePath() + ".html");
        assertTrue("Output file should be created", outputFile.exists());

        String content = new String(Files.readAllBytes(outputFile.toPath()));
        assertTrue("Should contain HTML structure", content.contains("<html"));
        assertTrue("Should contain highlighted code", content.contains("class"));
    }

    @Test
    public void testHighlightWithDestinationDirectory() throws Throwable {
        File javaFile = tempFolder.newFile("Example.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write("public class Example { }");
        }

        File destDir = tempFolder.newFolder("output");

        JHighlight.main(new String[]{"-d", destDir.getAbsolutePath(), javaFile.getAbsolutePath()});

        File outputFile = new File(destDir, "Example.java.html");
        assertTrue("Output file should be created in destination directory", outputFile.exists());
    }

    @Test
    public void testHighlightWithEncodingOption() throws Throwable {
        File javaFile = tempFolder.newFile("Encoded.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write("public class Encoded { }");
        }

        JHighlight.main(new String[]{"-e", "UTF-8", javaFile.getAbsolutePath()});

        File outputFile = new File(javaFile.getAbsolutePath() + ".html");
        assertTrue("Output file should be created", outputFile.exists());
    }

    @Test
    public void testHighlightWithFragmentOption() throws Throwable {
        File javaFile = tempFolder.newFile("Fragment.java");
        try (FileWriter writer = new FileWriter(javaFile)) {
            writer.write("public class Fragment { }");
        }

        JHighlight.main(new String[]{"--fragment", javaFile.getAbsolutePath()});

        File outputFile = new File(javaFile.getAbsolutePath() + ".html");
        assertTrue("Output file should be created", outputFile.exists());

        String content = new String(Files.readAllBytes(outputFile.toPath()));
        assertTrue("Should contain code element", content.contains("<code"));
    }

    @Test
    public void testHighlightWithVerboseOption() throws Throwable {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            File javaFile = tempFolder.newFile("Verbose.java");
            try (FileWriter writer = new FileWriter(javaFile)) {
                writer.write("public class Verbose { }");
            }

            JHighlight.main(new String[]{"--verbose", javaFile.getAbsolutePath()});

            String output = outContent.toString();
            assertTrue("Verbose mode should output progress", output.contains("done"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testHighlightXmlFile() throws Throwable {
        File xmlFile = tempFolder.newFile("config.xml");
        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write("<?xml version=\"1.0\"?>\n");
            writer.write("<config><setting name=\"test\">value</setting></config>");
        }

        JHighlight.main(new String[]{xmlFile.getAbsolutePath()});

        File outputFile = new File(xmlFile.getAbsolutePath() + ".html");
        assertTrue("Output file should be created for XML", outputFile.exists());
    }

    @Test
    public void testHighlightHtmlFile() throws Throwable {
        File htmlFile = tempFolder.newFile("page.html");
        try (FileWriter writer = new FileWriter(htmlFile)) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html><head><title>Test</title></head><body><p>Hello</p></body></html>");
        }

        JHighlight.main(new String[]{htmlFile.getAbsolutePath()});

        File outputFile = new File(htmlFile.getAbsolutePath() + ".html");
        assertTrue("Output file should be created for HTML", outputFile.exists());
    }

    @Test
    public void testHighlightDirectory() throws Throwable {
        File sourceDir = tempFolder.newFolder("src");

        File javaFile1 = new File(sourceDir, "One.java");
        try (FileWriter writer = new FileWriter(javaFile1)) {
            writer.write("public class One { }");
        }

        File javaFile2 = new File(sourceDir, "Two.java");
        try (FileWriter writer = new FileWriter(javaFile2)) {
            writer.write("public class Two { }");
        }

        JHighlight.main(new String[]{sourceDir.getAbsolutePath()});

        File outputFile1 = new File(sourceDir, "One.java.html");
        File outputFile2 = new File(sourceDir, "Two.java.html");
        assertTrue("Output file 1 should be created", outputFile1.exists());
        assertTrue("Output file 2 should be created", outputFile2.exists());
    }

    @Test
    public void testHighlightMultipleFiles() throws Throwable {
        File javaFile1 = tempFolder.newFile("First.java");
        try (FileWriter writer = new FileWriter(javaFile1)) {
            writer.write("public class First { }");
        }

        File javaFile2 = tempFolder.newFile("Second.java");
        try (FileWriter writer = new FileWriter(javaFile2)) {
            writer.write("public class Second { }");
        }

        JHighlight.main(new String[]{javaFile1.getAbsolutePath(), javaFile2.getAbsolutePath()});

        File outputFile1 = new File(javaFile1.getAbsolutePath() + ".html");
        File outputFile2 = new File(javaFile2.getAbsolutePath() + ".html");
        assertTrue("First output file should be created", outputFile1.exists());
        assertTrue("Second output file should be created", outputFile2.exists());
    }

    @Test
    public void testHighlightWithAllOptions() throws Throwable {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            File javaFile = tempFolder.newFile("AllOptions.java");
            try (FileWriter writer = new FileWriter(javaFile)) {
                writer.write("public class AllOptions { }");
            }

            File destDir = tempFolder.newFolder("all_output");

            JHighlight.main(new String[]{
                "--verbose",
                "--fragment",
                "-d", destDir.getAbsolutePath(),
                "-e", "UTF-8",
                javaFile.getAbsolutePath()
            });

            File outputFile = new File(destDir, "AllOptions.java.html");
            assertTrue("Output file should be created", outputFile.exists());

            String output = outContent.toString();
            assertTrue("Verbose output should be present", output.contains("done"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
