package ru.otus.l11;

import java.util.*;

public class Atm {
  private final Map<Denomination, List<Cell>> cells = new HashMap<>();
  private final Map<Integer, Denomination> denomHelper = new HashMap<>();

  public Atm(String[] args) throws IllegalAccessException {

    String[] cellParameters;
    int[] cellParametersInt;
    int cellAmount, cellCapacity, denomValue;
    Denomination denom;
    for(Denomination denomination:Denomination.values()){
      denomHelper.put(denomination.getValue(),denomination);
    }
    for (String cellDescriptpion:args) {
      cellParameters = cellDescriptpion.split("-");
      if (cellParameters.length % 3 != 0){
        throw new IllegalArgumentException("Use numOfCella-capacity-denom arguments!");
      }
      cellParametersInt = Arrays.stream(cellParameters).mapToInt(Integer::parseInt).toArray();
      cellAmount = cellParametersInt[0];
      cellCapacity = cellParametersInt[1];
      denomValue = cellParametersInt[2];
      if(denomHelper.containsKey(denomValue))
        denom = denomHelper.get(denomValue);
      else
        throw new IllegalArgumentException("No denom "+denomValue);

      this.addCell(denom,cellCapacity,cellAmount);

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
    Denomination denomination = denomHelper.get(denominationValue);
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
      throw new IllegalArgumentException("No space for "+amount+" of "+denominationValue+" bills!");

    addBillsByDistribution(denomination, distribution);
  }
  public void getMoney(int value) throws Exception {
    Integer[] denomArray = denomHelper.keySet().toArray(new Integer[denomHelper.keySet().size()]);
    Arrays.sort(denomArray, (a,b) -> b-a);
    int valueLeftToGet = value;
    List<Cell> currentDenominationCellList;
    Map<Integer, List<Integer>> getBillsDistribution= new HashMap<>();
    int requiredAmountOfNominal, amountToGet;
    for(int denomValue:denomArray){
      currentDenominationCellList = cells.get(denomHelper.get(denomValue));
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
      getBillsByDistribution(getBillsDistribution);
    } else{
      throw new Exception("Required sum can't be given");
    }

  }

  private void getBillsByDistribution(Map<Integer, List<Integer>> getBillsDistribution) throws CellException {
    for(Map.Entry<Integer, List<Integer>> entry:getBillsDistribution.entrySet()){
      List<Cell> cellListOfDenomination = cells.get(denomHelper.get(entry.getKey()));
      for(int i = 0; i < entry.getValue().size(); i++){
        cellListOfDenomination.get(i).removeBills(entry.getValue().get(i));
        System.out.println("Removed "+entry.getValue().get(i)+" bills of nominal "+entry.getKey());
      }
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
