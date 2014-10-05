/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: JHighlightVersion.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Provides acces to the version number of this JHighlight release.
 * 
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public class JHighlightVersion
{
	private String  mVersion = null;
	
	JHighlightVersion()
	{
		URL version_url = getClass().getClassLoader().getResource("JHIGHLIGHT_VERSION");
		if (version_url != null)
		{
			try
			{
				URLConnection connection = version_url.openConnection();
				connection.setUseCaches(false);
				InputStream inputStream = connection.getInputStream();
				
				byte[]                  buffer = new byte[64];
				int                     return_value = -1;
				ByteArrayOutputStream   output_stream = new ByteArrayOutputStream(buffer.length);
				
				try
				{
					return_value = inputStream.read(buffer);
					
					while (-1 != return_value)
					{
						output_stream.write(buffer, 0, return_value);
						return_value = inputStream.read(buffer);
					}
				}
				finally
				{
					output_stream.close();
					inputStream.close();
				}
				
				mVersion = output_stream.toString("UTF-8");
			}
			catch (IOException e)
			{
				mVersion = null;
			}
		}
		
		if (mVersion != null)
		{
			mVersion = mVersion.trim();
		}
		if (null == mVersion)
		{
			mVersion = "(unknown version)";
		}
	}
	
	private String getVersionString()
	{
		return mVersion;
	}
	
	/**
	 * Returns the version number of this JHighlight release.
	 * 
	 * @return the version number
	 * @since 1.0
	 */
	public static String getVersion()
	{
		return JHighlightVersionSingleton.INSTANCE.getVersionString();
	}
}
