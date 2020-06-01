package br.org.otus.communication;

import com.google.gson.GsonBuilder;

public class ParticipantFilterDTO {
  private IssueMessageDTO issueMessageDTO;
  private long rn;
  private String center;
  private String name;

  public static String serialize(ParticipantFilterDTO participantFilterDTO) {
    return ParticipantFilterDTO.getGsonBuilder().create().toJson(participantFilterDTO);
  }

  public static ParticipantFilterDTO deserialize(String issueMessageDTO) {
    return ParticipantFilterDTO.getGsonBuilder().create().fromJson(issueMessageDTO,ParticipantFilterDTO.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder();
  }

  public void setRn(long rn) {
    this.rn = rn;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCenter(String center) {
    this.center = center;
  }

  public long getRn() {
    return rn;
  }

  public String getCenter() {
    return center;
  }

  public String getName() {
    return name;
  }

  public void setIssueMessageDTO(IssueMessageDTO issueMessageDTO) {
    this.issueMessageDTO = issueMessageDTO;
  }
}
