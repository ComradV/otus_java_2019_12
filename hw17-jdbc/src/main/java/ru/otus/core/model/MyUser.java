package ru.otus.core.model;

import ru.otus.myAnnotations.Field;
import ru.otus.myAnnotations.Id;

/**][
 *
 *
 * @author comradv
 * created on 11.05.20.
 */
public class MyUser {
  @Id
  private final long id;

  @Field(type="varchar", size = 255)
  private String name;

  @Field(type="int", size = 3)
  private int age;

  public MyUser(long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  private MyUser(long id){
    this.id = id;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
            ", age=" + age +
        '}';
  }
}
