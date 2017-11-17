package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityConfigurationDao;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class ActivityConfigurationDaoBean extends MongoGenericDao<Document> implements ActivityConfigurationDao {

    private static final String COLLECTION_NAME = "activity_configuration";

    public ActivityConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<ActivityCategory> find() {
        return null;
    }

    @Override
    public ActivityCategory findByName(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public ActivityCategory create(ActivityCategory activityCategory) {
        Document parsed = Document.parse(ActivityCategory.serialize(activityCategory));
        collection.insertOne(parsed);

        return activityCategory;
    }

    @Override
    public String delete(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public String update(ActivityCategory activityCategory) throws DataNotFoundException {
        return null;
    }

    @Override
    public String updateDefault(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public Optional<ActivityCategory> getLastInsertedCategory() {
        Document lastInsertedDocument = (Document)collection.find(new BasicDBObject("objectType","ActivityCategory")).sort(new BasicDBObject("_id",-1)).limit(1);
        return Optional.ofNullable(ActivityCategory.deserialize(lastInsertedDocument.toJson()));
    }
}
