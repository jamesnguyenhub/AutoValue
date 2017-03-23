package com.tuyenmonkey.sample.entity;

import com.tuyenmonkey.autovalue.annotation.AutoValue;

/**
 * Created by Tuyen Monkey on 3/23/17.
 */

@AutoValue
public class User {
  private String username;
  private String password;
  private String email;

  public User() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
