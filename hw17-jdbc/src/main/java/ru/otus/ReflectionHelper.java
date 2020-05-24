package ru.otus;

import org.javatuples.Quartet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ReflectionHelper {

  private ReflectionHelper() {

  }

  public static String getIdFieldName(String className) throws Exception {
    Class cl = Class.forName(className);
    return getIdFieldName(cl);
  }

  public static String getIdFieldName(Class cl) throws IllegalArgumentException {
    List<Field> fieldsWithIdAnnotation = getAnnotatedFields(cl, "Id");
    if(fieldsWithIdAnnotation.size() == 1){
      return fieldsWithIdAnnotation.get(0).getName();
    } else if(fieldsWithIdAnnotation.isEmpty()){
      throw new IllegalArgumentException("No @Id field in class "+cl.getName());
    } else {
      throw new IllegalArgumentException("More than 1 @Id field in class "+cl.getName());
    }
  }

  public static List<Quartet<Field, String, String, Integer>> getFields(Class cl) {
    List<Quartet<Field,String, String, Integer>> fieldsList= new ArrayList<>();
    List<Field> fieldsWithIdAnnotation = getAnnotatedFields(cl, ru.otus.myAnnotations.Field.class);
    ru.otus.myAnnotations.Field currentAnnotation;
    for(Field field: fieldsWithIdAnnotation){
      currentAnnotation = field.getAnnotation(ru.otus.myAnnotations.Field.class);
      fieldsList.add(Quartet.with(field, field.getName(), currentAnnotation.type(), currentAnnotation.size()));
    }
    return fieldsList;
  }

  private static List<Field> getAnnotatedFields(Class cl, String annotationName) {
    try {
      Class annotationClass = Class.forName(ReflectionHelper.class.getPackageName()+".myAnnotations."+annotationName);
      return getAnnotatedFields(cl, annotationClass);
    } catch (ClassNotFoundException e){
      throw new RuntimeException(e);
    }

  }

  private static List<Field> getAnnotatedFields(Class cl, Class annotationClass) {
    List<Field> listAnnotatedFields = new ArrayList<>();
    for (Field md : cl.getDeclaredFields()) {
      if (md.isAnnotationPresent(annotationClass)) {
        listAnnotatedFields.add(md);
      }
    }
    return listAnnotatedFields;
  }

  public static Constructor<?> getConstructorWithoutParameters(String className) throws NoSuchMethodException, ClassNotFoundException {
    Class cl = Class.forName(className);
    return cl.getConstructor(null);
  }

  private static void addAnnotatedMethodsFromClass(String className, String annotationName, List<Method> listOfMethodsToFill) throws ClassNotFoundException {
    addAnnotatedMethodsFromClass(className, annotationName, listOfMethodsToFill, false);
  }

  private static void addAnnotatedMethodsFromClass(String className, String annotationName, List<Method> listOfMethodsToFill, boolean clearList) throws ClassNotFoundException {
    Class cl = Class.forName(className);
    Class annotationClass = Class.forName(annotationName);
    if(clearList){listOfMethodsToFill.clear();}
    for (Method md : cl.getMethods()) {
      if (md.isAnnotationPresent(annotationClass)) {
        listOfMethodsToFill.add(md);
      }
    }
  }

  public static <T> Field getIdField(Class<T> cl) {
    List<Field> fieldsWithIdAnnotation = getAnnotatedFields(cl, "Id");
    if(fieldsWithIdAnnotation.size() == 1){
      return fieldsWithIdAnnotation.get(0);
    } else if(fieldsWithIdAnnotation.isEmpty()){
      throw new IllegalArgumentException("No @Id field in class "+cl.getName());
    } else {
      throw new IllegalArgumentException("More than 1 @Id field in class "+cl.getName());
    }
  }
}
