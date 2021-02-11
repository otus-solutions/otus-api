package br.org.otus;

import br.org.otus.model.User;
import br.org.otus.user.api.UserFacade;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public abstract class UserAuthenticationResource extends AuthenticationResource {

  @Inject
  private UserFacade userFacade;

  protected User getUser(HttpServletRequest request) {
    String userEmail = getUserEmailToken(request);
    return userFacade.fetchByEmail(userEmail);
  }
}
