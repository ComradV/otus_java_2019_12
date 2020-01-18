package ru.otus.l01;

import java.util.*;

public final class myArrayList<T> implements List<T> {
  private T[] internalArray;
  private int size;

  private static final int DEFAULT_CAPACITY = 10;

  public myArrayList(){
    this(DEFAULT_CAPACITY);
  }

  public myArrayList(int capacity){
    internalArray = (T[]) new Object[capacity];
  }

  @Override
  public int size() {

    return size;

  }

  @Override
  public boolean isEmpty() {

    return size==0;

  }

  @Override
  public boolean contains(Object o) {
    if (!(o instanceof T)){
      return false;
    }
    for(int i=0; i<size; i++){
      if (internalArray[i].equals((T)o)){return true}

    }
    return false;
  }

  @Override
  public Iterator iterator() {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException();

    return new Object[0];

  }

  @Override
  public boolean add(Object o) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public boolean addAll(Collection c) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public boolean addAll(int index, Collection c) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();

  }

  @Override
  public Object get(int index) {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public Object set(int index, Object element) {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public void add(int index, Object element) {
    throw new UnsupportedOperationException();

  }

  @Override
  public Object remove(int index) {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException();
    return 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
    return 0;
  }

  @Override
  public ListIterator listIterator() {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public ListIterator listIterator(int index) {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public List subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
    return null;
  }

  @Override
  public boolean retainAll(Collection c) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public boolean removeAll(Collection c) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public boolean containsAll(Collection c) {
    throw new UnsupportedOperationException();
    return false;
  }

  @Override
  public Object[] toArray(Object[] a) {
    throw new UnsupportedOperationException();
    return new Object[0];
  }
}
