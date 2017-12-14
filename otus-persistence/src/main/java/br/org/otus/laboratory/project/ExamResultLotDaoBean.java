package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultLotDao;
import org.bson.Document;
import org.bson.types.ObjectId;

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
}
