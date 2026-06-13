/*
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 */
package com.uwyn.jhighlight.renderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** Backward-compatibility renderer interface.
 *
 * @deprecated Use {@link org.codelibs.jhighlight.renderer.Renderer} instead.
 */
@Deprecated // for backward compatibility
public interface Renderer {
    /** Highlights the given input stream and writes the result to the given output stream.
     *
     * @param name the name of the source (used to determine the type).
     * @param in the input stream to read the source from.
     * @param out the output stream to write the highlighted result to.
     * @param encoding the character encoding of the source.
     * @param fragment whether to output a fragment instead of a complete document.
     * @throws IOException if an I/O error occurs.
     */
    void highlight(String name, InputStream in, OutputStream out,
            String encoding, boolean fragment) throws IOException;

    /** Highlights the given source string and returns the highlighted result.
     *
     * @param name the name of the source (used to determine the type).
     * @param in the source to highlight.
     * @param encoding the character encoding of the source.
     * @param fragment whether to output a fragment instead of a complete document.
     * @return the highlighted source.
     * @throws IOException if an I/O error occurs.
     */
    String highlight(String name, String in, String encoding, boolean fragment)
            throws IOException;
}
