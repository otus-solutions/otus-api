package br.org.otus.security.services;

import br.org.otus.security.context.SessionIdentifier;
import br.org.otus.security.dtos.AuthenticationData;
import org.ccem.otus.exceptions.webservice.security.TokenException;

public interface SecurityContextService {

	String generateToken(AuthenticationData authenticationData, byte[] secretKey) throws TokenException;

	byte[] generateSecretKey();

	void addSession(SessionIdentifier sessionIdentifier);

	void removeToken(String jwtSignedAndSerialized);

	void validateToken(String token) throws TokenException;

	SessionIdentifier getSession(String token);
}
