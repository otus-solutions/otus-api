package br.org.otus.security.services;

import br.org.otus.security.PasswordResetControlDao;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.owail.sender.email.Email;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;

import java.security.SecureRandom;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetContextServiceBeanTest {
  private static final String EMAIL = "otus@otus.com";
  private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtb2RlIjoidXNlciIsImlzcyI6ImRpb2dvLnJvc2FzLmZlcnJlaXJhQGdtYWlsLmNvbSJ9.I5Ysne1C79cO5B_5hIQK9iBSnQ6M8msuyVHD4kdoFSo";

  @InjectMocks
  private PasswordResetContextServiceBean passwordResetContextServiceBean;
  @Mock
  private PasswordResetControlDao passwordResetControlDao;
  @Mock
  private PasswordResetRequestDto passwordResetRequestDto;

  @Test
  public void method_registerToken_should_call_passwordResetControlDao_registerToken() {
    when(passwordResetRequestDto.getEmail()).thenReturn(EMAIL);
    when(passwordResetRequestDto.getToken()).thenReturn(TOKEN);
    passwordResetContextServiceBean.registerToken(passwordResetRequestDto);
    verify(passwordResetControlDao, times(1)).registerToken(TOKEN, EMAIL);
  }

  @Test
  public void method_removeRequests_should_call_passwordResetControlDao_removeAllRegisters() {
    passwordResetContextServiceBean.removeRequests(EMAIL);
    verify(passwordResetControlDao, times(1)).removeAllRegisters(EMAIL);
  }

  @Test
  public void method_removeToken_should_call_passwordResetControlDao_removeRegister() {
    passwordResetContextServiceBean.removeToken(TOKEN);
    verify(passwordResetControlDao, times(1)).removeRegister(TOKEN);
  }

  @Test
  public void method_hasToken_should_call_passwordResetControlDao_tokenExists() {
    passwordResetContextServiceBean.hasToken(TOKEN);
    verify(passwordResetControlDao, times(1)).tokenExists(TOKEN);
  }

  @Test
  public void method_getRequestEmail_should_call_passwordResetControlDao_getRequestEmail() throws DataNotFoundException {
    passwordResetContextServiceBean.getRequestEmail(TOKEN);
    verify(passwordResetControlDao, times(1)).getRequestEmail(TOKEN);
  }

  @Test(expected = DataNotFoundException.class)
  public void method_getRequestEmail_should_throw_DataNotFoundException() throws DataNotFoundException {
    when(passwordResetControlDao.getRequestEmail(TOKEN)).thenThrow(new DataNotFoundException());
    passwordResetContextServiceBean.getRequestEmail(TOKEN);
  }
}
