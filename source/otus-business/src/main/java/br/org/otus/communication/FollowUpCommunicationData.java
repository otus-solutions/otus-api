package br.org.otus.communication;

public class FollowUpCommunicationData extends GenericCommunicationData {

  public FollowUpCommunicationData(String id) {
    super(id);
  }

  public static FollowUpCommunicationData deserialize(String json) {
    return FollowUpCommunicationData.getGsonBuilder().create().fromJson(json, FollowUpCommunicationData.class);
  }

}
