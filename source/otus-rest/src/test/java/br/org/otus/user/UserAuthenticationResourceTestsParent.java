package br.org.otus.user;

import br.org.otus.AuthenticationResourceTestsParent;
import br.org.otus.model.User;
import br.org.otus.user.api.UserFacade;
import org.mockito.Mock;

import static org.powermock.api.mockito.PowerMockito.doReturn;

public abstract class UserAuthenticationResourceTestsParent extends AuthenticationResourceTestsParent {

  @Mock
  protected UserFacade userFacade;
  @Mock
  protected User user;

  protected void mockContextAndUser(){
    /*
      Must add in child class: @PrepareForTest({AuthorizationHeaderReader.class})
     */
    mockContextToSetUserEmail();
    doReturn(user).when(userFacade).fetchByEmail(USER_EMAIL);
  }
}
