package org.ccem.otus.model.noteAboutParticipant;

import org.bson.types.ObjectId;
import org.ccem.otus.participant.model.comment.NoteAboutParticipant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class NoteAboutParticipantTest {

  private static final ObjectId NOTE_ABOUT_PARTICIPANT_OID = new ObjectId("5a33cb4a28f10d1043710f7d");
  private static final ObjectId NOTE_ABOUT_PARTICIPANT_OID_2 = new ObjectId("5a33cb4a28f10d1043710f7e");
  private static final Long RECRUITMENT_NUMBER = 1234567L;
  private static final String CREATION_DATE = "2021-02-15T20:01:50.680Z";
  private static final String CREATION_DATE_2 = "2021-02-16T20:01:50.680Z";
  private static final String LAST_UPDATE = "2021-02-20T20:01:50.680Z";
  private static final String LAST_UPDATE_2 = "2021-02-21T20:01:50.680Z";
  private static final String COMMENT = "something about X";
  private static final ObjectId USER_OID = new ObjectId("5a33cb4a28f10d1043710f00");
  private static final ObjectId USER_OID_2 = new ObjectId("5a33cb4a28f10d1043710f00");
  private static final String NOTE_ABOUT_PARTICIPANT_JSON = "{}";

  private NoteAboutParticipant noteAboutParticipant;

  @Before
  public void setUp(){
    noteAboutParticipant = new NoteAboutParticipant();
    Whitebox.setInternalState(noteAboutParticipant, "_id", NOTE_ABOUT_PARTICIPANT_OID);
    Whitebox.setInternalState(noteAboutParticipant, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(noteAboutParticipant, "creationDate", CREATION_DATE);
    Whitebox.setInternalState(noteAboutParticipant, "lastUpdate", LAST_UPDATE);
    Whitebox.setInternalState(noteAboutParticipant, "comment", COMMENT);
    Whitebox.setInternalState(noteAboutParticipant, "userId", USER_OID);
  }

  @Test
  public void constructor_should_set_edited_and_starred_flags_as_false(){
    assertFalse(noteAboutParticipant.getEdited());
    assertFalse(noteAboutParticipant.getStarred());
  }

  @Test
  public void getters_check(){
    assertEquals(NOTE_ABOUT_PARTICIPANT_OID, noteAboutParticipant.getId());
    assertEquals(RECRUITMENT_NUMBER, noteAboutParticipant.getRecruitmentNumber());
    assertEquals(CREATION_DATE, noteAboutParticipant.getCreationDate());
    assertEquals(LAST_UPDATE, noteAboutParticipant.getLastUpdate());
    assertEquals(COMMENT, noteAboutParticipant.getComment());
    assertEquals(USER_OID, noteAboutParticipant.getUserId());
  }

  @Test
  public void id_setter_check(){
    noteAboutParticipant.setId(NOTE_ABOUT_PARTICIPANT_OID_2);
    assertEquals(NOTE_ABOUT_PARTICIPANT_OID_2, noteAboutParticipant.getId());
  }

  @Test
  public void creationDate_setter_check(){
    noteAboutParticipant.setCreationDate(CREATION_DATE_2);
    assertEquals(CREATION_DATE_2, noteAboutParticipant.getCreationDate());
  }

  @Test
  public void lastUpdate_setter_check(){
    noteAboutParticipant.setLastUpdate(LAST_UPDATE_2);
    assertEquals(LAST_UPDATE_2, noteAboutParticipant.getLastUpdate());
  }

  @Test
  public void edited_setter_check(){
    noteAboutParticipant.setEdited(true);
    assertTrue(noteAboutParticipant.getEdited());
  }

  @Test
  public void starred_setter_check(){
    noteAboutParticipant.setStarred(true);
    assertTrue(noteAboutParticipant.getStarred());
  }

  @Test
  public void userId_setter_check(){
    noteAboutParticipant.setUserId(USER_OID_2);
    assertEquals(USER_OID_2, noteAboutParticipant.getUserId());
  }

  @Test
  public void deserialize_method_should_return_NoteAboutParticipant_instance(){
    assertTrue(NoteAboutParticipant.deserialize(NOTE_ABOUT_PARTICIPANT_JSON) instanceof NoteAboutParticipant);
  }

}
