package ru.otus.l09;

public class MyClassToTest {
  int x,y;

  @Test
  public void sumIntegers(){
    x = 1;
    y = 2;
    System.out.println("x = " + x + ", y = " + y + ", x+y = " + addInt(x,y));
  }

  @Test
  public void prodIntegers(){
    x = 3;
    y = 2;
    System.out.println("x = " + x + ", y = " + y + ", x*y = " + prodInt(x,y));
  }

  public int addInt(int x, int y){
    return x + y;
  }

  public int prodInt(int x, int y){
    return x * y;
  }

  @Before
  public void printBefore(){
    System.out.println("Before:" + this.toString());

  }

  @Override
  public String toString() {
    return "MyClassToTest{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }

  @After
  public void printAfter(){
    System.out.println("After:" + this.toString());

  }
  

}

