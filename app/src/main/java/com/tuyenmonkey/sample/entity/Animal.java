package com.tuyenmonkey.sample.entity;

import com.tuyenmonkey.autovalue.annotation.AutoValue;

/**
 * Created by Tuyen Monkey on 3/23/17.
 */

public class Animal {
  private String name;
  private String type;

  public Animal() {
  }

  public String getName() {
    return name;
  }

  @AutoValue
  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  @AutoValue
  public void setType(String type) {
    this.type = type;
  }
}
