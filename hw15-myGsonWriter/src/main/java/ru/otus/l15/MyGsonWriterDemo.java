package ru.otus.l15;

import com.google.gson.Gson;

public class MyGsonWriterDemo {
  public static void main(String[] args) throws IllegalAccessException {
    BagOfPrimitivesAndArray obj = new BagOfPrimitivesAndArray(22, "test", 10, new int[]{1, 2, 3});
    System.out.println(obj);

    String myJson = MyGsonWriter.getJson(obj);
    System.out.println("myJson  :\n"+myJson);

    Gson gson = new Gson();
    String gsonJson = gson.toJson(obj);
    System.out.println("gsonJson:\n"+gsonJson);

    BagOfPrimitivesAndArray obj2 = gson.fromJson(myJson, BagOfPrimitivesAndArray.class);
    System.out.println("Objects are equal: " + obj.equals(obj2));
    System.out.println("Deserialized object: " + obj2);
  }


}
