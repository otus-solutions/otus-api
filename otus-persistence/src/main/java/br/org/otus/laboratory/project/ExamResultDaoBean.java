package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.persistence.ExamResultDao;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamResultDaoBean extends MongoGenericDao implements ExamResultDao{


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
    public void deleteByExamId(String id) throws DataNotFoundException {
        Document query = new Document("examId", new ObjectId(id));
        DeleteResult deleteResult = collection.deleteMany(query);

        if (deleteResult.getDeletedCount() == 0){
            throw new DataNotFoundException(
                    new Throwable("Any result under the {" + id + "} examId."));
        }
    }

    @Override
    public List<Exam> getByExamLotId(ObjectId id) throws DataNotFoundException {
        ArrayList<Exam> exams = new ArrayList<>();

        Document match = new Document("$match", new Document("examLotId", id));
        Document lookup = new Document("$lookup", new Document("from","exam_result")
                .append("localField", "_id")
                .append("foreignField", "examId")
                .append("as", "examResults"));

        AggregateIterable output = collection.aggregate(Arrays.asList(match,lookup));

        for (Object anOutput : output) {
            Document next = (Document) anOutput;
            exams.add(Exam.deserialize(next.toJson()));
        }

        return exams;
    }
}
