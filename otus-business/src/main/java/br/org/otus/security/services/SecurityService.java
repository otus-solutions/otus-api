package br.org.otus.security.services;

import br.org.otus.security.dtos.AuthenticationData;
import br.org.otus.security.dtos.UserSecurityAuthorizationDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.AuthenticationException;
import org.ccem.otus.exceptions.webservice.security.TokenException;

public interface SecurityService {

	UserSecurityAuthorizationDto authenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException;

	String getPasswordResetToken(AuthenticationData authenticationData) throws TokenException, AuthenticationException, DataNotFoundException;

	void invalidate(String token);

	String projectAuthenticate(AuthenticationData authenticationData) throws TokenException, AuthenticationException;
}