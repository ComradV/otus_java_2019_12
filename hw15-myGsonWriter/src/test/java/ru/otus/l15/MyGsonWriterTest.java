package ru.otus.l15;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MyGsonWriterTest {
  Gson gson = new Gson();

  @Test
  void getJson1() throws IllegalAccessException {
    Object ob1 = new BagOfPrimitives(10,"Hello",15,new int[]{1,2,3,4,5});
    assertEquals(gson.toJson(ob1), MyGsonWriter.getJson(ob1));
  }

  @Test
  void getJson2() throws IllegalAccessException {
    List<Integer> ob2 = List.of(1,2,3);

//    System.out.println(gson.toJson(ob2));
    assertEquals(gson.toJson(ob2), MyGsonWriter.getJson(ob2));
  }

  @Test
  void getJson3() throws IllegalAccessException {
    Map<Integer, BagOfPrimitives> ob3 = new HashMap<>();
    ob3.put(1, new BagOfPrimitives(10,"1",15,new int[]{1,2}));
    ob3.put(2, new BagOfPrimitives(11,"2",18,new int[]{1,2,3}));
////    ob3.put(new BagOfPrimitives(10,"1",15,new int[]{1,2}), new BagOfPrimitives(10,"1",15,new int[]{1,2}));
////    ob3.put(new BagOfPrimitives(11,"2",15,new int[]{1,2}), new BagOfPrimitives(11,"2",18,new int[]{1,2,3}));
//
//    String myJson = MyGsonWriter.getJson(ob3);
////    myJson = gson.toJson(ob3);
//    System.out.println("My: "+myJson);
//
//    System.out.println("Typ:"+gson.toJson(ob3));
////    System.out.println(new BagOfPrimitives(10,"1",15,new int[]{1,2}));
//    Type mapType = new TypeToken<Map<Integer, BagOfPrimitives>>(){}.getType();
//    Map<Integer, BagOfPrimitives> ob4 = gson.fromJson(myJson, mapType);
//    System.out.println(ob4.get(1));
//    System.out.println(ob4.get(2));

    assertEquals(gson.toJson(ob3), MyGsonWriter.getJson(ob3));
  }

  @Test
  void getJson4() throws IllegalAccessException {
    Map<Integer, BagOfPrimitives> ob4 = new HashMap<>();
    ob4.put(1, new BagOfPrimitives(10,"1",15,new int[]{1,2}));
    ob4.put(2, new BagOfPrimitives(11,"2",18,new int[]{1,2,3}));
    assertEquals(gson.toJson(ob4), MyGsonWriter.getJson(ob4));
  }
}