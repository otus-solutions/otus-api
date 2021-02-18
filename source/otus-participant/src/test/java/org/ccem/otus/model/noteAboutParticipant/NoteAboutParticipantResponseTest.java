package org.ccem.otus.model.noteAboutParticipant;

import org.ccem.otus.participant.model.comment.NoteAboutParticipantResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class NoteAboutParticipantResponseTest {

  private static final String NOTE_ABOUT_PARTICIPANT_RESPONSE_JSON = "{}";

  private NoteAboutParticipantResponse noteAboutParticipantResponse;

  @Before
  public void setUp(){
    noteAboutParticipantResponse = new NoteAboutParticipantResponse();
  }

  @Test
  public void serialize_method_should_return_JsonString(){
    assertTrue(noteAboutParticipantResponse.serialize() instanceof String);
  }

  @Test
  public void deserialize_method_should_return_NoteAboutParticipantResponse_instance(){
    assertTrue(noteAboutParticipantResponse.deserializeNonStatic(NOTE_ABOUT_PARTICIPANT_RESPONSE_JSON) instanceof NoteAboutParticipantResponse);
  }

}
