package org.ccem.otus.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.*;
import org.ccem.otus.model.monitoring.laboratory.LaboratoryProgressDTO;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.*;
import org.ccem.otus.persistence.laboratory.LaboratoryProgressDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class MonitoringServiceBean implements MonitoringService {

  @Inject
  private MonitoringDao monitoringDao;

  @Inject
  private FieldCenterDao fieldCenterDao;

  @Inject
  private ParticipantDao participantDao;

  @Inject
  private FlagReportDao flagReportDao;

  @Inject
  private SurveyDao surveyDao;

  @Inject
  private SurveyMonitoringDao surveyMonitoringDao;

  @Inject
  private LaboratoryProgressDao laboratoryProgressDao;

  @Inject
  private ActivityInapplicabilityDao activityInapplicabilityDao;

  @Override
  public List<MonitoringDataSourceResult> get(String acronym) throws ValidationException {
    MonitoringDataSource monitoringDataSource = new MonitoringDataSource();
    return monitoringDao.get(monitoringDataSource.buildQuery(acronym));
  }

  @Override
  public ArrayList<MonitoringCenter> getMonitoringCenter() throws DataNotFoundException {

    ArrayList<MonitoringCenter> results = new ArrayList<>();
    ArrayList<String> centers = fieldCenterDao.listAcronyms();

    for (String acronymCenter : centers) {

      FieldCenter fieldCenter = fieldCenterDao.fetchByAcronym(acronymCenter);
      Long goals = participantDao.countParticipantActivities(acronymCenter);

      MonitoringCenter monitoring = new MonitoringCenter();
      monitoring.setName(fieldCenter.getName());
      monitoring.setGoal(goals);
      monitoring.setBackgroundColor(fieldCenter.getBackgroundColor());
      monitoring.setBorderColor(fieldCenter.getBorderColor());

      results.add(monitoring);
      monitoring = null;
    }
    return results;
  }

  @Override
  public ActivityProgressReportDto getActivitiesProgress() {
    LinkedList<String> surveyAcronyms = new LinkedList<>(surveyDao.listAcronyms());

    ArrayList<ActivitiesProgressReport> report = flagReportDao.getActivitiesProgressReport();

    return new ActivityProgressReportDto(report, surveyAcronyms);
  }

  @Override
  public ActivityProgressReportDto getActivitiesProgress(String center) {
    LinkedList<String> surveyAcronyms = new LinkedList<>(surveyDao.listAcronyms());

    ArrayList<ActivitiesProgressReport> report = flagReportDao.getActivitiesProgressReport(center);

    return new ActivityProgressReportDto(report, surveyAcronyms);
  }

  @Override
  public ArrayList<ParticipantActivityReportDto> getParticipantActivities(Long rn) {
    return surveyMonitoringDao.getParticipantActivities(rn);
  }

  @Override
  public void setActivityApplicability(ActivityInapplicability applicability) throws DataNotFoundException {
    activityInapplicabilityDao.update(applicability);
  }

  @Override
  public void deleteActivityApplicability(Long rn, String acronym) throws DataNotFoundException {
    activityInapplicabilityDao.delete(rn, acronym);
  }

  @Override
  public LaboratoryProgressDTO getOrphanExams() {
    return laboratoryProgressDao.getOrphanExams();
  }

  @Override
  public LaboratoryProgressDTO getQuantitativeByTypeOfAliquots() {
    return laboratoryProgressDao.getQuantitativeByTypeOfAliquots();
  }

  @Override
  public LaboratoryProgressDTO getDataOfPendingResultsByAliquot() {
    return laboratoryProgressDao.getDataOfPendingResultsByAliquot();
  }

}
