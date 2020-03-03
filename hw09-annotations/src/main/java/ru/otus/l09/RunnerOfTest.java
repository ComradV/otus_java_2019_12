package ru.otus.l09;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunnerOfTest {
  private int success_counter;
  private String className;
  private List<Method> methodsBefore, methodsAfter, methodsTest;
  private Constructor<?> constructor;

  private RunnerOfTest(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
    this.className = className;
    System.out.println(className);
//    String className = "ru.otus.l09.MyClassToTest";
    Class cl = Class.forName(className);

    Method[] method = cl.getMethods();
    methodsBefore = new ArrayList<>();
    methodsAfter = new ArrayList<>();
    methodsTest = new ArrayList<>();
    for (Method md : method) {
      if (md.isAnnotationPresent(Test.class)) {
        methodsTest.add(md);
      }
      if (md.isAnnotationPresent(Before.class)) {
        methodsBefore.add(md);

      }
      if (md.isAnnotationPresent(After.class)) {
        methodsAfter.add(md);

      }
    }
    constructor = cl.getConstructor(null);

  }
  private void makeTests() throws InstantiationException, InvocationTargetException, IllegalAccessException {
    for (Method methodTest : methodsTest) {
      Object o = constructor.newInstance();
      runMethods(o, methodsBefore);
      makeSingleTest(o, methodTest);
      runMethods(o, methodsAfter);
    }

  }

  private void makeSingleTest(Object o, Method methodTest) {
    try {
      methodTest.invoke(o);
      System.out.printf("Test " + methodTest.getName() + " success!");
      success_counter++;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.printf("Test " + methodTest.getName() + " failed!");
    }
  }

  public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

    String className = args[0];
    RunnerOfTest tester = new RunnerOfTest(className);
    tester.makeTests();
    tester.printTestStatistics();

  }

  private void printTestStatistics() {
    System.out.println("Class " + className + " test results:");
    System.out.println("Tests performed: " + methodsTest.size());
    System.out.println("Success: " + success_counter);
    System.out.println("Failed: " + (methodsTest.size() - success_counter));
  }

  private static void runMethods (Object o, List<Method> methods) throws IllegalAccessException, InvocationTargetException{
    methods.stream().forEach(methodBefore -> {
      try {
        methodBefore.invoke(o);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
//        throw e;
      }
    });
  }
}
