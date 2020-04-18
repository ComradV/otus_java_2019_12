package ru.otus.l15;

import com.google.gson.Gson;

public class MyGsonWriterDemo {
  public static void main(String[] args) throws IllegalAccessException {
    Gson gson = new Gson();
    BagOfPrimitives obj = new BagOfPrimitives(22, "test", 10, new int[]{1, 2, 3});
    System.out.println(obj);

    String myJson = MyGsonWriter.getJson(obj);
    System.out.println("myJson:\n"+myJson);

    String json = gson.toJson(obj);
    System.out.println(json);

    BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
    System.out.println(obj.equals(obj2));
    System.out.println(obj2);
  }


}
