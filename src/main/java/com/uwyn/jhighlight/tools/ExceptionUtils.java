/*
 * Copyright 2001-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: ExceptionUtils.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.tools;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Collection of utility methods to work with exceptions.
 * 
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public abstract class ExceptionUtils
{
	private ExceptionUtils()
	{
	}
	
	/**
	 * Obtains the entire stracktrace of an exception and converts it into a
	 * string.
	 * 
	 * @param exception the exception whose stacktrace has to be converted
	 * @return the stracktrace, converted into a string
	 * @since 1.0
	 */
	public static String getExceptionStackTrace(Throwable exception)
	{
		if (null == exception)  throw new IllegalArgumentException("exception can't be null;");
		
		String stack_trace = null;
		
		StringWriter string_writer = new StringWriter();
		PrintWriter print_writer = new PrintWriter(string_writer);
		
		exception.printStackTrace(print_writer);
		
		stack_trace = string_writer.getBuffer().toString();
		
		print_writer.close();
		
		try
		{
			string_writer.close();
		}
		// JDK 1.2.2 compatibility
		catch (Throwable e2)
		{
		}
		
		return stack_trace;
	}
}

