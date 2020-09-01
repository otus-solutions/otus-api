package br.org.otus.security.dtos;

import com.nimbusds.jwt.JWTClaimsSet;
import org.ccem.otus.exceptions.Dto;

public class ParticipantTempTokenRequestDto implements Dto, JWTClaimSetBuilder {

  private static final String MODE = "sharing";

  private String mode;
  private Long recruitmentNumber;
  private String activityId;
  private String token;

  public ParticipantTempTokenRequestDto(Long recruitmentNumber, String activityId) {
    this.mode = MODE;
    this.recruitmentNumber = recruitmentNumber;
    this.activityId = activityId;
  }

  @Override
  public Boolean isValid() {
    return recruitmentNumber!=null && activityId!=null;
  }

  @Override
  public JWTClaimsSet buildClaimSet() {
    JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
    builder.claim("mode", MODE);
    builder.claim("recruitmentNumber", recruitmentNumber.toString());
    builder.claim("activityId", activityId);
    return builder.build();
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
