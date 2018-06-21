package br.org.otus.monitoring;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.monitoring.MonitoringCenter;
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

  public ArrayList<MonitoringDataSourceResult> get(String acronym) {
    try {
      return monitoringService.get(acronym);
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public List<String> listActivities() {
    return surveyFacade.getSurveys();
  }
  
  public List<MonitoringCenter> getMonitoringCenters() {
      try {
        return monitoringService.getMonitoringCenter();
      } catch (ValidationException e) {
        throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
      } catch (DataNotFoundException e) {
        throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
      }
  }

}
