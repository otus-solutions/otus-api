package br.org.otus.security.dtos;

import br.org.otus.security.EncryptorResources;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.enums.AuthenticationMode;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.Encripting;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class AuthenticationDto implements Dto, Encripting, AuthenticationData {
  private static final String MODE = AuthenticationMode.USER.getName();

  public String userEmail;
  public String password;
  public String requestAddress;
  public String mode;

  @Override
  public Boolean isValid() {
    return (userEmail != null && !userEmail.isEmpty()) && (password != null && !password.isEmpty()) && (requestAddress != null && !requestAddress.isEmpty());
  }

  public void setEmail(String email) {
    this.userEmail = email;
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
    String mode = this.mode != null ? this.mode : MODE;
    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
    builder.issuer(userEmail);
    builder.claim("mode", mode);
    return builder.build();
  }
}
