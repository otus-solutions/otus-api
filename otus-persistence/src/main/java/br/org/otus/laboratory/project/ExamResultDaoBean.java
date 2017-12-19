package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.exam.upload.ExamResult;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ExamResultDaoBean extends MongoGenericDao implements ExamResultDao{


    private static final String COLLECTION_NAME = "exam_result";

    public ExamResultDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void insertMany(List<ExamResult> examResults) {
        List<Document> parsedExamResults = new ArrayList<>();
        examResults.stream().forEach(examResult -> parsedExamResults.add(Document.parse(ExamResult.serialize(examResult))));
        collection.insertMany(parsedExamResults);
    }

    @Override
    public List<ExamResult> getByExamId(ObjectId id) {
        ArrayList<ExamResult> examResults = new ArrayList<>();

        Document query = new Document();
        query.put("examId", id);

        MongoCursor iterator = collection.find(query).iterator();

        List<Document> employees = (List<Document>) collection.find(query).into(
                new ArrayList<Document>());

        while(iterator.hasNext()){
            Document next = (Document) iterator.next();
            examResults.add(ExamResult.deserialize(next.toJson()));
        }
        iterator.close();

        return examResults;
    }
}
