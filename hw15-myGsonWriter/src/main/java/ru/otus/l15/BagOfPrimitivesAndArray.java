package ru.otus.l15;

import java.util.Arrays;
import java.util.Objects;

public class BagOfPrimitivesAndArray {
  private final Integer value1;
  private final String value2;
  private final transient int value3;
  private int[] arr;

  BagOfPrimitivesAndArray(int value1, String value2, int value3, int[] arr) {
    this.value1 = value1;
    this.value2 = value2;
    this.value3 = value3;
    this.arr = Arrays.copyOf(arr,arr.length);
  }

  @Override
  public String toString() {
    return "BagOfPrimitives{" +
        "value1=" + value1 +
        ", value2='" + value2 + '\'' +
        ", value3(transient)=" + value3 +
        ", arr=" + Arrays.toString(arr) +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BagOfPrimitivesAndArray that = (BagOfPrimitivesAndArray) o;
    return value1 == that.value1 &&
        Objects.equals(value2, that.value2) &&
        Arrays.equals(arr, that.arr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value1, value2, value3, arr);
  }
}
