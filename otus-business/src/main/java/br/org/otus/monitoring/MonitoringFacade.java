package br.org.otus.monitoring;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.survey.api.SurveyFacade;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.monitoring.*;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
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

  public ActivityProgressReportDto getActivitiesProgress() {
    try {
      return monitoringService.getActivitiesProgress();
    } catch (Exception e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }


  public ActivityProgressReportDto getActivitiesProgress(String center) {
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

  public ParticipantExamReportDto getParticipantExamsProgress(Long rn) {
    try {
      return monitoringService.getParticipantExams(rn);
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

   public void setExamApplicability(ExamInapplicability examInapplicability) {
    try {
      monitoringService.setExamInapplicability(examInapplicability);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
  }

  public void deleteExamInapplicability(ExamInapplicability examInapplicability) {
     try {
      monitoringService.deleteExamInapplicability(examInapplicability);
    } catch (Exception e) {
      throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
    }
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
      throw new HttpResponseException(NotFound.build());
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
