package ru.otus.l11;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

  private static final Denomination CELL_DENOMINATION = Denomination.RUB100;
  private static final int CELL_DENOMINATION_VALUE = CELL_DENOMINATION.getValue();
  private static final int CELL_CAPACITY = 50;
  private static final int CELL_START_AMOUNT = 20;
  private static final int CELL_ADD_AMOUNT = 5;
  private static final int CELL_REMOVE_AMOUNT = 6;
  Cell cell;

  @BeforeEach
  void setUp() throws CellException {
    cell = new Cell(CELL_DENOMINATION, CELL_CAPACITY);
    cell.addBills(CELL_START_AMOUNT);
  }

  @Test
  void getCurrentValue() {
    assertEquals(cell.getCurrentValue(), CELL_START_AMOUNT*CELL_DENOMINATION_VALUE);
  }

  @Test
  void getFreeSpace() {
    assertEquals(cell.getFreeSpace(), CELL_CAPACITY-CELL_START_AMOUNT);
  }

  @Test
  void addBills() throws CellException {
    cell.addBills(CELL_ADD_AMOUNT);
    assertEquals(cell.getCurrentAmount(), CELL_START_AMOUNT+CELL_ADD_AMOUNT);
  }

  @Test
  void addTooManyBills() throws CellException {
    assertThrows(CellException.class, () -> cell.addBills(CELL_CAPACITY));
  }

  @Test
  void getCurrentAmount() {
    assertEquals(cell.getCurrentAmount(), CELL_START_AMOUNT);
  }

  @Test
  void removeBills() throws CellException {
    cell.removeBills(CELL_REMOVE_AMOUNT);
    assertEquals(cell.getCurrentAmount(), CELL_START_AMOUNT-CELL_REMOVE_AMOUNT);
  }

  @Test
  void removeTooManyBills() throws CellException {
    assertThrows(CellException.class, () -> cell.removeBills(CELL_CAPACITY+1));
  }

}