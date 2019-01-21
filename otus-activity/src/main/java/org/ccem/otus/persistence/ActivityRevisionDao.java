package org.ccem.otus.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;

public interface ActivityReviewDao {

    ObjectId persist(ActivityReview activityReview);

    List<ActivityReview> find();
}
