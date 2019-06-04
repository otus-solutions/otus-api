package org.ccem.otus.persistence;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.BSON;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityInapplicability;

import java.util.LinkedList;
import java.util.List;

public interface ActivityFlagReportDao {

  Document getActivitiesProgressReport(LinkedList<String> surveyAcronyms) throws DataNotFoundException;
  Document getActivitiesProgressReport(String center, LinkedList<String> surveyAcronyms) throws DataNotFoundException;
  Document getActivitiesProgressReportWithInapplicability(String center, LinkedList<String> surveyAcronyms, List<Document>activityInapplicabilities) throws DataNotFoundException;
}
