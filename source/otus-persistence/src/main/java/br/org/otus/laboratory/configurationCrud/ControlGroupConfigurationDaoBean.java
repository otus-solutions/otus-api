package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configurationCrud.model.ControlGroupConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.ControlGroupConfigurationDao;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public class ControlGroupConfigurationDaoBean extends MongoGenericDao<Document> implements ControlGroupConfigurationDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String OBJECT_TYPE = "controlGroupConfiguration";

    public ControlGroupConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void create(ControlGroupConfiguration controlGroupConfiguration) {
        Document parsed = Document.parse(ControlGroupConfiguration.serialize(controlGroupConfiguration));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public ArrayList<ControlGroupConfiguration> index() throws DataNotFoundException {
        ArrayList<ControlGroupConfiguration> controlGroupConfigurations = new ArrayList<>();
        Iterable<Document> result = collection.find(new Document("objectType", OBJECT_TYPE));

        result.forEach(document -> {
            controlGroupConfigurations.add(ControlGroupConfiguration.deserialize(document.toJson()));
        });
        return controlGroupConfigurations;
    }
}
