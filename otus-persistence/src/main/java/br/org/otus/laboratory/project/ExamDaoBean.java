package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.persistence.ExamDao;
import br.org.otus.examUploader.utils.ExamAdapter;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public class ExamDaoBean extends MongoGenericDao<Document> implements ExamDao {

    private static final String COLLECTION_NAME = "exam_result";

    public ExamDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }


    @Override
    public ObjectId insert(Exam exam) {
        ExamAdapter examAdapter = ExamAdapter.deserialize(Exam.serialize(exam));

        Document parsed = Document.parse(ExamAdapter.serialize(examAdapter));

        super.persist(parsed);
        return (ObjectId)parsed.get( "_id" );
    }

    @Override
    public List<br.org.otus.examUploader.ExamLot> getAll() {
        return null;
    }

    @Override
    public br.org.otus.examUploader.ExamLot getById(String id) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteById(String id) throws DataNotFoundException {

    }
}
