package br.org.otus.commons;

import br.org.otus.model.User;
import br.org.otus.user.management.ManagementUserService;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FindByTokenServiceBean.class})
public class FindByTokenServiceBeanTest {

  private static final String USER_MODE = "user";
  private static final String PARTICIPANT_MODE = "participant";
  private static final String INVALID_MODE = "x";
  private static final String EMAIL = "user@otus";
  private static final String TOKEN = "abc";


  @InjectMocks
  private FindByTokenServiceBean findByTokenServiceBean = PowerMockito.spy(new FindByTokenServiceBean());
  @Mock
  private ParticipantService participantService;
  @Mock
  private ManagementUserService managementUserService;

  private User user = new User();
  private Participant participant = new Participant(null);


  @Before
  public void setUp() throws Exception {
    when(managementUserService.fetchByEmail(EMAIL)).thenReturn(user);
    when(participantService.getByEmail(EMAIL)).thenReturn(participant);
  }

  @Test
  public void findUserByToken_method_should_return_User() throws Exception {
    doReturn(USER_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    doReturn(EMAIL).when(findByTokenServiceBean, "getTokenEmail", TOKEN);
    assertEquals(user, findByTokenServiceBean.findUserByToken(TOKEN));
  }

  @Test(expected = ValidationException.class)
  public void findUserByToken_method_throw_ValidationException() throws Exception {
    doReturn(PARTICIPANT_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    findByTokenServiceBean.findUserByToken(TOKEN);
  }


  @Test
  public void findParticipantByToken_method_should_return_Participant() throws Exception {
    doReturn(PARTICIPANT_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    doReturn(EMAIL).when(findByTokenServiceBean, "getTokenEmail", TOKEN);
    assertEquals(participant, findByTokenServiceBean.findParticipantByToken(TOKEN));
  }

  @Test(expected = ValidationException.class)
  public void findParticipantByToken_method_throw_ValidationException() throws Exception {
    doReturn(USER_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    findByTokenServiceBean.findParticipantByToken(TOKEN);
  }

  @Test
  public void findPersonByToken_method_should_return_User() throws Exception {
    doReturn(USER_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    doReturn(EMAIL).when(findByTokenServiceBean, "getTokenEmail", TOKEN);
    assertEquals(user, findByTokenServiceBean.findPersonByToken(TOKEN));
  }

  @Test
  public void findPersonByToken_method_should_return_Participant() throws Exception {
    doReturn(PARTICIPANT_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    doReturn(EMAIL).when(findByTokenServiceBean, "getTokenEmail", TOKEN);
    assertEquals(participant, findByTokenServiceBean.findPersonByToken(TOKEN));
  }

  @Test(expected = ValidationException.class)
  public void findPersonByToken_method_throw_ValidationException() throws Exception {
    doReturn(INVALID_MODE).when(findByTokenServiceBean, "getTokenMode", TOKEN);
    findByTokenServiceBean.findPersonByToken(TOKEN);
  }

}
