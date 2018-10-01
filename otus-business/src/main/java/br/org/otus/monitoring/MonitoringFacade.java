package br.org.otus.monitoring;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.ActivitiesProgressionReport;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.service.MonitoringService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MonitoringFacade {

  @Inject
  private MonitoringService monitoringService;

  @Inject
  private SurveyFacade surveyFacade;

  public List<MonitoringDataSourceResult> get(String acronym) {
    try {
      return monitoringService.get(acronym);
    } catch (ValidationException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public List<String> listActivities() {
    return surveyFacade.listAcronyms();
  }

  public List<MonitoringCenter> getMonitoringCenters() {
    try {
      return monitoringService.getMonitoringCenter();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public ArrayList<ActivitiesProgressionReport> getActivitiesProgress() {
    try {
      return monitoringService.getActivitiesProgress();
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }


  public ArrayList<ActivitiesProgressionReport> getActivitiesProgress(String center) {
    try {
      return monitoringService.getActivitiesProgress(center);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

}
