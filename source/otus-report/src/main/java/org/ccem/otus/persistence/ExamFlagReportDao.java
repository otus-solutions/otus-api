package org.ccem.otus.persistence;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ExamFlagReportDao {

  Document getExamProgressReport(LinkedList<String> possibleExams, ArrayList<Long> centerRns, List<Document> examInapplicabilities) throws DataNotFoundException;
}
