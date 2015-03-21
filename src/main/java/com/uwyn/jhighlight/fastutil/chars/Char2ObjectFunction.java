/* Copyright (C) 1991-2014 Free Software Foundation, Inc.
   This file is part of the GNU C Library.

   The GNU C Library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   The GNU C Library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with the GNU C Library; if not, see
   <http://www.gnu.org/licenses/>.  */
/* This header is separate from features.h so that the compiler can
   include it implicitly at the start of every compilation.  It must
   not itself include <features.h> or any other header that includes
   <features.h> because the implicit include comes before any feature
   test macros that may be defined in a source file before it first
   explicitly includes a system header.  GCC knows the name of this
   header in order to preinclude it.  */
/* glibc's intent is to support the IEC 559 math functionality, real
   and complex.  If the GCC (4.9 and later) predefined macros
   specifying compiler intent are available, use them to determine
   whether the overall intent is to support these features; otherwise,
   presume an older compiler has intent to support these features and
   define these macros by default.  */
/* wchar_t uses ISO/IEC 10646 (2nd ed., published 2011-03-15) /
   Unicode 6.0.  */
/* We do not support C11 <threads.h>.  */
/* Generic definitions */
/* Assertions (useful to generate conditional code) */
/* Current type and class (and size, if applicable) */
/* Value methods */
/* Interfaces (keys) */
/* Interfaces (values) */
/* Abstract implementations (keys) */
/* Abstract implementations (values) */
/* Static containers (keys) */
/* Static containers (values) */
/* Implementations */
/* Synchronized wrappers */
/* Unmodifiable wrappers */
/* Other wrappers */
/* Methods (keys) */
/* Methods (values) */
/* Methods (keys/values) */
/* Methods that have special names depending on keys (but the special names depend on values) */
/* Equality */
/* Object/Reference-only definitions (keys) */
/* Primitive-type-only definitions (keys) */
/* Object/Reference-only definitions (values) */
/*		 
 * Copyright (C) 2002-2014 Sebastiano Vigna 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.uwyn.jhighlight.fastutil.chars;
import com.uwyn.jhighlight.fastutil.Function;
/** A type-specific {@link Function}; provides some additional methods that use polymorphism to avoid (un)boxing.
 *
 * <P>Type-specific versions of <code>get()</code>, <code>put()</code> and
 * <code>remove()</code> cannot rely on <code>null</code> to denote absence of
 * a key.  Rather, they return a {@linkplain #defaultReturnValue() default
 * return value}, which is set to 0 cast to the return type (<code>false</code>
 * for booleans) at creation, but can be changed using the
 * <code>defaultReturnValue()</code> method. 
 *
 * <P>For uniformity reasons, even maps returning objects implement the default
 * return value (of course, in this case the default return value is
 * initialized to <code>null</code>).
 *
 * <P><strong>Warning:</strong> to fall in line as much as possible with the
 * {@linkplain java.util.Map standard map interface}, it is strongly suggested
 * that standard versions of <code>get()</code>, <code>put()</code> and
 * <code>remove()</code> for maps with primitive-type values <em>return
 * <code>null</code> to denote missing keys</em> rather than wrap the default
 * return value in an object (of course, for maps with object keys and values
 * this is not possible, as there is no type-specific version).
 *
 * @see Function
 */
public interface Char2ObjectFunction <V> extends Function<Character, V> {
 /** Adds a pair to the map.
	 *
	 * @param key the key.
	 * @param value the value.
	 * @return the old value, or the {@linkplain #defaultReturnValue() default return value} if no value was present for the given key.
	 * @see Function#put(Object,Object)
	 */
 V put( char key, V value );
 /** Returns the value to which the given key is mapped.
	 *
	 * @param key the key.
	 * @return the corresponding value, or the {@linkplain #defaultReturnValue() default return value} if no value was present for the given key.
	 * @see Function#get(Object)
	 */
 V get( char key );
 /** Removes the mapping with the given key.
	 * @param key the key.
	 * @return the old value, or the {@linkplain #defaultReturnValue() default return value} if no value was present for the given key.
	 * @see Function#remove(Object)
	 */
 V remove( char key );
 /**
	 * @see Function#containsKey(Object)
	 */
 boolean containsKey( char key );
 /** Sets the default return value. 
	 *
	 * This value must be returned by type-specific versions of
	 * <code>get()</code>, <code>put()</code> and <code>remove()</code> to
	 * denote that the map does not contain the specified key. It must be
	 * 0/<code>false</code>/<code>null</code> by default.
	 *
	 * @param rv the new default return value.
	 * @see #defaultReturnValue()
	 */
 void defaultReturnValue( V rv );
 /** Gets the default return value.
	 *
	 * @return the current default return value.
	 */
 V defaultReturnValue();
}
