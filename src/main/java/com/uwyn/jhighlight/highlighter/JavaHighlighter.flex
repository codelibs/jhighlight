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

%class JavaHighlighter
%implements ExplicitStateHighlighter

%unicode
%pack

%buffer 128

%public

%int

%{
	public static boolean ASSERT_IS_KEYWORD = false;

	/* styles */
	
	public static final byte PLAIN_STYLE = 1;
	public static final byte KEYWORD_STYLE = 2;
	public static final byte TYPE_STYLE = 3;
	public static final byte OPERATOR_STYLE = 4;
	public static final byte SEPARATOR_STYLE = 5;
	public static final byte LITERAL_STYLE = 6;
	public static final byte JAVA_COMMENT_STYLE = 7;
	public static final byte JAVADOC_COMMENT_STYLE = 8;
	public static final byte JAVADOC_TAG_STYLE = 9;
	
	/* Highlighter implementation */
	
	public int getStyleCount()
	{
		return 9;
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

	public JavaHighlighter()
	{
	}
%}

/* main character classes */

WhiteSpace = [ \t\f]

/* identifiers */

ConstantIdentifier = {SimpleConstantIdentifier}
SimpleConstantIdentifier = [A-Z0-9_]+

Identifier = [:jletter:][:jletterdigit:]*

TypeIdentifier = {SimpleTypeIdentifier}
SimpleTypeIdentifier = [A-Z][:jletterdigit:]*

/* int literals */

DecLiteral = 0 | [1-9][0-9]* [lL]?

HexLiteral    = 0 [xX] 0* {HexDigit}* [lL]?
HexDigit      = [0-9a-fA-F]

OctLiteral    = 0+ {OctDigit}* [lL]?
OctDigit          = [0-7]
	
/* float literals */

FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}|{FLit4}) ([fF]|[dD])?

FLit1 = [0-9]+ \. [0-9]* {Exponent}?
FLit2 = \. [0-9]+ {Exponent}?
FLit3 = [0-9]+ {Exponent}
FLit4 = [0-9]+ {Exponent}?

Exponent = [eE] [+\-]? [0-9]+

%state IN_COMMENT, IN_JAVA_DOC_COMMENT

%%

<YYINITIAL> {

  /* keywords */
  "abstract" |
  "break" |
  "case" |
  "catch" |
  "class" |
  "const" |
  "continue" |
  "do" |
  "else" |
  "extends" |
  "final" |
  "finally" |
  "for" |
  "default" |
  "implements" |
  "import" |
  "instanceof" |
  "interface" |
  "native" |
  "new" |
  "goto" |
  "if" |
  "public" |
  "super" |
  "switch" |
  "synchronized" |
  "package" |
  "private" |
  "protected" |
  "transient" |
  "return" |
  "static" |
  "while" |
  "this" |
  "throw" |
  "throws" |
  "try" |
  "volatile" |
  "strictfp" { return KEYWORD_STYLE; }

  "boolean" |
  "byte" |
  "char" |
  "double" |
  "int" |
  "long" |
  "float" |
  "short" |
  "void" { return TYPE_STYLE; }

  "assert" { return ASSERT_IS_KEYWORD ? KEYWORD_STYLE : PLAIN_STYLE; }

  /* literals */
  "true" |
  "false" |
  "null" |

  (\" ( [^\"\n\\] | \\[^\n] )* (\n | \\\n | \")) |
  (\' ( [^\'\n\\] | \\[^\n] )* (\n | \\\n | \')) |

  {DecLiteral} |
  {HexLiteral} |
  {OctLiteral} |

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

  {TypeIdentifier}  { return TYPE_STYLE; }

  \n |
  {Identifier} |
  {WhiteSpace}                   { return PLAIN_STYLE; }



// single line comment

  "//" [^\n]* \n |

// short comment

  "/**/"   	{ return JAVA_COMMENT_STYLE; }
  
// comment start
  
  "/**"     { yybegin(IN_JAVA_DOC_COMMENT); return JAVADOC_COMMENT_STYLE;}
  "/*"      { yybegin(IN_COMMENT);  return JAVA_COMMENT_STYLE;}

}


// normal comment mode

<IN_COMMENT> {
  

  // comment unterminated

  ([^\n*]|\*+[^\n*/])* (\n | \*+\n)  { return JAVA_COMMENT_STYLE; }

  // comment terminated

  ([^\n*]|\*+[^\n*/])* \*+ "/"  { yybegin(YYINITIAL); return JAVA_COMMENT_STYLE; }
  
}

// doc comment mode

<IN_JAVA_DOC_COMMENT> {
  
  // comment unterminated

  .|\n  { return JAVADOC_COMMENT_STYLE; }

  // comment terminated

  \* "/"  { yybegin(YYINITIAL); return JAVADOC_COMMENT_STYLE; }

	
  "@" {Identifier} { return JAVADOC_TAG_STYLE;  }
	
}

/* error fallback */

.|\n                             { return PLAIN_STYLE; }
