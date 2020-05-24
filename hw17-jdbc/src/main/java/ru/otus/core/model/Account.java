package ru.otus.core.model;

import ru.otus.myAnnotations.Field;
import ru.otus.myAnnotations.Id;

/**][
 *
 *
 * @author comradv
 * created on 11.05.20.
 */
public class Account {
  @Id
  private final long no;

  @Field(type="varchar", size = 255)
  private String type;

  @Field(type="int", size = 3)
  private int rest;

  public Account(long no, String type, int rest) {
    this.no = no;
    this.type = type;
    this.rest = rest;
  }

  private Account(long no){
    this.no = no;
  }

  public int getRest() {
    return rest;
  }

  public void setRest(int rest) {
    this.rest = rest;
  }

  public long getNo() {
    return no;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Account{" +
        "no=" + no +
        ", type='" + type + '\'' +
            ", rest=" + rest +
        '}';
  }
}
