package org.ccem.otus.participant.model.comment;

import org.bson.types.ObjectId;
import org.ccem.otus.model.SerializableModelWithID;

public class NoteAboutParticipant extends SerializableModelWithID {

  private ObjectId _id;
  private Long recruitmentNumber;
  private String date;
  private Boolean edited;
  private String comment;
  private ObjectId userId;

  public NoteAboutParticipant() {
    edited = false;
  }

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

  public ObjectId getUserId() {
    return userId;
  }

  public void setId(ObjectId _id) {
    this._id = _id;
  }

  public void setRecruitmentNumber(Long recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setEdited(Boolean edited) {
    this.edited = edited;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setUserId(ObjectId userId) {
    this.userId = userId;
  }

  public boolean isCreator(ObjectId userId){
    return this.userId.equals(userId);
  }
}
