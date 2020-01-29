package br.org.otus.extraction;

import br.org.otus.model.User;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@RunWith(PowerMockRunner.class)
public class ExtractionSecurityServiceTest {
  private static String TOKEN = UUID.randomUUID().toString();
  private static String IP = "192.168.0.1";
  private static final String EMAIL = "email@email";

  @InjectMocks
  private ExtractionSecurityService service;

  @Mock
  private ExtractionSecurityDaoBean extractionSecurityDaoBean;

  @Mock
  private User user;

  private ArrayList LIST = PowerMockito.spy(new ArrayList());

  @Test
  public void validateSecurityCredentials_method_should_return_true() throws DataNotFoundException {
    when(extractionSecurityDaoBean.validateSecurityCredentials(TOKEN)).thenReturn(user);
    assertEquals(service.validateSecurityCredentials(TOKEN, IP), true);
  }

  @Test
  public void validateSecurityCredentials_method_should_return_false() throws DataNotFoundException {
    when(user.getExtractionIps()).thenReturn(LIST);
    when(LIST.isEmpty()).thenReturn(false);
    when(user.getExtractionIps()).thenReturn(LIST);
    when(LIST.contains(IP)).thenReturn(false);
    when(extractionSecurityDaoBean.validateSecurityCredentials(TOKEN)).thenReturn(user);
    assertEquals(service.validateSecurityCredentials(TOKEN, IP), false);
  }

  @Test(expected = DataNotFoundException.class)
  public void validateSecurityCredentials_method_throw_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException(new Exception())).when(extractionSecurityDaoBean).validateSecurityCredentials(TOKEN);
    service.validateSecurityCredentials(TOKEN, IP);
  }

  @Test
  public void getExtractionToken_method_should_return_extractionToken() throws DataNotFoundException {
    when(extractionSecurityDaoBean.getExtractionToken(EMAIL)).thenReturn(TOKEN);
    assertEquals(service.getExtractionToken(EMAIL), TOKEN);
  }

  @Test(expected = DataNotFoundException.class)
  public void getExtractionToken_method_throw_DataNotFoundException() throws DataNotFoundException {
    doThrow(new DataNotFoundException(new Exception())).when(extractionSecurityDaoBean).getExtractionToken(EMAIL);
    service.getExtractionToken(EMAIL);
  }

}
