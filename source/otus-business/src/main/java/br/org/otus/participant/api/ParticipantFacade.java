package br.org.otus.participant.api;

import br.org.otus.participant.management.ManagementParticipantService;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.AlreadyExist;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
import br.org.otus.security.api.SecurityFacade;
import br.org.otus.security.dtos.PasswordResetRequestDto;
import br.org.otus.security.services.SecurityService;
import br.org.otus.user.dto.PasswordResetDto;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.AlreadyExistException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.http.EmailNotificationException;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;
import org.ccem.otus.service.ActivityService;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipantFacade {

  @Inject
  private ParticipantService participantService;

  @Inject
  private SecurityService securityService;

  @Inject
  private SecurityFacade securityFacade;

  @Inject
  private ManagementParticipantService managementParticipantService;

  @Inject
  private ActivityService activityService;

  public Participant getByRecruitmentNumber(long rn) {
    try {
      return participantService.getByRecruitmentNumber(rn);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));

    }
  }

  public ObjectId findIdByRecruitmentNumber(long rn) {
    try {
      return participantService.findIdByRecruitmentNumber(rn);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));

    }
  }

  public List<Participant> list(FieldCenter fieldCenter) {
    return participantService.list(fieldCenter);
  }

  public Long getPartipantsActives(String acronymCenter) {
    try {
      return participantService.getPartipantsActives(acronymCenter);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public Participant create(String participantJson, String userEmail) {
    Participant insertedParticipant = null;
    try {
      Participant participant = Participant.deserialize(participantJson);
      participant.setRegisteredBy(userEmail);
      insertedParticipant = participantService.create(participant);
      return insertedParticipant;
    } catch (ValidationException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public Participant update(String participantJson) {
    Participant updatedParticipant = null;
    try {
      Participant participant = Participant.deserialize(participantJson);
      updatedParticipant = participantService.update(participant);
      return updatedParticipant;
    } catch (ValidationException | DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }

  }

  public ArrayList<Long> listCenterRecruitmentNumbers(String center) {
    try {
      return participantService.listCenterRecruitmentNumbers(center);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void registerPassword(PasswordResetDto passwordResetDto) {
    try {
      passwordResetDto.encrypt();
      String requestEmail = securityService.getRequestEmail(passwordResetDto.getToken());
      participantService.registerPassword(requestEmail, passwordResetDto.getPassword());
      securityService.removePasswordResetRequests(requestEmail);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    } catch (EncryptedException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public void requestPasswordReset(PasswordResetRequestDto requestData) {
    requestData.setRedirectUrl(System.getenv("APP_URL"));
    securityFacade.removePasswordResetRequests(requestData.getEmail());
    securityFacade.requestParticipantPasswordReset(requestData);

    try {
      managementParticipantService.requestPasswordReset(requestData);
    } catch (EmailNotificationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public String requestPasswordResetLink(PasswordResetRequestDto requestData) {
    requestData.setRedirectUrl(System.getenv("APP_URL"));
    securityFacade.removePasswordResetRequests(requestData.getEmail());
    securityFacade.requestParticipantPasswordReset(requestData);

    try {
      return managementParticipantService.requestPasswordResetLink(requestData);
    } catch (MalformedURLException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
    }
  }

  public Boolean updateEmail(String participantId, String email) {
    try {
      if(!isValid(email)) {
        throw new ValidationException(new Throwable("Malformed email"));
      }

      if(!ObjectId.isValid(participantId)) {
        throw new ValidationException(new Throwable("ObjectId is not valid"));
      }

      ObjectId id = new ObjectId(participantId);
      Boolean result = participantService.updateEmail(id, email);
      Participant participant = participantService.getParticipant(id);

      if(result){
        activityService.updateParticipantEmail(participant.getRecruitmentNumber(), participant.getEmail());
      }

      return result;
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    } catch (AlreadyExistException e) {
      throw new HttpResponseException(AlreadyExist.build(e.getMessage()));
    }
  }

  public String getEmail(String participantId) {
    try {
      return participantService.getEmail(participantId);
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public Boolean deleteEmail(String participantId) {
    try {
      if(!ObjectId.isValid(participantId)) {
        throw new ValidationException(new Throwable("ObjectId is not valid"));
      }

      ObjectId id = new ObjectId(participantId);
      Boolean result = participantService.deleteEmail(id);
      Participant participant = participantService.getParticipant(id);

      if(result){
        activityService.updateParticipantEmail(participant.getRecruitmentNumber(), participant.getEmail());
      }

      return result;
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getMessage()));
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public boolean isValid(String email) {
    String regex = "^[\\w-_+]+(\\.[\\w-_]+)*\\@\\w+([[.-]?\\w])*(\\.[A-Za-z]{2,3})+$";
    return email.matches(regex);
  }
}
