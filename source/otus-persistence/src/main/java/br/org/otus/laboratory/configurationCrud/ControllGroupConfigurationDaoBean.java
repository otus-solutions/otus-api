package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configurationCrud.model.ControllGroupConfiguration;
import br.org.otus.laboratory.configurationCrud.model.MomentConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.ControllGroupConfigurationDao;
import br.org.otus.laboratory.configurationCrud.persistence.MomentConfigurationDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public class ControllGroupConfigurationDaoBean extends MongoGenericDao<Document> implements ControllGroupConfigurationDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String OBJECT_TYPE = "controllGroupConfiguration";

    public ControllGroupConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void create(ControllGroupConfiguration controllGroupConfiguration) {
        Document parsed = Document.parse(ControllGroupConfiguration.serialize(controllGroupConfiguration));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public ArrayList<ControllGroupConfiguration> index() throws DataNotFoundException {
        ArrayList<ControllGroupConfiguration> controllGroupConfigurations = new ArrayList<>();
        Iterable<Document> result = collection.find(new Document("objectType", OBJECT_TYPE));

        result.forEach(document -> {
            controllGroupConfigurations.add(ControllGroupConfiguration.deserialize(document.toJson()));
        });
        return controllGroupConfigurations;
    }
}
