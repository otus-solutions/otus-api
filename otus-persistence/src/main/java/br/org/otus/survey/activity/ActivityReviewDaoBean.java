package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.persistence.ActivityReviewDao;

import java.util.ArrayList;
import java.util.List;


public class ActivityReviewDaoBean extends MongoGenericDao<Document> implements ActivityReviewDao {

    public static final String COLLECTION_NAME = "activity_review";

    public ActivityReviewDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

    @Override
    public List<ActivityReview> find() {
        ArrayList<ActivityReview> activitiesReview = new ArrayList<ActivityReview>();

        FindIterable<Document> result = collection.find();
        result.forEach((Block<Document>) document -> {
            activitiesReview.add(ActivityReview.deserialize(document.toJson()));
        });

        return activitiesReview;
    }

    @Override
    public ObjectId persist(ActivityReview activityReview) {
        Document parsed = Document.parse(ActivityReview.serialize(activityReview));

        collection.insertOne(parsed);

        return parsed.getObjectId("_id");
    }
}
