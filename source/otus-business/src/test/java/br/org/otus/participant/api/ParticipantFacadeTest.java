package br.org.otus.participant.api;

import br.org.otus.participant.management.ManagementParticipantService;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.security.services.SecurityService;
import br.org.otus.survey.activity.api.ActivityFacade;
import br.org.otus.user.dto.PasswordResetDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ParticipantFacade.class)
public class ParticipantFacadeTest {
  private static long RN = 1063154;
  private static final String EMAIL = "otus@gmail.com";
  private static final String ID = "58c83f502226685b94f8973a";

  @InjectMocks
  private ParticipantFacade participantFacade;
  @Mock
  private ParticipantService participantService;
  @Mock
  private SecurityFacade securityFacade;
  @Mock
  private ManagementParticipantService managementParticipantService;
  @Mock
  private SecurityService securityService;
  @Mock
  private Participant participant;
  @Mock
  private FieldCenter fieldCenter;
  @Mock
  private java.util.List<Participant> partipantList;
  @Mock
  private ActivityService activityService;
  @Mock
  private DataNotFoundException e;
  @Mock
  private PasswordResetDto passwordResetDto = new PasswordResetDto();
  private PasswordResetRequestDto passwordResetRequestDto = PowerMockito.spy(new PasswordResetRequestDto());
  private EmailNotificationException emailNotificationException =  PowerMockito.spy(new EmailNotificationException());
  private MalformedURLException malformedURLException =  PowerMockito.spy(new MalformedURLException());
  private DataNotFoundException dataNotFoundException = PowerMockito.spy(new DataNotFoundException());
  private ValidationException validationException =  PowerMockito.spy(new ValidationException());
  private ObjectId objectId = new ObjectId(ID);

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

  @Test
  public void requestPasswordReset_method_should_evoke_3_call_methods() throws EmailNotificationException {
    when(passwordResetRequestDto.getEmail()).thenReturn("otus@otus.com");
    participantFacade.requestPasswordReset(passwordResetRequestDto);
    verify(securityFacade, times(1)).removePasswordResetRequests(passwordResetRequestDto.getEmail());
    verify(securityFacade, times(1)).requestParticipantPasswordReset(passwordResetRequestDto);
    verify(managementParticipantService, times(1)).requestPasswordReset(passwordResetRequestDto);
  }

  @Test(expected = HttpResponseException.class)
  public void requestPasswordReset_method_should_catch_EmailNotificationException() throws Exception {
    Mockito.doThrow(emailNotificationException).when(managementParticipantService).requestPasswordReset(passwordResetRequestDto);
    participantFacade.requestPasswordReset(passwordResetRequestDto);
  }

  @Test
  public void requestPasswordResetLink_method_should_evoke_3_call_methods() throws MalformedURLException {
    when(passwordResetRequestDto.getEmail()).thenReturn("otus@otus.com");
    participantFacade.requestPasswordResetLink(passwordResetRequestDto);
    verify(securityFacade, times(1)).removePasswordResetRequests(passwordResetRequestDto.getEmail());
    verify(securityFacade, times(1)).requestParticipantPasswordReset(passwordResetRequestDto);
    verify(managementParticipantService, times(1)).requestPasswordResetLink(passwordResetRequestDto);
  }

  @Test(expected = HttpResponseException.class)
  public void requestPasswordResetLink_method_should_catch_EmailNotificationException() throws Exception {
    Mockito.doThrow(malformedURLException).when(managementParticipantService).requestPasswordResetLink(passwordResetRequestDto);
    participantFacade.requestPasswordResetLink(passwordResetRequestDto);
  }

  @Test
  public void updateEmail_method_should_evoke_call_methods() throws DataNotFoundException, AlreadyExistException {
    when(participantService.updateEmail(objectId,EMAIL)).thenReturn(true);
    when(participantService.getParticipant(objectId)).thenReturn(participant);
    assertEquals(true,  participantFacade.updateEmail(ID, EMAIL));
    verify(participantService, times(1)).updateEmail(objectId, EMAIL);
  }

  @Test(expected = HttpResponseException.class)
  public void updateEmail_method_should_catch_Exception() throws Exception {
    Mockito.doThrow(dataNotFoundException).when(participantService).updateEmail(objectId, EMAIL);
    participantFacade.updateEmail(ID,EMAIL);
  }

  @Test
  public void deleteEmail_method_should_evoke_call_methods() throws DataNotFoundException {
    when(participantService.deleteEmail(objectId)).thenReturn(true);
    when(participantService.getParticipant(objectId)).thenReturn(participant);
    assertEquals(true,  participantFacade.deleteEmail(ID));
    verify(participantService, times(1)).deleteEmail(objectId);
  }

  @Test(expected = HttpResponseException.class)
  public void deleteEmail_method_should_catch_Exception() throws Exception {
    Mockito.doThrow(dataNotFoundException).when(participantService).deleteEmail(objectId);
    participantFacade.deleteEmail(ID);
  }

  @Test
  public void getEmail_method_should_evoke_call_methods() throws ValidationException, DataNotFoundException {
    when(participantService.getEmail(ID)).thenReturn(EMAIL);
    assertEquals(EMAIL,  participantFacade.getEmail(ID));
    verify(participantService, times(1)).getEmail(ID);
  }

  @Test(expected = HttpResponseException.class)
  public void getEmail_method_should_catch_Exception() throws Exception {
    Mockito.doThrow(validationException).when(participantService).getEmail(ID);
    participantFacade.getEmail(ID);
  }

}
