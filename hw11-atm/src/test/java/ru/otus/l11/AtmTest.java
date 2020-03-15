package ru.otus.l11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtmTest {
//  private static final int CELL_DENOMINATION_VALUE = CELL_DENOMINATION.getValue();
//  private static final int CELL_CAPACITY = 50;
//  private static final int CELL_START_AMOUNT = 20;
//  private static final int CELL_ADD_AMOUNT = 5;
//  private static final int CELL_REMOVE_AMOUNT = 6;
  Atm atm;

  @BeforeEach
  void setUp() throws IllegalAccessException {
    atm = new Atm(new String[]{"3-10-100", "2-100-50"});
  }

  @Test
  void countValue() throws Exception {
    assertEquals(atm.countValue(), 0);
  }

  @Test
  void addBills() throws Exception {
    atm.addBills(15,100);
    assertEquals(atm.countValue(), 15*100);
    atm.addBills(3,50);
    assertEquals(atm.countValue(), 15*100 + 3*50);
  }

  @Test
  void getMoney() throws Exception {
    atm.addBills(15,100);
    atm.addBills(3,50);
    atm.getMoney(200);
    assertEquals(atm.countValue(), 15*100 + 3*50 - 200);
    atm.getMoney(15*100 + 3*50 - 200);
    assertEquals(atm.countValue(), 0);
    assertThrows(Exception.class, () -> atm.getMoney(1));
  }
}