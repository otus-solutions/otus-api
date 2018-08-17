package br.org.otus.security.services;

import br.org.otus.security.PasswordResetControlDao;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;

public class PasswordResetContextServiceBean {
  @Inject
  private PasswordResetControlDao passwordResetControlDao;

  public void registerToken(PasswordResetRequestDto requestData) {
    passwordResetControlDao.registerToken(requestData.getToken(), requestData.getEmail());
  }

  public void removeRequests(String email) {
    passwordResetControlDao.removeAllRegisters(email);
  }

  public void removeToken(String token) {
    passwordResetControlDao.removeRegister(token);
  }

  public Boolean hasToken(String token) {
    return passwordResetControlDao.tokenExists(token);
  }

  public String getRequestEmail (String token) throws DataNotFoundException {
    return passwordResetControlDao.getRequestEmail(token);
  }
}
