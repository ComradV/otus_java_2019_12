package ru.otus.l11;

public class App {
  public static void main(String[] args) throws Exception {
    Atm atm = new Atm(new String[]{"3-10-100", "2-100-50"});
    System.out.println(atm.getStatus());
    atm.addBills(15,100);
    atm.addBills(3,50);
    System.out.println(atm.getStatus());
    atm.getMoney(750);
    System.out.println(atm.getStatus());
  }
}
