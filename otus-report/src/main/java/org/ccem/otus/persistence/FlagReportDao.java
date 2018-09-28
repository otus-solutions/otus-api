package org.ccem.otus.persistence;

import org.ccem.otus.model.monitoring.ActivitiesProgressionReport;

import java.util.ArrayList;

public interface FlagReportDao {

  ArrayList<ActivitiesProgressionReport> getActivitiesProgressionReport();

  ArrayList<ActivitiesProgressionReport> getActivitiesProgressionReport(String center);
}
