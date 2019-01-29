package br.org.otus.laboratory.project;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.result.DeleteResult;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.laboratory.project.builder.ExamResultQueryBuilder;
import br.org.otus.laboratory.project.exam.examUploader.persistence.ExamUploader;

public class ExamResultDaoBean extends MongoGenericDao<Document> implements ExamResultDao, ExamUploader {

  private static final String COLLECTION_NAME = "exam_result";

  public ExamResultDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public void insertMany(List<ExamResult> examResults) {
    List<Document> parsedExamResults = new ArrayList<>();
    examResults.forEach(examResult -> parsedExamResults.add(Document.parse(ExamResult.serialize(examResult))));
    collection.insertMany(parsedExamResults);
  }

  @Override
  public void deleteByExamSendingLotId(String id) throws DataNotFoundException {
    Document query = new Document("examSendingLotId", new ObjectId(id));
    DeleteResult deleteResult = collection.deleteMany(query);

    if (deleteResult.getDeletedCount() == 0) {
      throw new DataNotFoundException(new Throwable("Any result under the {" + id + "} examLotId."));
    }
  }

  @Override
  public List<Exam> getByExamSendingLotId(ObjectId id) throws DataNotFoundException {
    ArrayList<Exam> exams = new ArrayList<>();

    Document match = new Document("$match", new Document("examSendingLotId", id).append("objectType", "Exam"));
    Document lookup = new Document("$lookup", new Document("from", "exam_result").append("localField", "_id").append("foreignField", "examId").append("as", "examResults"));

    AggregateIterable output = collection.aggregate(Arrays.asList(match, lookup));

    for (Object anOutput : output) {
      Document next = (Document) anOutput;
      exams.add(Exam.deserialize(next.toJson()));
    }

    return exams;
  }

  @Override
  public Boolean checkIfThereInExamResultLot(String aliquotCode) {
    Document document = collection.find(eq("aliquotCode", aliquotCode)).first();
    if (document != null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public LinkedHashSet<String> getExamResultsExtractionHeader() throws DataNotFoundException {
    LinkedHashSet<String> headers = new LinkedHashSet<>();
    Document query = new Document("objectType", "ExamResults");
    for (String header : collection.distinct("resultName", query, String.class)) {
      headers.add(header);
    }

    if (headers.isEmpty()) {
      throw new DataNotFoundException(new Throwable("There are no exams to describe the extraction headers."));
    }

    return headers;
  }

  @Override
  public LinkedHashSet<ParticipantExamUploadRecordExtraction> getExamResultsExtractionValues() throws DataNotFoundException {
    LinkedHashSet<ParticipantExamUploadRecordExtraction> values = new LinkedHashSet<>();
    List<Bson> query = new ExamResultQueryBuilder()
        .getExamResultsWithAliquotValid()
        .getSortingByExamName()
        .getExamResultsGroupByRecruitmentNumber()
        .getProjectionOfExamResultsToExtraction()
        .build();

    try {
      AggregateIterable<Document> output = collection.aggregate(query).allowDiskUse(true);
      for (Object anOutput : output) {
        Document result = (Document) anOutput;
        values.add(ParticipantExamUploadRecordExtraction.deserialize(result.toJson()));
      }
    } catch (Exception e) {
      throw new DataNotFoundException(new Throwable("There are no exams to extraction."));
    }
    return values;
  }
}
