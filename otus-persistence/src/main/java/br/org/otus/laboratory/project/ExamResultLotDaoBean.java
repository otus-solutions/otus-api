package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultLotDao;
import com.mongodb.Block;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

public class ExamResultLotDaoBean extends MongoGenericDao implements ExamResultLotDao {

    private static final String COLLECTION_NAME = "exam_result_lot";

    public ExamResultLotDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ObjectId insert(ExamResultLot examResultLot) {
        Document parsed = Document.parse(ExamResultLot.serialize(examResultLot));

        super.persist(parsed);
        return (ObjectId)parsed.get( "_id" );
    }

    @Override
    public List<ExamResultLot> getAll() {
        ArrayList<ExamResultLot> results = new ArrayList<>();

        MongoCursor iterator = collection.find().iterator();

        while(iterator.hasNext()){
            Document next = (Document) iterator.next();
            results.add(ExamResultLot.deserialize(next.toJson()));
        }
        iterator.close();

        return results;
    }

    @Override
    public ExamResultLot getById(String id) {
        ArrayList<ExamResultLot> results = new ArrayList<>();

        Document query = new Document("_id",new ObjectId(id));

        //TODO 18/12/17: check this cast to document
        //TODO 18/12/17:  check how this returns when not found
        Document first = (Document) collection.find(query).first();

        return ExamResultLot.deserialize(first.toJson());
    }

    @Override
    public void deleteById(String id) throws DataNotFoundException {
        Document query = new Document("_id",new ObjectId(id)    );
        DeleteResult deleteResult = collection.deleteOne(query);

        if (!deleteResult.wasAcknowledged()) {
            //TODO 18/12/17: debug and check if fits
            throw new DataNotFoundException(
                    new Throwable("ExamResultLot not found."));
        }

        if (deleteResult.getDeletedCount() == 0) {
            throw new DataNotFoundException(
                    new Throwable("ExamResultLot not found."));
        }

    }
}
