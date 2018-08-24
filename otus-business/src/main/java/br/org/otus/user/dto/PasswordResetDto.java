package br.org.otus.user.dto;

import br.org.otus.security.EncryptorResources;
import br.org.otus.security.dtos.JWTClaimSetBuilder;
import com.google.gson.GsonBuilder;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class PasswordResetDto implements Dto {

  private String token;
  private String password;
  private String email;

  public PasswordResetDto() {
  }

  @Override
  public Boolean isValid() {
    return ((password != null && !password.isEmpty()) && (token != null && !token.isEmpty()));
  }

  @Override
  public void encrypt() throws EncryptedException {
    this.password = EncryptorResources.encryptIrreversible(password);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public static PasswordResetDto deserialize(String passwordResetJson) {
    PasswordResetDto examUploadDTO = PasswordResetDto.getGsonBuilder()
      .create()
      .fromJson(passwordResetJson, PasswordResetDto.class);
    return examUploadDTO;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PasswordResetDto that = (PasswordResetDto) o;

    if (token != null ? !token.equals(that.token) : that.token != null) return false;
    if (password != null ? !password.equals(that.password) : that.password != null) return false;
    return email != null ? email.equals(that.email) : that.email == null;
  }

  @Override
  public int hashCode() {
    int result = token != null ? token.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  public String getPassword() {
    return this.password;
  }
}
