package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configurationCrud.model.MomentConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.MomentConfigurationDao;
import br.org.otus.laboratory.configurationCrud.persistence.MomentConfigurationDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public class MomentConfigurationDaoBean extends MongoGenericDao<Document> implements MomentConfigurationDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String OBJECT_TYPE = "momentConfiguration";

    public MomentConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void create(MomentConfiguration momentConfiguration) {
        Document parsed = Document.parse(MomentConfiguration.serialize(momentConfiguration));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public ArrayList<MomentConfiguration> index() throws DataNotFoundException {
        ArrayList<MomentConfiguration> momentConfigurations = new ArrayList<>();
        Iterable<Document> result = collection.find(new Document("objectType", OBJECT_TYPE));

        result.forEach(document -> {
            momentConfigurations.add(MomentConfiguration.deserialize(document.toJson()));
        });
        return momentConfigurations;
    }
}
