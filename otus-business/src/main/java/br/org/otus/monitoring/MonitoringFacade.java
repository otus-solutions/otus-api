package br.org.otus.monitoring;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.participant.api.ParticipantFacade;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.ProgressReport;
import org.ccem.otus.model.monitoring.MonitoringCenter;
import org.ccem.otus.model.monitoring.MonitoringDataSourceResult;
import org.ccem.otus.model.monitoring.ParticipantActivityReportDto;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
import org.ccem.otus.service.LaboratoryMonitoringService;
import org.ccem.otus.service.MonitoringService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MonitoringFacade {

  @Inject
  private MonitoringService monitoringService;

  @Inject
  private SurveyFacade surveyFacade;

  @Inject
  private ParticipantFacade participantFacade;

  @Inject
  private LaboratoryConfigurationService laboratoryConfigurationService;

  @Inject
  private LaboratoryMonitoringService laboratoryMonitoringService;

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

  public ProgressReport getActivitiesProgress() {
    try {
      return monitoringService.getActivitiesProgress();
    } catch (Exception e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }


  public ProgressReport getActivitiesProgress(String center) {
    try {
      return monitoringService.getActivitiesProgress(center);
    } catch (Exception e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
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

  public void deleteActivityApplicability(Long rn, String acronym) {
    try {
      monitoringService.deleteActivityApplicability(rn, acronym);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  /* Laboratory Methods */

  public ProgressReport getExamFlagReport(String center) {
    LinkedList<String> possibleExams = laboratoryConfigurationService.getPossibleExams();
    ArrayList<Long> centerRecruitmentNumbers = participantFacade.getCenterRecruitmentNumbers(center);

    try {
      return laboratoryMonitoringService.getExamFlagReport(possibleExams, centerRecruitmentNumbers);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public LinkedList<String> getExamFlagReportLabels() {
    return laboratoryConfigurationService.getPossibleExams();
  }

  public LaboratoryProgressDTO getDataOrphanByExams() {
      try {
        return monitoringService.getDataOrphanByExams();
      } catch (DataNotFoundException e) {
        throw new HttpResponseException(NotFound.build());
      }
  }

  public LaboratoryProgressDTO getDataQuantitativeByTypeOfAliquots(String center) {
    try {
      return monitoringService.getDataQuantitativeByTypeOfAliquots(center);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build());
    }
  }

  public LaboratoryProgressDTO getDataOfPendingResultsByAliquot(String center) {
    try {
      return monitoringService.getDataOfPendingResultsByAliquot(center);
    } catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public LaboratoryProgressDTO getDataOfStorageByAliquot(String center) {
    try {
      return monitoringService.getDataOfStorageByAliquot(center);
    } catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build());
    }
  }

  public LaboratoryProgressDTO getDataByExam(String center) {
    try {
      return monitoringService.getDataByExam(center);
    } catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build());
    }
  }

  public LaboratoryProgressDTO getDataToCSVOfPendingResultsByAliquots(String center) {
    try {
      return monitoringService.getDataToCSVOfPendingResultsByAliquots(center);
    } catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build());
    }
  }

  public Object getDataToCSVOfOrphansByExam() {
    try {
      return monitoringService.getDataToCSVOfOrphansByExam();
    } catch (DataNotFoundException e){
      throw new HttpResponseException(NotFound.build());
    }
  }
}
