package br.org.otus.security.dtos;

import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;

public class ParticipantTempTokenRequestDto implements Dto, JWTClaimSetBuilder {

  private static final String MODE = "participant-temp-token";

  private Long recruitmentNumber;
  private String userId;
  private String token;

  public ParticipantTempTokenRequestDto(Long recruitmentNumber, String userId) {
    this.recruitmentNumber = recruitmentNumber;
    this.userId = userId;
  }

  @Override
  public Boolean isValid() {
    return null;
  }

  @Override
  public JWTClaimsSet buildClaimSet() {
    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
    builder.claim("mode", MODE);
    return builder.build();
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
