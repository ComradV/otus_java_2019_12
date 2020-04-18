package ru.otus.l15;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class MyGsonWriter {
  static String getJson(Object value) throws IllegalAccessException {

    if(value instanceof Number){
      return value.toString();
    } else if (value instanceof String){
      return "\""+value+"\"";
    } else {
      Class valueClass = value.getClass();
      StringBuilder sb = new StringBuilder();
      if(Map.class.isAssignableFrom(valueClass)){
        sb.append("{");

        ((Map)value).forEach((k,v) -> {
                  try {
                    sb.append(getJson(k.toString())+":"+getJson(v)+",");
                  } catch (IllegalAccessException e) {
                    e.printStackTrace();
                  }
                });
        if (sb.charAt(sb.length()-1) == ','){
          sb.delete(sb.length()-1,sb.length());
        }
        sb.append("}");
      } else if (List.class.isAssignableFrom(valueClass)){
        sb.append("[");
        for(int i = 0; i < ((List)value).size(); i++){
          if(i > 0){
            sb.append(",");
          }
//          sb.append(getJson(Array.get(value, i)));
          sb.append(getJson(((List)value).get(i)));
        }
        sb.append("]");
      } else if(valueClass.isArray()){
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
//          System.out.println(field);
//          System.out.println(Modifier.isTransient(field.getModifiers()));
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

  }}
