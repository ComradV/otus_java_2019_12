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
    diyArrayList = new DIYArrayList<>();
    for(int i = 0; i < TEST_SIZE; i++){
      diyArrayList.add(i*TEST_MULTIPLIER);
    }
    System.out.println(diyArrayList);
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
    List<Integer> newList = Arrays.asList(new Integer[]{1,2,3,4,5});
    for(int el:newList){
//      System.out.println(el);
    }
    Collections.addAll(diyArrayList,1,2,3);
    System.out.println(diyArrayList);
  }

  @Test
  void copy(){

  }

  @Test
  void sort(){

  }

}