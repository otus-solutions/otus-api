package org.ccem.otus.participant.model.comment;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class NoteAboutParticipantDto extends SerializableModelWithID {

  private ObjectId _id;
  private Long recruitmentNumber;
  private String creationDate;
  private String lastUpdate;
  private Boolean edited;
  private Boolean starred;
  private String comment;
  private String userName;
  private Boolean isCreator;

  @Override
  protected void registerSpecificTypeAdapter(GsonBuilder builder){
    registerGsonBuilderLongAdapter(builder);
    registerGsonBuilderLocalDateTimeAdapter(builder);
  }

  public static NoteAboutParticipantDto deserialize(String json){
    return (NoteAboutParticipantDto)deserialize(json, NoteAboutParticipantDto.class);
  }
}
