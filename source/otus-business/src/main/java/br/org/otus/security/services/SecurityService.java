package br.org.otus.security.services;

import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.security.dtos.ParticipantSecurityAuthorizationDto;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;

public interface SecurityService {

  UserSecurityAuthorizationDto authenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException;

  void invalidate(String token);

  ParticipantSecurityAuthorizationDto participantAuthenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException;

  void validateToken(String token) throws TokenException, AuthenticationException;

  String projectAuthenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException;

  String getPasswordResetToken(PasswordResetRequestDto requestData) throws TokenException, DataNotFoundException;

  void getParticipantPasswordResetToken(PasswordResetRequestDto requestData) throws TokenException, DataNotFoundException;

  String getRequestEmail(String token) throws DataNotFoundException;

  void validatePasswordReset(String token) throws TokenException;

  void removePasswordResetRequests(String email);

  void invalidateParticipantAuthenticate(String email, String token);
}