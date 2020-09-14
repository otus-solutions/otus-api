package br.org.otus.outcomes;

import br.org.otus.communication.ActivitySendingCommunicationData;
import br.org.otus.communication.FollowUpCommunicationData;
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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class FollowUpFacade {

  @Inject
  private ParticipantFacade participantFacade;

  private static final String PARTICIPANT_NAME = "participant_name";
  private static final String EVENT_NAME = "event_name";

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
      GatewayResponse notification = new CommunicationGatewayService().sendMail(FollowUpCommunicationData.serialize(followUpCommunicationData));
      System.err.println(notification.getData());
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

  private void sendAutoFillActivityNotificationEmail(Participant participant, SurveyActivity surveyActivity) throws MalformedURLException {
    ActivitySendingCommunicationData activitySendingCommunicationData = new ActivitySendingCommunicationData();
    activitySendingCommunicationData.setEmail(participant.getEmail());
    activitySendingCommunicationData.pushVariable("name", surveyActivity.getSurveyForm().getName());
    activitySendingCommunicationData.pushVariable("acronym", surveyActivity.getSurveyForm().getAcronym());
    GatewayResponse notification = new CommunicationGatewayService().sendMail(ActivitySendingCommunicationData.serialize(activitySendingCommunicationData));
    System.err.println(notification.getData());
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
        accomplishedParticipantEventByActivity(activityId);
        break;

      case "REOPENED":
        reopenedParticipantEventByActivity(activityId);
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

  public Object accomplishedParticipantEventByActivity(String activityId) {
    try {
      return new OutcomeGatewayService().accomplishedParticipantEventByActivity(activityId);
    } catch (MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    } catch (JsonSyntaxException e) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(502), "OUTCOMES COMUNICATION FAIL"));
    }
  }

  public Object reopenedParticipantEventByActivity(String activityId) {
    try {
      return new OutcomeGatewayService().reopenedParticipantEventByActivity(activityId);
    } catch (MalformedURLException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    } catch (RequestException ex) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(ex.getErrorCode()), ex.getErrorMessage(), ex.getErrorContent()));
    } catch (JsonSyntaxException e) {
      throw new HttpResponseException(new ResponseInfo(Response.Status.fromStatusCode(502), "OUTCOMES COMUNICATION FAIL"));
    }
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

  public GatewayResponse cancelParticipantEventByActivityId(String activityID) throws MalformedURLException, ReadRequestException, RequestException {
    return new OutcomeGatewayService().cancelParticipantEventByActivityId(activityID);
  }
}

