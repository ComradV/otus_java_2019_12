package ru.otus.l15;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;

import java.util.*;

class MyGsonWriterTest {
  Gson gson = new Gson();
  private static BagOfPrimitivesAndArray getBagOfPrimitives(int a){
    return new BagOfPrimitivesAndArray(a,"Hello",15,new int[]{1,2,3,4,5});
  }

  @Test
  @Description("Object, containing primitives and array")
  void primitivesAndArrayTest() throws IllegalAccessException {
    Object ob1 = getBagOfPrimitives(1);

    assertEquals(gson.toJson(ob1), MyGsonWriter.getJson(ob1));
  }

  @Test
  @Description("List")
  void getJson2() throws IllegalAccessException {
    List<Integer> list = List.of(1,2,3);

    assertEquals(gson.toJson(list), MyGsonWriter.getJson(list));
  }

  @Test
  @Description("Map")
  void getJson3() throws IllegalAccessException {
    Map<Integer, BagOfPrimitivesAndArray> map = new HashMap<>();
    map.put(1, getBagOfPrimitives(1));
    map.put(2, getBagOfPrimitives(2));

    assertEquals(gson.toJson(map), MyGsonWriter.getJson(map));
  }

  @Test
  @Description("Set")
  void getJson4() throws IllegalAccessException {
    Set<BagOfPrimitivesAndArray> set = new HashSet<>();
    set.add(getBagOfPrimitives(1));
    set.add(getBagOfPrimitives(2));
    set.add(getBagOfPrimitives(3));

    assertEquals(gson.toJson(set), MyGsonWriter.getJson(set));
  }

}
