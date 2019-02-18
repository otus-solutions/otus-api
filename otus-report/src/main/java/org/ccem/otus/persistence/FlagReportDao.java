package org.ccem.otus.persistence;

import org.ccem.otus.model.monitoring.ActivitiesProgressReport;

import java.util.ArrayList;
import java.util.LinkedList;

public interface FlagReportDao {

  ArrayList<ActivitiesProgressReport> getActivitiesProgressReport();

  ArrayList<ActivitiesProgressReport> getActivitiesProgressReport(String center);

  String getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms);
}
