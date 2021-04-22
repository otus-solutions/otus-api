package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configurationCrud.model.ExamConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.ExamConfigurationDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public class ExamConfigurationDaoBean extends MongoGenericDao<Document> implements ExamConfigurationDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String OBJECT_TYPE = "examConfiguration";

    public ExamConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void create(ExamConfiguration examConfiguration) {
        Document parsed = Document.parse(ExamConfiguration.serialize(examConfiguration));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public ArrayList<ExamConfiguration> index() throws DataNotFoundException {
        ArrayList<ExamConfiguration> examConfigurations = new ArrayList<>();
        Iterable<Document> result = collection.find(new Document("objectType", OBJECT_TYPE));

        result.forEach(document -> {
            examConfigurations.add(ExamConfiguration.deserialize(document.toJson()));
        });
        return examConfigurations;
    }
}
