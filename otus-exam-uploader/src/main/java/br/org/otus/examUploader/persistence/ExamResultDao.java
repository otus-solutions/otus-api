package br.org.otus.examUploader.persistence;

import java.util.LinkedList;
import java.util.List;

import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import java.util.List;

public interface ExamResultDao {

  void insertMany(List<ExamResult> examResults);

  void deleteByExamSendingLotId(String id) throws DataNotFoundException;

  List<Exam> getByExamSendingLotId(ObjectId id) throws DataNotFoundException;

  LinkedList<ParticipantExamUploadResultExtraction> getExamResultsExtractionValues() throws DataNotFoundException;

  AggregateIterable<Document> aggregate(List<Bson> query);
}
