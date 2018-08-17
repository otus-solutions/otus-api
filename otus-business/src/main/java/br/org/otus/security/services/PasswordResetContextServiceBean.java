package br.org.otus.security.services;

import br.org.otus.security.PasswordResetControlDao;
import br.org.otus.security.context.SessionIdentifier;

import javax.inject.Inject;
import java.util.Set;

//@ApplicationScoped
public class PasswordResetContextServiceBean {
  private Set<SessionIdentifier> sessions;

  @Inject
  PasswordResetControlDao passwordResetControlDao;

  public void addSession(String token) {
    passwordResetControlDao.addSession(token);
  }

  public void removeSession(String token) {
    String tokenWithoutPrefix = token.substring("Bearer".length()).trim();
    sessions.removeIf(session -> session.getToken().equals(tokenWithoutPrefix));
  }

  public SessionIdentifier getSession(String token) {
    return sessions.stream().filter(session -> session.getToken().equals(token)).findFirst().get();
  }


  public Boolean hasToken(String token) {
    return sessions.stream().anyMatch(session -> session.getToken().equals(token));
  }


  //TODO 16/08/18: to use this i will need pass SessionIndentifier
//  public Boolean verifySignature(String token) throws ParseException, JOSEException {
//    SignedJWT signedJWT = SignedJWT.parse(token);
//    SessionIdentifier session = getSession(token);
//    byte[] sharedSecret = session.getSecretKey();
//    JWSVerifier verifier = new MACVerifier(sharedSecret);
//    return signedJWT.verify(verifier);
//  }
}
