package org.ccem.otus.persistence;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.LinkedList;
import java.util.List;

public interface ActivityFlagReportDao {

  Document getActivitiesProgressReport(LinkedList<String> surveyAcronyms) throws DataNotFoundException;
  Document getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms) throws DataNotFoundException;
  Document getActivitiesProgressReportWithInapplicability(String center, LinkedList<String> surveyAcronyms, List<Document>activityInapplicabilities) throws DataNotFoundException;
}
