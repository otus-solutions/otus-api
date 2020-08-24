package br.org.otus.commons;

import br.org.otus.model.User;
import br.org.otus.user.management.ManagementUserService;
import com.nimbusds.jwt.SignedJWT;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;

import javax.inject.Inject;
import java.text.ParseException;

public class FindByTokenService {

  @Inject
  private ParticipantService participantService;

  @Inject
  private ManagementUserService managementUserService;

  public String findUserIdByToken(String token) throws DataNotFoundException, ValidationException, ParseException {
    final String mode = getTokenMode(token);
    final String email = getTokenEmail(token);

    if (!mode.equals("user")) {
      throw new ValidationException("Invalid Mode");
    }

    User user = managementUserService.fetchByEmail(email);
    return String.valueOf(user.get_id());
  }

  public String findParticipantIdByToken(String token) throws DataNotFoundException, ValidationException, ParseException {
    final String mode = getTokenMode(token);
    final String email = getTokenEmail(token);

    if (!mode.equals("participant")) {
      throw new ValidationException("Invalid Mode");
    }

    Participant participant = participantService.getByEmail(email);
    return String.valueOf(participant.getId());
  }


  private String getTokenEmail(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    String email = signedJWT.getJWTClaimsSet().getClaim("iss").toString();
    return email;
  }

  private String getTokenMode(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    String mode = signedJWT.getJWTClaimsSet().getClaim("mode").toString();
    return mode;
  }

}
