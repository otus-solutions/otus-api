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

  public String getPassword() {
    return this.password;
  }
}
