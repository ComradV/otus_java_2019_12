package ru.otus.l09;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

final class ReflectionHelper {

  private ReflectionHelper() {

  }

  public static Constructor<?> getConstructorWithoutParameters(String className) throws NoSuchMethodException, ClassNotFoundException {
    Class cl = Class.forName(className);
    return cl.getConstructor(null);
  }

  public static void addAnnotatedMethodsFromClass(String className, String annotationName, List<Method> listOfMethodsToFill) throws ClassNotFoundException {
    addAnnotatedMethodsFromClass(className, annotationName, listOfMethodsToFill, false);
  }

  public static void addAnnotatedMethodsFromClass(String className, String annotationName, List<Method> listOfMethodsToFill, boolean clearList) throws ClassNotFoundException {
    Class cl = Class.forName(className);
    Class annotationClass = Class.forName(annotationName);
    if(clearList){listOfMethodsToFill.clear();}
    for (Method md : cl.getMethods()) {
      if (md.isAnnotationPresent(annotationClass)) {
        listOfMethodsToFill.add(md);
      }
    }
  }
}

