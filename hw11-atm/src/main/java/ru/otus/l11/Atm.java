package ru.otus.l11;

import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm {
  private final Map<Denomination, List<Cell>> cells;

  //number (of cells)-capacity-denom. For example, 2 cells for 50 rub (1000 in each) and 3 cells for 100 rub (800 in each) is 2-1000-5 3-800-100
  public Atm(String[] args){
    cells = new HashMap<>();

  }

  public int countValue() throws Exception {
    throw new Exception("Not implemented yet");
  }
  public void addBills() throws Exception {
    throw new Exception("Not implemented yet");
  }
  public void getMoney() throws Exception {
    throw new Exception("Not implemented yet");

  }
  private void addCell(Denomination denom, int capacity, int amount ){
    for(int  i = 0; i < amount; i++){

    }
  }
}
