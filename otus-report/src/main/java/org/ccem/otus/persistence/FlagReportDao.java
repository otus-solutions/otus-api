package org.ccem.otus.persistence;

import org.ccem.otus.model.monitoring.ActivitiesProgressReport;

import java.util.ArrayList;

public interface FlagReportDao {

  ArrayList<ActivitiesProgressReport> getActivitiesProgressReport();

  ArrayList<ActivitiesProgressReport> getActivitiesProgressReport(String center);
}
