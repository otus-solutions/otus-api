package br.org.otus.monitoring;

import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.participant.model.MonitoringCenter;
import org.ccem.otus.service.MonitoringService;

import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.api.SurveyFacade;

public class MonitoringFacade {

  @Inject
  private MonitoringService monitoringService;

  @Inject
  private SurveyFacade surveyFacade;
  
  @Inject
  private ParticipantFacade participantFacade;

  public List<MonitoringDataSourceResult> list() {
    try {
      return monitoringService.list();
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public MonitoringDataSourceResult get(String acronym) {
    try {
      return monitoringService.get(acronym);
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public List<String> listActivities() {
    return surveyFacade.getSurveys();
  }
  
  public List<MonitoringCenter> getGoalsByCenter() {
      return participantFacade.getGoalsByCenter();
  }

}
