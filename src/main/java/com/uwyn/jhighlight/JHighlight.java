/*
 * Copyright 2004-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: JHighlight.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight;

import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import com.uwyn.jhighlight.tools.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Provides console access to the source code syntax highlighting for Java,
 * HTML, XHTML, XML and LZX files. The rendering will be done in HTML.
 * <p>The following file extensions will be processed: <code>.java</code>,
 * <code>.html</code>, <code>.htm</code>, <code>.xhtml</code>,
 * <code>.xml</code> and <code>.lzx</code>.
 * <p>Execute the highlighting with the following syntax:
 * <pre>java com.uwyn.jhighlight.JHighlight [--verbose] [--fragment] [-d destdir] [-e encoding] file|dir ...</pre>
 * <table border="0">
 * <tr>
 * <td><code>--verbose</code></td>
 * <td>Output messages about what the parser is doing.</td>
 * </tr>
 * <tr>
 * <td><code>--fragment</code></td>
 * <td>Output fragments instead of complete documents.</td>
 * </tr>
 * <tr>
 * <td><code>-d</code></td>
 * <td>Specify the destination directory</td>
 * </tr>
 * <tr>
 * <td><code>-e</code></td>
 * <td>Specify the encoding of the files</td>
 * </tr>
 * </table>
 * <p><a href="https://rife.dev.java.net">RIFE</a> template tags are also
 * supported and will be clearly highlighted.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public class JHighlight
{
	public static void main(String[] arguments) throws Throwable
	{
		String      destdir_name = null;
		boolean     verbose = false;
		String		encoding = null;
		boolean     fragment = false;
		ArrayList   names = new ArrayList();
		
		boolean valid_arguments = true;
		if (arguments.length < 1)
		{
			valid_arguments = false;
		}
		else
		{
			boolean next_is_destdir = false;
			boolean next_is_encoding = false;
			String argument;
			for (int i = 0; i < arguments.length; i++)
			{
				argument = arguments[i];
				if (next_is_destdir)
				{
					destdir_name = argument;
					next_is_destdir = false;
					continue;
				}
				
				if (next_is_encoding)
				{
					encoding = argument;
					next_is_encoding = false;
					continue;
				}
				
				if (argument.equals("-d"))
				{
					next_is_destdir = true;
					continue;
				}
				
				if (argument.equals("-e"))
				{
					next_is_encoding = true;
					continue;
				}
				
				if (argument.equals("--verbose"))
				{
					verbose = true;
					continue;
				}
				
				if (argument.equals("--fragment"))
				{
					fragment = true;
					continue;
				}
				
				names.add(argument);
			}
		}
		
		if (0 == names.size())
		{
			valid_arguments = false;
		}
		
		if (!valid_arguments)
		{
			System.err.println("Usage : java " + JHighlight.class.getName() + " [--verbose] [--fragment] [-d destdir] [-e encoding] file|dir ...");
			System.err.println("Generates highlighted XHTML files from all Java and XML source files");
			System.err.println("in the specified directories.");
			System.err.println("  --verbose  Output messages about what the parser is doing");
			System.err.println("  --fragment Output fragments instead of complete documents");
			System.err.println("  -d         Specify the destination directory");
			System.err.println("  -e         Specify the encoding of the files");
			System.exit(1);
		}
		
		File    destdir = null;
		if (destdir_name != null)
		{
			destdir = new File(destdir_name);
			if (!destdir.exists())
			{
				throw new IOException("The destination directory '" + destdir_name + "' doesn't exist.");
			}
			if (!destdir.canWrite())
			{
				throw new IOException("The destination directory '" + destdir_name + "' is not writable.");
			}
			if (!destdir.isDirectory())
			{
				throw new IOException("The destination directory '" + destdir_name + "' is not a directory.");
			}
		}
		
		Iterator    names_it = names.iterator();
		String      name;
		while (names_it.hasNext())
		{
			name = (String)names_it.next();
			
			File    location = new File(name);
			if (!location.exists())
			{
				throw new IOException("The source location '" + name + "' doesn't exist.");
			}
			if (!location.canRead())
			{
				throw new IOException("The source location '" + name + "' is not readable.");
			}
			
			if (!location.isDirectory())
			{
				File out = null;
				if (null == destdir)
				{
					out = new File(location.getAbsolutePath() + ".html");
				}
				else
				{
					out = new File(destdir, location.getName() + ".html");
				}
				
				highlightFile(location.getName(), location, out, encoding, fragment, verbose);
			}
			else
			{
				Set			supported_types = XhtmlRendererFactory.getSupportedTypes();
				Pattern[]	included = new Pattern[supported_types.size()];
				Pattern[]	excluded = new Pattern[supported_types.size()+5];
				excluded[0] = Pattern.compile(".*SCCS.*");
				excluded[0] = Pattern.compile(".*svn.*");
				excluded[0] = Pattern.compile(".*CVS.*");
				excluded[0] = Pattern.compile(".*jetty.*");
				excluded[0] = Pattern.compile(".*tomcat.*");
				
				Iterator 	types_it = supported_types.iterator();
				String		type;
				int			counter = 0;
				while (types_it.hasNext())
				{
					type = (String)types_it.next();
					included[counter] = Pattern.compile(".*\\."+type+"$");
					excluded[counter+5] = Pattern.compile(".*\\."+type+"\\.html\\.*");

					counter++;
				}

				ArrayList file_names = FileUtils.getFileList(location, included, excluded);
				
				Iterator    file_names_it = file_names.iterator();
				String      file_name;
				while (file_names_it.hasNext())
				{
					file_name = (String)file_names_it.next();
					
					File in = new File(location.getAbsolutePath() + File.separator + file_name);
					File out = null;
					if (null == destdir)
					{
						out = new File(location.getAbsolutePath() + File.separator + file_name + ".html");
					}
					else
					{
						out = new File(destdir, location.getName() + File.separator + file_name + ".html");
					}
					
					highlightFile(location.getName() + File.separator + file_name, in, out, encoding, fragment, verbose);
				}
			}
		}
	}
	
	private static void highlightFile(String name, File in, File out, String encoding, boolean fragment, boolean verbose)
	throws IOException
	{
		out.getParentFile().mkdirs();
		
		if (verbose)
		{
			System.out.print(name + " ... ");
		}
		
		XhtmlRendererFactory.getRenderer(FileUtils.getExtension(name))
			.highlight(name,
					   in.toURL().openStream(),
					   new FileOutputStream(out),
					   encoding,
					   fragment);
		
		if (verbose)
		{
			System.out.println("done.");
		}
	}
}
