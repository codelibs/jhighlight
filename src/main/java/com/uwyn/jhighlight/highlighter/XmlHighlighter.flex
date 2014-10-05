/*
 * Copyright 2000-2006 Omnicore Software, Hans Kratz & Dennis Strein GbR,
 *                     Geert Bevin <gbevin[remove] at uwyn dot com>.
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id$
 */
package com.uwyn.jhighlight.highlighter;

import java.io.Reader;
import java.io.IOException;

%%

%class XmlHighlighter
%implements ExplicitStateHighlighter

%unicode
%pack
%buffer 128
%public

%int

%{
	/* styles */
	
	public static final byte PLAIN_STYLE = 1;
	public static final byte CHAR_DATA = 2;
	public static final byte TAG_SYMBOLS = 3;
	public static final byte COMMENT = 4;
	public static final byte ATTRIBUTE_VALUE = 5;
	public static final byte ATTRIBUTE_NAME = 6;
	public static final byte PROCESSING_INSTRUCTION = 7;
	public static final byte TAG_NAME = 8;
	public static final byte RIFE_TAG = 9;
	public static final byte RIFE_NAME = 10;

	/* Highlighter implementation */
	
	public int getStyleCount()
	{
		return 10;
	}
	
	public byte getStartState()
	{
		return YYINITIAL+1;
	}
	
	public byte getCurrentState()
	{
		return (byte) (yystate()+1);
	}
	
	public void setState(byte newState)
	{
		yybegin(newState-1);
	}
	
	public byte getNextToken()
	{
		try
		{
			return (byte) yylex();
		}
		catch (IOException e)
		{
			throw new InternalError();
		}
	}
	
	public int getTokenLength()
	{
		return yylength();
	}
	
	public void setReader(Reader r)
	{
		this.zzReader = r;
	}

	public XmlHighlighter()
	{
	}
	
	private int mReturningState;
	private int mReturningStateTag;
%}

/* main character classes */
WhiteSpace = [ \t\f]
WhiteSpaceNewline = [ \t\f\n\r]

/* identifiers */
Letter = ([:jletterdigit:]|"."|"-"|"_"|":")
Name = ([:jletter:]|"_"|":") {Letter}*

RifeBeginStartComment = ("<!--"{WhiteSpaceNewline}*("V"|"B"|"BV"|"I"){WhiteSpace})
RifeBeginStopComment = ("-->")
RifeEndComment = ("<!--/"{WhiteSpaceNewline}*("V"|"B"|"BV"){WhiteSpaceNewline}*"-->")

RifeBeginStartCompact = ("[!"{WhiteSpaceNewline}*("V"|"B"|"BV"|"I"){WhiteSpace})
RifeBeginStopCompact = ("]")
RifeEndCompact = ("[!/"{WhiteSpaceNewline}*("V"|"B"|"BV"){WhiteSpaceNewline}*"]")

RifeBeginStartVelocity = ("${"{WhiteSpaceNewline}*("v"|"b"|"bv"|"i"){WhiteSpace})
RifeBeginStopVelocity = ("/"? "}")
RifeEndVelocity = ("${/"{WhiteSpaceNewline}*("v"|"b"|"bv"){WhiteSpaceNewline}*"}")

RifeBeginStartRegular = ("<r:"("v"|"b"|"bv"|"i"){WhiteSpace})
RifeBeginStopRegular = (">")
RifeEndRegular = ("</r:"("v"|"b"|"bv"){WhiteSpace}*">")

%state IN_RIFE_TAG_COMMENT, IN_RIFE_TAG_COMPACT, IN_RIFE_TAG_VELOCITY, IN_RIFE_TAG_REGULAR, IN_RIFE_NAME_SINGLEQUOTED, IN_RIFE_NAME_QUOTED, IN_RIFE_NAME, IN_COMMENT, TAG_START, IN_TAG, IN_SINGLE_QUOTE_STRING, IN_DOUBLE_QUOTE_STRING, IN_PROCESSING_INSTRUCTION, IN_CDATA_SECTION

%%

<YYINITIAL> {

// tokens...

	"<" { yybegin(TAG_START); return TAG_SYMBOLS; }
	"</" { yybegin(TAG_START); return TAG_SYMBOLS; }

	{RifeBeginStartComment} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMMENT); return RIFE_TAG; }
	{RifeEndComment} { return RIFE_TAG; }
	{RifeBeginStartCompact} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMPACT); return RIFE_TAG; }
	{RifeEndCompact} { return RIFE_TAG; }
	{RifeBeginStartVelocity} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_VELOCITY); return RIFE_TAG; }
	{RifeEndVelocity} { return RIFE_TAG; }
	{RifeBeginStartRegular} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_REGULAR); return RIFE_TAG; }
	{RifeEndRegular} { return RIFE_TAG; }

	"<!--" { yybegin(IN_COMMENT); return COMMENT; }

	"<?" { yybegin(IN_PROCESSING_INSTRUCTION); return PROCESSING_INSTRUCTION; }

	"<![CDATA[" { yybegin(IN_CDATA_SECTION); return CHAR_DATA; }

	\n|{WhiteSpace}*|. { return PLAIN_STYLE; }
}

<TAG_START> {
	{Name} { yybegin(IN_TAG); return TAG_NAME; }
	
	">" { yybegin(YYINITIAL); return TAG_SYMBOLS; }
	. { yybegin(IN_TAG); return PLAIN_STYLE; }
}

<IN_TAG> {
	{RifeBeginStartComment} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMMENT); return RIFE_TAG; }
	{RifeEndComment} { return RIFE_TAG; }
	{RifeBeginStartCompact} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMPACT); return RIFE_TAG; }
	{RifeEndCompact} { return RIFE_TAG; }
	{RifeBeginStartVelocity} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_VELOCITY); return RIFE_TAG; }
	{RifeEndVelocity} { return RIFE_TAG; }
	{RifeBeginStartRegular} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_REGULAR); return RIFE_TAG; }
	{RifeEndRegular} { return RIFE_TAG; }

	"/>" { yybegin(YYINITIAL); return TAG_SYMBOLS; }
	">" { yybegin(YYINITIAL); return TAG_SYMBOLS; }
	"=" { return TAG_SYMBOLS; }
	
	{Name} { return ATTRIBUTE_NAME; }
	
	"\'" { yybegin(IN_SINGLE_QUOTE_STRING); return ATTRIBUTE_VALUE; }
	"\"" { yybegin(IN_DOUBLE_QUOTE_STRING); return ATTRIBUTE_VALUE; }
	. { return PLAIN_STYLE; }
}

<IN_SINGLE_QUOTE_STRING> {
	{RifeBeginStartComment} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMMENT); return RIFE_TAG; }
	{RifeEndComment} { return RIFE_TAG; }
	{RifeBeginStartCompact} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMPACT); return RIFE_TAG; }
	{RifeEndCompact} { return RIFE_TAG; }
	{RifeBeginStartVelocity} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_VELOCITY); return RIFE_TAG; }
	{RifeEndVelocity} { return RIFE_TAG; }
	{RifeBeginStartRegular} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_REGULAR); return RIFE_TAG; }
	{RifeEndRegular} { return RIFE_TAG; }

	[^\'\n\[\]<>{}]* { return ATTRIBUTE_VALUE; }
	\[ { return ATTRIBUTE_VALUE; }
	\] { return ATTRIBUTE_VALUE; }
	\{ { return ATTRIBUTE_VALUE; }
	\} { return ATTRIBUTE_VALUE; }
	\n { return ATTRIBUTE_VALUE; }
	\' { yybegin(IN_TAG); return ATTRIBUTE_VALUE; }
	. { yybegin(IN_TAG); return TAG_SYMBOLS; }
}

<IN_DOUBLE_QUOTE_STRING> {
	{RifeBeginStartComment} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMMENT); return RIFE_TAG; }
	{RifeEndComment} { return RIFE_TAG; }
	{RifeBeginStartCompact} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_COMPACT); return RIFE_TAG; }
	{RifeEndCompact} { return RIFE_TAG; }
	{RifeBeginStartVelocity} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_VELOCITY); return RIFE_TAG; }
	{RifeEndVelocity} { return RIFE_TAG; }
	{RifeBeginStartRegular} { mReturningState = yystate(); yybegin(IN_RIFE_TAG_REGULAR); return RIFE_TAG; }
	{RifeEndRegular} { return RIFE_TAG; }

	[^\"\n\[\]<>{}]* { return ATTRIBUTE_VALUE; }
	\[ { return ATTRIBUTE_VALUE; }
	\] { return ATTRIBUTE_VALUE; }
	\{ { return ATTRIBUTE_VALUE; }
	\} { return ATTRIBUTE_VALUE; }
	\n { return ATTRIBUTE_VALUE; }
	\" { yybegin(IN_TAG); return ATTRIBUTE_VALUE; }
	. { yybegin(IN_TAG); return TAG_SYMBOLS; }
}

<IN_PROCESSING_INSTRUCTION> {
	([^?\n]| "?"+ [^>?\n])* (\n | "?"\n)  { return PROCESSING_INSTRUCTION; }
	([^?\n]| "?"+ [^>?\n])* "?"+ ">" { yybegin(YYINITIAL); return PROCESSING_INSTRUCTION; }
}

<IN_CDATA_SECTION> {
	[^\]\n]* { return CHAR_DATA; }
	"]]>" { yybegin(YYINITIAL); return CHAR_DATA; }
	.  { return CHAR_DATA; }
}

<IN_COMMENT> {
	[^\-\n]* { return COMMENT; }
	"-->" { yybegin(YYINITIAL); return COMMENT; }
	. { return COMMENT; }
}

<IN_RIFE_TAG_COMMENT> {
	[^\-\>\']* { return RIFE_TAG; }
	{RifeBeginStopComment} { yybegin(mReturningState); return RIFE_TAG; }
	"\'" { mReturningStateTag = yystate(); yybegin(IN_RIFE_NAME_SINGLEQUOTED); return RIFE_NAME; }
	. { return RIFE_TAG; }
}

<IN_RIFE_TAG_COMPACT> {
	[^\]\']* { return RIFE_TAG; }
	{RifeBeginStopCompact} { yybegin(mReturningState); return RIFE_TAG; }
	"\'" { mReturningStateTag = yystate(); yybegin(IN_RIFE_NAME_SINGLEQUOTED); return RIFE_NAME; }
	. { return RIFE_TAG; }
}

<IN_RIFE_TAG_VELOCITY> {
	{RifeBeginStopVelocity} { yybegin(mReturningState); return RIFE_TAG; }
	[^\s\t\}]* { mReturningStateTag = yystate(); yybegin(IN_RIFE_NAME); return RIFE_NAME; }
	. { return RIFE_TAG; }
}

<IN_RIFE_TAG_REGULAR> {
	[^\>\"]* { return RIFE_TAG; }
	{RifeBeginStopRegular} { yybegin(mReturningState); return RIFE_TAG; }
	"\"" { mReturningStateTag = yystate(); yybegin(IN_RIFE_NAME_QUOTED); return RIFE_NAME; }
	. { return RIFE_TAG; }
}

<IN_RIFE_NAME_SINGLEQUOTED> {
	\' { yybegin(mReturningStateTag); return RIFE_NAME; }
	. { return RIFE_NAME; }
}

<IN_RIFE_NAME_QUOTED> {
	\" { yybegin(mReturningStateTag); return RIFE_NAME; }
	. { return RIFE_NAME; }
}

<IN_RIFE_NAME> {
	{RifeBeginStopVelocity} { yybegin(mReturningState); return RIFE_TAG; }
	. { return RIFE_NAME; }
}

/* error fallback */

.|\n                             { return PLAIN_STYLE; }
