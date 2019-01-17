package org.ccem.otus.persistence;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;

public interface ActivityReviewDao {

    ObjectId persist(ActivityReview activityReview);
}
