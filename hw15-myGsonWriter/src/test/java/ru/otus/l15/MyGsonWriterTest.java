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
  void primitivesAndArrayTest() throws IllegalAccessException {
    Object ob1 = getBagOfPrimitives(1);

    assertEquals(gson.toJson(ob1), MyGsonWriter.getJson(ob1));
  }

  @Test
  void ListTest() throws IllegalAccessException {
    List<Integer> list = List.of(1,2,3);

    assertEquals(gson.toJson(list), MyGsonWriter.getJson(list));
  }

  @Test
  void MapTest() throws IllegalAccessException {
    Map<Integer, BagOfPrimitivesAndArray> map = new HashMap<>();
    map.put(1, getBagOfPrimitives(1));
    map.put(2, getBagOfPrimitives(2));

    assertEquals(gson.toJson(map), MyGsonWriter.getJson(map));
  }

  @Test
  void SetTest() throws IllegalAccessException {
    Set<BagOfPrimitivesAndArray> set = new HashSet<>();
    set.add(getBagOfPrimitives(1));
    set.add(getBagOfPrimitives(2));
    set.add(getBagOfPrimitives(3));

    assertEquals(gson.toJson(set), MyGsonWriter.getJson(set));
  }

  @Test
  void primitivesTest() throws IllegalAccessException {

    assertEquals(gson.toJson(null), MyGsonWriter.getJson(null));
    assertEquals(gson.toJson((byte)1), MyGsonWriter.getJson(((byte)1)));
    assertEquals(gson.toJson((byte)1), MyGsonWriter.getJson(((byte)1)));
    assertEquals(gson.toJson((short)1f), MyGsonWriter.getJson(((short)1f)));
    assertEquals(gson.toJson(1),MyGsonWriter.getJson((1)));
    assertEquals(gson.toJson(1L), MyGsonWriter.getJson((1L)));
    assertEquals(gson.toJson(1f), MyGsonWriter.getJson((1f)));
    assertEquals(gson.toJson(1d), MyGsonWriter.getJson((1d)));
    assertEquals(gson.toJson("aaa"), MyGsonWriter.getJson(("aaa")));
    assertEquals(gson.toJson("a'aa"), MyGsonWriter.getJson(("a'aa")));
    assertEquals(gson.toJson("a\"aa"), MyGsonWriter.getJson(("a\"aa")));
    assertEquals(gson.toJson('a'), MyGsonWriter.getJson(('a')));
    assertEquals(gson.toJson(new int[] {1, 2, 3}), MyGsonWriter.getJson((new int[] {1, 2, 3})));
    assertEquals(gson.toJson(List.of(1, 2 ,3)), MyGsonWriter.getJson((List.of(1, 2 ,3))));
    assertEquals(gson.toJson(Collections.singletonList(1)), MyGsonWriter.getJson((Collections.singletonList(1))));
  }
}
