/*
 * Copyright 2001-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: FileUtilsErrorException.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.tools.exceptions;

/**
 * Exception that will be trigger when unexpected errors occur during the
 * functionalities of the {@link com.uwyn.jhighlight.tools.FileUtils} class.
 * 
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public class FileUtilsErrorException extends Exception
{
	public FileUtilsErrorException(String message)
	{
		super(message);
	}
	
	public FileUtilsErrorException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
