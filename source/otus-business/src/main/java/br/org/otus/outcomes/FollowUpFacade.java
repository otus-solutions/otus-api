package br.org.otus.outcomes;

import br.org.otus.communication.CommunicationDataBuilder;
import br.org.otus.communication.FollowUpCommunicationData;
import br.org.otus.communication.GenericCommunicationData;
import br.org.otus.gateway.gates.CommunicationGatewayService;
import br.org.otus.gateway.gates.OutcomeGatewayService;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.exception.ReadRequestException;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.exception.ResponseInfo;
import br.org.otus.response.info.Validation;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.SurveyActivity;
import org.ccem.otus.participant.model.Participant;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

public class FollowUpFacade {
  private final static Logger LOGGER = Logger.getLogger("br.org.otus.outcomes.FollowUpFacade");
  private final static String ACCOMPLISHED_METHOD = "accomplishedParticipantEventByActivity";
  private final static String REOPEN_METHOD = "reopenedParticipantEventByActivity";
  private static final String PARTICIPANT_NAME = "participant_name";
  private static final String EVENT_NAME = "event_name";

  @Inject
  private ParticipantFacade participantFacade;

  public Object createFollowUp(String FollowUpJson) {
    try {
      GatewayResponse followUpId = new OutcomeGatewayService().createFollowUp(FollowUpJson);
      return new GsonBuilder().create().fromJson((String) followUpId.getData(), Document.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object updateFollowUp(String FollowUpJson) {
    try {
      return new OutcomeGatewayService().updateFollowUp(FollowUpJson);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object deactivateFollowUp(String FollowUpId) {
    try {
      return new OutcomeGatewayService().deactivateFollowUp(FollowUpId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object listFollowUps() {
    try {
      GatewayResponse gatewayResponse = new OutcomeGatewayService().listFollowUps();
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object listFollowUpsByParticipant(String rn) {
    try {
      ObjectId participantId = participantFacade.findIdByRecruitmentNumber(Long.parseLong(rn));
      GatewayResponse gatewayResponse = new OutcomeGatewayService().listFollowUpsByParticipant(participantId.toString());
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object createFollowUpEvent(String followUpId, String eventJson) {
    try {
      GatewayResponse gatewayResponse = new OutcomeGatewayService().createFollowUpEvent(followUpId, eventJson);
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Object.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object removeFollowUpEvent(String eventId) {
    try {
      return new OutcomeGatewayService().removeFollowUpEvent(eventId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object startParticipantEvent(String rn, String eventJson) {
    try {
      ObjectId participantId = participantFacade.findIdByRecruitmentNumber(Long.parseLong(rn));
      Participant participant = participantFacade.getByRecruitmentNumber(Long.parseLong(rn));

      ParticipantEventDTO participantEventDTO = ParticipantEventDTO.deserialize(eventJson);

      if (!participantEventDTO.isValid()) {
        return new DataFormatException();
      }

      GatewayResponse gatewayResponse = new OutcomeGatewayService().startParticipantEvent(participantId.toString(), eventJson);

      if (!gatewayResponse.getData().toString().isEmpty()) {
        if (!(participantEventDTO.emailNotification == null)) {
          this.notificationEvent(participantEventDTO, participant);
        }
      }


      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), Object.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public void notificationEvent(ParticipantEventDTO participantEventDTO, Participant participant) throws MalformedURLException {
    if (!participantEventDTO.objectType.contains("Follow")) {
      GatewayResponse gatewayResponseNotification = new OutcomeGatewayService().getNotificationDataEvent(participantEventDTO._id);
      FollowUpCommunicationData followUpCommunicationData = FollowUpCommunicationData.deserialize(gatewayResponseNotification.getData().toString());
      followUpCommunicationData.setEmail(participant.getEmail());
      followUpCommunicationData.pushVariable(PARTICIPANT_NAME, participant.getName());
      followUpCommunicationData.pushVariable(EVENT_NAME, participantEventDTO.description);
      try {
        GatewayResponse notification = new CommunicationGatewayService().sendMail(FollowUpCommunicationData.serialize(followUpCommunicationData));
        logNotification("notificationEvent", notification.getData(), true, participant, null);
      } catch (ReadRequestException ex) {
        logNotification("sendAutoFillActivityNotificationEmail", ex, false, participant, null);
      }
    }
  }

  public void createParticipantActivityAutoFillEvent(SurveyActivity surveyActivity, boolean notify) {
    Long rn = surveyActivity.getParticipantData().getRecruitmentNumber();
    ObjectId participantId = participantFacade.findIdByRecruitmentNumber(rn);
    Participant participant = participantFacade.getByRecruitmentNumber(rn);

    ParticipantEventDTO participantEventDTO = ParticipantEventDTO.createActivityAutoFillEvent(surveyActivity.getSurveyForm().getAcronym(), surveyActivity.getSurveyForm().getName(), surveyActivity.getActivityID().toString());

    try {
      GatewayResponse gatewayResponse = new OutcomeGatewayService().startParticipantEvent(participantId.toString(), ParticipantEventDTO.serialize(participantEventDTO));
      if (notify && !gatewayResponse.getData().toString().isEmpty()) {
        sendAutoFillActivityNotificationEmail(participant, surveyActivity);
      }

    } catch (MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void sendAutoFillActivityNotificationEmail(Participant participant, SurveyActivity surveyActivity) throws MalformedURLException {
    GenericCommunicationData communicationData = CommunicationDataBuilder.activitySending(participant.getEmail(), surveyActivity);


    try {
      GatewayResponse notification = new CommunicationGatewayService().sendMail(CommunicationDataBuilder.serialize(communicationData));
      logNotification("sendAutoFillActivityNotificationEmail", notification.getData(), true, participant, surveyActivity);
    } catch (ReadRequestException ex) {
      logNotification("sendAutoFillActivityNotificationEmail", ex, false, participant, surveyActivity);
    }
  }

  private void logNotification(String action, Object notification, Boolean success, Participant participant, SurveyActivity activity) {
    if (success) {
      LOGGER.info("status: success, action: " + action +
        "participantId: " + participant.getId() + ", email: " + participant.getEmail() +
        "info: " + notification +
        getActivityInfo(activity));
      return;
    }
    LOGGER.severe(" status: fail, action: " + action +
      "participantId: " + participant.getId() + ", email: " + participant.getEmail() +
      "info: " + notification +
      getActivityInfo(activity));
  }

  private String getActivityInfo(SurveyActivity activity) {
    return activity instanceof SurveyActivity ?
      "activityId: " + activity.getActivityID() + ", acronym: " + activity.getSurveyForm().getAcronym() + "\n" : "";
  }

  public Object cancelParticipantEvent(String eventId) {
    try {
      return new OutcomeGatewayService().cancelParticipantEvent(eventId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object searchParticipantEvent(String rn, String eventId) {
    try {
      ObjectId participantId = participantFacade.findIdByRecruitmentNumber(Long.parseLong(rn));
      return new OutcomeGatewayService().searchParticipantEvent(participantId.toString(), eventId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public void statusUpdateEvent(String status, String activityId) {
    switch (status) {
      case "FINALIZED":
        accomplishedParticipantEventByActivity(activityId, status);
        break;

      case "REOPENED":
        reopenedParticipantEventByActivity(activityId, status);
        break;
    }
  }

  public Object accomplishedParticipantEvent(String eventId) {
    try {
      return new OutcomeGatewayService().accomplishedParticipantEvent(eventId);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public Object accomplishedParticipantEventByActivity(String activityId, String status) {
    try {
      return new OutcomeGatewayService().accomplishedParticipantEventByActivity(activityId);
    } catch (MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    } catch (JsonSyntaxException | IOException e) {
      callOtuscomesErrorLog(activityId, status, ACCOMPLISHED_METHOD, e);
      return false;
    }
  }

  public Object reopenedParticipantEventByActivity(String activityId, String status) {
    try {
      return new OutcomeGatewayService().reopenedParticipantEventByActivity(activityId);
    } catch (MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    } catch (JsonSyntaxException | IOException e) {
      callOtuscomesErrorLog(activityId, status, REOPEN_METHOD, e);
      return false;
    }
  }

  private void callOtuscomesErrorLog(String activityId, String status, String action, Exception e) {
    LOGGER.severe("" + "info: " + Response.Status.fromStatusCode(502) + ", cause: OUTCOMES COMMUNICATION FAIL"
      + "activityId: " + activityId + ", status:" + status + ", action:" + action);
  }

  public Object listAllParticipantEvents(String rn) {
    try {
      ObjectId participantId = participantFacade.findIdByRecruitmentNumber(Long.parseLong(rn));
      GatewayResponse gatewayResponse = new OutcomeGatewayService().listAllParticipantEvents(participantId.toString());
      return new GsonBuilder().create().fromJson((String) gatewayResponse.getData(), ArrayList.class);
    } catch (JsonSyntaxException | MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    }
  }

  public GatewayResponse cancelParticipantEventByActivityId(String activityID) throws ReadRequestException, RequestException {
    try {
      return new OutcomeGatewayService().cancelParticipantEventByActivityId(activityID);
    } catch (Exception e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }
}

