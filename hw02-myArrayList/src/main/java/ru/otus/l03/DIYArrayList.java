package ru.otus.l03;

import java.util.*;

public final class DIYArrayList<T> implements List<T> {
  private T[] internalArray;
  private int size;

  private static final int DEFAULT_CAPACITY = 10;
  private static final int GROWTH_RATE = 2;

  public DIYArrayList(){
    this(DEFAULT_CAPACITY);
  }

  public DIYArrayList(int capacity){
    internalArray = (T[]) new Object[capacity];
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DIYArrayList, size: "+size+", capacity: "+internalArray.length+", elements: ");
    for(int i = 0; i < size-1; i++){
      sb.append(internalArray[i]+", ");
    }
    if(size > 0){
      sb.append(internalArray[size-1]);
    }
    return sb.toString();
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
    for(int i = 0; i < size; i++){
      if (internalArray[i].equals(o)){return true;}
    }
    return false;
  }

  @Override
  public Iterator iterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] toArray() {

    return internalArray.clone();

  }

  @Override
  public boolean add(T o) {
    if(size == internalArray.length){expand();}
    internalArray[size++] = o;
    return true;
  }

  private void expand() {

    T[] newInternalArray = (T[]) new Object[internalArray.length * GROWTH_RATE];
    System.arraycopy(internalArray,0,newInternalArray,0,internalArray.length);
    internalArray = newInternalArray;
  }

  @Override
  public boolean remove(Object o) {
    boolean found = false;
    int index = 0;
    for(int i = 0; i < size; i++){
      if (internalArray[i].equals(o)){
        found = true;
        index = i;
        break;
      }
    }
    if(found){
      remove(index);
    }
    return found;

  }

  @Override
  public boolean addAll(Collection c) {
    return addAll(size, c);
  }

  @Override
  public boolean addAll(int offset, Collection c) {
    while(size + c.size() < internalArray.length){
      expand();
    }
    System.arraycopy(c.toArray(),0,internalArray,offset,c.size());
    return true;
  }

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public T get(int index) {
    checkIndex(index);
    return internalArray[index];
  }

  private void checkIndex(int index){
    if(index > size-1 || index < 0){
      throw new IndexOutOfBoundsException("No such index in collection!");
    }
  }

  @Override
  public T set(int index, T element) {
    checkIndex(index);
    T prevValue = internalArray[index];
    internalArray[index] = element;
    return prevValue;
  }

  @Override
  public void add(int index, Object element) {
    throw new UnsupportedOperationException();

  }

  @Override
  public T remove(int index) {
    checkIndex(index);
    T prevValue = internalArray[index];
    System.arraycopy(internalArray,index+1,internalArray,index,size-index);
    size--;
    return prevValue;
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator listIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ListIterator listIterator(int index) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] toArray(Object[] a) {
    throw new UnsupportedOperationException();
  }
}
