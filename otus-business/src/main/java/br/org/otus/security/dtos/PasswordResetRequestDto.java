package br.org.otus.security.dtos;

import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class PasswordResetRequestDto implements Dto, JWTClaimSetBuilder {
  private static final String MODE = "password-reset";

  public String userEmail;
  public String token;

  public PasswordResetRequestDto(String userEmail) {
    this.userEmail = userEmail;
  }

  @Override
  public Boolean isValid() {
    return (!userEmail.isEmpty() && userEmail != null);
  }

  @Override
  public void encrypt() throws EncryptedException {}

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

  public void setEmail(String email) {
    this.userEmail = email;
  }
}
