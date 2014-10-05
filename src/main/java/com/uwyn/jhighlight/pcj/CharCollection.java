/*
 *  Primitive Collections for Java.
 *  Copyright (C) 2002  Søren Bak
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.uwyn.jhighlight.pcj;

/**
 *  This interface defines collections of char values.
 *
 *  @see        java.util.Collection
 *
 *  @author     S&oslash;ren Bak
 *  @version    1.1     2002/30/12
 *  @since      1.0
 */
import com.uwyn.jhighlight.pcj.CharCollection;
import com.uwyn.jhighlight.pcj.CharIterator;

public interface CharCollection
{
	
    /**
     *  Adds an element to this collection.
     *
     *  @param      v
     *              the element to add to this collection.
     *
     *  @return     <tt>true</tt> if this collection was modified
     *              as a result of adding <tt>v</tt>; returns
     *              <tt>false</tt> otherwise.
     *
     *  @throws     UnsupportedOperationException
     *              if the operation is not supported by this
     *              collection.
     *
     *  @see        #addAll(CharCollection)
     */
    boolean add(char v);
	
    /**
     *  Adds all the elements of a specified collection to
     *  this collection.
     *
     *  @param      c
     *              the collection whose elements to add to this
     *              collection.
     *
     *  @return     <tt>true</tt> if this collection was modified
     *              as a result of adding the elements of <tt>c</tt>;
     *              returns <tt>false</tt> otherwise.
     *
     *  @throws     UnsupportedOperationException
     *              if the operation is not supported by this
     *              collection.
     *
     *  @throws     NullPointerException
     *              if <tt>c</tt> is <tt>null</tt>.
     *
     *  @see        #add(char)
     */
    boolean addAll(CharCollection c);
	
    /**
     *  Clears this collection.
     *
     *  @throws     UnsupportedOperationException
     *              if the operation is not supported by this
     *              collection.
     */
    void clear();
	
    /**
     *  Indicates whether this collection contains a specified
     *  element.
     *
     *  @param      v
     *              the element to test for containment.
     *
     *  @return     <tt>true</tt> if <tt>v</tt> is contained in this
     *              collection; returns <tt>false</tt> otherwise.
     *
     *  @see        #containsAll(CharCollection)
     */
    boolean contains(char v);
	
    /**
     *  Indicates whether all elements of a specified
     *  collection is contained in this collection.
     *
     *  @param      c
     *              the collection whose elements to test for
     *              containment.
     *
     *  @return     <tt>true</tt> if all the elements of <tt>c</tt>
     *              are contained in this collection; returns
     *              <tt>false</tt> otherwise.
     *
     *  @throws     NullPointerException
     *              if <tt>c</tt> is <tt>null</tt>.
     *
     *  @see        #contains(char)
     */
    boolean containsAll(CharCollection c);
	
    /**
     *  Indicates whether this collection is equal to some object.
     *
     *  @param      obj
     *              the object with which to compare this collection.
     *
     *  @return     <tt>true</tt> if this collection is equals to
     *              <tt>obj</tt>; returns <tt>false</tt> otherwise.
     */
    boolean equals(Object obj);
	
    /**
     *  Returns a hash code value for this collection.
     *
     *  @return     a hash code value for this collection.
     */
    int hashCode();
	
    /**
     *  Indicates whether this collection is empty.
     *
     *  @return     <tt>true</tt> if this collection is empty; returns
     *              <tt>false</tt> otherwise.
     */
    boolean isEmpty();
	
    /**
     *  Returns an iterator over this collection.
     *
     *  @return     an iterator over this collection.
     */
    CharIterator iterator();
	
    /**
     *  Removes a specified element from this collection.
     *
     *  @param      v
     *              the char value to remove from this collection.
     *
     *  @return     <tt>true</tt> if this collection was modified
     *              as a result of removing <tt>v</tt>; returns
     *              <tt>false</tt> otherwise.
     *
     *  @throws     UnsupportedOperationException
     *              if the operation is not supported by this
     *              collection.
     */
    boolean remove(char v);
	
    /**
     *  Removes all the elements of a specified collection from
     *  this collection.
     *
     *  @param      c
     *              the collection whose elements to remove from this
     *              collection.
     *
     *  @return     <tt>true</tt> if this collection was modified
     *              as a result of removing the elements of <tt>c</tt>;
     *              returns <tt>false</tt> otherwise.
     *
     *  @throws     UnsupportedOperationException
     *              if the operation is not supported by this
     *              collection.
     *
     *  @throws     NullPointerException
     *              if <tt>c</tt> is <tt>null</tt>.
     */
    boolean removeAll(CharCollection c);
	
    /**
     *  Retains only the elements of a specified collection in
     *  this collection.
     *
     *  @param      c
     *              the collection whose elements to retain in this
     *              collection.
     *
     *  @return     <tt>true</tt> if this collection was modified
     *              as a result of removing the elements not contained
     *              in <tt>c</tt>;
     *              returns <tt>false</tt> otherwise.
     *
     *  @throws     UnsupportedOperationException
     *              if the operation is not supported by this
     *              collection.
     *
     *  @throws     NullPointerException
     *              if <tt>c</tt> is <tt>null</tt>.
     */
    boolean retainAll(CharCollection c);
	
    /**
     *  Returns the number of elements in this collection.
     *
     *  @return     the number of elements in this collection.
     */
    int size();
	
    /**
     *  Returns the elements of this collection as an array.
     *
     *  @return     a new array containing the elements of this
     *              collection.
     */
    char[] toArray();
	
    /**
     *  Returns the elements of this collection as an array.
     *
     *  @param      a
     *              an array to fill with the elements of this
     *              collection; if <tt>a</tt> is <tt>null</tt> or not
     *              big enough to contain all the elements of this
     *              collection, an new array is allocated,
     *              and <tt>a</tt> is not changed.
     *
     *  @return     <tt>a</tt>, if <tt>a</tt> has room for all the
     *              elements of this collection; otherwise a new
     *              array is allocated, filled with the elements of
     *              this collection, and returned.
     */
    char[] toArray(char[] a);
	
    /**
     *  Minimizes the memory used by this collection. The exact
     *  operation of this method depends on the class implementing it.
     *  Implementors may choose to ignore it completely.
     */
    void trimToSize();
	
}
