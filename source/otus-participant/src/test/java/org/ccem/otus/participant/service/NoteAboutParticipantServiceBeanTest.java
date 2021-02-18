package org.ccem.otus.participant.service;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.common.MemoryExcededException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.searchSettingsDto.SearchSettingsDto;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipant;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipantResponse;
import org.ccem.otus.participant.persistence.NoteAboutParticipantDao;
import org.ccem.otus.utils.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateUtil.class})
public class NoteAboutParticipantServiceBeanTest {

  private static final String NOTE_ABOUT_PARTICIPANT_ID = "5a33cb4a28f10d1043710f7d";
  private static final ObjectId NOTE_ABOUT_PARTICIPANT_OID = new ObjectId(NOTE_ABOUT_PARTICIPANT_ID);
  private static final Boolean STARRED = true;
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final ObjectId USER_OID = new ObjectId("5a33cb4a28f10d1043710f00");
  private static final ObjectId USER_OID_2 = new ObjectId("5a33cb4a28f10d1043710f01");
  private static final String NOW_DATE_ISO = "2021-02-15T20:01:50.680Z";

  @InjectMocks
  private NoteAboutParticipantServiceBean serviceBean;

  @Mock
  private NoteAboutParticipantDao dao;

  @Mock
  private NoteAboutParticipant noteAboutParticipant;
  @Mock
  private NoteAboutParticipant noteAboutParticipantFound;
  @Mock
  private SearchSettingsDto searchSettingsDto;

  @Before
  public void setUp() throws Exception {
    doReturn(NOTE_ABOUT_PARTICIPANT_OID).when(noteAboutParticipant).getId();
    PowerMockito.mockStatic(DateUtil.class);
    when(DateUtil.class, "nowToISODate").thenReturn(NOW_DATE_ISO);
  }

  @Test
  public void create_method_should_set_userId_and_creationDate_and_call_dao_create_method(){
    serviceBean.create(USER_OID, noteAboutParticipant);
    verify(noteAboutParticipant, Mockito.times(1)).setUserId(USER_OID);
    verify(noteAboutParticipant, Mockito.times(1)).setCreationDate(NOW_DATE_ISO);
    verify(dao, Mockito.times(1)).create(noteAboutParticipant);
  }

  @Test
  public void update_method_should_set_lastUpdate_and_edited_flag_and_call_dao_update_method() throws ValidationException, DataNotFoundException {
    serviceBean.update(USER_OID, noteAboutParticipant);
    verify(noteAboutParticipant, Mockito.times(1)).setLastUpdate(NOW_DATE_ISO);
    verify(noteAboutParticipant, Mockito.times(1)).setEdited(true);
    verify(dao, Mockito.times(1)).update(USER_OID, noteAboutParticipant);
  }

  @Test(expected = DataNotFoundException.class)
  public void update_method_should_throw_DataNotFoundException_in_case_NOT_found_noteAboutParticipant_by_id_and_userId() throws ValidationException, DataNotFoundException {
    doThrow(new DataNotFoundException()).when(dao).update(USER_OID, noteAboutParticipant);
    checkNoteExistenceOnlyByIdOk();
    serviceBean.update(USER_OID, noteAboutParticipant);
  }

  @Test(expected = ValidationException.class)
  public void update_method_should_throw_ValidationException_in_case_found_noteAboutParticipant_by_id_but_another_userId() throws ValidationException, DataNotFoundException {
    doThrow(new DataNotFoundException()).when(dao).update(USER_OID, noteAboutParticipant);
    checkNoteExistenceOnlyByIdFindSomeNote();
    serviceBean.update(USER_OID, noteAboutParticipant);
  }


  @Test(expected = ValidationException.class)
  public void updateStarred_method_should_throw_ValidationException_in_case_null_starred() throws ValidationException, DataNotFoundException {
    serviceBean.updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, null);
  }

  @Test
  public void updateStarred_method_should_call_updateStarred_dao_method() throws ValidationException, DataNotFoundException {
    serviceBean.updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
    verify(dao, Mockito.times(1)).updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
  }

  @Test(expected = DataNotFoundException.class)
  public void updateStarred_method_should_throw_DataNotFoundException_in_case_NOT_found_noteAboutParticipant_by_id_and_userId() throws ValidationException, DataNotFoundException {
    doThrow(new DataNotFoundException()).when(dao).updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
    checkNoteExistenceOnlyByIdOk();
    serviceBean.updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
  }

  @Test(expected = ValidationException.class)
  public void updateStarred_method_should_throw_DataNotFoundException_ValidationException_in_case_found_noteAboutParticipant_by_id_but_another_userId() throws ValidationException, DataNotFoundException {
    doThrow(new DataNotFoundException()).when(dao).updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
    checkNoteExistenceOnlyByIdFindSomeNote();
    serviceBean.updateStarred(USER_OID, NOTE_ABOUT_PARTICIPANT_OID, STARRED);
  }


  @Test
  public void delete_method_should_call_delete_dao_method() throws ValidationException, DataNotFoundException {
    serviceBean.delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
    verify(dao, Mockito.times(1)).delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
  }

  @Test(expected = DataNotFoundException.class)
  public void delete_method_should_throw_DataNotFoundException_in_case_NOT_found_noteAboutParticipant_by_id_and_userId() throws ValidationException, DataNotFoundException {
    doThrow(new DataNotFoundException()).when(dao).delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
    checkNoteExistenceOnlyByIdOk();
    serviceBean.delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
  }

  @Test(expected = ValidationException.class)
  public void delete_method_should_throw_DataNotFoundException_ValidationException_in_case_found_noteAboutParticipant_by_id_but_another_userId() throws ValidationException, DataNotFoundException {
    doThrow(new DataNotFoundException()).when(dao).delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
    checkNoteExistenceOnlyByIdFindSomeNote();
    serviceBean.delete(USER_OID, NOTE_ABOUT_PARTICIPANT_OID);
  }


  @Test
  public void getAll_method_should_call_getAll_dao_method() throws ValidationException, DataNotFoundException, MemoryExcededException {
    doReturn(true).when(searchSettingsDto).isValid();
    List<NoteAboutParticipantResponse> noteAboutParticipantResponses = new ArrayList<>();
    doReturn(noteAboutParticipantResponses).when(dao).getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto);
    assertEquals(
      noteAboutParticipantResponses,
      serviceBean.getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto)
    );
    verify(dao, Mockito.times(1)).getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto);
  }

  @Test(expected = ValidationException.class)
  public void getAll_method_should_throw_DataNotFoundException_in_case_NOT_found_noteAboutParticipant_by_id_and_userId() throws ValidationException, DataNotFoundException, MemoryExcededException {
    doReturn(false).when(searchSettingsDto).isValid();
    serviceBean.getAll(USER_OID, RECRUITMENT_NUMBER, searchSettingsDto);
  }



  private void checkNoteExistenceOnlyByIdOk(){
    doReturn(null).when(dao).get(NOTE_ABOUT_PARTICIPANT_OID);
  }

  private void checkNoteExistenceOnlyByIdFindSomeNote(){
    doReturn(noteAboutParticipantFound).when(dao).get(NOTE_ABOUT_PARTICIPANT_OID);
  }
}
