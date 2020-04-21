package ru.otus.l15;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Returns JSON string. Alternative to Gson writer.
 * Supports:
 * Primitives
 * Array
 * Collections: List, Map, Set
 * Objects with fields od described types
 *
 * Works with "transient" fields (skips it)
 * @author  Gleb Vasiliev
 */
public abstract class MyGsonWriter {
  static String getJson(Object value) throws IllegalAccessException {
    if (value==null){
      return "null";
    } else if(value instanceof Number){
      return value.toString();
    } else if (value instanceof String) {
      return "\"" + ((String)value)
              .replaceAll("\"","\\\\\"")
              .replaceAll("'","\\\\u0027")
              + "\"";
    } else if (value instanceof Character) {
      return "\"" + value + "\"";
    } else {
      Class valueClass = value.getClass();
      StringBuilder sb = new StringBuilder();
      if (Map.class.isAssignableFrom(valueClass)) {
        sb.append("{");

        Set keySet = ((Map) value).keySet();
        for (Object keyValue : keySet) {
          addToSb(sb, keyValue.toString(), getJson(((Map) value).get(keyValue)));
        }

        sb.append("}");
      } else if (List.class.isAssignableFrom(valueClass) || Set.class.isAssignableFrom(valueClass)) {
        sb.append("[");
        Iterator iter = ((Collection) value).iterator();
        while (iter.hasNext()) {
          addToSb(sb, getJson(iter.next()));
        }
        sb.append("]");
      } else if (valueClass.isArray()) {
        sb.append("[");
        for (int i = 0; i < Array.getLength(value); i++) {
          addToSb(sb, getJson(Array.get(value, i)));
        }
        sb.append("]");
      } else {

        sb.append("{");

        String fieldName;
        Object fieldValue;
        Field[] declaredFields = valueClass.getDeclaredFields();
        for (Field field : declaredFields) {
          if (Modifier.isTransient(field.getModifiers())) {
            continue;
          }
          fieldName = field.getName();
          field.setAccessible(true);
          fieldValue = field.get(value);

          addToSb(sb, fieldName, getJson(fieldValue));
        }
        sb.append("}");
      }
      return sb.toString();

    }

  }

  private static void addToSb(StringBuilder sb, String fieldName, String fieldValue) {
    if(sb.length()>1){
      sb.append(",");
    }
    sb.append("\""+fieldName+"\":"+fieldValue);
  }

  private static void addToSb(StringBuilder sb, String value) {
    if(sb.length()>1){
      sb.append(",");
    }
    sb.append(value);
  }

}
