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
/** An abstract class providing basic methods for functions implementing a type-specific interface.
 *
 * <P>Optional operations just throw an {@link
 * UnsupportedOperationException}. Generic versions of accessors delegate to
 * the corresponding type-specific counterparts following the interface rules
 * (they take care of returning <code>null</code> on a missing key).
 *
 * <P>This class handles directly a default return
 * value (including {@linkplain #defaultReturnValue() methods to access
 * it}). Instances of classes inheriting from this class have just to return
 * <code>defRetValue</code> to denote lack of a key in type-specific methods. The value
 * is serialized.
 *
 * <P>Implementing subclasses have just to provide type-specific <code>get()</code>,
 * type-specific <code>containsKey()</code>, and <code>size()</code> methods.
 *
 */
public abstract class AbstractChar2ObjectFunction <V> implements Char2ObjectFunction <V>, java.io.Serializable {
 private static final long serialVersionUID = -4940583368468432370L;
 protected AbstractChar2ObjectFunction() {}
 /**
	 * The default return value for <code>get()</code>, <code>put()</code> and
	 * <code>remove()</code>.  
	 */
 protected V defRetValue;
 public void defaultReturnValue( final V rv ) {
  defRetValue = rv;
 }
 public V defaultReturnValue() {
  return defRetValue;
 }
 public V put( char key, V value ) {
  throw new UnsupportedOperationException();
 }
 public V remove( char key ) {
  throw new UnsupportedOperationException();
 }
 public void clear() {
  throw new UnsupportedOperationException();
 }
 public boolean containsKey( final Object ok ) {
  return containsKey( ((((Character)(ok)).charValue())) );
 }
 /** Delegates to the corresponding type-specific method, taking care of returning <code>null</code> on a missing key.
	 *
	 * <P>This method must check whether the provided key is in the map using <code>containsKey()</code>. Thus,
	 * it probes the map <em>twice</em>. Implementors of subclasses should override it with a more efficient method.
	 */
 public V get( final Object ok ) {
  final char k = ((((Character)(ok)).charValue()));
  return containsKey( k ) ? (get( k )) : null;
 }
 /** Delegates to the corresponding type-specific method, taking care of returning <code>null</code> on a missing key. 
	 *
	 * <P>This method must check whether the provided key is in the map using <code>containsKey()</code>. Thus,
	 * it probes the map <em>twice</em>. Implementors of subclasses should override it with a more efficient method.
	 */
 public V put( final Character ok, final V ov ) {
  final char k = ((ok).charValue());
  final boolean containsKey = containsKey( k );
  final V v = put( k, (ov) );
  return containsKey ? (v) : null;
 }
 /** Delegates to the corresponding type-specific method, taking care of returning <code>null</code> on a missing key. 
	 *
	 * <P>This method must check whether the provided key is in the map using <code>containsKey()</code>. Thus,
	 * it probes the map <em>twice</em>. Implementors of subclasses should override it with a more efficient method.
	 */
 public V remove( final Object ok ) {
  final char k = ((((Character)(ok)).charValue()));
  final boolean containsKey = containsKey( k );
  final V v = remove( k );
  return containsKey ? (v) : null;
 }
}
