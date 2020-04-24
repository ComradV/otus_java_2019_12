package ru.otus.l03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DIYArrayListTest {
  private static final int TEST_SIZE = 30;
  private static final int TEST_MULTIPLIER = 10;
  DIYArrayList<Integer> diyArrayList;

  @BeforeEach
  void setUp() {
    diyArrayList = getTestArrayList();
//    System.out.println(diyArrayList);
  }

  private DIYArrayList getTestArrayList(){
    DIYArrayList<Integer> created = new DIYArrayList<>();
    for(int i = 0; i < TEST_SIZE; i++){
      created.add(i*TEST_MULTIPLIER);
    }
    return created;
  }

  @Test
  void size() {
    assertEquals(TEST_SIZE, diyArrayList.size());
  }

  @Test
  void isEmpty() {
    assertEquals(false, diyArrayList.isEmpty());
    diyArrayList.clear();
    assertEquals(true, diyArrayList.isEmpty());
  }

  @Test
  void contains() {
    int testValue = TEST_MULTIPLIER*TEST_SIZE;
    assertEquals(false, diyArrayList.contains(testValue));
    diyArrayList.add(testValue);
    assertEquals(true, diyArrayList.contains(testValue));
  }

  @Test
  void addAll(){
    Collections.addAll(diyArrayList,1,2,3);

    assertEquals(TEST_SIZE+3,diyArrayList.size());
    assertEquals(1,diyArrayList.get(TEST_SIZE));
    assertEquals(2,diyArrayList.get(TEST_SIZE+1));
    assertEquals(3,diyArrayList.get(TEST_SIZE+2));

  }

  @Test
  void copy(){
    DIYArrayList<Integer> newDIYArrayList = getTestArrayList();
    newDIYArrayList.set(0, newDIYArrayList.get(0)+10);

    assertNotEquals(newDIYArrayList, diyArrayList);

    Collections.copy(newDIYArrayList,diyArrayList);

    assertEquals(newDIYArrayList, diyArrayList);

  }

  @Test
  void sort(){
    DIYArrayList<Integer> newDIYArrayList = getTestArrayList();

    Collections.sort(diyArrayList);
    assertEquals(newDIYArrayList, diyArrayList);

    int a = diyArrayList.get(0);
    diyArrayList.set(0,diyArrayList.get(1));
    diyArrayList.set(1,a);

    assertNotEquals(newDIYArrayList, diyArrayList);

    Collections.sort(diyArrayList);
    assertEquals(newDIYArrayList, diyArrayList);

  }

  @Test
  void equals(){
    assertEquals(diyArrayList, diyArrayList);

    DIYArrayList<Integer> newDIYArrayList = getTestArrayList();
    assertTrue(diyArrayList.equals(newDIYArrayList));

    newDIYArrayList.set(0, newDIYArrayList.get(0)+10);
    assertFalse(diyArrayList.equals(newDIYArrayList));

    newDIYArrayList = getTestArrayList();
    newDIYArrayList.add(10);
    assertFalse(diyArrayList.equals(newDIYArrayList));

    assertFalse(diyArrayList.equals(newDIYArrayList));
  }
}