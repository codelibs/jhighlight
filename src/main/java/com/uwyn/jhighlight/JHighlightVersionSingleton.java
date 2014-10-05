/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: JHighlightVersionSingleton.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight;

/**
 * Helper class to avoid Double Check Locking
 * and still have a thread-safe singleton pattern
 */
class JHighlightVersionSingleton
{
	static final JHighlightVersion	INSTANCE = new JHighlightVersion();
}

