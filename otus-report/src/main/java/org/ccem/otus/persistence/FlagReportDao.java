package org.ccem.otus.persistence;

import org.bson.Document;
import org.ccem.otus.model.monitoring.ActivitiesProgressReport;

import java.util.ArrayList;
import java.util.LinkedList;

public interface FlagReportDao {

  Document getActivitiesProgressReport(LinkedList<String> surveyAcronyms);

  Document getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms);
}
