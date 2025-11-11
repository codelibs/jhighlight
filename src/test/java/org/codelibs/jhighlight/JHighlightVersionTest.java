package org.codelibs.jhighlight;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JHighlightVersionTest {

    @Test
    public void testGetVersion() {
        String version = JHighlightVersion.getVersion();
        assertNotNull("Version should not be null", version);
        assertTrue("Version should not be empty", version.length() > 0);
    }

    @Test
    public void testVersionSingleton() {
        // Call getVersion multiple times to ensure singleton works correctly
        String version1 = JHighlightVersion.getVersion();
        String version2 = JHighlightVersion.getVersion();
        String version3 = JHighlightVersion.getVersion();

        assertNotNull(version1);
        assertNotNull(version2);
        assertNotNull(version3);

        // All calls should return the same instance/value
        assertTrue("Version should be consistent", version1.equals(version2));
        assertTrue("Version should be consistent", version2.equals(version3));
    }

    @Test
    public void testVersionFormat() {
        String version = JHighlightVersion.getVersion();
        assertNotNull(version);

        // Version should either be a proper version string or "(unknown version)"
        assertTrue("Version should have content",
                   version.contains(".") || version.contains("unknown"));
    }
}
