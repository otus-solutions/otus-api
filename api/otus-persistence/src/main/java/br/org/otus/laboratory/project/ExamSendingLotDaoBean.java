package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.persistence.ExamSendingLotDao;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ExamSendingLotDaoBean extends MongoGenericDao implements ExamSendingLotDao {

    private static final String COLLECTION_NAME = "exam_sending_lot";

    public ExamSendingLotDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public ObjectId insert(ExamSendingLot examSendingLot) {
        Document parsed = Document.parse(ExamSendingLot.serialize(examSendingLot));

        super.persist(parsed);
        return (ObjectId)parsed.get( "_id" );
    }

    @Override
    public List<ExamSendingLot> getAll() {
        ArrayList<ExamSendingLot> results = new ArrayList<>();

        MongoCursor iterator = collection.find().iterator();

        while(iterator.hasNext()){
            Document next = (Document) iterator.next();
            results.add(ExamSendingLot.deserialize(next.toJson()));
        }
        iterator.close();

        return results;
    }

    @Override
    public ExamSendingLot getById(String id) throws DataNotFoundException {
        Document query = new Document("_id",new ObjectId(id));

        Document first = (Document) collection.find(query).first();

        if (first == null){
            throw new DataNotFoundException(
                    new Throwable("ExamLot not found. Id: " + id));
        }

        return ExamSendingLot.deserialize(first.toJson());
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
