package ru.otus.l09;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunnerOfTest {
  private int successCounter;
  private final String className;
  private final List<Method> methodsBefore = new ArrayList<>(),
          methodsAfter = new ArrayList<>(),
          methodsTest = new ArrayList<>();
  private final Constructor<?> constructor;

  public RunnerOfTest(String className) throws ClassNotFoundException, NoSuchMethodException {
    this.className = className;
    ReflectionHelper.addAnnotatedMethodsFromClass(className, "ru.otus.l09.Before", methodsBefore);
    ReflectionHelper.addAnnotatedMethodsFromClass(className, "ru.otus.l09.Test", methodsTest);
    ReflectionHelper.addAnnotatedMethodsFromClass(className, "ru.otus.l09.After", methodsAfter);
    constructor = ReflectionHelper.getConstructorWithoutParameters(className);

  }


  public void makeTests() throws InvocationTargetException, IllegalAccessException {
    boolean success;
    Object o = null;
    for (Method methodTest : methodsTest) {
      success = true;
      try {
        o = constructor.newInstance();
      } catch (Exception e){
        e.printStackTrace();
        success = false;
      }
      success &= runMethods(o, methodsBefore);
      if(success){
        success &= makeSingleTest(o, methodTest);
      }
      success &= runMethods(o, methodsAfter);
      successCounter += success ? 1 : 0;
    }

  }

  private static boolean runMethods (Object o, List<Method> methods) {
    boolean success = true;
    for (Method method:methods) {
      try {
        method.invoke(o);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
        success = false;
      }
    }
    return success;
  }

  private boolean makeSingleTest(Object o, Method methodTest) {
    boolean success = true;
    try {
      methodTest.invoke(o);
      System.out.printf("Test " + methodTest.getName() + " success!");
    } catch (Exception e) {
      e.printStackTrace();
      success = false;
      System.out.printf("Test " + methodTest.getName() + " failed!");
    }
    return success;
  }

  public void printTestStatistics() {
    System.out.println("Class " + className + " test results:");
    System.out.println("Tests performed: " + methodsTest.size());
    System.out.println("Success: " + successCounter);
    System.out.println("Failed: " + (methodsTest.size() - successCounter));
  }

}
