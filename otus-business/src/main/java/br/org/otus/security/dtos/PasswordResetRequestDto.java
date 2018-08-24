package br.org.otus.security.dtos;

import com.google.gson.GsonBuilder;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;

public class PasswordResetRequestDto implements Dto, JWTClaimSetBuilder {
  private static final String MODE = "password-reset";

  private String token;
  private String userEmail;
  private String redirectUrl;

  public PasswordResetRequestDto() {
  }

  @Override
  public Boolean isValid() {
    return (userEmail != null && !userEmail.isEmpty());
  }

  @Override
  public JWTClaimsSet buildClaimSet() {
    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
    builder.issuer(userEmail);
    builder.claim("mode", MODE);
    return builder.build();
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getEmail() {
    return userEmail;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public static PasswordResetRequestDto deserialize(String passwordRequestJson) {
    PasswordResetRequestDto examUploadDTO = PasswordResetRequestDto.getGsonBuilder()
      .create()
      .fromJson(passwordRequestJson, PasswordResetRequestDto.class);
    return examUploadDTO;
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    return builder;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}
