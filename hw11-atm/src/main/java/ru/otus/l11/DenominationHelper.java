package ru.otus.l11;

import java.util.Map;
import java.util.TreeMap;

public class DenominationHelper {
  private final Map<Integer, Denomination> denomHelper = new TreeMap<>((a,b) -> b-a);

  public DenominationHelper() {
    for(Denomination denomination:Denomination.values()){
      denomHelper.put(denomination.getValue(),denomination);
    }
  }

  public Denomination getByValue(int denomValue) throws IllegalArgumentException {
    if(denomHelper.containsKey(denomValue))
      return denomHelper.get(denomValue);
    throw new IllegalArgumentException("No denomination: "+denomValue);
  }
  public Integer[] getDenominationValuesDescending(){
    return denomHelper.keySet().toArray(new Integer[0]);
  }
}
