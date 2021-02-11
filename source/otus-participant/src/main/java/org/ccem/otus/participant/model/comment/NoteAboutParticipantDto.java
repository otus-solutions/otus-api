package org.ccem.otus.participant.model.comment;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class NoteAboutParticipantDto extends SerializableModelWithID {

  private ObjectId _id;
  private Long recruitmentNumber;
  private String date;
  private Boolean edited;
  private String comment;
  private Boolean isCreator;
  private String userName;
  private String userEmail;


  public ObjectId getId() {
    return _id;
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getDate() {
    return date;
  }

  public Boolean getEdited() {
    return edited;
  }

  public String getComment() {
    return comment;
  }

  public Boolean getCreator() {
    return isCreator;
  }

  public String getUserName() {
    return userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setCreator(Boolean creator) {
    isCreator = creator;
  }

  public static NoteAboutParticipantDto deserialize(String json){
    return (new NoteAboutParticipantDto()).deserializeNonStatic(json);//TODO
  }

  @Override
  protected void registerSpecificTypeAdapter(GsonBuilder builder){
    registerGsonBuilderLongAdapter(builder);
    registerGsonBuilderLocalDateTimeAdapter(builder);
  }
}
