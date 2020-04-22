package ru.otus.l11;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Atm {
  private final Map<Denomination, List<Cell>> cells = new HashMap<>();
  private final DenominationHelper denominationHelper = new DenominationHelper();
  /**
   * Constructs a new {@code Atm} with provided cell parameters
   * For example, if you need ATM with 2 cells of 1000 50-rub bills and 3 cells of 2000 100-rub bills,
   * tou should use:
   * <pre>
   *     Atm atm = new Atm(new int[][]{{2, 1000, 50}, {3, 2000, 100}}); </pre>
   *
   * @param   cellDescriptions contains array of [numberOfCells, capacity, denomination] type
   * @throws  IllegalArgumentException if the specified argument does not contain 3-elements array in each position
   */
  public Atm(int[][] cellDescriptions) throws IllegalArgumentException {

    int cellAmount, cellCapacity, denomValue;

    for (int[] cellDescription:cellDescriptions){
      if (cellDescription.length != 3){
        throw new IllegalArgumentException("Use [numOfCells,capacity,denom] arguments!");
      }
      cellAmount = cellDescription[0];
      cellCapacity = cellDescription[1];
      denomValue = cellDescription[2];

      addCell(denominationHelper.getByValue(denomValue),cellCapacity,cellAmount);
    }
  }

  public int countValue() {
    int totalValue = 0;
    for(List<Cell> cells:this.cells.values()){
      for(Cell cell:cells){
        totalValue += cell.getCurrentValue();
      }
    }
    return totalValue;
  }

  public void addBills(int amount, int denominationValue) throws Exception {
    Denomination denomination = denominationHelper.getByValue(denominationValue);
    List<Integer> distribution= new ArrayList<>();
    int amountLeftToDistribute = amount;
    int amountForCurrentCell;
    if (denomination == null){
      throw new IllegalArgumentException("No denom "+denominationValue);
    }
    List<Cell> denominationCellsList = cells.get(denomination);
    if(denominationCellsList == null){
      throw new IllegalArgumentException("No cells for "+denominationValue+" bills in this ATM!");
    }
    for(int i = 0; i < denominationCellsList.size(); i++){
      amountForCurrentCell = Math.min(amountLeftToDistribute, denominationCellsList.get(i).getFreeSpace());
      amountLeftToDistribute -= amountForCurrentCell;
      distribution.add(amountForCurrentCell);
      if(amountLeftToDistribute == 0) break;

    }
    if (amountLeftToDistribute != 0)
      throw new IllegalArgumentException("No space for "+amount+" of "+denominationValue+" bills");

    addBillsByDistribution(denomination, distribution);
  }

  public void getMoney(int value) throws Exception {
    Integer[] denomArray = denominationHelper.getDenominationValuesDescending();
    if (value > countValue()){
      throw new Exception("No enough money!");
    } else if (value < 0){
      throw new IllegalArgumentException("Unable to get negative sum");
    }
    int valueLeftToGet = value;
    List<Cell> currentDenominationCellList;
    Map<Integer, List<Integer>> getBillsDistribution= new HashMap<>();
    int requiredAmountOfNominal, amountToGet;
    for(int denomValue:denomArray){
      currentDenominationCellList = cells.get(denominationHelper.getByValue(denomValue));
      if(currentDenominationCellList == null) {continue;}

      requiredAmountOfNominal = valueLeftToGet / denomValue;
      List<Integer> currentDistributionList = new ArrayList<>();
      getBillsDistribution.put(denomValue, currentDistributionList);
      for(Cell cell:currentDenominationCellList){
        amountToGet = Math.min(requiredAmountOfNominal, cell.getCurrentAmount());
        valueLeftToGet -= amountToGet * denomValue;
        requiredAmountOfNominal -= amountToGet;
        currentDistributionList.add(amountToGet);
        if(requiredAmountOfNominal == 0){break;}
      }
      if(valueLeftToGet == 0){break;}
    }

    if(valueLeftToGet == 0){
      getBillsByDistributionMap(getBillsDistribution);
    } else{
      throw new Exception("Required sum can't be given - problem with denomination");
    }
  }

  private void getBillsByDistributionMap(Map<Integer, List<Integer>> getBillsDistribution) throws CellException {
    for(Map.Entry<Integer, List<Integer>> entry:getBillsDistribution.entrySet()) {
      getBillsByDistribution(denominationHelper.getByValue(entry.getKey()), entry.getValue());
    }
  }

  private void getBillsByDistribution(Denomination denomination, List<Integer> distribution) throws CellException {
    for(int i = 0; i < distribution.size(); i++){
      cells.get(denomination).get(i).removeBills(distribution.get(i));
      System.out.println("Removed "+distribution.get(i)+" bills of nominal "+denomination);
    }
  }

  private void addBillsByDistribution(Denomination denomination, List<Integer> distribution) throws CellException {
    for(int i = 0; i < distribution.size(); i++){
      cells.get(denomination).get(i).addBills(distribution.get(i));
    }

  }

  public String getStatus(){
    StringBuilder sb = new StringBuilder();
    sb.append("---ATM status---");
    for (List<Cell> cellList:cells.values()){
      for (Cell cell:cellList){
        sb.append("\n"+cell.getStatus());
      }
    }
    sb.append("\nTotal value in ATM: "+countValue());
    return sb.toString();
  }

  private void addCell(Denomination denom, int capacity, int amount ){
    List<Cell> listForCell;
    if (this.cells.containsKey(denom))
      listForCell = this.cells.get(denom);
    else{
      listForCell =  new ArrayList<>();
      this.cells.put(denom, listForCell);
    }
    for(int  i = 0; i < amount; i++){
      listForCell.add(new Cell(denom, capacity));
    }
  }
}
