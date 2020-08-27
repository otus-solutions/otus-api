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

public class FindByTokenServiceBean implements FindByTokenService {

  private static final String USER_MODE = "user";
  private static final String PARTICIPANT_MODE = "participant";
  private static final String INVALID_MODE_MESSAGE = "Invalid Mode";

  @Inject
  private ParticipantService participantService;

  @Inject
  private ManagementUserService managementUserService;


  public User findUserByToken(String token) throws DataNotFoundException, ValidationException, ParseException {
    if (!getTokenMode(token).equals(USER_MODE)) {
      throw new ValidationException(INVALID_MODE_MESSAGE);
    }
    return managementUserService.fetchByEmail(getTokenEmail(token));
  }

  public Participant findParticipantByToken(String token) throws DataNotFoundException, ValidationException, ParseException {
    if (!getTokenMode(token).equals(PARTICIPANT_MODE)) {
      throw new ValidationException(INVALID_MODE_MESSAGE);
    }
    return participantService.getByEmail(getTokenEmail(token));
  }

  public Object findPersonByToken(String token) throws ParseException, DataNotFoundException, ValidationException {
    switch (getTokenMode(token)){
      case USER_MODE:
        return managementUserService.fetchByEmail(getTokenEmail(token));
      case PARTICIPANT_MODE:
        return participantService.getByEmail(getTokenEmail(token));
      default:
        throw new ValidationException(INVALID_MODE_MESSAGE);
    }
  }

  private String getTokenEmail(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    return signedJWT.getJWTClaimsSet().getClaim("iss").toString();
  }

  private String getTokenMode(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    return signedJWT.getJWTClaimsSet().getClaim("mode").toString();
  }

}
