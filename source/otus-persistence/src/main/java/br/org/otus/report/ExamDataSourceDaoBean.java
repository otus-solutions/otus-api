package br.org.otus.report;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.ExamResult;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.ccem.otus.model.dataSources.exam.ExamDataSource;
import org.ccem.otus.model.dataSources.exam.ExamDataSourceResult;
import org.ccem.otus.persistence.ExamDataSourceDao;
import org.json.JSONObject;

import javax.inject.Inject;
import java.util.ArrayList;

public class ExamDataSourceDaoBean extends MongoGenericDao<Document> implements ExamDataSourceDao {

  private static final String COLLECTION_NAME = "exam_result";
  private ExamDataSourceResult result;

  @Inject
  private ExamSendingLotDataSourceDaoBean examSendingLotDataSource;

  public ExamDataSourceDaoBean() {
    super(COLLECTION_NAME, Document.class);
  }

  @Override
  public ExamDataSourceResult getResult(Long recruitmentNumber, ExamDataSource examDataSource) {
    this.result = null;
    ExamResult examResult = null;

    ArrayList<Document> queryToLot = examDataSource.buildQuery(recruitmentNumber);
    AggregateIterable<?> outputLot = collection.aggregate(queryToLot);

    for (Object anOutput : outputLot) {
      Document next = (Document) anOutput;
      examResult = ExamResult.deserialize(next.toJson());
    }

    if (examResult != null) {
      ArrayList<Document> queryToExam = examDataSource.buildQueryToExam(examResult.getExamId(), recruitmentNumber);
      AggregateIterable<?> outputExam = collection.aggregate(queryToExam);

      for (Object anOutput : outputExam) {
        Document next = (Document) anOutput;
        this.result = ExamDataSourceResult.deserialize(new JSONObject(next).toString());
      }
    }

    return this.result;
  }

}
