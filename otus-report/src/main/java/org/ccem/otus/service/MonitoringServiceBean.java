package org.ccem.otus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.model.monitoring.*;
import org.ccem.otus.participant.persistence.ParticipantDao;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.persistence.FlagReportDao;
import org.ccem.otus.persistence.MonitoringDao;
import org.ccem.otus.persistence.SurveyDao;

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
    List<String> surveys = surveyDao.listAcronyms();

    ArrayList<ActivitiesProgressReport> report = flagReportDao.getActivitiesProgressionReport();

    normalizeProgressReports(report, surveys);

    return report;
  }

  @Override
  public ArrayList<ActivitiesProgressReport> getActivitiesProgress(String center) {
    List<String> surveys = surveyDao.listAcronyms();

    ArrayList<ActivitiesProgressReport> report = flagReportDao.getActivitiesProgressionReport(center);

    normalizeProgressReports(report, surveys);

    return report;
  }

  private ArrayList<ActivitiesProgressReport> normalizeProgressReports(ArrayList<ActivitiesProgressReport> report, List<String> surveys) {
    HashMap<String, ActivityFlagReport> map = new HashMap<>();

    for (String acronym : surveys) {
      map.put(acronym, new ActivityFlagReport(acronym));
    }


    for (ActivitiesProgressReport activitiesProgressReport : report) {
      activitiesProgressReport.normalize(map);
    }

    return report;
  }

}
