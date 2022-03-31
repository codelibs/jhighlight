package org.codelibs.jhighlight.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}
