package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityConfigurationDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
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
        ArrayList<ActivityCategory> documentArray = new ArrayList<>();
        FindIterable<Document> documents = collection.find();
        documents.forEach((Block<? super Document>) document -> documentArray.add(ActivityCategory.deserialize(document.toJson())));
        return documentArray;
    }

    @Override
    public ActivityCategory findByName(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public ActivityCategory create(ActivityCategory activityCategory) {
        Document parsed = Document.parse(ActivityCategory.serialize(activityCategory));
        super.persist(parsed);

        return activityCategory;
    }

    @Override
    public String delete(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException {
        Document parsed = Document.parse(ActivityCategory.serialize(activityCategory));
        UpdateResult updateResult = collection.updateOne(new BasicDBObject("name", activityCategory.getName()), parsed);
        if (updateResult.getMatchedCount() == 0){
            throw new DataNotFoundException(
                    new Throwable("Category {" + activityCategory.getName() + "} not found."));
        }

        return activityCategory;
    }

    @Override
    public String findDefault(String name) throws DataNotFoundException {
        Document defaultcategory = collection.find(new BasicDBObject("isDefault", true)).first();
        return null;
    }

    @Override
    public Optional<ActivityCategory> getLastInsertedCategory() {
        Document lastInsertedDocument = collection.find(new BasicDBObject("objectType","ActivityCategory")).sort(new BasicDBObject("_id",-1)).first();
        if (lastInsertedDocument == null) return Optional.empty();
        else return Optional.ofNullable(ActivityCategory.deserialize(lastInsertedDocument.toJson()));

    }
}
