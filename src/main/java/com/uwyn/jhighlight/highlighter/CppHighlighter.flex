/*
 * Copyright 2006 Arnout Engelen <arnouten[remove] at bzzt dot net>.
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

%class CppHighlighter
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
	public static final byte TYPE_STYLE = 3;
	public static final byte OPERATOR_STYLE = 4;
	public static final byte SEPARATOR_STYLE = 5;
	public static final byte LITERAL_STYLE = 6;
	public static final byte CPP_COMMENT_STYLE = 7;
	public static final byte DOXYGEN_COMMENT_STYLE = 8;
	public static final byte DOXYGEN_TAG_STYLE = 9;
	public static final byte PREPROC_STYLE = 10;
	
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

	public CppHighlighter()
	{
	}
%}

/* main character classes */

WhiteSpace = [ \t\f]

/* identifiers */

ConstantIdentifier = {SimpleConstantIdentifier}
SimpleConstantIdentifier = [#A-Z0-9_]+

Identifier = [:jletter:][:jletterdigit:]*

TypeIdentifier = {SimpleTypeIdentifier}
SimpleTypeIdentifier = [A-Z][:jletterdigit:]*

/* int literals */

DecLiteral = 0 | [1-9][0-9]* {IntegerSuffix}

HexLiteral    = 0 [xX] 0* {HexDigit}* {IntegerSuffix}
HexDigit      = [0-9a-fA-F]

OctLiteral    = 0+ {OctDigit}* {IntegerSuffix}
OctDigit          = [0-7]

IntegerSuffix = [uU]? [lL]? [uU]?
	
/* float literals */

FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}|{FLit4}) ([fF]|[dD])?

FLit1 = [0-9]+ \. [0-9]* {Exponent}?
FLit2 = \. [0-9]+ {Exponent}?
FLit3 = [0-9]+ {Exponent}
FLit4 = [0-9]+ {Exponent}?

Exponent = [eE] [+\-]? [0-9]+

%state IN_COMMENT, IN_DOXYGEN_COMMENT

%%

<YYINITIAL> {

  /* keywords */
  "__abstract" |
  "abstract" |
  "__alignof" |
  "array" |
  "__asm" |
  "__assume" |
  "__based" |
  "__box" |
  "break" |
  "case" |
  "catch" |
  "__cdecl" |
  "class" |
  "const" |
  "const_cast" |
  "continue" |
  "__declspec" |
  "default" |
  "__delegate" |
  "delegate" |
  "delete" |
  "deprecated" |
  "dllexport" |
  "dllimport" |
  "do" |
  "double" |
  "dynamic_cast" |
  "else" |
  "event" |
  "__event" |
  "__except" |
  "explicit" |
  "extern" |
  "false" |
  "__fastcall" |
  "__finally" |
  "finally" |
  "for" |
  "__forceinline" |
  "friend" |
  "friend_as" |
  "__gc" |
  "gcnew" |
  "generic" |
  "goto" |
  "enum" |
  "__hook" |
  "__identifier" |
  "if" |
  "__if_exists" |
  "__if_not_exists" |
  "initonly" |
  "__inline" |
  "inline" |
  "__int8" |
  "__int16" |
  "__int32" |
  "__int64" |
  "__interface" |
  "interface" |
  "interface" |
  "interior_ptr" |
  "__leave" |
  "literal" |
  "__m64" |
  "__m128" |
  "__m128d" |
  "__m128i" |
  "__multiple_inheritance" |
  "mutable" |
  "naked" |
  "namespace" |
  "new" |
  "__nogc" |
  "noinline" |
  "__noop" |
  "noreturn" |
  "nothrow" |
  "novtable" |
  "nullptr" |
  "operator" |
  "__pin" |
  "private" |
  "__property" |
  "property" |
  "property" |
  "protected" |
  "public" |
  "__raise" |
  "register" |
  "reinterpret_cast" |
  "return" |
  "safecast" |
  "__sealed" |
  "sealed" |
  "selectany" |
  "signed" |
  "__single_inheritance" |
  "sizeof" |
  "static" |
  "static_cast" |
  "__stdcall" |
  "struct" |
  "__super" |
  "switch" |
  "template" |
  "this" |
  "thread" |
  "throw" |
  "true" |
  "try" |
  "__try" |
  "__except" |
  "__try_cast" |
  "typedef" |
  "typeid" |
  "typeid" |
  "typename" |
  "__unaligned" |
  "__unhook" |
  "union" |
  "unsigned" |
  "using" |
  "uuid" |
  "__uuidof" |
  "value" |
  "__value" |
  "virtual" |
  "__virtual_inheritance" |
  "void" |
  "volatile" |
  "__w64" |
  "__wchar_t," |
  "while"
     { return KEYWORD_STYLE; }

  "bool" |
  "char" |
  "double" |
  "int" |
  "long" |
  "float" |
  "short" |
  "void" { return TYPE_STYLE; }

  /* literals */
  "true" |
  "false" |

  (\" ( [^\"\n\\] | \\[^\n] )* (\n | \\\n | \")) |
  (\' ( [^\'\n\\] | \\[^\n] )* (\n | \\\n | \')) |

  {DecLiteral} |
  {OctLiteral} |
  {HexLiteral} |

  {FloatLiteral}
	{ return LITERAL_STYLE; }

  /* preprocessor symbols */
  "#define" |
  "#elif" |
  "#else" |
  "#endif" |
  "#error" |
  "#ifdef" |
  "#ifndef" |
  "#if" |
  "#import" |
  "#include" |
  "#line" |
  "#pragma" |
  "#undef" |
  "#using"
  	{ return PREPROC_STYLE; }

  
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

  "/**/"   	{ return CPP_COMMENT_STYLE; }
  
// comment start
  
  "/**"     { yybegin(IN_DOXYGEN_COMMENT); return DOXYGEN_COMMENT_STYLE;}
  "/*"      { yybegin(IN_COMMENT);  return CPP_COMMENT_STYLE;}

}


// normal comment mode

<IN_COMMENT> {
  

  // comment unterminated

  ([^\n*]|\*+[^\n*/])* (\n | \*+\n)  { return CPP_COMMENT_STYLE; }

  // comment terminated

  ([^\n*]|\*+[^\n*/])* \*+ "/"  { yybegin(YYINITIAL); return CPP_COMMENT_STYLE; }
  
}

// doc comment mode

<IN_DOXYGEN_COMMENT> {
  
  // comment unterminated

  .|\n  { return DOXYGEN_COMMENT_STYLE; }

  // comment terminated

  \* "/"  { yybegin(YYINITIAL); return DOXYGEN_COMMENT_STYLE; }

	
  "@" {Identifier} { return DOXYGEN_TAG_STYLE;  }
	
}

/* error fallback */

.|\n                             { return PLAIN_STYLE; }
