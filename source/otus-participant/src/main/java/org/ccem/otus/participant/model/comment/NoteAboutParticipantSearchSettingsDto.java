package org.ccem.otus.participant.model.comment;

import org.ccem.otus.model.searchSettingsDto.SearchSettingsDto;

public class NoteAboutParticipantSearchSettingsDto extends SearchSettingsDto {

  public static NoteAboutParticipantSearchSettingsDto deserialize(String json){
    return (NoteAboutParticipantSearchSettingsDto)deserialize(json, NoteAboutParticipantSearchSettingsDto.class);
  }
}
