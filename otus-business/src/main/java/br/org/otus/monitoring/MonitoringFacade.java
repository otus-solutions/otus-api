package br.org.otus.monitoring;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.ActivitiesProgressReport;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.monitoring.ParticipantActivityReportDto;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
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

  public ArrayList<ActivitiesProgressReport> getActivitiesProgress() {
    try {
      return monitoringService.getActivitiesProgress();
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }


  public ArrayList<ActivitiesProgressReport> getActivitiesProgress(String center) {
    try {
      return monitoringService.getActivitiesProgress(center);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public ArrayList<ParticipantActivityReportDto> getParticipantActivitiesProgress(Long rn) {
    try {
      return monitoringService.getParticipantActivities(rn);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void setActivityApplicability(ActivityInapplicability activityInapplicability) {
    try {
      monitoringService.setActivityApplicability(activityInapplicability);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public ArrayList<ParticipantActivityReportDto> deleteActivityApplicability(Long rn, String acronym) {
    try {
      return null;
      //return monitoringService.getParticipantActivities(rn);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }
}
