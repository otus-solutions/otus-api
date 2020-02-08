package br.org.otus.security.dtos;

import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public class ProjectAuthenticationDto implements AuthenticationData, Dto {
  private static final String MODE = "client";

  public String user;
  public String accessToken;
  public String requestAddress;

  @Override
  public Boolean isValid() {
    return (!user.isEmpty() && user != null) && (!accessToken.isEmpty() && accessToken != null) && (requestAddress != null);
  }

  @Override
  public String getUserEmail() {
    return user;
  }

  @Override
  public String getKey() {
    return accessToken;
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
  public JWTClaimsSet buildClaimSet() {
    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
    builder.issuer(user);
    builder.claim("mode", MODE);
    return builder.build();
  }
}
