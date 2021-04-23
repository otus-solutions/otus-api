package br.org.otus.laboratory.configurationCrud;

import br.org.mongodb.MongoGenericDao;
import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configurationCrud.persistence.LaboratoryConfigurationCrudDao;

import org.bson.Document;

public class LaboratoryConfigurationCrudDaoBean extends MongoGenericDao<Document> implements LaboratoryConfigurationCrudDao {
    private static final String COLLECTION_NAME = "laboratory_configuration";
    private static final String LABORATORY_CONFIGURATION_OBJECT_TYPE = "LaboratoryConfiguration";
    
    public LaboratoryConfigurationCrudDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public LaboratoryConfiguration persistConfiguration(LaboratoryConfiguration laboratoryConfiguration) {
        Document currentConfiguration = collection
                .find(new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE)).first();

        if (currentConfiguration == null) {
            super.persist(LaboratoryConfiguration.serialize(laboratoryConfiguration));
            return laboratoryConfiguration;
        } else {
            collection.updateOne(
                new Document(OBJECT_TYPE_PATH, LABORATORY_CONFIGURATION_OBJECT_TYPE),
                new Document(
                    "$set",
                    new Document(
                        OBJECT_TYPE_PATH,
                        LABORATORY_CONFIGURATION_OBJECT_TYPE + "Backup"
                    )
                )
            );

            LaboratoryConfiguration currentObject = LaboratoryConfiguration.deserialize(currentConfiguration.toJson());
            laboratoryConfiguration.setVersion(currentObject.getVersion() + 1);
            super.persist(LaboratoryConfiguration.serialize(laboratoryConfiguration));

            return laboratoryConfiguration;
        }
    }
}
