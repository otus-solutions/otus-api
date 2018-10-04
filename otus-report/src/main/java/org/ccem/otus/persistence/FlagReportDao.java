package org.ccem.otus.persistence;

import org.ccem.otus.model.monitoring.ActivitiesProgressReport;

import java.util.ArrayList;

public interface FlagReportDao {

  ArrayList<ActivitiesProgressReport> getActivitiesProgressionReport();

  ArrayList<ActivitiesProgressReport> getActivitiesProgressionReport(String center);
}
