package ru.otus.l11;

public class App {
  public static void main(String[] args) {
    System.out.println(Denomination.RUB50);
    System.out.println(Denomination.RUB50.getValue());
      for (String s: args) {
        System.out.println(s);
      }
    }
}
