package br.org.otus.laboratory.project;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.persistence.ExamDao;
import br.org.otus.examUploader.utils.ExamAdapter;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.aliquot.WorkAliquotFactory;
import br.org.otus.laboratory.project.exam.ExamLot;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.persistence.ParticipantDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ExamDaoBean extends MongoGenericDao<Document> implements ExamDao {

    private static final String COLLECTION_NAME = "exam_result";

    @Inject
    private ParticipantLaboratoryDao participantLaboratoryDao;
    @Inject
    private ParticipantDao participantDao;
    @Inject
    private LaboratoryConfigurationDao laboratoryConfigurationDao;

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
