package br.org.otus.participant.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.org.otus.security.services.SecurityService;
import br.org.otus.user.dto.PasswordResetDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ParticipantFacade.class)
public class ParticipantFacadeTest {
  private static long RN = 1063154;
  @InjectMocks
  private ParticipantFacade participantFacade;
  @Mock
  private ParticipantService participantService;
  @Mock
  private SecurityService securityService;
  @Mock
  private Participant participant;
  @Mock
  private FieldCenter fieldCenter;
  @Mock
  private java.util.List<Participant> partipantList;
  @Mock
  private DataNotFoundException e;
  @Mock
  private PasswordResetDto passwordResetDto = new PasswordResetDto();

  @Test
  public void method_getByRecruitmentNumber_should_return_participant() throws DataNotFoundException {
    when(participantService.getByRecruitmentNumber(RN)).thenReturn(participant);
    assertTrue(participantFacade.getByRecruitmentNumber(RN) instanceof Participant);
  }

  @Test
  public void method_list_should_return_instanceOf_ParticipantList() {
    when(participantService.list(fieldCenter)).thenReturn(partipantList);
    assertTrue(participantFacade.list(fieldCenter) instanceof java.util.List);
  }

  @Test
  public void method_registerPassword_should_register_participant_password() throws DataNotFoundException, EncryptedException {
    when(securityService.getRequestEmail("test")).thenReturn("test");
    when(passwordResetDto.getEmail()).thenReturn("test");
    when(passwordResetDto.getPassword()).thenReturn("test");
    when(passwordResetDto.getToken()).thenReturn("test");
    participantFacade.registerPassword(passwordResetDto);
    verify(passwordResetDto, Mockito.times(1)).encrypt();
    verify(securityService, Mockito.times(1)).getRequestEmail("test");
    verify(participantService, Mockito.times(1)).registerPassword("test", "test");
    verify(securityService, Mockito.times(1)).removePasswordResetRequests("test");
  }

  @Test(expected = HttpResponseException.class)
  public void method_registerPassword_should_throw_HttpResponseException_on_DataNotFoundException_from_getRequestEmail() throws DataNotFoundException, EncryptedException {
    when(passwordResetDto.getToken()).thenReturn("test");
    when(securityService.getRequestEmail("test")).thenThrow(new DataNotFoundException());
    participantFacade.registerPassword(passwordResetDto);
  }

}
