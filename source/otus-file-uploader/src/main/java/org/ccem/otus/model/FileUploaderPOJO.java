package org.ccem.otus.model;

import java.io.InputStream;

import javax.ws.rs.FormParam;

public class FileUploaderPOJO {

  @FormParam("item_id")
  private String itemId;

  @FormParam("recruitment_number")
  private long recruitmentNumber;

  @FormParam("sent_date")
  private String sentDate;

  @FormParam("name")
  private String name;

  @FormParam("type")
  private String type;

  @FormParam("size")
  private String size;

  @FormParam("interviewer")
  private String interviewer;

  @FormParam("file")
  private InputStream file;

  public FileUploaderPOJO() {

  }

  public String getItemId() {
    return itemId;
  }

  public long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getSentDate() {
    return sentDate;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getSize() {
    return size;
  }

  public void setInterviewer(String interviewer) {
    this.interviewer = interviewer;
  }

  public String getInterviewer() {
    return interviewer;
  }

  public InputStream getFile() {
    return file;
  }

}
