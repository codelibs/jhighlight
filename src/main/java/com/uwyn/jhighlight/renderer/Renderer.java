/*
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 */
package com.uwyn.jhighlight.renderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Deprecated // for backward compatibility
public interface Renderer {
    void highlight(String name, InputStream in, OutputStream out,
            String encoding, boolean fragment) throws IOException;

    String highlight(String name, String in, String encoding, boolean fragment)
            throws IOException;
}
