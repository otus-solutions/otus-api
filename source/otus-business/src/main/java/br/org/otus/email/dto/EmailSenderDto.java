package br.org.otus.email.dto;

import br.org.otus.security.EncryptorResources;
import br.org.tutty.Equalization;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.Encripting;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class EmailSenderDto implements Dto, Encripting {

  @Equalization(name = "name")
  private String name;

  @Equalization(name = "email")
  private String email;

  @Equalization(name = "password")
  private String password;

  private String passwordConfirmation;

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  @Override
  public Boolean isValid() {
    return Boolean.TRUE;
  }

  @Override
  public void encrypt() throws EncryptedException {
    this.password = EncryptorResources.encryptReversible(password);
    this.passwordConfirmation = EncryptorResources.encryptReversible(passwordConfirmation);
  }

}
