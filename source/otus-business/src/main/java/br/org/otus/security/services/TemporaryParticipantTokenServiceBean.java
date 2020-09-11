package br.org.otus.security.services;

import br.org.otus.security.dtos.JWTClaimSetBuilder;
import br.org.otus.security.dtos.ParticipantTempTokenRequestDto;
import org.ccem.otus.exceptions.webservice.security.TokenException;

import javax.inject.Inject;

public class TemporaryParticipantTokenServiceBean implements TemporaryParticipantTokenService {

  @Inject
  private SecurityContextService securityContextService;

  @Override
  public String generateTempToken(ParticipantTempTokenRequestDto requestData) throws TokenException {
    String token = buildToken(requestData);
    requestData.setToken(token);
    return token;
  }


  private String buildToken(JWTClaimSetBuilder tokenData) throws TokenException {
    byte[] secretKey = securityContextService.generateSecretKey();
    return securityContextService.generateToken(tokenData, secretKey);
  }
}
