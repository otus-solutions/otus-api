package br.org.otus.security.services;

import br.org.otus.model.User;
import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.*;
import br.org.otus.system.SystemConfig;
import br.org.otus.system.SystemConfigDaoBean;
import br.org.otus.persistence.UserDao;
import br.org.tutty.Equalizer;
import com.nimbusds.jwt.SignedJWT;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.ExpiredDataException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;
import org.ccem.otus.model.survey.activity.sharing.ActivitySharing;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.ActivitySharingDao;
import org.ccem.otus.utils.DateUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.text.ParseException;

@Stateless
public class SecurityServiceBean implements SecurityService {

  @Inject
  private UserDao userDao;

  @Inject
  private ParticipantDao participantDao;

  @Inject
  private ActivitySharingDao activitySharingDao;

  @Inject
  private SystemConfigDaoBean systemConfigDao;

  @Inject
  private SecurityContextService securityContextService;

  @Inject
  private PasswordResetContextService passwordResetContextService;

  @Override
  public UserSecurityAuthorizationDto authenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException {
    try {
      User user = userDao.fetchByEmail(authenticationData.getUserEmail());

      if (!user.getPassword().equals(authenticationData.getKey()) || !user.isEnable()) {
        throw new AuthenticationException();
      }

      UserSecurityAuthorizationDto userSecurityAuthorizationDto = new UserSecurityAuthorizationDto();
      Equalizer.equalize(user, userSecurityAuthorizationDto);

      if (user.getFieldCenter() != null) {
        userSecurityAuthorizationDto.getFieldCenter().acronym = user.getFieldCenter().getAcronym();
      }

      String token = initializeToken(authenticationData);
      userSecurityAuthorizationDto.setToken(token);
      return userSecurityAuthorizationDto;

    } catch (DataNotFoundException e) {
      throw new AuthenticationException();
    }
  }

  @Override
  public void validateToken(String token) throws TokenException {
    try {
      Participant participant = participantDao.fetchByToken(token);
      if (participant.getEmail().isEmpty()) {
        throw new TokenException();
      }
    } catch (DataNotFoundException e) {
      throw new TokenException(e);
    }
  }

  @Override
  public void validateActivitySharingToken(String token, String activityId) throws TokenException, ExpiredDataException {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      String payload = signedJWT.getPayload().toString();
      ParticipantTempTokenRequestDto dto = ParticipantTempTokenRequestDto.deserialize(payload);

      ActivitySharing activitySharing = activitySharingDao.getSharedURL(new ObjectId(dto.getActivityId()));
      if(activitySharing == null){
        throw new TokenException();
      }

      if (activityId != null) {
          if (ObjectId.isValid(activityId)) {
              if (!activityId.equals(activitySharing.getActivityId().toHexString())) {
                    throw new TokenException();
              }
          }
      }

      if(DateUtil.before(activitySharing.getExpirationDate(), DateUtil.nowToISODate())){
        throw new ExpiredDataException("Expired token");
      }
    }
    catch(ExpiredDataException e){
      throw e;
    }
    catch (ParseException | DataNotFoundException e) {
      throw new TokenException(e);
    }
  }

  @Override
  public ParticipantSecurityAuthorizationDto participantAuthenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException {
    try {
      Participant participant = participantDao.fetchByEmail(authenticationData.getUserEmail());

      if (!participant.getPassword().equals(authenticationData.getKey())) {
        throw new AuthenticationException();
      }

      ParticipantSecurityAuthorizationDto participantSecurityAuthorizationDto = new ParticipantSecurityAuthorizationDto();
      Equalizer.equalize(participant, participantSecurityAuthorizationDto);
      byte[] secretKey = securityContextService.generateSecretKey();
      String token = securityContextService.generateToken(authenticationData, secretKey);
      participantDao.addAuthToken(authenticationData.getUserEmail(), token);
      participantSecurityAuthorizationDto.setToken(token);
      return participantSecurityAuthorizationDto;

    } catch (DataNotFoundException e) {
      throw new AuthenticationException();
    }
  }

  @Override
  public void invalidateParticipantAuthenticate(String email, String token) {
    participantDao.removeAuthToken(email, token);
  }

  @Override
  public String projectAuthenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException {
    try {
      SystemConfig systemConfig = systemConfigDao.fetchSystemConfig();
      String password = authenticationData.getKey();

      if (!authenticationData.isValid() || !systemConfig.getProjectToken().equals(password)) {
        throw new AuthenticationException();
      }
      return initializeToken(authenticationData);

    } catch (NoResultException e) {
      throw new AuthenticationException(e);
    }
  }

  @Override
  public void invalidate(String token) {
    securityContextService.removeToken(token);
  }

  @Override
  public String getPasswordResetToken(PasswordResetRequestDto requestData) throws TokenException, DataNotFoundException {
    if (!userDao.exists(requestData.getEmail())) {
      throw new DataNotFoundException(new Throwable("User with email: {" + requestData.getEmail() + "} not found."));
    }

    String token = buildToken(requestData);
    requestData.setToken(token);
    passwordResetContextService.registerToken(requestData);
    return token;
  }

  public void registerParticipantPasswordResetToken(PasswordResetRequestDto requestData) throws TokenException, DataNotFoundException {

    try {
      Participant participant = participantDao.fetchByEmail(requestData.getEmail());
      if (participant instanceof Participant) {
        String token = buildToken(requestData);
        requestData.setToken(token);
        passwordResetContextService.registerToken(requestData);
      }
    } catch (DataNotFoundException e) {
      throw new DataNotFoundException(new Throwable("Participant with email: {" + requestData.getEmail() + "} not found."));
    }
  }

  @Override
  public String getRequestEmail(String token) throws DataNotFoundException {
    return passwordResetContextService.getRequestEmail(token);
  }

  @Override
  public void validatePasswordReset(String token) throws TokenException {
    if (!passwordResetContextService.hasToken(token)) {
      throw new TokenException();
    }
  }

  @Override
  public void removePasswordResetRequests(String email) {
    passwordResetContextService.removeRequests(email);
  }

  private String buildToken(JWTClaimSetBuilder tokenData) throws TokenException {
    byte[] secretKey = securityContextService.generateSecretKey();
    return securityContextService.generateToken(tokenData, secretKey);
  }


  private String initializeToken(AuthenticationData authenticationData) throws TokenException {
    byte[] secretKey = securityContextService.generateSecretKey();
    String jwtSignedAndSerialized = securityContextService.generateToken(authenticationData, secretKey);

    SessionIdentifier sessionIdentifier = new SessionIdentifier(jwtSignedAndSerialized, secretKey, authenticationData);
    securityContextService.addSession(sessionIdentifier);

    return jwtSignedAndSerialized;
  }

}
