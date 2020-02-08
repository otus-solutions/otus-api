package org.ccem.otus.model.survey.activity;

public class User {

  private String name;
  private String surname;
  private String phone;
  private String email;

  public User() {}

  public User(String name, String email, String surname, String phone) {
    this.name = name;
    this.email = email;
    this.surname = surname;
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

}