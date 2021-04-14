package br.org.otus.participant;

import br.org.otus.participant.api.NoteAboutParticipantFacade;
import br.org.otus.security.AuthorizationHeaderReader;
import br.org.otus.user.UserAuthenticationResourceTestsParent;
import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipantResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AuthorizationHeaderReader.class})
public class NoteAboutParticipantResourceTest extends UserAuthenticationResourceTestsParent {

  private static final String NOTE_ABOUT_PARTICIPANT_ID = "123";
  private static final String NOTE_ABOUT_PARTICIPANT_JSON = "{}";
  private static final Boolean STARRED = true;
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final String SEARCH_SETTINGS_JSON = "{}";

  @InjectMocks
  private NoteAboutParticipantResource resource;

  @Mock
  private NoteAboutParticipantFacade facade;


  @Before
  public void setUp(){
    mockContextAndUser();
  }

  @Test
  public void create_method_should_call_facade_create_method(){
    doReturn(NOTE_ABOUT_PARTICIPANT_ID).when(facade).create(user, NOTE_ABOUT_PARTICIPANT_JSON);
    assertEquals(
      encapsulateExpectedStringResponse(NOTE_ABOUT_PARTICIPANT_ID),
      resource.create(request, NOTE_ABOUT_PARTICIPANT_JSON));
    verify(facade, Mockito.times(1)).create(user, NOTE_ABOUT_PARTICIPANT_JSON);
  }

  @Test
  public void update_method_should_call_update_facade_method(){
    assertEquals(
      EMPTY_RESPONSE,
      resource.update(request, NOTE_ABOUT_PARTICIPANT_JSON));
    verify(facade, Mockito.times(1)).update(user, NOTE_ABOUT_PARTICIPANT_JSON);
  }

  @Test
  public void updateStarred_method_should_call_facade_updateStarred_method(){
    assertEquals(
      EMPTY_RESPONSE,
      resource.updateStarred(request, NOTE_ABOUT_PARTICIPANT_ID, STARRED));
    verify(facade, Mockito.times(1)).updateStarred(user,  NOTE_ABOUT_PARTICIPANT_ID, STARRED);
  }

  @Test
  public void delete_method_should_call_facade_delete_method(){
    assertEquals(
      EMPTY_RESPONSE,
      resource.delete(request, NOTE_ABOUT_PARTICIPANT_ID));
    verify(facade, Mockito.times(1)).delete(user, NOTE_ABOUT_PARTICIPANT_ID);
  }

  @Test
  public void getAll_method_should_call_getAll_facade_method(){
    List<NoteAboutParticipantResponse> noteResponses = new ArrayList<>();
    doReturn(noteResponses).when(facade).filter(user, RECRUITMENT_NUMBER, SEARCH_SETTINGS_JSON);
    assertEquals(
      encapsulateExpectedResponse(noteResponses.toString()),
      resource.filter(request, RECRUITMENT_NUMBER, SEARCH_SETTINGS_JSON));
    verify(facade, Mockito.times(1)).filter(user, RECRUITMENT_NUMBER, SEARCH_SETTINGS_JSON);
  }

}
