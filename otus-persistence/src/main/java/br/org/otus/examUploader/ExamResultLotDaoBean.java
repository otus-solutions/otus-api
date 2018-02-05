package br.org.otus.examUploader;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.ExamLot;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ExamResultLotDaoBean extends MongoGenericDao implements ExamResultLotDao {

    private static final String COLLECTION_NAME = "exam_result_lot";

    public ExamResultLotDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ObjectId insert(ExamLot examLot) {
        Document parsed = Document.parse(ExamLot.serialize(examLot));

        super.persist(parsed);
        return (ObjectId)parsed.get( "_id" );
    }

    @Override
    public List<ExamLot> getAll() {
        ArrayList<ExamLot> results = new ArrayList<>();

        MongoCursor iterator = collection.find().iterator();

        while(iterator.hasNext()){
            Document next = (Document) iterator.next();
            results.add(ExamLot.deserialize(next.toJson()));
        }
        iterator.close();

        return results;
    }

    @Override
    public ExamLot getById(String id) throws DataNotFoundException {
        Document query = new Document("_id",new ObjectId(id));

        Document first = (Document) collection.find(query).first();

        if (first == null){
            throw new DataNotFoundException(
                    new Throwable("ExamLot not found. Id: " + id));
        }

        return ExamLot.deserialize(first.toJson());
    }

    @Override
    public void deleteById(String id) throws DataNotFoundException {
        Document query = new Document("_id",new ObjectId(id)    );
        DeleteResult deleteResult = collection.deleteOne(query);

        if (deleteResult.getDeletedCount() == 0) {
            throw new DataNotFoundException(
                    new Throwable("ExamLot not found. Id: " + id));
        }

    }
}
