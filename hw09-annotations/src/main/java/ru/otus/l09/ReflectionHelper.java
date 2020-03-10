package ru.otus.l09;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

final class ReflectionHelper {

  private ReflectionHelper() {

  }

  static void fillMethohs(String className, List<Method> methodsBefore, List<Method> methodsTest, List<Method> methodsAfter) throws ClassNotFoundException {
    Class cl = Class.forName(className);
    for (Method md : cl.getMethods()) {
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

//    distributeMethods(cl.getMethods());

  }

  static Constructor<?> getConstructor(String className) throws NoSuchMethodException, ClassNotFoundException {
    Class cl = Class.forName(className);
    return cl.getConstructor(null);
  }

}

