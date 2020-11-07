package br.org.otus.communication;

import br.org.otus.model.User;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.utils.ObjectIdToStringAdapter;


public class CommunicationDataBuilder {
  public CommunicationDataBuilder() {

  }

  public static GenericCommunicationData activitySending(String recipient, SurveyActivity surveyActivity) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.ACTIVITY_SENDING.getValue());

    genericCommunicationData.setEmail(recipient);
    genericCommunicationData.pushVariable("name", surveyActivity.getSurveyForm().getName());
    genericCommunicationData.pushVariable("acronym", surveyActivity.getSurveyForm().getAcronym());

    return genericCommunicationData;
  }

  public static GenericCommunicationData newUserGreeting(String recipient) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.NEW_USER_GREETINGS.getValue());
    genericCommunicationData.setEmail(recipient);

    return genericCommunicationData;
  }

  public static GenericCommunicationData newUserNotification(String recipient, User user) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.NEW_USER_NOTIFICATION.getValue());

    genericCommunicationData.setEmail(recipient);

    genericCommunicationData.pushVariable("name", user.getName());
    genericCommunicationData.pushVariable("surname", user.getSurname());
    genericCommunicationData.pushVariable("mail", user.getEmail());
    genericCommunicationData.pushVariable("phone", user.getPhone());
    return genericCommunicationData;
  }

  public static GenericCommunicationData userPasswordReset(String recipient, String token, String host) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.RESET_PASSWD_USER.getValue());

    genericCommunicationData.setEmail(recipient);
    genericCommunicationData.pushVariable("host", host);
    genericCommunicationData.pushVariable("token", token);

    return genericCommunicationData;
  }

  public static GenericCommunicationData enableUser(String recipient) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.ENABLE_USER.getValue());

    genericCommunicationData.setEmail(recipient);

    return genericCommunicationData;
  }

  public static GenericCommunicationData disableUser(String recipient) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.DISABLE_USER.getValue());

    genericCommunicationData.setEmail(recipient);

    return genericCommunicationData;
  }

  public static GenericCommunicationData systemInstallation(String recipient) {
    GenericCommunicationData genericCommunicationData = new GenericCommunicationData(TemplateEmailKeys.SYSTEM_INSTALATION.getValue());

    genericCommunicationData.setEmail(recipient);

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
