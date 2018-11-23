package org.ccem.otus.service;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.*;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.*;

@Stateless
public class MonitoringServiceBean implements MonitoringService {

  @Inject
  private MonitoringDao monitoringDao;

  @Inject
  private SurveyMonitoringDao surveyMonitoringDao;

  @Inject
  private ActivityInapplicabilityDao activityInapplicabilityDao;

  @Inject
  private FieldCenterDao fieldCenterDao;

  @Inject
  private ParticipantDao participantDao;

  @Inject
  private FlagReportDao flagReportDao;

  @Inject
  private SurveyDao surveyDao;

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
      Long goals = participantDao.getPartipantsActives(acronymCenter);

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
  public ArrayList<ActivitiesProgressReport> getActivitiesProgress() {
    LinkedList<String> surveyAcronyms = new LinkedList<>(surveyDao.listAcronyms());

    ArrayList<ActivitiesProgressReport> report = flagReportDao.getActivitiesProgressReport();

    normalizeProgressReports(report, surveyAcronyms);

    return report;
  }

  @Override
  public ArrayList<ActivitiesProgressReport> getActivitiesProgress(String center) {
    LinkedList<String> surveyAcronyms = new LinkedList<>(surveyDao.listAcronyms());

    ArrayList<ActivitiesProgressReport> report = flagReportDao.getActivitiesProgressReport(center);

    normalizeProgressReports(report, surveyAcronyms);

    return report;
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

  private ArrayList<ActivitiesProgressReport> normalizeProgressReports(ArrayList<ActivitiesProgressReport> report,
                                                                       LinkedList<String> surveys) {

    for (ActivitiesProgressReport activitiesProgressReport : report) {
      activitiesProgressReport.normalize(surveys);
    }

    return report;
  }

}
