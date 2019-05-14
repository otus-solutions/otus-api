package org.ccem.otus.persistence;

import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ExamFlagReportDao {

  Document getExamProgressReport(LinkedList<String> possibleExams, ArrayList<Long> centerRns) throws DataNotFoundException;
}
