package ru.otus.l15;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class MyGsonWriterDemo {
  public static void main(String[] args) throws IllegalAccessException {
    Gson gson = new Gson();
    BagOfPrimitives obj = new BagOfPrimitives(22, "test", 10, new int[]{1, 2, 3});
    System.out.println(obj);

    String myJson = getJson(obj);
    System.out.println("myJson:\n"+myJson);

    String json = gson.toJson(obj);
    System.out.println(json);

    BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
    System.out.println(obj.equals(obj2));
    System.out.println(obj2);
  }

  static String getJson(Object value) throws IllegalAccessException {
    if(value instanceof Number){
      return value.toString();
    } else if (value instanceof String){
      return "\""+value+"\"";
    } else { //it is an object or array
 ///
      Class valueClass = value.getClass();
      StringBuilder sb = new StringBuilder();

      if (valueClass.isArray()){
        sb.append("[");
        for(int i = 0; i < Array.getLength(value); i++){
          if(i > 0){
            sb.append(",");
          }
          sb.append(getJson(Array.get(value, i)));
        }
        sb.append("]");
      } else {

        sb.append("{");

        String fieldName;
        Object fieldValue;
        Field[] declaredFields = valueClass.getDeclaredFields();
        for (Field field : declaredFields) {
          System.out.println(field);
          System.out.println(Modifier.isTransient(field.getModifiers()));
          if(Modifier.isTransient(field.getModifiers())){
            continue;
          }
          fieldName = field.getName();
          field.setAccessible(true);
          fieldValue = field.get(value);

          addToSb(sb,fieldName,getJson(fieldValue));

//          Class fieldType = field.getType();
//          field.getType().isArray();
//          if (fieldType.isArray()){
//            sb.append("[");
//            for(int i = 0; i < Array.getLength(fieldValue); i++){
//              if(i > 0){
//                sb.append(",");
//              }
//              sb.append(getJson(Array.get(fieldValue, i)));
//            }
//            sb.append("]");
//          } else {
//            sb.append(getJson(fieldValue));
//          }

//      if (field.getType().isPrimitive()){
//        fieldValue = field.get(obj).toString();
//        if(sb.length()>1){
//          sb.append(",");
//        }
//        sb.append("\""+fieldName+"\":"+fieldValue);
          //}// else if (field.getType
        }
        sb.append("}");
      }
      return sb.toString();

    }

//      if (value.isArray()){
//      sb.append("[");
//      for(int i = 0; i < Array.getLength(fieldValue); i++){
//        Array.get(fieldValue, i);
//      }
//      sb.append("]");
//    }

  }

  private static void addToSb(StringBuilder sb, String fieldName, String fieldValue) {
    if(sb.length()>1){
      sb.append(",");
    }
    sb.append("\""+fieldName+"\":"+fieldValue);

  }
}
