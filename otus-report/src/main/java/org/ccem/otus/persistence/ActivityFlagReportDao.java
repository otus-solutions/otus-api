package org.ccem.otus.persistence;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.LinkedList;

public interface ActivityFlagReportDao {

  Document getActivitiesProgressReport(LinkedList<String> surveyAcronyms) throws DataNotFoundException;

  Document getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms) throws DataNotFoundException;
}
