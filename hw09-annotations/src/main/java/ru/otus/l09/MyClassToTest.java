package ru.otus.l09;

public class MyClassToTest {

  @Test
  public int generateFour(){
    return 4;
  }

  public int addInt(int x, int y){
    return x + y;
  }

  public int addOne(int x){
    return x + 1;
  }

  @Before
  public void printBefore(){
    System.out.println("printBefore");
  }
  @After
  public void printAfter(){
    System.out.println("printAfter");
  }
  

}

