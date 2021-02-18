package org.ccem.otus.model.noteAboutParticipant;

import org.ccem.otus.participant.model.noteAboutParticipant.NoteAboutParticipantSearchSettingsDto;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteAboutParticipantSearchSettingsDtoTest {

  private static final String JSON = "{}";

  @Test
  public void deserialize_static_method_should_convert_JsonString_to_objectModel(){
    assertTrue(NoteAboutParticipantSearchSettingsDto.deserialize(JSON) instanceof NoteAboutParticipantSearchSettingsDto);
  }

}
