package br.org.otus.security.dtos;

import br.org.otus.security.EncryptorResources;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class PasswordResetDto implements Dto, AuthenticationData {
  private static final String MODE = "password-reset";  //TODO 15/08/18: review

  public String userEmail;
  public String password;
  public String requestAddress;

  public PasswordResetDto(String userEmail) {
    this.userEmail = userEmail;
  }

  @Override
  public Boolean isValid() {
    return (!userEmail.isEmpty() && userEmail != null) && (!password.isEmpty() && password != null) && (requestAddress != null);
  }

  @Override
  public String getUserEmail() {
    return userEmail;
  }

  @Override
  public String getKey() {
    return password;
  }

  @Override
  public String getMode() {
    return MODE;
  }

  @Override
  public String getRequestAddress() {
    return requestAddress;
  }

  @Override
  public void setRequestAddress(String requestAddress) {
    this.requestAddress = requestAddress;
  }

  @Override
  public void encrypt() throws EncryptedException {
    this.password = EncryptorResources.encryptIrreversible(password);
  }

  @Override
  public JWTClaimsSet buildClaimSet() {
    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
    builder.issuer(userEmail);
    builder.claim("mode", MODE);
    return builder.build();
  }

  public void setEmail(String email) {
    this.userEmail = email;
  }
}
