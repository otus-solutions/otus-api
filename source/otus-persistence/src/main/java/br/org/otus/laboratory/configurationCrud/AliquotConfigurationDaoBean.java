package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configurationCrud.model.AliquotConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.AliquotConfigurationDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public class AliquotConfigurationDaoBean extends MongoGenericDao<Document> implements AliquotConfigurationDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String OBJECT_TYPE = "aliquotConfiguration";

    public AliquotConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void create(AliquotConfiguration aliquotConfiguration) {
        Document parsed = Document.parse(AliquotConfiguration.serialize(aliquotConfiguration));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public ArrayList<AliquotConfiguration> index() throws DataNotFoundException {
        ArrayList<AliquotConfiguration> aliquotConfigurations = new ArrayList<>();
        Iterable<Document> result = collection.find(new Document("objectType", OBJECT_TYPE));

        result.forEach(document -> {
            aliquotConfigurations.add(AliquotConfiguration.deserialize(document.toJson()));
        });
        return aliquotConfigurations;
    }
}
