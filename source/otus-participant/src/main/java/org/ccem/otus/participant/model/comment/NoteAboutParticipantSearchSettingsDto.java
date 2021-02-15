package org.ccem.otus.participant.model.comment;

import org.ccem.otus.utils.SearchSettingsDto;

public class NoteAboutParticipantSearchSettingsDto extends SearchSettingsDto {

  @Override
  protected boolean isValidField(String fieldName) {
    return true; //TODO while search does not filter or sort by field
  }

  public static NoteAboutParticipantSearchSettingsDto deserialize(String json){
    return (NoteAboutParticipantSearchSettingsDto)deserialize(json, NoteAboutParticipantSearchSettingsDto.class);
  }
}
