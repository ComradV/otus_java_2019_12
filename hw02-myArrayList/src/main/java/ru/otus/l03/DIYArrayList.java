package ru.otus.l03;

import javax.imageio.ImageTranscoder;
import java.util.*;
import java.util.function.Consumer;

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
  public ListIterator<T> listIterator() {
    return new Itr(0);
  }

  private class Itr implements ListIterator<T>{
    int cursor;       // index of next element to return
    int lastRet = -1; // index of last element returned; -1 if no such

    public Itr(int cursor){
      this.cursor = cursor;
    }

    public boolean hasNext() {
      return cursor != size;
    }

    public T next() {
      int i = cursor;
      if (i >= DIYArrayList.this.size)
        throw new NoSuchElementException();
      cursor = i + 1;
      return DIYArrayList.this.internalArray[lastRet = i];
    }

    public boolean hasPrevious() {
      return cursor != 0;
    }

    public int nextIndex() {
      return cursor;
    }

    public int previousIndex() {
      return cursor - 1;
    }

    @Override
    public void remove() {
        if (lastRet < 0)
          throw new IllegalStateException();

        try {
          DIYArrayList.this.remove(lastRet);
          cursor = lastRet;
          lastRet = -1;
        } catch (IndexOutOfBoundsException ex) {
          throw new ConcurrentModificationException();
        }

    }

    public T previous() {
      int i = cursor - 1;
      if (i < 0)
        throw new NoSuchElementException();
      Object[] elementData = DIYArrayList.this.internalArray;
      if (i >= elementData.length)
        throw new ConcurrentModificationException();
      cursor = i;
      return (T) elementData[lastRet = i];
    }

    public void set(T e) {
      if (lastRet < 0)
        throw new IllegalStateException();

      try {
        DIYArrayList.this.set(lastRet, e);
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }
    }

    public void add(T e) {

      try {
        int i = cursor;
        DIYArrayList.this.add(i, e);
        cursor = i + 1;
        lastRet = -1;
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }

    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    DIYArrayList<T> that = (DIYArrayList<T>) obj;
    if(that.size() != size()){
      return false;
    }

    for(int i = 0; i < size(); i++){
      if (!get(i).equals(that.get(i))){
        return false;
      }
    }
    return true;
  }

  @Override
  public ListIterator listIterator(int index) {
    return new Itr(index);
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
    return Arrays.copyOf(internalArray, size);
  }

  @Override
  public void sort(Comparator<? super T> c) {
    Arrays.sort((T[]) internalArray, 0, size, c);
  }
}
