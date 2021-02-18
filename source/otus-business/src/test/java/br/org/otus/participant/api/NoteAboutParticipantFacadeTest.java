package br.org.otus.participant.api;

import br.org.otus.LoggerTestsParent;
import br.org.otus.model.User;
import br.org.otus.response.exception.HttpResponseException;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipant;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipantSearchSettingsDto;
import org.ccem.otus.participant.service.NoteAboutParticipantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NoteAboutParticipantFacade.class, NoteAboutParticipantSearchSettingsDto.class})
public class NoteAboutParticipantFacadeTest extends LoggerTestsParent {

  private static final String NOTE_ABOUT_PARTICIPANT_ID = "5a33cb4a28f10d1043710f7d";
  private static final ObjectId NOTE_ABOUT_PARTICIPANT_OID = new ObjectId(NOTE_ABOUT_PARTICIPANT_ID);
  private static final String NOTE_ABOUT_PARTICIPANT_JSON = "{}";
  private static final Boolean STARRED = true;
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final String SEARCH_SETTINGS_JSON = "{}";
  private static final ObjectId USER_OID = new ObjectId("5a33cb4a28f10d1043710f00");

  @InjectMocks
  private NoteAboutParticipantFacade facade;

  @Mock
  private NoteAboutParticipantService service;

  @Mock
  private NoteAboutParticipant noteAboutParticipant;
  @Mock
  private User user;
  @Mock
  private NoteAboutParticipantSearchSettingsDto searchSettingsDto;

  private DataNotFoundException dataNotFoundException = new DataNotFoundException(new Throwable("error"));
  private ValidationException validationException = new ValidationException(new Throwable("error"));

  @Before
  public void setUp() throws Exception {
    setUpLogger(NoteAboutParticipantFacade.class);

    PowerMockito.whenNew(NoteAboutParticipant.class).withNoArguments().thenReturn(noteAboutParticipant);
    doReturn(noteAboutParticipant).when(noteAboutParticipant).deserializeNonStatic(NOTE_ABOUT_PARTICIPANT_JSON);
    doReturn(USER_OID).when(user).get_id();

    PowerMockito.mockStatic(NoteAboutParticipantSearchSettingsDto.class);
    when(NoteAboutParticipantSearchSettingsDto.class, "deserialize", SEARCH_SETTINGS_JSON)
      .thenReturn(searchSettingsDto);
  }

  @Test
  public void create_method_should_call_create_service_method_and_return_id(){
    doReturn(NOTE_ABOUT_PARTICIPANT_OID).when(service).create(USER_OID, noteAboutParticipant);
    assertEquals(
      NOTE_ABOUT_PARTICIPANT_OID.toHexString(),
      facade.create(user, NOTE_ABOUT_PARTICIPANT_JSON)
    );
  }

  @Test
  public void update_method_should_call_update_service_method() throws ValidationException, DataNotFoundException {
    facade.update(user, NOTE_ABOUT_PARTICIPANT_JSON);
    verify(service, Mockito.times(1)).update(USER_OID, noteAboutParticipant);
  }

  @Test(expected = HttpResponseException.class)
  public void update_method_should_handle_DataNotFoundException() throws ValidationException, DataNotFoundException {
    doThrow(dataNotFoundException).when(service).update(USER_OID, noteAboutParticipant);
    facade.update(user, NOTE_ABOUT_PARTICIPANT_JSON);
  }

  @Test(expected = HttpResponseException.class)
  public void update_method_should_handle_ValidationException() throws ValidationException, DataNotFoundException {
    doThrow(validationException).when(service).update(USER_OID, noteAboutParticipant);
    facade.update(user, NOTE_ABOUT_PARTICIPANT_JSON);
    verifyLoggerSevereWasCalled();
  }

  @Test
  public void updateStarred_method_should_call_updateStarred_service_method() throws ValidationException, DataNotFoundException {
    facade.updateStarred(user, NOTE_ABOUT_PARTICIPANT_ID, STARRED);
    verify(service, Mockito.times(1)).updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
  }

  @Test(expected = HttpResponseException.class)
  public void updateStarred_method_should_handle_DataNotFoundException() throws ValidationException, DataNotFoundException {
    doThrow(dataNotFoundException).when(service).updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
    facade.updateStarred(user, NOTE_ABOUT_PARTICIPANT_ID, STARRED);
  }

  @Test(expected = HttpResponseException.class)
  public void updateStarred_method_should_handle_ValidationException() throws ValidationException, DataNotFoundException {
    doThrow(validationException).when(service).updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
    facade.updateStarred(user, NOTE_ABOUT_PARTICIPANT_ID, STARRED);
    verifyLoggerSevereWasCalled();
  }

  @Test
  public void delete_method_should_call_delete_service_method() throws ValidationException, DataNotFoundException {
    facade.delete(user, NOTE_ABOUT_PARTICIPANT_ID);
    verify(service, Mockito.times(1)).delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
  }

  @Test(expected = HttpResponseException.class)
  public void delete_method_should_handle_DataNotFoundException() throws ValidationException, DataNotFoundException {
    doThrow(dataNotFoundException).when(service).delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
    facade.delete(user, NOTE_ABOUT_PARTICIPANT_ID);
  }

  @Test(expected = HttpResponseException.class)
  public void delete_method_should_handle_ValidationException() throws ValidationException, DataNotFoundException {
    doThrow(validationException).when(service).delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
    facade.delete(user, NOTE_ABOUT_PARTICIPANT_ID);
    verifyLoggerSevereWasCalled();
  }

  @Test
  public void getAll_method_should_call_getAll_service_method() throws ValidationException, DataNotFoundException, MemoryExcededException {
    facade.getAll(user, RECRUITMENT_NUMBER, SEARCH_SETTINGS_JSON);
    verify(service, Mockito.times(1)).getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto);
  }

  @Test(expected = HttpResponseException.class)
  public void getAll_method_should_handle_DataNotFoundException() throws ValidationException, DataNotFoundException, MemoryExcededException {
    doThrow(dataNotFoundException).when(service).getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto);
    facade.getAll(user, RECRUITMENT_NUMBER, SEARCH_SETTINGS_JSON);
  }

  @Test(expected = HttpResponseException.class)
  public void getAll_method_should_handle_ValidationException() throws ValidationException, DataNotFoundException, MemoryExcededException {
    doThrow(validationException).when(service).getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto);
    facade.getAll(user, RECRUITMENT_NUMBER, SEARCH_SETTINGS_JSON);
    verifyLoggerSevereWasCalled();
  }

}
