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
package it.unimi.dsi.fastutil.chars;
import java.util.Collection;
/** A type-specific {@link Collection}; provides some additional methods
 * that use polymorphism to avoid (un)boxing.
 *
 * <P>Additionally, this class defines strengthens (again) {@link #iterator()} and defines
 * a slightly different semantics for {@link #toArray(Object[])}.
 *
 * @see Collection
 */
public interface CharCollection extends Collection<Character>, CharIterable {
 /** Returns a type-specific iterator on the elements of this collection.
	 *
	 * <p>Note that this specification strengthens the one given in 
	 * {@link java.lang.Iterable#iterator()}, which was already 
	 * strengthened in the corresponding type-specific class,
	 * but was weakened by the fact that this interface extends {@link Collection}.
	 *
	 * @return a type-specific iterator on the elements of this collection.
	 */
 CharIterator iterator();
 /** Returns a type-specific iterator on this elements of this collection.
	 *
	 * @see #iterator()
	 * @deprecated As of <code>fastutil</code> 5, replaced by {@link #iterator()}.
	 */
 @Deprecated
 CharIterator charIterator();
 /** Returns an containing the items of this collection;
	 * the runtime type of the returned array is that of the specified array. 
	 *
	 * <p><strong>Warning</strong>: Note that, contrarily to {@link Collection#toArray(Object[])}, this
	 * methods just writes all elements of this collection: no special 
	 * value will be added after the last one.
	 *
	 * @param a if this array is big enough, it will be used to store this collection.
	 * @return a primitive type array containing the items of this collection.
	 * @see Collection#toArray(Object[])
	 */
 <T> T[] toArray(T[] a);
 /**
	 * @see Collection#contains(Object)
	 */
 boolean contains( char key );
 /** Returns a primitive type array containing the items of this collection. 
	 * @return a primitive type array containing the items of this collection.
	 * @see Collection#toArray()
	 */
 char[] toCharArray();
 /** Returns a primitive type array containing the items of this collection.
	 *
	 * <p>Note that, contrarily to {@link Collection#toArray(Object[])}, this
	 * methods just writes all elements of this collection: no special 
	 * value will be added after the last one.
	 *
	 * @param a if this array is big enough, it will be used to store this collection.
	 * @return a primitive type array containing the items of this collection.
	 * @see Collection#toArray(Object[])
	 */
 char[] toCharArray( char a[] );
 /** Returns a primitive type array containing the items of this collection. 
	 *
	 * <p>Note that, contrarily to {@link Collection#toArray(Object[])}, this
	 * methods just writes all elements of this collection: no special 
	 * value will be added after the last one.
	 *
	 * @param a if this array is big enough, it will be used to store this collection.
	 * @return a primitive type array containing the items of this collection.
	 * @see Collection#toArray(Object[])
	 */
 char[] toArray( char a[] );
 /**
	 * @see Collection#add(Object)
	 */
 boolean add( char key );
 /** Note that this method should be called {@link java.util.Collection#remove(Object) remove()}, but the clash
	 * with the similarly named index-based method in the {@link java.util.List} interface
	 * forces us to use a distinguished name. For simplicity, the set interfaces reinstates
	 * <code>remove()</code>.
	 *
	 * @see Collection#remove(Object)
	 */
 boolean rem( char key );
 /**
	 * @see Collection#addAll(Collection)
	 */
 boolean addAll( CharCollection c );
 /**
	 * @see Collection#containsAll(Collection)
	 */
 boolean containsAll( CharCollection c );
 /**
	 * @see Collection#removeAll(Collection)
	 */
 boolean removeAll( CharCollection c );
 /**
	 * @see Collection#retainAll(Collection)
	 */
 boolean retainAll( CharCollection c );
}
