package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.model.survey.activity.configuration.ActivityCategory;
import org.ccem.otus.persistence.ActivityConfigurationDao;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ActivityConfigurationDaoBean extends MongoGenericDao<Document> implements ActivityConfigurationDao {

    private static final String COLLECTION_NAME = "activity_configuration";

    public ActivityConfigurationDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<ActivityCategory> findNonDeleted() {
        ArrayList<ActivityCategory> categories = new ArrayList<>();

        BasicDBObject query = new BasicDBObject();
        query.put("objectType", "ActivityCategory");
        query.put("deleted", false);

        FindIterable<Document> documents = collection.find(query);

        documents.forEach((Block<? super Document>) document -> categories.add(ActivityCategory.deserialize(document.toJson())));

        return categories;
    }

    @Override
    public List<ActivityCategory> findAll() {
        ArrayList<ActivityCategory> categories = new ArrayList<>();

        BasicDBObject query = new BasicDBObject();
        query.put("objectType", "ActivityCategory");

        FindIterable<Document> documents = collection.find(query);

        documents.forEach((Block<? super Document>) document -> categories.add(ActivityCategory.deserialize(document.toJson())));

        return categories;
    }

    @Override
    public ActivityCategory findByName(String name) throws DataNotFoundException {
        BasicDBObject query = new BasicDBObject();
        query.put("objectType", "ActivityCategory");
        query.put("name", name);

        Document first = collection.find(query).first();
        return ActivityCategory.deserialize(first.toJson());
    }

    @Override
    public ActivityCategory create(ActivityCategory activityCategory) {
        Document parsed = Document.parse(ActivityCategory.serialize(activityCategory));
        super.persist(parsed);

        return activityCategory;
    }

    @Override
    public void softDelete(String name) throws DataNotFoundException {
        BasicDBObject query = new BasicDBObject();
        query.put("objectType", "ActivityCategory");
        query.put("name", name);

        UpdateResult updateResult = collection.updateOne(query, new Document("$set", new Document("deleted", true)), new UpdateOptions().upsert(false));

        if (updateResult.getMatchedCount() == 0) {
            throw new DataNotFoundException(
                    new Throwable("ActivityCategory {" + name + "} not found."));        }
    }

    @Override
    public ActivityCategory update(ActivityCategory activityCategory) throws DataNotFoundException {
        Document parsed = Document.parse(ActivityCategory.serialize(activityCategory));

        BasicDBObject query = new BasicDBObject();
        query.put("objectType", "ActivityCategory");
        query.put("name", activityCategory.getName());

        UpdateResult updateResult = collection.updateOne(query,
                new Document("$set", new Document("label",activityCategory.getLabel())), new UpdateOptions().upsert(false));

        if (updateResult.getMatchedCount() == 0){
            throw new DataNotFoundException(
                    new Throwable("ActivityCategory {" + activityCategory.getName() + "} not found."));
        }

        return activityCategory;
    }

    @Override
    public void setNewDefault(String name) throws DataNotFoundException {
        BasicDBObject query = new BasicDBObject();
        query.put("objectType", "ActivityCategory");
        query.put("isDefault", true);
        UpdateResult undefaultResult = collection.updateOne(query, new Document("$set", new Document("isDefault", false)), new UpdateOptions().upsert(false));

        if (undefaultResult.getMatchedCount() > 1){
            throw new DataNotFoundException(
                    new Throwable("Default category error. More than one default found"));
        }

        BasicDBObject otherQuery = new BasicDBObject();
        otherQuery.put("objectType", "ActivityCategory");
        otherQuery.put("name", name);
        UpdateResult defaultSetResult = collection.updateOne(otherQuery, new Document("$set", new Document("isDefault", true)), new UpdateOptions().upsert(false));

        if (defaultSetResult.getMatchedCount() == 0){
            throw new DataNotFoundException(
                    new Throwable("ActivityCategory {" + name + "} not found."));
        }

    }

    @Override
    public ActivityCategory getLastInsertedCategory() {
        BasicDBObject query = new BasicDBObject("objectType","ActivityCategory");

        Document lastInsertedDocument = collection.find(query).sort(new BasicDBObject("_id",-1)).first();

        return lastInsertedDocument == null ? null : ActivityCategory.deserialize(lastInsertedDocument.toJson());

    }


    public ActivityCategory getLaastInsertedCategory() {
        BasicDBObject query = new BasicDBObject("objectType","ActivityCategory");

        Document lastInsertedDocument = collection.find(query).sort(new BasicDBObject("_id",-1)).first();

        return lastInsertedDocument == null ? null : ActivityCategory.deserialize(lastInsertedDocument.toJson());

    }
}
