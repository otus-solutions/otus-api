package br.org.otus.email;

import br.org.tutty.Equalization;

public class BasicEmailSender implements EmailSender {

  @Equalization(name = "name")
  private String name;
  @Equalization(name = "email")
  private String emailAddress;
  @Equalization(name = "password")
  private String password;

  public BasicEmailSender() {
  }

  public BasicEmailSender(String name, String emailAddress, String password) {
    super();
    this.name = name;
    this.emailAddress = emailAddress;
    this.password = password;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getEmail() {
    return emailAddress;
  }

  @Override
  public String getPassword() {
    return password;
  }
}
