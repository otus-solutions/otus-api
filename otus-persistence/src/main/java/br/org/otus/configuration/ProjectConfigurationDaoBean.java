package br.org.otus.configuration;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.client.result.UpdateResult;
import model.ProjectConfiguration;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import persistence.ProjectConfigurationDao;

import javax.ejb.Stateless;
//TODO 20/07/18: rename?

@Stateless
public class ProjectConfigurationDaoBean extends MongoGenericDao<Document> implements ProjectConfigurationDao {
    public static final String COLLECTION_NAME = "project_configuration";
    public static final String OBJECT_TYPE = "objectType";
    public static final String PROJECT_CONFIGURATION_NOT_FOUND= "Project Configuration not found";

    public ProjectConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }


    @Override
    public void enableParticipantRegistration(boolean permission) throws DataNotFoundException {
        Document query = new Document(OBJECT_TYPE, ProjectConfiguration.DEFAULT_OBJECT_TYPE); //todo: STATIC ACCESS TO CLASS OT

        Document update = new Document("$set",
                new Document("participantRegistration", permission)
        );

        UpdateResult updateResult = collection.updateOne(query, update);

        if (updateResult.getMatchedCount() == 0) {
            throw new DataNotFoundException(PROJECT_CONFIGURATION_NOT_FOUND);
        }
    }

    @Override
    public ProjectConfiguration getProjectConfiguration() throws DataNotFoundException {
        Document query = new Document(OBJECT_TYPE, ProjectConfiguration.DEFAULT_OBJECT_TYPE); //todo: STATIC ACCESS TO CLASS OT

        Document first = collection.find(query).first();

        if (first == null) {
            throw new DataNotFoundException(PROJECT_CONFIGURATION_NOT_FOUND);
        }

        return ProjectConfiguration.deserialize(first.toJson());
    }
}
