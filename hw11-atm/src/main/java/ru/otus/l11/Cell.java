package ru.otus.l11;

public class Cell {
  final int capacity;
  final Denomination denomination;
  int currentAmount;
  public Cell(Denomination denomination, int capacity){
    this.denomination = denomination;
    this.capacity = capacity;
  }

  public int getCurrentValue(){
    return denomination.getValue() * currentAmount;
  }

  public int getFreeSpace() {
    return capacity - currentAmount;
  }

  public void addBills(int amount) throws CellException {
    if (amount > getFreeSpace()){
      throw new CellException("Cell has only "+getFreeSpace()+ "place for bills. Cannot add "+amount+" bills!");
    }
    currentAmount += amount;
  }

  public int getCurrentAmount(){return currentAmount;}

  public void removeBills(int amount) throws CellException {
    if (amount > currentAmount){
      throw new CellException("Cell has only "+ currentAmount + " bills. Cannot get "+amount+" bills!");
    }
    currentAmount -= amount;
  }

  public String getStatus(){
    return "denomination: "+denomination+", capacity:"+capacity+", amount: "+ currentAmount +", free space:"+getFreeSpace()+", value: "+ getCurrentValue();
  }
}
