package ru.otus.l09;

import java.lang.reflect.InvocationTargetException;

public class App {
  public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
    String className = args[0];
    RunnerOfTest tester = new RunnerOfTest(className);
    tester.makeTests();
    tester.printTestStatistics();
  }
}
