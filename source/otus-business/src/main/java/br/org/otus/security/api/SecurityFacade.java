package br.org.otus.security.api;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.security.dtos.*;
import br.org.otus.security.services.SecurityService;
import com.mongodb.MongoException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;

import javax.inject.Inject;

public class SecurityFacade {

  @Inject
  private SecurityService securityService;

  public UserSecurityAuthorizationDto userAuthentication(AuthenticationDto authenticationDto, String requestAddress) {
    try {
      authenticationDto.setRequestAddress(requestAddress);
      return securityService.authenticate(authenticationDto);
    } catch (AuthenticationException | TokenException e) {
      throw new HttpResponseException(ResponseBuild.Security.Authorization.build());
    }
  }

  public String projectAuthentication(ProjectAuthenticationDto projectAuthenticationDto, String requestAddress) {
    try {
      projectAuthenticationDto.setRequestAddress(requestAddress);
      return securityService.projectAuthenticate(projectAuthenticationDto);

    } catch (AuthenticationException | TokenException e) {
      throw new HttpResponseException(ResponseBuild.Security.Authorization.build());
    }
  }

  public void invalidate(String token) {
    securityService.invalidate(token);
  }


  public void requestPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
    try {
      securityService.getPasswordResetToken(passwordResetRequestDto);
    } catch (TokenException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void requestParticipantPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
    try {
      securityService.getParticipantPasswordResetToken(passwordResetRequestDto);
    } catch (TokenException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void validatePasswordResetRequest(String token) {
    try {
      securityService.validatePasswordReset(token);
    } catch (TokenException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build("Invalid token"));
    }
  }

  public void removePasswordResetRequests(String email) {
    securityService.removePasswordResetRequests(email);
  }

  public String getRequestEmail(String token) {
    try {
      return securityService.getRequestEmail(token);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public ParticipantSecurityAuthorizationDto participantAuthentication(AuthenticationDto authenticationDto) {
    try {
      return securityService.participantAuthenticate(authenticationDto);
    } catch (AuthenticationException | TokenException e) {
      throw new HttpResponseException(ResponseBuild.Security.Authorization.build());
    }
  }

  public void validateToken(String token) {
    try {
      securityService.validateToken(token);
    } catch (AuthenticationException | TokenException e) {
      throw new HttpResponseException(ResponseBuild.Security.Authorization.build());
    }
  }

  public void invalidateParticipantAuthentication(String email, String token) {
    securityService.invalidateParticipantAuthenticate(email, token);
  }
}
