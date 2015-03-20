/*
 * Copyright 2000-2006 Omnicore Software, Hans Kratz & Dennis Strein GbR,
 *                     Geert Bevin <gbevin[remove] at uwyn dot com>,
 *					   Ulf Dittmer (ulf[remove] at ulfdittmer dot com).
 * Distributed under the terms of either:
 * - the common development and distribution license (CDDL), v1.0; or
 * - the GNU Lesser General Public License, v2.1 or later
 * $Id$
 */
package com.uwyn.jhighlight.highlighter;

import java.io.Reader;
import java.io.IOException;

%%

%class JavaScriptHighlighter
%implements ExplicitStateHighlighter

%unicode
%pack

%buffer 128

%public

%int

%{
	/* styles */
	
	public static final byte PLAIN_STYLE = 1;
	public static final byte KEYWORD_STYLE = 2;
	public static final byte OPERATOR_STYLE = 3;
	public static final byte SEPARATOR_STYLE = 4;
	public static final byte LITERAL_STYLE = 5;
	public static final byte JAVA_COMMENT_STYLE = 6;
	
	/* Highlighter implementation */
	
	public int getStyleCount()
	{
		return 6;
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
	throws IOException
	{
		return (byte) yylex();
	}
	
	public int getTokenLength()
	{
		return yylength();
	}
	
	public void setReader(Reader r)
	{
		this.zzReader = r;
	}

	public JavaScriptHighlighter()
	{
	}
%}

/* main character classes */

WhiteSpace = [ \t\f]

/* identifiers */

ConstantIdentifier = {SimpleConstantIdentifier}
SimpleConstantIdentifier = [A-Z0-9_]+

Identifier = [:jletter:][:jletterdigit:]*

/* int literals */

DecLiteral = 0 | [1-9][0-9]*

/* float literals */

FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}|{FLit4})

FLit1 = [0-9]+ \. [0-9]* {Exponent}?
FLit2 = \. [0-9]+ {Exponent}?
FLit3 = [0-9]+ {Exponent}
FLit4 = [0-9]+ {Exponent}?

Exponent = [eE] [+\-]? [0-9]+

%state IN_COMMENT

%%

<YYINITIAL> {

  /* keywords */
  "break" |
  "case" |
  "continue" |
  "do" |
  "else" |
  "for" |
  "default" |
  "new" |
  "in" |
  "goto" |
  "if" |
  "switch" |
  "return" |
  "while" |
  "var" |
  "function" |
  "with" |
  "const" |
  "this" { return KEYWORD_STYLE; }

  /* literals */
  "true" |
  "false" |
  "null" |

  (\" ( [^\"\n\\] | \\[^\n] )* (\n | \\\n | \")) |
  (\' ( [^\'\n\\] | \\[^\n] )* (\n | \\\n | \')) |

  {DecLiteral} |
  {FloatLiteral}
	{ return LITERAL_STYLE; }
  
  /* separators */
  "(" |
  ")" |
  "{" |
  "}" |
  "[" |
  "]" |
  ";" |
  "," |
  "."                          { return SEPARATOR_STYLE; }
  
  /* operators */
  "=" |
  ">" |
  "<" |
  "!" |
  "~" |
  "?" |
  ":" |
  "+" |
  "-" |
  "*" |
  "/" |
  "&" |
  "|" |
  "^" |
  "%"                      { return OPERATOR_STYLE; }

  {ConstantIdentifier}                    { return PLAIN_STYLE; }

  \n |
  {Identifier} |
  {WhiteSpace}                   { return PLAIN_STYLE; }

// single line comment

  "//" [^\n]* \n |

// short comment

  "/**/"   	{ return JAVA_COMMENT_STYLE; }
  
// comment start
  
  "/*"      { yybegin(IN_COMMENT);  return JAVA_COMMENT_STYLE;}

}


// normal comment mode

<IN_COMMENT> {

  // comment unterminated

  ([^\n*]|\*+[^\n*/])* (\n | \*+\n)  { return JAVA_COMMENT_STYLE; }

  // comment terminated

  ([^\n*]|\*+[^\n*/])* \*+ "/"  { yybegin(YYINITIAL); return JAVA_COMMENT_STYLE; }
  
}

/* error fallback */

.|\n                             { return PLAIN_STYLE; }
