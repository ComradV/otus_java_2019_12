package ru.otus.l11;

public enum Denomination {
  RUB100{
    @Override
    public int getValue(){return 100;}
    public String toString(){return "100 рублей (сотня)";}
  },
  RUB50{
    @Override
    public int getValue(){return 50;}
    public String toString(){return "50 рублей (полтинник)";}
  },
  RUB10{
    @Override
    public int getValue(){return 10;}
    public String toString(){return "10 рублей (десятка)";}
  };
  public abstract int getValue();
}