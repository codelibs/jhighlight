/*
 * Copyright 2000-2006 Omnicore Software, Hans Kratz & Dennis Strein GbR,
 *                     Geert Bevin <gbevin[remove] at uwyn dot com>.
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: ExplicitStateHighlighter.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.highlighter;

import java.io.IOException;
import java.io.Reader;

/**
 * Provides access to the lexical scanning of a highlighted language.
 * 
 * @author Omnicore Software
 * @author Hans Kratz &amp; Dennis Strein GbR
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public interface ExplicitStateHighlighter
{
	/**
	 * Sets the reader that will be used to receive the text data.
	 * 
	 * @param reader the <code>Reader</code> that has to be used
	 */
	void setReader(Reader reader);

	/**
	 * Obtain the next token from the scanner.
	 * 
	 * @return one of the tokens that are define in the scanner
	 * @exception IOException when an error occurred during the parsing of
	 * the reader
	 */
	byte getNextToken() throws IOException;

	/**
	 * Returns the length of the matched text region.
	 * 
	 * @return the length of the matched text region
	 */
	int getTokenLength();
}
