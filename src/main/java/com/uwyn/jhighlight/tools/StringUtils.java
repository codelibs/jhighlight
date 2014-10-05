/*
 * Copyright 2001-2006 Geert Bevin <gbevin[remove] at uwyn dot com>
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id: StringUtils.java 3108 2006-03-13 18:03:00Z gbevin $
 */
package com.uwyn.jhighlight.tools;

import com.uwyn.jhighlight.pcj.map.CharKeyOpenHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * General purpose class containing common <code>String</code> manipulation
 * methods.
 *
 * @author Geert Bevin (gbevin[remove] at uwyn dot com)
 * @version $Revision: 3108 $
 * @since 1.0
 */
public abstract class StringUtils
{
	private static final CharKeyOpenHashMap	mHtmlEncodeMap = new CharKeyOpenHashMap();
	
	static
	{
		// Html encoding mapping according to the HTML 4.0 spec
		// http://www.w3.org/TR/REC-html40/sgml/entities.html
		
		// Special characters for HTML
		mHtmlEncodeMap.put('\u0026', "&amp;");
		mHtmlEncodeMap.put('\u003C', "&lt;");
		mHtmlEncodeMap.put('\u003E', "&gt;");
		mHtmlEncodeMap.put('\u0022', "&quot;");
		
		mHtmlEncodeMap.put('\u0152', "&OElig;");
		mHtmlEncodeMap.put('\u0153', "&oelig;");
		mHtmlEncodeMap.put('\u0160', "&Scaron;");
		mHtmlEncodeMap.put('\u0161', "&scaron;");
		mHtmlEncodeMap.put('\u0178', "&Yuml;");
		mHtmlEncodeMap.put('\u02C6', "&circ;");
		mHtmlEncodeMap.put('\u02DC', "&tilde;");
		mHtmlEncodeMap.put('\u2002', "&ensp;");
		mHtmlEncodeMap.put('\u2003', "&emsp;");
		mHtmlEncodeMap.put('\u2009', "&thinsp;");
		mHtmlEncodeMap.put('\u200C', "&zwnj;");
		mHtmlEncodeMap.put('\u200D', "&zwj;");
		mHtmlEncodeMap.put('\u200E', "&lrm;");
		mHtmlEncodeMap.put('\u200F', "&rlm;");
		mHtmlEncodeMap.put('\u2013', "&ndash;");
		mHtmlEncodeMap.put('\u2014', "&mdash;");
		mHtmlEncodeMap.put('\u2018', "&lsquo;");
		mHtmlEncodeMap.put('\u2019', "&rsquo;");
		mHtmlEncodeMap.put('\u201A', "&sbquo;");
		mHtmlEncodeMap.put('\u201C', "&ldquo;");
		mHtmlEncodeMap.put('\u201D', "&rdquo;");
		mHtmlEncodeMap.put('\u201E', "&bdquo;");
		mHtmlEncodeMap.put('\u2020', "&dagger;");
		mHtmlEncodeMap.put('\u2021', "&Dagger;");
		mHtmlEncodeMap.put('\u2030', "&permil;");
		mHtmlEncodeMap.put('\u2039', "&lsaquo;");
		mHtmlEncodeMap.put('\u203A', "&rsaquo;");
		mHtmlEncodeMap.put('\u20AC', "&euro;");
		
		// Character entity references for ISO 8859-1 characters
		mHtmlEncodeMap.put('\u00A0', "&nbsp;");
		mHtmlEncodeMap.put('\u00A1', "&iexcl;");
		mHtmlEncodeMap.put('\u00A2', "&cent;");
		mHtmlEncodeMap.put('\u00A3', "&pound;");
		mHtmlEncodeMap.put('\u00A4', "&curren;");
		mHtmlEncodeMap.put('\u00A5', "&yen;");
		mHtmlEncodeMap.put('\u00A6', "&brvbar;");
		mHtmlEncodeMap.put('\u00A7', "&sect;");
		mHtmlEncodeMap.put('\u00A8', "&uml;");
		mHtmlEncodeMap.put('\u00A9', "&copy;");
		mHtmlEncodeMap.put('\u00AA', "&ordf;");
		mHtmlEncodeMap.put('\u00AB', "&laquo;");
		mHtmlEncodeMap.put('\u00AC', "&not;");
		mHtmlEncodeMap.put('\u00AD', "&shy;");
		mHtmlEncodeMap.put('\u00AE', "&reg;");
		mHtmlEncodeMap.put('\u00AF', "&macr;");
		mHtmlEncodeMap.put('\u00B0', "&deg;");
		mHtmlEncodeMap.put('\u00B1', "&plusmn;");
		mHtmlEncodeMap.put('\u00B2', "&sup2;");
		mHtmlEncodeMap.put('\u00B3', "&sup3;");
		mHtmlEncodeMap.put('\u00B4', "&acute;");
		mHtmlEncodeMap.put('\u00B5', "&micro;");
		mHtmlEncodeMap.put('\u00B6', "&para;");
		mHtmlEncodeMap.put('\u00B7', "&middot;");
		mHtmlEncodeMap.put('\u00B8', "&cedil;");
		mHtmlEncodeMap.put('\u00B9', "&sup1;");
		mHtmlEncodeMap.put('\u00BA', "&ordm;");
		mHtmlEncodeMap.put('\u00BB', "&raquo;");
		mHtmlEncodeMap.put('\u00BC', "&frac14;");
		mHtmlEncodeMap.put('\u00BD', "&frac12;");
		mHtmlEncodeMap.put('\u00BE', "&frac34;");
		mHtmlEncodeMap.put('\u00BF', "&iquest;");
		mHtmlEncodeMap.put('\u00C0', "&Agrave;");
		mHtmlEncodeMap.put('\u00C1', "&Aacute;");
		mHtmlEncodeMap.put('\u00C2', "&Acirc;");
		mHtmlEncodeMap.put('\u00C3', "&Atilde;");
		mHtmlEncodeMap.put('\u00C4', "&Auml;");
		mHtmlEncodeMap.put('\u00C5', "&Aring;");
		mHtmlEncodeMap.put('\u00C6', "&AElig;");
		mHtmlEncodeMap.put('\u00C7', "&Ccedil;");
		mHtmlEncodeMap.put('\u00C8', "&Egrave;");
		mHtmlEncodeMap.put('\u00C9', "&Eacute;");
		mHtmlEncodeMap.put('\u00CA', "&Ecirc;");
		mHtmlEncodeMap.put('\u00CB', "&Euml;");
		mHtmlEncodeMap.put('\u00CC', "&Igrave;");
		mHtmlEncodeMap.put('\u00CD', "&Iacute;");
		mHtmlEncodeMap.put('\u00CE', "&Icirc;");
		mHtmlEncodeMap.put('\u00CF', "&Iuml;");
		mHtmlEncodeMap.put('\u00D0', "&ETH;");
		mHtmlEncodeMap.put('\u00D1', "&Ntilde;");
		mHtmlEncodeMap.put('\u00D2', "&Ograve;");
		mHtmlEncodeMap.put('\u00D3', "&Oacute;");
		mHtmlEncodeMap.put('\u00D4', "&Ocirc;");
		mHtmlEncodeMap.put('\u00D5', "&Otilde;");
		mHtmlEncodeMap.put('\u00D6', "&Ouml;");
		mHtmlEncodeMap.put('\u00D7', "&times;");
		mHtmlEncodeMap.put('\u00D8', "&Oslash;");
		mHtmlEncodeMap.put('\u00D9', "&Ugrave;");
		mHtmlEncodeMap.put('\u00DA', "&Uacute;");
		mHtmlEncodeMap.put('\u00DB', "&Ucirc;");
		mHtmlEncodeMap.put('\u00DC', "&Uuml;");
		mHtmlEncodeMap.put('\u00DD', "&Yacute;");
		mHtmlEncodeMap.put('\u00DE', "&THORN;");
		mHtmlEncodeMap.put('\u00DF', "&szlig;");
		mHtmlEncodeMap.put('\u00E0', "&agrave;");
		mHtmlEncodeMap.put('\u00E1', "&aacute;");
		mHtmlEncodeMap.put('\u00E2', "&acirc;");
		mHtmlEncodeMap.put('\u00E3', "&atilde;");
		mHtmlEncodeMap.put('\u00E4', "&auml;");
		mHtmlEncodeMap.put('\u00E5', "&aring;");
		mHtmlEncodeMap.put('\u00E6', "&aelig;");
		mHtmlEncodeMap.put('\u00E7', "&ccedil;");
		mHtmlEncodeMap.put('\u00E8', "&egrave;");
		mHtmlEncodeMap.put('\u00E9', "&eacute;");
		mHtmlEncodeMap.put('\u00EA', "&ecirc;");
		mHtmlEncodeMap.put('\u00EB', "&euml;");
		mHtmlEncodeMap.put('\u00EC', "&igrave;");
		mHtmlEncodeMap.put('\u00ED', "&iacute;");
		mHtmlEncodeMap.put('\u00EE', "&icirc;");
		mHtmlEncodeMap.put('\u00EF', "&iuml;");
		mHtmlEncodeMap.put('\u00F0', "&eth;");
		mHtmlEncodeMap.put('\u00F1', "&ntilde;");
		mHtmlEncodeMap.put('\u00F2', "&ograve;");
		mHtmlEncodeMap.put('\u00F3', "&oacute;");
		mHtmlEncodeMap.put('\u00F4', "&ocirc;");
		mHtmlEncodeMap.put('\u00F5', "&otilde;");
		mHtmlEncodeMap.put('\u00F6', "&ouml;");
		mHtmlEncodeMap.put('\u00F7', "&divide;");
		mHtmlEncodeMap.put('\u00F8', "&oslash;");
		mHtmlEncodeMap.put('\u00F9', "&ugrave;");
		mHtmlEncodeMap.put('\u00FA', "&uacute;");
		mHtmlEncodeMap.put('\u00FB', "&ucirc;");
		mHtmlEncodeMap.put('\u00FC', "&uuml;");
		mHtmlEncodeMap.put('\u00FD', "&yacute;");
		mHtmlEncodeMap.put('\u00FE', "&thorn;");
		mHtmlEncodeMap.put('\u00FF', "&yuml;");
		
		// Mathematical, Greek and Symbolic characters for HTML
		mHtmlEncodeMap.put('\u0192', "&fnof;");
		mHtmlEncodeMap.put('\u0391', "&Alpha;");
		mHtmlEncodeMap.put('\u0392', "&Beta;");
		mHtmlEncodeMap.put('\u0393', "&Gamma;");
		mHtmlEncodeMap.put('\u0394', "&Delta;");
		mHtmlEncodeMap.put('\u0395', "&Epsilon;");
		mHtmlEncodeMap.put('\u0396', "&Zeta;");
		mHtmlEncodeMap.put('\u0397', "&Eta;");
		mHtmlEncodeMap.put('\u0398', "&Theta;");
		mHtmlEncodeMap.put('\u0399', "&Iota;");
		mHtmlEncodeMap.put('\u039A', "&Kappa;");
		mHtmlEncodeMap.put('\u039B', "&Lambda;");
		mHtmlEncodeMap.put('\u039C', "&Mu;");
		mHtmlEncodeMap.put('\u039D', "&Nu;");
		mHtmlEncodeMap.put('\u039E', "&Xi;");
		mHtmlEncodeMap.put('\u039F', "&Omicron;");
		mHtmlEncodeMap.put('\u03A0', "&Pi;");
		mHtmlEncodeMap.put('\u03A1', "&Rho;");
		mHtmlEncodeMap.put('\u03A3', "&Sigma;");
		mHtmlEncodeMap.put('\u03A4', "&Tau;");
		mHtmlEncodeMap.put('\u03A5', "&Upsilon;");
		mHtmlEncodeMap.put('\u03A6', "&Phi;");
		mHtmlEncodeMap.put('\u03A7', "&Chi;");
		mHtmlEncodeMap.put('\u03A8', "&Psi;");
		mHtmlEncodeMap.put('\u03A9', "&Omega;");
		mHtmlEncodeMap.put('\u03B1', "&alpha;");
		mHtmlEncodeMap.put('\u03B2', "&beta;");
		mHtmlEncodeMap.put('\u03B3', "&gamma;");
		mHtmlEncodeMap.put('\u03B4', "&delta;");
		mHtmlEncodeMap.put('\u03B5', "&epsilon;");
		mHtmlEncodeMap.put('\u03B6', "&zeta;");
		mHtmlEncodeMap.put('\u03B7', "&eta;");
		mHtmlEncodeMap.put('\u03B8', "&theta;");
		mHtmlEncodeMap.put('\u03B9', "&iota;");
		mHtmlEncodeMap.put('\u03BA', "&kappa;");
		mHtmlEncodeMap.put('\u03BB', "&lambda;");
		mHtmlEncodeMap.put('\u03BC', "&mu;");
		mHtmlEncodeMap.put('\u03BD', "&nu;");
		mHtmlEncodeMap.put('\u03BE', "&xi;");
		mHtmlEncodeMap.put('\u03BF', "&omicron;");
		mHtmlEncodeMap.put('\u03C0', "&pi;");
		mHtmlEncodeMap.put('\u03C1', "&rho;");
		mHtmlEncodeMap.put('\u03C2', "&sigmaf;");
		mHtmlEncodeMap.put('\u03C3', "&sigma;");
		mHtmlEncodeMap.put('\u03C4', "&tau;");
		mHtmlEncodeMap.put('\u03C5', "&upsilon;");
		mHtmlEncodeMap.put('\u03C6', "&phi;");
		mHtmlEncodeMap.put('\u03C7', "&chi;");
		mHtmlEncodeMap.put('\u03C8', "&psi;");
		mHtmlEncodeMap.put('\u03C9', "&omega;");
		mHtmlEncodeMap.put('\u03D1', "&thetasym;");
		mHtmlEncodeMap.put('\u03D2', "&upsih;");
		mHtmlEncodeMap.put('\u03D6', "&piv;");
		mHtmlEncodeMap.put('\u2022', "&bull;");
		mHtmlEncodeMap.put('\u2026', "&hellip;");
		mHtmlEncodeMap.put('\u2032', "&prime;");
		mHtmlEncodeMap.put('\u2033', "&Prime;");
		mHtmlEncodeMap.put('\u203E', "&oline;");
		mHtmlEncodeMap.put('\u2044', "&frasl;");
		mHtmlEncodeMap.put('\u2118', "&weierp;");
		mHtmlEncodeMap.put('\u2111', "&image;");
		mHtmlEncodeMap.put('\u211C', "&real;");
		mHtmlEncodeMap.put('\u2122', "&trade;");
		mHtmlEncodeMap.put('\u2135', "&alefsym;");
		mHtmlEncodeMap.put('\u2190', "&larr;");
		mHtmlEncodeMap.put('\u2191', "&uarr;");
		mHtmlEncodeMap.put('\u2192', "&rarr;");
		mHtmlEncodeMap.put('\u2193', "&darr;");
		mHtmlEncodeMap.put('\u2194', "&harr;");
		mHtmlEncodeMap.put('\u21B5', "&crarr;");
		mHtmlEncodeMap.put('\u21D0', "&lArr;");
		mHtmlEncodeMap.put('\u21D1', "&uArr;");
		mHtmlEncodeMap.put('\u21D2', "&rArr;");
		mHtmlEncodeMap.put('\u21D3', "&dArr;");
		mHtmlEncodeMap.put('\u21D4', "&hArr;");
		mHtmlEncodeMap.put('\u2200', "&forall;");
		mHtmlEncodeMap.put('\u2202', "&part;");
		mHtmlEncodeMap.put('\u2203', "&exist;");
		mHtmlEncodeMap.put('\u2205', "&empty;");
		mHtmlEncodeMap.put('\u2207', "&nabla;");
		mHtmlEncodeMap.put('\u2208', "&isin;");
		mHtmlEncodeMap.put('\u2209', "&notin;");
		mHtmlEncodeMap.put('\u220B', "&ni;");
		mHtmlEncodeMap.put('\u220F', "&prod;");
		mHtmlEncodeMap.put('\u2211', "&sum;");
		mHtmlEncodeMap.put('\u2212', "&minus;");
		mHtmlEncodeMap.put('\u2217', "&lowast;");
		mHtmlEncodeMap.put('\u221A', "&radic;");
		mHtmlEncodeMap.put('\u221D', "&prop;");
		mHtmlEncodeMap.put('\u221E', "&infin;");
		mHtmlEncodeMap.put('\u2220', "&ang;");
		mHtmlEncodeMap.put('\u2227', "&and;");
		mHtmlEncodeMap.put('\u2228', "&or;");
		mHtmlEncodeMap.put('\u2229', "&cap;");
		mHtmlEncodeMap.put('\u222A', "&cup;");
		mHtmlEncodeMap.put('\u222B', "&int;");
		mHtmlEncodeMap.put('\u2234', "&there4;");
		mHtmlEncodeMap.put('\u223C', "&sim;");
		mHtmlEncodeMap.put('\u2245', "&cong;");
		mHtmlEncodeMap.put('\u2248', "&asymp;");
		mHtmlEncodeMap.put('\u2260', "&ne;");
		mHtmlEncodeMap.put('\u2261', "&equiv;");
		mHtmlEncodeMap.put('\u2264', "&le;");
		mHtmlEncodeMap.put('\u2265', "&ge;");
		mHtmlEncodeMap.put('\u2282', "&sub;");
		mHtmlEncodeMap.put('\u2283', "&sup;");
		mHtmlEncodeMap.put('\u2284', "&nsub;");
		mHtmlEncodeMap.put('\u2286', "&sube;");
		mHtmlEncodeMap.put('\u2287', "&supe;");
		mHtmlEncodeMap.put('\u2295', "&oplus;");
		mHtmlEncodeMap.put('\u2297', "&otimes;");
		mHtmlEncodeMap.put('\u22A5', "&perp;");
		mHtmlEncodeMap.put('\u22C5', "&sdot;");
		mHtmlEncodeMap.put('\u2308', "&lceil;");
		mHtmlEncodeMap.put('\u2309', "&rceil;");
		mHtmlEncodeMap.put('\u230A', "&lfloor;");
		mHtmlEncodeMap.put('\u230B', "&rfloor;");
		mHtmlEncodeMap.put('\u2329', "&lang;");
		mHtmlEncodeMap.put('\u232A', "&rang;");
		mHtmlEncodeMap.put('\u25CA', "&loz;");
		mHtmlEncodeMap.put('\u2660', "&spades;");
		mHtmlEncodeMap.put('\u2663', "&clubs;");
		mHtmlEncodeMap.put('\u2665', "&hearts;");
		mHtmlEncodeMap.put('\u2666', "&diams;");
	}
	
	private StringUtils()
	{
	}
	
	/**
	 * Transforms a provided <code>String</code> object into a new string,
	 * containing only valid Html characters.
	 *
	 * @param source The string that has to be transformed into a valid Html
	 * string.
	 *
	 * @return The encoded <code>String</code> object.
	 *
	 * @since 1.0
	 */
	public static String encodeHtml(String source)
	{
		return encode(source, mHtmlEncodeMap);
	}
	
	/**
	 * Transforms a provided <code>String</code> object into a new string,
	 * using the mapping that are provided through the supplied encoding table.
	 *
	 * @param source The string that has to be transformed into a valid string,
	 * using the mappings that are provided through the supplied encoding table.
	 * @param encodingTables A <code>Map</code> object containing the mappings to
	 * transform characters into valid entities. The keys of this map should be
	 * <code>Character</code> objects and the values <code>String</code>
	 * objects.
	 *
	 * @return The encoded <code>String</code> object.
	 *
	 * @since 1.0
	 */
	private static String encode(String source, CharKeyOpenHashMap encodingTable)
	{
		if (null == source)
		{
			return null;
		}
		
		if (null == encodingTable)
		{
			return source;
		}
		
		StringBuffer	encoded_string = null;
		char[]			string_to_encode_array = source.toCharArray();
		int				last_match = -1;
		int				difference = 0;
		
		for (int i = 0; i < string_to_encode_array.length; i++)
		{
			char char_to_encode = string_to_encode_array[i];
			
			if (encodingTable.containsKey(char_to_encode))
			{
				if (null == encoded_string)
				{
					encoded_string = new StringBuffer(source.length());
				}
				difference = i - (last_match + 1);
				if (difference > 0)
				{
					encoded_string.append(string_to_encode_array, last_match + 1, difference);
				}
				encoded_string.append(encodingTable.get(char_to_encode));
				last_match = i;
			}
		}
		
		if (null == encoded_string)
		{
			return source;
		}
		else
		{
			difference = string_to_encode_array.length - (last_match + 1);
			if (difference > 0)
			{
				encoded_string.append(string_to_encode_array, last_match + 1, difference);
			}
			return encoded_string.toString();
		}
	}
	
	/**
	 * Checks if the name filters through an including and an excluding
	 * regular expression.
	 *
	 * @param name The <code>String</code> that will be filtered.
	 * @param included The regular expressions that needs to succeed
	 * @param excluded The regular expressions that needs to fail
	 *
	 * @return <code>true</code> if the name filtered through correctly; or
	 * <p>
	 * <code>false</code> otherwise.
	 *
	 * @since 1.0
	 */
	public static boolean filter(String name, Pattern included, Pattern excluded)
	{
		Pattern[] included_array = null;
		if (included != null)
		{
			included_array = new Pattern[] {included};
		}
		
		Pattern[] excluded_array = null;
		if (excluded != null)
		{
			excluded_array = new Pattern[] {excluded};
		}
		
		return filter(name, included_array, excluded_array);
	}
	
	/**
	 * Checks if the name filters through a series of including and excluding
	 * regular expressions.
	 *
	 * @param name The <code>String</code> that will be filtered.
	 * @param included An array of regular expressions that need to succeed
	 * @param excluded An array of regular expressions that need to fail
	 *
	 * @return <code>true</code> if the name filtered through correctly; or
	 * <p>
	 * <code>false</code> otherwise.
	 *
	 * @since 1.0
	 */
	public static boolean filter(String name, Pattern[] included, Pattern[] excluded)
	{
		if (null == name)
		{
			return false;
		}
		
		boolean accepted = false;
		
		// retain only the includes
		if (null == included)
		{
			accepted = true;
		}
		else
		{
			Pattern pattern;
			for (int i = 0; i < included.length; i++)
			{
				pattern = included[i];
				
				if (pattern != null &&
					pattern.matcher(name).matches())
				{
					accepted = true;
					break;
				}
			}
		}
		
		// remove the excludes
		if (accepted &&
			excluded != null)
		{
			Pattern pattern;
			for (int i = 0; i < excluded.length; i++)
			{
				pattern = excluded[i];
				
				if (pattern != null &&
					pattern.matcher(name).matches())
				{
					accepted = false;
					break;
				}
			}
		}
		
		return accepted;
	}
	
	/**
	 * Splits a string into different parts, using a seperator string to detect
	 * the seperation boundaries in a case-sensitive manner. The seperator will
	 * not be included in the list of parts.
	 *
	 * @param source The string that will be split into parts.
	 * @param seperator The seperator string that will be used to determine the
	 * parts.
	 *
	 * @return An <code>ArrayList</code> containing the parts as
	 * <code>String</code> objects.
	 *
	 * @since 1.0
	 */
	public static ArrayList split(String source, String seperator)
	{
		return split(source, seperator, true);
	}
	
	/**
	 * Splits a string into different parts, using a seperator string to detect
	 * the seperation boundaries. The seperator will not be included in the list
	 * of parts.
	 *
	 * @param source The string that will be split into parts.
	 * @param seperator The seperator string that will be used to determine the
	 * parts.
	 * @param matchCase A <code>boolean</code> indicating if the match is going
	 * to be performed in a case-sensitive manner or not.
	 *
	 * @return An <code>ArrayList</code> containing the parts as
	 * <code>String</code> objects.
	 *
	 * @since 1.0
	 */
	public static ArrayList split(String source, String seperator, boolean matchCase)
	{
		ArrayList	substrings = new ArrayList();
		
		if (null == source)
		{
			return substrings;
		}
		
		if (null == seperator)
		{
			substrings.add(source);
			return substrings;
		}
		
		int		current_index = 0;
		int		delimiter_index = 0;
		String	element = null;
		
		String	source_lookup_reference = null;
		if (!matchCase)
		{
			source_lookup_reference = source.toLowerCase();
			seperator = seperator.toLowerCase();
		}
		else
		{
			source_lookup_reference = source;
		}
		
		while (current_index <= source_lookup_reference.length())
		{
			delimiter_index = source_lookup_reference.indexOf(seperator, current_index);
			
			if (-1 == delimiter_index)
			{
				element = new String(source.substring(current_index, source.length()));
				substrings.add(element);
				current_index = source.length() + 1;
			}
			else
			{
				element = new String(source.substring(current_index, delimiter_index));
				substrings.add(element);
				current_index = delimiter_index + seperator.length();
			}
		}
		
		return substrings;
	}
	
	/**
	 * Searches for a string within a specified string in a case-sensitive
	 * manner and replaces every match with another string.
	 *
	 * @param source The string in which the matching parts will be replaced.
	 * @param stringToReplace The string that will be searched for.
	 * @param replacementString The string that will replace each matching part.
	 *
	 * @return A new <code>String</code> object containing the replacement
	 * result.
	 *
	 * @since 1.0
	 */
	public static String replace(String source, String stringToReplace, String replacementString)
	{
		return replace(source, stringToReplace, replacementString, true);
	}
	
	/**
	 * Searches for a string within a specified string and replaces every match
	 * with another string.
	 *
	 * @param source The string in which the matching parts will be replaced.
	 * @param stringToReplace The string that will be searched for.
	 * @param replacementString The string that will replace each matching part.
	 * @param matchCase A <code>boolean</code> indicating if the match is going
	 * to be performed in a case-sensitive manner or not.
	 *
	 * @return A new <code>String</code> object containing the replacement
	 * result.
	 *
	 * @since 1.0
	 */
	public static String replace(String source, String stringToReplace, String replacementString, boolean matchCase)
	{
		if (null == source)
		{
			return null;
		}
		
		if (null == stringToReplace)
		{
			return source;
		}
		
		if (null == replacementString)
		{
			return source;
		}
		
		Iterator		string_parts = split(source, stringToReplace, matchCase).iterator();
		StringBuffer	new_string = new StringBuffer();
		
		synchronized (new_string) // speed increase by thread lock pre-allocation
		{
			while (string_parts.hasNext())
			{
				String string_part = (String)string_parts.next();
				new_string.append(string_part);
				if (string_parts.hasNext())
				{
					new_string.append(replacementString);
				}
			}
			
			return new_string.toString();
		}
	}
	
	/**
	 * Creates a new string that contains the provided string a number of times.
	 *
	 * @param source The string that will be repeated.
	 * @param count  The number of times that the string will be repeated.
	 * @return A new <code>String</code> object containing the repeated
	 * concatenation result.
	 *
	 * @since 1.0
	 */
	public static String repeat(String source, int count)
	{
		if (null == source)
		{
			return null;
		}
		
		StringBuffer new_string = new StringBuffer();
		synchronized (new_string) // speed increase by thread lock pre-allocation
		{
			while (count > 0)
			{
				new_string.append(source);
				count --;
			}
			
			return new_string.toString();
		}
	}
	
	/**
	 * Converts all tabs on a line to spaces according to the provided tab
	 * width.
	 *
	 * @param line The line whose tabs have to be converted.
	 * @param tabWidth The tab width.
	 * @return A new <code>String</code> object containing the line with the
	 * replaced tabs.
	 * @since 1.0
	 */
	public static String convertTabsToSpaces(String line, int tabWidth)
	{
		StringBuffer result = new StringBuffer();
		
		synchronized (result) // speed increase by thread lock pre-allocation
		{
			int tab_index = -1;
			int last_tab_index = 0;
			int added_chars = 0;
			int tab_size;
			while ((tab_index = line.indexOf("\t", last_tab_index)) != -1)
			{
				tab_size = tabWidth - ((tab_index + added_chars) % tabWidth);
				if (0 == tab_size)
				{
					tab_size = tabWidth;
				}
				added_chars += tab_size - 1;
				result.append(line.substring(last_tab_index, tab_index));
				result.append(StringUtils.repeat(" ", tab_size));
				last_tab_index = tab_index + 1;
			}
			if (0 == last_tab_index)
			{
				return line;
			}
			else
			{
				result.append(line.substring(last_tab_index));
			}
		}
		
		return result.toString();
	}
}


