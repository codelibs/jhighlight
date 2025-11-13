package org.codelibs.jhighlight.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileUtilsTest {
    private File tempDir;

    @Before
    public void setUp() throws IOException {
        // Create a temporary directory for testing
        tempDir = Files.createTempDirectory("jhighlight-test").toFile();
    }

    @After
    public void tearDown() {
        // Clean up temporary directory
        if (tempDir != null && tempDir.exists()) {
            deleteDirectory(tempDir);
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    @Test
    public void testGetExtension_Basic() {
        assertEquals("java", FileUtils.getExtension("Test.java"));
        assertEquals("txt", FileUtils.getExtension("readme.txt"));
        assertEquals("xml", FileUtils.getExtension("config.xml"));
    }

    @Test
    public void testGetExtension_MultipleDotsInFilename() {
        assertEquals("java", FileUtils.getExtension("Test.backup.java"));
        assertEquals("gz", FileUtils.getExtension("archive.tar.gz"));
    }

    @Test
    public void testGetExtension_NoExtension() {
        assertNull(FileUtils.getExtension("README"));
        assertNull(FileUtils.getExtension("Makefile"));
    }

    @Test
    public void testGetExtension_DotAtEnd() {
        assertNull(FileUtils.getExtension("test."));
    }

    @Test
    public void testGetExtension_HiddenFile() {
        assertNull(FileUtils.getExtension(".gitignore"));
        assertEquals("txt", FileUtils.getExtension(".hidden.txt"));
    }

    @Test
    public void testGetExtension_LowercaseConversion() {
        assertEquals("java", FileUtils.getExtension("Test.JAVA"));
        assertEquals("txt", FileUtils.getExtension("README.TXT"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetExtension_NullFileName() {
        FileUtils.getExtension(null);
    }

    @Test
    public void testGetFileList_NullFile() {
        ArrayList result = FileUtils.getFileList(null, null, null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFileList_SingleFile() throws IOException {
        File testFile = new File(tempDir, "test.java");
        testFile.createNewFile();

        ArrayList result = FileUtils.getFileList(testFile, null, null);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test.java", result.get(0));
    }

    @Test
    public void testGetFileList_SingleFile_WithIncludePattern() throws IOException {
        File testFile = new File(tempDir, "test.java");
        testFile.createNewFile();

        Pattern[] included = new Pattern[] { Pattern.compile(".*\\.java") };
        ArrayList result = FileUtils.getFileList(testFile, included, null);
        assertEquals(1, result.size());
        assertEquals("test.java", result.get(0));
    }

    @Test
    public void testGetFileList_SingleFile_FilteredOut() throws IOException {
        File testFile = new File(tempDir, "test.txt");
        testFile.createNewFile();

        Pattern[] included = new Pattern[] { Pattern.compile(".*\\.java") };
        ArrayList result = FileUtils.getFileList(testFile, included, null);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFileList_Directory_NoFiles() {
        ArrayList result = FileUtils.getFileList(tempDir, null, null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetFileList_Directory_MultipleFiles() throws IOException {
        new File(tempDir, "Test1.java").createNewFile();
        new File(tempDir, "Test2.java").createNewFile();
        new File(tempDir, "README.txt").createNewFile();

        ArrayList result = FileUtils.getFileList(tempDir, null, null);
        assertEquals(3, result.size());
        // Results should be sorted alphabetically
        assertTrue(result.contains("README.txt"));
        assertTrue(result.contains("Test1.java"));
        assertTrue(result.contains("Test2.java"));
    }

    @Test
    public void testGetFileList_Directory_WithIncludePattern() throws IOException {
        new File(tempDir, "Test1.java").createNewFile();
        new File(tempDir, "Test2.java").createNewFile();
        new File(tempDir, "README.txt").createNewFile();

        Pattern[] included = new Pattern[] { Pattern.compile(".*\\.java") };
        ArrayList result = FileUtils.getFileList(tempDir, included, null);
        assertEquals(2, result.size());
        assertTrue(result.contains("Test1.java"));
        assertTrue(result.contains("Test2.java"));
    }

    @Test
    public void testGetFileList_Directory_WithExcludePattern() throws IOException {
        new File(tempDir, "Test.java").createNewFile();
        new File(tempDir, "TestCase.java").createNewFile();
        new File(tempDir, "Example.java").createNewFile();

        Pattern[] excluded = new Pattern[] { Pattern.compile(".*Test.*") };
        ArrayList result = FileUtils.getFileList(tempDir, null, excluded);
        assertEquals(1, result.size());
        assertEquals("Example.java", result.get(0));
    }

    @Test
    public void testGetFileList_Directory_WithIncludeAndExclude() throws IOException {
        new File(tempDir, "Test.java").createNewFile();
        new File(tempDir, "TestCase.java").createNewFile();
        new File(tempDir, "Example.java").createNewFile();
        new File(tempDir, "README.txt").createNewFile();

        Pattern[] included = new Pattern[] { Pattern.compile(".*\\.java") };
        Pattern[] excluded = new Pattern[] { Pattern.compile(".*Test.*") };
        ArrayList result = FileUtils.getFileList(tempDir, included, excluded);
        assertEquals(1, result.size());
        assertEquals("Example.java", result.get(0));
    }

    @Test
    public void testGetFileList_NestedDirectories() throws IOException {
        File subDir = new File(tempDir, "subdir");
        subDir.mkdir();

        new File(tempDir, "Test1.java").createNewFile();
        new File(subDir, "Test2.java").createNewFile();
        new File(subDir, "Test3.java").createNewFile();

        ArrayList result = FileUtils.getFileList(tempDir, null, null);
        assertEquals(3, result.size());
        assertTrue(result.contains("Test1.java"));
        assertTrue(result.contains("subdir" + File.separator + "Test2.java"));
        assertTrue(result.contains("subdir" + File.separator + "Test3.java"));
    }

    @Test
    public void testGetFileList_NestedDirectories_WithFilter() throws IOException {
        File subDir = new File(tempDir, "subdir");
        subDir.mkdir();

        new File(tempDir, "Test1.java").createNewFile();
        new File(subDir, "Test2.java").createNewFile();
        new File(subDir, "README.txt").createNewFile();

        Pattern[] included = new Pattern[] { Pattern.compile(".*\\.java") };
        ArrayList result = FileUtils.getFileList(tempDir, included, null);
        assertEquals(2, result.size());
        assertTrue(result.contains("Test1.java"));
        assertTrue(result.contains("subdir" + File.separator + "Test2.java"));
    }

    @Test
    public void testGetFileList_DeeplyNestedDirectories() throws IOException {
        File level1 = new File(tempDir, "level1");
        File level2 = new File(level1, "level2");
        File level3 = new File(level2, "level3");
        level3.mkdirs();

        new File(tempDir, "root.java").createNewFile();
        new File(level1, "level1.java").createNewFile();
        new File(level2, "level2.java").createNewFile();
        new File(level3, "level3.java").createNewFile();

        ArrayList result = FileUtils.getFileList(tempDir, null, null);
        assertEquals(4, result.size());
        assertTrue(result.contains("root.java"));
    }
}
