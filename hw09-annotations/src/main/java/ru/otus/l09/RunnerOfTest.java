package ru.otus.l09;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunnerOfTest {

  public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    int success_counter = 0;

    String className = args[0];
    System.out.println(className);
//    String className = "ru.otus.l09.MyClassToTest";
    Class cl = Class.forName(className);

    Method[] method = cl.getMethods();
    List<Method> methodsBefore = new ArrayList<>();
    List<Method> methodsAfter = new ArrayList<>();
    List<Method> methodsTest = new ArrayList<>();

    for(Method md: method){
      if(md.isAnnotationPresent(Test.class)) {
        methodsTest.add(md);
      }
      if(md.isAnnotationPresent(Before.class)) {
        methodsBefore.add(md);

      }
      if(md.isAnnotationPresent(After.class)) {
        methodsAfter.add(md);

      }
    }
    Constructor<?> constructor = cl.getConstructor(null);

    for (Method methodTest: methodsTest){
      Object o = constructor.newInstance();
      methodsBefore.stream().forEach(methodBefore -> {
        try {
          methodBefore.invoke(o);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      });

      try {
        methodTest.invoke(o);
        System.out.printf("Test "+methodTest.getName()+" success!");
        success_counter++;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.printf("Test "+methodTest.getName()+" failed!");
      }

      methodsAfter.stream().forEach(methodAfter -> {
        try {
          methodAfter.invoke(o);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      });

    }
    System.out.println("Class " + className+" test results:");
    System.out.println("Tests performed: " + methodsTest.size());
    System.out.printf("Success: " + success_counter);
    System.out.printf("Failed: " + (methodsTest.size() - success_counter));

  }
}
