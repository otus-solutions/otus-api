package br.org.otus.survey.activity;

import br.org.mongodb.MongoGenericDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.persistence.ActivityReviewDao;



public class ActivityReviewDaoBean extends MongoGenericDao<Document> implements ActivityReviewDao {

    public static final String COLLECTION_NAME = "activity_review";

    public ActivityReviewDaoBean() {
        super(COLLECTION_NAME, Document.class);
    }

     @Override
    public ObjectId persist(ActivityReview activityReview) {
        Document parsed = Document.parse(ActivityReview.serialize(activityReview));
        super.persist(parsed);
//        collection.insertOne(parsed);

        return parsed.getObjectId("_id");
    }
}
