package br.org.otus.monitoring;

import br.org.otus.laboratory.project.exam.examInapplicability.ExamInapplicability;
import br.org.otus.laboratory.project.exam.examInapplicability.persistence.ExamInapplicabilityDao;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import br.org.mongodb.MongoGenericDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ExamInapplicabilityDaoBean extends MongoGenericDao<Document> implements ExamInapplicabilityDao {

    public static final String COLLECTION_NAME = "exam_inapplicability";
    private static final String RECRUITMENT_NUMBER = "recruitmentNumber";
    private static final String NAME = "name";

    public ExamInapplicabilityDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void update(ExamInapplicability applicability) {
        Document parsed = Document.parse(ExamInapplicability.serialize(applicability));

        UpdateResult updateLabData = collection.updateOne(and(eq(RECRUITMENT_NUMBER, applicability.getRecruitmentNumber()),eq(NAME, applicability.getName())),
                new Document("$set", parsed), new UpdateOptions().upsert(true));
    }

    @Override
    public void delete(ExamInapplicability applicability) {
        DeleteResult deleteResult = collection.deleteOne(and(eq(RECRUITMENT_NUMBER, applicability.getRecruitmentNumber()),eq(NAME, applicability.getName())));

    }
}
