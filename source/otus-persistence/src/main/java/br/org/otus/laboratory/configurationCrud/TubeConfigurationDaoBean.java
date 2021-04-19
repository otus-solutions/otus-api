package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configurationCrud.model.TubeConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.TubeConfigurationDao;
import com.mongodb.async.client.FindIterable;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.ArrayList;

public class TubeConfigurationDaoBean extends MongoGenericDao<Document> implements TubeConfigurationDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String OBJECT_TYPE = "tubeConfiguration";

    public TubeConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public void create(TubeConfiguration tubeConfiguration) {
        Document parsed = Document.parse(TubeConfiguration.serialize(tubeConfiguration));
        parsed.remove("_id");
        super.persist(parsed);
    }

    @Override
    public ArrayList<TubeConfiguration> index() throws DataNotFoundException {
        ArrayList<TubeConfiguration> tubeConfigurations = new ArrayList<>();
        Iterable<Document> result = collection.find(new Document("objectType", OBJECT_TYPE));

        result.forEach(document -> {
            tubeConfigurations.add(TubeConfiguration.deserialize(document.toJson()));
        });
        return tubeConfigurations;
    }
}
