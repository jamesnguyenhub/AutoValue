package com.tuyenmonkey.sample.entity;

import com.tuyenmonkey.autovalue.annotation.AutoValue;

/**
 * Created by Tuyen Monkey on 3/23/17.
 */

@AutoValue
public class Animal {
  private String name;
  private String type;

  public Animal() {
  }

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
