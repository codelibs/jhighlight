package org.codelibs.jhighlight.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.codelibs.jhighlight.tools.StringUtils;
import org.junit.Test;

public class StringUtilsTest {
    @Test
    public void encodeHtml() {
        assertEquals("abc", StringUtils.encodeHtml("abc"));
        assertEquals("a&amp;c", StringUtils.encodeHtml("a&c"));
        assertEquals("&lt;b&gt;abc&lt;/b&gt;",
                StringUtils.encodeHtml("<b>abc</b>"));

        assertNull(StringUtils.encodeHtml(null));
        assertEquals("", StringUtils.encodeHtml(""));
    }

    @Test
    public void encodeHtmlSpecialCharacters() {
        // Test special HTML characters
        assertEquals("&amp;&lt;&gt;&quot;", StringUtils.encodeHtml("&<>\""));

        // Test ISO 8859-1 characters
        assertEquals("&nbsp;&copy;&reg;", StringUtils.encodeHtml("\u00A0\u00A9\u00AE"));

        // Test special characters
        assertEquals("&euro;&trade;", StringUtils.encodeHtml("\u20AC\u2122"));

        // Test Greek characters
        assertEquals("&Alpha;&beta;&Gamma;", StringUtils.encodeHtml("\u0391\u03B2\u0393"));

        // Test mathematical symbols
        assertEquals("&forall;&exist;&empty;", StringUtils.encodeHtml("\u2200\u2203\u2205"));
    }

    @Test
    public void testFilter_SinglePattern() {
        Pattern included = Pattern.compile(".*\\.java");
        Pattern excluded = Pattern.compile(".*Test\\.java");

        // Should pass - matches include pattern and not exclude pattern
        assertTrue(StringUtils.filter("Example.java", included, excluded));

        // Should fail - matches both include and exclude patterns
        assertFalse(StringUtils.filter("ExampleTest.java", included, excluded));

        // Should fail - doesn't match include pattern
        assertFalse(StringUtils.filter("Example.txt", included, excluded));

        // Should fail - null name
        assertFalse(StringUtils.filter(null, included, excluded));
    }

    @Test
    public void testFilter_NullPatterns() {
        // Null include pattern - should accept everything not excluded
        assertTrue(StringUtils.filter("anything", (Pattern[])null, (Pattern[])null));
        assertTrue(StringUtils.filter("test.java", (Pattern[])null, (Pattern[])null));

        // Only exclude pattern
        Pattern excluded = Pattern.compile(".*\\.class");
        assertTrue(StringUtils.filter("test.java", (Pattern[])null, new Pattern[]{excluded}));
        assertFalse(StringUtils.filter("test.class", (Pattern[])null, new Pattern[]{excluded}));
    }

    @Test
    public void testFilter_ArrayPatterns() {
        Pattern[] included = new Pattern[] {
            Pattern.compile(".*\\.java"),
            Pattern.compile(".*\\.groovy")
        };
        Pattern[] excluded = new Pattern[] {
            Pattern.compile(".*Test\\..*"),
            Pattern.compile(".*Mock\\..*")
        };

        // Should pass - matches java include pattern
        assertTrue(StringUtils.filter("Example.java", included, excluded));

        // Should pass - matches groovy include pattern
        assertTrue(StringUtils.filter("Example.groovy", included, excluded));

        // Should fail - matches exclude pattern
        assertFalse(StringUtils.filter("ExampleTest.java", included, excluded));
        assertFalse(StringUtils.filter("MockExample.groovy", included, excluded));

        // Should fail - doesn't match any include pattern
        assertFalse(StringUtils.filter("Example.txt", included, excluded));
    }

    @Test
    public void testSplit_Basic() {
        ArrayList result = StringUtils.split("one,two,three", ",");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }

    @Test
    public void testSplit_NullSource() {
        ArrayList result = StringUtils.split(null, ",");
        assertEquals(0, result.size());
    }

    @Test
    public void testSplit_NullSeparator() {
        ArrayList result = StringUtils.split("test", null);
        assertEquals(1, result.size());
        assertEquals("test", result.get(0));
    }

    @Test
    public void testSplit_EmptyParts() {
        ArrayList result = StringUtils.split("one,,three", ",");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("", result.get(1));
        assertEquals("three", result.get(2));
    }

    @Test
    public void testSplit_CaseInsensitive() {
        ArrayList result = StringUtils.split("oneANDtwoAndthree", "and", false);
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }

    @Test
    public void testSplit_CaseSensitive() {
        // Test case-sensitive splitting - "and" should only match exact lowercase "and"
        ArrayList result = StringUtils.split("oneandtwoandthree", "and", true);
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));

        // Test that case-sensitive doesn't match different case
        result = StringUtils.split("oneANDtwoAndthree", "and", true);
        assertEquals(1, result.size());
        assertEquals("oneANDtwoAndthree", result.get(0));
    }

    @Test
    public void testSplit_MultiCharSeparator() {
        ArrayList result = StringUtils.split("one::two::three", "::");
        assertEquals(3, result.size());
        assertEquals("one", result.get(0));
        assertEquals("two", result.get(1));
        assertEquals("three", result.get(2));
    }

    @Test
    public void testReplace_Basic() {
        String result = StringUtils.replace("hello world", "world", "universe");
        assertEquals("hello universe", result);
    }

    @Test
    public void testReplace_NullSource() {
        String result = StringUtils.replace(null, "old", "new");
        assertNull(result);
    }

    @Test
    public void testReplace_NullSearch() {
        String result = StringUtils.replace("hello world", null, "new");
        assertEquals("hello world", result);
    }

    @Test
    public void testReplace_NullReplacement() {
        String result = StringUtils.replace("hello world", "world", null);
        assertEquals("hello world", result);
    }

    @Test
    public void testReplace_Multiple() {
        String result = StringUtils.replace("foo foo foo", "foo", "bar");
        assertEquals("bar bar bar", result);
    }

    @Test
    public void testReplace_CaseInsensitive() {
        String result = StringUtils.replace("Hello HELLO hello", "hello", "hi", false);
        assertEquals("hi hi hi", result);
    }

    @Test
    public void testReplace_CaseSensitive() {
        String result = StringUtils.replace("Hello HELLO hello", "hello", "hi", true);
        assertEquals("Hello HELLO hi", result);
    }

    @Test
    public void testReplace_NoMatch() {
        String result = StringUtils.replace("hello world", "foo", "bar");
        assertEquals("hello world", result);
    }

    @Test
    public void testRepeat_Basic() {
        String result = StringUtils.repeat("ab", 3);
        assertEquals("ababab", result);
    }

    @Test
    public void testRepeat_Zero() {
        String result = StringUtils.repeat("test", 0);
        assertEquals("", result);
    }

    @Test
    public void testRepeat_One() {
        String result = StringUtils.repeat("test", 1);
        assertEquals("test", result);
    }

    @Test
    public void testRepeat_Null() {
        String result = StringUtils.repeat(null, 5);
        assertNull(result);
    }

    @Test
    public void testConvertTabsToSpaces_Basic() {
        String result = StringUtils.convertTabsToSpaces("hello\tworld", 4);
        assertEquals("hello   world", result);
    }

    @Test
    public void testConvertTabsToSpaces_NoTabs() {
        String result = StringUtils.convertTabsToSpaces("hello world", 4);
        assertEquals("hello world", result);
    }

    @Test
    public void testConvertTabsToSpaces_MultipleTabs() {
        String result = StringUtils.convertTabsToSpaces("\t\t", 4);
        assertEquals("        ", result);
    }

    @Test
    public void testConvertTabsToSpaces_TabAtStart() {
        String result = StringUtils.convertTabsToSpaces("\ttest", 4);
        assertEquals("    test", result);
    }

    @Test
    public void testConvertTabsToSpaces_TabWidth8() {
        String result = StringUtils.convertTabsToSpaces("a\tb", 8);
        assertEquals("a       b", result);
    }

    @Test
    public void testConvertTabsToSpaces_TabAlignment() {
        // Tab should align to next multiple of tabWidth
        String result = StringUtils.convertTabsToSpaces("ab\tcd", 4);
        assertEquals("ab  cd", result); // 2 spaces to reach position 4
    }

    @Test
    public void testConvertTabsToSpaces_ComplexAlignment() {
        String result = StringUtils.convertTabsToSpaces("a\tb\tc\td", 4);
        assertEquals("a   b   c   d", result);
    }
}
