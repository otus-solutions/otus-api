package br.org.otus.security.dtos;

import br.org.otus.security.EncryptorResources;
import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class PasswordResetDto implements Dto, JWTClaimSetBuilder {
  private static final String MODE = "password-reset";

  public String userEmail;
  public String requestAddress;

  public PasswordResetDto(String userEmail) {
    this.userEmail = userEmail;
  }

  @Override
  public Boolean isValid() {
    return (!userEmail.isEmpty() && userEmail != null) && (!password.isEmpty() && password != null) && (requestAddress != null);
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
