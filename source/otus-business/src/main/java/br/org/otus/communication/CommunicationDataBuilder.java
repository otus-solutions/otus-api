package br.org.otus.communication;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.utils.ObjectIdToStringAdapter;
import br.org.otus.model.User;
import org.ccem.otus.participant.model.Participant;


public class CommunicationDataBuilder {
  public CommunicationDataBuilder() {

  }

  public static GenericCommunicationData newUserGreeting(User user) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.NEW_USER_GREETINGS.getValue());
    genericCommunicationData.setEmail(user.getEmail());

    return genericCommunicationData;
  }

  public static GenericCommunicationData activitySending(SurveyActivity surveyActivity, Participant participant) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.ACTIVITY_SENDING.getValue());

    genericCommunicationData.setEmail(participant.getEmail());
    genericCommunicationData.pushVariable("name", surveyActivity.getSurveyForm().getName());
    genericCommunicationData.pushVariable("acronym", surveyActivity.getSurveyForm().getAcronym());

    return genericCommunicationData;
  }

  public static GenericCommunicationData newUserNotification(User systemAdmin, User user) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.NEW_USER_NOTIFICATION.getValue());

    genericCommunicationData.setEmail(systemAdmin.getEmail());

    genericCommunicationData.pushVariable("name", user.getName());
    genericCommunicationData.pushVariable("surname", user.getSurname());
    genericCommunicationData.pushVariable("mail", user.getEmail());
    genericCommunicationData.pushVariable("phone", user.getPhone());
    return genericCommunicationData;
  }

  public static String serialize(GenericCommunicationData followUpCommunicationData) {
    return GenericCommunicationData.getGsonBuilder().create().toJson(followUpCommunicationData);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }
}
