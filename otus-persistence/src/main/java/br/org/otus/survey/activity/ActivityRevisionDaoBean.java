package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityRevision.ActivityRevision;
import org.ccem.otus.persistence.ActivityRevisionDao;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class ActivityRevisionDaoBean extends MongoGenericDao<Document> implements ActivityRevisionDao {

    public static final String COLLECTION_NAME = "activity_revision";
    public static final String ACTIVITY_ID = "activityId";

    public ActivityRevisionDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<ActivityRevision> find(ObjectId activityId) {
        ArrayList<ActivityRevision> activitiesRevision = new ArrayList<ActivityRevision>();

        FindIterable<Document> result = collection.find(eq(ACTIVITY_ID, activityId));

        result.forEach((Block<Document>) document -> {
            activitiesRevision.add(ActivityRevision.deserialize(document.toJson()));
        });

        return activitiesRevision;
    }

    @Override
    public ObjectId persist(ActivityRevision activityRevision) {
        Document parsed = Document.parse(ActivityRevision.serialize(activityRevision));

        collection.insertOne(parsed);

        return parsed.getObjectId("_id");
    }
}
