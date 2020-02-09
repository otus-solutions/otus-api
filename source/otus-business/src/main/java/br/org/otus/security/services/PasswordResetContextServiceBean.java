package br.org.otus.security.services;

import br.org.otus.security.PasswordResetControlDao;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PasswordResetContextServiceBean implements PasswordResetContextService {

  @Inject
  private PasswordResetControlDao passwordResetControlDao;

  @Override
  public void registerToken(PasswordResetRequestDto requestData) {
    passwordResetControlDao.registerToken(requestData.getToken(), requestData.getEmail());
  }

  @Override
  public void removeRequests(String email) {
    passwordResetControlDao.removeAllRegisters(email);
  }

  @Override
  public void removeToken(String token) {
    passwordResetControlDao.removeRegister(token);
  }

  @Override
  public Boolean hasToken(String token) {
    return passwordResetControlDao.tokenExists(token);
  }

  @Override
  public String getRequestEmail(String token) throws DataNotFoundException {
    return passwordResetControlDao.getRequestEmail(token);
  }
}
