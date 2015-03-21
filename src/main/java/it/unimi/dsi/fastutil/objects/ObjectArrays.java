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
 *
 *
 *
 * For the sorting and binary search code:
 *
 * Copyright (C) 1999 CERN - European Organization for Nuclear Research.
 *
 *   Permission to use, copy, modify, distribute and sell this software and
 *   its documentation for any purpose is hereby granted without fee,
 *   provided that the above copyright notice appear in all copies and that
 *   both that copyright notice and this permission notice appear in
 *   supporting documentation. CERN makes no representations about the
 *   suitability of this software for any purpose. It is provided "as is"
 *   without expressed or implied warranty. 
 */
package it.unimi.dsi.fastutil.objects;
import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import java.util.Random;
import java.util.Comparator;
/** A class providing static methods and objects that do useful things with type-specific arrays.
 *
 * In particular, the <code>ensureCapacity()</code>, <code>grow()</code>,
 * <code>trim()</code> and <code>setLength()</code> methods allow to handle
 * arrays much like array lists. This can be very useful when efficiency (or
 * syntactic simplicity) reasons make array lists unsuitable.
 *
 * <P><strong>Warning:</strong> creating arrays 
 * using {@linkplain java.lang.reflect.Array#newInstance(Class,int) reflection}, as it
 * happens in {@link #ensureCapacity(Object[],int,int)} and {@link #grow(Object[],int,int)},
 * is <em>significantly slower</em> than using <code>new</code>. This phenomenon is particularly
 * evident in the first growth phases of an array reallocated with doubling (or similar) logic.
 *
 * @see java.util.Arrays
 */
public class ObjectArrays {
 private ObjectArrays() {}
 /** A static, final, empty array. */
 public final static Object[] EMPTY_ARRAY = {};
 /** Creates a new array using a the given one as prototype. 
	 *
	 * <P>This method returns a new array of the given length whose element
	 * are of the same class as of those of <code>prototype</code>. In case
	 * of an empty array, it tries to return {@link #EMPTY_ARRAY}, if possible.
	 *
	 * @param prototype an array that will be used to type the new one.
	 * @param length the length of the new array.
	 * @return a new array of given type and length.
	 */
 @SuppressWarnings("unchecked")
 private static <K> K[] newArray( final K[] prototype, final int length ) {
  final Class<?> componentType = prototype.getClass().getComponentType();
  if ( length == 0 && componentType == Object.class ) return (K[])EMPTY_ARRAY;
  return (K[])java.lang.reflect.Array.newInstance( prototype.getClass().getComponentType(), length );
 }
 /** Ensures that an array can contain the given number of entries.
	 *
	 * <P>If you cannot foresee whether this array will need again to be
	 * enlarged, you should probably use <code>grow()</code> instead.
	 *
	 * @param array an array.
	 * @param length the new minimum length for this array.
	 * @return <code>array</code>, if it contains <code>length</code> entries or more; otherwise,
	 * an array with <code>length</code> entries whose first <code>array.length</code>
	 * entries are the same as those of <code>array</code>.
	 */
 public static <K> K[] ensureCapacity( final K[] array, final int length ) {
  if ( length > array.length ) {
   final K t[] =
    newArray( array, length );
   System.arraycopy( array, 0, t, 0, array.length );
   return t;
  }
  return array;
 }
 /** Ensures that an array can contain the given number of entries, preserving just a part of the array.
	 *
	 * @param array an array.
	 * @param length the new minimum length for this array.
	 * @param preserve the number of elements of the array that must be preserved in case a new allocation is necessary.
	 * @return <code>array</code>, if it can contain <code>length</code> entries or more; otherwise,
	 * an array with <code>length</code> entries whose first <code>preserve</code>
	 * entries are the same as those of <code>array</code>.
	 */
 public static <K> K[] ensureCapacity( final K[] array, final int length, final int preserve ) {
  if ( length > array.length ) {
   final K t[] =
    newArray( array, length );
   System.arraycopy( array, 0, t, 0, preserve );
   return t;
  }
  return array;
 }
 /** Grows the given array to the maximum between the given length and
	 * the current length multiplied by two, provided that the given
	 * length is larger than the current length.
	 *
	 * <P>If you want complete control on the array growth, you
	 * should probably use <code>ensureCapacity()</code> instead.
	 *
	 * @param array an array.
	 * @param length the new minimum length for this array.
	 * @return <code>array</code>, if it can contain <code>length</code>
	 * entries; otherwise, an array with
	 * max(<code>length</code>,<code>array.length</code>/&phi;) entries whose first
	 * <code>array.length</code> entries are the same as those of <code>array</code>.
	 * */
 public static <K> K[] grow( final K[] array, final int length ) {
  if ( length > array.length ) {
   final int newLength = (int)Math.max( Math.min( 2L * array.length, Arrays.MAX_ARRAY_SIZE ), length );
   final K t[] =
    newArray( array, newLength );
   System.arraycopy( array, 0, t, 0, array.length );
   return t;
  }
  return array;
 }
 /** Grows the given array to the maximum between the given length and
	 * the current length multiplied by two, provided that the given
	 * length is larger than the current length, preserving just a part of the array.
	 *
	 * <P>If you want complete control on the array growth, you
	 * should probably use <code>ensureCapacity()</code> instead.
	 *
	 * @param array an array.
	 * @param length the new minimum length for this array.
	 * @param preserve the number of elements of the array that must be preserved in case a new allocation is necessary.
	 * @return <code>array</code>, if it can contain <code>length</code>
	 * entries; otherwise, an array with
	 * max(<code>length</code>,<code>array.length</code>/&phi;) entries whose first
	 * <code>preserve</code> entries are the same as those of <code>array</code>.
	 * */
 public static <K> K[] grow( final K[] array, final int length, final int preserve ) {
  if ( length > array.length ) {
   final int newLength = (int)Math.max( Math.min( 2L * array.length, Arrays.MAX_ARRAY_SIZE ), length );
   final K t[] =
    newArray( array, newLength );
   System.arraycopy( array, 0, t, 0, preserve );
   return t;
  }
  return array;
 }
 /** Trims the given array to the given length.
	 *
	 * @param array an array.
	 * @param length the new maximum length for the array.
	 * @return <code>array</code>, if it contains <code>length</code>
	 * entries or less; otherwise, an array with
	 * <code>length</code> entries whose entries are the same as
	 * the first <code>length</code> entries of <code>array</code>.
	 * 
	 */
 public static <K> K[] trim( final K[] array, final int length ) {
  if ( length >= array.length ) return array;
  final K t[] =
   newArray( array, length );
  System.arraycopy( array, 0, t, 0, length );
  return t;
 }
 /** Sets the length of the given array.
	 *
	 * @param array an array.
	 * @param length the new length for the array.
	 * @return <code>array</code>, if it contains exactly <code>length</code>
	 * entries; otherwise, if it contains <em>more</em> than
	 * <code>length</code> entries, an array with <code>length</code> entries
	 * whose entries are the same as the first <code>length</code> entries of
	 * <code>array</code>; otherwise, an array with <code>length</code> entries
	 * whose first <code>array.length</code> entries are the same as those of
	 * <code>array</code>.
	 * 
	 */
 public static <K> K[] setLength( final K[] array, final int length ) {
  if ( length == array.length ) return array;
  if ( length < array.length ) return trim( array, length );
  return ensureCapacity( array, length );
 }
 /** Returns a copy of a portion of an array.
	 *
	 * @param array an array.
	 * @param offset the first element to copy.
	 * @param length the number of elements to copy.
	 * @return a new array containing <code>length</code> elements of <code>array</code> starting at <code>offset</code>.
	 */
 public static <K> K[] copy( final K[] array, final int offset, final int length ) {
  ensureOffsetLength( array, offset, length );
  final K[] a =
   newArray( array, length );
  System.arraycopy( array, offset, a, 0, length );
  return a;
 }
 /** Returns a copy of an array.
	 *
	 * @param array an array.
	 * @return a copy of <code>array</code>.
	 */
 public static <K> K[] copy( final K[] array ) {
  return array.clone();
 }
 /** Fills the given array with the given value.
	 *
	 * <P>This method uses a backward loop. It is significantly faster than the corresponding
	 * method in {@link java.util.Arrays}.
	 *
	 * @param array an array.
	 * @param value the new value for all elements of the array.
	 */
 public static <K> void fill( final K[] array, final K value ) {
  int i = array.length;
  while( i-- != 0 ) array[ i ] = value;
 }
 /** Fills a portion of the given array with the given value.
	 *
	 * <P>If possible (i.e., <code>from</code> is 0) this method uses a
	 * backward loop. In this case, it is significantly faster than the
	 * corresponding method in {@link java.util.Arrays}.
	 *
	 * @param array an array.
	 * @param from the starting index of the portion to fill (inclusive).
	 * @param to the end index of the portion to fill (exclusive).
	 * @param value the new value for all elements of the specified portion of the array.
	 */
 public static <K> void fill( final K[] array, final int from, int to, final K value ) {
  ensureFromTo( array, from, to );
  if ( from == 0 ) while( to-- != 0 ) array[ to ] = value;
  else for( int i = from; i < to; i++ ) array[ i ] = value;
 }
 /** Returns true if the two arrays are elementwise equal.
	 *
	 * @param a1 an array.
	 * @param a2 another array.
	 * @return true if the two arrays are of the same length, and their elements are equal.
	 * @deprecated Please use the corresponding {@link java.util.Arrays} method, which is intrinsified in recent JVMs.
	 */
 @Deprecated
 public static <K> boolean equals( final K[] a1, final K a2[] ) {
  int i = a1.length;
  if ( i != a2.length ) return false;
  while( i-- != 0 ) if (! ( (a1[ i ]) == null ? (a2[ i ]) == null : (a1[ i ]).equals(a2[ i ]) ) ) return false;
  return true;
 }
 /** Ensures that a range given by its first (inclusive) and last (exclusive) elements fits an array.
	 *
	 * <P>This method may be used whenever an array range check is needed.
	 *
	 * @param a an array.
	 * @param from a start index (inclusive).
	 * @param to an end index (exclusive).
	 * @throws IllegalArgumentException if <code>from</code> is greater than <code>to</code>.
	 * @throws ArrayIndexOutOfBoundsException if <code>from</code> or <code>to</code> are greater than the array length or negative.
	 */
 public static <K> void ensureFromTo( final K[] a, final int from, final int to ) {
  Arrays.ensureFromTo( a.length, from, to );
 }
 /** Ensures that a range given by an offset and a length fits an array.
	 *
	 * <P>This method may be used whenever an array range check is needed.
	 *
	 * @param a an array.
	 * @param offset a start index.
	 * @param length a length (the number of elements in the range).
	 * @throws IllegalArgumentException if <code>length</code> is negative.
	 * @throws ArrayIndexOutOfBoundsException if <code>offset</code> is negative or <code>offset</code>+<code>length</code> is greater than the array length.
	 */
 public static <K> void ensureOffsetLength( final K[] a, final int offset, final int length ) {
  Arrays.ensureOffsetLength( a.length, offset, length );
 }
 private static final int SMALL = 7;
 private static final int MEDIUM = 50;
 private static <K> void swap( final K x[], final int a, final int b ) {
  final K t = x[ a ];
  x[ a ] = x[ b ];
  x[ b ] = t;
 }
 private static <K> void vecSwap( final K[] x, int a, int b, final int n ) {
  for( int i = 0; i < n; i++, a++, b++ ) swap( x, a, b );
 }
 private static <K> int med3( final K x[], final int a, final int b, final int c, Comparator <K> comp ) {
  int ab = comp.compare( x[ a ], x[ b ] );
  int ac = comp.compare( x[ a ], x[ c ] );
  int bc = comp.compare( x[ b ], x[ c ] );
  return ( ab < 0 ?
   ( bc < 0 ? b : ac < 0 ? c : a ) :
   ( bc > 0 ? b : ac > 0 ? c : a ) );
 }
 private static <K> void selectionSort( final K[] a, final int from, final int to, final Comparator <K> comp ) {
  for( int i = from; i < to - 1; i++ ) {
   int m = i;
   for( int j = i + 1; j < to; j++ ) if ( comp.compare( a[ j ], a[ m ] ) < 0 ) m = j;
   if ( m != i ) {
    final K u = a[ i ];
    a[ i ] = a[ m ];
    a[ m ] = u;
   }
  }
 }
 private static <K> void insertionSort( final K[] a, final int from, final int to, final Comparator <K> comp ) {
  for ( int i = from; ++i < to; ) {
   K t = a[ i ];
   int j = i;
   for ( K u = a[ j - 1 ]; comp.compare( t, u ) < 0; u = a[ --j - 1 ] ) {
    a[ j ] = u;
    if ( from == j - 1 ) {
     --j;
     break;
    }
   }
   a[ j ] = t;
  }
 }
 @SuppressWarnings("unchecked")
 private static <K> void selectionSort( final K[] a, final int from, final int to ) {
  for( int i = from; i < to - 1; i++ ) {
   int m = i;
   for( int j = i + 1; j < to; j++ ) if ( ( ((Comparable<K>)(a[ j ])).compareTo(a[ m ]) < 0 ) ) m = j;
   if ( m != i ) {
    final K u = a[ i ];
    a[ i ] = a[ m ];
    a[ m ] = u;
   }
  }
 }
 @SuppressWarnings("unchecked")
 private static <K> void insertionSort( final K[] a, final int from, final int to ) {
  for ( int i = from; ++i < to; ) {
   K t = a[ i ];
   int j = i;
   for ( K u = a[ j - 1 ]; ( ((Comparable<K>)(t)).compareTo(u) < 0 ); u = a[ --j - 1 ] ) {
    a[ j ] = u;
    if ( from == j - 1 ) {
     --j;
     break;
    }
   }
   a[ j ] = t;
  }
 }
 /** Sorts the specified range of elements according to the order induced by the specified
	 * comparator using quicksort. 
	 * 
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 *
	 * <p>Note that this implementation does not allocate any object, contrarily to the implementation
	 * used to sort primitive types in {@link java.util.Arrays}, which switches to mergesort on large inputs.
	 * 
	 * @param x the array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 * 
	 */
 public static <K> void quickSort( final K[] x, final int from, final int to, final Comparator <K> comp ) {
  final int len = to - from;
  // Selection sort on smallest arrays
  if ( len < SMALL ) {
   selectionSort( x, from, to, comp );
   return;
  }
  // Choose a partition element, v
  int m = from + len / 2; // Small arrays, middle element
  if ( len > SMALL ) {
   int l = from;
   int n = to - 1;
   if ( len > MEDIUM ) { // Big arrays, pseudomedian of 9
    int s = len / 8;
    l = med3( x, l, l + s, l + 2 * s, comp );
    m = med3( x, m - s, m, m + s, comp );
    n = med3( x, n - 2 * s, n - s, n, comp );
   }
   m = med3( x, l, m, n, comp ); // Mid-size, med of 3
  }
  final K v = x[ m ];
  // Establish Invariant: v* (<v)* (>v)* v*
  int a = from, b = a, c = to - 1, d = c;
  while(true) {
   int comparison;
   while ( b <= c && ( comparison = comp.compare( x[ b ], v ) ) <= 0 ) {
    if ( comparison == 0 ) swap( x, a++, b );
    b++;
   }
   while (c >= b && ( comparison = comp.compare( x[ c ], v ) ) >=0 ) {
    if ( comparison == 0 ) swap( x, c, d-- );
    c--;
   }
   if ( b > c ) break;
   swap( x, b++, c-- );
  }
  // Swap partition elements back to middle
  int s, n = to;
  s = Math.min( a - from, b - a );
  vecSwap( x, from, b - s, s );
  s = Math.min( d - c, n - d - 1 );
  vecSwap( x, b, n - s, s );
  // Recursively sort non-partition-elements
  if ( ( s = b - a ) > 1 ) quickSort( x, from, from + s, comp );
  if ( ( s = d - c ) > 1 ) quickSort( x, n - s, n, comp );
 }
 /** Sorts an array according to the order induced by the specified
	 * comparator using quicksort. 
	 * 
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 * 
	 * <p>Note that this implementation does not allocate any object, contrarily to the implementation
	 * used to sort primitive types in {@link java.util.Arrays}, which switches to mergesort on large inputs.
	 * 
	 * @param x the array to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 * 
	 */
 public static <K> void quickSort( final K[] x, final Comparator <K> comp ) {
  quickSort( x, 0, x.length, comp );
 }
 @SuppressWarnings("unchecked")
 private static <K> int med3( final K x[], final int a, final int b, final int c ) {
  int ab = ( ((Comparable<K>)(x[ a ])).compareTo(x[ b ]) );
  int ac = ( ((Comparable<K>)(x[ a ])).compareTo(x[ c ]) );
  int bc = ( ((Comparable<K>)(x[ b ])).compareTo(x[ c ]) );
  return ( ab < 0 ?
   ( bc < 0 ? b : ac < 0 ? c : a ) :
   ( bc > 0 ? b : ac > 0 ? c : a ) );
 }
 /** Sorts the specified range of elements according to the natural ascending order using quicksort.
	 * 
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 * 
	 * <p>Note that this implementation does not allocate any object, contrarily to the implementation
	 * used to sort primitive types in {@link java.util.Arrays}, which switches to mergesort on large inputs.
	 * 
	 * @param x the array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 */
 @SuppressWarnings("unchecked")
 public static <K> void quickSort( final K[] x, final int from, final int to ) {
  final int len = to - from;
  // Selection sort on smallest arrays
  if ( len < SMALL ) {
   selectionSort( x, from, to );
   return;
  }
  // Choose a partition element, v
  int m = from + len / 2; // Small arrays, middle element
  if ( len > SMALL ) {
   int l = from;
   int n = to - 1;
   if ( len > MEDIUM ) { // Big arrays, pseudomedian of 9
    int s = len / 8;
    l = med3( x, l, l + s, l + 2 * s );
    m = med3( x, m - s, m, m + s );
    n = med3( x, n - 2 * s, n - s, n );
   }
   m = med3( x, l, m, n ); // Mid-size, med of 3
  }
  final K v = x[ m ];
  // Establish Invariant: v* (<v)* (>v)* v*
  int a = from, b = a, c = to - 1, d = c;
  while(true) {
   int comparison;
   while ( b <= c && ( comparison = ( ((Comparable<K>)(x[ b ])).compareTo(v) ) ) <= 0 ) {
    if ( comparison == 0 ) swap( x, a++, b );
    b++;
   }
   while (c >= b && ( comparison = ( ((Comparable<K>)(x[ c ])).compareTo(v) ) ) >=0 ) {
    if ( comparison == 0 ) swap( x, c, d-- );
    c--;
   }
   if ( b > c ) break;
   swap( x, b++, c-- );
  }
  // Swap partition elements back to middle
  int s, n = to;
  s = Math.min( a - from, b - a );
  vecSwap( x, from, b - s, s );
  s = Math.min( d - c, n - d - 1 );
  vecSwap( x, b, n - s, s );
  // Recursively sort non-partition-elements
  if ( ( s = b - a ) > 1 ) quickSort( x, from, from + s );
  if ( ( s = d - c ) > 1 ) quickSort( x, n - s, n );
 }
 /** Sorts an array according to the natural ascending order using quicksort.
	 * 
	 * <p>The sorting algorithm is a tuned quicksort adapted from Jon L. Bentley and M. Douglas
	 * McIlroy, &ldquo;Engineering a Sort Function&rdquo;, <i>Software: Practice and Experience</i>, 23(11), pages
	 * 1249&minus;1265, 1993.
	 * 
	 * <p>Note that this implementation does not allocate any object, contrarily to the implementation
	 * used to sort primitive types in {@link java.util.Arrays}, which switches to mergesort on large inputs.
	 * 
	 * @param x the array to be sorted.
	 * 
	 */
 public static <K> void quickSort( final K[] x ) {
  quickSort( x, 0, x.length );
 }
 /** Sorts the specified range of elements according to the natural ascending order using mergesort, using a given pre-filled support array.
	 * 
	 * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as a result
	 * of the sort. Moreover, no support arrays will be allocated. 
	 
	 * @param a the array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 * @param supp a support array containing at least <code>to</code> elements, and whose entries are identical to those
	 * of {@code a} in the specified range.
	 */
 @SuppressWarnings("unchecked")
 public static <K> void mergeSort( final K a[], final int from, final int to, final K supp[] ) {
  int len = to - from;
  // Insertion sort on smallest arrays
  if ( len < SMALL ) {
   insertionSort( a, from, to );
   return;
  }
  // Recursively sort halves of a into supp
  final int mid = ( from + to ) >>> 1;
  mergeSort( supp, from, mid, a );
  mergeSort( supp, mid, to, a );
  // If list is already sorted, just copy from supp to a.  This is an
  // optimization that results in faster sorts for nearly ordered lists.
  if ( ( ((Comparable<K>)(supp[ mid - 1 ])).compareTo(supp[ mid ]) <= 0 ) ) {
   System.arraycopy( supp, from, a, from, len );
   return;
  }
  // Merge sorted halves (now in supp) into a
  for( int i = from, p = from, q = mid; i < to; i++ ) {
   if ( q >= to || p < mid && ( ((Comparable<K>)(supp[ p ])).compareTo(supp[ q ]) <= 0 ) ) a[ i ] = supp[ p++ ];
   else a[ i ] = supp[ q++ ];
  }
 }
 /** Sorts the specified range of elements according to the natural ascending order using mergesort.
	 * 
	 * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as a result
	 * of the sort. An array as large as <code>a</code> will be allocated by this method.
	 
	 * @param a the array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 */
 public static <K> void mergeSort( final K a[], final int from, final int to ) {
  mergeSort( a, from, to, a.clone() );
 }
 /**	Sorts an array according to the natural ascending order using mergesort.
	 * 
	 * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as a result
	 * of the sort. An array as large as <code>a</code> will be allocated by this method.
	 
	 * @param a the array to be sorted.
	 */
 public static <K> void mergeSort( final K a[] ) {
  mergeSort( a, 0, a.length );
 }
 /** Sorts the specified range of elements according to the order induced by the specified
	 * comparator using mergesort, using a given pre-filled support array.
	 * 
	 * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as a result
	 * of the sort. Moreover, no support arrays will be allocated.
	 
	 * @param a the array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 * @param supp a support array containing at least <code>to</code> elements, and whose entries are identical to those
	 * of {@code a} in the specified range.
	 */
 @SuppressWarnings("unchecked")
 public static <K> void mergeSort( final K a[], final int from, final int to, Comparator <K> comp, final K supp[] ) {
  int len = to - from;
  // Insertion sort on smallest arrays
  if ( len < SMALL ) {
   insertionSort( a, from, to, comp );
   return;
     }
  // Recursively sort halves of a into supp
  final int mid = ( from + to ) >>> 1;
  mergeSort( supp, from, mid, comp, a );
  mergeSort( supp, mid, to, comp, a );
  // If list is already sorted, just copy from supp to a.  This is an
  // optimization that results in faster sorts for nearly ordered lists.
  if ( comp.compare( supp[ mid - 1 ], supp[ mid ] ) <= 0 ) {
   System.arraycopy( supp, from, a, from, len );
   return;
  }
  // Merge sorted halves (now in supp) into a
  for( int i = from, p = from, q = mid; i < to; i++ ) {
   if ( q >= to || p < mid && comp.compare( supp[ p ], supp[ q ] ) <= 0 ) a[ i ] = supp[ p++ ];
   else a[ i ] = supp[ q++ ];
  }
 }
 /** Sorts the specified range of elements according to the order induced by the specified
	 * comparator using mergesort.
	 * 
	 * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as a result
	 * of the sort. An array as large as <code>a</code> will be allocated by this method.
	 *
	 * @param a the array to be sorted.
	 * @param from the index of the first element (inclusive) to be sorted.
	 * @param to the index of the last element (exclusive) to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 */
 public static <K> void mergeSort( final K a[], final int from, final int to, Comparator <K> comp ) {
  mergeSort( a, from, to, comp, a.clone() );
 }
 /** Sorts an array according to the order induced by the specified
	 * comparator using mergesort.
	 * 
	 * <p>This sort is guaranteed to be <i>stable</i>: equal elements will not be reordered as a result
	 * of the sort.  An array as large as <code>a</code> will be allocated by this method.
	 
	 * @param a the array to be sorted.
	 * @param comp the comparator to determine the sorting order.
	 */
 public static <K> void mergeSort( final K a[], Comparator <K> comp ) {
  mergeSort( a, 0, a.length, comp );
 }
 /**
	 * Searches a range of the specified array for the specified value using 
	 * the binary search algorithm. The range must be sorted prior to making this call. 
	 * If it is not sorted, the results are undefined. If the range contains multiple elements with 
	 * the specified value, there is no guarantee which one will be found.
	 *
	 * @param a the array to be searched.
	 * @param from  the index of the first element (inclusive) to be searched.
	 * @param to  the index of the last element (exclusive) to be searched.
	 * @param key the value to be searched for.
	 * @return index of the search key, if it is contained in the array;
	 *             otherwise, <samp>(-(<i>insertion point</i>) - 1)</samp>.  The <i>insertion
	 *             point</i> is defined as the the point at which the value would
	 *             be inserted into the array: the index of the first
	 *             element greater than the key, or the length of the array, if all
	 *             elements in the array are less than the specified key.  Note
	 *             that this guarantees that the return value will be &gt;= 0 if
	 *             and only if the key is found.
	 * @see java.util.Arrays
	 */
 @SuppressWarnings({"unchecked","rawtypes"})
 public static <K> int binarySearch( final K[] a, int from, int to, final K key ) {
  K midVal;
  to--;
  while (from <= to) {
   final int mid = (from + to) >>> 1;
   midVal = a[ mid ];
   final int cmp = ((Comparable)midVal).compareTo( key );
   if ( cmp < 0 ) from = mid + 1;
   else if (cmp > 0) to = mid - 1;
   else return mid;
        }
  return -( from + 1 );
 }
 /**
	 * Searches an array for the specified value using 
	 * the binary search algorithm. The range must be sorted prior to making this call. 
	 * If it is not sorted, the results are undefined. If the range contains multiple elements with 
	 * the specified value, there is no guarantee which one will be found.
	 *
	 * @param a the array to be searched.
	 * @param key the value to be searched for.
	 * @return index of the search key, if it is contained in the array;
	 *             otherwise, <samp>(-(<i>insertion point</i>) - 1)</samp>.  The <i>insertion
	 *             point</i> is defined as the the point at which the value would
	 *             be inserted into the array: the index of the first
	 *             element greater than the key, or the length of the array, if all
	 *             elements in the array are less than the specified key.  Note
	 *             that this guarantees that the return value will be &gt;= 0 if
	 *             and only if the key is found.
	 * @see java.util.Arrays
	 */
 public static <K> int binarySearch( final K[] a, final K key ) {
  return binarySearch( a, 0, a.length, key );
 }
 /**
	 * Searches a range of the specified array for the specified value using 
	 * the binary search algorithm and a specified comparator. The range must be sorted following the comparator prior to making this call. 
	 * If it is not sorted, the results are undefined. If the range contains multiple elements with 
	 * the specified value, there is no guarantee which one will be found.
	 *
	 * @param a the array to be searched.
	 * @param from  the index of the first element (inclusive) to be searched.
	 * @param to  the index of the last element (exclusive) to be searched.
	 * @param key the value to be searched for.
	 * @param c a comparator.
	 * @return index of the search key, if it is contained in the array;
	 *             otherwise, <samp>(-(<i>insertion point</i>) - 1)</samp>.  The <i>insertion
	 *             point</i> is defined as the the point at which the value would
	 *             be inserted into the array: the index of the first
	 *             element greater than the key, or the length of the array, if all
	 *             elements in the array are less than the specified key.  Note
	 *             that this guarantees that the return value will be &gt;= 0 if
	 *             and only if the key is found.
	 * @see java.util.Arrays
	 */
 public static <K> int binarySearch( final K[] a, int from, int to, final K key, final Comparator <K> c ) {
  K midVal;
  to--;
  while (from <= to) {
   final int mid = (from + to) >>> 1;
   midVal = a[ mid ];
   final int cmp = c.compare( midVal, key );
   if ( cmp < 0 ) from = mid + 1;
   else if (cmp > 0) to = mid - 1;
   else return mid; // key found
  }
  return -( from + 1 );
 }
 /**
	 * Searches an array for the specified value using 
	 * the binary search algorithm and a specified comparator. The range must be sorted following the comparator prior to making this call. 
	 * If it is not sorted, the results are undefined. If the range contains multiple elements with 
	 * the specified value, there is no guarantee which one will be found.
	 *
	 * @param a the array to be searched.
	 * @param key the value to be searched for.
	 * @param c a comparator.
	 * @return index of the search key, if it is contained in the array;
	 *             otherwise, <samp>(-(<i>insertion point</i>) - 1)</samp>.  The <i>insertion
	 *             point</i> is defined as the the point at which the value would
	 *             be inserted into the array: the index of the first
	 *             element greater than the key, or the length of the array, if all
	 *             elements in the array are less than the specified key.  Note
	 *             that this guarantees that the return value will be &gt;= 0 if
	 *             and only if the key is found.
	 * @see java.util.Arrays
	 */
 public static <K> int binarySearch( final K[] a, final K key, final Comparator <K> c ) {
  return binarySearch( a, 0, a.length, key, c );
 }
 /** Shuffles the specified array fragment using the specified pseudorandom number generator.
	 * 
	 * @param a the array to be shuffled.
	 * @param from the index of the first element (inclusive) to be shuffled.
	 * @param to the index of the last element (exclusive) to be shuffled.
	 * @param random a pseudorandom number generator (please use a <a href="http://dsiutils.dsi.unimi.it/docs/it/unimi/dsi/util/XorShiftStarRandom.html">XorShift*</a> generator).
	 * @return <code>a</code>.
	 */
 public static <K> K[] shuffle( final K[] a, final int from, final int to, final Random random ) {
  for( int i = to - from; i-- != 0; ) {
   final int p = random.nextInt( i + 1 );
   final K t = a[ from + i ];
   a[ from + i ] = a[ from + p ];
   a[ from + p ] = t;
  }
  return a;
 }
 /** Shuffles the specified array using the specified pseudorandom number generator.
	 * 
	 * @param a the array to be shuffled.
	 * @param random a pseudorandom number generator (please use a <a href="http://dsiutils.dsi.unimi.it/docs/it/unimi/dsi/util/XorShiftStarRandom.html">XorShift*</a> generator).
	 * @return <code>a</code>.
	 */
 public static <K> K[] shuffle( final K[] a, final Random random ) {
  for( int i = a.length; i-- != 0; ) {
   final int p = random.nextInt( i + 1 );
   final K t = a[ i ];
   a[ i ] = a[ p ];
   a[ p ] = t;
  }
  return a;
 }
 /** Reverses the order of the elements in the specified array.
	 * 
	 * @param a the array to be reversed.
	 * @return <code>a</code>.
	 */
 public static <K> K[] reverse( final K[] a ) {
  final int length = a.length;
  for( int i = length / 2; i-- != 0; ) {
   final K t = a[ length - i - 1 ];
   a[ length - i - 1 ] = a[ i ];
   a[ i ] = t;
  }
  return a;
 }
 /** Reverses the order of the elements in the specified array fragment.
	 * 
	 * @param a the array to be reversed.
	 * @param from the index of the first element (inclusive) to be reversed.
	 * @param to the index of the last element (exclusive) to be reversed.
	 * @return <code>a</code>.
	 */
 public static <K> K[] reverse( final K[] a, final int from, final int to ) {
  final int length = to - from;
  for( int i = length / 2; i-- != 0; ) {
   final K t = a[ from + length - i - 1 ];
   a[ from + length - i - 1 ] = a[ from + i ];
   a[ from + i ] = t;
  }
  return a;
 }
 /** A type-specific content-based hash strategy for arrays. */
 private static final class ArrayHashStrategy <K> implements Hash.Strategy<K[]>, java.io.Serializable {
  private static final long serialVersionUID = -7046029254386353129L;
  public int hashCode( final K[] o ) {
   return java.util.Arrays.hashCode( o );
  }
  public boolean equals( final K[] a, final K[] b ) {
   return java.util.Arrays.equals( a, b );
  }
 }
 /** A type-specific content-based hash strategy for arrays.
	 *
	 * <P>This hash strategy may be used in custom hash collections whenever keys are
	 * arrays, and they must be considered equal by content. This strategy
	 * will handle <code>null</code> correctly, and it is serializable.
	 */
 @SuppressWarnings({"rawtypes"})
 public final static Hash.Strategy HASH_STRATEGY = new ArrayHashStrategy();
}
