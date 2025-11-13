package org.codelibs.jhighlight.highlighter;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class CppHighlighterTest {

    private static final int MAX_TOKENS = 1000; // Prevent infinite loops

    @Test
    public void testHighlightSimpleCppCode() throws IOException {
        String code = "int main() { return 0; }";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have multiple tokens", tokenCount > 0);
    }

    @Test
    public void testHighlightCppKeywords() throws IOException {
        String code = "class struct namespace using template typename";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for keywords", tokenCount > 0);
    }

    @Test
    public void testHighlightCppPreprocessor() throws IOException {
        String code = "#include <iostream>\n" +
                      "#define MAX 100\n" +
                      "#ifdef DEBUG\n" +
                      "#endif";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for preprocessor directives", tokenCount > 0);
    }

    @Test
    public void testHighlightCppComments() throws IOException {
        String code = "// Single line comment\n" +
                      "/* Multi-line comment */\n" +
                      "int x = 5;";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for comments", tokenCount > 0);
    }

    @Test
    public void testHighlightCppClass() throws IOException {
        String code = "class MyClass {\n" +
                      "public:\n" +
                      "    MyClass();\n" +
                      "    ~MyClass();\n" +
                      "private:\n" +
                      "    int value;\n" +
                      "};";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for class definition", tokenCount > 5);
    }

    @Test
    public void testHighlightCppPointers() throws IOException {
        String code = "int* ptr = nullptr;\n" +
                      "int& ref = value;";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for pointers and references", tokenCount > 0);
    }

    @Test
    public void testHighlightCppTemplates() throws IOException {
        String code = "template<typename T>\n" +
                      "class Container { };";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for templates", tokenCount > 0);
    }

    @Test
    public void testHighlightCppNamespace() throws IOException {
        String code = "namespace MyNamespace {\n" +
                      "    void function();\n" +
                      "}";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for namespace", tokenCount > 0);
    }

    @Test
    public void testHighlightCppOperators() throws IOException {
        String code = "a = b + c - d * e / f % g;";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
        }
        assertTrue("Should have tokens for operators", tokenCount > 0);
    }

    @Test
    public void testHighlightComplexCppProgram() throws IOException {
        String code = "#include <iostream>\n\n" +
                      "using namespace std;\n\n" +
                      "class Example {\n" +
                      "public:\n" +
                      "    Example(int val) : value(val) {}\n" +
                      "    void print() {\n" +
                      "        cout << \"Value: \" << value << endl;\n" +
                      "    }\n" +
                      "private:\n" +
                      "    int value;\n" +
                      "};\n\n" +
                      "int main() {\n" +
                      "    Example ex(42);\n" +
                      "    ex.print();\n" +
                      "    return 0;\n" +
                      "}";
        CppHighlighter highlighter = new CppHighlighter();
        highlighter.setReader(new StringReader(code));

        int tokenCount = 0;
        while (highlighter.getNextToken() != 0 && tokenCount < MAX_TOKENS) {
            tokenCount++;
            assertTrue("Token length should be positive", highlighter.getTokenLength() > 0);
        }
        assertTrue("Should have many tokens for complex program", tokenCount > 30);
    }
}
